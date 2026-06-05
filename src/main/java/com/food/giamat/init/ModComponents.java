package com.food.giamat.init;

import com.food.giamat.FoodBygiamat;
import com.food.giamat.item.CombinedFoodData;
import com.mojang.serialization.Codec;
import net.minecraft.component.ComponentType;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModComponents {

    /**
     * Marker component placed on any food stack that has been salted.
     * It is NOT a separate item - just extra data on the existing food stack.
     */
    public static final ComponentType<Boolean> SALTED = Registry.register(
            Registries.DATA_COMPONENT_TYPE,
            Identifier.of(FoodBygiamat.MOD_ID, "salted"),
            ComponentType.<Boolean>builder()
                    .codec(Codec.BOOL)
                    .packetCodec(PacketCodecs.BOOLEAN)
                    .build());

    /**
     * Number of toppings stacked on a pizza. Used to scale how filling a
     * topped pizza is.
     */
    public static final ComponentType<Integer> PIZZA_TOPPINGS = Registry.register(
            Registries.DATA_COMPONENT_TYPE,
            Identifier.of(FoodBygiamat.MOD_ID, "pizza_toppings"),
            ComponentType.<Integer>builder()
                    .codec(Codec.INT)
                    .packetCodec(PacketCodecs.INTEGER)
                    .build());

    public static final ComponentType<CombinedFoodData> COMBINED_FOOD_DATA = Registry.register(
            Registries.DATA_COMPONENT_TYPE,
            Identifier.of(FoodBygiamat.MOD_ID, "combined_food_data"),
            ComponentType.<CombinedFoodData>builder()
                    .codec(CombinedFoodData.CODEC)
                    .build());

    public static void initialize() {
        // Registration happens in the static initializer above.
    }
}
