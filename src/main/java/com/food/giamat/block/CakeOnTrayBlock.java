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
 * Vanilla cake placed on a tray. Behaves exactly like CakeBlock but leaves an
 * empty tray behind when the last slice is eaten.
 */
public class CakeOnTrayBlock extends CakeBlock {
    public static final MapCodec<CakeOnTrayBlock> CODEC = simpleCodec(CakeOnTrayBlock::new);

    // Tray base + rim and the cake sitting on top.
    private static final VoxelShape SHAPE = Block.box(0, 0, 0, 16, 12, 16);

    public CakeOnTrayBlock(Properties settings) {
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
        if (result.consumesAction() && !world.isClientSide() && bites == 6) {
            world.setBlock(pos, ModBlocks.TRAY_BLOCK.defaultBlockState(), Block.UPDATE_ALL);
        }
        return result;
    }
}
