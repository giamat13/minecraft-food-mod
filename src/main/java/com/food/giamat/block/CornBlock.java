package com.food.giamat.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.VegetationBlock;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.tags.BlockTags;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.level.BlockGetter;

/**
 * Corn grows like sugar cane but tops out at two blocks tall. It grows on any
 * dirt-like block (or sand) and stacks on top of itself.
 */
public class CornBlock extends VegetationBlock {
    public static final MapCodec<CornBlock> CODEC = simpleCodec(CornBlock::new);
    public static final IntegerProperty AGE = BlockStateProperties.AGE_15;

    private static final int MAX_HEIGHT = 2;
    private static final VoxelShape SHAPE = Block.box(2.0, 0.0, 2.0, 14.0, 16.0, 14.0);

    public CornBlock(Properties settings) {
        super(settings);
        registerDefaultState(defaultBlockState().setValue(AGE, 0));
    }

    @Override
    protected MapCodec<? extends VegetationBlock> codec() {
        return CODEC;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(AGE);
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    @Override
    protected boolean mayPlaceOn(BlockState floor, BlockGetter world, BlockPos pos) {
        return floor.is(this)
                || floor.is(BlockTags.DIRT)
                || floor.is(Blocks.FARMLAND)
                || floor.is(BlockTags.SAND);
    }

    @Override
    protected void randomTick(BlockState state, ServerLevel world, BlockPos pos, RandomSource random) {
        // Only the topmost stalk grows, and only into open air above it.
        if (!world.getBlockState(pos.above()).isAir()) {
            return;
        }
        int height = 1;
        while (world.getBlockState(pos.below(height)).is(this)) {
            height++;
        }
        if (height >= MAX_HEIGHT) {
            return;
        }
        int age = state.getValue(AGE);
        if (age >= 15) {
            world.setBlock(pos.above(), defaultBlockState(), Block.UPDATE_ALL);
            world.setBlock(pos, state.setValue(AGE, 0), Block.UPDATE_CLIENTS);
        } else {
            world.setBlock(pos, state.setValue(AGE, age + 1), Block.UPDATE_CLIENTS);
        }
    }
}
