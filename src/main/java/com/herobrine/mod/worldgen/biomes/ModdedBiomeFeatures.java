package com.herobrine.mod.worldgen.biomes;

import com.google.common.collect.ImmutableList;
import com.herobrine.mod.HerobrineMod;
import net.minecraft.block.Blocks;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.biome.BiomeGenerationSettings;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.blockstateprovider.SimpleBlockStateProvider;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.foliageplacer.BlobFoliagePlacer;
import net.minecraft.world.gen.placement.AtSurfaceWithExtraConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraft.world.gen.trunkplacer.StraightTrunkPlacer;
import org.jetbrains.annotations.NotNull;

public class ModdedBiomeFeatures {
    public static final ConfiguredFeature<BaseTreeFeatureConfig, ?> CURSED_OAK_CONFIG = register("cursed_oak_config", Feature.TREE.configured((new BaseTreeFeatureConfig.Builder(new SimpleBlockStateProvider(Blocks.OAK_LOG.defaultBlockState()), new SimpleBlockStateProvider(Blocks.AIR.defaultBlockState()), new BlobFoliagePlacer(FeatureSpread.fixed(2), FeatureSpread.fixed(0), 3), new StraightTrunkPlacer(4, 2, 0), new TwoLayerFeature(1, 0, 1))).ignoreVines().build()));
    public static final ConfiguredFeature<BaseTreeFeatureConfig, ?> CURSED_OAK_TREE = register("cursed_oak_tree", Feature.TREE.configured(CURSED_OAK_CONFIG.config()));
    public static final ConfiguredFeature<BaseTreeFeatureConfig, ?> CURSED_BIRCH_CONFIG = register("cursed_birch_config", Feature.TREE.configured((new BaseTreeFeatureConfig.Builder(new SimpleBlockStateProvider(Blocks.BIRCH_LOG.defaultBlockState()), new SimpleBlockStateProvider(Blocks.AIR.defaultBlockState()), new BlobFoliagePlacer(FeatureSpread.fixed(2), FeatureSpread.fixed(0), 3), new StraightTrunkPlacer(5, 2, 0), new TwoLayerFeature(1, 0, 1))).ignoreVines().build()));
    public static final ConfiguredFeature<BaseTreeFeatureConfig, ?> CURSED_BIRCH_TREE = register("cursed_birch_tree", Feature.TREE.configured(CURSED_BIRCH_CONFIG.config()));
    public static final ConfiguredFeature<?, ?> CURSED_TREES = register("cursed_trees", Feature.RANDOM_SELECTOR.configured(new MultipleRandomFeatureConfig(ImmutableList.of(CURSED_BIRCH_TREE.weighted(0.2F)), CURSED_OAK_TREE)).decorated(Features.Placements.HEIGHTMAP_SQUARE).decorated(Placement.COUNT_EXTRA.configured(new AtSurfaceWithExtraConfig(10, 0.1F, 1))));

    private static <FC extends IFeatureConfig> @NotNull ConfiguredFeature<FC, ?> register(String key, ConfiguredFeature<FC, ?> configuredFeature) {
        return Registry.register(WorldGenRegistries.CONFIGURED_FEATURE, HerobrineMod.MODID + ":" + key, configuredFeature);
    }

    public static void withCursedTrees(BiomeGenerationSettings.@NotNull Builder builder) {
        builder.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, CURSED_TREES);
    }
}
