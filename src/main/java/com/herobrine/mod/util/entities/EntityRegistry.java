package com.herobrine.mod.util.entities;

import com.herobrine.mod.HerobrineMod;
import com.herobrine.mod.entities.*;
import com.herobrine.mod.util.items.ItemList;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntitySpawnPlacementRegistry;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biomes;
import net.minecraft.world.gen.Heightmap;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.event.RegistryEvent;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

@SuppressWarnings("unchecked")
public class EntityRegistry {
    public static EntityType<HerobrineWarriorEntity> HEROBRINE_WARRIOR_ENTITY = (EntityType<HerobrineWarriorEntity>) EntityType.Builder.create((EntityType<HerobrineWarriorEntity> type, World worldIn) -> new HerobrineWarriorEntity(worldIn), EntityClassification.MONSTER).size(0.6F, 1.95F).build("herobrine_warrior").setRegistryName("herobrine_warrior");
    public static EntityType<?> HOLY_WATER_ENTITY = EntityType.Builder.create((EntityType<HolyWaterEntity> type, World worldIn) -> new HolyWaterEntity(worldIn), EntityClassification.MISC).build("holy_water").setRegistryName("holy_water");
    public static EntityType<?> UNHOLY_WATER_ENTITY = EntityType.Builder.create((EntityType<UnholyWaterEntity> type, World worldIn) -> new UnholyWaterEntity(worldIn), EntityClassification.MISC).build("unholy_water").setRegistryName("unholy_water");
    public static EntityType<InfectedPigEntity> INFECTED_PIG_ENTITY = (EntityType<InfectedPigEntity>) EntityType.Builder.create((EntityType<InfectedPigEntity> type, World worldIn) -> new InfectedPigEntity(worldIn), EntityClassification.MONSTER).size(0.9F, 0.9F).build("infected_pig").setRegistryName("infected_pig");
    public static EntityType<InfectedChickenEntity> INFECTED_CHICKEN_ENTITY = (EntityType<InfectedChickenEntity>) EntityType.Builder.create((EntityType<InfectedChickenEntity> type, World worldIn) -> new InfectedChickenEntity(worldIn), EntityClassification.MONSTER).size(0.4F, 0.7F).build("infected_chicken").setRegistryName("infected_chicken");
    public static EntityType<InfectedSheepEntity> INFECTED_SHEEP_ENTITY = (EntityType<InfectedSheepEntity>) EntityType.Builder.create((EntityType<InfectedSheepEntity> type, World worldIn) -> new InfectedSheepEntity(worldIn), EntityClassification.MONSTER).size(0.9F, 1.3F).build("infected_sheep").setRegistryName("infected_sheep");
    public static EntityType<InfectedCowEntity> INFECTED_COW_ENTITY = (EntityType<InfectedCowEntity>) EntityType.Builder.create((EntityType<InfectedCowEntity> type, World worldIn) -> new InfectedCowEntity(worldIn), EntityClassification.MONSTER).size(0.9F, 1.4F).build("infected_cow").setRegistryName("infected_cow");
    public static EntityType<InfectedMooshroomEntity> INFECTED_MOOSHROOM_ENTITY = (EntityType<InfectedMooshroomEntity>) EntityType.Builder.create((EntityType<InfectedMooshroomEntity> type, World worldIn) -> new InfectedMooshroomEntity(worldIn), EntityClassification.MONSTER).size(0.9F, 1.4F).build("infected_mooshroom").setRegistryName("infected_mooshroom");
    public static EntityType<InfectedVillagerEntity> INFECTED_VILLAGER_ENTITY = (EntityType<InfectedVillagerEntity>) EntityType.Builder.create((EntityType<InfectedVillagerEntity> type, World worldIn) -> new InfectedVillagerEntity(worldIn), EntityClassification.MONSTER).size(0.6F, 1.95F).build("infected_villager").setRegistryName("infected_villager");
    public static EntityType<HerobrineSpyEntity> HEROBRINE_SPY_ENTITY = (EntityType<HerobrineSpyEntity>) EntityType.Builder.create((EntityType<HerobrineSpyEntity> type, World worldIn) -> new HerobrineSpyEntity(worldIn), EntityClassification.MONSTER).size(0.6F, 1.95F).build("herobrine_spy").setRegistryName("herobrine_spy");
    public static EntityType<HerobrineBuilderEntity> HEROBRINE_BUILDER_ENTITY = (EntityType<HerobrineBuilderEntity>) EntityType.Builder.create((EntityType<HerobrineBuilderEntity> type, World worldIn) -> new HerobrineBuilderEntity(worldIn), EntityClassification.MONSTER).size(0.6F, 1.95F).build("herobrine_builder").setRegistryName("herobrine_builder");
    public static EntityType<HerobrineMageEntity> HEROBRINE_MAGE_ENTITY = (EntityType<HerobrineMageEntity>) EntityType.Builder.create((EntityType<HerobrineMageEntity> type, World worldIn) -> new HerobrineMageEntity(worldIn), EntityClassification.MONSTER).size(0.6F, 1.95F).build("herobrine_mage").setRegistryName("herobrine_mage");
    public static EntityType<FakeHerobrineMageEntity> FAKE_HEROBRINE_MAGE_ENTITY = (EntityType<FakeHerobrineMageEntity>) EntityType.Builder.create((EntityType<FakeHerobrineMageEntity> type, World worldIn) -> new FakeHerobrineMageEntity(worldIn), EntityClassification.MONSTER).size(0.6F, 1.95F).build("fake_herobrine_mage").setRegistryName("fake_herobrine_mage");
    public static EntityType<SteveSurvivorEntity> STEVE_SURVIVOR_ENTITY = (EntityType<SteveSurvivorEntity>) EntityType.Builder.create((EntityType<SteveSurvivorEntity> type, World worldIn) -> new SteveSurvivorEntity(worldIn), EntityClassification.CREATURE).size(0.6F, 1.8F).build("steve_survivor").setRegistryName("steve_survivor");
    public static EntityType<AlexSurvivorEntity> ALEX_SURVIVOR_ENTITY = (EntityType<AlexSurvivorEntity>) EntityType.Builder.create((EntityType<AlexSurvivorEntity> type, World worldIn) -> new AlexSurvivorEntity(worldIn), EntityClassification.CREATURE).size(0.6F, 1.8F).build("alex_survivor").setRegistryName("alex_survivor");
    public static EntityType<InfectedWolfEntity> INFECTED_WOLF_ENTITY = (EntityType<InfectedWolfEntity>) EntityType.Builder.create((EntityType<InfectedWolfEntity> type, World worldIn) -> new InfectedWolfEntity(worldIn), EntityClassification.MONSTER).size(0.6F, 0.85F).build("infected_wolf").setRegistryName("infected_wolf");
    public static EntityType<InfectedLlamaEntity> INFECTED_LLAMA_ENTITY = (EntityType<InfectedLlamaEntity>) EntityType.Builder.create((EntityType<InfectedLlamaEntity> type, World worldIn) -> new InfectedLlamaEntity(worldIn), EntityClassification.MONSTER).size(0.9F, 1.87F).build("infected_llama").setRegistryName("infected_llama");
    public static EntityType<InfectedHorseEntity> INFECTED_HORSE_ENTITY = (EntityType<InfectedHorseEntity>) EntityType.Builder.create((EntityType<InfectedHorseEntity> type, World worldIn) -> new InfectedHorseEntity(worldIn), EntityClassification.MONSTER).size(1.3964844F, 1.6F).build("infected_horse").setRegistryName("infected_horse");
    public static EntityType<InfectedDonkeyEntity> INFECTED_DONKEY_ENTITY = (EntityType<InfectedDonkeyEntity>) EntityType.Builder.create((EntityType<InfectedDonkeyEntity> type, World worldIn) -> new InfectedDonkeyEntity(worldIn), EntityClassification.MONSTER).size(1.3964844F, 1.5F).build("infected_donkey").setRegistryName("infected_donkey");
    public static EntityType<InfectedRabbitEntity> INFECTED_RABBIT_ENTITY = (EntityType<InfectedRabbitEntity>) EntityType.Builder.create((EntityType<InfectedRabbitEntity> type, World worldIn) -> new InfectedRabbitEntity(worldIn), EntityClassification.MONSTER).size(0.4F, 0.5F).build("infected_rabbit").setRegistryName("infected_rabbit");
    public static EntityType<InfectedBatEntity> INFECTED_BAT_ENTITY = (EntityType<InfectedBatEntity>) EntityType.Builder.create((EntityType<InfectedBatEntity> type, World worldIn) -> new InfectedBatEntity(worldIn), EntityClassification.MONSTER).size(0.5F, 0.9F).build("infected_bat").setRegistryName("infected_bat");

    public static void registerEntitySpawnEggs(@NotNull final RegistryEvent.Register<Item> event) {
        event.getRegistry().registerAll(
                ItemList.herobrine_warrior_spawn_egg = registerEntitySpawnEgg(HEROBRINE_WARRIOR_ENTITY, 0x000000, 0xFF0000, "herobrine_warrior_spawn_egg"),
                ItemList.infected_pig_spawn_egg = registerEntitySpawnEgg(INFECTED_PIG_ENTITY, 0xF0A5A2, 0xFFFFFF, "infected_pig_spawn_egg"),
                ItemList.infected_chicken_spawn_egg = registerEntitySpawnEgg(INFECTED_CHICKEN_ENTITY, 0xA1A1A1, 0xFFFFFF, "infected_chicken_spawn_egg"),
                ItemList.infected_sheep_spawn_egg = registerEntitySpawnEgg(INFECTED_SHEEP_ENTITY, 0xE7E7E7, 0xFFFFFF, "infected_sheep_spawn_egg"),
                ItemList.infected_cow_spawn_egg = registerEntitySpawnEgg(INFECTED_COW_ENTITY, 0x443626, 0xFFFFFF, "infected_cow_spawn_egg"),
                ItemList.infected_mooshroom_spawn_egg = registerEntitySpawnEgg(INFECTED_MOOSHROOM_ENTITY, 0xA00F10, 0xFFFFFF, "infected_mooshroom_spawn_egg"),
                ItemList.infected_villager_spawn_egg = registerEntitySpawnEgg(INFECTED_VILLAGER_ENTITY, 0x563C33, 0xFFFFFF, "infected_villager_spawn_egg"),
                ItemList.herobrine_spy_spawn_egg = registerEntitySpawnEgg(HEROBRINE_SPY_ENTITY, 0x000000, 0x00FF00, "herobrine_spy_spawn_egg"),
                ItemList.herobrine_builder_spawn_egg = registerEntitySpawnEgg(HEROBRINE_BUILDER_ENTITY, 0x000000, 0xFFFF00, "herobrine_builder_spawn_egg"),
                ItemList.herobrine_mage_spawn_egg = registerEntitySpawnEgg(HEROBRINE_MAGE_ENTITY, 0x000000, 0x0000FF, "herobrine_mage_spawn_egg"),
                ItemList.steve_survivor_spawn_egg = registerEntitySpawnEgg(STEVE_SURVIVOR_ENTITY, 0x00AFAF, 0xAA7D66, "steve_survivor_spawn_egg"),
                ItemList.alex_survivor_spawn_egg = registerEntitySpawnEgg(ALEX_SURVIVOR_ENTITY, 0x8BBA88, 0xF2DABA, "alex_survivor_spawn_egg"),
                ItemList.infected_wolf_spawn_egg = registerEntitySpawnEgg(INFECTED_WOLF_ENTITY, 0xD7D3D3, 0xFFFFFF, "infected_wolf_spawn_egg"),
                ItemList.infected_llama_spawn_egg = registerEntitySpawnEgg(INFECTED_LLAMA_ENTITY, 0xC09E7D, 0xFFFFFF, "infected_llama_spawn_egg"),
                ItemList.infected_horse_spawn_egg = registerEntitySpawnEgg(INFECTED_HORSE_ENTITY, 0xC09E7D, 0xFFFFFF, "infected_horse_spawn_egg"),
                ItemList.infected_donkey_spawn_egg = registerEntitySpawnEgg(INFECTED_DONKEY_ENTITY, 0x534539, 0xFFFFFF, "infected_donkey_spawn_egg"),
                ItemList.infected_rabbit_spawn_egg = registerEntitySpawnEgg(INFECTED_RABBIT_ENTITY, 0x995F40, 0xFFFFFF, "infected_rabbit_spawn_egg"),
                ItemList.infected_bat_spawn_egg = registerEntitySpawnEgg(INFECTED_BAT_ENTITY, 0x4C3E30, 0xFFFFFF, "infected_bat_spawn_egg")
        );
    }

    public static void registerSpawnPlacement() {
        EntitySpawnPlacementRegistry.register(EntityRegistry.INFECTED_BAT_ENTITY, EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, InfectedBatEntity::canSpawn);
        EntitySpawnPlacementRegistry.register(EntityRegistry.HEROBRINE_SPY_ENTITY, EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, MonsterEntity::canMonsterSpawnInLight);
        EntitySpawnPlacementRegistry.register(EntityRegistry.HEROBRINE_WARRIOR_ENTITY, EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, MonsterEntity::canMonsterSpawnInLight);
        EntitySpawnPlacementRegistry.register(EntityRegistry.HEROBRINE_BUILDER_ENTITY, EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, MonsterEntity::canMonsterSpawnInLight);
        EntitySpawnPlacementRegistry.register(EntityRegistry.HEROBRINE_MAGE_ENTITY, EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, MonsterEntity::canMonsterSpawnInLight);
        EntitySpawnPlacementRegistry.register(EntityRegistry.INFECTED_RABBIT_ENTITY, EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, AbstractInfectedEntity::canSpawn);
        EntitySpawnPlacementRegistry.register(EntityRegistry.INFECTED_DONKEY_ENTITY, EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, AbstractInfectedEntity::canSpawn);
        EntitySpawnPlacementRegistry.register(EntityRegistry.INFECTED_HORSE_ENTITY, EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, AbstractInfectedEntity::canSpawn);
        EntitySpawnPlacementRegistry.register(EntityRegistry.INFECTED_LLAMA_ENTITY, EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, InfectedLlamaEntity::canSpawn);
        EntitySpawnPlacementRegistry.register(EntityRegistry.INFECTED_WOLF_ENTITY, EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, AbstractInfectedEntity::canSpawn);
        EntitySpawnPlacementRegistry.register(EntityRegistry.INFECTED_MOOSHROOM_ENTITY, EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, InfectedMooshroomEntity::canSpawn);
        EntitySpawnPlacementRegistry.register(EntityRegistry.INFECTED_SHEEP_ENTITY, EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, AbstractInfectedEntity::canSpawn);
        EntitySpawnPlacementRegistry.register(EntityRegistry.INFECTED_CHICKEN_ENTITY, EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, AbstractInfectedEntity::canSpawn);
        EntitySpawnPlacementRegistry.register(EntityRegistry.INFECTED_COW_ENTITY, EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, AbstractInfectedEntity::canSpawn);
        EntitySpawnPlacementRegistry.register(EntityRegistry.INFECTED_PIG_ENTITY, EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, AbstractInfectedEntity::canSpawn);
        EntitySpawnPlacementRegistry.register(EntityRegistry.INFECTED_VILLAGER_ENTITY, EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, InfectedVillagerEntity::canSpawn);
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
                BiomeDictionary.Type.MODIFIED,
                BiomeDictionary.Type.OCEAN,
                BiomeDictionary.Type.PLATEAU
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
                currentBiome.getSpawns(EntityClassification.MONSTER).add(new Biome.SpawnListEntry(INFECTED_VILLAGER_ENTITY, 60, 1, 4));
            }
        }

        for (BiomeDictionary.Type t : InfectedRabbitTypes) {
            Set<Biome> biomes = BiomeDictionary.getBiomes(t);
            for (Biome currentBiome : biomes) {
                currentBiome.getSpawns(EntityClassification.MONSTER).add(new Biome.SpawnListEntry(INFECTED_RABBIT_ENTITY, 60, 2, 3));
            }
        }

        for (BiomeDictionary.Type t : InfectedHorseTypes) {
            Set<Biome> biomes = BiomeDictionary.getBiomes(t);
            for (Biome currentBiome : biomes) {
                currentBiome.getSpawns(EntityClassification.MONSTER).add(new Biome.SpawnListEntry(INFECTED_HORSE_ENTITY, 60, 2, 6));
            }
        }

        for (BiomeDictionary.Type t : PlainsTypes) {
            Set<Biome> biomes = BiomeDictionary.getBiomes(t);
            for (Biome currentBiome : biomes) {
                currentBiome.getSpawns(EntityClassification.MONSTER).add(new Biome.SpawnListEntry(INFECTED_DONKEY_ENTITY, 60, 1, 3));
            }
        }

        for (BiomeDictionary.Type t : SavannaTypes) {
            Set<Biome> biomes = BiomeDictionary.getBiomes(t);
            for (Biome currentBiome : biomes) {
                currentBiome.getSpawns(EntityClassification.MONSTER).add(new Biome.SpawnListEntry(INFECTED_LLAMA_ENTITY, 60, 4, 4));
                currentBiome.getSpawns(EntityClassification.MONSTER).add(new Biome.SpawnListEntry(INFECTED_DONKEY_ENTITY, 60, 1, 1));
            }
        }

        for (BiomeDictionary.Type t : MountainTypes) {
            Set<Biome> biomes = BiomeDictionary.getBiomes(t);
            for (Biome currentBiome : biomes) {
                currentBiome.getSpawns(EntityClassification.MONSTER).add(new Biome.SpawnListEntry(INFECTED_LLAMA_ENTITY, 60, 4, 6));
            }
        }

        for (BiomeDictionary.Type t : InfectedWolfTypes) {
            Set<Biome> biomes = BiomeDictionary.getBiomes(t);
            for (Biome currentBiome : biomes) {
                currentBiome.getSpawns(EntityClassification.MONSTER).add(new Biome.SpawnListEntry(INFECTED_WOLF_ENTITY, 60, 4, 4));
            }
        }

        for (BiomeDictionary.Type t : InfectedAnimalTypes) {
            Set<Biome> biomes = BiomeDictionary.getBiomes(t);
            for (Biome currentBiome : biomes) {
                currentBiome.getSpawns(EntityClassification.MONSTER).add(new Biome.SpawnListEntry(INFECTED_PIG_ENTITY, 60, 3, 6));
                currentBiome.getSpawns(EntityClassification.MONSTER).add(new Biome.SpawnListEntry(INFECTED_COW_ENTITY, 60, 2, 4));
                currentBiome.getSpawns(EntityClassification.MONSTER).add(new Biome.SpawnListEntry(INFECTED_CHICKEN_ENTITY, 60, 4, 8));
                currentBiome.getSpawns(EntityClassification.MONSTER).add(new Biome.SpawnListEntry(INFECTED_SHEEP_ENTITY, 60, 3, 6));
            }
        }

        for (BiomeDictionary.Type t : MushroomBiomeTypes) {
            Set<Biome> biomes = BiomeDictionary.getBiomes(t);
            for (Biome currentBiome : biomes) {
                currentBiome.getSpawns(EntityClassification.MONSTER).add(new Biome.SpawnListEntry(INFECTED_MOOSHROOM_ENTITY, 1, 1, 1));
            }
        }

        for (BiomeDictionary.Type t : HerobrineTypes) {
            Set<Biome> biomes = BiomeDictionary.getBiomes(t);
            for (Biome currentBiome : biomes) {
                currentBiome.getSpawns(EntityClassification.MONSTER).add(new Biome.SpawnListEntry(HEROBRINE_BUILDER_ENTITY, 30, 1, 1));
                currentBiome.getSpawns(EntityClassification.MONSTER).add(new Biome.SpawnListEntry(HEROBRINE_MAGE_ENTITY, 30, 1, 1));
                currentBiome.getSpawns(EntityClassification.MONSTER).add(new Biome.SpawnListEntry(HEROBRINE_SPY_ENTITY, 50, 1, 1));
                currentBiome.getSpawns(EntityClassification.MONSTER).add(new Biome.SpawnListEntry(HEROBRINE_WARRIOR_ENTITY, 15, 1, 1));
            }
        }

        for (BiomeDictionary.Type t : EndTypes) {
            Set<Biome> biomes = BiomeDictionary.getBiomes(t);
            for (Biome currentBiome : biomes) {
                if(currentBiome != Biomes.THE_END) {
                    currentBiome.getSpawns(EntityClassification.MONSTER).add(new Biome.SpawnListEntry(HEROBRINE_BUILDER_ENTITY, 1, 1, 1));
                    currentBiome.getSpawns(EntityClassification.MONSTER).add(new Biome.SpawnListEntry(HEROBRINE_MAGE_ENTITY, 1, 1, 1));
                    currentBiome.getSpawns(EntityClassification.MONSTER).add(new Biome.SpawnListEntry(HEROBRINE_SPY_ENTITY, 1, 1, 1));
                    currentBiome.getSpawns(EntityClassification.MONSTER).add(new Biome.SpawnListEntry(HEROBRINE_WARRIOR_ENTITY, 1, 1, 1));
                }
            }
        }

        for (BiomeDictionary.Type t : NetherTypes) {
            Set<Biome> biomes = BiomeDictionary.getBiomes(t);
            for (Biome currentBiome : biomes) {
                currentBiome.getSpawns(EntityClassification.MONSTER).add(new Biome.SpawnListEntry(HEROBRINE_BUILDER_ENTITY, 8, 1, 1));
                currentBiome.getSpawns(EntityClassification.MONSTER).add(new Biome.SpawnListEntry(HEROBRINE_MAGE_ENTITY, 6, 1, 1));
                currentBiome.getSpawns(EntityClassification.MONSTER).add(new Biome.SpawnListEntry(HEROBRINE_SPY_ENTITY, 10, 1, 1));
                currentBiome.getSpawns(EntityClassification.MONSTER).add(new Biome.SpawnListEntry(HEROBRINE_WARRIOR_ENTITY, 5, 1, 1));
            }
        }
    }

    @NotNull
    private static Item registerEntitySpawnEgg(EntityType<?> type, int primaryColor, int secondaryColor, String name) {
        SpawnEggItem item = new SpawnEggItem(type, primaryColor, secondaryColor, new Item.Properties().group(ItemGroup.MISC));
        item.setRegistryName(HerobrineMod.location(name));
        return item;
    }
}