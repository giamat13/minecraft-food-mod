package com.food.giamat.item;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;

public class BurntBreadItem extends Item {

    public BurntBreadItem(Properties settings) {
        super(settings);
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level world, LivingEntity user) {
        ItemStack result = super.finishUsingItem(stack, world, user);
        if (!world.isClientSide() && world instanceof ServerLevel serverWorld) {
            user.hurtServer(serverWorld, world.damageSources().generic(), 1.0f);
        }
        return result;
    }
}
