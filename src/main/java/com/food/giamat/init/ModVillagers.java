package com.food.giamat.init;

public class ModVillagers {

    public static void initialize() {
        // Villager trades are fully data-driven in MC 26.x — no Java registration needed.
        // Mod trades live as datapack JSON:
        //   data/food-by-giamat/villager_trade/<profession>/<level>/<name>.json  (VillagerTrade entries)
        //   data/minecraft/tags/villager_trade/<profession>/level_<n>.json       (additive tags that
        //   merge into the vanilla level trade set, giving each villager a chance to offer the trade)
    }
}
