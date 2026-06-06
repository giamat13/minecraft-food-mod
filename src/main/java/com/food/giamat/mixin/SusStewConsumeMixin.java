package com.food.giamat.mixin;

import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.component.SuspiciousStewEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * After any food item finishes being used, apply any SUSPICIOUS_STEW_EFFECTS
 * component stored on it. Vanilla suspicious_stew is excluded because it
 * already applies its own effects natively.
 */
@Mixin(Item.class)
public abstract class SusStewConsumeMixin {

    @Inject(method = "finishUsingItem", at = @At("RETURN"))
    private void applyStewEffects(ItemStack stack, Level world, LivingEntity user,
            CallbackInfoReturnable<ItemStack> cir) {
        if (world.isClientSide() || stack.getItem() == Items.SUSPICIOUS_STEW) return;
        if (!(user instanceof Player player)) return;

        SuspiciousStewEffects effects =
                stack.get(DataComponents.SUSPICIOUS_STEW_EFFECTS);
        if (effects != null) {
            for (SuspiciousStewEffects.Entry e : effects.effects()) {
                player.addEffect(new MobEffectInstance(e.effect(), e.duration(), 0));
            }
        }
    }
}
