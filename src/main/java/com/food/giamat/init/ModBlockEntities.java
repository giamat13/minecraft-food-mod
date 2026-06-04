package com.food.giamat.init;

import com.food.giamat.FoodBygiamat;
import com.food.giamat.block.entity.SusEffectsBlockEntity;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModBlockEntities {

    public static final BlockEntityType<SusEffectsBlockEntity> SUS_EFFECTS = Registry.register(
            Registries.BLOCK_ENTITY_TYPE,
            Identifier.of(FoodBygiamat.MOD_ID, "sus_effects"),
            FabricBlockEntityTypeBuilder.create(
                    SusEffectsBlockEntity::new,
                    ModBlocks.SUS_CAKE_BLOCK,
                    ModBlocks.SUS_PIZZA_BLOCK
            ).build()
    );

    public static void initialize() {
        // Registration happens in the static initializer above.
    }
}
