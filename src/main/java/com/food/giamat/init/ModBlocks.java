package com.food.giamat.init;

import com.food.giamat.FoodBygiamat;
import com.food.giamat.block.BananaLeavesBlock;
import com.food.giamat.block.CakeOnTrayBlock;
import com.food.giamat.block.ChiliPepperBushBlock;
import com.food.giamat.block.CornBlock;
import com.food.giamat.block.CursedCakeBlock;
import com.food.giamat.block.EndCakeBlock;
import com.food.giamat.block.GrapeBushBlock;
import com.food.giamat.block.OliveLeavesBlock;
import com.food.giamat.block.PizzaBlock;
import com.food.giamat.block.PizzaOnTrayBlock;
import com.food.giamat.block.RiceBlock;
import com.food.giamat.block.LemonLeavesBlock;
import com.food.giamat.block.PomegranateLeavesBlock;
import com.food.giamat.block.SusCakeBlock;
import com.food.giamat.block.SusPizzaBlock;
import com.food.giamat.block.TomatoBushBlock;
import com.food.giamat.block.TrayBlock;
import net.fabricmc.fabric.api.creativetab.v1.CreativeModeTabEvents;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;

public class ModBlocks {
    public static final Block BANANA_LEAVES = Registry.register(
            BuiltInRegistries.BLOCK,
            ResourceKey.create(Registries.BLOCK, Identifier.fromNamespaceAndPath(FoodBygiamat.MOD_ID, "banana_leaves")),
            new BananaLeavesBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_LEAVES)
                    .setId(ResourceKey.create(Registries.BLOCK, Identifier.fromNamespaceAndPath(FoodBygiamat.MOD_ID, "banana_leaves"))))
    );

    // Lemon leaves: fruit-bearing tree leaves that drop lemons.
    public static final Block LEMON_LEAVES = Registry.register(
            BuiltInRegistries.BLOCK,
            ResourceKey.create(Registries.BLOCK, Identifier.fromNamespaceAndPath(FoodBygiamat.MOD_ID, "lemon_leaves")),
            new LemonLeavesBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_LEAVES)
                    .setId(ResourceKey.create(Registries.BLOCK, Identifier.fromNamespaceAndPath(FoodBygiamat.MOD_ID, "lemon_leaves"))))
    );

    // Pomegranate leaves: fruit-bearing tree leaves that drop pomegranates.
    public static final Block POMEGRANATE_LEAVES = Registry.register(
            BuiltInRegistries.BLOCK,
            ResourceKey.create(Registries.BLOCK, Identifier.fromNamespaceAndPath(FoodBygiamat.MOD_ID, "pomegranate_leaves")),
            new PomegranateLeavesBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_LEAVES)
                    .setId(ResourceKey.create(Registries.BLOCK, Identifier.fromNamespaceAndPath(FoodBygiamat.MOD_ID, "pomegranate_leaves"))))
    );

    // Custom cake block placed by the cursed cake item: eating a slice poisons and nauseates.
    public static final Block CURSED_CAKE_BLOCK = Registry.register(
            BuiltInRegistries.BLOCK,
            ResourceKey.create(Registries.BLOCK, Identifier.fromNamespaceAndPath(FoodBygiamat.MOD_ID, "cursed_cake")),
            new CursedCakeBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.CAKE)
                    .setId(ResourceKey.create(Registries.BLOCK, Identifier.fromNamespaceAndPath(FoodBygiamat.MOD_ID, "cursed_cake"))))
    );

    // Wild chili pepper bush that generates on desert sand (no block item; harvested for peppers).
    public static final Block CHILI_PEPPER_BUSH = Registry.register(
            BuiltInRegistries.BLOCK,
            ResourceKey.create(Registries.BLOCK, Identifier.fromNamespaceAndPath(FoodBygiamat.MOD_ID, "chili_pepper_bush")),
            new ChiliPepperBushBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.DEAD_BUSH)
                    .setId(ResourceKey.create(Registries.BLOCK, Identifier.fromNamespaceAndPath(FoodBygiamat.MOD_ID, "chili_pepper_bush"))))
    );

    // Wild tomato bush that generates in the plains (behaves like the chili bush).
    public static final Block TOMATO_BUSH = Registry.register(
            BuiltInRegistries.BLOCK,
            ResourceKey.create(Registries.BLOCK, Identifier.fromNamespaceAndPath(FoodBygiamat.MOD_ID, "tomato_bush")),
            new TomatoBushBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.DEAD_BUSH)
                    .setId(ResourceKey.create(Registries.BLOCK, Identifier.fromNamespaceAndPath(FoodBygiamat.MOD_ID, "tomato_bush"))))
    );

    // Corn: a two-block-tall crop that grows like sugar cane.
    public static final Block CORN = Registry.register(
            BuiltInRegistries.BLOCK,
            ResourceKey.create(Registries.BLOCK, Identifier.fromNamespaceAndPath(FoodBygiamat.MOD_ID, "corn")),
            new CornBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.SUGAR_CANE)
                    .randomTicks()
                    .setId(ResourceKey.create(Registries.BLOCK, Identifier.fromNamespaceAndPath(FoodBygiamat.MOD_ID, "corn"))))
    );

    // Rice: a crop that only grows on dirt submerged in water (a rice paddy).
    public static final Block RICE = Registry.register(
            BuiltInRegistries.BLOCK,
            ResourceKey.create(Registries.BLOCK, Identifier.fromNamespaceAndPath(FoodBygiamat.MOD_ID, "rice")),
            new RiceBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.SUGAR_CANE)
                    .randomTicks()
                    .setId(ResourceKey.create(Registries.BLOCK, Identifier.fromNamespaceAndPath(FoodBygiamat.MOD_ID, "rice"))))
    );

    // End cake: placed by the end cake item; eating a slice teleports you.
    public static final Block END_CAKE_BLOCK = Registry.register(
            BuiltInRegistries.BLOCK,
            ResourceKey.create(Registries.BLOCK, Identifier.fromNamespaceAndPath(FoodBygiamat.MOD_ID, "end_cake")),
            new EndCakeBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.CAKE)
                    .setId(ResourceKey.create(Registries.BLOCK, Identifier.fromNamespaceAndPath(FoodBygiamat.MOD_ID, "end_cake"))))
    );

    // Wild grape bush that generates in the plains (behaves like the tomato bush).
    public static final Block GRAPE_BUSH = Registry.register(
            BuiltInRegistries.BLOCK,
            ResourceKey.create(Registries.BLOCK, Identifier.fromNamespaceAndPath(FoodBygiamat.MOD_ID, "grape_bush")),
            new GrapeBushBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.DEAD_BUSH)
                    .setId(ResourceKey.create(Registries.BLOCK, Identifier.fromNamespaceAndPath(FoodBygiamat.MOD_ID, "grape_bush"))))
    );

    // Pizza block: placed by the pizza item, behaves like cake (6 slices).
    public static final Block PIZZA_BLOCK = Registry.register(
            BuiltInRegistries.BLOCK,
            ResourceKey.create(Registries.BLOCK, Identifier.fromNamespaceAndPath(FoodBygiamat.MOD_ID, "pizza")),
            new PizzaBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.CAKE)
                    .setId(ResourceKey.create(Registries.BLOCK, Identifier.fromNamespaceAndPath(FoodBygiamat.MOD_ID, "pizza"))))
    );

    // Serving tray: a flat block that can hold pizza or cake.
    public static final Block TRAY_BLOCK = Registry.register(
            BuiltInRegistries.BLOCK,
            ResourceKey.create(Registries.BLOCK, Identifier.fromNamespaceAndPath(FoodBygiamat.MOD_ID, "tray")),
            new TrayBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK)
                    .noOcclusion()
                    .setId(ResourceKey.create(Registries.BLOCK, Identifier.fromNamespaceAndPath(FoodBygiamat.MOD_ID, "tray"))))
    );

    // Pizza on a tray: pizza block that leaves an empty tray when fully eaten.
    public static final Block PIZZA_ON_TRAY_BLOCK = Registry.register(
            BuiltInRegistries.BLOCK,
            ResourceKey.create(Registries.BLOCK, Identifier.fromNamespaceAndPath(FoodBygiamat.MOD_ID, "pizza_on_tray")),
            new PizzaOnTrayBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.CAKE)
                    .noOcclusion()
                    .setId(ResourceKey.create(Registries.BLOCK, Identifier.fromNamespaceAndPath(FoodBygiamat.MOD_ID, "pizza_on_tray"))))
    );

    // Cake on a tray: vanilla cake block that leaves an empty tray when fully eaten.
    public static final Block CAKE_ON_TRAY_BLOCK = Registry.register(
            BuiltInRegistries.BLOCK,
            ResourceKey.create(Registries.BLOCK, Identifier.fromNamespaceAndPath(FoodBygiamat.MOD_ID, "cake_on_tray")),
            new CakeOnTrayBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.CAKE)
                    .noOcclusion()
                    .setId(ResourceKey.create(Registries.BLOCK, Identifier.fromNamespaceAndPath(FoodBygiamat.MOD_ID, "cake_on_tray"))))
    );

    // Sus cake: vanilla cake infused with suspicious stew; eating a slice applies the stew's effects.
    public static final Block SUS_CAKE_BLOCK = Registry.register(
            BuiltInRegistries.BLOCK,
            ResourceKey.create(Registries.BLOCK, Identifier.fromNamespaceAndPath(FoodBygiamat.MOD_ID, "sus_cake")),
            new SusCakeBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.CAKE)
                    .setId(ResourceKey.create(Registries.BLOCK, Identifier.fromNamespaceAndPath(FoodBygiamat.MOD_ID, "sus_cake"))))
    );

    // Olive leaves: larger fruit-bearing tree leaves that drop olives.
    public static final Block OLIVE_LEAVES = Registry.register(
            BuiltInRegistries.BLOCK,
            ResourceKey.create(Registries.BLOCK, Identifier.fromNamespaceAndPath(FoodBygiamat.MOD_ID, "olive_leaves")),
            new OliveLeavesBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_LEAVES)
                    .setId(ResourceKey.create(Registries.BLOCK, Identifier.fromNamespaceAndPath(FoodBygiamat.MOD_ID, "olive_leaves"))))
    );

    // Sus pizza: pizza infused with suspicious stew; eating a slice applies the stew's effects.
    public static final Block SUS_PIZZA_BLOCK = Registry.register(
            BuiltInRegistries.BLOCK,
            ResourceKey.create(Registries.BLOCK, Identifier.fromNamespaceAndPath(FoodBygiamat.MOD_ID, "sus_pizza")),
            new SusPizzaBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.CAKE)
                    .setId(ResourceKey.create(Registries.BLOCK, Identifier.fromNamespaceAndPath(FoodBygiamat.MOD_ID, "sus_pizza"))))
    );

    private static ResourceKey<net.minecraft.world.item.CreativeModeTab> tab(String path) {
        return ResourceKey.create(Registries.CREATIVE_MODE_TAB,
                Identifier.fromNamespaceAndPath("minecraft", path));
    }

    public static void initialize() {
        ResourceKey<Item> leavesKey = ResourceKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(FoodBygiamat.MOD_ID, "banana_leaves"));
        Registry.register(
                BuiltInRegistries.ITEM,
                leavesKey,
                new BlockItem(BANANA_LEAVES, new Item.Properties()
                        .setId(leavesKey)
                        .useBlockDescriptionPrefix())
        );

        ResourceKey<Item> lemonLeavesKey = ResourceKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(FoodBygiamat.MOD_ID, "lemon_leaves"));
        Registry.register(
                BuiltInRegistries.ITEM,
                lemonLeavesKey,
                new BlockItem(LEMON_LEAVES, new Item.Properties()
                        .setId(lemonLeavesKey)
                        .useBlockDescriptionPrefix())
        );

        ResourceKey<Item> pomegLeavesKey = ResourceKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(FoodBygiamat.MOD_ID, "pomegranate_leaves"));
        Registry.register(
                BuiltInRegistries.ITEM,
                pomegLeavesKey,
                new BlockItem(POMEGRANATE_LEAVES, new Item.Properties()
                        .setId(pomegLeavesKey)
                        .useBlockDescriptionPrefix())
        );

        // Block item for the chili pepper bush so Silk Touch can drop a placeable version.
        ResourceKey<Item> chiliKey = ResourceKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(FoodBygiamat.MOD_ID, "chili_pepper_bush"));
        Registry.register(
                BuiltInRegistries.ITEM,
                chiliKey,
                new BlockItem(CHILI_PEPPER_BUSH, new Item.Properties()
                        .setId(chiliKey)
                        .useBlockDescriptionPrefix())
        );

        // Block item for the tomato bush so shears/Silk Touch can drop a placeable version.
        ResourceKey<Item> tomatoBushKey = ResourceKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(FoodBygiamat.MOD_ID, "tomato_bush"));
        Registry.register(
                BuiltInRegistries.ITEM,
                tomatoBushKey,
                new BlockItem(TOMATO_BUSH, new Item.Properties()
                        .setId(tomatoBushKey)
                        .useBlockDescriptionPrefix())
        );

        // Corn and rice are placed by edible items that double as seeds.
        ResourceKey<Item> cornKey = ResourceKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(FoodBygiamat.MOD_ID, "corn"));
        Registry.register(
                BuiltInRegistries.ITEM,
                cornKey,
                new BlockItem(CORN, new Item.Properties()
                        .setId(cornKey)
                        .useBlockDescriptionPrefix()
                        .food(new FoodProperties(2, 0.2f, false)))
        );

        ResourceKey<Item> riceKey = ResourceKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(FoodBygiamat.MOD_ID, "rice"));
        Registry.register(
                BuiltInRegistries.ITEM,
                riceKey,
                new BlockItem(RICE, new Item.Properties()
                        .setId(riceKey)
                        .useBlockDescriptionPrefix()
                        .food(new FoodProperties(1, 0.1f, false)))
        );

        // Block item for the grape bush so shears/Silk Touch can drop a placeable version.
        ResourceKey<Item> grapeBushKey = ResourceKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(FoodBygiamat.MOD_ID, "grape_bush"));
        Registry.register(
                BuiltInRegistries.ITEM,
                grapeBushKey,
                new BlockItem(GRAPE_BUSH, new Item.Properties()
                        .setId(grapeBushKey)
                        .useBlockDescriptionPrefix())
        );

        // Tray item — crafted from iron, placed as a decorative block.
        ResourceKey<Item> trayKey = ResourceKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(FoodBygiamat.MOD_ID, "tray"));
        Registry.register(
                BuiltInRegistries.ITEM,
                trayKey,
                new BlockItem(TRAY_BLOCK, new Item.Properties().setId(trayKey))
        );

        ResourceKey<Item> oliveLeavesKey = ResourceKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(FoodBygiamat.MOD_ID, "olive_leaves"));
        Registry.register(
                BuiltInRegistries.ITEM,
                oliveLeavesKey,
                new BlockItem(OLIVE_LEAVES, new Item.Properties()
                        .setId(oliveLeavesKey)
                        .useBlockDescriptionPrefix())
        );

        CreativeModeTabEvents.modifyOutputEvent(tab("natural_blocks")).register(output -> {
            output.accept(BANANA_LEAVES.asItem());
            output.accept(LEMON_LEAVES.asItem());
            output.accept(POMEGRANATE_LEAVES.asItem());
            output.accept(OLIVE_LEAVES.asItem());
            output.accept(CHILI_PEPPER_BUSH.asItem());
            output.accept(TOMATO_BUSH.asItem());
            output.accept(CORN.asItem());
            output.accept(RICE.asItem());
            output.accept(GRAPE_BUSH.asItem());
        });
    }
}
