package com.food.giamat.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.PlantBlock;
import net.minecraft.block.ShapeContext;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;

/**
 * A wild chili pepper bush that grows on sand (so it can spawn in deserts).
 * Breaking it drops chili peppers; eating one grants brief fire resistance.
 */
public class ChiliPepperBushBlock extends PlantBlock {
    public static final MapCodec<ChiliPepperBushBlock> CODEC = createCodec(ChiliPepperBushBlock::new);
    private static final VoxelShape SHAPE = Block.createCuboidShape(2.0, 0.0, 2.0, 14.0, 13.0, 14.0);

    public ChiliPepperBushBlock(Settings settings) {
        super(settings);
    }

    @Override
    protected MapCodec<? extends PlantBlock> getCodec() {
        return CODEC;
    }

    @Override
    protected boolean canPlantOnTop(BlockState floor, BlockView world, BlockPos pos) {
        // Allow the desert sand it spawns on, plus the usual dirt-like blocks.
        return floor.isIn(BlockTags.SAND) || super.canPlantOnTop(floor, world, pos);
    }

    @Override
    protected VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }
}
