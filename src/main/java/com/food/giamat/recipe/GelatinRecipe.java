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

public class GelatinRecipe extends CustomRecipe {
    public static final GelatinRecipe INSTANCE = new GelatinRecipe();
    public static final MapCodec<GelatinRecipe> MAP_CODEC = MapCodec.unit(INSTANCE);
    public static final StreamCodec<RegistryFriendlyByteBuf, GelatinRecipe> STREAM_CODEC = StreamCodec.unit(INSTANCE);

    public GelatinRecipe() {}

    private static boolean isWaterBottle(ItemStack stack) {
        if (stack.getItem() != Items.POTION) return false;
        PotionContents contents = stack.get(DataComponents.POTION_CONTENTS);
        return contents != null && contents.is(Potions.WATER);
    }

    @Override
    public boolean matches(CraftingInput input, Level world) {
        boolean hasBoneMeal = false, hasSlimeball = false, hasWaterBottle = false, hasGlowBerries = false;
        for (int i = 0; i < input.size(); i++) {
            ItemStack stack = input.getItem(i);
            if (stack.isEmpty()) continue;
            if (stack.getItem() == Items.BONE_MEAL && !hasBoneMeal) { hasBoneMeal = true; }
            else if (stack.getItem() == Items.SLIME_BALL && !hasSlimeball) { hasSlimeball = true; }
            else if (isWaterBottle(stack) && !hasWaterBottle) { hasWaterBottle = true; }
            else if (stack.getItem() == Items.GLOW_BERRIES && !hasGlowBerries) { hasGlowBerries = true; }
            else { return false; }
        }
        return hasBoneMeal && hasSlimeball && hasWaterBottle && hasGlowBerries;
    }

    @Override
    public ItemStack assemble(CraftingInput input) {
        return new ItemStack(ModItems.GELATIN);
    }

    @Override
    public NonNullList<ItemStack> getRemainingItems(CraftingInput input) {
        NonNullList<ItemStack> remainders = NonNullList.withSize(input.size(), ItemStack.EMPTY);
        for (int i = 0; i < input.size(); i++) {
            if (isWaterBottle(input.getItem(i))) {
                remainders.set(i, new ItemStack(Items.GLASS_BOTTLE));
            }
        }
        return remainders;
    }

    @Override
    public RecipeSerializer<GelatinRecipe> getSerializer() {
        return ModRecipes.GELATIN_SERIALIZER;
    }
}
