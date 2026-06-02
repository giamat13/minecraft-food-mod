package com.food.giamat.item;

import net.minecraft.block.Block;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

/**
 * A cake that is also a normal food item.
 *
 * <p>Because it extends {@link BlockItem} but does not override {@code use}, it
 * inherits two behaviours at once:
 * <ul>
 *     <li>aiming at a block places the (vanilla) cake block, just like a normal
 *     cake item;</li>
 *     <li>aiming at the air eats the whole cake, restoring all of the hunger a
 *     full cake would (7 slices) and applying any effects below.</li>
 * </ul>
 *
 * <p>A "cursed" cake (baked from a cake made with a sniffer or dragon egg)
 * poisons and nauseates whoever eats it.
 */
public class EdibleCakeItem extends BlockItem {

    private final boolean cursed;

    public EdibleCakeItem(Block block, Settings settings, boolean cursed) {
        super(block, settings);
        this.cursed = cursed;
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
