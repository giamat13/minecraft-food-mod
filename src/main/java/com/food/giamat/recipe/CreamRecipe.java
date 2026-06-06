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

public class CreamRecipe extends CustomRecipe {
    public static final CreamRecipe INSTANCE = new CreamRecipe();
    public static final MapCodec<CreamRecipe> MAP_CODEC = MapCodec.unit(INSTANCE);
    public static final StreamCodec<RegistryFriendlyByteBuf, CreamRecipe> STREAM_CODEC = StreamCodec.unit(INSTANCE);

    public CreamRecipe() {}

    @Override
    public boolean matches(CraftingInput input, Level world) {
        boolean hasOliveOil = false, hasMilk = false;
        for (int i = 0; i < input.size(); i++) {
            ItemStack stack = input.getItem(i);
            if (stack.isEmpty()) continue;
            if (stack.getItem() == ModItems.OLIVE_OIL && !hasOliveOil) { hasOliveOil = true; }
            else if (stack.getItem() == Items.MILK_BUCKET && !hasMilk) { hasMilk = true; }
            else { return false; }
        }
        return hasOliveOil && hasMilk;
    }

    @Override
    public ItemStack assemble(CraftingInput input) {
        return new ItemStack(ModItems.CREAM);
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
    public RecipeSerializer<CreamRecipe> getSerializer() {
        return ModRecipes.CREAM_SERIALIZER;
    }
}
