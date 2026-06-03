package com.food.giamat.block;

import com.food.giamat.init.ModItems;
import com.mojang.serialization.MapCodec;
import net.minecraft.block.PlantBlock;
import net.minecraft.item.Item;

/**
 * A wild grape bush that grows in plains. It behaves just like the tomato
 * bush (shear the fruit off, regrows after a while) but drops grapes.
 */
public class GrapeBushBlock extends FruitBushBlock {
    public static final MapCodec<GrapeBushBlock> CODEC = createCodec(GrapeBushBlock::new);

    public GrapeBushBlock(Settings settings) {
        super(settings, false);
    }

    @Override
    protected MapCodec<? extends PlantBlock> getCodec() {
        return CODEC;
    }

    @Override
    protected Item getFruit() {
        return ModItems.GRAPE;
    }
}
