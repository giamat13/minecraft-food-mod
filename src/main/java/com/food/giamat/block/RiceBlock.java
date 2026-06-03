package com.food.giamat.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.PlantBlock;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.Waterloggable;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.WorldView;

/**
 * Rice grows in rice paddies: it can only be placed on dirt that is submerged in
 * water. It is always waterlogged so the dirt stays "covered by water".
 */
public class RiceBlock extends PlantBlock implements Waterloggable {
    public static final MapCodec<RiceBlock> CODEC = createCodec(RiceBlock::new);
    public static final IntProperty AGE = Properties.AGE_3;
    public static final int MAX_AGE = 3;

    private static final BooleanProperty WATERLOGGED = Properties.WATERLOGGED;
    private static final VoxelShape SHAPE = Block.createCuboidShape(2.0, 0.0, 2.0, 14.0, 14.0, 14.0);

    public RiceBlock(Settings settings) {
        super(settings);
        setDefaultState(getDefaultState().with(AGE, 0).with(WATERLOGGED, true));
    }

    @Override
    protected MapCodec<? extends PlantBlock> getCodec() {
        return CODEC;
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(AGE, WATERLOGGED);
    }

    @Override
    protected VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }

    public boolean isMature(BlockState state) {
        return state.get(AGE) >= MAX_AGE;
    }

    @Override
    protected boolean canPlantOnTop(BlockState floor, BlockView world, BlockPos pos) {
        return floor.isIn(BlockTags.DIRT);
    }

    @Override
    protected boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        return super.canPlaceAt(state, world, pos) && world.getFluidState(pos).isIn(FluidTags.WATER);
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        BlockState state = super.getPlacementState(ctx);
        if (state == null) {
            return null;
        }
        // Only placeable inside water (rice paddy); reject dry spots.
        if (!ctx.getWorld().getFluidState(ctx.getBlockPos()).isIn(FluidTags.WATER)) {
            return null;
        }
        return state.with(WATERLOGGED, true);
    }

    @Override
    protected FluidState getFluidState(BlockState state) {
        return state.get(WATERLOGGED) ? Fluids.WATER.getStill(false) : super.getFluidState(state);
    }

    @Override
    protected void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        int age = state.get(AGE);
        if (age < MAX_AGE && random.nextInt(5) == 0) {
            world.setBlockState(pos, state.with(AGE, age + 1), Block.NOTIFY_LISTENERS);
        }
    }
}
