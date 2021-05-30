package panda.simplecabbage.compat;


import panda.simplecabbage.config.ConfigSimpleCabbage;
import panda.simplecabbage.init.ModBlocks;
import panda.simplecabbage.init.ModItems;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import java.util.HashSet;
import blusunrize.immersiveengineering.api.ComparableItemStack;
import blusunrize.immersiveengineering.api.crafting.FermenterRecipe;
import blusunrize.immersiveengineering.api.crafting.SqueezerRecipe;
import blusunrize.immersiveengineering.api.tool.BelljarHandler;

public class ImmersiveEngineeringCompat {
	
	private ImmersiveEngineeringCompat() {
	    throw new IllegalStateException("Utility class");
	}
	
	public static void init(){
		FermenterRecipe.addRecipe(new FluidStack(FluidRegistry.getFluid("ethanol"),ConfigSimpleCabbage.ethanolvolume), ItemStack.EMPTY, ModItems.CABBAGE_HEAD, 6400);
		SqueezerRecipe.addRecipe(new FluidStack(FluidRegistry.getFluid("plantoil"), ConfigSimpleCabbage.plantoilvolume), ItemStack.EMPTY, ModItems.CABBAGE_SEEDS, 6400);

		BelljarHandler.DefaultPlantHandler cornBelljarHandler = new BelljarHandler.DefaultPlantHandler(){
			private HashSet<ComparableItemStack> validSeeds = new HashSet<>();
			@Override
			protected HashSet<ComparableItemStack> getSeedSet(){
				return validSeeds;
			}
			@Override
			@SideOnly(Side.CLIENT)
			public IBlockState[] getRenderedPlant(ItemStack seed, ItemStack soil, float growth, TileEntity tile){
				int age = Math.min(4, Math.round(growth*4));

				return new IBlockState[]{getState(age)};
			}
			@Override
			@SideOnly(Side.CLIENT)
			public float getRenderSize(ItemStack seed, ItemStack soil, float growth, TileEntity tile){
				return 0.8f;
			}
		};
		BelljarHandler.registerHandler(cornBelljarHandler);
		cornBelljarHandler.register(new ItemStack(ModItems.CABBAGE_SEEDS), new ItemStack[]{new ItemStack(ModItems.CABBAGE_HEAD,ConfigSimpleCabbage.clochedropamount)},new ItemStack(Blocks.DIRT), ModBlocks.CABBAGE.getDefaultState());
	}
	
	private static IBlockState getState(int age){
		return ModBlocks.CABBAGE.getStateFromMeta(age);
	}

}
