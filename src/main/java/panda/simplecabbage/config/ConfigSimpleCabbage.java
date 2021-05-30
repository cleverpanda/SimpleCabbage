package panda.simplecabbage.config;

import org.apache.commons.lang3.tuple.Pair;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.config.ModConfig;
import panda.simplecabbage.SimpleCabbage;


@EventBusSubscriber(modid = SimpleCabbage.MODID)
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
	public static float growModifier;
	public static boolean generatefarms;
	
	public static final ForgeConfigSpec SERVER_SPEC;
	static final ServerConfig SERVER;

	static {
		{
			final Pair<ServerConfig, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(ServerConfig::new);
			SERVER = specPair.getLeft();
			SERVER_SPEC = specPair.getRight();
		}
	}
	
	public static void bakeServer(final ModConfig config) {

		ethanolvolume = SERVER.ethanolvolume.get();
		plantoilvolume = SERVER.plantoilvolume.get();
		clochedropamount = SERVER.clochedropamount.get();
	}
}
