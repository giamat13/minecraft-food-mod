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
        return stack.has(DataComponents.FOOD) && stack.getItem() != ModItems.COMBINED_FOOD;
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
            if (recipe == this) continue;
            if (recipe instanceof CraftingRecipe crafting && crafting.matches(input, world)) {
                return true;
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

        int totalNutrition = 0;
        double totalSatWeight = 0;
        List<MobEffectInstance> allEffects = new ArrayList<>();

        for (ItemStack food : foods) {
            FoodProperties fc = food.get(DataComponents.FOOD);
            if (fc != null) {
                totalNutrition += fc.nutrition();
                totalSatWeight += (double) fc.nutrition() * fc.saturation();
            }

            Consumable consumable = food.get(DataComponents.CONSUMABLE);
            if (consumable != null) {
                for (ConsumeEffect effect : consumable.onConsumeEffects()) {
                    if (effect instanceof ApplyStatusEffectsConsumeEffect applyEffect) {
                        allEffects.addAll(applyEffect.effects());
                    }
                }
            }
        }

        float avgSatMod = totalNutrition > 0 ? (float) (totalSatWeight / totalNutrition) : 0f;

        ItemStack result = new ItemStack(ModItems.COMBINED_FOOD);
        result.set(DataComponents.FOOD, new FoodProperties(totalNutrition, avgSatMod, false));
        result.set(ModComponents.COMBINED_FOOD_DATA, new CombinedFoodData(foods.size(), allEffects));

        return result;
    }

    @Override
    public RecipeSerializer<CombinedFoodRecipe> getSerializer() {
        return ModRecipes.COMBINED_FOOD_SERIALIZER;
    }
}
