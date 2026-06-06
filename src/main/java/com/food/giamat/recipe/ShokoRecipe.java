package com.food.giamat.recipe;

import com.food.giamat.init.ModItems;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.NonNullList;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;

public class ShokoRecipe extends CustomRecipe {
    public static final ShokoRecipe INSTANCE = new ShokoRecipe();
    public static final MapCodec<ShokoRecipe> MAP_CODEC = MapCodec.unit(INSTANCE);
    public static final StreamCodec<RegistryFriendlyByteBuf, ShokoRecipe> STREAM_CODEC = StreamCodec.unit(INSTANCE);

    public ShokoRecipe() {}

    @Override
    public boolean matches(CraftingInput input, Level world) {
        boolean hasMelted = false, hasMilk = false;
        for (int i = 0; i < input.size(); i++) {
            ItemStack stack = input.getItem(i);
            if (stack.isEmpty()) continue;
            if (stack.getItem() == ModItems.MELTED_CHOCOLATE && !hasMelted) { hasMelted = true; }
            else if (stack.getItem() == Items.MILK_BUCKET && !hasMilk) { hasMilk = true; }
            else { return false; }
        }
        return hasMelted && hasMilk;
    }

    @Override
    public ItemStack assemble(CraftingInput input) {
        return new ItemStack(ModItems.SHOKO);
    }

    @Override
    public NonNullList<ItemStack> getRemainingItems(CraftingInput input) {
        NonNullList<ItemStack> remainders = NonNullList.withSize(input.size(), ItemStack.EMPTY);
        for (int i = 0; i < input.size(); i++) {
            if (input.getItem(i).getItem() == Items.MILK_BUCKET) {
                remainders.set(i, new ItemStack(Items.BUCKET));
            }
        }
        return remainders;
    }

    @Override
    public RecipeSerializer<ShokoRecipe> getSerializer() {
        return ModRecipes.SHOKO_SERIALIZER;
    }
}
