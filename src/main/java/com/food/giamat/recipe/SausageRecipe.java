package com.food.giamat.recipe;

import com.food.giamat.init.ModItems;
import java.util.List;
import java.util.Set;
import net.minecraft.block.Blocks;
import net.minecraft.item.Item;
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

public class SausageRecipe extends SpecialCraftingRecipe {

    private static final Set<Item> RAW_MEATS = Set.of(
            Items.PORKCHOP, Items.BEEF, Items.CHICKEN, Items.MUTTON, Items.RABBIT
    );
    private static final Set<Item> COOKED_MEATS = Set.of(
            Items.COOKED_PORKCHOP, Items.COOKED_BEEF, Items.COOKED_CHICKEN, Items.COOKED_MUTTON, Items.COOKED_RABBIT
    );

    public SausageRecipe(CraftingRecipeCategory category) {
        super(category);
    }

    private static boolean isRawMeat(ItemStack stack) {
        return RAW_MEATS.contains(stack.getItem());
    }

    private static boolean isCookedMeat(ItemStack stack) {
        return COOKED_MEATS.contains(stack.getItem());
    }

    private int findType(CraftingRecipeInput input) {
        // returns 0=none, 1=raw, 2=cooked
        boolean hasMeat = false;
        boolean hasString = false;
        boolean isCooked = false;
        for (int i = 0; i < input.size(); i++) {
            ItemStack stack = input.getStackInSlot(i);
            if (stack.isEmpty()) continue;
            if (isRawMeat(stack) && !hasMeat) { hasMeat = true; isCooked = false; }
            else if (isCookedMeat(stack) && !hasMeat) { hasMeat = true; isCooked = true; }
            else if (stack.isOf(Items.STRING) && !hasString) { hasString = true; }
            else { return 0; }
        }
        if (!hasMeat || !hasString) return 0;
        return isCooked ? 2 : 1;
    }

    @Override
    public boolean matches(CraftingRecipeInput input, World world) {
        return findType(input) != 0;
    }

    @Override
    public ItemStack craft(CraftingRecipeInput input, RegistryWrapper.WrapperLookup lookup) {
        int type = findType(input);
        if (type == 1) return new ItemStack(ModItems.UNBAKED_SAUSAGE);
        if (type == 2) return new ItemStack(ModItems.SAUSAGE);
        return ItemStack.EMPTY;
    }

    @Override
    public boolean isIgnoredInRecipeBook() { return false; }

    @Override
    public IngredientPlacement getIngredientPlacement() {
        return IngredientPlacement.forShapeless(List.of(
                Ingredient.ofItems(Items.PORKCHOP, Items.BEEF, Items.CHICKEN, Items.MUTTON, Items.RABBIT,
                        Items.COOKED_PORKCHOP, Items.COOKED_BEEF, Items.COOKED_CHICKEN, Items.COOKED_MUTTON, Items.COOKED_RABBIT),
                Ingredient.ofItems(Items.STRING)
        ));
    }

    @Override
    public List<RecipeDisplay> getDisplays() {
        SlotDisplay meatDisplay = new SlotDisplay.CompositeSlotDisplay(List.of(
                new SlotDisplay.ItemSlotDisplay(Items.PORKCHOP),
                new SlotDisplay.ItemSlotDisplay(Items.BEEF),
                new SlotDisplay.ItemSlotDisplay(Items.CHICKEN),
                new SlotDisplay.ItemSlotDisplay(Items.COOKED_PORKCHOP),
                new SlotDisplay.ItemSlotDisplay(Items.COOKED_BEEF),
                new SlotDisplay.ItemSlotDisplay(Items.COOKED_CHICKEN)
        ));
        return List.of(new ShapelessCraftingRecipeDisplay(
                List.of(meatDisplay, new SlotDisplay.ItemSlotDisplay(Items.STRING)),
                new SlotDisplay.ItemSlotDisplay(ModItems.SAUSAGE),
                new SlotDisplay.ItemSlotDisplay(Blocks.CRAFTING_TABLE.asItem())
        ));
    }

    @Override
    public RecipeSerializer<? extends SpecialCraftingRecipe> getSerializer() {
        return ModRecipes.SAUSAGE_SERIALIZER;
    }
}
