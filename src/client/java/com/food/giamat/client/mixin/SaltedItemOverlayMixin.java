package com.food.giamat.client.mixin;

import com.food.giamat.init.ModComponents;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GuiGraphicsExtractor.class)
public class SaltedItemOverlayMixin {

    @Inject(
            method = "item(Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/world/item/ItemStack;III)V",
            at = @At("TAIL"))
    private void foodbygiamat$drawSaltDots(LivingEntity entity, ItemStack stack,
                                           int x, int y, int seed, CallbackInfo ci) {
        if (stack.isEmpty() || !stack.getOrDefault(ModComponents.SALTED, false)) {
            return;
        }
        GuiGraphicsExtractor context = (GuiGraphicsExtractor) (Object) this;
        int white = 0xFFFFFFFF;
        context.fill(RenderPipelines.GUI, x + 3, y + 3, x + 5, y + 5, white);
        context.fill(RenderPipelines.GUI, x + 10, y + 4, x + 12, y + 6, white);
        context.fill(RenderPipelines.GUI, x + 6, y + 9, x + 8, y + 11, white);
        context.fill(RenderPipelines.GUI, x + 11, y + 11, x + 13, y + 13, white);
    }
}
