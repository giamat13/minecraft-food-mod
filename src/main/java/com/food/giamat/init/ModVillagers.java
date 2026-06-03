package com.food.giamat.init;

import com.food.giamat.FoodBygiamat;
import com.google.common.collect.ImmutableSet;
import net.fabricmc.fabric.api.object.builder.v1.trade.TradeOfferHelper;
import net.fabricmc.fabric.api.object.builder.v1.world.poi.PointOfInterestHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.village.TradeOffer;
import net.minecraft.village.TradedItem;
import net.minecraft.village.VillagerProfession;
import net.minecraft.world.poi.PointOfInterestType;

import java.util.Optional;

/**
 * Registers the "rabbi" villager: a religious villager whose workstation is the
 * Torah stand. Rabbis sell challah and wine. Placing a Torah stand next to an
 * unemployed villager turns them into a rabbi.
 */
public class ModVillagers {

    private static final Identifier TORAH_STAND_ID = Identifier.of(FoodBygiamat.MOD_ID, "torah_stand");
    private static final Identifier RABBI_ID = Identifier.of(FoodBygiamat.MOD_ID, "rabbi");

    public static final RegistryKey<PointOfInterestType> TORAH_STAND_POI =
            RegistryKey.of(RegistryKeys.POINT_OF_INTEREST_TYPE, TORAH_STAND_ID);

    public static final RegistryKey<VillagerProfession> RABBI =
            RegistryKey.of(RegistryKeys.VILLAGER_PROFESSION, RABBI_ID);

    public static void initialize() {
        // The Torah stand is the rabbi's job-site block.
        PointOfInterestHelper.register(TORAH_STAND_ID, 1, 1, ModBlocks.TORAH_STAND);

        Registry.register(
                Registries.VILLAGER_PROFESSION,
                RABBI,
                new VillagerProfession(
                        Text.translatable("entity.minecraft.villager.rabbi"),
                        entry -> entry.matchesKey(TORAH_STAND_POI),
                        entry -> entry.matchesKey(TORAH_STAND_POI),
                        ImmutableSet.of(),
                        ImmutableSet.of(),
                        SoundEvents.ENTITY_VILLAGER_WORK_CLERIC)
        );

        // Level 1: gather wheat, sell challah.
        TradeOfferHelper.registerVillagerOffers(RABBI, 1, factories -> {
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

        // Level 2: sell wine.
        TradeOfferHelper.registerVillagerOffers(RABBI, 2, factories -> {
            factories.add((world, entity, random) -> new TradeOffer(
                    new TradedItem(Items.EMERALD, 3),
                    Optional.empty(),
                    new ItemStack(ModItems.WINE, 1),
                    8, 10, 0.05f
            ));
        });
    }
}
