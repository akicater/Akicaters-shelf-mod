package com.aki;

import com.aki.things.block.blockEntities;
import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.aki.things.block.block.initializeBlockItems;
import static com.aki.things.block.block.initializeBlocks;
import static com.aki.things.block.blockEntities.registerTypes;
import static com.aki.things.tabs.groups.initiliazeGroups;

public class AkiMod implements ModInitializer {
	public static final String MOD_ID = "aki";
    public static final Logger LOGGER = LoggerFactory.getLogger("aki");

	@Override
	public void onInitialize() {
		//Blocks
		initializeBlocks();
		initializeBlockItems();
		registerTypes();

		//Groups
		initiliazeGroups();

		LOGGER.info("Hello from akicater's shelf mod!");
	}
}