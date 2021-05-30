package panda.simplecabbage.init;

import javax.annotation.Nonnull;

import com.google.common.base.Preconditions;

import net.minecraft.item.BlockItem;
import net.minecraft.item.Food;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.SoupItem;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.registries.ObjectHolder;
import panda.simplecabbage.SimpleCabbage;
import panda.simplecabbage.config.ConfigSimpleCabbage;
import panda.simplecabbage.items.ItemCabbageSeeds;


@EventBusSubscriber(modid = SimpleCabbage.MODID, bus = EventBusSubscriber.Bus.MOD)
@ObjectHolder(SimpleCabbage.MODID)
public final class ModItems {

	public static final Item CABBAGE_HEAD = null;
	public static final Item CABBAGE_SEEDS = null;
	public static final Item CABBAGE_STEW = null;
	public static final Item CABBAGE_STEW_HAM = null;

	private static Item simply(@Nonnull Item item, @Nonnull String name) {
		Preconditions.checkNotNull(item, "Entry cannot be null!");
		Preconditions.checkNotNull(name, "Registry name to assign to entry cannot be null!");
		return item.setRegistryName(SimpleCabbage.MODID, name);
	}

	@SubscribeEvent
	public static void onRegisterItems(RegistryEvent.Register<Item> event) {
		event.getRegistry().registerAll(
			simply(makeFood(ConfigSimpleCabbage.headFood, ConfigSimpleCabbage.headSat), "cabbage_head"),
			simply(new ItemCabbageSeeds(), "cabbage_seeds"),
			simply(makeStew(ConfigSimpleCabbage.stewFood), "cabbage_stew"),
			simply(makeStew(ConfigSimpleCabbage.hamStewFood), "cabbage_stew_ham"),
		    new BlockItem(ModBlocks.CABBAGE, new Item.Properties().maxStackSize(64)).setRegistryName(SimpleCabbage.MODID, "cabbage")
		);
	}
	
	public static Item makeStew(int hunger) {
		return new SoupItem((new Item.Properties()).maxStackSize(1).group(ItemGroup.FOOD)
				.food((new Food.Builder()).hunger(hunger).saturation(0.6F).build()));
	}
	
	public static Item makeFood(int hunger, float sat) {
		return new Item(new Item.Properties().group(ItemGroup.FOOD).food(new Food.Builder().hunger(hunger).saturation(sat).build()));		
	}
	
}