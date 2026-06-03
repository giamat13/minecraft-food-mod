package com.food.giamat.recipe;

import com.food.giamat.init.ModComponents;
import com.food.giamat.init.ModItems;
import java.util.List;
import java.util.Set;
import net.minecraft.block.Blocks;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.FoodComponent;
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
import net.minecraft.world.World;

/**
 * Adds toppings to a pizza. Each topping makes the pizza more filling, up to a
 * cap. The result is a "topped pizza" whose food value is stored per-stack so
 * the same item can represent anywhere from one to several toppings.
 */
public class PizzaToppingRecipe extends SpecialCraftingRecipe {

    private static final int MAX_TOPPINGS = 4;

    private static final Set<Item> TOPPINGS = Set.of(
            ModItems.CHEESE,
            ModItems.TOMATO,
            ModItems.CHILI_PEPPER,
            ModItems.SAUSAGE,
            ModItems.CORN_HOT,
            Items.COOKED_BEEF,
            Items.BROWN_MUSHROOM,
            Items.RED_MUSHROOM
    );

    public PizzaToppingRecipe(CraftingRecipeCategory category) {
        super(category);
    }

    private static boolean isBase(ItemStack stack) {
        return stack.isOf(ModItems.PIZZA) || stack.isOf(ModItems.TOPPED_PIZZA);
    }

    private static boolean isTopping(ItemStack stack) {
        return TOPPINGS.contains(stack.getItem());
    }

    private static ItemStack assemble(CraftingRecipeInput input) {
        ItemStack base = ItemStack.EMPTY;
        int existingToppings = 0;
        int added = 0;
        for (int i = 0; i < input.size(); i++) {
            ItemStack stack = input.getStackInSlot(i);
            if (stack.isEmpty()) {
                continue;
            }
            if (isBase(stack)) {
                if (!base.isEmpty()) {
                    return ItemStack.EMPTY; // only one pizza at a time
                }
                base = stack;
                existingToppings = stack.isOf(ModItems.TOPPED_PIZZA)
                        ? stack.getOrDefault(ModComponents.PIZZA_TOPPINGS, 1)
                        : 0;
            } else if (isTopping(stack)) {
                added++;
            } else {
                return ItemStack.EMPTY;
            }
        }
        if (base.isEmpty() || added == 0) {
            return ItemStack.EMPTY;
        }
        int total = Math.min(MAX_TOPPINGS, existingToppings + added);
        ItemStack result = new ItemStack(ModItems.TOPPED_PIZZA);
        result.set(ModComponents.PIZZA_TOPPINGS, total);
        result.set(DataComponentTypes.FOOD, new FoodComponent(8 + 2 * total, Math.min(1.0f, 0.8f + 0.1f * total), false));
        return result;
    }

    @Override
    public boolean matches(CraftingRecipeInput input, World world) {
        return !assemble(input).isEmpty();
    }

    @Override
    public ItemStack craft(CraftingRecipeInput input, RegistryWrapper.WrapperLookup lookup) {
        return assemble(input);
    }

    @Override
    public boolean isIgnoredInRecipeBook() {
        return true;
    }

    @Override
    public IngredientPlacement getIngredientPlacement() {
        return IngredientPlacement.forShapeless(List.of(
                Ingredient.ofItems(ModItems.PIZZA, ModItems.TOPPED_PIZZA),
                Ingredient.ofItems(TOPPINGS.toArray(new Item[0]))
        ));
    }

    @Override
    public List<RecipeDisplay> getDisplays() {
        return List.of(new ShapelessCraftingRecipeDisplay(
                List.of(
                        new SlotDisplay.ItemSlotDisplay(ModItems.PIZZA),
                        new SlotDisplay.ItemSlotDisplay(ModItems.CHEESE)
                ),
                new SlotDisplay.ItemSlotDisplay(ModItems.TOPPED_PIZZA),
                new SlotDisplay.ItemSlotDisplay(Blocks.CRAFTING_TABLE.asItem())
        ));
    }

    @Override
    public RecipeSerializer<? extends SpecialCraftingRecipe> getSerializer() {
        return ModRecipes.PIZZA_TOPPING_SERIALIZER;
    }
}
