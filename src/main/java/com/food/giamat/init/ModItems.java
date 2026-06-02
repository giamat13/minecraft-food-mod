package com.food.giamat.init;

import com.food.giamat.FoodBygiamat;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.component.type.FoodComponent;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
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

    // New ingredient items (non-food)
    public static final Item MELTED_CHOCOLATE = register("melted_chocolate");
    public static final Item UNBAKED_PITA = register("unbaked_pita");
    public static final Item BREADCRUMBS = register("breadcrumbs");
    public static final Item UNBAKED_SCHNITZEL = register("unbaked_schnitzel");
    public static final Item GELATIN = register("gelatin");
    public static final Item UNBAKED_SAUSAGE = register("unbaked_sausage");
    public static final Item UNBAKED_CHICKEN_NUGGETS = register("unbaked_chicken_nuggets");
    public static final Item UNBAKED_CHICKEN_NUGGETS_BREADCRUMBS = register("unbaked_chicken_nuggets_breadcrumbs");

    // New food items
    public static final Item SHOKO = registerFood("shoko", 6, 0.4f);
    public static final Item PITA = registerFood("pita", 5, 0.6f);
    public static final Item SCHNITZEL = registerFood("schnitzel", 8, 0.8f);
    public static final Item GUMMY_CANDY_WATERMELON = registerFood("gummy_candy_watermelon", 3, 0.2f);
    public static final Item GUMMY_CANDY_APPLE = registerFood("gummy_candy_apple", 3, 0.2f);
    public static final Item GUMMY_CANDY_SWEET_BERRIES = registerFood("gummy_candy_sweet_berries", 3, 0.2f);
    public static final Item GUMMY_CANDY_CARROT = registerFood("gummy_candy_carrot", 3, 0.2f);
    public static final Item BANANA = registerFood("banana", 4, 0.3f);
    public static final Item SAUSAGE = registerFood("sausage", 6, 0.8f);
    public static final Item SAUSAGE_IN_BUN = registerFood("sausage_in_bun", 9, 0.9f);
    public static final Item HAMBURGER = registerFood("hamburger", 10, 0.9f);
    public static final Item CHICKEN_NUGGETS = registerFood("chicken_nuggets", 6, 0.6f);
    public static final Item CHICKEN_NUGGETS_BREADCRUMBS = registerFood("chicken_nuggets_breadcrumbs", 7, 0.7f);

    // Special food with effects
    public static final Item GUMMY_SCHNITZEL = Registry.register(
            Registries.ITEM,
            RegistryKey.of(RegistryKeys.ITEM, Identifier.of(FoodBygiamat.MOD_ID, "gummy_schnitzel")),
            new Item(new Item.Settings()
                    .registryKey(RegistryKey.of(RegistryKeys.ITEM, Identifier.of(FoodBygiamat.MOD_ID, "gummy_schnitzel")))
                    .food(new FoodComponent.Builder()
                            .nutrition(8)
                            .saturationModifier(0.6f)
                            .statusEffect(new StatusEffectInstance(StatusEffects.NAUSEA, 200, 0), 1.0f)
                            .statusEffect(new StatusEffectInstance(StatusEffects.HUNGER, 200, 0), 1.0f)
                            .build()))
    );

    private static Item register(String name) {
        RegistryKey<Item> key = RegistryKey.of(RegistryKeys.ITEM, Identifier.of(FoodBygiamat.MOD_ID, name));
        return Registry.register(
                Registries.ITEM,
                key,
                new Item(new Item.Settings().registryKey(key)));
    }

    private static Item registerFood(String name, int nutrition, float saturation) {
        RegistryKey<Item> key = RegistryKey.of(RegistryKeys.ITEM, Identifier.of(FoodBygiamat.MOD_ID, name));
        return Registry.register(
                Registries.ITEM,
                key,
                new Item(new Item.Settings()
                        .registryKey(key)
                        .food(new FoodComponent.Builder()
                                .nutrition(nutrition)
                                .saturationModifier(saturation)
                                .build()))
        );
    }

    public static void initialize() {
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.FOOD_AND_DRINK).register(entries -> {
            entries.addAfter(net.minecraft.item.Items.CAKE, CHOCOLATE);
            entries.addAfter(CHOCOLATE,
                    SHOKO, PITA, SCHNITZEL,
                    GUMMY_CANDY_WATERMELON, GUMMY_CANDY_APPLE, GUMMY_CANDY_SWEET_BERRIES, GUMMY_CANDY_CARROT,
                    GUMMY_SCHNITZEL, BANANA,
                    SAUSAGE, SAUSAGE_IN_BUN, HAMBURGER,
                    CHICKEN_NUGGETS, CHICKEN_NUGGETS_BREADCRUMBS);
        });
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register(entries -> {
            entries.add(FLOUR);
            entries.add(DOUGH);
            entries.add(UNBAKED_BREAD);
            entries.add(SALT);
            entries.add(MELTED_CHOCOLATE);
            entries.add(UNBAKED_PITA);
            entries.add(BREADCRUMBS);
            entries.add(UNBAKED_SCHNITZEL);
            entries.add(GELATIN);
            entries.add(UNBAKED_SAUSAGE);
            entries.add(UNBAKED_CHICKEN_NUGGETS);
            entries.add(UNBAKED_CHICKEN_NUGGETS_BREADCRUMBS);
        });
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.TOOLS).register(entries -> {
            entries.add(STRAINER);
        });
    }
}
