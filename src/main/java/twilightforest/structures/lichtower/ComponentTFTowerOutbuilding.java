package twilightforest.structures.lichtower;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.Direction;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.IWorld;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.gen.feature.structure.StructurePiece;
import twilightforest.TFFeature;

import java.util.List;
import java.util.Random;

public class ComponentTFTowerOutbuilding extends ComponentTFTowerWing {

	public ComponentTFTowerOutbuilding() {
		super();
	}

	protected ComponentTFTowerOutbuilding(TFFeature feature, int i, int x, int y, int z, int pSize, int pHeight, Direction direction) {
		super(feature, i, x, y, z, pSize, pHeight, direction);
	}

	/**
	 * NO BEARDS!
	 */
	@Override
	public void makeABeard(StructurePiece parent, List<StructurePiece> list, Random rand) {
		return;
	}

	/**
	 * Outbuildings should not make new wings close to the ground.
	 */
	@Override
	public boolean makeTowerWing(List<StructurePiece> list, Random rand, int index, int x, int y, int z, int wingSize, int wingHeight, Rotation direction) {
		if (y > 7) {
			return super.makeTowerWing(list, rand, index, x, y, z, wingSize, wingHeight, direction);
		} else {
			return false;
		}
	}

	@Override
	public boolean addComponentParts(IWorld world, Random rand, MutableBoundingBox sbb, ChunkPos chunkPosIn) {
		final BlockState cobblestone = Blocks.COBBLESTONE.getDefaultState();
		for (int x = 0; x < this.size; x++) {
			for (int z = 0; z < this.size; z++) {
				this.replaceAirAndLiquidDownwards(world, cobblestone, x, -1, z, sbb);
			}
		}
		return super.addComponentParts(world, rand, sbb, chunkPosIn);
	}
}
