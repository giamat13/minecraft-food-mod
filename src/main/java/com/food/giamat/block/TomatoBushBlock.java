package com.food.giamat.block;

import com.food.giamat.init.ModItems;
import com.mojang.serialization.MapCodec;
import net.minecraft.world.level.block.VegetationBlock;
import net.minecraft.world.item.Item;

/**
 * A wild tomato bush that grows in plains. It behaves just like the chili
 * pepper bush but drops tomatoes instead of peppers.
 */
public class TomatoBushBlock extends FruitBushBlock {
    public static final MapCodec<TomatoBushBlock> CODEC = simpleCodec(TomatoBushBlock::new);

    public TomatoBushBlock(Properties settings) {
        super(settings, false);
    }

    @Override
    protected MapCodec<? extends VegetationBlock> codec() {
        return CODEC;
    }

    @Override
    protected Item getFruit() {
        return ModItems.TOMATO;
    }
}
