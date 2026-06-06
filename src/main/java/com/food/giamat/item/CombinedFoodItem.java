package com.food.giamat.item;

import com.food.giamat.init.ModComponents;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class CombinedFoodItem extends Item {

    public CombinedFoodItem(Properties settings) {
        super(settings);
    }

    @Override
    public int getUseDuration(ItemStack stack, LivingEntity user) {
        return 64;
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level world, LivingEntity user) {
        ItemStack result = super.finishUsingItem(stack, world, user);

        if (!world.isClientSide() && user instanceof Player player) {
            CombinedFoodData data = stack.get(ModComponents.COMBINED_FOOD_DATA);
            if (data == null) return result;

            for (MobEffectInstance effect : data.effects()) {
                player.addEffect(new MobEffectInstance(
                        effect.getEffect(), effect.getDuration(), effect.getAmplifier()));
            }

            if (data.foodCount() > 6) {
                int extra = data.foodCount() - 6;
                // 5s per food over the limit, nausea level rises with each
                player.addEffect(new MobEffectInstance(
                        MobEffects.NAUSEA, extra * 100, extra - 1));
            }
        }

        return result;
    }
}
