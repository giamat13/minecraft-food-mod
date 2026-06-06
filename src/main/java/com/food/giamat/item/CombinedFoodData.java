package com.food.giamat.item;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.effect.MobEffectInstance;

import java.util.List;

public record CombinedFoodData(int foodCount, List<MobEffectInstance> effects) {

    public static final Codec<CombinedFoodData> CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                    Codec.INT.fieldOf("food_count").forGetter(CombinedFoodData::foodCount),
                    MobEffectInstance.CODEC.listOf().fieldOf("effects").forGetter(CombinedFoodData::effects)
            ).apply(instance, CombinedFoodData::new));
}
