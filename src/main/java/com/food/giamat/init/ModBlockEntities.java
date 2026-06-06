package com.food.giamat.init;

import com.food.giamat.FoodBygiamat;
import com.food.giamat.block.entity.SusEffectsBlockEntity;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.Registry;
import net.minecraft.resources.Identifier;

public class ModBlockEntities {

    public static final BlockEntityType<SusEffectsBlockEntity> SUS_EFFECTS = Registry.register(
            BuiltInRegistries.BLOCK_ENTITY_TYPE,
            Identifier.fromNamespaceAndPath(FoodBygiamat.MOD_ID, "sus_effects"),
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
