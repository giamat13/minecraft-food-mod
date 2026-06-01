package com.food.giamat.recipe;

import com.food.giamat.init.ModComponents;
import com.food.giamat.init.ModItems;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.FoodComponent;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.SpecialCraftingRecipe;
import net.minecraft.recipe.book.CraftingRecipeCategory;
import net.minecraft.recipe.input.CraftingRecipeInput;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;

/**
 * Adds salt to ANY food item: food + salt = the same food, but salted.
 * This is NOT a new item - it just tags the food stack with a component, gives it
 * +1 nutrition (half a hunger point) and renames it "... with Salt". Because it is
 * not a separate item it never shows up in the creative menu or recipe book.
 */
public class SaltRecipe extends SpecialCraftingRecipe {

    public SaltRecipe(CraftingRecipeCategory category) {
        super(category);
    }

    private static boolean isSaltableFood(ItemStack stack) {
        return stack.contains(DataComponentTypes.FOOD)
                && !stack.getOrDefault(ModComponents.SALTED, false);
    }

    private ItemStack findFood(CraftingRecipeInput input) {
        ItemStack food = ItemStack.EMPTY;
        int saltCount = 0;
        for (int i = 0; i < input.size(); i++) {
            ItemStack stack = input.getStackInSlot(i);
            if (stack.isEmpty()) {
                continue;
            }
            if (stack.isOf(ModItems.SALT)) {
                saltCount++;
            } else if (isSaltableFood(stack) && food.isEmpty()) {
                food = stack;
            } else {
                return ItemStack.EMPTY;
            }
        }
        return (saltCount == 1 && !food.isEmpty()) ? food : ItemStack.EMPTY;
    }

    @Override
    public boolean matches(CraftingRecipeInput input, World world) {
        return !findFood(input).isEmpty();
    }

    @Override
    public ItemStack craft(CraftingRecipeInput input, RegistryWrapper.WrapperLookup lookup) {
        ItemStack food = findFood(input);
        if (food.isEmpty()) {
            return ItemStack.EMPTY;
        }

        ItemStack result = food.copyWithCount(1);
        result.set(ModComponents.SALTED, true);

        // +1 nutrition (= half a hunger point) on top of the food's normal value.
        FoodComponent original = result.get(DataComponentTypes.FOOD);
        if (original != null) {
            result.set(DataComponentTypes.FOOD, new FoodComponent(
                    original.nutrition() + 1,
                    original.saturation(),
                    original.canAlwaysEat()));
        }

        // Rename to "<name> with Salt".
        Text base = food.getName();
        result.set(DataComponentTypes.CUSTOM_NAME,
                Text.empty()
                        .append(base)
                        .append(Text.literal(" with Salt").formatted(Formatting.WHITE))
                        .formatted(Formatting.ITALIC));

        return result;
    }

    @Override
    public RecipeSerializer<? extends SpecialCraftingRecipe> getSerializer() {
        return ModRecipes.SALT_SERIALIZER;
    }
}
