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
                ItemList.herobrine_mage_spawn_egg = registerEntitySpawnEgg(HEROBRINE_MAGE_ENTITY, 0x000000, 0x0000FF, "herobrine_mage_spawn_egg")
        );
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
                BiomeDictionary.Type.MODIFIED,
                BiomeDictionary.Type.NETHER,
                BiomeDictionary.Type.PLATEAU
        };

        BiomeDictionary.Type[] MushroomBiomeTypes = {
                BiomeDictionary.Type.MUSHROOM
        };

        for (BiomeDictionary.Type t : InfectedVillagerTypes) {
            Set<Biome> biomes = BiomeDictionary.getBiomes(t);
            for (Biome currentBiome : biomes) {
                currentBiome.getSpawns(EntityClassification.MONSTER).add(new Biome.SpawnListEntry(INFECTED_VILLAGER_ENTITY, 3, 1, 4));
            }
        }

        for (BiomeDictionary.Type t : InfectedAnimalTypes) {
            Set<Biome> biomes = BiomeDictionary.getBiomes(t);
            for (Biome currentBiome : biomes) {
                currentBiome.getSpawns(EntityClassification.MONSTER).add(new Biome.SpawnListEntry(INFECTED_PIG_ENTITY, 4, 3, 6));
                currentBiome.getSpawns(EntityClassification.MONSTER).add(new Biome.SpawnListEntry(INFECTED_COW_ENTITY, 4, 2, 4));
                currentBiome.getSpawns(EntityClassification.MONSTER).add(new Biome.SpawnListEntry(INFECTED_CHICKEN_ENTITY, 4, 4, 8));
                currentBiome.getSpawns(EntityClassification.MONSTER).add(new Biome.SpawnListEntry(INFECTED_SHEEP_ENTITY, 4, 3, 6));
            }
        }

        for (BiomeDictionary.Type t : MushroomBiomeTypes) {
            Set<Biome> biomes = BiomeDictionary.getBiomes(t);
            for (Biome currentBiome : biomes) {
                currentBiome.getSpawns(EntityClassification.MONSTER).add(new Biome.SpawnListEntry(HEROBRINE_BUILDER_ENTITY, 1, 1, 1));
                currentBiome.getSpawns(EntityClassification.MONSTER).add(new Biome.SpawnListEntry(HEROBRINE_MAGE_ENTITY, 1, 1, 1));
                currentBiome.getSpawns(EntityClassification.MONSTER).add(new Biome.SpawnListEntry(HEROBRINE_SPY_ENTITY, 1, 1, 1));
                currentBiome.getSpawns(EntityClassification.MONSTER).add(new Biome.SpawnListEntry(HEROBRINE_WARRIOR_ENTITY, 1, 1, 1));
                currentBiome.getSpawns(EntityClassification.MONSTER).add(new Biome.SpawnListEntry(INFECTED_MOOSHROOM_ENTITY, 1, 1, 1));
            }
        }

        for (BiomeDictionary.Type t : HerobrineTypes) {
            Set<Biome> biomes = BiomeDictionary.getBiomes(t);
            for (Biome currentBiome : biomes) {
                currentBiome.getSpawns(EntityClassification.MONSTER).add(new Biome.SpawnListEntry(HEROBRINE_BUILDER_ENTITY, 1, 1, 1));
                currentBiome.getSpawns(EntityClassification.MONSTER).add(new Biome.SpawnListEntry(HEROBRINE_MAGE_ENTITY, 1, 1, 1));
                currentBiome.getSpawns(EntityClassification.MONSTER).add(new Biome.SpawnListEntry(HEROBRINE_SPY_ENTITY, 2, 1, 1));
                currentBiome.getSpawns(EntityClassification.MONSTER).add(new Biome.SpawnListEntry(HEROBRINE_WARRIOR_ENTITY, 1, 1, 1));
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