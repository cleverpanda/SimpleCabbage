package panda.simplecabbage.init;

import net.minecraft.block.Block;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry.ObjectHolder;
import panda.simplecabbage.SimpleCabbage;
import panda.simplecabbage.blocks.BlockCabbage;


package panda.corn.init;

import javax.annotation.Nonnull;

import com.google.common.base.Preconditions;

import net.minecraft.block.Block;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.registries.ObjectHolder;
import panda.corn.SimpleCorn;
import panda.corn.blocks.BlockCorn;
import panda.corn.blocks.BlockCornMid;
import panda.corn.blocks.BlockCornTop;

@EventBusSubscriber(modid = SimpleCabbage.MODID)
@ObjectHolder(SimpleCabbage.MODID)
public final class ModBlocks {

	public static final BlockCabbage CORN = null;

	
	public static Block simply(@Nonnull Block block,@Nonnull String name) {
		Preconditions.checkNotNull(block, "Entry cannot be null!");
		Preconditions.checkNotNull(name, "Registry name to assign to entry cannot be null!");
		return block.setRegistryName(SimpleCabbage.MODID, name);
	}

	@SubscribeEvent
	public static void onRegisterBlocks(RegistryEvent.Register<Block> event) {
		event.getRegistry().registerAll(
			simply(new BlockCabbage(),"corn")
		);	
	}

}


//@EventBusSubscriber(modid = SimpleCabbage.MODID)
//@ObjectHolder(SimpleCabbage.MODID)
//public final class ModBlocks {
//	
//	private ModBlocks() {
//	    throw new IllegalStateException("Utility class");
//	}
//
//	public static final BlockCabbage CABBAGE = null;
//
//	@SubscribeEvent
//	public static void register(RegistryEvent.Register<Block> event) {
//		event.getRegistry().register(new BlockCabbage().setRegistryName(SimpleCabbage.MODID, "cabbage").setTranslationKey(SimpleCabbage.MODID + ".cabbage"));
//	}
//	
//}
