package com.food.giamat.init;

import com.food.giamat.FoodBygiamat;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.component.type.FoodComponent;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;

public class ModItems {

    public static final Item CHOCOLATE = Registry.register(
            Registries.ITEM,
            RegistryKey.of(RegistryKeys.ITEM, Identifier.of(FoodBygiamat.MOD_ID, "chocolate")),
            new Item(new Item.Settings()
                    .registryKey(RegistryKey.of(RegistryKeys.ITEM, Identifier.of(FoodBygiamat.MOD_ID, "chocolate")))
                    .food(new FoodComponent.Builder()
                            .nutrition(4)
                            .saturationModifier(0.3f)
                            .build()))
    );

    // Crafting ingredients (not edible on their own): wheat -> flour -> dough -> unbaked bread -> bread (smelted)
    public static final Item FLOUR = register("flour");
    public static final Item DOUGH = register("dough");
    public static final Item UNBAKED_BREAD = register("unbaked_bread");

    // Salt is collected by straining a water cauldron; strainer is the tool used to do it.
    public static final Item SALT = register("salt");
    public static final Item STRAINER = register("strainer");

    private static Item register(String name) {
        RegistryKey<Item> key = RegistryKey.of(RegistryKeys.ITEM, Identifier.of(FoodBygiamat.MOD_ID, name));
        return Registry.register(
                Registries.ITEM,
                key,
                new Item(new Item.Settings().registryKey(key)));
    }

    public static void initialize() {
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.FOOD_AND_DRINK).register(entries -> {
            entries.addAfter(net.minecraft.item.Items.CAKE, CHOCOLATE);
        });
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register(entries -> {
            entries.add(FLOUR);
            entries.add(DOUGH);
            entries.add(UNBAKED_BREAD);
            entries.add(SALT);
        });
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.TOOLS).register(entries -> {
            entries.add(STRAINER);
        });
    }
}
