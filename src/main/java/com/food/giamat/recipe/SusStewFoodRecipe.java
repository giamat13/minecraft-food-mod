package com.food.giamat.recipe;

import com.food.giamat.init.ModItems;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.SuspiciousStewEffects;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

public class SusStewFoodRecipe extends CustomRecipe {
    public static final SusStewFoodRecipe INSTANCE = new SusStewFoodRecipe();
    public static final MapCodec<SusStewFoodRecipe> MAP_CODEC = MapCodec.unit(INSTANCE);
    public static final StreamCodec<RegistryFriendlyByteBuf, SusStewFoodRecipe> STREAM_CODEC = StreamCodec.unit(INSTANCE);

    public SusStewFoodRecipe() {}

    private boolean isSusEligible(ItemStack stack) {
        return (stack.has(DataComponents.FOOD) || stack.getItem() == Items.CAKE)
                && stack.getItem() != Items.SUSPICIOUS_STEW;
    }

    private record Match(ItemStack food, ItemStack stew) {}

    @Nullable
    private Match findMatch(CraftingInput input) {
        ItemStack food = ItemStack.EMPTY;
        ItemStack stew = ItemStack.EMPTY;
        for (int i = 0; i < input.size(); i++) {
            ItemStack stack = input.getItem(i);
            if (stack.isEmpty()) continue;
            if (stack.getItem() == Items.SUSPICIOUS_STEW) {
                if (!stew.isEmpty()) return null;
                stew = stack;
            } else if (isSusEligible(stack)) {
                if (!food.isEmpty()) return null;
                food = stack;
            } else {
                return null;
            }
        }
        return (!food.isEmpty() && !stew.isEmpty()) ? new Match(food, stew) : null;
    }

    @Override
    public boolean matches(CraftingInput input, Level world) {
        return findMatch(input) != null;
    }

    @Override
    public ItemStack assemble(CraftingInput input) {
        Match match = findMatch(input);
        if (match == null) return ItemStack.EMPTY;

        SuspiciousStewEffects effects = match.stew().get(DataComponents.SUSPICIOUS_STEW_EFFECTS);

        if (match.food().getItem() == Items.CAKE) {
            ItemStack result = new ItemStack(ModItems.SUS_CAKE);
            if (effects != null) result.set(DataComponents.SUSPICIOUS_STEW_EFFECTS, effects);
            return result;
        }

        if (match.food().getItem() == ModItems.PIZZA) {
            ItemStack result = new ItemStack(ModItems.SUS_PIZZA);
            if (effects != null) result.set(DataComponents.SUSPICIOUS_STEW_EFFECTS, effects);
            return result;
        }

        ItemStack result = match.food().copyWithCount(1);
        if (effects != null) result.set(DataComponents.SUSPICIOUS_STEW_EFFECTS, effects);
        return result;
    }

    @Override
    public RecipeSerializer<SusStewFoodRecipe> getSerializer() {
        return ModRecipes.SUS_STEW_FOOD_SERIALIZER;
    }
}
