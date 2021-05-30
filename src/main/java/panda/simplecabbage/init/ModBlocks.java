package panda.simplecabbage.init;

import javax.annotation.Nonnull;

import com.google.common.base.Preconditions;

import net.minecraft.block.Block;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.registries.ObjectHolder;
import panda.simplecabbage.SimpleCabbage;
import panda.simplecabbage.blocks.BlockCabbage;


@EventBusSubscriber(modid = SimpleCabbage.MODID, bus = EventBusSubscriber.Bus.MOD)
@ObjectHolder(SimpleCabbage.MODID)
public final class ModBlocks {

	public static final BlockCabbage CABBAGE = null;
	
	public static Block simply(@Nonnull Block block, @Nonnull String name) {
		Preconditions.checkNotNull(block, "Entry cannot be null!");
		Preconditions.checkNotNull(name, "Registry name to assign to entry cannot be null!");
		return block.setRegistryName(SimpleCabbage.MODID, name);
	}

	@SubscribeEvent
	public static void onRegisterBlocks(RegistryEvent.Register<Block> event) {
		event.getRegistry().register(simply(new BlockCabbage(), "cabbage"));	
	}

}
