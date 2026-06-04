package com.food.giamat.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.LeavesBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;

public class LemonLeavesBlock extends LeavesBlock {
    public static final MapCodec<LemonLeavesBlock> CODEC = createCodec(LemonLeavesBlock::new);

    public LemonLeavesBlock(AbstractBlock.Settings settings) {
        super(0.1f, settings);
    }

    @Override
    public MapCodec<LemonLeavesBlock> getCodec() {
        return CODEC;
    }

    @Override
    protected void spawnLeafParticle(World world, BlockPos pos, Random random) {
    }
}
