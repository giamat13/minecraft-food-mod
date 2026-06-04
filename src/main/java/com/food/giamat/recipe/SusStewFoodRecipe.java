package com.food.giamat.recipe;

import com.food.giamat.init.ModItems;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.SuspiciousStewEffectsComponent;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.SpecialCraftingRecipe;
import net.minecraft.recipe.book.CraftingRecipeCategory;
import net.minecraft.recipe.input.CraftingRecipeInput;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

/**
 * Combines any food item with a suspicious stew to produce the same food
 * carrying the stew's effects. Vanilla cake becomes sus_cake; mod pizza
 * becomes sus_pizza (both are placed as blocks that apply the effect on bite).
 * All other foods become item-carried and the effect fires when eaten.
 */
public class SusStewFoodRecipe extends SpecialCraftingRecipe {

    public SusStewFoodRecipe(CraftingRecipeCategory category) {
        super(category);
    }

    private boolean isSusEligible(ItemStack stack) {
        return (stack.contains(DataComponentTypes.FOOD) || stack.isOf(Items.CAKE))
                && !stack.isOf(Items.SUSPICIOUS_STEW);
    }

    private record Match(ItemStack food, ItemStack stew) {}

    @Nullable
    private Match findMatch(CraftingRecipeInput input) {
        ItemStack food = ItemStack.EMPTY;
        ItemStack stew = ItemStack.EMPTY;
        for (int i = 0; i < input.size(); i++) {
            ItemStack stack = input.getStackInSlot(i);
            if (stack.isEmpty()) continue;
            if (stack.isOf(Items.SUSPICIOUS_STEW)) {
                if (!stew.isEmpty()) return null;
                stew = stack;
            } else if (isSusEligible(stack)) {
                if (!food.isEmpty()) return null;
                food = stack;
            } else {
                return null;
            }
        }
        return (!food.isEmpty() && !stew.isEmpty()) ? new Match(food, stew) : null;
    }

    @Override
    public boolean matches(CraftingRecipeInput input, World world) {
        return findMatch(input) != null;
    }

    @Override
    public ItemStack craft(CraftingRecipeInput input, RegistryWrapper.WrapperLookup lookup) {
        Match match = findMatch(input);
        if (match == null) return ItemStack.EMPTY;

        SuspiciousStewEffectsComponent effects =
                match.stew().get(DataComponentTypes.SUSPICIOUS_STEW_EFFECTS);

        // Vanilla cake → sus_cake block item
        if (match.food().isOf(Items.CAKE)) {
            ItemStack result = new ItemStack(ModItems.SUS_CAKE);
            if (effects != null) result.set(DataComponentTypes.SUSPICIOUS_STEW_EFFECTS, effects);
            return result;
        }

        // Mod pizza → sus_pizza block item
        if (match.food().isOf(ModItems.PIZZA)) {
            ItemStack result = new ItemStack(ModItems.SUS_PIZZA);
            if (effects != null) result.set(DataComponentTypes.SUSPICIOUS_STEW_EFFECTS, effects);
            return result;
        }

        // Any other food: add component to the existing stack
        ItemStack result = match.food().copyWithCount(1);
        if (effects != null) result.set(DataComponentTypes.SUSPICIOUS_STEW_EFFECTS, effects);
        return result;
    }

    @Override
    public RecipeSerializer<? extends SpecialCraftingRecipe> getSerializer() {
        return ModRecipes.SUS_STEW_FOOD_SERIALIZER;
    }
}
