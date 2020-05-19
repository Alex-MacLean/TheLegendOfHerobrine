package com.herobrine.mod.util.worldgen;

import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import org.jetbrains.annotations.NotNull;

public class ModFeatures {
    public static final Feature<NoFeatureConfig> CURSED_TREE = register("cursed_tree", new CursedTreeFeature(NoFeatureConfig::deserialize, false, false));
    public static final Feature<NoFeatureConfig> CURSED_BIRCH_TREE = register("cursed_birch_tree", new CursedBirchTreeFeature(NoFeatureConfig::deserialize, false, false));

    @SuppressWarnings("unchecked")
    private static <C extends IFeatureConfig, F extends Feature<C>> @NotNull F register(String key, F value) {
        return (F) (Registry.<Feature<?>>register(Registry.FEATURE, key, value));
    }
}
