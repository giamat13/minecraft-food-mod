package com.food.giamat.recipe;

import com.food.giamat.init.ModComponents;
import com.food.giamat.init.ModItems;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;

import java.util.Set;

public class PizzaToppingRecipe extends CustomRecipe {
    public static final PizzaToppingRecipe INSTANCE = new PizzaToppingRecipe();
    public static final MapCodec<PizzaToppingRecipe> MAP_CODEC = MapCodec.unit(INSTANCE);
    public static final StreamCodec<RegistryFriendlyByteBuf, PizzaToppingRecipe> STREAM_CODEC = StreamCodec.unit(INSTANCE);

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

    public PizzaToppingRecipe() {}

    private static boolean isBase(ItemStack stack) {
        return stack.getItem() == ModItems.PIZZA || stack.getItem() == ModItems.TOPPED_PIZZA;
    }

    private static boolean isTopping(ItemStack stack) {
        return TOPPINGS.contains(stack.getItem());
    }

    private static ItemStack buildStack(CraftingInput input) {
        ItemStack base = ItemStack.EMPTY;
        int existingToppings = 0;
        int added = 0;
        for (int i = 0; i < input.size(); i++) {
            ItemStack stack = input.getItem(i);
            if (stack.isEmpty()) continue;
            if (isBase(stack)) {
                if (!base.isEmpty()) return ItemStack.EMPTY;
                base = stack;
                existingToppings = stack.getItem() == ModItems.TOPPED_PIZZA
                        ? stack.getOrDefault(ModComponents.PIZZA_TOPPINGS, 1)
                        : 0;
            } else if (isTopping(stack)) {
                added++;
            } else {
                return ItemStack.EMPTY;
            }
        }
        if (base.isEmpty() || added == 0) return ItemStack.EMPTY;
        int total = Math.min(MAX_TOPPINGS, existingToppings + added);
        ItemStack result = new ItemStack(ModItems.TOPPED_PIZZA);
        result.set(ModComponents.PIZZA_TOPPINGS, total);
        result.set(DataComponents.FOOD, new FoodProperties(8 + 2 * total, Math.min(1.0f, 0.8f + 0.1f * total), false));
        return result;
    }

    @Override
    public boolean matches(CraftingInput input, Level world) {
        return !buildStack(input).isEmpty();
    }

    @Override
    public ItemStack assemble(CraftingInput input) {
        return buildStack(input);
    }

    @Override
    public RecipeSerializer<PizzaToppingRecipe> getSerializer() {
        return ModRecipes.PIZZA_TOPPING_SERIALIZER;
    }
}
