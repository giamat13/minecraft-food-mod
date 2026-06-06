package com.food.giamat.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.CakeBlock;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;

/**
 * A cake block that poisons and nauseates whoever eats a slice from it.
 *
 * <p>This is the block placed by the cursed cake item, so the curse applies
 * whether the cake is eaten whole (in hand) or sliced after being placed.
 */
public class CursedCakeBlock extends CakeBlock {
    public static final MapCodec<CursedCakeBlock> CODEC = simpleCodec(CursedCakeBlock::new);

    public CursedCakeBlock(Properties settings) {
        super(settings);
    }

    @Override
    @SuppressWarnings({"unchecked", "rawtypes"})
    public MapCodec<CakeBlock> codec() {
        // CakeBlock pins the return type to MapCodec<CakeBlock>; our codec still
        // produces CursedCakeBlock instances at runtime, so the raw cast is safe.
        return (MapCodec) CODEC;
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level world, BlockPos pos, Player player, BlockHitResult hit) {
        InteractionResult result = super.useWithoutItem(state, world, pos, player, hit);
        // A slice was actually eaten only when the use was accepted.
        if (result.consumesAction() && !world.isClientSide()) {
            player.addEffect(new MobEffectInstance(MobEffects.NAUSEA, 200, 0));
            player.addEffect(new MobEffectInstance(MobEffects.POISON, 200, 0));
        }
        return result;
    }
}
