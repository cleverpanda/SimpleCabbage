package panda.simplecabbage;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Set;

import net.minecraft.block.ComposterBlock;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.entity.passive.ChickenEntity;
import net.minecraft.entity.passive.ParrotEntity;
import net.minecraft.entity.passive.PigEntity;
//import net.minecraft.entity.passive.EntityChicken;
//import net.minecraft.entity.passive.EntityPig;
//import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
//import net.minecraft.world.gen.structure.MapGenStructureIO;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.crafting.CompoundIngredient;
//import net.minecraftforge.common.config.Configuration;
//import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
//import net.minecraftforge.fml.common.Mod.EventHandler;
//import net.minecraftforge.fml.common.Mod.Instance;
//import net.minecraftforge.fml.common.SidedProxy;
//import net.minecraftforge.fml.common.event.FMLConstructionEvent;
//import net.minecraftforge.fml.common.event.FMLInitializationEvent;
//import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
//import net.minecraftforge.fml.common.registry.VillagerRegistry;
import panda.simplecabbage.gen.ComponentCabbageField;
import panda.corn.SimpleCorn;
import panda.corn.config.ConfigSimpleCorn;
import panda.corn.init.ModBlocks;
import panda.simplecabbage.gen.CabbageWorldGen;
import panda.simplecabbage.init.ModItems;
import panda.simplecabbage.other.DropSeedsHandler;
import panda.simplecabbage.other.ImmersiveEngineeringCompat;
import panda.simplecabbage.other.ThermalCompat;
import panda.simplecabbage.proxy.CommonProxy;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import org.apache.commons.lang3.reflect.FieldUtils;
import org.apache.logging.log4j.Level;

@Mod(SimpleCabbage.MODID)
@EventBusSubscriber(modid = SimpleCabbage.MODID, bus = EventBusSubscriber.Bus.MOD)
public class SimpleCabbage {	
	
	public static final String MODID = "simplecabbage";
	public static final String NAME = "Simple Cabbage";
	//public static final String VERSION = "1.0.7";
	
	private static boolean isIEInstalled;
	private static boolean isThermalInstalled;
	//public Configuration config;
	
	//@Instance(MODID)
	//public static SimpleCabbage instance;
	
	//@SidedProxy(clientSide = "panda.simplecabbage.proxy.ClientProxy", serverSide = "panda.simplecabbage.proxy.ServerProxy")
	//public static CommonProxy proxy;
	
	
	public SimpleCabbage() {	
		final ModLoadingContext modLoadingContext = ModLoadingContext.get();
		modLoadingContext.registerConfig(ModConfig.Type.SERVER, ConfigSimpleCabbage.SERVER_SPEC);	
	}
	
	@SuppressWarnings("unchecked")
	@SubscribeEvent
	public static void setup(final FMLCommonSetupEvent event){
		ComposterBlock.CHANCES.put(ModItems.CORNCOB.asItem(), 0.65F);
		ComposterBlock.CHANCES.put(ModItems.KERNELS.asItem(), 0.3F);
		replaceTemptation(PigEntity.class, "TEMPTATION_ITEMS",ModItems.CORNCOB);
		replaceTemptation(ChickenEntity.class, "TEMPTATION_ITEMS",ModItems.KERNELS);

		((Set<Item>) ObfuscationReflectionHelper.getPrivateValue(ParrotEntity.class, null, "TAME_ITEMS")).add(ModItems.KERNELS);


		//VillagerRegistry.FARMER.getCareer(0).addTrade(1, new EntityVillager.EmeraldForItems(ModItems.CORNCOB, new EntityVillager.PriceInfo(18, 22)));			
	}
	
	@SubscribeEvent
	public static void setupClient(final FMLClientSetupEvent event) {
		RenderTypeLookup.setRenderLayer(ModBlocks.CORN    , RenderType.getCutout());
		RenderTypeLookup.setRenderLayer(ModBlocks.CORN_MID, RenderType.getCutout());
		RenderTypeLookup.setRenderLayer(ModBlocks.CORN_TOP, RenderType.getCutout());
	}

	public static <T> void replaceTemptation(Class<?> clazz, String fieldName, Item item) {
		Field field = ObfuscationReflectionHelper.findField(clazz, fieldName);
		FieldUtils.removeFinalModifier(field, true);
		try {
			field.set(null, new CompoundIngredient(Arrays.asList((Ingredient)field.get(null), Ingredient.fromStacks(new ItemStack(item)))) {} );
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		}
	}
	
	
	@SubscribeEvent
	public static void commonSetup(final FMLCommonSetupEvent event) {

		event.enqueueWork(() -> {
			ModCrafting.Ingredients.register();
			ModCriterion.register();
			ModLootTables.registerLootTables();
			ModLootConditionTypes.register();
			ModLootFunctionTypes.register();

			BlockDumper.dump();
			Tests.runTests();
		});
	}
	
	
	
	
	
//	@EventHandler
//	public void preInit(FMLPreInitializationEvent event){
//		config = new Configuration(event.getSuggestedConfigurationFile());
//		ConfigSimpleCabbage.load(config);
//		
//		if(ConfigSimpleCabbage.generatefarms) {
//			MapGenStructureIO.registerStructureComponent(ComponentCabbageField.class, "Vicaf");
//			VillagerRegistry.instance().registerVillageCreationHandler(new CabbageWorldGen());
//		}
//		
//		MinecraftForge.EVENT_BUS.register(new DropSeedsHandler());
//		
//	}
//	
//	@SuppressWarnings("unchecked")
//	@EventHandler
//	public void init(FMLInitializationEvent event){
//		if(isIEInstalled){
//			ImmersiveEngineeringCompat.init();
//		}
//		
//		if(isThermalInstalled){
//			ThermalCompat.init();
//		}
//		
//		((Set<Item>) ObfuscationReflectionHelper.getPrivateValue(    EntityPig.class, null, "TEMPTATION_ITEMS")).add(ModItems.CABBAGE_HEAD);
//		((Set<Item>) ObfuscationReflectionHelper.getPrivateValue(EntityChicken.class, null, "TEMPTATION_ITEMS")).add(ModItems.CABBAGE_SEEDS);
//		
//		MinecraftForge.addGrassSeed(new ItemStack(ModItems.CABBAGE_SEEDS), ConfigSimpleCabbage.seedsWeight);
//
//		VillagerRegistry.FARMER.getCareer(0).addTrade(1, new EntityVillager.EmeraldForItems(ModItems.CABBAGE_HEAD, new EntityVillager.PriceInfo(15, 19)));
//	}
	
	
	@SubscribeEvent
	public static void enqueue(final InterModEnqueueEvent event) {
		isIEInstalled = ModList.get().isLoaded("immersiveengineering");
		isThermalInstalled = ModList.get().isLoaded("thermalexpansion");
	}

}
