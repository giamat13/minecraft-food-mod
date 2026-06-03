package com.food.giamat.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.PlantBlock;
import net.minecraft.block.ShapeContext;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;

/**
 * Corn grows like sugar cane but tops out at two blocks tall. It grows on any
 * dirt-like block (or sand) and stacks on top of itself.
 */
public class CornBlock extends PlantBlock {
    public static final MapCodec<CornBlock> CODEC = createCodec(CornBlock::new);
    public static final IntProperty AGE = Properties.AGE_15;

    private static final int MAX_HEIGHT = 2;
    private static final VoxelShape SHAPE = Block.createCuboidShape(2.0, 0.0, 2.0, 14.0, 16.0, 14.0);

    public CornBlock(Settings settings) {
        super(settings);
        setDefaultState(getDefaultState().with(AGE, 0));
    }

    @Override
    protected MapCodec<? extends PlantBlock> getCodec() {
        return CODEC;
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(AGE);
    }

    @Override
    protected VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }

    @Override
    protected boolean canPlantOnTop(BlockState floor, BlockView world, BlockPos pos) {
        return floor.isOf(this)
                || floor.isIn(BlockTags.DIRT)
                || floor.isOf(Blocks.FARMLAND)
                || floor.isIn(BlockTags.SAND);
    }

    @Override
    protected void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        // Only the topmost stalk grows, and only into open air above it.
        if (!world.getBlockState(pos.up()).isAir()) {
            return;
        }
        int height = 1;
        while (world.getBlockState(pos.down(height)).isOf(this)) {
            height++;
        }
        if (height >= MAX_HEIGHT) {
            return;
        }
        int age = state.get(AGE);
        if (age >= 15) {
            world.setBlockState(pos.up(), getDefaultState());
            world.setBlockState(pos, state.with(AGE, 0), Block.NOTIFY_LISTENERS);
        } else {
            world.setBlockState(pos, state.with(AGE, age + 1), Block.NOTIFY_LISTENERS);
        }
    }
}
