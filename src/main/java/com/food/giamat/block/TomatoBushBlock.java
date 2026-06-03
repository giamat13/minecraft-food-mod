package com.food.giamat.block;

import com.food.giamat.init.ModItems;
import com.mojang.serialization.MapCodec;
import net.minecraft.block.PlantBlock;
import net.minecraft.item.Item;

/**
 * A wild tomato bush that grows in plains. It behaves just like the chili
 * pepper bush but drops tomatoes instead of peppers.
 */
public class TomatoBushBlock extends FruitBushBlock {
    public static final MapCodec<TomatoBushBlock> CODEC = createCodec(TomatoBushBlock::new);

    public TomatoBushBlock(Settings settings) {
        super(settings, false);
    }

    @Override
    protected MapCodec<? extends PlantBlock> getCodec() {
        return CODEC;
    }

    @Override
    protected Item getFruit() {
        return ModItems.TOMATO;
    }
}
