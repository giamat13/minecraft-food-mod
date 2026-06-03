package com.food.giamat.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.CakeBlock;
import net.minecraft.block.ShapeContext;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;

/**
 * A pizza placed on the floor. Behaves like a cake (eaten slice by slice) but is
 * drawn as a flat round pizza, so it gets a flat outline shape instead of cake's.
 */
public class PizzaBlock extends CakeBlock {
    public static final MapCodec<PizzaBlock> CODEC = createCodec(PizzaBlock::new);

    private static final VoxelShape SHAPE = Block.createCuboidShape(1, 0, 1, 15, 2, 15);

    public PizzaBlock(Settings settings) {
        super(settings);
    }

    @Override
    @SuppressWarnings({"unchecked", "rawtypes"})
    public MapCodec<CakeBlock> getCodec() {
        return (MapCodec) CODEC;
    }

    @Override
    protected VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }
}
