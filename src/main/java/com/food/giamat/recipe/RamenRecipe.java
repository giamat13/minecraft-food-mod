package com.food.giamat.recipe;

import com.food.giamat.init.ModItems;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.NonNullList;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;

import java.util.Set;

public class RamenRecipe extends CustomRecipe {
    public static final RamenRecipe INSTANCE = new RamenRecipe();
    public static final MapCodec<RamenRecipe> MAP_CODEC = MapCodec.unit(INSTANCE);
    public static final StreamCodec<RegistryFriendlyByteBuf, RamenRecipe> STREAM_CODEC = StreamCodec.unit(INSTANCE);

    private static final Set<Item> MEATS = Set.of(
            Items.BEEF, Items.COOKED_BEEF,
            Items.PORKCHOP, Items.COOKED_PORKCHOP,
            Items.CHICKEN, Items.COOKED_CHICKEN,
            Items.MUTTON, Items.COOKED_MUTTON,
            Items.RABBIT, Items.COOKED_RABBIT,
            Items.COD, Items.COOKED_COD,
            Items.SALMON, Items.COOKED_SALMON
    );

    private static final Set<Item> SOUPS = Set.of(
            Items.MUSHROOM_STEW,
            Items.RABBIT_STEW,
            Items.SUSPICIOUS_STEW,
            Items.BEETROOT_SOUP
    );

    public RamenRecipe() {}

    private boolean findIngredients(CraftingInput input) {
        boolean hasPasta = false, hasMeat = false, hasSoup = false;
        for (int i = 0; i < input.size(); i++) {
            ItemStack stack = input.getItem(i);
            if (stack.isEmpty()) continue;
            if (stack.getItem() == ModItems.PASTA && !hasPasta) hasPasta = true;
            else if (MEATS.contains(stack.getItem()) && !hasMeat) hasMeat = true;
            else if (SOUPS.contains(stack.getItem()) && !hasSoup) hasSoup = true;
            else return false;
        }
        return hasPasta && hasMeat && hasSoup;
    }

    @Override
    public boolean matches(CraftingInput input, Level world) {
        return findIngredients(input);
    }

    @Override
    public ItemStack assemble(CraftingInput input) {
        if (findIngredients(input)) return new ItemStack(ModItems.RAMEN);
        return ItemStack.EMPTY;
    }

    @Override
    public NonNullList<ItemStack> getRemainingItems(CraftingInput input) {
        NonNullList<ItemStack> remaining = NonNullList.withSize(input.size(), ItemStack.EMPTY);
        for (int i = 0; i < input.size(); i++) {
            if (SOUPS.contains(input.getItem(i).getItem())) {
                remaining.set(i, new ItemStack(Items.BOWL));
                break;
            }
        }
        return remaining;
    }

    @Override
    public RecipeSerializer<RamenRecipe> getSerializer() {
        return ModRecipes.RAMEN_SERIALIZER;
    }
}
