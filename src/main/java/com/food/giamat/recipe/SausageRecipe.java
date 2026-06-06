package com.food.giamat.recipe;

import com.food.giamat.init.ModItems;
import com.mojang.serialization.MapCodec;
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

public class SausageRecipe extends CustomRecipe {
    public static final SausageRecipe INSTANCE = new SausageRecipe();
    public static final MapCodec<SausageRecipe> MAP_CODEC = MapCodec.unit(INSTANCE);
    public static final StreamCodec<RegistryFriendlyByteBuf, SausageRecipe> STREAM_CODEC = StreamCodec.unit(INSTANCE);

    private static final Set<Item> RAW_MEATS = Set.of(
            Items.PORKCHOP, Items.BEEF, Items.CHICKEN, Items.MUTTON, Items.RABBIT
    );
    private static final Set<Item> COOKED_MEATS = Set.of(
            Items.COOKED_PORKCHOP, Items.COOKED_BEEF, Items.COOKED_CHICKEN, Items.COOKED_MUTTON, Items.COOKED_RABBIT
    );

    public SausageRecipe() {}

    private static boolean isRawMeat(ItemStack stack) { return RAW_MEATS.contains(stack.getItem()); }
    private static boolean isCookedMeat(ItemStack stack) { return COOKED_MEATS.contains(stack.getItem()); }

    private int findType(CraftingInput input) {
        boolean hasMeat = false, hasString = false, isCooked = false;
        for (int i = 0; i < input.size(); i++) {
            ItemStack stack = input.getItem(i);
            if (stack.isEmpty()) continue;
            if (isRawMeat(stack) && !hasMeat) { hasMeat = true; isCooked = false; }
            else if (isCookedMeat(stack) && !hasMeat) { hasMeat = true; isCooked = true; }
            else if (stack.getItem() == Items.STRING && !hasString) { hasString = true; }
            else { return 0; }
        }
        if (!hasMeat || !hasString) return 0;
        return isCooked ? 2 : 1;
    }

    @Override
    public boolean matches(CraftingInput input, Level world) { return findType(input) != 0; }

    @Override
    public ItemStack assemble(CraftingInput input) {
        int type = findType(input);
        if (type == 1) return new ItemStack(ModItems.UNBAKED_SAUSAGE);
        if (type == 2) return new ItemStack(ModItems.SAUSAGE);
        return ItemStack.EMPTY;
    }

    @Override
    public RecipeSerializer<SausageRecipe> getSerializer() {
        return ModRecipes.SAUSAGE_SERIALIZER;
    }
}
