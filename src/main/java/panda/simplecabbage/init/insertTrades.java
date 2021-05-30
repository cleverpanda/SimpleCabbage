package panda.simplecabbage.init;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.entity.merchant.villager.VillagerProfession;
import net.minecraft.entity.merchant.villager.VillagerTrades.ITrade;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraftforge.common.BasicTrade;
import net.minecraftforge.event.village.VillagerTradesEvent;
import net.minecraftforge.event.village.WandererTradesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import panda.simplecabbage.SimpleCabbage;

@EventBusSubscriber(modid = SimpleCabbage.MODID)
public class InsertTrades {
	
	@SubscribeEvent
	public static void addVillageTrades(final VillagerTradesEvent event) {
		if(event.getType() == VillagerProfession.FARMER) {
			List<ITrade> trades = new ArrayList<>();		
			trades.add(new BasicTrade(new ItemStack(ModItems.CABBAGE_HEAD, 16),new ItemStack(Items.EMERALD, 1), 12, 10, 0.05F));
			//(ItemStack price, ItemStack forSale, int maxTrades, int xp, float priceMult)
			event.getTrades().put(2,trades);
		}
	}
	
	@SubscribeEvent
	public static void addWandererTrades(final WandererTradesEvent event) {
		event.getGenericTrades().add(new BasicTrade(1, new ItemStack(ModItems.CABBAGE_SEEDS, 1), 12, 5, 1F));
		//(ItemStack price, ItemStack price2, ItemStack forSale, int maxTrades, int xp, float priceMult)
	}
}
