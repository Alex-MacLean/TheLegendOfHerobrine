package com.herobrine.mod.util.entities;

import com.herobrine.mod.HerobrineMod;
import com.herobrine.mod.config.Config;
import com.herobrine.mod.entities.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntitySpawnPlacementRegistry;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.init.Biomes;
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
        registerEntity("steve_survivor", SteveSurvivorEntity.class, 1671582, 0x00AFAF, 0xAA7D66);
        registerEntity("alex_survivor", AlexSurvivorEntity.class, 1975319, 0x8BBA88, 0xF2DABA);
        registerEntity("infected_bat", InfectedBatEntity.class, 6974294, 0x4C3E30, 0xFFFFFF);
        registerEntity("infected_donkey", InfectedDonkeyEntity.class, 1876519, 0x534539, 0xFFFFFF);
        registerEntity("infected_horse", InfectedHorseEntity.class, 1986325, 0xC09E7D, 0xFFFFFF);
        registerEntity("infected_llama", InfectedLlamaEntity.class, 1486328, 0xC09E7D, 0xFFFFFF);
        registerEntity("infected_rabbit", InfectedRabbitEntity.class, 3219875, 0x995F40, 0xFFFFFF);
        registerEntity("infected_wolf", InfectedWolfEntity.class, 1258745, 0xD7D3D3, 0xFFFFFF);
        registerEntityWithoutEgg("fake_herobrine_mage", FakeHerobrineMageEntity.class,2486184);
        registerEntityWithoutEgg("holy_water", HolyWaterEntity.class, 8929428);
        registerEntityWithoutEgg("unholy_water", UnholyWaterEntity.class, 1749582);
    }

    private static void registerEntity(String name, Class<? extends Entity> entity, int id, int color1, int color2) {
        net.minecraftforge.fml.common.registry.EntityRegistry.registerModEntity(new ResourceLocation(HerobrineMod.MODID + ":" + name), entity, HerobrineMod.MODID + "." + name, id, HerobrineMod.instance, 8192, 1, true, color1, color2);
    }

    private static void registerEntityWithoutEgg(String name, Class<? extends Entity> entity, int id) {
        net.minecraftforge.fml.common.registry.EntityRegistry.registerModEntity(new ResourceLocation(HerobrineMod.MODID + ":" + name), entity, HerobrineMod.MODID + "." + name, id, HerobrineMod.instance, 8192, 1, true);
    }

    public static void registerSpawnPlacement() {
        EntitySpawnPlacementRegistry.setPlacementType(HerobrineBuilderEntity.class, EntityLiving.SpawnPlacementType.ON_GROUND);
        EntitySpawnPlacementRegistry.setPlacementType(HerobrineMageEntity.class, EntityLiving.SpawnPlacementType.ON_GROUND);
        EntitySpawnPlacementRegistry.setPlacementType(HerobrineSpyEntity.class, EntityLiving.SpawnPlacementType.ON_GROUND);
        EntitySpawnPlacementRegistry.setPlacementType(HerobrineWarriorEntity.class, EntityLiving.SpawnPlacementType.ON_GROUND);
        EntitySpawnPlacementRegistry.setPlacementType(HerobrineBuilderEntity.class, EntityLiving.SpawnPlacementType.ON_GROUND);
        EntitySpawnPlacementRegistry.setPlacementType(InfectedPigEntity.class, EntityLiving.SpawnPlacementType.ON_GROUND);
        EntitySpawnPlacementRegistry.setPlacementType(InfectedChickenEntity.class, EntityLiving.SpawnPlacementType.ON_GROUND);
        EntitySpawnPlacementRegistry.setPlacementType(InfectedSheepEntity.class, EntityLiving.SpawnPlacementType.ON_GROUND);
        EntitySpawnPlacementRegistry.setPlacementType(InfectedCowEntity.class, EntityLiving.SpawnPlacementType.ON_GROUND);
        EntitySpawnPlacementRegistry.setPlacementType(InfectedVillagerEntity.class, EntityLiving.SpawnPlacementType.ON_GROUND);
        EntitySpawnPlacementRegistry.setPlacementType(InfectedMooshroomEntity.class, EntityLiving.SpawnPlacementType.ON_GROUND);
        EntitySpawnPlacementRegistry.setPlacementType(InfectedBatEntity.class, EntityLiving.SpawnPlacementType.ON_GROUND);
        EntitySpawnPlacementRegistry.setPlacementType(InfectedHorseEntity.class, EntityLiving.SpawnPlacementType.ON_GROUND);
        EntitySpawnPlacementRegistry.setPlacementType(InfectedDonkeyEntity.class, EntityLiving.SpawnPlacementType.ON_GROUND);
        EntitySpawnPlacementRegistry.setPlacementType(InfectedLlamaEntity.class, EntityLiving.SpawnPlacementType.ON_GROUND);
        EntitySpawnPlacementRegistry.setPlacementType(InfectedRabbitEntity.class, EntityLiving.SpawnPlacementType.ON_GROUND);
        EntitySpawnPlacementRegistry.setPlacementType(InfectedWolfEntity.class, EntityLiving.SpawnPlacementType.ON_GROUND);
    }

    public static void registerEntityWorldSpawns() {
        BiomeDictionary.Type[] InfectedVillagerTypes = {
                BiomeDictionary.Type.CONIFEROUS,
                BiomeDictionary.Type.PLAINS,
                BiomeDictionary.Type.SANDY,
                BiomeDictionary.Type.SAVANNA,
                BiomeDictionary.Type.SNOWY,
        };

        BiomeDictionary.Type[] InfectedRabbitTypes = {
                BiomeDictionary.Type.CONIFEROUS,
                BiomeDictionary.Type.SANDY,
                BiomeDictionary.Type.SNOWY,
                BiomeDictionary.Type.FOREST,

        };

        BiomeDictionary.Type[] InfectedAnimalTypes = {
                BiomeDictionary.Type.BEACH,
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

        BiomeDictionary.Type[] InfectedWolfTypes = {
                BiomeDictionary.Type.CONIFEROUS,
                BiomeDictionary.Type.DENSE,
                BiomeDictionary.Type.FOREST,
                BiomeDictionary.Type.LUSH
        };

        BiomeDictionary.Type[] InfectedHorseTypes = {
                BiomeDictionary.Type.SAVANNA,
                BiomeDictionary.Type.PLAINS
        };

        BiomeDictionary.Type[] SavannaTypes = {
                BiomeDictionary.Type.SAVANNA
        };

        BiomeDictionary.Type[] MountainTypes = {
                BiomeDictionary.Type.MOUNTAIN
        };

        BiomeDictionary.Type[] NetherTypes = {
                BiomeDictionary.Type.NETHER
        };

        BiomeDictionary.Type[] EndTypes = {
                BiomeDictionary.Type.END
        };

        BiomeDictionary.Type[] HerobrineTypes = {
                BiomeDictionary.Type.CONIFEROUS,
                BiomeDictionary.Type.DEAD,
                BiomeDictionary.Type.DENSE,
                BiomeDictionary.Type.FOREST,
                BiomeDictionary.Type.HILLS,
                BiomeDictionary.Type.JUNGLE,
                BiomeDictionary.Type.LUSH,
                BiomeDictionary.Type.MAGICAL,
                BiomeDictionary.Type.MESA,
                BiomeDictionary.Type.MOUNTAIN,
                BiomeDictionary.Type.PLAINS,
                BiomeDictionary.Type.SANDY,
                BiomeDictionary.Type.SAVANNA,
                BiomeDictionary.Type.SNOWY,
                BiomeDictionary.Type.SPARSE,
                BiomeDictionary.Type.SPOOKY,
                BiomeDictionary.Type.SWAMP,
                BiomeDictionary.Type.WASTELAND,
                BiomeDictionary.Type.WET,

                BiomeDictionary.Type.OCEAN
        };

        BiomeDictionary.Type[] MushroomBiomeTypes = {
                BiomeDictionary.Type.MUSHROOM
        };

        BiomeDictionary.Type[] PlainsTypes = {
                BiomeDictionary.Type.PLAINS
        };


        for (BiomeDictionary.Type t : InfectedVillagerTypes) {
            Set<Biome> biomes = BiomeDictionary.getBiomes(t);
            for (Biome currentBiome : biomes) {
                currentBiome.getSpawnableList(EnumCreatureType.MONSTER).add(new Biome.SpawnListEntry(InfectedVillagerEntity.class, Config.InfectedMobWeight, 1, 4));
            }
        }

        for (BiomeDictionary.Type t : InfectedRabbitTypes) {
            Set<Biome> biomes = BiomeDictionary.getBiomes(t);
            for (Biome currentBiome : biomes) {
                currentBiome.getSpawnableList(EnumCreatureType.MONSTER).add(new Biome.SpawnListEntry(InfectedRabbitEntity.class, Config.InfectedMobWeight, 2, 3));
            }
        }

        for (BiomeDictionary.Type t : InfectedHorseTypes) {
            Set<Biome> biomes = BiomeDictionary.getBiomes(t);
            for (Biome currentBiome : biomes) {
                currentBiome.getSpawnableList(EnumCreatureType.MONSTER).add(new Biome.SpawnListEntry(InfectedHorseEntity.class, Config.InfectedMobWeight, 2, 6));
            }
        }

        for (BiomeDictionary.Type t : PlainsTypes) {
            Set<Biome> biomes = BiomeDictionary.getBiomes(t);
            for (Biome currentBiome : biomes) {
                currentBiome.getSpawnableList(EnumCreatureType.MONSTER).add(new Biome.SpawnListEntry(InfectedDonkeyEntity.class, Config.InfectedMobWeight, 1, 3));
            }
        }

        for (BiomeDictionary.Type t : SavannaTypes) {
            Set<Biome> biomes = BiomeDictionary.getBiomes(t);
            for (Biome currentBiome : biomes) {
                currentBiome.getSpawnableList(EnumCreatureType.MONSTER).add(new Biome.SpawnListEntry(InfectedLlamaEntity.class, Config.InfectedMobWeight, 4, 4));
               currentBiome.getSpawnableList(EnumCreatureType.MONSTER).add(new Biome.SpawnListEntry(InfectedDonkeyEntity.class, Config.InfectedMobWeight, 1, 1));
            }
        }

        for (BiomeDictionary.Type t : MountainTypes) {
            Set<Biome> biomes = BiomeDictionary.getBiomes(t);
            for (Biome currentBiome : biomes) {
                currentBiome.getSpawnableList(EnumCreatureType.MONSTER).add(new Biome.SpawnListEntry(InfectedLlamaEntity.class, Config.InfectedMobWeight, 4, 6));
            }
        }

        for (BiomeDictionary.Type t : InfectedWolfTypes) {
            Set<Biome> biomes = BiomeDictionary.getBiomes(t);
            for (Biome currentBiome : biomes) {
                currentBiome.getSpawnableList(EnumCreatureType.MONSTER).add(new Biome.SpawnListEntry(InfectedWolfEntity.class, Config.InfectedMobWeight, 4, 4));
            }
        }

        for (BiomeDictionary.Type t : InfectedAnimalTypes) {
            Set<Biome> biomes = BiomeDictionary.getBiomes(t);
            for (Biome currentBiome : biomes) {
                currentBiome.getSpawnableList(EnumCreatureType.MONSTER).add(new Biome.SpawnListEntry(InfectedPigEntity.class, Config.InfectedMobWeight, 3, 6));
                currentBiome.getSpawnableList(EnumCreatureType.MONSTER).add(new Biome.SpawnListEntry(InfectedCowEntity.class, Config.InfectedMobWeight, 2, 4));
                currentBiome.getSpawnableList(EnumCreatureType.MONSTER).add(new Biome.SpawnListEntry(InfectedChickenEntity.class, Config.InfectedMobWeight, 4, 8));
                currentBiome.getSpawnableList(EnumCreatureType.MONSTER).add(new Biome.SpawnListEntry(InfectedSheepEntity.class, Config.InfectedMobWeight, 3, 6));
            }
        }

        for (BiomeDictionary.Type t : MushroomBiomeTypes) {
            Set<Biome> biomes = BiomeDictionary.getBiomes(t);
            for (Biome currentBiome : biomes) {
                currentBiome.getSpawnableList(EnumCreatureType.MONSTER).add(new Biome.SpawnListEntry(InfectedMooshroomEntity.class, 1, 1, 1));
            }
        }

        for (BiomeDictionary.Type t : HerobrineTypes) {
            Set<Biome> biomes = BiomeDictionary.getBiomes(t);
            for (Biome currentBiome : biomes) {
                currentBiome.getSpawnableList(EnumCreatureType.MONSTER).add(new Biome.SpawnListEntry(HerobrineBuilderEntity.class, Config.HerobrineBuilderWeight, 1, 1));
                currentBiome.getSpawnableList(EnumCreatureType.MONSTER).add(new Biome.SpawnListEntry(HerobrineMageEntity.class, Config.HerobrineMageWeight, 1, 1));
                currentBiome.getSpawnableList(EnumCreatureType.MONSTER).add(new Biome.SpawnListEntry(HerobrineSpyEntity.class, Config.HerobrineSpyWeight, 1, 1));
                currentBiome.getSpawnableList(EnumCreatureType.MONSTER).add(new Biome.SpawnListEntry(HerobrineWarriorEntity.class, Config.HerobrineWarriorWeight, 1, 1));
                currentBiome.getSpawnableList(EnumCreatureType.MONSTER).add(new Biome.SpawnListEntry(InfectedBatEntity.class, (int) (Config.InfectedMobWeight * 2.5), 1, 1));
            }
        }

        //In Minecraft 1.12 The End is all considered to the one biome Herobrine can't spawn in the vanilla end dimension. This is here so Herobrine can spawn in modded end biomes, but in most cases not on the main island where the Ender Dragon is fought.
        for (BiomeDictionary.Type t : EndTypes) {
            Set<Biome> biomes = BiomeDictionary.getBiomes(t);
            for (Biome currentBiome : biomes) {
                if(currentBiome != Biomes.SKY) {
                    currentBiome.getSpawnableList(EnumCreatureType.MONSTER).add(new Biome.SpawnListEntry(HerobrineBuilderEntity.class, 1, 1, 1));
                    currentBiome.getSpawnableList(EnumCreatureType.MONSTER).add(new Biome.SpawnListEntry(HerobrineMageEntity.class, 1, 1, 1));
                    currentBiome.getSpawnableList(EnumCreatureType.MONSTER).add(new Biome.SpawnListEntry(HerobrineSpyEntity.class, 1, 1, 1));
                    currentBiome.getSpawnableList(EnumCreatureType.MONSTER).add(new Biome.SpawnListEntry(HerobrineWarriorEntity.class, 1, 1, 1));
                }
            }
        }

        for (BiomeDictionary.Type t : NetherTypes) {
            Set<Biome> biomes = BiomeDictionary.getBiomes(t);
            for (Biome currentBiome : biomes) {
                currentBiome.getSpawnableList(EnumCreatureType.MONSTER).add(new Biome.SpawnListEntry(HerobrineBuilderEntity.class, Config.HerobrineBuilderWeight / 3, 1, 1));
                currentBiome.getSpawnableList(EnumCreatureType.MONSTER).add(new Biome.SpawnListEntry(HerobrineMageEntity.class, Config.HerobrineMageWeight / 5, 1, 1));
                currentBiome.getSpawnableList(EnumCreatureType.MONSTER).add(new Biome.SpawnListEntry(HerobrineSpyEntity.class, Config.HerobrineSpyWeight / 3, 1, 1));
                currentBiome.getSpawnableList(EnumCreatureType.MONSTER).add(new Biome.SpawnListEntry(HerobrineWarriorEntity.class, Config.HerobrineWarriorWeight / 4, 1, 1));
            }
        }
    }
}