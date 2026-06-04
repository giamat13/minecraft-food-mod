package com.food.giamat.init;

import com.food.giamat.FoodBygiamat;
import com.food.giamat.block.BananaLeavesBlock;
import com.food.giamat.block.CakeOnTrayBlock;
import com.food.giamat.block.ChiliPepperBushBlock;
import com.food.giamat.block.CornBlock;
import com.food.giamat.block.CursedCakeBlock;
import com.food.giamat.block.EndCakeBlock;
import com.food.giamat.block.GrapeBushBlock;
import com.food.giamat.block.PizzaBlock;
import com.food.giamat.block.PizzaOnTrayBlock;
import com.food.giamat.block.RiceBlock;
import com.food.giamat.block.SusCakeBlock;
import com.food.giamat.block.SusPizzaBlock;
import com.food.giamat.block.TomatoBushBlock;
import com.food.giamat.block.TrayBlock;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.component.type.FoodComponent;
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

    // Wild tomato bush that generates in the plains (behaves like the chili bush).
    public static final Block TOMATO_BUSH = Registry.register(
            Registries.BLOCK,
            RegistryKey.of(RegistryKeys.BLOCK, Identifier.of(FoodBygiamat.MOD_ID, "tomato_bush")),
            new TomatoBushBlock(AbstractBlock.Settings.copy(Blocks.DEAD_BUSH)
                    .registryKey(RegistryKey.of(RegistryKeys.BLOCK, Identifier.of(FoodBygiamat.MOD_ID, "tomato_bush"))))
    );

    // Corn: a two-block-tall crop that grows like sugar cane.
    public static final Block CORN = Registry.register(
            Registries.BLOCK,
            RegistryKey.of(RegistryKeys.BLOCK, Identifier.of(FoodBygiamat.MOD_ID, "corn")),
            new CornBlock(AbstractBlock.Settings.copy(Blocks.SUGAR_CANE)
                    .ticksRandomly()
                    .registryKey(RegistryKey.of(RegistryKeys.BLOCK, Identifier.of(FoodBygiamat.MOD_ID, "corn"))))
    );

    // Rice: a crop that only grows on dirt submerged in water (a rice paddy).
    public static final Block RICE = Registry.register(
            Registries.BLOCK,
            RegistryKey.of(RegistryKeys.BLOCK, Identifier.of(FoodBygiamat.MOD_ID, "rice")),
            new RiceBlock(AbstractBlock.Settings.copy(Blocks.SUGAR_CANE)
                    .ticksRandomly()
                    .registryKey(RegistryKey.of(RegistryKeys.BLOCK, Identifier.of(FoodBygiamat.MOD_ID, "rice"))))
    );

    // End cake: placed by the end cake item; eating a slice teleports you.
    public static final Block END_CAKE_BLOCK = Registry.register(
            Registries.BLOCK,
            RegistryKey.of(RegistryKeys.BLOCK, Identifier.of(FoodBygiamat.MOD_ID, "end_cake")),
            new EndCakeBlock(AbstractBlock.Settings.copy(Blocks.CAKE)
                    .registryKey(RegistryKey.of(RegistryKeys.BLOCK, Identifier.of(FoodBygiamat.MOD_ID, "end_cake"))))
    );

    // Wild grape bush that generates in the plains (behaves like the tomato bush).
    public static final Block GRAPE_BUSH = Registry.register(
            Registries.BLOCK,
            RegistryKey.of(RegistryKeys.BLOCK, Identifier.of(FoodBygiamat.MOD_ID, "grape_bush")),
            new GrapeBushBlock(AbstractBlock.Settings.copy(Blocks.DEAD_BUSH)
                    .registryKey(RegistryKey.of(RegistryKeys.BLOCK, Identifier.of(FoodBygiamat.MOD_ID, "grape_bush"))))
    );

    // Pizza block: placed by the pizza item, behaves like cake (6 slices).
    public static final Block PIZZA_BLOCK = Registry.register(
            Registries.BLOCK,
            RegistryKey.of(RegistryKeys.BLOCK, Identifier.of(FoodBygiamat.MOD_ID, "pizza")),
            new PizzaBlock(AbstractBlock.Settings.copy(Blocks.CAKE)
                    .registryKey(RegistryKey.of(RegistryKeys.BLOCK, Identifier.of(FoodBygiamat.MOD_ID, "pizza"))))
    );

    // Serving tray: a flat block that can hold pizza or cake.
    public static final Block TRAY_BLOCK = Registry.register(
            Registries.BLOCK,
            RegistryKey.of(RegistryKeys.BLOCK, Identifier.of(FoodBygiamat.MOD_ID, "tray")),
            new TrayBlock(AbstractBlock.Settings.copy(Blocks.IRON_BLOCK)
                    .nonOpaque()
                    .registryKey(RegistryKey.of(RegistryKeys.BLOCK, Identifier.of(FoodBygiamat.MOD_ID, "tray"))))
    );

    // Pizza on a tray: pizza block that leaves an empty tray when fully eaten.
    public static final Block PIZZA_ON_TRAY_BLOCK = Registry.register(
            Registries.BLOCK,
            RegistryKey.of(RegistryKeys.BLOCK, Identifier.of(FoodBygiamat.MOD_ID, "pizza_on_tray")),
            new PizzaOnTrayBlock(AbstractBlock.Settings.copy(Blocks.CAKE)
                    .nonOpaque()
                    .registryKey(RegistryKey.of(RegistryKeys.BLOCK, Identifier.of(FoodBygiamat.MOD_ID, "pizza_on_tray"))))
    );

    // Cake on a tray: vanilla cake block that leaves an empty tray when fully eaten.
    public static final Block CAKE_ON_TRAY_BLOCK = Registry.register(
            Registries.BLOCK,
            RegistryKey.of(RegistryKeys.BLOCK, Identifier.of(FoodBygiamat.MOD_ID, "cake_on_tray")),
            new CakeOnTrayBlock(AbstractBlock.Settings.copy(Blocks.CAKE)
                    .nonOpaque()
                    .registryKey(RegistryKey.of(RegistryKeys.BLOCK, Identifier.of(FoodBygiamat.MOD_ID, "cake_on_tray"))))
    );

    // Sus cake: vanilla cake infused with suspicious stew; eating a slice applies the stew's effects.
    public static final Block SUS_CAKE_BLOCK = Registry.register(
            Registries.BLOCK,
            RegistryKey.of(RegistryKeys.BLOCK, Identifier.of(FoodBygiamat.MOD_ID, "sus_cake")),
            new SusCakeBlock(AbstractBlock.Settings.copy(Blocks.CAKE)
                    .registryKey(RegistryKey.of(RegistryKeys.BLOCK, Identifier.of(FoodBygiamat.MOD_ID, "sus_cake"))))
    );

    // Sus pizza: pizza infused with suspicious stew; eating a slice applies the stew's effects.
    public static final Block SUS_PIZZA_BLOCK = Registry.register(
            Registries.BLOCK,
            RegistryKey.of(RegistryKeys.BLOCK, Identifier.of(FoodBygiamat.MOD_ID, "sus_pizza")),
            new SusPizzaBlock(AbstractBlock.Settings.copy(Blocks.CAKE)
                    .registryKey(RegistryKey.of(RegistryKeys.BLOCK, Identifier.of(FoodBygiamat.MOD_ID, "sus_pizza"))))
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

        // Block item for the tomato bush so shears/Silk Touch can drop a placeable version.
        RegistryKey<Item> tomatoBushKey = RegistryKey.of(RegistryKeys.ITEM, Identifier.of(FoodBygiamat.MOD_ID, "tomato_bush"));
        Registry.register(
                Registries.ITEM,
                tomatoBushKey,
                new BlockItem(TOMATO_BUSH, new Item.Settings()
                        .registryKey(tomatoBushKey)
                        .useBlockPrefixedTranslationKey())
        );

        // Corn and rice are placed by edible items that double as seeds.
        RegistryKey<Item> cornKey = RegistryKey.of(RegistryKeys.ITEM, Identifier.of(FoodBygiamat.MOD_ID, "corn"));
        Registry.register(
                Registries.ITEM,
                cornKey,
                new BlockItem(CORN, new Item.Settings()
                        .registryKey(cornKey)
                        .useBlockPrefixedTranslationKey()
                        .food(new FoodComponent(2, 0.2f, false)))
        );

        RegistryKey<Item> riceKey = RegistryKey.of(RegistryKeys.ITEM, Identifier.of(FoodBygiamat.MOD_ID, "rice"));
        Registry.register(
                Registries.ITEM,
                riceKey,
                new BlockItem(RICE, new Item.Settings()
                        .registryKey(riceKey)
                        .useBlockPrefixedTranslationKey()
                        .food(new FoodComponent(1, 0.1f, false)))
        );

        // Block item for the grape bush so shears/Silk Touch can drop a placeable version.
        RegistryKey<Item> grapeBushKey = RegistryKey.of(RegistryKeys.ITEM, Identifier.of(FoodBygiamat.MOD_ID, "grape_bush"));
        Registry.register(
                Registries.ITEM,
                grapeBushKey,
                new BlockItem(GRAPE_BUSH, new Item.Settings()
                        .registryKey(grapeBushKey)
                        .useBlockPrefixedTranslationKey())
        );

        // Tray item — crafted from iron, placed as a decorative block.
        RegistryKey<Item> trayKey = RegistryKey.of(RegistryKeys.ITEM, Identifier.of(FoodBygiamat.MOD_ID, "tray"));
        Registry.register(
                Registries.ITEM,
                trayKey,
                new BlockItem(TRAY_BLOCK, new Item.Settings().registryKey(trayKey))
        );

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.NATURAL).register(entries -> {
            entries.add(BANANA_LEAVES.asItem());
            entries.add(CHILI_PEPPER_BUSH.asItem());
            entries.add(TOMATO_BUSH.asItem());
            entries.add(CORN.asItem());
            entries.add(RICE.asItem());
            entries.add(GRAPE_BUSH.asItem());
        });
    }
}
