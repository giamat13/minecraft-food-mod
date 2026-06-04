package com.food.giamat.block;

import com.food.giamat.block.entity.SusEffectsBlockEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.SuspiciousStewEffectsComponent;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

/**
 * Block item for SusCakeBlock. Copies the SUSPICIOUS_STEW_EFFECTS component
 * from the item stack into the block entity when the cake is placed.
 */
public class SusCakeBlockItem extends BlockItem {

    public SusCakeBlockItem(Block block, Settings settings) {
        super(block, settings);
    }

    @Override
    protected void onPlaced(World world, BlockPos pos, BlockState state,
            @Nullable LivingEntity placer, ItemStack stack) {
        super.onPlaced(world, pos, state, placer, stack);
        if (!world.isClient()) {
            BlockEntity be = world.getBlockEntity(pos);
            if (be instanceof SusEffectsBlockEntity susEntity) {
                SuspiciousStewEffectsComponent effects =
                        stack.get(DataComponentTypes.SUSPICIOUS_STEW_EFFECTS);
                if (effects != null) {
                    susEntity.setEffects(effects);
                }
            }
        }
    }
}
