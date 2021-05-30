package panda.simplecabbage.blocks;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.CropsBlock;
import net.minecraft.block.FarmlandBlock;
import net.minecraft.block.IGrowable;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.Direction;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.IPlantable;
import panda.simplecabbage.config.ConfigSimpleCabbage;
import panda.simplecabbage.init.ModItems;

public class BlockCabbage extends CropsBlock implements IGrowable {

	public static final IntegerProperty AGE_0_4 = IntegerProperty.create("age", 0, 4);

	protected static final VoxelShape[] SHAPES = new VoxelShape[]{
		Block.makeCuboidShape(5D, 0D, 5D, 11D,  3D, 11D),
		Block.makeCuboidShape(4D, 0D, 4D, 12D,  8D, 12D),
		Block.makeCuboidShape(4D, 0D, 4D, 10D,  8D, 12D),
		Block.makeCuboidShape(3D, 0D, 3D, 13D,  8D, 13D),
		Block.makeCuboidShape(3D, 0D, 3D, 13D, 10D, 13D)
	};

	public BlockCabbage() {
		super(Block.Properties.create(Material.PLANTS).doesNotBlockMovement().tickRandomly().hardnessAndResistance(0.0F,0F).sound(SoundType.CROP));
	}

	@Override
	 public VoxelShape getShape(BlockState state, IBlockReader world, BlockPos pos, ISelectionContext context) {
		 return SHAPES[state.get(AGE_0_4)];
	 }

	 @Override
	 protected boolean isValidGround(BlockState state, IBlockReader worldIn, BlockPos pos) {
		 return state.getBlock() instanceof FarmlandBlock;
	 }

	 @Override
	 public IntegerProperty getAgeProperty() {
		 return AGE_0_4;
	 }	   

	 @Override
	 @OnlyIn(Dist.CLIENT)
	 protected IItemProvider getSeedsItem() {
		 return ModItems.CABBAGE_SEEDS;
	 }

	 @Override
	 public boolean isValidPosition(BlockState state, IWorldReader world, BlockPos pos) {
		 BlockState down = world.getBlockState(pos.down());
		 return down.getBlock().canSustainPlant(down, world, pos, Direction.UP, this);
	 }

	 @Override
	 public boolean canGrow(IBlockReader world, BlockPos pos, BlockState state, boolean isClient) {
		 return !isMaxAge(state) && world.getBlockState(pos.up()).getMaterial().isReplaceable();
	 }

	 @Override
	 public boolean canUseBonemeal(World worldIn, Random rand, BlockPos pos, BlockState state) {
		 return canGrow(worldIn, pos, worldIn.getBlockState(pos), worldIn.isRemote);
	 }

	 @Override
	 protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder){
		 builder.add(AGE_0_4);
	 }

	 @Override
	 public int getMaxAge() {
		 return 4;
	 }
	 
	 @Override
	 @OnlyIn(Dist.CLIENT)
	 public ItemStack getItem(IBlockReader worldIn, BlockPos pos, BlockState state) {
	     return new ItemStack(ModItems.CABBAGE_SEEDS);
	 }


	 public boolean checkFertile(World world, BlockPos pos) {
		 return world.getBlockState(pos.down()).isFertile(world, pos.down());
	 }

	 @Override
	 public ItemStack getPickBlock(BlockState state, RayTraceResult target, IBlockReader world, BlockPos pos, PlayerEntity player){
		 return new ItemStack(ModItems.CABBAGE_SEEDS);
	 }
	 
	@Override
	public boolean ticksRandomly(BlockState state) {
		return state.get(AGE_0_4) < 5;
	}
	
	
	
	
	
	
	
	@Override
    public void grow(ServerWorld worldIn, Random rand, BlockPos pos, BlockState state) {
		this.randomTick(state, worldIn, pos, rand);
	}
	
	
	
	
	
	
	@Override
	 public void randomTick(BlockState state, ServerWorld worldIn, BlockPos pos, Random random) {

        if (!worldIn.isAreaLoaded(pos, 1)) return; // Forge: prevent loading unloaded chunks when checking neighbor's light
        if (worldIn.getLightSubtracted(pos.up(),0) >= 9) {
            int i = this.getAge(state);

            if (i < this.getMaxAge()) {
                float f = 25; //getGrowthChance(this, worldIn, pos);

                if(ForgeHooks.onCropsGrowPre(worldIn, pos, state, random.nextInt((int) (((25.0F / f) + 1))) == 0)){
                    worldIn.setBlockState(pos, this.withAge(i + 1), 2);
                    ForgeHooks.onCropsGrowPost(worldIn, pos, state);
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
                BlockState iblockstate = worldIn.getBlockState(below.add(i, 0, j));

                if (iblockstate.getBlock().canSustainPlant(iblockstate, worldIn, below.add(i, 0, j), Direction.UP, (IPlantable)blockIn)) {
                	soilpts = 1.0F;
                	
                    if (iblockstate.getBlock().isFertile(iblockstate, worldIn, below.add(i, 0, j))) {
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

}
