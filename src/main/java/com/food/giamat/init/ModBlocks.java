package com.food.giamat.init;

import com.food.giamat.FoodBygiamat;
import com.food.giamat.block.BananaLeavesBlock;
import com.food.giamat.block.ChiliPepperBushBlock;
import com.food.giamat.block.CursedCakeBlock;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;

public class ModBlocks {
    public static final Block BANANA_LEAVES = Registry.register(
            Registries.BLOCK,
            RegistryKey.of(RegistryKeys.BLOCK, Identifier.of(FoodBygiamat.MOD_ID, "banana_leaves")),
            new BananaLeavesBlock(AbstractBlock.Settings.copy(Blocks.OAK_LEAVES)
                    .registryKey(RegistryKey.of(RegistryKeys.BLOCK, Identifier.of(FoodBygiamat.MOD_ID, "banana_leaves"))))
    );

    // Custom cake block placed by the cursed cake item: eating a slice poisons and nauseates.
    public static final Block CURSED_CAKE_BLOCK = Registry.register(
            Registries.BLOCK,
            RegistryKey.of(RegistryKeys.BLOCK, Identifier.of(FoodBygiamat.MOD_ID, "cursed_cake")),
            new CursedCakeBlock(AbstractBlock.Settings.copy(Blocks.CAKE)
                    .registryKey(RegistryKey.of(RegistryKeys.BLOCK, Identifier.of(FoodBygiamat.MOD_ID, "cursed_cake"))))
    );

    // Wild chili pepper bush that generates on desert sand (no block item; harvested for peppers).
    public static final Block CHILI_PEPPER_BUSH = Registry.register(
            Registries.BLOCK,
            RegistryKey.of(RegistryKeys.BLOCK, Identifier.of(FoodBygiamat.MOD_ID, "chili_pepper_bush")),
            new ChiliPepperBushBlock(AbstractBlock.Settings.copy(Blocks.DEAD_BUSH)
                    .registryKey(RegistryKey.of(RegistryKeys.BLOCK, Identifier.of(FoodBygiamat.MOD_ID, "chili_pepper_bush"))))
    );

    public static void initialize() {
        RegistryKey<Item> leavesKey = RegistryKey.of(RegistryKeys.ITEM, Identifier.of(FoodBygiamat.MOD_ID, "banana_leaves"));
        Registry.register(
                Registries.ITEM,
                leavesKey,
                new BlockItem(BANANA_LEAVES, new Item.Settings()
                        .registryKey(leavesKey)
                        .useBlockPrefixedTranslationKey())
        );

        // Block item for the chili pepper bush so Silk Touch can drop a placeable version.
        RegistryKey<Item> chiliKey = RegistryKey.of(RegistryKeys.ITEM, Identifier.of(FoodBygiamat.MOD_ID, "chili_pepper_bush"));
        Registry.register(
                Registries.ITEM,
                chiliKey,
                new BlockItem(CHILI_PEPPER_BUSH, new Item.Settings()
                        .registryKey(chiliKey)
                        .useBlockPrefixedTranslationKey())
        );

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.NATURAL).register(entries -> {
            entries.add(BANANA_LEAVES.asItem());
            entries.add(CHILI_PEPPER_BUSH.asItem());
        });
    }
}
