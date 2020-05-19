package com.herobrine.mod.util.worldgen;

import com.herobrine.mod.worldgen.biomes.CursedForest;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeManager;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public class BiomeInit {
    public static final Biome CURSED_FOREST = new CursedForest();

    public static void registerBiomes() {
        registerBiome(CURSED_FOREST, "cursed_forest", BiomeManager.BiomeType.WARM, 4, BiomeDictionary.Type.FOREST, BiomeDictionary.Type.DEAD, BiomeDictionary.Type.SPOOKY, BiomeDictionary.Type.WASTELAND, BiomeDictionary.Type.MAGICAL, BiomeDictionary.Type.RARE);
    }

    @Contract("_, _, _, _, _ -> param1")
    @SuppressWarnings("UnusedReturnValue")
    public static @NotNull Biome registerBiome(@NotNull Biome biome, String name, BiomeManager.BiomeType biomeType, int weight, BiomeDictionary.Type... types) {
        biome.setRegistryName(name);
        ForgeRegistries.BIOMES.register(biome);
        BiomeDictionary.addTypes(biome, types);
        BiomeManager.addBiome(biomeType, new BiomeManager.BiomeEntry(biome, weight));
        BiomeManager.addSpawnBiome(biome);
        return biome;
    }
}