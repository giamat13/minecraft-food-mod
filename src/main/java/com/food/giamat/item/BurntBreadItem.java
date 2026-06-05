package com.food.giamat.item;

import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;

public class BurntBreadItem extends Item {

    public BurntBreadItem(Settings settings) {
        super(settings);
    }

    @Override
    public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
        ItemStack result = super.finishUsing(stack, world, user);
        if (!world.isClient() && world instanceof ServerWorld serverWorld) {
            user.damage(serverWorld, world.getDamageSources().generic(), 1.0f);
        }
        return result;
    }
}
