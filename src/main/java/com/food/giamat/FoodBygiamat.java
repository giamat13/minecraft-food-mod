package com.food.giamat;

import com.food.giamat.init.ModBlocks;
import com.food.giamat.init.ModComponents;
import com.food.giamat.init.ModItems;
import com.food.giamat.recipe.ModRecipes;
import com.food.giamat.worldgen.ModWorldGen;
import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FoodBygiamat implements ModInitializer {
	public static final String MOD_ID = "food-by-giamat";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		ModComponents.initialize();
		ModItems.initialize();
		ModBlocks.initialize();
		ModRecipes.initialize();
		ModEvents.initialize();
		ModWorldGen.initialize();
		LOGGER.info("Food By Giamat loaded!");
	}
}