package com.food.giamat.recipe;

import com.food.giamat.init.ModItems;
import java.util.List;
import net.minecraft.block.Blocks;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.PotionContentsComponent;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.potion.Potions;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.IngredientPlacement;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.SpecialCraftingRecipe;
import net.minecraft.recipe.book.CraftingRecipeCategory;
import net.minecraft.recipe.display.RecipeDisplay;
import net.minecraft.recipe.display.ShapelessCraftingRecipeDisplay;
import net.minecraft.recipe.display.SlotDisplay;
import net.minecraft.recipe.input.CraftingRecipeInput;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.world.World;

/**
 * Shapeless crafting recipe: flour + water (water bucket OR water bottle) = dough.
 * The water container is NOT consumed: an empty bucket / glass bottle is returned,
 * only the water itself is used up.
 */
public class DoughRecipe extends SpecialCraftingRecipe {

    public DoughRecipe(CraftingRecipeCategory category) {
        super(category);
    }

    private static boolean isWaterSource(ItemStack stack) {
        if (stack.isOf(Items.WATER_BUCKET)) {
            return true;
        }
        if (stack.isOf(Items.POTION)) {
            PotionContentsComponent contents = stack.get(DataComponentTypes.POTION_CONTENTS);
            return contents != null && contents.matches(Potions.WATER);
        }
        return false;
    }

    @Override
    public boolean matches(CraftingRecipeInput input, World world) {
        boolean hasFlour = false;
        boolean hasWater = false;
        for (int i = 0; i < input.size(); i++) {
            ItemStack stack = input.getStackInSlot(i);
            if (stack.isEmpty()) {
                continue;
            }
            if (stack.isOf(ModItems.FLOUR)) {
                if (hasFlour) {
                    return false;
                }
                hasFlour = true;
            } else if (isWaterSource(stack)) {
                if (hasWater) {
                    return false;
                }
                hasWater = true;
            } else {
                // any other item in the grid means this is not a dough recipe
                return false;
            }
        }
        return hasFlour && hasWater;
    }

    @Override
    public ItemStack craft(CraftingRecipeInput input, RegistryWrapper.WrapperLookup lookup) {
        return new ItemStack(ModItems.DOUGH);
    }

    @Override
    public DefaultedList<ItemStack> getRecipeRemainders(CraftingRecipeInput input) {
        DefaultedList<ItemStack> remainders = DefaultedList.ofSize(input.size(), ItemStack.EMPTY);
        for (int i = 0; i < input.size(); i++) {
            ItemStack stack = input.getStackInSlot(i);
            if (stack.isOf(Items.WATER_BUCKET)) {
                remainders.set(i, new ItemStack(Items.BUCKET));
            } else if (stack.isOf(Items.POTION)) {
                remainders.set(i, new ItemStack(Items.GLASS_BOTTLE));
            }
        }
        return remainders;
    }

    // --- Recipe book support: show this recipe in the automatic recipe book ---

    @Override
    public boolean isIgnoredInRecipeBook() {
        return false;
    }

    @Override
    public IngredientPlacement getIngredientPlacement() {
        return IngredientPlacement.forShapeless(List.of(
                Ingredient.ofItems(ModItems.FLOUR),
                Ingredient.ofItems(Items.WATER_BUCKET, Items.POTION)));
    }

    @Override
    public List<RecipeDisplay> getDisplays() {
        // Water shown as: water bucket (-> empty bucket) OR water bottle (-> glass bottle)
        ItemStack waterBottle = PotionContentsComponent.createStack(Items.POTION, Potions.WATER);
        SlotDisplay waterDisplay = new SlotDisplay.CompositeSlotDisplay(List.of(
                new SlotDisplay.WithRemainderSlotDisplay(
                        new SlotDisplay.ItemSlotDisplay(Items.WATER_BUCKET),
                        new SlotDisplay.ItemSlotDisplay(Items.BUCKET)),
                new SlotDisplay.WithRemainderSlotDisplay(
                        new SlotDisplay.StackSlotDisplay(waterBottle),
                        new SlotDisplay.ItemSlotDisplay(Items.GLASS_BOTTLE))));

        return List.of(new ShapelessCraftingRecipeDisplay(
                List.of(new SlotDisplay.ItemSlotDisplay(ModItems.FLOUR), waterDisplay),
                new SlotDisplay.ItemSlotDisplay(ModItems.DOUGH),
                new SlotDisplay.ItemSlotDisplay(Blocks.CRAFTING_TABLE.asItem())));
    }

    @Override
    public RecipeSerializer<? extends SpecialCraftingRecipe> getSerializer() {
        return ModRecipes.DOUGH_SERIALIZER;
    }
}
