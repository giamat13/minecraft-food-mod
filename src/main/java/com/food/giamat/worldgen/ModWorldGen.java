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

    public static final RegistryKey<PlacedFeature> LEMON_TREE_PLACED =
            RegistryKey.of(RegistryKeys.PLACED_FEATURE, Identifier.of(FoodBygiamat.MOD_ID, "lemon_tree"));

    public static final RegistryKey<PlacedFeature> POMEGRANATE_TREE_PLACED =
            RegistryKey.of(RegistryKeys.PLACED_FEATURE, Identifier.of(FoodBygiamat.MOD_ID, "pomegranate_tree"));

    public static final RegistryKey<PlacedFeature> CHILI_PEPPER_BUSH_PLACED =
            RegistryKey.of(RegistryKeys.PLACED_FEATURE, Identifier.of(FoodBygiamat.MOD_ID, "chili_pepper_bush"));

    public static final RegistryKey<PlacedFeature> TOMATO_BUSH_PLACED =
            RegistryKey.of(RegistryKeys.PLACED_FEATURE, Identifier.of(FoodBygiamat.MOD_ID, "tomato_bush"));

    public static final RegistryKey<PlacedFeature> CORN_PLACED =
            RegistryKey.of(RegistryKeys.PLACED_FEATURE, Identifier.of(FoodBygiamat.MOD_ID, "corn"));

    public static final RegistryKey<PlacedFeature> RICE_PLACED =
            RegistryKey.of(RegistryKeys.PLACED_FEATURE, Identifier.of(FoodBygiamat.MOD_ID, "rice"));

    public static final RegistryKey<PlacedFeature> GRAPE_BUSH_PLACED =
            RegistryKey.of(RegistryKeys.PLACED_FEATURE, Identifier.of(FoodBygiamat.MOD_ID, "grape_bush"));

    public static final RegistryKey<PlacedFeature> OLIVE_TREE_PLACED =
            RegistryKey.of(RegistryKeys.PLACED_FEATURE, Identifier.of(FoodBygiamat.MOD_ID, "olive_tree"));

    public static void initialize() {
        BiomeModifications.addFeature(
                BiomeSelectors.includeByKey(BiomeKeys.PLAINS),
                GenerationStep.Feature.VEGETAL_DECORATION,
                BANANA_TREE_PLACED
        );
        BiomeModifications.addFeature(
                BiomeSelectors.includeByKey(BiomeKeys.SAVANNA),
                GenerationStep.Feature.VEGETAL_DECORATION,
                LEMON_TREE_PLACED
        );
        BiomeModifications.addFeature(
                BiomeSelectors.includeByKey(BiomeKeys.SAVANNA),
                GenerationStep.Feature.VEGETAL_DECORATION,
                POMEGRANATE_TREE_PLACED
        );
        BiomeModifications.addFeature(
                BiomeSelectors.includeByKey(BiomeKeys.DESERT),
                GenerationStep.Feature.VEGETAL_DECORATION,
                CHILI_PEPPER_BUSH_PLACED
        );
        BiomeModifications.addFeature(
                BiomeSelectors.includeByKey(BiomeKeys.PLAINS),
                GenerationStep.Feature.VEGETAL_DECORATION,
                TOMATO_BUSH_PLACED
        );
        BiomeModifications.addFeature(
                BiomeSelectors.includeByKey(BiomeKeys.PLAINS),
                GenerationStep.Feature.VEGETAL_DECORATION,
                CORN_PLACED
        );
        BiomeModifications.addFeature(
                BiomeSelectors.includeByKey(BiomeKeys.SWAMP),
                GenerationStep.Feature.VEGETAL_DECORATION,
                RICE_PLACED
        );
        BiomeModifications.addFeature(
                BiomeSelectors.includeByKey(BiomeKeys.PLAINS),
                GenerationStep.Feature.VEGETAL_DECORATION,
                GRAPE_BUSH_PLACED
        );
        BiomeModifications.addFeature(
                BiomeSelectors.includeByKey(BiomeKeys.SAVANNA),
                GenerationStep.Feature.VEGETAL_DECORATION,
                OLIVE_TREE_PLACED
        );
    }
}
