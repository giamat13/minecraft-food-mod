package com.food.giamat.block;

import com.food.giamat.block.entity.SusEffectsBlockEntity;
import com.mojang.serialization.MapCodec;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockState;
import net.minecraft.block.CakeBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

/**
 * A pizza that applies the suspicious stew effects stored in its block entity
 * whenever a slice is eaten.
 */
public class SusPizzaBlock extends PizzaBlock implements BlockEntityProvider {
    public static final MapCodec<SusPizzaBlock> CODEC = createCodec(SusPizzaBlock::new);

    public SusPizzaBlock(Settings settings) {
        super(settings);
    }

    @Override
    @SuppressWarnings({"unchecked", "rawtypes"})
    public MapCodec<CakeBlock> getCodec() {
        return (MapCodec) CODEC;
    }

    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new SusEffectsBlockEntity(pos, state);
    }

    @Override
    public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(
            World world, BlockState state, BlockEntityType<T> type) {
        return null;
    }

    @Override
    protected ActionResult onUse(BlockState state, World world, BlockPos pos,
            PlayerEntity player, BlockHitResult hit) {
        // Capture entity reference before super() may remove the block on last bite.
        SusEffectsBlockEntity susEntity = null;
        if (!world.isClient()) {
            if (world.getBlockEntity(pos) instanceof SusEffectsBlockEntity be) {
                susEntity = be;
            }
        }

        ActionResult result = super.onUse(state, world, pos, player, hit);

        if (result.isAccepted() && susEntity != null) {
            susEntity.applyEffects(player);
        }
        return result;
    }
}
