package com.food.giamat.init;

import com.food.giamat.FoodBygiamat;
import com.food.giamat.block.BananaLeavesBlock;
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

    public static void initialize() {
        // Register block item
        RegistryKey<Item> itemKey = RegistryKey.of(RegistryKeys.ITEM, Identifier.of(FoodBygiamat.MOD_ID, "banana_leaves"));
        Registry.register(
                Registries.ITEM,
                itemKey,
                new BlockItem(BANANA_LEAVES, new Item.Settings().registryKey(itemKey))
        );
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.NATURAL).register(entries -> {
            entries.add(BANANA_LEAVES.asItem());
        });
    }
}
