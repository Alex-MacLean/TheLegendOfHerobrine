package com.herobrine.mod.worldgen.structures;

public class Statue {
/*    public static void registerStructure() {
        Feature<NoFeatureConfig> feature = new Feature<NoFeatureConfig>(NoFeatureConfig::deserialize) {
            @Override
            public boolean place(@NotNull IWorld iworld, @NotNull ChunkGenerator generator, @NotNull Random random, @NotNull BlockPos pos, @NotNull NoFeatureConfig config) {
                int ci = pos.getX();
                int ck = pos.getZ();
                if ((random.nextInt(1000000) + 1) <= Config.COMMON.HerobrineStatueWeight.get()) {
                    int count = random.nextInt(1) + 1;
                    for (int a = 0; a < count; a++) {
                        int i = ci + random.nextInt(16) + 8;
                        int k = ck + random.nextInt(16) + 8;
                        int j = iworld.getHeight(Heightmap.Type.OCEAN_FLOOR_WG, i, k);
                        j -= 1;
                        BlockState blockAt = iworld.getBlockState(new BlockPos(i, j, k));
                        boolean blockCriteria = false;
                        if (blockAt.getBlock() == Blocks.STONE.getDefaultState().getBlock() || blockAt.getBlock() == Blocks.GRAVEL.getDefaultState().getBlock())
                            blockCriteria = true;
                        if (!blockCriteria)
                            continue;
                        Template template = ((ServerWorld) iworld.getWorld()).getSaveHandler().getStructureTemplateManager().getTemplateDefaulted(new ResourceLocation(HerobrineMod.MODID, "herobrine_statue"));
                        Rotation rotation = Rotation.values()[random.nextInt(3)];
                        Mirror mirror = Mirror.values()[random.nextInt(2)];
                        BlockPos spawnTo = new BlockPos(i, j + 1, k);
                        template.addBlocksToWorldChunk(iworld, spawnTo, new PlacementSettings().setRotation(rotation).setRandom(random).setMirror(mirror).setIgnoreEntities(false));
                    }
                }
                return true;
            }
        };
        BiomeDictionary.Type[] Biome = {
                BiomeDictionary.Type.MOUNTAIN,
                BiomeDictionary.Type.HILLS
        };
        for (BiomeDictionary.Type biomeType : Biome) {
            Set<Biome> biome = BiomeDictionary.getBiomes(biomeType);
            for (Biome currentBiome : biome) {
                currentBiome.addFeature(GenerationStage.Decoration.SURFACE_STRUCTURES, feature.withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG).withPlacement(Placement.NOPE.configure(IPlacementConfig.NO_PLACEMENT_CONFIG)));
            }
        }
    }*/
}
