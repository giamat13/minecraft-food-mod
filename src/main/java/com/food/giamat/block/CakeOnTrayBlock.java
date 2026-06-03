package com.food.giamat.block;

import com.food.giamat.init.ModBlocks;
import com.mojang.serialization.MapCodec;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.CakeBlock;
import net.minecraft.block.ShapeContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

/**
 * Vanilla cake placed on a tray. Behaves exactly like CakeBlock but leaves an
 * empty tray behind when the last slice is eaten.
 */
public class CakeOnTrayBlock extends CakeBlock {
    public static final MapCodec<CakeOnTrayBlock> CODEC = createCodec(CakeOnTrayBlock::new);

    // Tray base + rim and the cake sitting on top.
    private static final VoxelShape SHAPE = Block.createCuboidShape(0, 0, 0, 16, 12, 16);

    public CakeOnTrayBlock(Settings settings) {
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

    @Override
    protected ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
        int bites = state.get(BITES);
        ActionResult result = super.onUse(state, world, pos, player, hit);
        if (result.isAccepted() && !world.isClient() && bites == 6) {
            world.setBlockState(pos, ModBlocks.TRAY_BLOCK.getDefaultState());
        }
        return result;
    }
}
