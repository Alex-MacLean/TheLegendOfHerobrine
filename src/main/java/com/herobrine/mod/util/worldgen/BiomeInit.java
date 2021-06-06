package com.herobrine.mod.util.worldgen;

import com.herobrine.mod.HerobrineMod;
import com.herobrine.mod.config.Config;
import com.herobrine.mod.worldgen.biomes.ModdedBiomeMaker;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeManager;
import net.minecraftforge.registries.ForgeRegistries;

public class BiomeInit {
    private static final RegistryKey<Biome> CURSED_FOREST = RegistryKey.create(Registry.BIOME_REGISTRY, HerobrineMod.location("cursed_forest"));
    public static void registerBiomes() {
        ForgeRegistries.BIOMES.register(ModdedBiomeMaker.makeCursedForestBiome(0.1F, 0.2F).setRegistryName(HerobrineMod.MODID, "cursed_forest"));
        BiomeManager.addBiome(BiomeManager.BiomeType.WARM, new BiomeManager.BiomeEntry(CURSED_FOREST, Config.COMMON.CursedForestWeight.get()));
        BiomeDictionary.addTypes(CURSED_FOREST, BiomeDictionary.Type.FOREST, BiomeDictionary.Type.OVERWORLD, BiomeDictionary.Type.DEAD, BiomeDictionary.Type.WASTELAND, BiomeDictionary.Type.MAGICAL, BiomeDictionary.Type.RARE);
    }
}