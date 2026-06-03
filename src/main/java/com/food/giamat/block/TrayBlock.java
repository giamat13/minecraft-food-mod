package com.food.giamat.block;

import com.food.giamat.init.ModBlocks;
import com.food.giamat.init.ModItems;
import com.mojang.serialization.MapCodec;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.DyeColor;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

public class TrayBlock extends Block {
    public static final MapCodec<TrayBlock> CODEC = createCodec(TrayBlock::new);

    private static final VoxelShape SHAPE = VoxelShapes.union(
            Block.createCuboidShape(0, 0, 0, 16, 2, 16),   // flat base
            Block.createCuboidShape(0, 2, 0, 2, 4, 16),    // left rim
            Block.createCuboidShape(14, 2, 0, 16, 4, 16),  // right rim
            Block.createCuboidShape(2, 2, 0, 14, 4, 2),    // front rim
            Block.createCuboidShape(2, 2, 14, 14, 4, 16)   // back rim
    );

    private final DyeColor color;

    public TrayBlock(Settings settings, DyeColor color) {
        super(settings);
        this.color = color;
    }

    // Constructor used by CODEC (no color param) — defaults to gray/plain tray
    public TrayBlock(Settings settings) {
        this(settings, null);
    }

    public DyeColor getColor() {
        return color;
    }

    @Override
    public MapCodec<TrayBlock> getCodec() {
        return CODEC;
    }

    @Override
    protected VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }

    @Override
    protected ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
        ItemStack held = player.getMainHandStack();
        if (!world.isClient()) {
            // Pizza on tray
            if (held.isOf(ModItems.PIZZA) || held.isOf(ModItems.TOPPED_PIZZA)) {
                world.setBlockState(pos, ModBlocks.PIZZA_ON_TRAY_BLOCK.getDefaultState());
                if (!player.isCreative()) held.decrement(1);
                return ActionResult.SUCCESS;
            }
            // Vanilla cake on tray — we place the vanilla cake block on top of the tray pos
            // by replacing the tray with a cake-on-tray block
            if (held.isOf(net.minecraft.item.Items.CAKE)) {
                world.setBlockState(pos, ModBlocks.CAKE_ON_TRAY_BLOCK.getDefaultState());
                if (!player.isCreative()) held.decrement(1);
                return ActionResult.SUCCESS;
            }
        }
        return ActionResult.PASS;
    }
}
