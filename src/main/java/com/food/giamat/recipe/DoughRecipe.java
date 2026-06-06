package com.food.giamat.recipe;

import com.food.giamat.init.ModItems;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.NonNullList;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;

public class DoughRecipe extends CustomRecipe {
    public static final DoughRecipe INSTANCE = new DoughRecipe();
    public static final MapCodec<DoughRecipe> MAP_CODEC = MapCodec.unit(INSTANCE);
    public static final StreamCodec<RegistryFriendlyByteBuf, DoughRecipe> STREAM_CODEC = StreamCodec.unit(INSTANCE);

    public DoughRecipe() {}

    private static boolean isWaterSource(ItemStack stack) {
        if (stack.getItem() == Items.WATER_BUCKET) return true;
        if (stack.getItem() == Items.POTION) {
            PotionContents contents = stack.get(DataComponents.POTION_CONTENTS);
            return contents != null && contents.is(Potions.WATER);
        }
        return false;
    }

    @Override
    public boolean matches(CraftingInput input, Level world) {
        boolean hasFlour = false, hasWater = false;
        for (int i = 0; i < input.size(); i++) {
            ItemStack stack = input.getItem(i);
            if (stack.isEmpty()) continue;
            if (stack.getItem() == ModItems.FLOUR) {
                if (hasFlour) return false;
                hasFlour = true;
            } else if (isWaterSource(stack)) {
                if (hasWater) return false;
                hasWater = true;
            } else {
                return false;
            }
        }
        return hasFlour && hasWater;
    }

    @Override
    public ItemStack assemble(CraftingInput input) {
        return new ItemStack(ModItems.DOUGH);
    }

    @Override
    public NonNullList<ItemStack> getRemainingItems(CraftingInput input) {
        NonNullList<ItemStack> remainders = NonNullList.withSize(input.size(), ItemStack.EMPTY);
        for (int i = 0; i < input.size(); i++) {
            ItemStack stack = input.getItem(i);
            if (stack.getItem() == Items.WATER_BUCKET) {
                remainders.set(i, new ItemStack(Items.BUCKET));
            } else if (stack.getItem() == Items.POTION) {
                remainders.set(i, new ItemStack(Items.GLASS_BOTTLE));
            }
        }
        return remainders;
    }

    @Override
    public RecipeSerializer<DoughRecipe> getSerializer() {
        return ModRecipes.DOUGH_SERIALIZER;
    }
}
