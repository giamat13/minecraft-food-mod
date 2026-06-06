package com.food.giamat.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;

public class PomegranateLeavesBlock extends LeavesBlock {
    public static final MapCodec<PomegranateLeavesBlock> CODEC = simpleCodec(PomegranateLeavesBlock::new);

    public PomegranateLeavesBlock(BlockBehaviour.Properties settings) {
        super(0.1f, settings);
    }

    @Override
    public MapCodec<PomegranateLeavesBlock> codec() {
        return CODEC;
    }

    @Override
    protected void spawnFallingLeavesParticle(Level world, BlockPos pos, RandomSource random) {
    }
}
