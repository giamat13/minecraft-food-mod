package com.food.giamat.recipe;

import com.food.giamat.init.ModComponents;
import com.food.giamat.init.ModItems;
import com.food.giamat.item.CombinedFoodData;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.Consumable;
import net.minecraft.world.item.consume_effects.ApplyStatusEffectsConsumeEffect;
import net.minecraft.world.item.consume_effects.ConsumeEffect;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;

import java.util.ArrayList;
import java.util.List;

public class CombinedFoodRecipe extends CustomRecipe {
    public static final CombinedFoodRecipe INSTANCE = new CombinedFoodRecipe();
    public static final MapCodec<CombinedFoodRecipe> MAP_CODEC = MapCodec.unit(INSTANCE);
    public static final StreamCodec<RegistryFriendlyByteBuf, CombinedFoodRecipe> STREAM_CODEC = StreamCodec.unit(INSTANCE);

    public CombinedFoodRecipe() {}

    private boolean isCombineable(ItemStack stack) {
        // A combined meal has no FOOD component of its own (see assemble()), so it
        // needs its own check via COMBINED_FOOD_DATA to be usable as an ingredient.
        return stack.has(DataComponents.FOOD) || stack.has(ModComponents.COMBINED_FOOD_DATA);
    }

    @Override
    public boolean matches(CraftingInput input, Level world) {
        int foodCount = 0;
        for (int i = 0; i < input.size(); i++) {
            ItemStack stack = input.getItem(i);
            if (stack.isEmpty()) continue;
            if (!isCombineable(stack)) return false;
            foodCount++;
        }
        if (foodCount < 2) return false;

        // Lowest priority: combining foods is only a fallback. If the same grid
        // is a valid input for any other crafting recipe (a mod recipe like
        // hamburger / buttered_bread, or a vanilla one), defer to it instead of
        // producing a combined meal.
        return !anotherRecipeMatches(input, world);
    }

    private boolean anotherRecipeMatches(CraftingInput input, Level world) {
        MinecraftServer server = world.getServer();
        // On a remote client we can't inspect the recipe set; the server is the
        // authority for the crafted result, so just allow the match here.
        if (server == null) return false;
        for (RecipeHolder<?> holder : server.getRecipeManager().getRecipes()) {
            Recipe<?> recipe = holder.value();
            // Skip every combined-food recipe (not just the singleton), so we never
            // "defer to ourselves" and let the meal shadow a real recipe.
            if (recipe instanceof CombinedFoodRecipe) continue;
            if (!(recipe instanceof CraftingRecipe crafting)) continue;
            // A single broken recipe's matches() must not abort the scan: if it threw
            // and bubbled up, we'd treat "no other recipe matched" as true and the
            // combined meal would win. Isolate each check.
            try {
                if (crafting.matches(input, world)) return true;
            } catch (Exception ignored) {
                // this recipe simply doesn't claim the grid
            }
        }
        return false;
    }

    @Override
    public ItemStack assemble(CraftingInput input) {
        List<ItemStack> foods = new ArrayList<>();
        for (int i = 0; i < input.size(); i++) {
            ItemStack stack = input.getItem(i);
            if (!stack.isEmpty() && isCombineable(stack)) {
                foods.add(stack);
            }
        }

        if (foods.size() < 2) return ItemStack.EMPTY;

        int totalFoodCount = 0;
        int totalNutrition = 0;
        double totalSatWeight = 0;
        List<MobEffectInstance> allEffects = new ArrayList<>();

        for (ItemStack food : foods) {
            // A combined meal carries no FOOD/CONSUMABLE component of its own (see
            // below); its totals already live in COMBINED_FOOD_DATA, so fold that in
            // directly instead of reading components that aren't there.
            CombinedFoodData nested = food.get(ModComponents.COMBINED_FOOD_DATA);
            if (nested != null) {
                totalFoodCount += nested.foodCount();
                totalNutrition += nested.nutrition();
                totalSatWeight += (double) nested.nutrition() * nested.saturation();
                for (MobEffectInstance instance : nested.effects()) {
                    addStacking(allEffects, instance);
                }
                continue;
            }

            totalFoodCount++;
            FoodProperties fc = food.get(DataComponents.FOOD);
            if (fc != null) {
                totalNutrition += fc.nutrition();
                // FoodProperties#saturation is a modifier; the actual saturation a
                // food restores is nutrition * modifier * 2. Sum those weights so the
                // combined meal restores exactly the total of every ingredient.
                totalSatWeight += (double) fc.nutrition() * fc.saturation();
            }

            Consumable consumable = food.get(DataComponents.CONSUMABLE);
            if (consumable != null) {
                for (ConsumeEffect effect : consumable.onConsumeEffects()) {
                    if (effect instanceof ApplyStatusEffectsConsumeEffect applyEffect) {
                        for (MobEffectInstance instance : applyEffect.effects()) {
                            addStacking(allEffects, instance);
                        }
                    }
                }
            }
        }

        // Nutrition-weighted average modifier: totalNutrition * avgSatMod * 2 equals
        // the summed saturation of all ingredients, so the meal is exactly as filling
        // as eating each food individually.
        float avgSatMod = totalNutrition > 0 ? (float) (totalSatWeight / totalNutrition) : 0f;

        ItemStack result = new ItemStack(ModItems.COMBINED_FOOD);
        // Nutrition, saturation and the merged effects are all applied by
        // CombinedFoodItem from this component when the meal is eaten. We do NOT
        // put a FOOD component on the stack: that would make vanilla restore the
        // hunger/saturation a second time on top of the manual application.
        result.set(ModComponents.COMBINED_FOOD_DATA,
                new CombinedFoodData(totalFoodCount, totalNutrition, avgSatMod, allEffects));

        return result;
    }

    /**
     * Merges an effect into the accumulator. Identical effects (same type and
     * amplifier) have their durations added together instead of one silently
     * replacing the other, so combining several foods that share an effect stacks
     * its duration. Different amplifiers of the same effect are kept separate.
     */
    private static void addStacking(List<MobEffectInstance> list, MobEffectInstance incoming) {
        for (int i = 0; i < list.size(); i++) {
            MobEffectInstance current = list.get(i);
            if (current.getEffect().equals(incoming.getEffect())
                    && current.getAmplifier() == incoming.getAmplifier()) {
                list.set(i, new MobEffectInstance(
                        current.getEffect(),
                        current.getDuration() + incoming.getDuration(),
                        current.getAmplifier(),
                        current.isAmbient(),
                        current.isVisible(),
                        current.showIcon()));
                return;
            }
        }
        list.add(new MobEffectInstance(incoming));
    }

    @Override
    public RecipeSerializer<CombinedFoodRecipe> getSerializer() {
        return ModRecipes.COMBINED_FOOD_SERIALIZER;
    }
}
