package panda.simplecabbage.items;

import net.minecraft.block.BlockState;
import net.minecraft.item.BlockNamedItem;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.PlantType;
import panda.simplecabbage.init.ModBlocks;

public class ItemCabbageSeeds extends BlockNamedItem implements IPlantable{
	public ItemCabbageSeeds() {
		super(ModBlocks.CABBAGE, new Properties().group(ItemGroup.MATERIALS));
	}
	
	@Override
	public PlantType getPlantType(IBlockReader world, BlockPos pos) {
        return PlantType.CROP;
    }

	@Override
	public BlockState getPlant(IBlockReader world, BlockPos pos) {
		return ModBlocks.CABBAGE.getDefaultState();
	}
	
	@Override
	public void fillItemGroup(ItemGroup group, NonNullList<ItemStack> items) {
		if(this.isInGroup(group))
			items.add(new ItemStack(this));
	}
}

