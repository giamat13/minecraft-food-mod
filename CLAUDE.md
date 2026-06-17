# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## What this is

A Fabric mod for Minecraft ("Food by giamat") that adds 80+ foods, crops, a multi-step
cooking chain, custom blocks, and worldgen. Mod id: `food-by-giamat`, base package
`com.food.giamat`, Java 21+ source compiled at release 25.

## Build & run

Use the Gradle wrapper (`./gradlew` on POSIX, `gradlew.bat` / `./gradlew.bat` on Windows):

- `./gradlew build` — compile + produce the mod jar (in `build/libs/`).
- `./gradlew runClient` — launch a dev Minecraft client with the mod loaded. On success the
  log prints `Food By Giamat loaded!`.
- `./gradlew runServer` — launch a dev dedicated server.
- `./gradlew genSources` — decompile Minecraft to read mapped vanilla sources (invaluable when
  porting against official mappings).

There is no test suite, linter, or CI. "Verifying a change" means it compiles and behaves
correctly in `runClient`. Build/version knobs live in `gradle.properties` (Minecraft, loader,
loom, Fabric API, and `mod_version`); never hardcode versions in `build.gradle`.

> Version note: `gradle.properties` / `fabric.mod.json` are the source of truth for the target
> Minecraft version, and they are currently ahead of `README.md` (a 26.1.2 → 26.2 bump is in
> progress). Trust the gradle files, not the README, for the active target.

## Mappings & API caveats (read before editing Java)

Minecraft 26.1+ is **unobfuscated** — code compiles against **official Mojang mappings**, not
Yarn. If you know older Fabric/Yarn names, they are wrong here. Common renames you'll hit:
`Identifier.fromNamespaceAndPath` (not `Identifier.of`); `BuiltInRegistries` for registry
instances and `core.registries.Registries` for registry keys; `ResourceKey`/`ResourceLocation`;
`Item.Properties.setId/.stacksTo/.food`; `state.getValue/setValue`; `level.isClientSide()` is a
method. When in doubt, run `genSources` and read the actual mapped class rather than guessing.

Two features are currently **blocked** by removed/redesigned APIs and may be stubbed or absent:
Fabric villager `TradeOfferHelper` (gone) and the salt-overlay client mixin (GUI render pipeline
redesign). Check `ModVillagers` / `SaltedItemOverlayMixin` before assuming they work.

## Architecture

**Entrypoints** (declared in `fabric.mod.json`):
- `FoodBygiamat` (main) — `onInitialize()` calls each registry holder's `initialize()` in order:
  Components → Items → Blocks → BlockEntities → Recipes → Events → Villagers → WorldGen.
- `FoodBygiamatClient` (client) — currently a near-empty stub for client-only rendering setup.

**Registration pattern.** All content is registered via static fields in `init/Mod*.java`
holder classes (`ModItems`, `ModBlocks`, `ModBlockEntities`, `ModComponents`, `ModVillagers`)
and `recipe/ModRecipes`. Registration runs in the static initializers; the `initialize()`
methods are empty and exist only to force class loading from `FoodBygiamat`. To add content,
add a `Registry.register(...)` static field to the relevant holder — `ModItems` has helpers
(`register`, `registerFood`, `registerUnbaked`) for the common cases.

**Items.** Most foods are plain `Item`s with `FoodProperties`. Items needing custom consume
behavior get their own class in `item/` (e.g. `BurntBreadItem`, `CombinedFoodItem`,
`SpicyRamenItem`). Item-as-data: the "salted" state and pizza topping count are stored as
**data components** (`ModComponents`), not separate items.

**Data components** (`init/ModComponents`) attach persistent + network-synced data to stacks:
`SALTED` (marker), `PIZZA_TOPPINGS` (int), `COMBINED_FOOD_DATA` (record with effect list, in
`item/CombinedFoodData`). Use `.persistent(Codec)` + `.networkSynchronized(StreamCodec)`.

**Recipes** (`recipe/`). The cooking chain is implemented as custom crafting recipes, each a
`CustomRecipe`/`MAP_CODEC`/`STREAM_CODEC` triple registered in `ModRecipes`. `CombinedFoodRecipe`
is the notable one: drop 2+ food stacks in the grid and it sums nutrition and merges every food's
`ApplyStatusEffectsConsumeEffect` into one `COMBINED_FOOD` stack via the data component. JSON
recipes that use these serializers live in `data/food-by-giamat/recipe/`.

**Blocks** (`block/`). Crops/bushes (`CornBlock`, `RiceBlock`, `FruitBushBlock` and subclasses,
`*BushBlock`), orchard leaves (`*LeavesBlock`), and edible/placed-food blocks
(`CakeOnTrayBlock`, `PizzaBlock`, `EndCake`, `CursedCake`, `SusCake`/`SusPizza`). The "sus"
blocks carry a `SusEffectsBlockEntity` (`block/entity/`, registered in `ModBlockEntities`) that
stores the infused Suspicious Stew effect; their `BlockItem`s are custom (`SusCakeBlockItem`,
`SusPizzaBlockItem`).

**Events** (`ModEvents`). Gameplay wiring via Fabric callbacks: `UseBlockCallback` (strainer on a
water cauldron → salt; shears on a fruit bush → harvest) and `LootTableEvents.MODIFY` (inject
burnt bread / end cake into vanilla chest loot).

**Worldgen** (`worldgen/ModWorldGen` + `data/.../worldgen/`). Crops and trees spawn via
configured/placed features defined as datapack JSON. Per the migration notes, the
`minecraft:random_patch` feature was removed in 26.1; bush/crop scatter is now done with a
`simple_block` configured feature plus `count` + `random_offset` + `block_predicate_filter`
placement modifiers. Trees use `minecraft:tree`.

**Mixins.** `food-by-giamat.mixins.json` (common: `SusStewConsumeMixin`) and
`food-by-giamat.client.mixins.json` (client: `SaltedItemOverlayMixin`). Both at
`compatibilityLevel: JAVA_25`. New mixins must be listed in the matching config or they won't load.

**Resources.** Assets (models, textures, blockstates, lang, item models) under
`src/main/resources/assets/food-by-giamat/`; data (recipes, loot tables, advancements, worldgen,
structures) under `src/main/resources/data/`. Client-only source lives in the separate `src/client`
source set (Loom `splitEnvironmentSourceSets()`); keep client-only code out of `src/main`.
