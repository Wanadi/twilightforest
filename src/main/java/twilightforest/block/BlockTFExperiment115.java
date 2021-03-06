package twilightforest.block;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.stats.Stats;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import twilightforest.item.TFItems;

import java.util.Random;

public class BlockTFExperiment115 extends Block {

    public static final IntegerProperty NOMS = IntegerProperty.create("omnomnom", 0, 7);
    public static final BooleanProperty REGENERATE = BooleanProperty.create("regenerate");

    private static final VoxelShape[] AABB = new VoxelShape[] {
			VoxelShapes.create(new AxisAlignedBB(0.0625D, 0.0D, 0.0625D, 0.9375D, 0.5D, 0.9375D)),
            VoxelShapes.create(new AxisAlignedBB(0.0625D, 0.0D, 0.0625D, 0.5D, 0.5D, 0.9375D)),
            VoxelShapes.create(new AxisAlignedBB(0.0625D, 0.0D, 0.0625D, 0.5D, 0.5D, 0.5D))
    };

    public BlockTFExperiment115() {
        super(Properties.create(Material.CAKE, MaterialColor.IRON).hardnessAndResistance(0.5F).sound(SoundType.CLOTH).tickRandomly());
        this.setDefaultState(this.stateContainer.getBaseState().with(NOMS, 7).with(REGENERATE, false));
    }

    @Override
    public ItemStack getPickBlock(BlockState state, RayTraceResult target, IBlockReader world, BlockPos pos, PlayerEntity player) {
		return new ItemStack(TFItems.experiment_115.get());
    }

	@Override
	@Deprecated
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
		switch (state.get(NOMS)) {
			default:
				return AABB[0];
			case 4:
			case 5:
				return AABB[1];
			case 6:
			case 7:
				return AABB[2];
		}
	}

	//TODO: Check this
//	@Override
//	@Deprecated
//	public boolean isSolid(BlockState state) {
//		return false;
//	}

	@Override
	@Deprecated
	public ActionResultType onUse(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult hit) {
		int bitesTaken = state.get(NOMS);
		ItemStack stack = player.getHeldItem(hand);

		if (!player.isSneaking()) {
			if (bitesTaken > 0 && stack.getItem() == TFItems.experiment_115.get()) {
				worldIn.setBlockState(pos, state.with(NOMS, bitesTaken - 1));
				if (!player.isCreative()) stack.shrink(1);
				if (player instanceof ServerPlayerEntity) CriteriaTriggers.PLACED_BLOCK.trigger((ServerPlayerEntity) player, pos, stack);
				return ActionResultType.SUCCESS;
			} else if (((!state.get(REGENERATE)) && stack.getItem() == Items.REDSTONE) && (player.isCreative() || bitesTaken == 0)) {
				worldIn.setBlockState(pos, state.with(REGENERATE,true));
				if (!player.isCreative()) stack.shrink(1);
				if (player instanceof ServerPlayerEntity) CriteriaTriggers.PLACED_BLOCK.trigger((ServerPlayerEntity) player, pos, stack);
				return ActionResultType.SUCCESS;
			}
		}

		//TODO: Result must be ActionResultType
		return this.eatCake(worldIn, pos, state, player) || stack.isEmpty();
	}

	private ActionResultType eatCake(World world, BlockPos pos, BlockState state, PlayerEntity player) {
        if (!player.canEat(false)) return ActionResultType.PASS;
        else {
            player.addStat(Stats.EAT_CAKE_SLICE);
            player.getFoodStats().addStats(4, 0.3F);
            int i = state.get(NOMS);

            if (i < 7) world.setBlockState(pos, state.with(NOMS, i + 1), 3);
            else       world.removeBlock(pos, false);

            if (player instanceof ServerPlayerEntity)
                CriteriaTriggers.CONSUME_ITEM.trigger((ServerPlayerEntity) player, new ItemStack(TFItems.experiment_115.get(), 8 - i));

            return ActionResultType.SUCCESS;
        }
    }

	@Override
	@Deprecated
	public void scheduledTick(BlockState state, ServerWorld worldIn, BlockPos pos, Random random) {
		if (state.get(REGENERATE) && state.get(NOMS) != 0) {
			worldIn.setBlockState(pos, state.with(NOMS, state.get(NOMS) - 1));
		}
	}

//	@Override
//	@Deprecated
//	public void neighborChanged(BlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos, boolean isMoving) {
//		if (!this.canBlockStay(worldIn, pos)) {
//			worldIn.removeBlock(pos, false);
//		}
//	}

	@Override
	@Deprecated
	public boolean isValidPosition(BlockState state, IWorldReader worldIn, BlockPos pos) {
		return worldIn.getBlockState(pos.down()).getMaterial().isSolid();
	}

	@Override
	@Deprecated
	public BlockState updatePostPlacement(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos) {
		return facing == Direction.DOWN && !stateIn.isValidPosition(worldIn, currentPos) ? Blocks.AIR.getDefaultState() : super.updatePostPlacement(stateIn, facing, facingState, worldIn, currentPos, facingPos);
	}

//    @Override
//    public int quantityDropped(Random random)
//    {
//        return 0;
//    }

	@Override
	protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
		builder.add(NOMS, REGENERATE);
	}

	//TODO: Move to client
//	@Override
//    @OnlyIn(Dist.CLIENT)
//    public BlockRenderLayer getRenderLayer() {
//        return BlockRenderLayer.CUTOUT;
//    }

    @Override
	@Deprecated
    public int getComparatorInputOverride(BlockState state, World world, BlockPos pos) {
        return 15-(state.get(NOMS)*2);
    }

    @Override
	@Deprecated
    public boolean hasComparatorInputOverride(BlockState state)
    {
        return true;
    }

    @Override
	@Deprecated
    public boolean canProvidePower(BlockState state) {
        return state.get(REGENERATE);
    }

	@Override
	@Deprecated
	public int getWeakPower(BlockState state, IBlockReader blockAccess, BlockPos pos, Direction side) {
		return state.get(REGENERATE) ? 15-(state.get(NOMS)*2) : 0;
	}

//	@Override
//    @OnlyIn(Dist.CLIENT)
//    public void registerModel() {
//        ModelLoader.setCustomModelResourceLocation(TFItems.experiment_115, 0, new ModelResourceLocation(TwilightForestMod.ID + ":experiment_115", "inventory"));
//        ModelLoader.setCustomModelResourceLocation(TFItems.experiment_115, 1, new ModelResourceLocation(TwilightForestMod.ID + ":experiment_115", "inventory_full"));
//        ModelLoader.setCustomModelResourceLocation(TFItems.experiment_115, 2, new ModelResourceLocation(TwilightForestMod.ID + ":experiment_115", "inventory_think"));
//
//        // Sorry, just gonna tack this here for now. The Shielding Scepter doesn't allow me to tack on another model like I would here. I'd like to not make a placeholder item either >.>
//        // TODO 1.14: Well...we may need a placeholder item
//        ModelLoader.setCustomModelResourceLocation(TFItems.experiment_115, 3, new ModelResourceLocation(TwilightForestMod.ID + ":shield", "inventory"));
//    }
}
