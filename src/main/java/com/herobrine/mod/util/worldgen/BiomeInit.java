package com.herobrine.mod.util.worldgen;

import com.herobrine.mod.HerobrineMod;
import com.herobrine.mod.config.Config;
import com.herobrine.mod.worldgen.biomes.CursedForest;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder;
import net.minecraftforge.common.BiomeManager;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class BiomeInit {
    public static final DeferredRegister<Biome> BIOMES = new DeferredRegister<>(ForgeRegistries.BIOMES, HerobrineMod.MODID);

    public static final RegistryObject<Biome> CURSED_FOREST = BIOMES.register("cursed_forest", () -> new CursedForest(new Biome.Builder().surfaceBuilder(SurfaceBuilder.DEFAULT, SurfaceBuilder.GRASS_DIRT_GRAVEL_CONFIG).precipitation(Biome.RainType.RAIN).category(Biome.Category.FOREST).depth(0.1F).scale(0.2F).temperature(0.7F).downfall(0.8F).waterColor(4159204).waterFogColor(329011).parent(null)));

    public static void registerBiomes() {
        registerBiome(CURSED_FOREST.get(), BiomeManager.BiomeType.WARM, Config.COMMON.CursedForestBiomeWeight.get(), Type.FOREST, Type.OVERWORLD, Type.DEAD, Type.SPOOKY, Type.WASTELAND, Type.MAGICAL, Type.RARE);
    }

    public static void registerBiome(Biome biome, BiomeManager.BiomeType biomeType, int weight, Type... types) {
        BiomeManager.addBiome(biomeType, new BiomeManager.BiomeEntry(biome, weight));
        BiomeDictionary.addTypes(biome, types);
        BiomeManager.addSpawnBiome(biome);
    }
}