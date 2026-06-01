package com.food.giamat.client.mixin;

import com.food.giamat.init.ModComponents;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Draws a few small white "salt" dots on top of any food item icon that has been salted.
 * All GUI item rendering (inventory, hotbar, containers) funnels through this method,
 * and the flat 2D GUI layers by draw order, so drawing at TAIL puts the dots on top.
 */
@Mixin(DrawContext.class)
public class SaltedItemOverlayMixin {

    @Inject(
            method = "drawItem(Lnet/minecraft/entity/LivingEntity;Lnet/minecraft/world/World;Lnet/minecraft/item/ItemStack;III)V",
            at = @At("TAIL"))
    private void foodbygiamat$drawSaltDots(LivingEntity entity, World world, ItemStack stack,
                                           int x, int y, int seed, CallbackInfo ci) {
        if (stack.isEmpty() || !stack.getOrDefault(ModComponents.SALTED, false)) {
            return;
        }
        DrawContext context = (DrawContext) (Object) this;
        int white = 0xFFFFFFFF;
        // scattered 2x2 grains across the 16x16 icon
        context.fill(x + 3, y + 3, x + 5, y + 5, white);
        context.fill(x + 10, y + 4, x + 12, y + 6, white);
        context.fill(x + 6, y + 9, x + 8, y + 11, white);
        context.fill(x + 11, y + 11, x + 13, y + 13, white);
    }
}
