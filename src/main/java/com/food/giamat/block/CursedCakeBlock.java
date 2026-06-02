package com.food.giamat.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.block.BlockState;
import net.minecraft.block.CakeBlock;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/**
 * A cake block that poisons and nauseates whoever eats a slice from it.
 *
 * <p>This is the block placed by the cursed cake item, so the curse applies
 * whether the cake is eaten whole (in hand) or sliced after being placed.
 */
public class CursedCakeBlock extends CakeBlock {
    public static final MapCodec<CursedCakeBlock> CODEC = createCodec(CursedCakeBlock::new);

    public CursedCakeBlock(Settings settings) {
        super(settings);
    }

    @Override
    @SuppressWarnings({"unchecked", "rawtypes"})
    public MapCodec<CakeBlock> getCodec() {
        // CakeBlock pins the return type to MapCodec<CakeBlock>; our codec still
        // produces CursedCakeBlock instances at runtime, so the raw cast is safe.
        return (MapCodec) CODEC;
    }

    @Override
    protected ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
        ActionResult result = super.onUse(state, world, pos, player, hit);
        // A slice was actually eaten only when the use was accepted.
        if (result.isAccepted() && !world.isClient()) {
            player.addStatusEffect(new StatusEffectInstance(StatusEffects.NAUSEA, 200, 0));
            player.addStatusEffect(new StatusEffectInstance(StatusEffects.POISON, 200, 0));
        }
        return result;
    }
}
