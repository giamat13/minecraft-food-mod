package com.food.giamat.block;

import com.food.giamat.init.ModItems;
import com.mojang.serialization.MapCodec;
import net.minecraft.block.PlantBlock;
import net.minecraft.item.Item;

/**
 * A wild chili pepper bush that grows on sand (so it can spawn in deserts).
 * Breaking it drops chili peppers; eating one grants brief fire resistance.
 */
public class ChiliPepperBushBlock extends FruitBushBlock {
    public static final MapCodec<ChiliPepperBushBlock> CODEC = createCodec(ChiliPepperBushBlock::new);

    public ChiliPepperBushBlock(Settings settings) {
        super(settings, true);
    }

    @Override
    protected MapCodec<? extends PlantBlock> getCodec() {
        return CODEC;
    }

    @Override
    protected Item getFruit() {
        return ModItems.CHILI_PEPPER;
    }
}
