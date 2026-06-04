package com.food.giamat.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.LeavesBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;

public class PomegranateLeavesBlock extends LeavesBlock {
    public static final MapCodec<PomegranateLeavesBlock> CODEC = createCodec(PomegranateLeavesBlock::new);

    public PomegranateLeavesBlock(AbstractBlock.Settings settings) {
        super(0.1f, settings);
    }

    @Override
    public MapCodec<PomegranateLeavesBlock> getCodec() {
        return CODEC;
    }

    @Override
    protected void spawnLeafParticle(World world, BlockPos pos, Random random) {
    }
}
