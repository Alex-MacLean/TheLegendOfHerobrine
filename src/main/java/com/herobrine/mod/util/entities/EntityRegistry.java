package com.herobrine.mod.util.entities;

import com.herobrine.mod.HerobrineMod;
import com.herobrine.mod.entities.*;
import com.herobrine.mod.util.items.ItemList;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.event.RegistryEvent;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

public class EntityRegistry {
    public static EntityType<?> HEROBRINE_WARRIOR_ENTITY = EntityType.Builder.create((EntityType<HerobrineWarriorEntity> type, World worldIn) -> new HerobrineWarriorEntity(worldIn), EntityClassification.MONSTER).size(0.6F, 1.95F).build("herobrine_warrior").setRegistryName("herobrine_warrior");
    public static EntityType<?> HOLY_WATER_ENTITY = EntityType.Builder.create((EntityType<HolyWaterEntity> type, World worldIn) -> new HolyWaterEntity(worldIn), EntityClassification.MISC).build("holy_water").setRegistryName("holy_water");
    public static EntityType<?> UNHOLY_WATER_ENTITY = EntityType.Builder.create((EntityType<UnholyWaterEntity> type, World worldIn) -> new UnholyWaterEntity(worldIn), EntityClassification.MISC).build("unholy_water").setRegistryName("unholy_water");
    public static EntityType<?> INFECTED_PIG_ENTITY = EntityType.Builder.create((EntityType<InfectedPigEntity> type, World worldIn) -> new InfectedPigEntity(worldIn), EntityClassification.MONSTER).size(0.9F, 0.9F).build("infected_pig").setRegistryName("infected_pig");
    public static EntityType<?> INFECTED_CHICKEN_ENTITY = EntityType.Builder.create((EntityType<InfectedChickenEntity> type, World worldIn) -> new InfectedChickenEntity(worldIn), EntityClassification.MONSTER).size(0.4F, 0.7F).build("infected_chicken").setRegistryName("infected_chicken");
    public static EntityType<?> INFECTED_SHEEP_ENTITY = EntityType.Builder.create((EntityType<InfectedSheepEntity> type, World worldIn) -> new InfectedSheepEntity(worldIn), EntityClassification.MONSTER).size(0.9F, 1.3F).build("infected_sheep").setRegistryName("infected_sheep");
    public static EntityType<?> INFECTED_COW_ENTITY = EntityType.Builder.create((EntityType<InfectedCowEntity> type, World worldIn) -> new InfectedCowEntity(worldIn), EntityClassification.MONSTER).size(0.9F, 1.4F).build("infected_cow").setRegistryName("infected_cow");
    public static EntityType<?> INFECTED_MOOSHROOM_ENTITY = EntityType.Builder.create((EntityType<InfectedMooshroomEntity> type, World worldIn) -> new InfectedMooshroomEntity(worldIn), EntityClassification.MONSTER).size(0.9F, 1.4F).build("infected_mooshroom").setRegistryName("infected_mooshroom");
    public static EntityType<?> INFECTED_VILLAGER_ENTITY = EntityType.Builder.create((EntityType<InfectedVillagerEntity> type, World worldIn) -> new InfectedVillagerEntity(worldIn), EntityClassification.MONSTER).size(0.6F, 1.95F).build("infected_villager").setRegistryName("infected_villager");
    public static EntityType<?> HEROBRINE_SPY_ENTITY = EntityType.Builder.create((EntityType<HerobrineSpyEntity> type, World worldIn) -> new HerobrineSpyEntity(worldIn), EntityClassification.MONSTER).size(0.6F, 1.95F).build("herobrine_spy").setRegistryName("herobrine_spy");
    public static EntityType<?> HEROBRINE_BUILDER_ENTITY = EntityType.Builder.create((EntityType<HerobrineBuilderEntity> type, World worldIn) -> new HerobrineBuilderEntity(worldIn), EntityClassification.MONSTER).size(0.6F, 1.95F).build("herobrine_builder").setRegistryName("herobrine_builder");
    public static EntityType<?> HEROBRINE_MAGE_ENTITY = EntityType.Builder.create((EntityType<HerobrineMageEntity> type, World worldIn) -> new HerobrineMageEntity(worldIn), EntityClassification.MONSTER).size(0.6F, 1.95F).build("herobrine_mage").setRegistryName("herobrine_mage");
    public static EntityType<?> FAKE_HEROBRINE_MAGE_ENTITY = EntityType.Builder.create((EntityType<FakeHerobrineMageEntity> type, World worldIn) -> new FakeHerobrineMageEntity(worldIn), EntityClassification.MONSTER).size(0.6F, 1.95F).build("fake_herobrine_mage").setRegistryName("fake_herobrine_mage");
    public static EntityType<?> STEVE_SURVIVOR_ENTITY = EntityType.Builder.create((EntityType<SteveSurvivorEntity> type, World worldIn) -> new SteveSurvivorEntity(worldIn), EntityClassification.CREATURE).size(0.6F, 1.8F).build("steve_survivor").setRegistryName("steve_survivor");
    public static EntityType<?> ALEX_SURVIVOR_ENTITY = EntityType.Builder.create((EntityType<AlexSurvivorEntity> type, World worldIn) -> new AlexSurvivorEntity(worldIn), EntityClassification.CREATURE).size(0.6F, 1.8F).build("alex_survivor").setRegistryName("alex_survivor");
    public static EntityType<?> INFECTED_WOLF_ENTITY = EntityType.Builder.create((EntityType<InfectedWolfEntity> type, World worldIn) -> new InfectedWolfEntity(worldIn), EntityClassification.MONSTER).size(0.6F, 0.85F).build("infected_wolf").setRegistryName("infected_wolf");
    public static EntityType<?> INFECTED_LLAMA_ENTITY = EntityType.Builder.create((EntityType<InfectedLlamaEntity> type, World worldIn) -> new InfectedLlamaEntity(worldIn), EntityClassification.MONSTER).size(0.9F, 1.87F).build("infected_llama").setRegistryName("infected_llama");
    public static EntityType<?> INFECTED_HORSE_ENTITY = EntityType.Builder.create((EntityType<InfectedHorseEntity> type, World worldIn) -> new InfectedHorseEntity(worldIn), EntityClassification.MONSTER).size(1.3964844F, 1.6F).build("infected_horse").setRegistryName("infected_horse");
    public static EntityType<?> INFECTED_DONKEY_ENTITY = EntityType.Builder.create((EntityType<InfectedDonkeyEntity> type, World worldIn) -> new InfectedDonkeyEntity(worldIn), EntityClassification.MONSTER).size(1.3964844F, 1.5F).build("infected_donkey").setRegistryName("infected_donkey");
    public static EntityType<?> INFECTED_RABBIT_ENTITY = EntityType.Builder.create((EntityType<InfectedRabbitEntity> type, World worldIn) -> new InfectedRabbitEntity(worldIn), EntityClassification.MONSTER).size(0.4F, 0.5F).build("infected_rabbit").setRegistryName("infected_rabbit");
    public static EntityType<?> INFECTED_BAT_ENTITY = EntityType.Builder.create((EntityType<InfectedBatEntity> type, World worldIn) -> new InfectedBatEntity(worldIn), EntityClassification.MONSTER).size(0.5F, 0.9F).build("infected_bat").setRegistryName("infected_bat");

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

        BiomeDictionary.Type[] HerobrineTypes = {
                BiomeDictionary.Type.BEACH,
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
                BiomeDictionary.Type.RARE,
                BiomeDictionary.Type.SANDY,
                BiomeDictionary.Type.SAVANNA,
                BiomeDictionary.Type.SNOWY,
                BiomeDictionary.Type.SPARSE,
                BiomeDictionary.Type.SPOOKY,
                BiomeDictionary.Type.SWAMP,
                BiomeDictionary.Type.WASTELAND,
                BiomeDictionary.Type.WET,
                BiomeDictionary.Type.MODIFIED,
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
                currentBiome.getSpawns(EntityClassification.MONSTER).add(new Biome.SpawnListEntry(INFECTED_VILLAGER_ENTITY, 3, 1, 4));
            }
        }

        for (BiomeDictionary.Type t : InfectedRabbitTypes) {
            Set<Biome> biomes = BiomeDictionary.getBiomes(t);
            for (Biome currentBiome : biomes) {
                currentBiome.getSpawns(EntityClassification.MONSTER).add(new Biome.SpawnListEntry(INFECTED_RABBIT_ENTITY, 3, 2, 3));
            }
        }

        for (BiomeDictionary.Type t : InfectedHorseTypes) {
            Set<Biome> biomes = BiomeDictionary.getBiomes(t);
            for (Biome currentBiome : biomes) {
                currentBiome.getSpawns(EntityClassification.MONSTER).add(new Biome.SpawnListEntry(INFECTED_HORSE_ENTITY, 3, 2, 6));
            }
        }

        for (BiomeDictionary.Type t : PlainsTypes) {
            Set<Biome> biomes = BiomeDictionary.getBiomes(t);
            for (Biome currentBiome : biomes) {
                currentBiome.getSpawns(EntityClassification.MONSTER).add(new Biome.SpawnListEntry(INFECTED_DONKEY_ENTITY, 3, 1, 3));
            }
        }

        for (BiomeDictionary.Type t : SavannaTypes) {
            Set<Biome> biomes = BiomeDictionary.getBiomes(t);
            for (Biome currentBiome : biomes) {
                currentBiome.getSpawns(EntityClassification.MONSTER).add(new Biome.SpawnListEntry(INFECTED_LLAMA_ENTITY, 3, 4, 4));
                currentBiome.getSpawns(EntityClassification.MONSTER).add(new Biome.SpawnListEntry(INFECTED_DONKEY_ENTITY, 3, 1, 1));
            }
        }

        for (BiomeDictionary.Type t : MountainTypes) {
            Set<Biome> biomes = BiomeDictionary.getBiomes(t);
            for (Biome currentBiome : biomes) {
                currentBiome.getSpawns(EntityClassification.MONSTER).add(new Biome.SpawnListEntry(INFECTED_LLAMA_ENTITY, 3, 4, 6));
            }
        }

        for (BiomeDictionary.Type t : InfectedWolfTypes) {
            Set<Biome> biomes = BiomeDictionary.getBiomes(t);
            for (Biome currentBiome : biomes) {
                currentBiome.getSpawns(EntityClassification.MONSTER).add(new Biome.SpawnListEntry(INFECTED_WOLF_ENTITY, 3, 4, 4));
            }
        }

        for (BiomeDictionary.Type t : InfectedAnimalTypes) {
            Set<Biome> biomes = BiomeDictionary.getBiomes(t);
            for (Biome currentBiome : biomes) {
                currentBiome.getSpawns(EntityClassification.MONSTER).add(new Biome.SpawnListEntry(INFECTED_PIG_ENTITY, 3, 3, 6));
                currentBiome.getSpawns(EntityClassification.MONSTER).add(new Biome.SpawnListEntry(INFECTED_COW_ENTITY, 3, 2, 4));
                currentBiome.getSpawns(EntityClassification.MONSTER).add(new Biome.SpawnListEntry(INFECTED_CHICKEN_ENTITY, 3, 4, 8));
                currentBiome.getSpawns(EntityClassification.MONSTER).add(new Biome.SpawnListEntry(INFECTED_SHEEP_ENTITY, 3, 3, 6));
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
                currentBiome.getSpawns(EntityClassification.MONSTER).add(new Biome.SpawnListEntry(HEROBRINE_BUILDER_ENTITY, 2, 1, 1));
                currentBiome.getSpawns(EntityClassification.MONSTER).add(new Biome.SpawnListEntry(HEROBRINE_MAGE_ENTITY, 2, 1, 1));
                currentBiome.getSpawns(EntityClassification.MONSTER).add(new Biome.SpawnListEntry(HEROBRINE_SPY_ENTITY, 3, 1, 1));
                currentBiome.getSpawns(EntityClassification.MONSTER).add(new Biome.SpawnListEntry(HEROBRINE_WARRIOR_ENTITY, 2, 1, 1));
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