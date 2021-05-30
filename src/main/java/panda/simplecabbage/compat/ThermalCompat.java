package panda.simplecabbage.compat;

import cofh.thermalexpansion.util.managers.machine.InsolatorManager;
import cofh.thermalfoundation.item.ItemFertilizer;
import net.minecraft.item.ItemStack;
import panda.simplecabbage.init.ModItems;

public class ThermalCompat {
	
	private ThermalCompat() {
	    throw new IllegalStateException("Utility class");
	}
	
	public static void init() {
		InsolatorManager.addRecipe(4800, 1200, new ItemStack(ModItems.CABBAGE_SEEDS), ItemFertilizer.fertilizerBasic, new ItemStack(ModItems.CABBAGE_HEAD,2));
		InsolatorManager.addRecipe(7200, 1800, new ItemStack(ModItems.CABBAGE_SEEDS), ItemFertilizer.fertilizerRich, new ItemStack(ModItems.CABBAGE_HEAD,4));
		InsolatorManager.addRecipe(9600, 2400, new ItemStack(ModItems.CABBAGE_SEEDS), ItemFertilizer.fertilizerFlux, new ItemStack(ModItems.CABBAGE_HEAD,6));
	}
}
