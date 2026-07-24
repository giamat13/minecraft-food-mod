package com.food.giamat.block;

import com.food.giamat.init.ModItems;
import com.mojang.serialization.MapCodec;
import net.minecraft.world.level.block.VegetationBlock;
import net.minecraft.world.item.Item;

/**
 * A wild pineapple bush that grows in the jungle. It behaves just like the
 * tomato bush but drops pineapples instead of tomatoes.
 */
public class PineappleBushBlock extends FruitBushBlock {
    public static final MapCodec<PineappleBushBlock> CODEC = simpleCodec(PineappleBushBlock::new);

    public PineappleBushBlock(Properties settings) {
        super(settings, false);
    }

    @Override
    protected MapCodec<? extends VegetationBlock> codec() {
        return CODEC;
    }

    @Override
    protected Item getFruit() {
        return ModItems.PINEAPPLE;
    }
}
