package com.food.giamat.block;

import com.food.giamat.block.entity.SusEffectsBlockEntity;
import com.mojang.serialization.MapCodec;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.CakeBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

/**
 * A cake that applies the suspicious stew effects stored in its block entity
 * whenever a slice is eaten.
 */
public class SusCakeBlock extends CakeBlock implements EntityBlock {
    public static final MapCodec<SusCakeBlock> CODEC = simpleCodec(SusCakeBlock::new);

    public SusCakeBlock(Properties settings) {
        super(settings);
    }

    @Override
    @SuppressWarnings({"unchecked", "rawtypes"})
    public MapCodec<CakeBlock> codec() {
        return (MapCodec) CODEC;
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new SusEffectsBlockEntity(pos, state);
    }

    @Override
    public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(
            Level world, BlockState state, BlockEntityType<T> type) {
        return null;
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level world, BlockPos pos,
            Player player, BlockHitResult hit) {
        // Capture entity reference before super() may remove the block on last bite.
        SusEffectsBlockEntity susEntity = null;
        if (!world.isClientSide()) {
            if (world.getBlockEntity(pos) instanceof SusEffectsBlockEntity be) {
                susEntity = be;
            }
        }

        InteractionResult result = super.useWithoutItem(state, world, pos, player, hit);

        if (result.consumesAction() && susEntity != null) {
            susEntity.applyEffects(player);
        }
        return result;
    }
}
