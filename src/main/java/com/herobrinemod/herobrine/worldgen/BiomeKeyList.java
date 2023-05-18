package com.herobrinemod.herobrine.worldgen;

import com.herobrinemod.herobrine.HerobrineMod;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.minecraft.world.biome.Biome;

public class BiomeKeyList {
    public static final RegistryKey<Biome> CURSED_FOREST = RegistryKey.of(RegistryKeys.BIOME, new Identifier(HerobrineMod.MODID, "cursed_forest"));
}
