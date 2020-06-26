package com.herobrine.mod.worldgen.structures;

import com.herobrine.mod.HerobrineMod;
import com.herobrine.mod.config.Config;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.tags.BlockTags;
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

public class ShrineRemnants {

    public static void registerStructure() {
        Feature<NoFeatureConfig> feature = new Feature<NoFeatureConfig>(NoFeatureConfig::deserialize) {
            @Override
            public boolean place(@NotNull IWorld iworld, @NotNull ChunkGenerator generator, @NotNull Random random, @NotNull BlockPos pos, @NotNull NoFeatureConfig config) {
                int ci = pos.getX();
                int ck = pos.getZ();
                if ((random.nextInt(1000000) + 1) <= Config.COMMON.ShrineRemnantSpawnWeight.get()) {
                    int count = random.nextInt(1) + 1;
                    for (int a = 0; a < count; a++) {
                        int i = ci + random.nextInt(16) + 8;
                        int k = ck + random.nextInt(16) + 8;
                        int j = iworld.getHeight(Heightmap.Type.OCEAN_FLOOR_WG, i, k);
                        j -= 1;
                        BlockState blockAt = iworld.getBlockState(new BlockPos(i, j, k));
                        boolean blockCriteria = false;
                        if (blockAt.getMaterial() != Material.LAVA && blockAt.getMaterial() != Material.WATER && !BlockTags.ANVIL.contains(blockAt.getBlock()) && !BlockTags.BANNERS.contains(blockAt.getBlock()) && !BlockTags.BEDS.contains(blockAt.getBlock()) && !BlockTags.BIRCH_LOGS.contains(blockAt.getBlock()) && !BlockTags.BUTTONS.contains(blockAt.getBlock()) && !BlockTags.CARPETS.contains(blockAt.getBlock()) && !BlockTags.CORAL_BLOCKS.contains(blockAt.getBlock()) && !BlockTags.CORAL_PLANTS.contains(blockAt.getBlock()) && !BlockTags.CORALS.contains(blockAt.getBlock()) && !BlockTags.ACACIA_LOGS.contains(blockAt.getBlock()) && !BlockTags.DARK_OAK_LOGS.contains(blockAt.getBlock()) && !BlockTags.DOORS.contains(blockAt.getBlock()) && !BlockTags.FENCES.contains(blockAt.getBlock()) && !BlockTags.FLOWER_POTS.contains(blockAt.getBlock()) && !BlockTags.JUNGLE_LOGS.contains(blockAt.getBlock()) && !BlockTags.LEAVES.contains(blockAt.getBlock()) && !BlockTags.LOGS.contains(blockAt.getBlock()) && !BlockTags.OAK_LOGS.contains(blockAt.getBlock()) && !BlockTags.PLANKS.contains(blockAt.getBlock()) && !BlockTags.RAILS.contains(blockAt.getBlock()) && !BlockTags.SAPLINGS.contains(blockAt.getBlock()) && !BlockTags.SIGNS.contains(blockAt.getBlock()) && !BlockTags.SLABS.contains(blockAt.getBlock()) && !BlockTags.SMALL_FLOWERS.contains(blockAt.getBlock()) && !BlockTags.SPRUCE_LOGS.contains(blockAt.getBlock()) && !BlockTags.STAIRS.contains(blockAt.getBlock()) && !BlockTags.STANDING_SIGNS.contains(blockAt.getBlock()) && !BlockTags.TRAPDOORS.contains(blockAt.getBlock()) && !BlockTags.UNDERWATER_BONEMEALS.contains(blockAt.getBlock()) && !BlockTags.WALL_CORALS.contains(blockAt.getBlock()) && !BlockTags.WALL_SIGNS.contains(blockAt.getBlock()) && !BlockTags.WALLS.contains(blockAt.getBlock()) && !BlockTags.WOODEN_BUTTONS.contains(blockAt.getBlock()) && !BlockTags.WOODEN_DOORS.contains(blockAt.getBlock()) && !BlockTags.WOODEN_FENCES.contains(blockAt.getBlock()) && !BlockTags.WOODEN_PRESSURE_PLATES.contains(blockAt.getBlock()) && !BlockTags.WOODEN_SLABS.contains(blockAt.getBlock()) && !BlockTags.WOODEN_STAIRS.contains(blockAt.getBlock()) && !BlockTags.WOODEN_TRAPDOORS.contains(blockAt.getBlock()) && !BlockTags.WOOL.contains(blockAt.getBlock()))
                            blockCriteria = true;
                        if (!blockCriteria)
                            continue;
                        int type = random.nextInt(4);
                        Template template = new Template();
                        if(type == 0) {
                            template = ((ServerWorld) iworld.getWorld()).getSaveHandler().getStructureTemplateManager().getTemplateDefaulted(new ResourceLocation(HerobrineMod.MODID, "ruined_shrine"));
                        } else if(type == 1) {
                            template = ((ServerWorld) iworld.getWorld()).getSaveHandler().getStructureTemplateManager().getTemplateDefaulted(new ResourceLocation(HerobrineMod.MODID, "ruined_shrine_alt"));
                        } else if(type == 2) {
                            template = ((ServerWorld) iworld.getWorld()).getSaveHandler().getStructureTemplateManager().getTemplateDefaulted(new ResourceLocation(HerobrineMod.MODID, "ruined_shrine_alt_1"));
                        } else if(type == 3) {
                            template = ((ServerWorld) iworld.getWorld()).getSaveHandler().getStructureTemplateManager().getTemplateDefaulted(new ResourceLocation(HerobrineMod.MODID, "ruined_shrine_alt_2"));
                        } else if(type == 4) {
                            template = ((ServerWorld) iworld.getWorld()).getSaveHandler().getStructureTemplateManager().getTemplateDefaulted(new ResourceLocation(HerobrineMod.MODID, "ruined_shrine_alt_3"));
                        }
                        Rotation rotation = Rotation.values()[random.nextInt(3)];
                        Mirror mirror = Mirror.values()[random.nextInt(2)];
                        BlockPos spawnTo = new BlockPos(i, j, k);
                        template.addBlocksToWorldChunk(iworld, spawnTo, new PlacementSettings().setRotation(rotation).setRandom(random).setMirror(mirror).setIgnoreEntities(false));
                    }
                }
                return true;
            }
        };
        BiomeDictionary.Type[] Biomes = {
                BiomeDictionary.Type.SPOOKY,
        };
        for (BiomeDictionary.Type biomeType : Biomes) {
            Set<Biome> biome = BiomeDictionary.getBiomes(biomeType);
            for (Biome currentBiome : biome) {
                currentBiome.addFeature(GenerationStage.Decoration.SURFACE_STRUCTURES, Biome.createDecoratedFeature(feature, IFeatureConfig.NO_FEATURE_CONFIG, Placement.NOPE, IPlacementConfig.NO_PLACEMENT_CONFIG));
            }
        }
    }
}