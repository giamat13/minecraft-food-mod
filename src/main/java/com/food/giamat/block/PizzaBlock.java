package com.food.giamat.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.CakeBlock;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.level.BlockGetter;

/**
 * A pizza placed on the floor. Behaves like a cake (eaten slice by slice) but is
 * drawn as a flat round pizza, so it gets a flat outline shape instead of cake's.
 */
public class PizzaBlock extends CakeBlock {
    public static final MapCodec<PizzaBlock> CODEC = simpleCodec(PizzaBlock::new);

    private static final VoxelShape SHAPE = Block.box(1, 0, 1, 15, 2, 15);

    public PizzaBlock(Properties settings) {
        super(settings);
    }

    @Override
    @SuppressWarnings({"unchecked", "rawtypes"})
    public MapCodec<CakeBlock> codec() {
        return (MapCodec) CODEC;
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }
}
