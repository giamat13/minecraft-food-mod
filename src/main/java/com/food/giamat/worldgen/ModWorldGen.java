package com.food.giamat.worldgen;

import com.food.giamat.FoodBygiamat;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.resources.ResourceKey;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

public class ModWorldGen {

    public static final ResourceKey<PlacedFeature> BANANA_TREE_PLACED =
            ResourceKey.create(Registries.PLACED_FEATURE, Identifier.fromNamespaceAndPath(FoodBygiamat.MOD_ID, "banana_tree"));

    public static final ResourceKey<PlacedFeature> LEMON_TREE_PLACED =
            ResourceKey.create(Registries.PLACED_FEATURE, Identifier.fromNamespaceAndPath(FoodBygiamat.MOD_ID, "lemon_tree"));

    public static final ResourceKey<PlacedFeature> POMEGRANATE_TREE_PLACED =
            ResourceKey.create(Registries.PLACED_FEATURE, Identifier.fromNamespaceAndPath(FoodBygiamat.MOD_ID, "pomegranate_tree"));

    public static final ResourceKey<PlacedFeature> CHILI_PEPPER_BUSH_PLACED =
            ResourceKey.create(Registries.PLACED_FEATURE, Identifier.fromNamespaceAndPath(FoodBygiamat.MOD_ID, "chili_pepper_bush"));

    public static final ResourceKey<PlacedFeature> TOMATO_BUSH_PLACED =
            ResourceKey.create(Registries.PLACED_FEATURE, Identifier.fromNamespaceAndPath(FoodBygiamat.MOD_ID, "tomato_bush"));

    public static final ResourceKey<PlacedFeature> CORN_PLACED =
            ResourceKey.create(Registries.PLACED_FEATURE, Identifier.fromNamespaceAndPath(FoodBygiamat.MOD_ID, "corn"));

    public static final ResourceKey<PlacedFeature> RICE_PLACED =
            ResourceKey.create(Registries.PLACED_FEATURE, Identifier.fromNamespaceAndPath(FoodBygiamat.MOD_ID, "rice"));

    public static final ResourceKey<PlacedFeature> GRAPE_BUSH_PLACED =
            ResourceKey.create(Registries.PLACED_FEATURE, Identifier.fromNamespaceAndPath(FoodBygiamat.MOD_ID, "grape_bush"));

    public static final ResourceKey<PlacedFeature> OLIVE_TREE_PLACED =
            ResourceKey.create(Registries.PLACED_FEATURE, Identifier.fromNamespaceAndPath(FoodBygiamat.MOD_ID, "olive_tree"));

    public static void initialize() {
        BiomeModifications.addFeature(
                BiomeSelectors.includeByKey(Biomes.PLAINS),
                GenerationStep.Decoration.VEGETAL_DECORATION,
                BANANA_TREE_PLACED
        );
        BiomeModifications.addFeature(
                BiomeSelectors.includeByKey(Biomes.SAVANNA),
                GenerationStep.Decoration.VEGETAL_DECORATION,
                LEMON_TREE_PLACED
        );
        BiomeModifications.addFeature(
                BiomeSelectors.includeByKey(Biomes.SAVANNA),
                GenerationStep.Decoration.VEGETAL_DECORATION,
                POMEGRANATE_TREE_PLACED
        );
        BiomeModifications.addFeature(
                BiomeSelectors.includeByKey(Biomes.DESERT),
                GenerationStep.Decoration.VEGETAL_DECORATION,
                CHILI_PEPPER_BUSH_PLACED
        );
        BiomeModifications.addFeature(
                BiomeSelectors.includeByKey(Biomes.PLAINS),
                GenerationStep.Decoration.VEGETAL_DECORATION,
                TOMATO_BUSH_PLACED
        );
        BiomeModifications.addFeature(
                BiomeSelectors.includeByKey(Biomes.PLAINS),
                GenerationStep.Decoration.VEGETAL_DECORATION,
                CORN_PLACED
        );
        BiomeModifications.addFeature(
                BiomeSelectors.includeByKey(Biomes.SWAMP),
                GenerationStep.Decoration.VEGETAL_DECORATION,
                RICE_PLACED
        );
        BiomeModifications.addFeature(
                BiomeSelectors.includeByKey(Biomes.PLAINS),
                GenerationStep.Decoration.VEGETAL_DECORATION,
                GRAPE_BUSH_PLACED
        );
        BiomeModifications.addFeature(
                BiomeSelectors.includeByKey(Biomes.SAVANNA),
                GenerationStep.Decoration.VEGETAL_DECORATION,
                OLIVE_TREE_PLACED
        );
    }
}
