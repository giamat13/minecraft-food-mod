package com.food.giamat.item;

import net.minecraft.block.Block;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.util.ActionResult;
import net.minecraft.world.World;

/**
 * A cake that is also a normal food item.
 *
 * <p>A normal right-click eats the whole cake (restoring all the hunger a full
 * cake would — 7 slices — and applying any effects below). Sneaking while
 * right-clicking a block places the cake block instead, so it can also be eaten
 * slice by slice.
 *
 * <p>A "cursed" cake (baked from a cake made with a sniffer or dragon egg)
 * poisons and nauseates whoever eats it, whether eaten whole or as slices from
 * the placed {@link com.food.giamat.block.CursedCakeBlock}.
 */
public class EdibleCakeItem extends BlockItem {

    private final boolean cursed;

    public EdibleCakeItem(Block block, Settings settings, boolean cursed) {
        super(block, settings);
        this.cursed = cursed;
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        PlayerEntity player = context.getPlayer();
        // Sneak to place the cake as a block; otherwise fall through to use() so a
        // plain right-click eats it in hand even when aiming at a block.
        if (player != null && player.isSneaking()) {
            return super.useOnBlock(context);
        }
        return ActionResult.PASS;
    }

    @Override
    public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
        ItemStack result = super.finishUsing(stack, world, user);
        if (this.cursed && !world.isClient()) {
            user.addStatusEffect(new StatusEffectInstance(StatusEffects.NAUSEA, 200, 0));
            user.addStatusEffect(new StatusEffectInstance(StatusEffects.POISON, 200, 0));
        }
        return result;
    }
}
