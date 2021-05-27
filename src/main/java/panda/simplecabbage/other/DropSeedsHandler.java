package panda.simplecabbage.other;

import java.util.Iterator;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.world.BlockEvent.HarvestDropsEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import panda.simplecabbage.ConfigSimpleCabbage;
import panda.simplecabbage.init.ModBlocks;
import panda.simplecabbage.init.ModItems;

public class DropSeedsHandler {
	//cancel cabbage seed drops if we are restricted to cold biomes if the dees would drop in a non-cold biome
	@SubscribeEvent(priority = EventPriority.LOWEST)
	public void onHarvestGrass(HarvestDropsEvent event) {
		boolean coldOnly = ConfigSimpleCabbage.coldbiomesonly;
		if(!coldOnly || event.getState().getBlock() == ModBlocks.CABBAGE) return;

		if(event.getWorld().getBiome(event.getPos()).getDefaultTemperature() > 0.25F) {
			Iterator<ItemStack> drops = event.getDrops().iterator();
			while(drops.hasNext()) {
				Item item = ((ItemStack)drops.next()).getItem();
				if(item == ModItems.CABBAGE_SEEDS) {
					drops.remove();
				} 
			}
		}	
	}
}
