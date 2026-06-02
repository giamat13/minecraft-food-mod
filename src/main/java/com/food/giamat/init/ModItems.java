package com.food.giamat.init;

import com.food.giamat.FoodBygiamat;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.component.type.ConsumableComponent;
import net.minecraft.component.type.FoodComponent;
import net.minecraft.item.consume.ApplyEffectsConsumeEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.BlockItem;
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
                    .food(new FoodComponent(4, 0.3f, false)))
    );

    // Crafting ingredients (not edible on their own): wheat -> flour -> dough -> unbaked bread -> bread (smelted)
    public static final Item FLOUR = register("flour");
    public static final Item DOUGH = register("dough");

    // Salt is collected by straining a water cauldron; strainer is the tool used to do it.
    public static final Item SALT = register("salt");
    public static final Item STRAINER = register("strainer");

    // Non-food crafting ingredients
    public static final Item MELTED_CHOCOLATE = register("melted_chocolate");
    public static final Item BREADCRUMBS = register("breadcrumbs");
    public static final Item GELATIN = register("gelatin");

    // Unbaked bread is edible with no ill effects (unlike the other unbaked items).
    public static final Item UNBAKED_BREAD = registerFood("unbaked_bread", 1, 0.1f);

    // Unbaked items — edible but cause poison (5 s) and nausea (10 s)
    public static final Item UNBAKED_PITA = registerUnbaked("unbaked_pita", 1);
    public static final Item UNBAKED_SCHNITZEL = registerUnbaked("unbaked_schnitzel", 2);
    public static final Item UNBAKED_SAUSAGE = registerUnbaked("unbaked_sausage", 1);
    public static final Item UNBAKED_CHICKEN_NUGGETS = registerUnbaked("unbaked_chicken_nuggets", 1);
    public static final Item UNBAKED_CHICKEN_NUGGETS_BREADCRUMBS = registerUnbaked("unbaked_chicken_nuggets_breadcrumbs", 2);

    // New food items
    public static final Item SHOKO = registerFood("shoko", 6, 0.4f);
    public static final Item PITA = registerFood("pita", 5, 0.6f);
    public static final Item SCHNITZEL = registerFood("schnitzel", 8, 0.8f);
    public static final Item GUMMY_CANDY_WATERMELON = registerFood("gummy_candy_watermelon", 3, 0.2f);
    public static final Item GUMMY_CANDY_APPLE = registerFood("gummy_candy_apple", 3, 0.2f);
    public static final Item GUMMY_CANDY_SWEET_BERRIES = registerFood("gummy_candy_sweet_berries", 3, 0.2f);
    public static final Item GUMMY_CANDY_CARROT = registerFood("gummy_candy_carrot", 3, 0.2f);
    public static final Item BANANA = registerFood("banana", 4, 0.3f);
    // Chili pepper: foraged from desert bushes; eating one grants Fire Resistance for 10 s.
    public static final Item CHILI_PEPPER = Registry.register(
            Registries.ITEM,
            RegistryKey.of(RegistryKeys.ITEM, Identifier.of(FoodBygiamat.MOD_ID, "chili_pepper")),
            new Item(new Item.Settings()
                    .registryKey(RegistryKey.of(RegistryKeys.ITEM, Identifier.of(FoodBygiamat.MOD_ID, "chili_pepper")))
                    .food(new FoodComponent(2, 0.3f, false),
                            ConsumableComponent.builder()
                                    .consumeEffect(new ApplyEffectsConsumeEffect(new StatusEffectInstance(StatusEffects.FIRE_RESISTANCE, 200, 0), 1.0f))
                                    .build()))
    );
    public static final Item SAUSAGE = registerFood("sausage", 6, 0.8f);
    public static final Item SAUSAGE_IN_BUN = registerFood("sausage_in_bun", 9, 0.9f);
    public static final Item HAMBURGER = registerFood("hamburger", 10, 0.9f);
    public static final Item CHICKEN_NUGGETS = registerFood("chicken_nuggets", 6, 0.6f);
    public static final Item CHICKEN_NUGGETS_BREADCRUMBS = registerFood("chicken_nuggets_breadcrumbs", 7, 0.7f);

    // Cookie: dough + chocolate + dough -> unbaked cookie -> (quick bake) -> cookie.
    public static final Item UNBAKED_COOKIE = registerUnbaked("unbaked_cookie", 1);

    // Cake: built from dough (instead of wheat) plus an egg, then baked into a vanilla cake.
    // A sniffer or dragon egg yields a "cursed" cake that poisons and nauseates when eaten.
    public static final Item UNBAKED_CAKE = registerUnbaked("unbaked_cake", 1);
    public static final Item UNBAKED_CAKE_CURSED = registerUnbaked("unbaked_cake_cursed", 1);
    // The cursed cake is placed like a vanilla cake; eating a slice curses you (see CursedCakeBlock).
    public static final Item CURSED_CAKE = registerCursedCake();

    // Special food with effects
    public static final Item GUMMY_SCHNITZEL = Registry.register(
            Registries.ITEM,
            RegistryKey.of(RegistryKeys.ITEM, Identifier.of(FoodBygiamat.MOD_ID, "gummy_schnitzel")),
            new Item(new Item.Settings()
                    .registryKey(RegistryKey.of(RegistryKeys.ITEM, Identifier.of(FoodBygiamat.MOD_ID, "gummy_schnitzel")))
                    .food(new FoodComponent(8, 0.6f, false),
                            ConsumableComponent.builder()
                                    .consumeEffect(new ApplyEffectsConsumeEffect(new StatusEffectInstance(StatusEffects.NAUSEA, 200, 0), 1.0f))
                                    .consumeEffect(new ApplyEffectsConsumeEffect(new StatusEffectInstance(StatusEffects.HUNGER, 200, 0), 1.0f))
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

    private static Item registerUnbaked(String name, int nutrition) {
        RegistryKey<Item> key = RegistryKey.of(RegistryKeys.ITEM, Identifier.of(FoodBygiamat.MOD_ID, name));
        return Registry.register(
                Registries.ITEM,
                key,
                new Item(new Item.Settings()
                        .registryKey(key)
                        .food(new FoodComponent(nutrition, 0.1f, false),
                                ConsumableComponent.builder()
                                        .consumeEffect(new ApplyEffectsConsumeEffect(new StatusEffectInstance(StatusEffects.POISON, 100, 0), 1.0f))
                                        .consumeEffect(new ApplyEffectsConsumeEffect(new StatusEffectInstance(StatusEffects.NAUSEA, 200, 0), 1.0f))
                                        .build()))
        );
    }

    private static Item registerCursedCake() {
        RegistryKey<Item> key = RegistryKey.of(RegistryKeys.ITEM, Identifier.of(FoodBygiamat.MOD_ID, "cursed_cake"));
        return Registry.register(
                Registries.ITEM,
                key,
                new BlockItem(ModBlocks.CURSED_CAKE_BLOCK, new Item.Settings().registryKey(key)));
    }

    public static void initialize() {
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.FOOD_AND_DRINK).register(entries -> {
            entries.addAfter(net.minecraft.item.Items.CAKE, CHOCOLATE);
            entries.addAfter(CHOCOLATE,
                    SHOKO, PITA, SCHNITZEL,
                    GUMMY_CANDY_WATERMELON, GUMMY_CANDY_APPLE, GUMMY_CANDY_SWEET_BERRIES, GUMMY_CANDY_CARROT,
                    GUMMY_SCHNITZEL, BANANA, CHILI_PEPPER,
                    SAUSAGE, SAUSAGE_IN_BUN, HAMBURGER,
                    CHICKEN_NUGGETS, CHICKEN_NUGGETS_BREADCRUMBS,
                    CURSED_CAKE,
                    UNBAKED_BREAD, UNBAKED_PITA, UNBAKED_SCHNITZEL,
                    UNBAKED_SAUSAGE, UNBAKED_CHICKEN_NUGGETS, UNBAKED_CHICKEN_NUGGETS_BREADCRUMBS,
                    UNBAKED_COOKIE, UNBAKED_CAKE, UNBAKED_CAKE_CURSED);
        });
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register(entries -> {
            entries.add(FLOUR);
            entries.add(DOUGH);
            entries.add(SALT);
            entries.add(MELTED_CHOCOLATE);
            entries.add(BREADCRUMBS);
            entries.add(GELATIN);
        });
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.TOOLS).register(entries -> {
            entries.add(STRAINER);
        });
    }
}
