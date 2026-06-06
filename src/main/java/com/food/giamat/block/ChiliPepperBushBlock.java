package com.food.giamat.block;

import com.food.giamat.init.ModItems;
import com.mojang.serialization.MapCodec;
import net.minecraft.world.level.block.VegetationBlock;
import net.minecraft.world.item.Item;

/**
 * A wild chili pepper bush that grows on sand (so it can spawn in deserts).
 * Breaking it drops chili peppers; eating one grants brief fire resistance.
 */
public class ChiliPepperBushBlock extends FruitBushBlock {
    public static final MapCodec<ChiliPepperBushBlock> CODEC = simpleCodec(ChiliPepperBushBlock::new);

    public ChiliPepperBushBlock(Properties settings) {
        super(settings, true);
    }

    @Override
    protected MapCodec<? extends VegetationBlock> codec() {
        return CODEC;
    }

    @Override
    protected Item getFruit() {
        return ModItems.CHILI_PEPPER;
    }
}
