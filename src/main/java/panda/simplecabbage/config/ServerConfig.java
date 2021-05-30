package panda.simplecabbage.config;

import net.minecraftforge.common.ForgeConfigSpec;
import panda.simplecabbage.SimpleCabbage;

final class ServerConfig {

	final ForgeConfigSpec.IntValue ethanolvolume;
	final ForgeConfigSpec.IntValue plantoilvolume;
	final ForgeConfigSpec.IntValue clochedropamount;

	ServerConfig(final ForgeConfigSpec.Builder builder) {
		builder.push("general");

		builder.push("immersive engineering");
		ethanolvolume = builder.comment("Amount of ethanol in mB that corn produces in an IE fermenter").translation(SimpleCabbage.MODID + ".config.ethanolVolume").defineInRange("ethanolVolume", 120, 0, Integer.MAX_VALUE);
		plantoilvolume = builder.comment("Amount of plant oil in mB that kernels produces in an IE squeezer").translation(SimpleCabbage.MODID + ".config.plantOilVolume").defineInRange("plantOilVolume", 100, 0, Integer.MAX_VALUE);
		clochedropamount = builder.comment("The number of corn cobs you get from growing corn in a garden cloche").translation(SimpleCabbage.MODID + ".config.clocheDropAmount").defineInRange("clocheDropAmount", 2, 0, Integer.MAX_VALUE);

		builder.pop();
	}

}


//headFood = config.getInt("VALUE_FOOD_CABBAGE", Configuration.CATEGORY_GENERAL, 3, 1, 20,"Hunger value of heads of cabbage");
//headSat = config.getFloat("VALUE_SATURATION_CABBAGE",  Configuration.CATEGORY_GENERAL, 0.1F, 0F, 1F, "Saturation value of heads of cabbage");
//
//stewFood = config.getInt("VALUE_FOOD_STEW", Configuration.CATEGORY_GENERAL, 7, 1, 20,"Hunger value of cabbage stew");		
//hamStewFood = config.getInt("VALUE_FOOD_HAM_STEW", Configuration.CATEGORY_GENERAL, 9, 1, 20,"Hunger value of ham and cabbage stew");
//
//coldbiomesonly =  config.getBoolean("COLD_BIOMES_ONLY", Configuration.CATEGORY_GENERAL, true,"restricts cabbage seed drops to cold biomes");
//seedsWeight =  config.getInt("VALUE_SEED_DROP", Configuration.CATEGORY_GENERAL, 4, 1, 100,"The relative chance of dropping cabbage seeds from grass. wheat seeds are 10.");
//generationWeight =  config.getInt("VALUE_CABBAGE_FIELD_GENERATION", Configuration.CATEGORY_GENERAL, 40, 0, 100,"The relative chance of spawning cabbage fields. The small houses are 3, Blacksmiths are 15. higher is lower.");
//growModifier = config.getFloat("VALUE_GROWTH_MODIFY", Configuration.CATEGORY_GENERAL, 1.2F, 0F, 100F,"Multiplies the speed cabbage grows, compared to default (wheat)");
//
//useeasyharvesting = config.getBoolean("USE_EASY_HARVESTING", Configuration.CATEGORY_GENERAL, false, "Allow right click harvesting");
//generatefarms = config.getBoolean("GENERATE_FARMS", Configuration.CATEGORY_GENERAL, true, "Whether or not to generate cabbage patches in villages");
//plantoilvolume = config.getInt("VALUE_IE_SQUEEZER_OIL_VOLUME",  ieCategory, 80, 0, 1000, "Amount of plant oil in mB that seeds produces in an IE squeezer");
//ethanolvolume = config.getInt("VALUE_IE_FERMENTER_ETHANOL_VOLUME",  ieCategory, 40, 0, 1000, "Amount of ethanol in mB that cabbages produces in an IE fermenter");
//clochedropamount = config.getInt("VALUE_NUM_DROPS_CLOCHE",ieCategory,1,0,100,"The number of cabbage heads you get from growing cabbages in a garden cloche");