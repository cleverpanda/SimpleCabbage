package panda.simplecabbage.blocks;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockCrops;
import net.minecraft.block.IGrowable;
import net.minecraft.block.SoundType;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.EnumPlantType;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.IPlantable;
import panda.simplecabbage.ConfigSimpleCabbage;
import panda.simplecabbage.init.ModItems;

public class BlockCabbage extends BlockCrops implements IGrowable {

	public static final PropertyInteger AGE = PropertyInteger.create("age", 0, 4);

	public static final AxisAlignedBB[] CROPS_AABB = new AxisAlignedBB[] {
			new AxisAlignedBB(0.3125D, 0.0D, 0.3125D, 0.6875D, 0.1875D, 0.6875D), //0  6 6 3
			new AxisAlignedBB(0.25D,   0.0D, 0.25D,   0.75D,   0.5D,    0.75D), //1
			new AxisAlignedBB(0.25D,   0.0D, 0.25D,   0.625D,  0.5D,    0.75D), //2
			new AxisAlignedBB(0.1875D, 0.0D, 0.1875D, 0.8125D, 0.5D,    0.8125D), //3
			new AxisAlignedBB(0.1875D, 0.0D, 0.1875D, 0.8125D, 0.625D,  0.8125D), //4
		};

	public BlockCabbage() {
		setHardness(0.0F);
		setSoundType(SoundType.PLANT);
		disableStats();
	}

	@Override
	public void getDrops(NonNullList<ItemStack> drops, IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
		if (getAge(state) == getMaxAge()){
			drops.add(new ItemStack(getCrop()));
		}else {
			drops.add(new ItemStack(getSeed()));
		}
		if(!ConfigSimpleCabbage.useeasyharvesting) {
			drops.add(new ItemStack(getSeed()));
		}				
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, getAgeProperty());
	}

	@Override
	protected PropertyInteger getAgeProperty() {
		return AGE;
	}

	@Override
	protected int getBonemealAgeIncrease(World worldIn) {
		return MathHelper.getInt(worldIn.rand, 1, 2);
	}

	@Override
	protected Item getCrop() {
		return ModItems.CABBAGE_HEAD;
	}
	
	@Override
    protected Item getSeed() {
        return ModItems.CABBAGE_SEEDS;
    }

	@Override
	public int getMaxAge() {
		return 4;
	}

	@Override
	public EnumPlantType getPlantType(IBlockAccess world, BlockPos pos) {
		return EnumPlantType.Crop;
	}

	@Override
	public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player) {
		return new ItemStack(getSeed());
	}

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {

		//if(worldIn.isRemote && playerIn != null) {
		//	Minecraft.getMinecraft().player.sendChatMessage("chance:"+(int) ((25.0F / getGrowthChance(this, worldIn, pos)) + 1));
		//}
		//worldIn.setBlockState(pos, this.getDefaultState());
		if(ConfigSimpleCabbage.useeasyharvesting && isMaxAge(state)){
			this.dropBlockAsItem(worldIn, pos, state, 0);
			return worldIn.setBlockState(pos, this.getDefaultState());
		}
		
		return super.onBlockActivated(worldIn, pos, state, playerIn, hand, facing, hitX, hitY, hitZ);		
	}
	
	@Override
    public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
        super.updateTick(worldIn, pos, state, rand);

        if (!worldIn.isAreaLoaded(pos, 1)) return; // Forge: prevent loading unloaded chunks when checking neighbor's light
        if (worldIn.getLightFromNeighbors(pos.up()) >= 9) {
            int i = this.getAge(state);

            if (i < this.getMaxAge()) {
                float f = getGrowthChance(this, worldIn, pos);

                if(ForgeHooks.onCropsGrowPre(worldIn, pos, state, rand.nextInt((int) (((25.0F / f) + 1)/ConfigSimpleCabbage.growModifier)) == 0)){
                    worldIn.setBlockState(pos, this.withAge(i + 1), 2);
                    ForgeHooks.onCropsGrowPost(worldIn, pos, state, worldIn.getBlockState(pos));
                }
            }
        }
    }
	
	public static float getGrowthChance(Block blockIn, World worldIn, BlockPos pos) {          
		boolean n = blockIn == worldIn.getBlockState(pos.north()).getBlock();
		boolean s = blockIn == worldIn.getBlockState(pos.south()).getBlock();
		boolean e = blockIn == worldIn.getBlockState(pos.east()).getBlock();
		boolean w = blockIn == worldIn.getBlockState(pos.west()).getBlock();
		boolean ne = blockIn == worldIn.getBlockState(pos.north().east()).getBlock();
		boolean nw = blockIn == worldIn.getBlockState(pos.north().west()).getBlock();
		boolean se = blockIn == worldIn.getBlockState(pos.south().east()).getBlock();
		boolean sw = blockIn == worldIn.getBlockState(pos.south().west()).getBlock();
		
		//determine points for crop placement, favors every other block on diagonals
        boolean[][] sides = {{ nw, n, ne },{ ne, e, se },{ se, s, sw },{ sw, w, nw }};
        int rate = 12;
        for (boolean[] side : sides) {
        	int cont = 1 + (side[0]?1:0)+ (side[2]?1:0);
        	rate += side[1]?-cont:cont;
        }
        //convert points space to multiplier space. wheat is either 0.5 or 1.0 depending on placement, scale range to
        // 0.5 to ConfigSimpleCabbage.growModifier (1.6)
        //effectively a growth speed modifier compared to wheat under ideal conditions
        float mult =  (((ConfigSimpleCabbage.growModifier-0.5f)/24f)*rate+0.5f);     
        	       
        float score = 0;
        
        //determine points for ground type and hydration
        BlockPos below = pos.down();
        for (int i = -1; i <= 1; ++i) {
            for (int j = -1; j <= 1; ++j) {
                float soilpts = 0.0F;
                IBlockState iblockstate = worldIn.getBlockState(below.add(i, 0, j));

                if (iblockstate.getBlock().canSustainPlant(iblockstate, worldIn, below.add(i, 0, j), EnumFacing.UP, (IPlantable)blockIn)) {
                	soilpts = 1.0F;
                	
                    if (iblockstate.getBlock().isFertile(worldIn, below.add(i, 0, j))) {
                    	soilpts = 3.0F;
                    }
                }

                if (i != 0 || j != 0) { // on a diagonal
                	soilpts /= 4.0F;
                }

                score += soilpts;
            }
        }
        
        return score*mult;
    }
	
//	@Override
//	public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
//		this.checkAndDropBlock(worldIn, pos, state); //Check and see if we can still exist.
//		if (worldIn.getBlockState(pos) == state) //If we can:
//		{
//			if (!worldIn.isAreaLoaded(pos, 1)) //Make sure we should bother checking
//				return;
//			if (worldIn.getLightFromNeighbors(pos.up()) >= 9 && worldIn.getBlockState(pos.down()).getBlock().isFertile(worldIn, pos.down())) //Check for light and water
//			{
//				boolean canGrow = rand.nextInt(ConfigSimpleCabbage.growChance) == 0;
//				if (!isMaxAge(state)) {
//					if (ForgeHooks.onCropsGrowPre(worldIn, pos, state, canGrow)) {
//						worldIn.setBlockState(pos, withAge(getAge(state) + 1));
//						ForgeHooks.onCropsGrowPost(worldIn, pos, state, worldIn.getBlockState(pos));
//					}
//				}
//			}
//		}
//	}
	

	//@Override
	//public IBlockState getStateFromMeta(int meta) {
	//	return withAge(meta);
	//}

	//@Override
	//public void grow(World worldIn, Random rand, BlockPos pos, IBlockState state) {
	//	updateTick(worldIn, pos, state, rand);
	//}

	//@Override
	//public int getMetaFromState(IBlockState state) {
	//	return getAge(state);
	//}
	
	//@Override
	//public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
	//	return CROPS_AABB[state.getValue(AGE)];
	//}

	//@Override
	//public boolean canUseBonemeal(World worldIn, Random rand, BlockPos pos, IBlockState state) {
	//	return true;
	//}

}
