package panda.simplecabbage.init;

import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemSoup;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry.ObjectHolder;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.registries.IForgeRegistry;
import panda.simplecabbage.ConfigSimpleCabbage;
import panda.simplecabbage.SimpleCabbage;
import panda.simplecabbage.items.ItemCabbageSeeds;

//@EventBusSubscriber(modid = SimpleCabbage.MODID)
//@ObjectHolder(SimpleCabbage.MODID)
//public final class ModItems {
//	
//	private ModItems() {
//	    throw new IllegalStateException("Utility class");
//	}
//
//	public static final Item CABBAGE_HEAD = null;
//	public static final Item CABBAGE_SEEDS = null;
//	public static final Item CABBAGE_STEW = null;
//	public static final Item CABBAGE_STEW_HAM = null;
//
//	private static Item simply(Item item, String name) {
//		return item.setRegistryName(SimpleCabbage.MODID, name).setTranslationKey(SimpleCabbage.MODID + "." + name);
//	}
//
//	@SubscribeEvent
//	public static void register(RegistryEvent.Register<Item> event) {
//		IForgeRegistry<Item> registry = event.getRegistry();
//
//		registry.register(simply(new ItemFood(ConfigSimpleCabbage.headFood, ConfigSimpleCabbage.headSat, false), "cabbage_head"));
//		registry.register(simply(new ItemCabbageSeeds(), "cabbage_seeds"));
//		registry.register(simply(new ItemSoup(ConfigSimpleCabbage.stewFood), "cabbage_stew"));
//		registry.register(simply(new ItemSoup(ConfigSimpleCabbage.hamStewFood), "cabbage_stew_ham"));
//
//		registry.register(new ItemBlock(ModBlocks.CABBAGE).setRegistryName(ModBlocks.CABBAGE.getRegistryName()));
//	}
//
//	@SubscribeEvent
//	public static void recipes(RegistryEvent.Register<IRecipe> event) {
//		OreDictionary.registerOre("seedsCabbage", ModItems.CABBAGE_SEEDS);
//		OreDictionary.registerOre("seedCabbage", ModItems.CABBAGE_SEEDS);
//		OreDictionary.registerOre("listAllVeggies", ModItems.CABBAGE_HEAD);
//		OreDictionary.registerOre("cropCabbage", ModItems.CABBAGE_HEAD);
//		OreDictionary.registerOre("listAllseed", ModItems.CABBAGE_SEEDS);
//	}
//	public static final RegistryObject<Item> CABBAGE = ITEMS.register("cabbage",
//			() -> new Item(new Item.Properties().food(Foods.CABBAGE).group(FarmersDelight.ITEM_GROUP)));
//	public static final RegistryObject<Item> HAM = ITEMS.register("ham",
//			() -> new Item(new Item.Properties().food(Foods.HAM).group(FarmersDelight.ITEM_GROUP)));
//	public static final RegistryObject<Item> TOMATO_SEEDS = ITEMS.register("tomato_seeds", () -> new BlockNamedItem(ModBlocks.TOMATO_CROP.get(), new Item.Properties().group(FarmersDelight.ITEM_GROUP)));
//	public static final RegistryObject<Item> CHICKEN_SOUP = ITEMS.register("chicken_soup",
//			() -> new ConsumableItem(new Item.Properties().food(Foods.CHICKEN_SOUP).containerItem(Items.BOWL).maxStackSize(16).group(FarmersDelight.ITEM_GROUP)));
//	
//}

