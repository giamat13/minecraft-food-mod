package com.food.giamat.item;

import com.food.giamat.init.ModComponents;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
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
        // Read the data before super consumes (and may empty) the stack.
        CombinedFoodData data = stack.get(ModComponents.COMBINED_FOOD_DATA);
        ItemStack result = super.finishUsingItem(stack, world, user);

        if (!world.isClientSide() && user instanceof Player player && data != null) {
            // Restore the combined hunger and saturation of every ingredient.
            // The item itself carries no FOOD component amounts, so this is the
            // single place the meal's nourishment is applied.
            player.getFoodData().eat(new FoodProperties(data.nutrition(), data.saturation(), false));

            for (MobEffectInstance effect : data.effects()) {
                player.addEffect(new MobEffectInstance(effect));
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
