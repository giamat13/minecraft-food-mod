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
 * Pizza placed on a tray. Behaves like CakeBlock (6 slices) but drops the tray
 * when fully eaten or broken.
 */
public class PizzaOnTrayBlock extends CakeBlock {
    public static final MapCodec<PizzaOnTrayBlock> CODEC = createCodec(PizzaOnTrayBlock::new);

    // Tray base + rim and the pizza resting on top.
    private static final VoxelShape SHAPE = Block.createCuboidShape(0, 0, 0, 16, 5, 16);

    public PizzaOnTrayBlock(Settings settings) {
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
        // When the last slice is eaten, the block is removed — leave an empty tray.
        if (result.isAccepted() && !world.isClient() && bites == 6) {
            world.setBlockState(pos, ModBlocks.TRAY_BLOCK.getDefaultState());
        }
        return result;
    }
}
