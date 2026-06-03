package com.food.giamat;

import com.food.giamat.block.FruitBushBlock;
import com.food.giamat.init.ModItems;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.fabricmc.fabric.api.object.builder.v1.trade.TradeOfferHelper;
import net.minecraft.block.Blocks;
import net.minecraft.block.BlockState;
import net.minecraft.block.LeveledCauldronBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
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

        // Right-click a fruit bush with shears: snip the fruit off, leaving an
        // empty bush that grows its fruit back after a while.
        UseBlockCallback.EVENT.register((player, world, hand, hit) -> {
            ItemStack stack = player.getStackInHand(hand);
            if (!stack.isOf(Items.SHEARS)) {
                return ActionResult.PASS;
            }

            BlockPos pos = hit.getBlockPos();
            BlockState state = world.getBlockState(pos);
            if (!(state.getBlock() instanceof FruitBushBlock bush) || state.get(FruitBushBlock.HARVESTED)) {
                return ActionResult.PASS;
            }

            if (world.isClient()) {
                return ActionResult.SUCCESS;
            }

            bush.shearFruit(state, world, pos);
            return ActionResult.SUCCESS_SERVER;
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
