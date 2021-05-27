package panda.simplecabbage.proxy;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import panda.simplecabbage.init.ModBlocks;
import panda.simplecabbage.init.ModItems;

@Mod.EventBusSubscriber(Side.CLIENT)
public class ClientProxy extends CommonProxy {

	@SubscribeEvent
    public static void registerModels(ModelRegistryEvent event) {
		
		registerItemModel(ModItems.CABBAGE_STEW);
		registerItemModel(ModItems.CABBAGE_STEW_HAM);
		registerItemModel(ModItems.CABBAGE_HEAD);
		registerItemModel(ModItems.CABBAGE_SEEDS);
		registerItemModel(Item.getItemFromBlock(ModBlocks.CABBAGE));
	}

	public static void registerItemModel(Item item) {
		ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(item.getRegistryName(), "inventory"));
	}
}
