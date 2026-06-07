package com.food.giamat.item;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.SmallFireball;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class SpicyRamenItem extends Item {

    public SpicyRamenItem(Properties properties) {
        super(properties);
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level world, LivingEntity user) {
        ItemStack result = super.finishUsingItem(stack, world, user);
        if (!world.isClientSide() && user instanceof Player player) {
            Vec3 look = player.getLookAngle();
            SmallFireball fireball = new SmallFireball(world, player, look);
            fireball.setPos(
                    player.getX() + look.x,
                    player.getEyeY(),
                    player.getZ() + look.z
            );
            world.addFreshEntity(fireball);
        }
        return result;
    }
}
