package com.food.giamat.recipe;

import com.food.giamat.init.ModComponents;
import com.food.giamat.init.ModItems;
import com.food.giamat.item.CombinedFoodData;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.ConsumableComponent;
import net.minecraft.component.type.FoodComponent;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.item.ItemStack;
import net.minecraft.item.consume.ApplyEffectsConsumeEffect;
import net.minecraft.item.consume.ConsumeEffect;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.SpecialCraftingRecipe;
import net.minecraft.recipe.book.CraftingRecipeCategory;
import net.minecraft.recipe.input.CraftingRecipeInput;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

public class CombinedFoodRecipe extends SpecialCraftingRecipe {

    public CombinedFoodRecipe(CraftingRecipeCategory category) {
        super(category);
    }

    private boolean isCombineable(ItemStack stack) {
        // Any food item except the combined food itself (no recursive combining)
        return stack.contains(DataComponentTypes.FOOD) && !stack.isOf(ModItems.COMBINED_FOOD);
    }

    @Override
    public boolean matches(CraftingRecipeInput input, World world) {
        int foodCount = 0;
        for (int i = 0; i < input.size(); i++) {
            ItemStack stack = input.getStackInSlot(i);
            if (stack.isEmpty()) continue;
            if (!isCombineable(stack)) return false;
            foodCount++;
        }
        return foodCount >= 2;
    }

    @Override
    public ItemStack craft(CraftingRecipeInput input, RegistryWrapper.WrapperLookup lookup) {
        List<ItemStack> foods = new ArrayList<>();
        for (int i = 0; i < input.size(); i++) {
            ItemStack stack = input.getStackInSlot(i);
            if (!stack.isEmpty() && isCombineable(stack)) {
                foods.add(stack);
            }
        }

        if (foods.size() < 2) return ItemStack.EMPTY;

        int totalNutrition = 0;
        double totalSatWeight = 0;
        List<StatusEffectInstance> allEffects = new ArrayList<>();

        for (ItemStack food : foods) {
            FoodComponent fc = food.get(DataComponentTypes.FOOD);
            if (fc != null) {
                totalNutrition += fc.nutrition();
                totalSatWeight += (double) fc.nutrition() * fc.saturation();
            }

            ConsumableComponent consumable = food.get(DataComponentTypes.CONSUMABLE);
            if (consumable != null) {
                for (ConsumeEffect effect : consumable.onConsumeEffects()) {
                    if (effect instanceof ApplyEffectsConsumeEffect applyEffect) {
                        allEffects.addAll(applyEffect.effects());
                    }
                }
            }
        }

        float avgSatMod = totalNutrition > 0 ? (float) (totalSatWeight / totalNutrition) : 0f;

        ItemStack result = new ItemStack(ModItems.COMBINED_FOOD);
        result.set(DataComponentTypes.FOOD, new FoodComponent(totalNutrition, avgSatMod, false));
        result.set(ModComponents.COMBINED_FOOD_DATA, new CombinedFoodData(foods.size(), allEffects));

        return result;
    }

    @Override
    public RecipeSerializer<? extends SpecialCraftingRecipe> getSerializer() {
        return ModRecipes.COMBINED_FOOD_SERIALIZER;
    }
}
