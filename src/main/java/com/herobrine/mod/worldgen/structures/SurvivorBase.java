package com.herobrine.mod.worldgen.structures;

import com.herobrine.mod.HerobrineMod;
import com.herobrine.mod.config.Config;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.Mirror;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.feature.template.PlacementSettings;
import net.minecraft.world.gen.feature.template.Template;
import net.minecraft.world.gen.placement.IPlacementConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.BiomeDictionary;
import org.jetbrains.annotations.NotNull;

import java.util.Random;
import java.util.Set;

public class SurvivorBase {
    public static void registerStructure() {
        Feature<NoFeatureConfig> feature = new Feature<NoFeatureConfig>(NoFeatureConfig::deserialize) {
            @Override
            public boolean place(@NotNull IWorld iworld, @NotNull ChunkGenerator generator, @NotNull Random random, @NotNull BlockPos pos, @NotNull NoFeatureConfig config) {
                int ci = pos.getX();
                int ck = pos.getZ();
                if ((random.nextInt(1000000) + 1) <= Config.COMMON.SurvivorBaseWeight.get()) {
                    int count = random.nextInt(1) + 1;
                    for (int a = 0; a < count; a++) {
                        int i = ci + random.nextInt(16) + 8;
                        int k = ck + random.nextInt(16) + 8;
                        int j = iworld.getHeight(Heightmap.Type.OCEAN_FLOOR_WG, i, k);
                        j -= 1;
                        BlockState blockAt = iworld.getBlockState(new BlockPos(i, j, k));
                        boolean blockCriteria = false;
                        if (blockAt.getBlock() == Blocks.GRASS_BLOCK.getDefaultState().getBlock() || blockAt.getBlock() == Blocks.DIRT.getDefaultState().getBlock())
                            blockCriteria = true;
                        if (!blockCriteria)
                            continue;
                        Template template;
                        if(random.nextInt(10) >= 1) {
                            if(random.nextBoolean()) {
                                template = ((ServerWorld) iworld.getWorld()).getSaveHandler().getStructureTemplateManager().getTemplateDefaulted(new ResourceLocation(HerobrineMod.MODID, "survivor_base_steve"));
                            } else template = ((ServerWorld) iworld.getWorld()).getSaveHandler().getStructureTemplateManager().getTemplateDefaulted(new ResourceLocation(HerobrineMod.MODID, "survivor_base_alex"));
                        } else template = ((ServerWorld) iworld.getWorld()).getSaveHandler().getStructureTemplateManager().getTemplateDefaulted(new ResourceLocation(HerobrineMod.MODID, "survivor_base_abandoned"));
                        Rotation rotation = Rotation.values()[random.nextInt(3)];
                        Mirror mirror = Mirror.values()[random.nextInt(2)];
                        BlockPos spawnTo = new BlockPos(i, j -1, k);
                        template.addBlocksToWorldChunk(iworld, spawnTo, new PlacementSettings().setRotation(rotation).setRandom(random).setMirror(mirror).setIgnoreEntities(false));
                    }
                }
                return true;
            }
        };
        BiomeDictionary.Type[] Biome = {
                BiomeDictionary.Type.PLAINS
        };
        for (BiomeDictionary.Type biomeType : Biome) {
            Set<Biome> biome = BiomeDictionary.getBiomes(biomeType);
            for (Biome currentBiome : biome) {
                currentBiome.addFeature(GenerationStage.Decoration.SURFACE_STRUCTURES, feature.withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG).withPlacement(Placement.NOPE.configure(IPlacementConfig.NO_PLACEMENT_CONFIG)));
            }
        }
    }
}
