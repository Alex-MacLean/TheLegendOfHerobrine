package com.herobrine.mod.worldgen.structures;

public class TrappedHouse {
/*    public static void registerStructure() {
        Feature<NoFeatureConfig> feature = new Feature<NoFeatureConfig>(NoFeatureConfig::deserialize) {
            @Override
            public boolean generate(ISeedReader reader, ChunkGenerator generator, Random random, BlockPos pos, FC config) {
                int ci = pos.getX();
                int ck = pos.getZ();
                if ((random.nextInt(1000000) + 1) <= Config.COMMON.TrappedHouseWeight.get()) {
                    int count = random.nextInt(1) + 1;
                    for (int a = 0; a < count; a++) {
                        int i = ci + random.nextInt(16) + 8;
                        int k = ck + random.nextInt(16) + 8;
                        int j = serverWorld.getHeight(Heightmap.Type.OCEAN_FLOOR_WG, i, k);
                        j -= 1;
                        BlockState blockAt = serverWorld.getBlockState(new BlockPos(i, j, k));
                        boolean blockCriteria = false;
                        if (blockAt.getBlock() == Blocks.GRASS_BLOCK.getDefaultState().getBlock())
                            blockCriteria = true;
                        if (!blockCriteria)
                            continue;
                        Template template = serverWorld.getWorld().getStructureTemplateManager().getTemplateDefaulted(new ResourceLocation(HerobrineMod.MODID, "trapped_house"));
                        Rotation rotation = Rotation.values()[random.nextInt(3)];
                        Mirror mirror = Mirror.values()[random.nextInt(2)];
                        BlockPos spawnTo = new BlockPos(i, j - 2, k);
                        template.func_237144_a_(serverWorld, spawnTo, new PlacementSettings().setRotation(rotation).setMirror(mirror).setIgnoreEntities(false), random);
                    }
                }
                return true;
            }
        };
        for (Biome biome : ForgeRegistries.BIOMES.getValues()) {
            biome.addFeature(GenerationStage.Decoration.SURFACE_STRUCTURES, feature.withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG).withPlacement(Placement.NOPE.configure(IPlacementConfig.NO_PLACEMENT_CONFIG)));
        }
    }*/
}