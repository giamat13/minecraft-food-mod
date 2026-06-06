package com.food.giamat.block;

import com.food.giamat.init.ModBlocks;
import com.mojang.serialization.MapCodec;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.CakeBlock;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;

/**
 * Pizza placed on a tray. Behaves like CakeBlock (6 slices) but drops the tray
 * when fully eaten or broken.
 */
public class PizzaOnTrayBlock extends CakeBlock {
    public static final MapCodec<PizzaOnTrayBlock> CODEC = simpleCodec(PizzaOnTrayBlock::new);

    // Tray base + rim and the pizza resting on top.
    private static final VoxelShape SHAPE = Block.box(0, 0, 0, 16, 5, 16);

    public PizzaOnTrayBlock(Properties settings) {
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

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level world, BlockPos pos, Player player, BlockHitResult hit) {
        int bites = state.getValue(BITES);
        InteractionResult result = super.useWithoutItem(state, world, pos, player, hit);
        // When the last slice is eaten, the block is removed — leave an empty tray.
        if (result.consumesAction() && !world.isClientSide() && bites == 6) {
            world.setBlock(pos, ModBlocks.TRAY_BLOCK.defaultBlockState(), Block.UPDATE_ALL);
        }
        return result;
    }
}
