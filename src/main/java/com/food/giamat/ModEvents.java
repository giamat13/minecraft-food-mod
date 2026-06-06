package com.food.giamat;

import com.food.giamat.block.FruitBushBlock;
import com.food.giamat.init.ModItems;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.fabricmc.fabric.api.loot.v3.LootTableEvents;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.LayeredCauldronBlock;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.sounds.SoundSource;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionResult;
import net.minecraft.core.BlockPos;

public class ModEvents {

    public static void initialize() {
        // Right-click a water cauldron with the strainer: skim a little water off and get salt.
        UseBlockCallback.EVENT.register((player, world, hand, hit) -> {
            ItemStack stack = player.getItemInHand(hand);
            if (stack.getItem() != ModItems.STRAINER) {
                return InteractionResult.PASS;
            }

            BlockPos pos = hit.getBlockPos();
            BlockState state = world.getBlockState(pos);
            if (!state.is(Blocks.WATER_CAULDRON)) {
                return InteractionResult.PASS;
            }

            if (world.isClientSide()) {
                return InteractionResult.SUCCESS;
            }

            LayeredCauldronBlock.lowerFillLevel(state, world, pos);

            ItemStack salt = new ItemStack(ModItems.SALT);
            if (!player.addItem(salt)) {
                player.drop(salt, false);
            }

            world.playSound(null, pos, SoundEvents.BUCKET_FILL, SoundSource.BLOCKS, 0.8f, 1.2f);
            return InteractionResult.SUCCESS;
        });

        // Right-click a fruit bush with shears: snip the fruit off, leaving an
        // empty bush that grows its fruit back after a while.
        UseBlockCallback.EVENT.register((player, world, hand, hit) -> {
            ItemStack stack = player.getItemInHand(hand);
            if (stack.getItem() != Items.SHEARS) {
                return InteractionResult.PASS;
            }

            BlockPos pos = hit.getBlockPos();
            BlockState state = world.getBlockState(pos);
            if (!(state.getBlock() instanceof FruitBushBlock bush) || state.getValue(FruitBushBlock.HARVESTED)) {
                return InteractionResult.PASS;
            }

            if (world.isClientSide()) {
                return InteractionResult.SUCCESS;
            }

            bush.shearFruit(state, world, pos);
            return InteractionResult.SUCCESS;
        });

        // Inject burnt bread into shipwreck supply, desert pyramid, and end city loot tables.
        LootTableEvents.MODIFY.register((key, tableBuilder, source, registries) -> {
            if (!source.isBuiltin()) return;
            Identifier id = key.identifier();
            if (id.equals(Identifier.withDefaultNamespace("chests/shipwreck_supply"))) {
                tableBuilder.withPool(LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1.0f))
                        .add(LootItem.lootTableItem(ModItems.BURNT_BREAD).setWeight(5)));
            } else if (id.equals(Identifier.withDefaultNamespace("chests/desert_pyramid"))) {
                tableBuilder.withPool(LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1.0f))
                        .add(LootItem.lootTableItem(ModItems.BURNT_BREAD).setWeight(3)));
            } else if (id.equals(Identifier.withDefaultNamespace("chests/end_city_treasure"))) {
                tableBuilder.withPool(LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1.0f))
                        .add(LootItem.lootTableItem(ModItems.END_CAKE).setWeight(10)));
            }
        });
    }
}
