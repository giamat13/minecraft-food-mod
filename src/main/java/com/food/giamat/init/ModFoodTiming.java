package com.food.giamat.init;

import net.fabricmc.fabric.api.item.v1.DefaultItemComponentEvents;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.component.Consumable;

// Scales how long a food takes to eat with how filling it is, so a cookie is a
// quicker bite than a full loaf of bread. Only nudges items still at vanilla's
// default consume time, so foods with an intentionally different eat speed
// (e.g. dried kelp's quick snack) are left alone. Applies to every item with a
// FOOD component, vanilla or modded, since both go through the same component.
public class ModFoodTiming {

    private static final float MIN_SECONDS = 0.5f;
    private static final float MAX_SECONDS = 2.2f;
    private static final int BASELINE_NUTRITION = 5;

    public static void initialize() {
        DefaultItemComponentEvents.MODIFY.register(context -> context.modify(
                ModFoodTiming::hasDefaultConsumeTime,
                (builder, item) -> {
                    FoodProperties food = item.components().get(DataComponents.FOOD);
                    Consumable consumable = item.components().get(DataComponents.CONSUMABLE);
                    float seconds = scaledSeconds(food.nutrition());
                    builder.set(DataComponents.CONSUMABLE, new Consumable(
                            seconds,
                            consumable.animation(),
                            consumable.sound(),
                            consumable.hasConsumeParticles(),
                            consumable.onConsumeEffects()));
                }));
    }

    private static boolean hasDefaultConsumeTime(Item item) {
        DataComponentMap components = item.components();
        FoodProperties food = components.get(DataComponents.FOOD);
        Consumable consumable = components.get(DataComponents.CONSUMABLE);
        return food != null && consumable != null
                && consumable.consumeSeconds() == Consumable.DEFAULT_CONSUME_SECONDS;
    }

    public static float scaledSeconds(int nutrition) {
        // Ratio-based (not a flat offset) so small snacks shrink noticeably
        // (cookie, nutrition 2: ~1s) while hearty meals only stretch modestly
        // (steak, nutrition 8: ~2s) around the vanilla baseline of 5 nutrition.
        float ratio = (float) Math.sqrt(Math.max(nutrition, 0) / (double) BASELINE_NUTRITION);
        float seconds = Consumable.DEFAULT_CONSUME_SECONDS * ratio;
        return Math.max(MIN_SECONDS, Math.min(MAX_SECONDS, seconds));
    }
}
