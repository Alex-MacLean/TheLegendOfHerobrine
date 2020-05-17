package com.herobrine.mod.util.entities;

import com.herobrine.mod.HerobrineMod;
import com.herobrine.mod.entities.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;

import java.util.Set;

public class EntityRegistry {
    public static void registerEntities() {
        registerEntity("infected_pig", InfectedPigEntity.class, 3792185,0xF0A5A2, 0xFFFFFF);
        registerEntity("infected_sheep", InfectedSheepEntity.class, 3829195,0xE7E7E7, 0xFFFFFF);
        registerEntity("infected_chicken", InfectedChickenEntity.class, 9587294,0xA1A1A1, 0xFFFFFF);
        registerEntity("infected_cow", InfectedCowEntity.class, 5489318,0x443626, 0xFFFFFF);
        registerEntity("infected_villager", InfectedVillagerEntity.class, 6218753,0x563C33, 0xFFFFFF);
        registerEntity("infected_mooshroom", InfectedMooshroomEntity.class, 1986598,0xA00F10, 0xFFFFFF);
        registerEntity("herobrine_warrior", HerobrineWarriorEntity.class, 1742128,0x000000, 0xFF0000);
        registerEntity("herobrine_builder", HerobrineBuilderEntity.class, 4589318,0x000000, 0xFFFF00);
        registerEntity("herobrine_spy", HerobrineSpyEntity.class, 3792584, 0x000000,0x00FF00);
        registerEntity("herobrine_mage", HerobrineMageEntity.class, 9572941,0x000000,0x0000FF);
        registerEntityWithoutEgg("fake_herobrine_mage", FakeHerobrineMageEntity.class,2486184);
        registerEntityWithoutEgg("holy_water", HolyWaterEntity.class, 8929428);
        registerEntityWithoutEgg("unholy_water", UnholyWaterEntity.class, 1749582);
    }

    private static void registerEntity(String name, Class<? extends Entity> entity, int id, int color1, int color2) {
        net.minecraftforge.fml.common.registry.EntityRegistry.registerModEntity(new ResourceLocation(HerobrineMod.MODID + ":" + name), entity, name, id, HerobrineMod.instance, 8192, 1, true, color1, color2);
    }

    private static void registerEntityWithoutEgg(String name, Class<? extends Entity> entity, int id) {
        net.minecraftforge.fml.common.registry.EntityRegistry.registerModEntity(new ResourceLocation(HerobrineMod.MODID + ":" + name), entity, name, id, HerobrineMod.instance, 8192, 1, true);
    }

    public static void registerEntityWorldSpawns() {
        BiomeDictionary.Type[] InfectedVillagerTypes = {
                BiomeDictionary.Type.COLD,
                BiomeDictionary.Type.CONIFEROUS,
                BiomeDictionary.Type.DRY,
                BiomeDictionary.Type.HOT,
                BiomeDictionary.Type.PLAINS,
                BiomeDictionary.Type.SANDY,
                BiomeDictionary.Type.SAVANNA,
                BiomeDictionary.Type.SNOWY,
                BiomeDictionary.Type.SPARSE
        };

        BiomeDictionary.Type[] InfectedAnimalTypes = {
                BiomeDictionary.Type.BEACH,
                BiomeDictionary.Type.COLD,
                BiomeDictionary.Type.CONIFEROUS,
                BiomeDictionary.Type.DENSE,
                BiomeDictionary.Type.FOREST,
                BiomeDictionary.Type.HILLS,
                BiomeDictionary.Type.JUNGLE,
                BiomeDictionary.Type.LUSH,
                BiomeDictionary.Type.MAGICAL,
                BiomeDictionary.Type.MOUNTAIN,
                BiomeDictionary.Type.PLAINS,
                BiomeDictionary.Type.SAVANNA,
                BiomeDictionary.Type.SNOWY,
                BiomeDictionary.Type.SPARSE,
                BiomeDictionary.Type.SPOOKY,
                BiomeDictionary.Type.SWAMP
        };

        BiomeDictionary.Type[] HerobrineTypes = {
                BiomeDictionary.Type.BEACH,
                BiomeDictionary.Type.COLD,
                BiomeDictionary.Type.CONIFEROUS,
                BiomeDictionary.Type.DEAD,
                BiomeDictionary.Type.DENSE,
                BiomeDictionary.Type.DRY,
                BiomeDictionary.Type.FOREST,
                BiomeDictionary.Type.HILLS,
                BiomeDictionary.Type.HOT,
                BiomeDictionary.Type.JUNGLE,
                BiomeDictionary.Type.LUSH,
                BiomeDictionary.Type.MAGICAL,
                BiomeDictionary.Type.MESA,
                BiomeDictionary.Type.MOUNTAIN,
                BiomeDictionary.Type.OCEAN,
                BiomeDictionary.Type.PLAINS,
                BiomeDictionary.Type.RARE,
                BiomeDictionary.Type.RIVER,
                BiomeDictionary.Type.SANDY,
                BiomeDictionary.Type.SAVANNA,
                BiomeDictionary.Type.SNOWY,
                BiomeDictionary.Type.SPARSE,
                BiomeDictionary.Type.SPOOKY,
                BiomeDictionary.Type.SWAMP,
                BiomeDictionary.Type.VOID,
                BiomeDictionary.Type.WASTELAND,
                BiomeDictionary.Type.WATER,
                BiomeDictionary.Type.WET,
                BiomeDictionary.Type.END,
                BiomeDictionary.Type.NETHER,
        };

        BiomeDictionary.Type[] MushroomBiomeTypes = {
                BiomeDictionary.Type.MUSHROOM
        };

        for (BiomeDictionary.Type t : InfectedVillagerTypes) {
            Set<Biome> biomes = BiomeDictionary.getBiomes(t);
            for (Biome currentBiome : biomes) {
                currentBiome.getSpawnableList(EnumCreatureType.MONSTER).add(new Biome.SpawnListEntry(InfectedVillagerEntity.class, 4, 1, 4));
            }
        }

        for (BiomeDictionary.Type t : InfectedAnimalTypes) {
            Set<Biome> biomes = BiomeDictionary.getBiomes(t);
            for (Biome currentBiome : biomes) {
                currentBiome.getSpawnableList(EnumCreatureType.MONSTER).add(new Biome.SpawnListEntry(InfectedPigEntity.class, 6, 3, 6));
                currentBiome.getSpawnableList(EnumCreatureType.MONSTER).add(new Biome.SpawnListEntry(InfectedCowEntity.class, 6, 2, 4));
                currentBiome.getSpawnableList(EnumCreatureType.MONSTER).add(new Biome.SpawnListEntry(InfectedChickenEntity.class, 6, 4, 8));
                currentBiome.getSpawnableList(EnumCreatureType.MONSTER).add(new Biome.SpawnListEntry(InfectedSheepEntity.class, 6, 3, 6));
            }
        }

        for (BiomeDictionary.Type t : MushroomBiomeTypes) {
            Set<Biome> biomes = BiomeDictionary.getBiomes(t);
            for (Biome currentBiome : biomes) {
                currentBiome.getSpawnableList(EnumCreatureType.MONSTER).add(new Biome.SpawnListEntry(HerobrineBuilderEntity.class, 1, 1, 1));
                currentBiome.getSpawnableList(EnumCreatureType.MONSTER).add(new Biome.SpawnListEntry(HerobrineMageEntity.class, 1, 1, 1));
                currentBiome.getSpawnableList(EnumCreatureType.MONSTER).add(new Biome.SpawnListEntry(HerobrineSpyEntity.class, 1, 1, 1));
                currentBiome.getSpawnableList(EnumCreatureType.MONSTER).add(new Biome.SpawnListEntry(HerobrineWarriorEntity.class, 1, 1, 1));
                currentBiome.getSpawnableList(EnumCreatureType.MONSTER).add(new Biome.SpawnListEntry(InfectedMooshroomEntity.class, 1, 1, 1));
            }
        }

        for (BiomeDictionary.Type t : HerobrineTypes) {
            Set<Biome> biomes = BiomeDictionary.getBiomes(t);
            for (Biome currentBiome : biomes) {
                currentBiome.getSpawnableList(EnumCreatureType.MONSTER).add(new Biome.SpawnListEntry(HerobrineBuilderEntity.class, 2, 1, 1));
                currentBiome.getSpawnableList(EnumCreatureType.MONSTER).add(new Biome.SpawnListEntry(HerobrineMageEntity.class, 2, 1, 1));
                currentBiome.getSpawnableList(EnumCreatureType.MONSTER).add(new Biome.SpawnListEntry(HerobrineSpyEntity.class, 3, 1, 1));
                currentBiome.getSpawnableList(EnumCreatureType.MONSTER).add(new Biome.SpawnListEntry(HerobrineWarriorEntity.class, 2, 1, 1));
            }
        }
    }
}