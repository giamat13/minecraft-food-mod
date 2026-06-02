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

public class GelatinRecipe extends SpecialCraftingRecipe {

    public GelatinRecipe(CraftingRecipeCategory category) {
        super(category);
    }

    private static boolean isWaterBottle(ItemStack stack) {
        if (!stack.isOf(Items.POTION)) return false;
        PotionContentsComponent contents = stack.get(DataComponentTypes.POTION_CONTENTS);
        return contents != null && contents.matches(Potions.WATER);
    }

    @Override
    public boolean matches(CraftingRecipeInput input, World world) {
        boolean hasBoneMeal = false, hasSlimeball = false, hasWaterBottle = false, hasGlowBerries = false;
        for (int i = 0; i < input.size(); i++) {
            ItemStack stack = input.getStackInSlot(i);
            if (stack.isEmpty()) continue;
            if (stack.isOf(Items.BONE_MEAL) && !hasBoneMeal) { hasBoneMeal = true; }
            else if (stack.isOf(Items.SLIME_BALL) && !hasSlimeball) { hasSlimeball = true; }
            else if (isWaterBottle(stack) && !hasWaterBottle) { hasWaterBottle = true; }
            else if (stack.isOf(Items.GLOW_BERRIES) && !hasGlowBerries) { hasGlowBerries = true; }
            else { return false; }
        }
        return hasBoneMeal && hasSlimeball && hasWaterBottle && hasGlowBerries;
    }

    @Override
    public ItemStack craft(CraftingRecipeInput input, RegistryWrapper.WrapperLookup lookup) {
        return new ItemStack(ModItems.GELATIN);
    }

    @Override
    public DefaultedList<ItemStack> getRecipeRemainders(CraftingRecipeInput input) {
        DefaultedList<ItemStack> remainders = DefaultedList.ofSize(input.size(), ItemStack.EMPTY);
        for (int i = 0; i < input.size(); i++) {
            if (isWaterBottle(input.getStackInSlot(i))) {
                remainders.set(i, new ItemStack(Items.GLASS_BOTTLE));
            }
        }
        return remainders;
    }

    @Override
    public boolean isIgnoredInRecipeBook() { return false; }

    @Override
    public IngredientPlacement getIngredientPlacement() {
        ItemStack waterBottle = PotionContentsComponent.createStack(Items.POTION, Potions.WATER);
        return IngredientPlacement.forShapeless(List.of(
                Ingredient.ofItems(Items.BONE_MEAL),
                Ingredient.ofItems(Items.SLIME_BALL),
                Ingredient.ofStacks(waterBottle),
                Ingredient.ofItems(Items.GLOW_BERRIES)
        ));
    }

    @Override
    public List<RecipeDisplay> getDisplays() {
        ItemStack waterBottle = PotionContentsComponent.createStack(Items.POTION, Potions.WATER);
        SlotDisplay waterDisplay = new SlotDisplay.WithRemainderSlotDisplay(
                new SlotDisplay.StackSlotDisplay(waterBottle),
                new SlotDisplay.ItemSlotDisplay(Items.GLASS_BOTTLE)
        );
        return List.of(new ShapelessCraftingRecipeDisplay(
                List.of(
                        new SlotDisplay.ItemSlotDisplay(Items.BONE_MEAL),
                        new SlotDisplay.ItemSlotDisplay(Items.SLIME_BALL),
                        waterDisplay,
                        new SlotDisplay.ItemSlotDisplay(Items.GLOW_BERRIES)
                ),
                new SlotDisplay.ItemSlotDisplay(ModItems.GELATIN),
                new SlotDisplay.ItemSlotDisplay(Blocks.CRAFTING_TABLE.asItem())
        ));
    }

    @Override
    public RecipeSerializer<? extends SpecialCraftingRecipe> getSerializer() {
        return ModRecipes.GELATIN_SERIALIZER;
    }
}
