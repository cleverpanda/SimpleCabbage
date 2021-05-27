package panda.simplecabbage.items;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.item.BlockItem;
import net.minecraft.item.BlockNamedItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraftforge.common.IPlantable;
import panda.simplecabbage.init.ModBlocks;

public class ItemCabbageSeeds extends BlockItem implements IPlantable{

	public ItemCabbageSeeds() {
		super(ModBlocks.CABBAGE, new Item.Properties().tab(ItemGroup.TAB_MATERIALS));
		//GenericDeferredWork.enqueue(() -> ComposterBlock.CHANCES.putIfAbsent(this, 0.3f));
	}

	@Override
	public BlockState getPlant(IBlockReader world, BlockPos pos) {
		return this.getBlock().getDefaultState();
	}

//	public ItemCabbageSeeds() {
//		super(ModBlocks.CABBAGE, Blocks.FARMLAND);
//	}
//	
//	@Override
//	public EnumPlantType getPlantType(IBlockAccess world, BlockPos pos){
//		return EnumPlantType.Crop;
//	}
//	
//	@Override
//	public IBlockState getPlant(IBlockAccess world, BlockPos pos){
//		return ModBlocks.CABBAGE.getDefaultState();
//	}
}
