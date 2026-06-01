package com.food.giamat.init;

import com.food.giamat.FoodBygiamat;
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

    public static void initialize() {
        // Registration happens in the static initializer above.
    }
}
