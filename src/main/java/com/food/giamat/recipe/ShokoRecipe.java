package com.food.giamat.recipe;

import com.food.giamat.init.ModItems;
import java.util.List;
import net.minecraft.block.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
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

public class ShokoRecipe extends SpecialCraftingRecipe {

    public ShokoRecipe(CraftingRecipeCategory category) {
        super(category);
    }

    @Override
    public boolean matches(CraftingRecipeInput input, World world) {
        boolean hasMelted = false, hasMilk = false;
        for (int i = 0; i < input.size(); i++) {
            ItemStack stack = input.getStackInSlot(i);
            if (stack.isEmpty()) continue;
            if (stack.isOf(ModItems.MELTED_CHOCOLATE) && !hasMelted) { hasMelted = true; }
            else if (stack.isOf(Items.MILK_BUCKET) && !hasMilk) { hasMilk = true; }
            else { return false; }
        }
        return hasMelted && hasMilk;
    }

    @Override
    public ItemStack craft(CraftingRecipeInput input, RegistryWrapper.WrapperLookup lookup) {
        return new ItemStack(ModItems.SHOKO);
    }

    @Override
    public DefaultedList<ItemStack> getRecipeRemainders(CraftingRecipeInput input) {
        DefaultedList<ItemStack> remainders = DefaultedList.ofSize(input.size(), ItemStack.EMPTY);
        for (int i = 0; i < input.size(); i++) {
            if (input.getStackInSlot(i).isOf(Items.MILK_BUCKET)) {
                remainders.set(i, new ItemStack(Items.BUCKET));
            }
        }
        return remainders;
    }

    @Override
    public boolean isIgnoredInRecipeBook() { return false; }

    @Override
    public IngredientPlacement getIngredientPlacement() {
        return IngredientPlacement.forShapeless(List.of(
                Ingredient.ofItems(ModItems.MELTED_CHOCOLATE),
                Ingredient.ofItems(Items.MILK_BUCKET)
        ));
    }

    @Override
    public List<RecipeDisplay> getDisplays() {
        return List.of(new ShapelessCraftingRecipeDisplay(
                List.of(
                        new SlotDisplay.ItemSlotDisplay(ModItems.MELTED_CHOCOLATE),
                        new SlotDisplay.WithRemainderSlotDisplay(
                                new SlotDisplay.ItemSlotDisplay(Items.MILK_BUCKET),
                                new SlotDisplay.ItemSlotDisplay(Items.BUCKET))
                ),
                new SlotDisplay.ItemSlotDisplay(ModItems.SHOKO),
                new SlotDisplay.ItemSlotDisplay(Blocks.CRAFTING_TABLE.asItem())
        ));
    }

    @Override
    public RecipeSerializer<? extends SpecialCraftingRecipe> getSerializer() {
        return ModRecipes.SHOKO_SERIALIZER;
    }
}
