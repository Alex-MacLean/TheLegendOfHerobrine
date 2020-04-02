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
import net.minecraft.world.biome.Biomes;
import net.minecraftforge.event.RegistryEvent;
import org.jetbrains.annotations.NotNull;

public class EntityRegistry {
    public static EntityType<?> HEROBRINE_ENTITY = EntityType.Builder.create((EntityType<HerobrineEntity> type, World worldIn) -> new HerobrineEntity(worldIn), EntityClassification.MONSTER).size(0.6F, 2.0F).build("herobrine").setRegistryName("herobrine");
    public static EntityType<?> HOLY_WATER_ENTITY = EntityType.Builder.create((EntityType<HolyWaterEntity> type, World worldIn) -> new HolyWaterEntity(worldIn), EntityClassification.MISC).build("holy_water").setRegistryName("holy_water");
    public static EntityType<?> UNHOLY_WATER_ENTITY = EntityType.Builder.create((EntityType<UnholyWaterEntity> type, World worldIn) -> new UnholyWaterEntity(worldIn), EntityClassification.MISC).build("unholy_water").setRegistryName("unholy_water");
    public static EntityType<?> INFECTED_PIG_ENTITY = EntityType.Builder.create((EntityType<InfectedPigEntity> type, World worldIn) -> new InfectedPigEntity(worldIn), EntityClassification.MONSTER).size(0.9F, 0.9F).build("infected_pig").setRegistryName("infected_pig");
    public static EntityType<?> INFECTED_CHICKEN_ENTITY = EntityType.Builder.create((EntityType<InfectedChickenEntity> type, World worldIn) -> new InfectedChickenEntity(worldIn), EntityClassification.MONSTER).size(0.4F, 0.7F).build("infected_chicken").setRegistryName("infected_chicken");
    public static EntityType<?> INFECTED_SHEEP_ENTITY = EntityType.Builder.create((EntityType<InfectedSheepEntity> type, World worldIn) -> new InfectedSheepEntity(worldIn), EntityClassification.MONSTER).size(0.9F, 1.3F).build("infected_sheep").setRegistryName("infected_sheep");
    public static EntityType<?> INFECTED_COW_ENTITY = EntityType.Builder.create((EntityType<InfectedCowEntity> type, World worldIn) -> new InfectedCowEntity(worldIn), EntityClassification.MONSTER).size(0.9F, 1.4F).build("infected_cow").setRegistryName("infected_cow");

    public static void registerEntitySpawnEggs(@NotNull final RegistryEvent.Register<Item> event) {
        event.getRegistry().registerAll(
                ItemList.herobrine_spawn_egg = registerEntitySpawnEgg(HEROBRINE_ENTITY, 0x000000, 0xff0000, "herobrine_spawn_egg"),
                ItemList.infected_pig_spawn_egg = registerEntitySpawnEgg(INFECTED_PIG_ENTITY, 0xF0A5A2, 0xffffff, "infected_pig_spawn_egg"),
                ItemList.infected_chicken_spawn_egg = registerEntitySpawnEgg(INFECTED_CHICKEN_ENTITY, 0xA1A1A1, 0xffffff, "infected_chicken_spawn_egg"),
                ItemList.infected_sheep_spawn_egg = registerEntitySpawnEgg(INFECTED_SHEEP_ENTITY, 0xE7E7E7, 0xffffff, "infected_sheep_spawn_egg"),
                ItemList.infected_cow_spawn_egg = registerEntitySpawnEgg(INFECTED_COW_ENTITY, 0x443626, 0xffffff, "infected_cow_spawn_egg")
        );
    }

    public static void registerEntityWorldSpawns() {
        registerEntityWorldSpawn(HEROBRINE_ENTITY, 3, 1, 1, Biomes.BEACH, Biomes.BAMBOO_JUNGLE_HILLS, Biomes.BAMBOO_JUNGLE, Biomes.BADLANDS_PLATEAU, Biomes.BADLANDS, Biomes.BIRCH_FOREST, Biomes.BIRCH_FOREST_HILLS, Biomes.DARK_FOREST, Biomes.DARK_FOREST_HILLS, Biomes.DESERT, Biomes.DESERT_HILLS, Biomes.DESERT_LAKES, Biomes.END_BARRENS, Biomes.END_HIGHLANDS, Biomes.END_MIDLANDS, Biomes.ERODED_BADLANDS, Biomes.FLOWER_FOREST, Biomes.FOREST, Biomes.FROZEN_RIVER, Biomes.GIANT_SPRUCE_TAIGA, Biomes.GIANT_SPRUCE_TAIGA_HILLS, Biomes.GIANT_TREE_TAIGA, Biomes.GIANT_TREE_TAIGA_HILLS, Biomes.GRAVELLY_MOUNTAINS, Biomes.ICE_SPIKES, Biomes.JUNGLE, Biomes.JUNGLE_EDGE, Biomes.JUNGLE_HILLS, Biomes.MODIFIED_BADLANDS_PLATEAU, Biomes.MODIFIED_GRAVELLY_MOUNTAINS, Biomes.MODIFIED_JUNGLE, Biomes.MODIFIED_JUNGLE_EDGE, Biomes.MODIFIED_WOODED_BADLANDS_PLATEAU, Biomes.MOUNTAINS, Biomes.MOUNTAIN_EDGE, Biomes.NETHER, Biomes.PLAINS, Biomes.RIVER, Biomes.SAVANNA, Biomes.SAVANNA_PLATEAU, Biomes.SHATTERED_SAVANNA, Biomes.SHATTERED_SAVANNA_PLATEAU, Biomes.SMALL_END_ISLANDS, Biomes.SNOWY_BEACH, Biomes.SNOWY_MOUNTAINS, Biomes.SNOWY_TAIGA, Biomes.SNOWY_TAIGA_HILLS, Biomes.SNOWY_TAIGA_MOUNTAINS, Biomes.SNOWY_TUNDRA, Biomes.STONE_SHORE, Biomes.SUNFLOWER_PLAINS, Biomes.SWAMP, Biomes.SWAMP_HILLS, Biomes.TAIGA, Biomes.TAIGA_HILLS, Biomes.TAIGA_MOUNTAINS, Biomes.TALL_BIRCH_FOREST, Biomes.TALL_BIRCH_HILLS, Biomes.WOODED_BADLANDS_PLATEAU, Biomes.WOODED_HILLS, Biomes.WOODED_MOUNTAINS);
        registerEntityWorldSpawn(INFECTED_PIG_ENTITY, 7, 3, 6, Biomes.BAMBOO_JUNGLE_HILLS, Biomes.BAMBOO_JUNGLE, Biomes.BADLANDS_PLATEAU, Biomes.BADLANDS, Biomes.BIRCH_FOREST, Biomes.BIRCH_FOREST_HILLS, Biomes.DARK_FOREST, Biomes.DARK_FOREST_HILLS, Biomes.DESERT, Biomes.DESERT_HILLS, Biomes.DESERT_LAKES, Biomes.ERODED_BADLANDS, Biomes.FLOWER_FOREST, Biomes.FOREST, Biomes.GIANT_SPRUCE_TAIGA, Biomes.GIANT_SPRUCE_TAIGA_HILLS, Biomes.GIANT_TREE_TAIGA, Biomes.GIANT_TREE_TAIGA_HILLS, Biomes.GRAVELLY_MOUNTAINS, Biomes.JUNGLE, Biomes.JUNGLE_EDGE, Biomes.JUNGLE_HILLS, Biomes.MODIFIED_BADLANDS_PLATEAU, Biomes.MODIFIED_GRAVELLY_MOUNTAINS, Biomes.MODIFIED_JUNGLE, Biomes.MODIFIED_JUNGLE_EDGE, Biomes.MODIFIED_WOODED_BADLANDS_PLATEAU, Biomes.MOUNTAINS, Biomes.MOUNTAIN_EDGE, Biomes.PLAINS, Biomes.SAVANNA, Biomes.SAVANNA_PLATEAU, Biomes.SHATTERED_SAVANNA, Biomes.SHATTERED_SAVANNA_PLATEAU, Biomes.SNOWY_BEACH, Biomes.SNOWY_MOUNTAINS, Biomes.SNOWY_TAIGA, Biomes.SNOWY_TAIGA_HILLS, Biomes.SNOWY_TAIGA_MOUNTAINS, Biomes.SNOWY_TUNDRA, Biomes.STONE_SHORE, Biomes.SUNFLOWER_PLAINS, Biomes.SWAMP, Biomes.SWAMP_HILLS, Biomes.TAIGA, Biomes.TAIGA_HILLS, Biomes.TAIGA_MOUNTAINS, Biomes.TALL_BIRCH_FOREST, Biomes.TALL_BIRCH_HILLS, Biomes.WOODED_BADLANDS_PLATEAU, Biomes.WOODED_HILLS, Biomes.WOODED_MOUNTAINS);
        registerEntityWorldSpawn(INFECTED_CHICKEN_ENTITY, 7, 4, 8, Biomes.BAMBOO_JUNGLE_HILLS, Biomes.BAMBOO_JUNGLE, Biomes.BADLANDS_PLATEAU, Biomes.BADLANDS, Biomes.BIRCH_FOREST, Biomes.BIRCH_FOREST_HILLS, Biomes.DARK_FOREST, Biomes.DARK_FOREST_HILLS, Biomes.DESERT, Biomes.DESERT_HILLS, Biomes.DESERT_LAKES, Biomes.ERODED_BADLANDS, Biomes.FLOWER_FOREST, Biomes.FOREST, Biomes.GIANT_SPRUCE_TAIGA, Biomes.GIANT_SPRUCE_TAIGA_HILLS, Biomes.GIANT_TREE_TAIGA, Biomes.GIANT_TREE_TAIGA_HILLS, Biomes.GRAVELLY_MOUNTAINS, Biomes.JUNGLE, Biomes.JUNGLE_EDGE, Biomes.JUNGLE_HILLS, Biomes.MODIFIED_BADLANDS_PLATEAU, Biomes.MODIFIED_GRAVELLY_MOUNTAINS, Biomes.MODIFIED_JUNGLE, Biomes.MODIFIED_JUNGLE_EDGE, Biomes.MODIFIED_WOODED_BADLANDS_PLATEAU, Biomes.MOUNTAINS, Biomes.MOUNTAIN_EDGE, Biomes.PLAINS, Biomes.SAVANNA, Biomes.SAVANNA_PLATEAU, Biomes.SHATTERED_SAVANNA, Biomes.SHATTERED_SAVANNA_PLATEAU, Biomes.SNOWY_BEACH, Biomes.SNOWY_MOUNTAINS, Biomes.SNOWY_TAIGA, Biomes.SNOWY_TAIGA_HILLS, Biomes.SNOWY_TAIGA_MOUNTAINS, Biomes.SNOWY_TUNDRA, Biomes.STONE_SHORE, Biomes.SUNFLOWER_PLAINS, Biomes.SWAMP, Biomes.SWAMP_HILLS, Biomes.TAIGA, Biomes.TAIGA_HILLS, Biomes.TAIGA_MOUNTAINS, Biomes.TALL_BIRCH_FOREST, Biomes.TALL_BIRCH_HILLS, Biomes.WOODED_BADLANDS_PLATEAU, Biomes.WOODED_HILLS, Biomes.WOODED_MOUNTAINS);
        registerEntityWorldSpawn(INFECTED_SHEEP_ENTITY, 7, 3, 6, Biomes.BAMBOO_JUNGLE_HILLS, Biomes.BAMBOO_JUNGLE, Biomes.BADLANDS_PLATEAU, Biomes.BADLANDS, Biomes.BIRCH_FOREST, Biomes.BIRCH_FOREST_HILLS, Biomes.DARK_FOREST, Biomes.DARK_FOREST_HILLS, Biomes.DESERT, Biomes.DESERT_HILLS, Biomes.DESERT_LAKES, Biomes.ERODED_BADLANDS, Biomes.FLOWER_FOREST, Biomes.FOREST, Biomes.GIANT_SPRUCE_TAIGA, Biomes.GIANT_SPRUCE_TAIGA_HILLS, Biomes.GIANT_TREE_TAIGA, Biomes.GIANT_TREE_TAIGA_HILLS, Biomes.GRAVELLY_MOUNTAINS, Biomes.JUNGLE, Biomes.JUNGLE_EDGE, Biomes.JUNGLE_HILLS, Biomes.MODIFIED_BADLANDS_PLATEAU, Biomes.MODIFIED_GRAVELLY_MOUNTAINS, Biomes.MODIFIED_JUNGLE, Biomes.MODIFIED_JUNGLE_EDGE, Biomes.MODIFIED_WOODED_BADLANDS_PLATEAU, Biomes.MOUNTAINS, Biomes.MOUNTAIN_EDGE, Biomes.PLAINS, Biomes.SAVANNA, Biomes.SAVANNA_PLATEAU, Biomes.SHATTERED_SAVANNA, Biomes.SHATTERED_SAVANNA_PLATEAU, Biomes.SNOWY_BEACH, Biomes.SNOWY_MOUNTAINS, Biomes.SNOWY_TAIGA, Biomes.SNOWY_TAIGA_HILLS, Biomes.SNOWY_TAIGA_MOUNTAINS, Biomes.SNOWY_TUNDRA, Biomes.STONE_SHORE, Biomes.SUNFLOWER_PLAINS, Biomes.SWAMP, Biomes.SWAMP_HILLS, Biomes.TAIGA, Biomes.TAIGA_HILLS, Biomes.TAIGA_MOUNTAINS, Biomes.TALL_BIRCH_FOREST, Biomes.TALL_BIRCH_HILLS, Biomes.WOODED_BADLANDS_PLATEAU, Biomes.WOODED_HILLS, Biomes.WOODED_MOUNTAINS);
        registerEntityWorldSpawn(INFECTED_COW_ENTITY, 7, 2, 4, Biomes.BAMBOO_JUNGLE_HILLS, Biomes.BAMBOO_JUNGLE, Biomes.BADLANDS_PLATEAU, Biomes.BADLANDS, Biomes.BIRCH_FOREST, Biomes.BIRCH_FOREST_HILLS, Biomes.DARK_FOREST, Biomes.DARK_FOREST_HILLS, Biomes.DESERT, Biomes.DESERT_HILLS, Biomes.DESERT_LAKES, Biomes.ERODED_BADLANDS, Biomes.FLOWER_FOREST, Biomes.FOREST, Biomes.GIANT_SPRUCE_TAIGA, Biomes.GIANT_SPRUCE_TAIGA_HILLS, Biomes.GIANT_TREE_TAIGA, Biomes.GIANT_TREE_TAIGA_HILLS, Biomes.GRAVELLY_MOUNTAINS, Biomes.JUNGLE, Biomes.JUNGLE_EDGE, Biomes.JUNGLE_HILLS, Biomes.MODIFIED_BADLANDS_PLATEAU, Biomes.MODIFIED_GRAVELLY_MOUNTAINS, Biomes.MODIFIED_JUNGLE, Biomes.MODIFIED_JUNGLE_EDGE, Biomes.MODIFIED_WOODED_BADLANDS_PLATEAU, Biomes.MOUNTAINS, Biomes.MOUNTAIN_EDGE, Biomes.PLAINS, Biomes.SAVANNA, Biomes.SAVANNA_PLATEAU, Biomes.SHATTERED_SAVANNA, Biomes.SHATTERED_SAVANNA_PLATEAU, Biomes.SNOWY_BEACH, Biomes.SNOWY_MOUNTAINS, Biomes.SNOWY_TAIGA, Biomes.SNOWY_TAIGA_HILLS, Biomes.SNOWY_TAIGA_MOUNTAINS, Biomes.SNOWY_TUNDRA, Biomes.STONE_SHORE, Biomes.SUNFLOWER_PLAINS, Biomes.SWAMP, Biomes.SWAMP_HILLS, Biomes.TAIGA, Biomes.TAIGA_HILLS, Biomes.TAIGA_MOUNTAINS, Biomes.TALL_BIRCH_FOREST, Biomes.TALL_BIRCH_HILLS, Biomes.WOODED_BADLANDS_PLATEAU, Biomes.WOODED_HILLS, Biomes.WOODED_MOUNTAINS);
    }

    @NotNull
    private static Item registerEntitySpawnEgg(EntityType<?> type, int primaryColor, int secondaryColor, String name) {
        SpawnEggItem item = new SpawnEggItem(type, primaryColor, secondaryColor, new Item.Properties().group(ItemGroup.MISC));
        item.setRegistryName(HerobrineMod.location(name));
        return item;
    }

    private static void registerEntityWorldSpawn(EntityType<?> entity, int weight, int minGroupCount, int maxGroupCount, @NotNull Biome... biomes){
            for (Biome biome : biomes) {
                biome.getSpawns(entity.getClassification()).add(new Biome.SpawnListEntry(entity, weight, minGroupCount, maxGroupCount));
            }
    }
}