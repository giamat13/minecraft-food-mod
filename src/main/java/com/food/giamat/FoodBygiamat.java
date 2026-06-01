package com.food.giamat;

import com.food.giamat.init.ModItems;
import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FoodBygiamat implements ModInitializer {
	public static final String MOD_ID = "food-by-giamat";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		ModItems.initialize();
		LOGGER.info("Food By Giamat loaded!");
	}
}