package com.food.giamat.init;

import com.food.giamat.FoodBygiamat;
import com.food.giamat.block.SusCakeBlockItem;
import com.food.giamat.block.SusPizzaBlockItem;
import com.food.giamat.init.ModBlocks;
import com.food.giamat.item.BurntBreadItem;
import com.food.giamat.item.CombinedFoodItem;
import com.food.giamat.item.SpicyRamenItem;
import net.fabricmc.fabric.api.creativetab.v1.CreativeModeTabEvents;
import net.minecraft.world.item.component.Consumable;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.consume_effects.ApplyStatusEffectsConsumeEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;

public class ModItems {

    public static final Item CHOCOLATE = Registry.register(
            BuiltInRegistries.ITEM,
            ResourceKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(FoodBygiamat.MOD_ID, "chocolate")),
            new Item(new Item.Properties()
                    .setId(ResourceKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(FoodBygiamat.MOD_ID, "chocolate")))
                    .food(new FoodProperties(4, 0.3f, false)))
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
    public static final Item GUMMY_CANDY_BANANA = registerFood("gummy_candy_banana", 3, 0.2f);
    public static final Item GUMMY_CANDY_LEMON = registerFood("gummy_candy_lemon", 3, 0.2f);
    public static final Item GUMMY_CANDY_POMEGRANATE = registerFood("gummy_candy_pomegranate", 3, 0.2f);
    public static final Item GUMMY_CANDY_GRAPE = registerFood("gummy_candy_grape", 3, 0.2f);
    public static final Item BANANA = registerFood("banana", 4, 0.3f);
    // Chili pepper: foraged from desert bushes; eating one grants Fire Resistance for 10 s.
    public static final Item CHILI_PEPPER = Registry.register(
            BuiltInRegistries.ITEM,
            ResourceKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(FoodBygiamat.MOD_ID, "chili_pepper")),
            new Item(new Item.Properties()
                    .setId(ResourceKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(FoodBygiamat.MOD_ID, "chili_pepper")))
                    .food(new FoodProperties(2, 0.3f, false),
                            Consumable.builder()
                                    .onConsume(new ApplyStatusEffectsConsumeEffect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 200, 0), 1.0f))
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

    // Tomato: foraged from wild tomato bushes in the plains.
    public static final Item TOMATO = registerFood("tomato", 3, 0.3f);
    // Tomato paste: crushed from a single tomato; used to make pizza.
    public static final Item TOMATO_PASTE = register("tomato_paste");
    // Cheese: pressed from a bucket of milk; pizza topping and a snack on its own.
    public static final Item CHEESE = registerFood("cheese", 4, 0.4f);

    // Pizza: an uncooked pie (dough + tomato paste + cheese) baked in a furnace.
    // Placing the pizza item puts down a pizza block (like cake); it can also be eaten directly.
    public static final Item UNCOOKED_PIZZA = registerUnbaked("uncooked_pizza", 2);
    public static final Item PIZZA = registerPizza();
    // Topped pizza: its food value is set per-stack from the number of toppings.
    public static final Item TOPPED_PIZZA = registerFood("topped_pizza", 10, 0.9f);

    // Corn products: raw corn is grown as a crop (see ModBlocks); cooking it in a
    // furnace makes hot corn, and a smoker pops it into popcorn.
    public static final Item CORN_HOT = registerFood("corn_hot", 5, 0.6f);
    public static final Item POPCORN = registerFood("popcorn", 4, 0.5f);

    // Sushi: rolled from rice, raw fish and kelp.
    public static final Item SUSHI = registerFood("sushi", 6, 0.6f);
    // Super sushi: sushi forged with netherite and dirt. Regeneration II (5s) + Jump Boost II (10min).
    public static final Item SUPER_SUSHI = Registry.register(
            BuiltInRegistries.ITEM,
            ResourceKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(FoodBygiamat.MOD_ID, "super_sushi")),
            new Item(new Item.Properties()
                    .setId(ResourceKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(FoodBygiamat.MOD_ID, "super_sushi")))
                    .food(new FoodProperties(6, 0.6f, false),
                            Consumable.builder()
                                    .onConsume(new ApplyStatusEffectsConsumeEffect(new MobEffectInstance(MobEffects.REGENERATION, 100, 1), 1.0f))
                                    .onConsume(new ApplyStatusEffectsConsumeEffect(new MobEffectInstance(MobEffects.JUMP_BOOST, 12000, 1), 1.0f))
                                    .build()))
    );

    // Chocolate donut: a ring of dough around chocolate, baked in a furnace.
    public static final Item UNCOOKED_CHOCOLATE_DONUT = registerUnbaked("uncooked_chocolate_donut", 1);
    public static final Item CHOCOLATE_DONUT = registerFood("chocolate_donut", 6, 0.7f);

    // End cake: a purple cake; eating a slice teleports you (see EndCakeBlock).
    public static final Item END_CAKE = registerEndCake();

    // Sweet dough: dough kneaded with sugar; the base for challah.
    public static final Item SWEET_DOUGH = register("sweet_dough");
    // Challah: braided sweet bread. Made like bread but from sweet dough.
    // The unbaked loaf is edible with no ill effects (like unbaked bread).
    public static final Item UNBAKED_CHALLAH = registerFood("unbaked_challah", 1, 0.1f);
    public static final Item CHALLAH = registerFood("challah", 6, 0.6f);

    // Citrus / orchard fruits grown on trees (lemon tree, pomegranate tree).
    public static final Item LEMON = registerFood("lemon", 2, 0.2f);
    public static final Item POMEGRANATE = registerFood("pomegranate", 5, 0.5f);

    // Popsicles: stick + ice + fruit (shaped, vertical column) = flavoured popsicle.
    public static final Item BANANA_POPSICLE = registerFood("banana_popsicle", 4, 0.4f);
    public static final Item LEMON_POPSICLE = registerFood("lemon_popsicle", 4, 0.4f);
    public static final Item POMEGRANATE_POPSICLE = registerFood("pomegranate_popsicle", 4, 0.4f);
    public static final Item GRAPE_POPSICLE = registerFood("grape_popsicle", 3, 0.3f);
    public static final Item APPLE_POPSICLE = registerFood("apple_popsicle", 4, 0.4f);
    public static final Item MELON_POPSICLE = registerFood("melon_popsicle", 3, 0.2f);
    public static final Item SWEET_BERRY_POPSICLE = registerFood("sweet_berry_popsicle", 3, 0.3f);
    // Glow berry popsicle grants Night Vision briefly (like vanilla glow berries).
    public static final Item GLOW_BERRY_POPSICLE = Registry.register(
            BuiltInRegistries.ITEM,
            ResourceKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(FoodBygiamat.MOD_ID, "glow_berry_popsicle")),
            new Item(new Item.Properties()
                    .setId(ResourceKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(FoodBygiamat.MOD_ID, "glow_berry_popsicle")))
                    .food(new FoodProperties(3, 0.3f, false),
                            Consumable.builder()
                                    .onConsume(new ApplyStatusEffectsConsumeEffect(
                                            new MobEffectInstance(MobEffects.NIGHT_VISION, 100, 0), 1.0f))
                                    .build()))
    );

    // Burnt bread: overcooking regular bread scorches it. Gives 1 hunger but damages the eater.
    public static final Item BURNT_BREAD = registerBurntBread();

    // Olive: harvested from olive trees. Used to craft olive oil.
    public static final Item OLIVE = registerFood("olive", 2, 0.2f);
    // Olive oil: pressed from 4 olives. Ingredient for cream.
    public static final Item OLIVE_OIL = register("olive_oil");
    // Cream: olive oil mixed with milk. Used to make butter.
    public static final Item CREAM = register("cream");
    // Butter: churned from 4 portions of cream. Used with bread.
    public static final Item BUTTER = register("butter");
    // Buttered bread: bread spread with butter. Grants Regeneration I for 5 seconds.
    public static final Item BUTTERED_BREAD = Registry.register(
            BuiltInRegistries.ITEM,
            ResourceKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(FoodBygiamat.MOD_ID, "buttered_bread")),
            new Item(new Item.Properties()
                    .setId(ResourceKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(FoodBygiamat.MOD_ID, "buttered_bread")))
                    .food(new FoodProperties(5, 0.8f, false),
                            Consumable.builder()
                                    .onConsume(new ApplyStatusEffectsConsumeEffect(
                                            new MobEffectInstance(MobEffects.REGENERATION, 100, 0), 1.0f))
                                    .build()))
    );
    // Salted buttered bread: buttered bread with salt. +3 extra hunger and Regeneration I.
    public static final Item SALTED_BUTTERED_BREAD = Registry.register(
            BuiltInRegistries.ITEM,
            ResourceKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(FoodBygiamat.MOD_ID, "salted_buttered_bread")),
            new Item(new Item.Properties()
                    .setId(ResourceKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(FoodBygiamat.MOD_ID, "salted_buttered_bread")))
                    .food(new FoodProperties(8, 0.8f, false),
                            Consumable.builder()
                                    .onConsume(new ApplyStatusEffectsConsumeEffect(
                                            new MobEffectInstance(MobEffects.REGENERATION, 100, 0), 1.0f))
                                    .build()))
    );

    // Pasta: dough shaped into noodles, then cooked in a furnace.
    // Uncooked pasta causes poison and nausea; cooked pasta is safe to eat.
    public static final Item UNCOOKED_PASTA = registerUnbaked("uncooked_pasta", 1);
    public static final Item PASTA = registerFood("pasta", 5, 0.6f);
    // Tomato sauce: two tomato pastes combined with a bowl.
    public static final Item TOMATO_SAUCE = register("tomato_sauce");
    // Pasta in tomato sauce: pasta combined with tomato sauce.
    public static final Item PASTA_IN_TOMATO_SAUCE = registerFood("pasta_in_tomato_sauce", 8, 0.8f);
    // Chips: one baked potato in the crafting grid yields 2 chips.
    public static final Item CHIPS = registerFood("chips", 4, 0.5f);

    // Ramen: pasta + any meat + any soup = a hearty noodle bowl.
    public static final Item RAMEN = registerFood("ramen", 10, 0.9f);
    // Spicy ramen: ramen + chili pepper = shoots a small fireball when eaten.
    public static final Item SPICY_RAMEN = registerSpicyRamen();

    // Combined food: any 2+ food items in a crafting grid → merged meal.
    // Takes 2× as long to eat; applies all individual effects; nausea if > 6 foods.
    public static final Item COMBINED_FOOD = registerCombinedFood();

    // Sus cake / sus pizza: food + suspicious stew → infused with the stew's effect.
    public static final Item SUS_CAKE = registerSusCake();
    public static final Item SUS_PIZZA = registerSusPizza();

    // Grape: foraged from wild grape bushes in the plains; also pressed into wine.
    public static final Item GRAPE = registerFood("grape", 2, 0.1f);
    // Wine: grapes pressed with sugar and water. Drinkable any time, but the
    // alcohol leaves you dizzy (Nausea for 3 seconds).
    public static final Item WINE = Registry.register(
            BuiltInRegistries.ITEM,
            ResourceKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(FoodBygiamat.MOD_ID, "wine")),
            new Item(new Item.Properties()
                    .setId(ResourceKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(FoodBygiamat.MOD_ID, "wine")))
                    .stacksTo(16)
                    .food(new FoodProperties(1, 0.1f, true),
                            Consumable.builder()
                                    .onConsume(new ApplyStatusEffectsConsumeEffect(new MobEffectInstance(MobEffects.NAUSEA, 60, 0), 1.0f))
                                    .build()))
    );

    // Special food with effects
    public static final Item GUMMY_SCHNITZEL = Registry.register(
            BuiltInRegistries.ITEM,
            ResourceKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(FoodBygiamat.MOD_ID, "gummy_schnitzel")),
            new Item(new Item.Properties()
                    .setId(ResourceKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(FoodBygiamat.MOD_ID, "gummy_schnitzel")))
                    .food(new FoodProperties(8, 0.6f, false),
                            Consumable.builder()
                                    .onConsume(new ApplyStatusEffectsConsumeEffect(new MobEffectInstance(MobEffects.NAUSEA, 200, 0), 1.0f))
                                    .onConsume(new ApplyStatusEffectsConsumeEffect(new MobEffectInstance(MobEffects.HUNGER, 200, 0), 1.0f))
                                    .build()))
    );

    private static Item register(String name) {
        ResourceKey<Item> key = ResourceKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(FoodBygiamat.MOD_ID, name));
        return Registry.register(
                BuiltInRegistries.ITEM,
                key,
                new Item(new Item.Properties().setId(key)));
    }

    private static Item registerCombinedFood() {
        ResourceKey<Item> key = ResourceKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(FoodBygiamat.MOD_ID, "combined_food"));
        Item item = new CombinedFoodItem(new Item.Properties()
                .setId(key)
                .food(new FoodProperties(0, 0f, false),
                        Consumable.builder().build()));
        return Registry.register(BuiltInRegistries.ITEM, key, item);
    }

    private static Item registerBurntBread() {
        ResourceKey<Item> key = ResourceKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(FoodBygiamat.MOD_ID, "burnt_bread"));
        return Registry.register(
                BuiltInRegistries.ITEM,
                key,
                new BurntBreadItem(new Item.Properties()
                        .setId(key)
                        .food(new FoodProperties(1, 0.1f, false)))
        );
    }

    private static Item registerFood(String name, int nutrition, float saturation) {
        ResourceKey<Item> key = ResourceKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(FoodBygiamat.MOD_ID, name));
        return Registry.register(
                BuiltInRegistries.ITEM,
                key,
                new Item(new Item.Properties()
                        .setId(key)
                        .food(new FoodProperties.Builder()
                                .nutrition(nutrition)
                                .saturationModifier(saturation)
                                .build()))
        );
    }

    private static Item registerUnbaked(String name, int nutrition) {
        ResourceKey<Item> key = ResourceKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(FoodBygiamat.MOD_ID, name));
        return Registry.register(
                BuiltInRegistries.ITEM,
                key,
                new Item(new Item.Properties()
                        .setId(key)
                        .food(new FoodProperties(nutrition, 0.1f, false),
                                Consumable.builder()
                                        .onConsume(new ApplyStatusEffectsConsumeEffect(new MobEffectInstance(MobEffects.POISON, 100, 0), 1.0f))
                                        .onConsume(new ApplyStatusEffectsConsumeEffect(new MobEffectInstance(MobEffects.NAUSEA, 200, 0), 1.0f))
                                        .build()))
        );
    }

    private static Item registerSusCake() {
        ResourceKey<Item> key = ResourceKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(FoodBygiamat.MOD_ID, "sus_cake"));
        return Registry.register(
                BuiltInRegistries.ITEM,
                key,
                new SusCakeBlockItem(ModBlocks.SUS_CAKE_BLOCK, new Item.Properties().setId(key)));
    }

    private static Item registerSusPizza() {
        ResourceKey<Item> key = ResourceKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(FoodBygiamat.MOD_ID, "sus_pizza"));
        return Registry.register(
                BuiltInRegistries.ITEM,
                key,
                new SusPizzaBlockItem(ModBlocks.SUS_PIZZA_BLOCK, new Item.Properties().setId(key)));
    }

    private static Item registerPizza() {
        ResourceKey<Item> key = ResourceKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(FoodBygiamat.MOD_ID, "pizza"));
        return Registry.register(
                BuiltInRegistries.ITEM,
                key,
                new BlockItem(ModBlocks.PIZZA_BLOCK, new Item.Properties()
                        .setId(key)
                        .food(new FoodProperties.Builder().nutrition(8).saturationModifier(0.8f).build()))
        );
    }

    private static Item registerCursedCake() {
        ResourceKey<Item> key = ResourceKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(FoodBygiamat.MOD_ID, "cursed_cake"));
        return Registry.register(
                BuiltInRegistries.ITEM,
                key,
                new BlockItem(ModBlocks.CURSED_CAKE_BLOCK, new Item.Properties().setId(key)));
    }

    private static Item registerSpicyRamen() {
        ResourceKey<Item> key = ResourceKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(FoodBygiamat.MOD_ID, "spicy_ramen"));
        return Registry.register(
                BuiltInRegistries.ITEM,
                key,
                new SpicyRamenItem(new Item.Properties()
                        .setId(key)
                        .food(new FoodProperties.Builder()
                                .nutrition(10)
                                .saturationModifier(0.9f)
                                .build()))
        );
    }

    private static Item registerEndCake() {
        ResourceKey<Item> key = ResourceKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(FoodBygiamat.MOD_ID, "end_cake"));
        return Registry.register(
                BuiltInRegistries.ITEM,
                key,
                new BlockItem(ModBlocks.END_CAKE_BLOCK, new Item.Properties().setId(key)));
    }

    private static ResourceKey<net.minecraft.world.item.CreativeModeTab> tab(String path) {
        return ResourceKey.create(Registries.CREATIVE_MODE_TAB,
                Identifier.fromNamespaceAndPath("minecraft", path));
    }

    public static void initialize() {
        CreativeModeTabEvents.modifyOutputEvent(tab("food_and_drinks")).register(output -> {
            output.insertAfter(net.minecraft.world.item.Items.CAKE, CHOCOLATE);
            output.insertAfter(CHOCOLATE,
                    COMBINED_FOOD,
                    SHOKO, PITA, SCHNITZEL,
                    GUMMY_CANDY_WATERMELON, GUMMY_CANDY_APPLE, GUMMY_CANDY_SWEET_BERRIES, GUMMY_CANDY_CARROT,
                    GUMMY_CANDY_BANANA, GUMMY_CANDY_LEMON, GUMMY_CANDY_POMEGRANATE, GUMMY_CANDY_GRAPE,
                    GUMMY_SCHNITZEL, BANANA, LEMON, POMEGRANATE, OLIVE, CHILI_PEPPER, TOMATO,
                    CHEESE, PIZZA, TOPPED_PIZZA, SUS_PIZZA,
                    CORN_HOT, POPCORN, CHIPS, SUSHI, SUPER_SUSHI,
                    PASTA_IN_TOMATO_SAUCE, RAMEN, SPICY_RAMEN,
                    CHOCOLATE_DONUT, END_CAKE,
                    CHALLAH, GRAPE, WINE,
                    SAUSAGE, SAUSAGE_IN_BUN, HAMBURGER,
                    CHICKEN_NUGGETS, CHICKEN_NUGGETS_BREADCRUMBS,
                    BUTTERED_BREAD, SALTED_BUTTERED_BREAD, BURNT_BREAD,
                    CURSED_CAKE, SUS_CAKE,
                    BANANA_POPSICLE, LEMON_POPSICLE, POMEGRANATE_POPSICLE,
                    GRAPE_POPSICLE, APPLE_POPSICLE,
                    MELON_POPSICLE, SWEET_BERRY_POPSICLE, GLOW_BERRY_POPSICLE,
                    UNBAKED_BREAD, UNBAKED_PITA, UNBAKED_SCHNITZEL,
                    UNBAKED_SAUSAGE, UNBAKED_CHICKEN_NUGGETS, UNBAKED_CHICKEN_NUGGETS_BREADCRUMBS,
                    UNBAKED_COOKIE, UNBAKED_CAKE, UNBAKED_CAKE_CURSED,
                    UNCOOKED_PIZZA, UNCOOKED_CHOCOLATE_DONUT, UNBAKED_CHALLAH,
                    UNCOOKED_PASTA, PASTA);
        });
        CreativeModeTabEvents.modifyOutputEvent(tab("ingredients")).register(output -> {
            output.accept(FLOUR);
            output.accept(DOUGH);
            output.accept(SALT);
            output.accept(MELTED_CHOCOLATE);
            output.accept(BREADCRUMBS);
            output.accept(GELATIN);
            output.accept(TOMATO_PASTE);
            output.accept(TOMATO_SAUCE);
            output.accept(SWEET_DOUGH);
            output.accept(OLIVE_OIL);
            output.accept(CREAM);
            output.accept(BUTTER);
        });
        CreativeModeTabEvents.modifyOutputEvent(tab("tools_and_utilities")).register(output -> {
            output.accept(STRAINER);
            output.accept(ModBlocks.TRAY_BLOCK.asItem());
        });
    }
}
