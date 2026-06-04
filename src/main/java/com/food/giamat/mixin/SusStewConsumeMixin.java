package com.food.giamat.mixin;

import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.SuspiciousStewEffectsComponent;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.world.World;
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

    @Inject(method = "finishUsing", at = @At("RETURN"))
    private void applyStewEffects(ItemStack stack, World world, LivingEntity user,
            CallbackInfoReturnable<ItemStack> cir) {
        if (world.isClient() || stack.isOf(Items.SUSPICIOUS_STEW)) return;
        if (!(user instanceof PlayerEntity player)) return;

        SuspiciousStewEffectsComponent effects =
                stack.get(DataComponentTypes.SUSPICIOUS_STEW_EFFECTS);
        if (effects != null) {
            for (SuspiciousStewEffectsComponent.StewEffect e : effects.effects()) {
                player.addStatusEffect(new StatusEffectInstance(e.effect(), e.duration(), 0));
            }
        }
    }
}
