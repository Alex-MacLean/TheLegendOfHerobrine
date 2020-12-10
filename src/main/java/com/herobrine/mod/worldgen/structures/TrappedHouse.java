package com.herobrine.mod.worldgen.structures;

/*
public class TrappedHouse extends Feature<NoFeatureConfig> {
    public TrappedHouse(Codec<NoFeatureConfig> codec) {
        super(codec);
    }
    //private static final RegistryKey<Feature<?>> TRAPPED_HOUSE = RegistryKey.getOrCreateKey(Registry.FEATURE_KEY, HerobrineMod.location("trapped_house"));

    @Override
    public boolean generate(ISeedReader reader, @NotNull ChunkGenerator generator, Random random, BlockPos pos, @NotNull NoFeatureConfig config) {
        int ci = pos.getX();
        int ck = pos.getZ();
        ServerWorld world = reader.getWorld();
        if ((random.nextInt(1000000) + 1) <= Config.COMMON.TrappedHouseWeight.get()) {
            int count = random.nextInt(1) + 1;
            for (int a = 0; a < count; a++) {
                int i = ci + random.nextInt(16) + 8;
                int k = ck + random.nextInt(16) + 8;
                int j = world.getHeight(Heightmap.Type.OCEAN_FLOOR_WG, i, k);
                j -= 1;
                BlockState blockAt = world.getBlockState(new BlockPos(i, j, k));
                boolean blockCriteria = false;
                if (blockAt.getBlock() == Blocks.GRASS_BLOCK.getDefaultState().getBlock())
                    blockCriteria = true;
                if (!blockCriteria)
                    continue;
                Template template = world.getWorld().getStructureTemplateManager().getTemplateDefaulted(new ResourceLocation(HerobrineMod.MODID, "trapped_house"));
                Rotation rotation = Rotation.values()[random.nextInt(3)];
                Mirror mirror = Mirror.values()[random.nextInt(2)];
                BlockPos spawnTo = new BlockPos(i, j - 2, k);
                template.func_237144_a_(world, spawnTo, new PlacementSettings().setRotation(rotation).setMirror(mirror).setIgnoreEntities(false), random);
            }
        }
        return true;
    }
}*/
