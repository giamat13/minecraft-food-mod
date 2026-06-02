package com.food.giamat.worldgen;

import com.food.giamat.FoodBygiamat;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.minecraft.world.biome.BiomeKeys;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.feature.PlacedFeature;

public class ModWorldGen {

    public static final RegistryKey<PlacedFeature> BANANA_TREE_PLACED =
            RegistryKey.of(RegistryKeys.PLACED_FEATURE, Identifier.of(FoodBygiamat.MOD_ID, "banana_tree"));

    public static final RegistryKey<PlacedFeature> CHILI_PEPPER_BUSH_PLACED =
            RegistryKey.of(RegistryKeys.PLACED_FEATURE, Identifier.of(FoodBygiamat.MOD_ID, "chili_pepper_bush"));

    public static void initialize() {
        BiomeModifications.addFeature(
                BiomeSelectors.includeByKey(BiomeKeys.PLAINS),
                GenerationStep.Feature.VEGETAL_DECORATION,
                BANANA_TREE_PLACED
        );
        BiomeModifications.addFeature(
                BiomeSelectors.includeByKey(BiomeKeys.DESERT),
                GenerationStep.Feature.VEGETAL_DECORATION,
                CHILI_PEPPER_BUSH_PLACED
        );
    }
}
