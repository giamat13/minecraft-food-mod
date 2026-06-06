package com.food.giamat.recipe;

import com.food.giamat.init.ModComponents;
import com.food.giamat.init.ModItems;
import com.mojang.serialization.MapCodec;
import net.minecraft.ChatFormatting;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;

public class SaltRecipe extends CustomRecipe {
    public static final SaltRecipe INSTANCE = new SaltRecipe();
    public static final MapCodec<SaltRecipe> MAP_CODEC = MapCodec.unit(INSTANCE);
    public static final StreamCodec<RegistryFriendlyByteBuf, SaltRecipe> STREAM_CODEC = StreamCodec.unit(INSTANCE);

    public SaltRecipe() {}

    private static boolean isSaltableFood(ItemStack stack) {
        return stack.has(DataComponents.FOOD)
                && !stack.getOrDefault(ModComponents.SALTED, false)
                && stack.getItem() != ModItems.BUTTERED_BREAD
                && stack.getItem() != ModItems.SALTED_BUTTERED_BREAD;
    }

    private ItemStack findFood(CraftingInput input) {
        ItemStack food = ItemStack.EMPTY;
        int saltCount = 0;
        for (int i = 0; i < input.size(); i++) {
            ItemStack stack = input.getItem(i);
            if (stack.isEmpty()) continue;
            if (stack.getItem() == ModItems.SALT) {
                saltCount++;
            } else if (isSaltableFood(stack) && food.isEmpty()) {
                food = stack;
            } else {
                return ItemStack.EMPTY;
            }
        }
        return (saltCount == 1 && !food.isEmpty()) ? food : ItemStack.EMPTY;
    }

    @Override
    public boolean matches(CraftingInput input, Level world) {
        return !findFood(input).isEmpty();
    }

    @Override
    public ItemStack assemble(CraftingInput input) {
        ItemStack food = findFood(input);
        if (food.isEmpty()) return ItemStack.EMPTY;

        ItemStack result = food.copyWithCount(1);
        result.set(ModComponents.SALTED, true);

        FoodProperties original = result.get(DataComponents.FOOD);
        if (original != null) {
            result.set(DataComponents.FOOD, new FoodProperties(
                    original.nutrition() + 1,
                    original.saturation(),
                    original.canAlwaysEat()));
        }

        Component base = food.getHoverName().copy().withStyle(s -> s.withItalic(false));
        result.set(DataComponents.CUSTOM_NAME,
                Component.empty()
                        .append(base)
                        .append(Component.literal(" with Salt").withStyle(s -> s.withItalic(false).withColor(ChatFormatting.WHITE))));

        return result;
    }

    @Override
    public RecipeSerializer<SaltRecipe> getSerializer() {
        return ModRecipes.SALT_SERIALIZER;
    }
}
