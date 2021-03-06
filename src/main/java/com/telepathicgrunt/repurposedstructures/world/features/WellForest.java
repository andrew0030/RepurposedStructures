package com.telepathicgrunt.repurposedstructures.world.features;

import com.mojang.serialization.Codec;
import com.telepathicgrunt.repurposedstructures.RepurposedStructures;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.feature.template.Template;

import java.util.Random;


public class WellForest extends WellAbstract {
    private static final float ORE_CHANCE = 0.3f;
    private static final ResourceLocation FOREST_WELL_ORE_RL = new ResourceLocation("repurposed_structures:forest_well_ores");
    private static final ResourceLocation FOREST_WELL_RL = new ResourceLocation(RepurposedStructures.MODID + ":wells/forest");

    public WellForest(Codec<NoFeatureConfig> config) {
        super(config);
    }


    public boolean generate(ISeedReader world, ChunkGenerator chunkGenerator, Random random, BlockPos position, NoFeatureConfig config) {
        // move to top land block below position
        BlockPos.Mutable mutable = new BlockPos.Mutable().setPos(position);
        for (mutable.move(Direction.UP); world.isAirBlock(mutable) && mutable.getY() > 2; ) {
            mutable.move(Direction.DOWN);
        }

        // check to make sure spot is valid and not a single block ledge
        Block block = world.getBlockState(mutable).getBlock();
        if (isSoil(block) && (!world.isAirBlock(mutable.down()) || !world.isAirBlock(mutable.down(2)))) {

            // Creates the well centered on our spot
            mutable.move(Direction.DOWN, 5);
            Template template = this.generateTemplate(FOREST_WELL_RL, world, random, mutable);
            this.handleDataBlocks(FOREST_WELL_ORE_RL, template, world, random, mutable, Blocks.STONE, ORE_CHANCE);

            return true;
        }

        return false;
    }
}