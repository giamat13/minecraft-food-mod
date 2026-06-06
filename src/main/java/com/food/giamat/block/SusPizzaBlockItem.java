package com.food.giamat.block;

import com.food.giamat.block.entity.SusEffectsBlockEntity;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.component.SuspiciousStewEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

/**
 * Block item for SusPizzaBlock. Copies the SUSPICIOUS_STEW_EFFECTS component
 * from the item stack into the block entity when the pizza is placed.
 */
public class SusPizzaBlockItem extends BlockItem {

    public SusPizzaBlockItem(Block block, Properties settings) {
        super(block, settings);
    }

    @Override
    protected boolean updateCustomBlockEntityTag(BlockPos pos, Level world,
            @Nullable Player placer, ItemStack stack, BlockState state) {
        boolean result = super.updateCustomBlockEntityTag(pos, world, placer, stack, state);
        if (!world.isClientSide()) {
            BlockEntity be = world.getBlockEntity(pos);
            if (be instanceof SusEffectsBlockEntity susEntity) {
                SuspiciousStewEffects effects =
                        stack.get(DataComponents.SUSPICIOUS_STEW_EFFECTS);
                if (effects != null) {
                    susEntity.setEffects(effects);
                }
            }
        }
        return result;
    }
}
