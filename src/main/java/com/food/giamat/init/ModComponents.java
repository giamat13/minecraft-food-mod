package com.food.giamat.init;

import com.food.giamat.FoodBygiamat;
import com.food.giamat.item.CombinedFoodData;
import com.mojang.serialization.Codec;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.Registry;
import net.minecraft.resources.Identifier;

public class ModComponents {

    /**
     * Marker component placed on any food stack that has been salted.
     * It is NOT a separate item - just extra data on the existing food stack.
     */
    public static final DataComponentType<Boolean> SALTED = Registry.register(
            BuiltInRegistries.DATA_COMPONENT_TYPE,
            Identifier.fromNamespaceAndPath(FoodBygiamat.MOD_ID, "salted"),
            DataComponentType.<Boolean>builder()
                    .persistent(Codec.BOOL)
                    .networkSynchronized(ByteBufCodecs.BOOL)
                    .build());

    /**
     * Number of toppings stacked on a pizza. Used to scale how filling a
     * topped pizza is.
     */
    public static final DataComponentType<Integer> PIZZA_TOPPINGS = Registry.register(
            BuiltInRegistries.DATA_COMPONENT_TYPE,
            Identifier.fromNamespaceAndPath(FoodBygiamat.MOD_ID, "pizza_toppings"),
            DataComponentType.<Integer>builder()
                    .persistent(Codec.INT)
                    .networkSynchronized(ByteBufCodecs.INT)
                    .build());

    public static final DataComponentType<CombinedFoodData> COMBINED_FOOD_DATA = Registry.register(
            BuiltInRegistries.DATA_COMPONENT_TYPE,
            Identifier.fromNamespaceAndPath(FoodBygiamat.MOD_ID, "combined_food_data"),
            DataComponentType.<CombinedFoodData>builder()
                    .persistent(CombinedFoodData.CODEC)
                    .build());

    public static void initialize() {
        // Registration happens in the static initializer above.
    }
}
