package com.food.giamat.init;

import net.fabricmc.fabric.api.object.builder.v1.trade.TradeOfferHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.village.TradeOffer;
import net.minecraft.village.TradedItem;
import net.minecraft.village.VillagerProfession;

import java.util.Optional;

public class ModVillagers {

    public static void initialize() {
        // Cleric level 1: buy wheat → emerald, sell challah.
        TradeOfferHelper.registerVillagerOffers(VillagerProfession.CLERIC, 1, factories -> {
            factories.add((world, entity, random) -> new TradeOffer(
                    new TradedItem(Items.WHEAT, 18),
                    Optional.empty(),
                    new ItemStack(Items.EMERALD, 1),
                    16, 2, 0.05f
            ));
            factories.add((world, entity, random) -> new TradeOffer(
                    new TradedItem(Items.EMERALD, 1),
                    Optional.empty(),
                    new ItemStack(ModItems.CHALLAH, 2),
                    12, 5, 0.05f
            ));
        });

        // Cleric level 2: sell wine.
        TradeOfferHelper.registerVillagerOffers(VillagerProfession.CLERIC, 2, factories -> {
            factories.add((world, entity, random) -> new TradeOffer(
                    new TradedItem(Items.EMERALD, 3),
                    Optional.empty(),
                    new ItemStack(ModItems.WINE, 1),
                    8, 10, 0.05f
            ));
        });

        // Wandering trader: sell challah and wine via the common-items pool.
        TradeOfferHelper.registerWanderingTraderOffers(builder ->
            builder.addOffersToPool(
                    TradeOfferHelper.WanderingTraderOffersBuilder.SELL_COMMON_ITEMS_POOL,
                    (world, entity, random) -> new TradeOffer(
                            new TradedItem(Items.EMERALD, 1),
                            Optional.empty(),
                            new ItemStack(ModItems.CHALLAH, 2),
                            5, 5, 0.05f
                    ),
                    (world, entity, random) -> new TradeOffer(
                            new TradedItem(Items.EMERALD, 3),
                            Optional.empty(),
                            new ItemStack(ModItems.WINE, 1),
                            3, 10, 0.05f
                    )
            )
        );
    }
}
