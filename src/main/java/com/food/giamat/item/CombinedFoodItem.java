package com.food.giamat.item;

import com.food.giamat.init.ModComponents;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class CombinedFoodItem extends Item {

    public CombinedFoodItem(Settings settings) {
        super(settings);
    }

    @Override
    public int getMaxUseTime(ItemStack stack, LivingEntity user) {
        // 2x the default food eat time (32 ticks)
        return 64;
    }

    @Override
    public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
        ItemStack result = super.finishUsing(stack, world, user);

        if (!world.isClient() && user instanceof PlayerEntity player) {
            CombinedFoodData data = stack.get(ModComponents.COMBINED_FOOD_DATA);
            if (data == null) return result;

            for (StatusEffectInstance effect : data.effects()) {
                player.addStatusEffect(new StatusEffectInstance(
                        effect.getEffectType(), effect.getDuration(), effect.getAmplifier()));
            }

            if (data.foodCount() > 6) {
                int extra = data.foodCount() - 6;
                // 5s per food over the limit, nausea level rises with each
                player.addStatusEffect(new StatusEffectInstance(
                        StatusEffects.NAUSEA, extra * 100, extra - 1));
            }
        }

        return result;
    }
}
