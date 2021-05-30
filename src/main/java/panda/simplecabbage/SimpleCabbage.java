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
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraftforge.common.crafting.CompoundIngredient;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import panda.simplecabbage.config.ConfigSimpleCabbage;
import panda.simplecabbage.init.ModBlocks;
import panda.simplecabbage.init.ModItems;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;

import org.apache.commons.lang3.reflect.FieldUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(SimpleCabbage.MODID)
@EventBusSubscriber(modid = SimpleCabbage.MODID, bus = EventBusSubscriber.Bus.MOD)
public class SimpleCabbage {	
	//modid = SimpleCorn.MODID, name = SimpleCorn.NAME, version = SimpleCorn.VERSION, dependencies = "after:immersiveengineering;"+"after:thermalexpansion"
	public static final String MODID = "simplecabbage";
	public static final Logger LOGGER = LogManager.getLogger(MODID);

	public SimpleCabbage(){
		final ModLoadingContext modLoadingContext = ModLoadingContext.get();
		modLoadingContext.registerConfig(ModConfig.Type.SERVER, ConfigSimpleCabbage.SERVER_SPEC);		
	}

	@SuppressWarnings("unchecked")
	@SubscribeEvent
	public static void setup(final FMLCommonSetupEvent event){
		ComposterBlock.CHANCES.put(ModItems.CABBAGE_HEAD.asItem(), 0.65F);
		ComposterBlock.CHANCES.put(ModItems.CABBAGE_SEEDS.asItem(), 0.3F);
		replaceTemptation(PigEntity.class, "TEMPTATION_ITEMS",ModItems.CABBAGE_HEAD);
		replaceTemptation(ChickenEntity.class, "TEMPTATION_ITEMS",ModItems.CABBAGE_SEEDS);

		((Set<Item>) ObfuscationReflectionHelper.getPrivateValue(ParrotEntity.class, null, "TAME_ITEMS")).add(ModItems.CABBAGE_SEEDS);		
	}
	
	@SubscribeEvent
	public static void setupClient(final FMLClientSetupEvent event) {
		RenderTypeLookup.setRenderLayer(ModBlocks.CABBAGE, RenderType.getCutout());
	}
	
	public static <T> void replaceTemptation(Class<? super T> clazz, String fieldName, Item item) {
		Field field = ObfuscationReflectionHelper.findField(clazz, fieldName);
		FieldUtils.removeFinalModifier(field, true);
		try {
			field.set(null, new CompoundIngredient(Arrays.asList((Ingredient)field.get(null), Ingredient.fromStacks(new ItemStack(item)))) {} );
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		}
	}

	@SubscribeEvent
	public static void onModConfigEvent(final ModConfig.ModConfigEvent event) {
		final ModConfig config = event.getConfig();
		if (config.getSpec() == ConfigSimpleCabbage.SERVER_SPEC) {
			ConfigSimpleCabbage.bakeServer(config);
		}
	}
	
	
	//@SubscribeEvent
	//public static void enqueue(final InterModEnqueueEvent event) {
	//	isIEInstalled = ModList.get().isLoaded("immersiveengineering");
	//	isThermalInstalled = ModList.get().isLoaded("thermalexpansion");
	//}

}
