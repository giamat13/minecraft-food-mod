package com.food.giamat.block;

import com.food.giamat.init.ModBlocks;
import com.food.giamat.init.ModItems;
import com.mojang.serialization.MapCodec;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;

public class TrayBlock extends Block {
    public static final MapCodec<TrayBlock> CODEC = simpleCodec(TrayBlock::new);

    private static final VoxelShape SHAPE = Shapes.or(
            Block.box(0, 0, 0, 16, 2, 16),   // flat base
            Block.box(0, 2, 0, 2, 4, 16),    // left rim
            Block.box(14, 2, 0, 16, 4, 16),  // right rim
            Block.box(2, 2, 0, 14, 4, 2),    // front rim
            Block.box(2, 2, 14, 14, 4, 16)   // back rim
    );

    private final DyeColor color;

    public TrayBlock(Properties settings, DyeColor color) {
        super(settings);
        this.color = color;
    }

    // Constructor used by CODEC (no color param) — defaults to gray/plain tray
    public TrayBlock(Properties settings) {
        this(settings, null);
    }

    public DyeColor getColor() {
        return color;
    }

    @Override
    public MapCodec<TrayBlock> codec() {
        return CODEC;
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level world, BlockPos pos, Player player, BlockHitResult hit) {
        ItemStack held = player.getMainHandItem();
        if (!world.isClientSide()) {
            // Pizza on tray
            if (held.getItem() == ModItems.PIZZA || held.getItem() == ModItems.TOPPED_PIZZA) {
                world.setBlock(pos, ModBlocks.PIZZA_ON_TRAY_BLOCK.defaultBlockState(), Block.UPDATE_ALL);
                if (!player.isCreative()) held.shrink(1);
                return InteractionResult.SUCCESS;
            }
            // Vanilla cake on tray — we place the vanilla cake block on top of the tray pos
            // by replacing the tray with a cake-on-tray block
            if (held.getItem() == net.minecraft.world.item.Items.CAKE) {
                world.setBlock(pos, ModBlocks.CAKE_ON_TRAY_BLOCK.defaultBlockState(), Block.UPDATE_ALL);
                if (!player.isCreative()) held.shrink(1);
                return InteractionResult.SUCCESS;
            }
        }
        return InteractionResult.PASS;
    }
}
