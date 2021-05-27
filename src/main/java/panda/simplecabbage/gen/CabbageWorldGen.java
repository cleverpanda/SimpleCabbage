package panda.simplecabbage.gen;

import java.util.List;
import java.util.Random;

import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.gen.structure.StructureComponent;
import net.minecraft.world.gen.structure.StructureVillagePieces;
import net.minecraft.world.gen.structure.StructureVillagePieces.PieceWeight;
import net.minecraft.world.gen.structure.StructureVillagePieces.Start;
import net.minecraft.world.gen.structure.StructureVillagePieces.Village;
import net.minecraftforge.fml.common.registry.VillagerRegistry.IVillageCreationHandler;
import panda.simplecabbage.ConfigSimpleCabbage;

public class CabbageWorldGen implements IVillageCreationHandler {

	@Override
	public PieceWeight getVillagePieceWeight(Random random, int i) {
        return  new StructureVillagePieces.PieceWeight(ComponentCabbageField.class, ConfigSimpleCabbage.generationWeight, MathHelper.getInt(random, 2 + i, 4 + i * 2));
	}

	@Override
	public Class<?> getComponentClass() {
		return ComponentCabbageField.class;
	}

	@Override
	public Village buildComponent(PieceWeight villagePiece, Start startPiece,List<StructureComponent> pieces, Random random, int x, int y, int z,EnumFacing facing, int p5) {
		return ComponentCabbageField.createPiece(startPiece, pieces, random, x, y, z, facing, p5);
	}
}
