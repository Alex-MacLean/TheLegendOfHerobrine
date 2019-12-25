package com.herobrine.mod.entities;

import com.herobrine.mod.HerobrineMod;
import com.herobrine.mod.items.ItemList;
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
    public static EntityType<?> HEROBRINE_ENTITY = EntityType.Builder.create((EntityType<HerobrineEntity> type, World worldIn) -> new HerobrineEntity(worldIn), EntityClassification.MONSTER).build("herobrine").setRegistryName("herobrine");

    public static void registerEntitySpawnEggs(@NotNull final RegistryEvent.Register<Item> event) {
        event.getRegistry().registerAll(
                ItemList.herobrine_spawn_egg = registerEntitySpawnEgg(HEROBRINE_ENTITY, 0x000000, 0xff0000, "herobrine_spawn_egg")
        );
    }

    public static void registerEntityWorldSpawns() {
            registerEntityWorldSpawn(HEROBRINE_ENTITY, 3, 1, 1, Biomes.BEACH, Biomes.BAMBOO_JUNGLE_HILLS, Biomes.BAMBOO_JUNGLE, Biomes.BADLANDS_PLATEAU, Biomes.BADLANDS, Biomes.BIRCH_FOREST, Biomes.BIRCH_FOREST_HILLS, Biomes.DARK_FOREST, Biomes.DARK_FOREST_HILLS, Biomes.DESERT, Biomes.DESERT_HILLS, Biomes.DESERT_LAKES, Biomes.END_BARRENS, Biomes.END_HIGHLANDS, Biomes.END_MIDLANDS, Biomes.ERODED_BADLANDS, Biomes.FLOWER_FOREST, Biomes.FOREST, Biomes.FROZEN_RIVER, Biomes.GIANT_SPRUCE_TAIGA, Biomes.GIANT_SPRUCE_TAIGA_HILLS, Biomes.GIANT_TREE_TAIGA, Biomes.GIANT_TREE_TAIGA_HILLS, Biomes.GRAVELLY_MOUNTAINS, Biomes.ICE_SPIKES, Biomes.JUNGLE, Biomes.JUNGLE_EDGE, Biomes.JUNGLE_HILLS, Biomes.MODIFIED_BADLANDS_PLATEAU, Biomes.MODIFIED_GRAVELLY_MOUNTAINS, Biomes.MODIFIED_JUNGLE, Biomes.MODIFIED_JUNGLE_EDGE, Biomes.MODIFIED_WOODED_BADLANDS_PLATEAU, Biomes.MOUNTAINS, Biomes.MUSHROOM_FIELD_SHORE, Biomes.MUSHROOM_FIELDS, Biomes.MOUNTAIN_EDGE, Biomes.NETHER, Biomes.PLAINS, Biomes.RIVER, Biomes.SAVANNA, Biomes.SAVANNA_PLATEAU, Biomes.SHATTERED_SAVANNA, Biomes.SHATTERED_SAVANNA_PLATEAU, Biomes.SMALL_END_ISLANDS, Biomes.SNOWY_BEACH, Biomes.SNOWY_MOUNTAINS, Biomes.SNOWY_TAIGA, Biomes.SNOWY_TAIGA_HILLS, Biomes.SNOWY_TAIGA_MOUNTAINS, Biomes.SNOWY_TUNDRA, Biomes.STONE_SHORE, Biomes.SUNFLOWER_PLAINS, Biomes.SWAMP, Biomes.SWAMP_HILLS, Biomes.TAIGA, Biomes.TAIGA_HILLS, Biomes.TAIGA_MOUNTAINS, Biomes.TALL_BIRCH_FOREST, Biomes.TALL_BIRCH_HILLS, Biomes.WOODED_BADLANDS_PLATEAU, Biomes.WOODED_HILLS, Biomes.WOODED_MOUNTAINS);
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