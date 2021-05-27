package panda.simplecabbage;

import net.minecraftforge.common.config.Configuration;

public class ConfigSimpleCabbage {
	
	public static int headFood;
	public static float headSat;
	public static int stewFood;
	public static int hamStewFood;
	public static int seedsWeight;
	public static boolean coldbiomesonly;
	public static int generationWeight;
	public static int ethanolvolume;
	public static int plantoilvolume;
	public static int clochedropamount;
	public static boolean useeasyharvesting;
	public static float growModifier;
	public static boolean generatefarms;


	private ConfigSimpleCabbage() {
	    throw new IllegalStateException("Utility class");
	}
	
	public static void load(Configuration config) {
		config.load();
		String ieCategory = "Immersive Engineering";
		headFood = config.getInt("VALUE_FOOD_CABBAGE", Configuration.CATEGORY_GENERAL, 3, 1, 20,"Hunger value of heads of cabbage");
		headSat = config.getFloat("VALUE_SATURATION_CABBAGE",  Configuration.CATEGORY_GENERAL, 0.1F, 0F, 1F, "Saturation value of heads of cabbage");
		
		stewFood = config.getInt("VALUE_FOOD_STEW", Configuration.CATEGORY_GENERAL, 7, 1, 20,"Hunger value of cabbage stew");		
		hamStewFood = config.getInt("VALUE_FOOD_HAM_STEW", Configuration.CATEGORY_GENERAL, 9, 1, 20,"Hunger value of ham and cabbage stew");

		coldbiomesonly =  config.getBoolean("COLD_BIOMES_ONLY", Configuration.CATEGORY_GENERAL, true,"restricts cabbage seed drops to cold biomes");
		seedsWeight =  config.getInt("VALUE_SEED_DROP", Configuration.CATEGORY_GENERAL, 4, 1, 100,"The relative chance of dropping cabbage seeds from grass. wheat seeds are 10.");
		generationWeight =  config.getInt("VALUE_CABBAGE_FIELD_GENERATION", Configuration.CATEGORY_GENERAL, 40, 0, 100,"The relative chance of spawning cabbage fields. The small houses are 3, Blacksmiths are 15. higher is lower.");
		growModifier = config.getFloat("VALUE_GROWTH_MODIFY", Configuration.CATEGORY_GENERAL, 1.2F, 0F, 100F,"Multiplies the speed cabbage grows, compared to default (wheat)");
		
		useeasyharvesting = config.getBoolean("USE_EASY_HARVESTING", Configuration.CATEGORY_GENERAL, false, "Allow right click harvesting");
		generatefarms = config.getBoolean("GENERATE_FARMS", Configuration.CATEGORY_GENERAL, true, "Whether or not to generate cabbage patches in villages");
		plantoilvolume = config.getInt("VALUE_IE_SQUEEZER_OIL_VOLUME",  ieCategory, 80, 0, 1000, "Amount of plant oil in mB that seeds produces in an IE squeezer");
		ethanolvolume = config.getInt("VALUE_IE_FERMENTER_ETHANOL_VOLUME",  ieCategory, 40, 0, 1000, "Amount of ethanol in mB that cabbages produces in an IE fermenter");
		clochedropamount = config.getInt("VALUE_NUM_DROPS_CLOCHE",ieCategory,1,0,100,"The number of cabbage heads you get from growing cabbages in a garden cloche");
		if (config.hasChanged()) config.save();
	}
}
