package com.food.giamat;

import com.food.giamat.init.ModItems;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.fabricmc.fabric.api.event.player.UseItemCallback;
import net.fabricmc.fabric.api.object.builder.v1.trade.TradeOfferHelper;
import net.minecraft.block.Blocks;
import net.minecraft.block.BlockState;
import net.minecraft.block.LeveledCauldronBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.village.TradeOffer;
import net.minecraft.village.TradedItem;
import net.minecraft.village.VillagerProfession;
import java.util.Optional;

public class ModEvents {

    public static void initialize() {
        // Right-click a water cauldron with the strainer: skim a little water off and get salt.
        UseBlockCallback.EVENT.register((player, world, hand, hit) -> {
            ItemStack stack = player.getStackInHand(hand);
            if (!stack.isOf(ModItems.STRAINER)) {
                return ActionResult.PASS;
            }

            BlockPos pos = hit.getBlockPos();
            BlockState state = world.getBlockState(pos);
            if (!state.isOf(Blocks.WATER_CAULDRON)) {
                return ActionResult.PASS;
            }

            if (world.isClient()) {
                return ActionResult.SUCCESS;
            }

            // Lower the water level a little (empties the cauldron once it reaches the bottom).
            LeveledCauldronBlock.decrementFluidLevel(state, world, pos);

            ItemStack salt = new ItemStack(ModItems.SALT);
            if (!player.giveItemStack(salt)) {
                player.dropItem(salt, false);
            }

            world.playSound(null, pos, SoundEvents.ITEM_BUCKET_FILL, SoundCategory.BLOCKS, 0.8f, 1.2f);
            return ActionResult.SUCCESS_SERVER;
        });

        // Eggs: aiming at the air eats the egg as a small snack; aiming at a block lets it
        // be thrown the normal way.
        UseItemCallback.EVENT.register((player, world, hand) -> {
            ItemStack stack = player.getStackInHand(hand);
            if (!stack.isOf(Items.EGG)) {
                return ActionResult.PASS;
            }

            // If the player is looking at a block, fall through to the vanilla throw behaviour.
            HitResult hit = player.raycast(4.5, 1.0f, false);
            if (hit.getType() == HitResult.Type.BLOCK) {
                return ActionResult.PASS;
            }

            // Nothing to do if the player is not hungry.
            if (!player.canConsume(false)) {
                return ActionResult.PASS;
            }

            if (!world.isClient()) {
                player.getHungerManager().add(2, 0.3f);
                world.playSound(null, player.getX(), player.getY(), player.getZ(),
                        SoundEvents.ENTITY_GENERIC_EAT, SoundCategory.PLAYERS, 1.0f, 1.0f);
            }
            stack.decrementUnlessCreative(1, player);
            return ActionResult.SUCCESS;
        });

        // Butcher villager trades
        TradeOfferHelper.registerVillagerOffers(VillagerProfession.BUTCHER, 1, factories -> {
            factories.add((world, entity, random) -> new TradeOffer(
                    new TradedItem(Items.EMERALD, 2),
                    Optional.empty(),
                    new ItemStack(ModItems.SAUSAGE, 5),
                    12, 5, 0.05f
            ));
            factories.add((world, entity, random) -> new TradeOffer(
                    new TradedItem(Items.EMERALD, 2),
                    Optional.empty(),
                    new ItemStack(ModItems.HAMBURGER, 1),
                    12, 5, 0.05f
            ));
        });
    }
}
