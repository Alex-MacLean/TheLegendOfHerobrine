package com.herobrine.mod.util.entities;

import com.herobrine.mod.HerobrineMod;
import com.herobrine.mod.config.Config;
import com.herobrine.mod.entities.*;
import com.herobrine.mod.util.items.ItemList;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntitySpawnPlacementRegistry;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.GlobalEntityTypeAttributes;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.MobSpawnInfo;
import net.minecraft.world.gen.Heightmap;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Objects;
import java.util.Set;

@SuppressWarnings("unchecked")
@Mod.EventBusSubscriber(modid = HerobrineMod.MODID)
public class EntityRegistry {
    public static EntityType<HerobrineWarriorEntity> HEROBRINE_WARRIOR_ENTITY = (EntityType<HerobrineWarriorEntity>) EntityType.Builder.of((EntityType<HerobrineWarriorEntity> type, World worldIn) -> new HerobrineWarriorEntity(worldIn), EntityClassification.MONSTER).sized(0.6F, 1.8F).build("herobrine_warrior").setRegistryName("herobrine_warrior");
    public static EntityType<?> HOLY_WATER_ENTITY = EntityType.Builder.of((EntityType<HolyWaterEntity> type, World worldIn) -> new HolyWaterEntity(worldIn), EntityClassification.MISC).build("holy_water").setRegistryName("holy_water");
    public static EntityType<?> UNHOLY_WATER_ENTITY = EntityType.Builder.of((EntityType<UnholyWaterEntity> type, World worldIn) -> new UnholyWaterEntity(worldIn), EntityClassification.MISC).build("unholy_water").setRegistryName("unholy_water");
    public static EntityType<InfectedPigEntity> INFECTED_PIG_ENTITY = (EntityType<InfectedPigEntity>) EntityType.Builder.of((EntityType<InfectedPigEntity> type, World worldIn) -> new InfectedPigEntity(worldIn), EntityClassification.MONSTER).sized(0.9F, 0.9F).build("infected_pig").setRegistryName("infected_pig");
    public static EntityType<InfectedChickenEntity> INFECTED_CHICKEN_ENTITY = (EntityType<InfectedChickenEntity>) EntityType.Builder.of((EntityType<InfectedChickenEntity> type, World worldIn) -> new InfectedChickenEntity(worldIn), EntityClassification.MONSTER).sized(0.4F, 0.7F).build("infected_chicken").setRegistryName("infected_chicken");
    public static EntityType<InfectedSheepEntity> INFECTED_SHEEP_ENTITY = (EntityType<InfectedSheepEntity>) EntityType.Builder.of((EntityType<InfectedSheepEntity> type, World worldIn) -> new InfectedSheepEntity(worldIn), EntityClassification.MONSTER).sized(0.9F, 1.3F).build("infected_sheep").setRegistryName("infected_sheep");
    public static EntityType<InfectedCowEntity> INFECTED_COW_ENTITY = (EntityType<InfectedCowEntity>) EntityType.Builder.of((EntityType<InfectedCowEntity> type, World worldIn) -> new InfectedCowEntity(worldIn), EntityClassification.MONSTER).sized(0.9F, 1.4F).build("infected_cow").setRegistryName("infected_cow");
    public static EntityType<InfectedMooshroomEntity> INFECTED_MOOSHROOM_ENTITY = (EntityType<InfectedMooshroomEntity>) EntityType.Builder.of((EntityType<InfectedMooshroomEntity> type, World worldIn) -> new InfectedMooshroomEntity(worldIn), EntityClassification.MONSTER).sized(0.9F, 1.4F).build("infected_mooshroom").setRegistryName("infected_mooshroom");
    public static EntityType<InfectedVillagerEntity> INFECTED_VILLAGER_ENTITY = (EntityType<InfectedVillagerEntity>) EntityType.Builder.of((EntityType<InfectedVillagerEntity> type, World worldIn) -> new InfectedVillagerEntity(worldIn), EntityClassification.MONSTER).sized(0.6F, 1.95F).build("infected_villager").setRegistryName("infected_villager");
    public static EntityType<HerobrineSpyEntity> HEROBRINE_SPY_ENTITY = (EntityType<HerobrineSpyEntity>) EntityType.Builder.of((EntityType<HerobrineSpyEntity> type, World worldIn) -> new HerobrineSpyEntity(worldIn), EntityClassification.MONSTER).sized(0.6F, 1.8F).build("herobrine_spy").setRegistryName("herobrine_spy");
    public static EntityType<HerobrineBuilderEntity> HEROBRINE_BUILDER_ENTITY = (EntityType<HerobrineBuilderEntity>) EntityType.Builder.of((EntityType<HerobrineBuilderEntity> type, World worldIn) -> new HerobrineBuilderEntity(worldIn), EntityClassification.MONSTER).sized(0.6F, 1.8F).build("herobrine_builder").setRegistryName("herobrine_builder");
    public static EntityType<HerobrineMageEntity> HEROBRINE_MAGE_ENTITY = (EntityType<HerobrineMageEntity>) EntityType.Builder.of((EntityType<HerobrineMageEntity> type, World worldIn) -> new HerobrineMageEntity(worldIn), EntityClassification.MONSTER).sized(0.6F, 1.8F).build("herobrine_mage").setRegistryName("herobrine_mage");
    public static EntityType<FakeHerobrineMageEntity> FAKE_HEROBRINE_MAGE_ENTITY = (EntityType<FakeHerobrineMageEntity>) EntityType.Builder.of((EntityType<FakeHerobrineMageEntity> type, World worldIn) -> new FakeHerobrineMageEntity(worldIn), EntityClassification.MONSTER).sized(0.6F, 1.8F).build("fake_herobrine_mage").setRegistryName("fake_herobrine_mage");
    public static EntityType<SteveSurvivorEntity> STEVE_SURVIVOR_ENTITY = (EntityType<SteveSurvivorEntity>) EntityType.Builder.of((EntityType<SteveSurvivorEntity> type, World worldIn) -> new SteveSurvivorEntity(worldIn), EntityClassification.CREATURE).sized(0.6F, 1.8F).build("steve_survivor").setRegistryName("steve_survivor");
    public static EntityType<AlexSurvivorEntity> ALEX_SURVIVOR_ENTITY = (EntityType<AlexSurvivorEntity>) EntityType.Builder.of((EntityType<AlexSurvivorEntity> type, World worldIn) -> new AlexSurvivorEntity(worldIn), EntityClassification.CREATURE).sized(0.6F, 1.8F).build("alex_survivor").setRegistryName("alex_survivor");
    public static EntityType<InfectedWolfEntity> INFECTED_WOLF_ENTITY = (EntityType<InfectedWolfEntity>) EntityType.Builder.of((EntityType<InfectedWolfEntity> type, World worldIn) -> new InfectedWolfEntity(worldIn), EntityClassification.MONSTER).sized(0.6F, 0.85F).build("infected_wolf").setRegistryName("infected_wolf");
    public static EntityType<InfectedLlamaEntity> INFECTED_LLAMA_ENTITY = (EntityType<InfectedLlamaEntity>) EntityType.Builder.of((EntityType<InfectedLlamaEntity> type, World worldIn) -> new InfectedLlamaEntity(worldIn), EntityClassification.MONSTER).sized(0.9F, 1.87F).build("infected_llama").setRegistryName("infected_llama");
    public static EntityType<InfectedHorseEntity> INFECTED_HORSE_ENTITY = (EntityType<InfectedHorseEntity>) EntityType.Builder.of((EntityType<InfectedHorseEntity> type, World worldIn) -> new InfectedHorseEntity(worldIn), EntityClassification.MONSTER).sized(1.3964844F, 1.6F).build("infected_horse").setRegistryName("infected_horse");
    public static EntityType<InfectedDonkeyEntity> INFECTED_DONKEY_ENTITY = (EntityType<InfectedDonkeyEntity>) EntityType.Builder.of((EntityType<InfectedDonkeyEntity> type, World worldIn) -> new InfectedDonkeyEntity(worldIn), EntityClassification.MONSTER).sized(1.3964844F, 1.5F).build("infected_donkey").setRegistryName("infected_donkey");
    public static EntityType<InfectedRabbitEntity> INFECTED_RABBIT_ENTITY = (EntityType<InfectedRabbitEntity>) EntityType.Builder.of((EntityType<InfectedRabbitEntity> type, World worldIn) -> new InfectedRabbitEntity(worldIn), EntityClassification.MONSTER).sized(0.4F, 0.5F).build("infected_rabbit").setRegistryName("infected_rabbit");
    public static EntityType<InfectedBatEntity> INFECTED_BAT_ENTITY = (EntityType<InfectedBatEntity>) EntityType.Builder.of((EntityType<InfectedBatEntity> type, World worldIn) -> new InfectedBatEntity(worldIn), EntityClassification.MONSTER).sized(0.5F, 0.9F).build("infected_bat").setRegistryName("infected_bat");
    public static EntityType<HerobrineStalkerEntity> HEROBRINE_STALKER_ENTITY = (EntityType<HerobrineStalkerEntity>) EntityType.Builder.of((EntityType<HerobrineStalkerEntity> type, World worldIn) -> new HerobrineStalkerEntity(worldIn), EntityClassification.MONSTER).sized(0.6F, 1.8F).build("herobrine_stalker").setRegistryName("herobrine_stalker");

    public static void registerEntitySpawnEggs(@NotNull final RegistryEvent.Register<Item> event) {
        event.getRegistry().registerAll(
                ItemList.herobrine_warrior_spawn_egg = registerEntitySpawnEggs(HEROBRINE_WARRIOR_ENTITY, 0x000000, 0xFF0000, "herobrine_warrior_spawn_egg"),
                ItemList.infected_pig_spawn_egg = registerEntitySpawnEggs(INFECTED_PIG_ENTITY, 0xF0A5A2, 0xFFFFFF, "infected_pig_spawn_egg"),
                ItemList.infected_chicken_spawn_egg = registerEntitySpawnEggs(INFECTED_CHICKEN_ENTITY, 0xA1A1A1, 0xFFFFFF, "infected_chicken_spawn_egg"),
                ItemList.infected_sheep_spawn_egg = registerEntitySpawnEggs(INFECTED_SHEEP_ENTITY, 0xE7E7E7, 0xFFFFFF, "infected_sheep_spawn_egg"),
                ItemList.infected_cow_spawn_egg = registerEntitySpawnEggs(INFECTED_COW_ENTITY, 0x443626, 0xFFFFFF, "infected_cow_spawn_egg"),
                ItemList.infected_mooshroom_spawn_egg = registerEntitySpawnEggs(INFECTED_MOOSHROOM_ENTITY, 0xA00F10, 0xFFFFFF, "infected_mooshroom_spawn_egg"),
                ItemList.infected_villager_spawn_egg = registerEntitySpawnEggs(INFECTED_VILLAGER_ENTITY, 0x563C33, 0xFFFFFF, "infected_villager_spawn_egg"),
                ItemList.herobrine_spy_spawn_egg = registerEntitySpawnEggs(HEROBRINE_SPY_ENTITY, 0x000000, 0x00FF00, "herobrine_spy_spawn_egg"),
                ItemList.herobrine_builder_spawn_egg = registerEntitySpawnEggs(HEROBRINE_BUILDER_ENTITY, 0x000000, 0xFFFF00, "herobrine_builder_spawn_egg"),
                ItemList.herobrine_mage_spawn_egg = registerEntitySpawnEggs(HEROBRINE_MAGE_ENTITY, 0x000000, 0x0000FF, "herobrine_mage_spawn_egg"),
                ItemList.steve_survivor_spawn_egg = registerEntitySpawnEggs(STEVE_SURVIVOR_ENTITY, 0x00AFAF, 0xAA7D66, "steve_survivor_spawn_egg"),
                ItemList.alex_survivor_spawn_egg = registerEntitySpawnEggs(ALEX_SURVIVOR_ENTITY, 0x8BBA88, 0xF2DABA, "alex_survivor_spawn_egg"),
                ItemList.infected_wolf_spawn_egg = registerEntitySpawnEggs(INFECTED_WOLF_ENTITY, 0xD7D3D3, 0xFFFFFF, "infected_wolf_spawn_egg"),
                ItemList.infected_llama_spawn_egg = registerEntitySpawnEggs(INFECTED_LLAMA_ENTITY, 0xC09E7D, 0xFFFFFF, "infected_llama_spawn_egg"),
                ItemList.infected_horse_spawn_egg = registerEntitySpawnEggs(INFECTED_HORSE_ENTITY, 0xC09E7D, 0xFFFFFF, "infected_horse_spawn_egg"),
                ItemList.infected_donkey_spawn_egg = registerEntitySpawnEggs(INFECTED_DONKEY_ENTITY, 0x534539, 0xFFFFFF, "infected_donkey_spawn_egg"),
                ItemList.infected_rabbit_spawn_egg = registerEntitySpawnEggs(INFECTED_RABBIT_ENTITY, 0x995F40, 0xFFFFFF, "infected_rabbit_spawn_egg"),
                ItemList.infected_bat_spawn_egg = registerEntitySpawnEggs(INFECTED_BAT_ENTITY, 0x4C3E30, 0xFFFFFF, "infected_bat_spawn_egg"),
                ItemList.herobrine_stalker_spawn_egg = registerEntitySpawnEggs(HEROBRINE_STALKER_ENTITY, 0x000000, 0xFFA500, "herobrine_stalker_spawn_egg")
        );
    }

    @NotNull
    private static Item registerEntitySpawnEggs(EntityType<?> type, int primaryColor, int secondaryColor, String name) {
        SpawnEggItem item = new SpawnEggItem(type, primaryColor, secondaryColor, new Item.Properties().tab(ItemGroup.TAB_MISC));
        item.setRegistryName(HerobrineMod.location(name));
        return item;
    }

    public static void registerEntityAttributes() {
        GlobalEntityTypeAttributes.put(HEROBRINE_WARRIOR_ENTITY, HerobrineWarriorEntity.registerAttributes().build());
        GlobalEntityTypeAttributes.put((EntityType<? extends LivingEntity>) HOLY_WATER_ENTITY, LivingEntity.createLivingAttributes().build());
        GlobalEntityTypeAttributes.put((EntityType<? extends LivingEntity>) UNHOLY_WATER_ENTITY, LivingEntity.createLivingAttributes().build());
        GlobalEntityTypeAttributes.put(INFECTED_PIG_ENTITY, InfectedPigEntity.registerAttributes().build());
        GlobalEntityTypeAttributes.put(INFECTED_CHICKEN_ENTITY, InfectedChickenEntity.registerAttributes().build());
        GlobalEntityTypeAttributes.put(INFECTED_SHEEP_ENTITY, InfectedSheepEntity.registerAttributes().build());
        GlobalEntityTypeAttributes.put(INFECTED_COW_ENTITY, InfectedCowEntity.registerAttributes().build());
        GlobalEntityTypeAttributes.put(INFECTED_MOOSHROOM_ENTITY, InfectedMooshroomEntity.registerAttributes().build());
        GlobalEntityTypeAttributes.put(INFECTED_VILLAGER_ENTITY, InfectedVillagerEntity.registerAttributes().build());
        GlobalEntityTypeAttributes.put(HEROBRINE_SPY_ENTITY, HerobrineSpyEntity.registerAttributes().build());
        GlobalEntityTypeAttributes.put(HEROBRINE_BUILDER_ENTITY, HerobrineBuilderEntity.registerAttributes().build());
        GlobalEntityTypeAttributes.put(HEROBRINE_MAGE_ENTITY, HerobrineMageEntity.registerAttributes().build());
        GlobalEntityTypeAttributes.put(FAKE_HEROBRINE_MAGE_ENTITY, FakeHerobrineMageEntity.registerAttributes().build());
        GlobalEntityTypeAttributes.put(STEVE_SURVIVOR_ENTITY, AbstractSurvivorEntity.registerAttributes().build());
        GlobalEntityTypeAttributes.put(ALEX_SURVIVOR_ENTITY, AbstractSurvivorEntity.registerAttributes().build());
        GlobalEntityTypeAttributes.put(INFECTED_WOLF_ENTITY, InfectedWolfEntity.registerAttributes().build());
        GlobalEntityTypeAttributes.put(INFECTED_LLAMA_ENTITY, InfectedLlamaEntity.registerAttributes().build());
        GlobalEntityTypeAttributes.put(INFECTED_HORSE_ENTITY, InfectedHorseEntity.registerAttributes().build());
        GlobalEntityTypeAttributes.put(INFECTED_DONKEY_ENTITY, InfectedDonkeyEntity.registerAttributes().build());
        GlobalEntityTypeAttributes.put(INFECTED_RABBIT_ENTITY, InfectedRabbitEntity.registerAttributes().build());
        GlobalEntityTypeAttributes.put(INFECTED_BAT_ENTITY, InfectedBatEntity.registerAttributes().build());
        GlobalEntityTypeAttributes.put(HEROBRINE_STALKER_ENTITY, HerobrineStalkerEntity.registerAttributes().build());
    }

    public static void registerSpawnPlacement() {
        EntitySpawnPlacementRegistry.register(EntityRegistry.INFECTED_BAT_ENTITY, EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, InfectedBatEntity::canSpawn);
        EntitySpawnPlacementRegistry.register(EntityRegistry.HEROBRINE_SPY_ENTITY, EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, AbstractHerobrineEntity::canSpawn);
        EntitySpawnPlacementRegistry.register(EntityRegistry.HEROBRINE_WARRIOR_ENTITY, EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, AbstractHerobrineEntity::canSpawn);
        EntitySpawnPlacementRegistry.register(EntityRegistry.HEROBRINE_BUILDER_ENTITY, EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, AbstractHerobrineEntity::canSpawn);
        EntitySpawnPlacementRegistry.register(EntityRegistry.HEROBRINE_MAGE_ENTITY, EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, AbstractHerobrineEntity::canSpawn);
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
        EntitySpawnPlacementRegistry.register(EntityRegistry.HEROBRINE_STALKER_ENTITY, EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, AbstractHerobrineEntity::canSpawn);
    }

    @SubscribeEvent
    public static void onBiomeLoad(BiomeLoadingEvent event) {
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

        RegistryKey<Biome> key = RegistryKey.create(Registry.BIOME_REGISTRY, Objects.requireNonNull(event.getName()));
        Set<BiomeDictionary.Type> types = BiomeDictionary.getTypes(key);
        for (BiomeDictionary.Type t : HerobrineTypes) {
            List<MobSpawnInfo.Spawners> spawns = event.getSpawns().getSpawner(EntityClassification.MONSTER);
            if (types.contains(t)) {
                if(event.getCategory() != Biome.Category.NETHER) {
                    spawns.add(new MobSpawnInfo.Spawners(HEROBRINE_WARRIOR_ENTITY, Config.COMMON.HerobrineWarriorWeight.get(), 1, 1));
                    spawns.add(new MobSpawnInfo.Spawners(HEROBRINE_BUILDER_ENTITY, Config.COMMON.HerobrineBuilderWeight.get(), 1, 1));
                    spawns.add(new MobSpawnInfo.Spawners(HEROBRINE_MAGE_ENTITY, Config.COMMON.HerobrineMageWeight.get(), 1, 1));
                    spawns.add(new MobSpawnInfo.Spawners(HEROBRINE_SPY_ENTITY, Config.COMMON.HerobrineSpyWeight.get(), 1, 1));
                    spawns.add(new MobSpawnInfo.Spawners(INFECTED_BAT_ENTITY, (int) (Config.COMMON.InfectedMobWeight.get() * 2.5), 1, 1));
                    spawns.add(new MobSpawnInfo.Spawners(HEROBRINE_STALKER_ENTITY, Config.COMMON.HerobrineStalkerWeight.get(), 1, 1));
                }
            }
        }

        for (BiomeDictionary.Type t : NetherTypes) {
            List<MobSpawnInfo.Spawners> spawns = event.getSpawns().getSpawner(EntityClassification.MONSTER);
            if (types.contains(t)) {
                spawns.add(new MobSpawnInfo.Spawners(HEROBRINE_WARRIOR_ENTITY, Config.COMMON.HerobrineWarriorWeight.get() / 4, 1, 1));
                spawns.add(new MobSpawnInfo.Spawners(HEROBRINE_BUILDER_ENTITY, Config.COMMON.HerobrineBuilderWeight.get() / 4, 1, 1));
                spawns.add(new MobSpawnInfo.Spawners(HEROBRINE_MAGE_ENTITY, Config.COMMON.HerobrineMageWeight.get() / 4, 1, 1));
                spawns.add(new MobSpawnInfo.Spawners(HEROBRINE_SPY_ENTITY, Config.COMMON.HerobrineSpyWeight.get() / 5, 1, 1));
                spawns.add(new MobSpawnInfo.Spawners(HEROBRINE_STALKER_ENTITY, Config.COMMON.HerobrineStalkerWeight.get() / 5, 1, 1));
            }
        }

        for (BiomeDictionary.Type t : EndTypes) {
            List<MobSpawnInfo.Spawners> spawns = event.getSpawns().getSpawner(EntityClassification.MONSTER);
            if (types.contains(t) && !types.contains(BiomeDictionary.Type.COLD) && !types.contains(BiomeDictionary.Type.DRY)) {
                if(Config.COMMON.HerobrineEndSpawnType.get() > 0) {
                    spawns.add(new MobSpawnInfo.Spawners(HEROBRINE_SPY_ENTITY, 1, 1, 1));
                    spawns.add(new MobSpawnInfo.Spawners(HEROBRINE_STALKER_ENTITY, 1, 1, 1));
                }
                if(Config.COMMON.HerobrineEndSpawnType.get() > 1) {
                    spawns.add(new MobSpawnInfo.Spawners(HEROBRINE_BUILDER_ENTITY, 1, 1, 1));
                }
                if(Config.COMMON.HerobrineEndSpawnType.get() > 2) {
                    spawns.add(new MobSpawnInfo.Spawners(HEROBRINE_WARRIOR_ENTITY, 1, 1, 1));
                    spawns.add(new MobSpawnInfo.Spawners(HEROBRINE_MAGE_ENTITY, 1, 1, 1));
                }
            }
        }

        for (BiomeDictionary.Type t : MushroomBiomeTypes) {
            List<MobSpawnInfo.Spawners> spawns = event.getSpawns().getSpawner(EntityClassification.MONSTER);
            if (types.contains(t)) {
                spawns.add(new MobSpawnInfo.Spawners(INFECTED_MOOSHROOM_ENTITY, 1, 1, 1));
            }
        }

        for (BiomeDictionary.Type t : InfectedAnimalTypes) {
            List<MobSpawnInfo.Spawners> spawns = event.getSpawns().getSpawner(EntityClassification.MONSTER);
            if (types.contains(t)) {
                spawns.add(new MobSpawnInfo.Spawners(INFECTED_PIG_ENTITY, Config.COMMON.InfectedMobWeight.get(), 3, 6));
                spawns.add(new MobSpawnInfo.Spawners(INFECTED_COW_ENTITY, Config.COMMON.InfectedMobWeight.get(), 2, 4));
                spawns.add(new MobSpawnInfo.Spawners(INFECTED_CHICKEN_ENTITY, Config.COMMON.InfectedMobWeight.get(), 4, 8));
                spawns.add(new MobSpawnInfo.Spawners(INFECTED_SHEEP_ENTITY, Config.COMMON.InfectedMobWeight.get(), 3, 6));
            }
        }

        for (BiomeDictionary.Type t : InfectedWolfTypes) {
            List<MobSpawnInfo.Spawners> spawns = event.getSpawns().getSpawner(EntityClassification.MONSTER);
            if (types.contains(t)) {
                spawns.add(new MobSpawnInfo.Spawners(INFECTED_WOLF_ENTITY, Config.COMMON.InfectedMobWeight.get(), 4, 4));
            }
        }

        for (BiomeDictionary.Type t : MountainTypes) {
            List<MobSpawnInfo.Spawners> spawns = event.getSpawns().getSpawner(EntityClassification.MONSTER);
            if (types.contains(t)) {
                spawns.add(new MobSpawnInfo.Spawners(INFECTED_LLAMA_ENTITY, Config.COMMON.InfectedMobWeight.get(), 4, 6));
            }
        }

        for (BiomeDictionary.Type t : SavannaTypes) {
            List<MobSpawnInfo.Spawners> spawns = event.getSpawns().getSpawner(EntityClassification.MONSTER);
            if (types.contains(t)) {
                spawns.add(new MobSpawnInfo.Spawners(INFECTED_LLAMA_ENTITY, Config.COMMON.InfectedMobWeight.get(), 4, 4));
                spawns.add(new MobSpawnInfo.Spawners(INFECTED_DONKEY_ENTITY, Config.COMMON.InfectedMobWeight.get(), 1, 1));
            }
        }

        for (BiomeDictionary.Type t : PlainsTypes) {
            List<MobSpawnInfo.Spawners> spawns = event.getSpawns().getSpawner(EntityClassification.MONSTER);
            if (types.contains(t)) {
                spawns.add(new MobSpawnInfo.Spawners(INFECTED_DONKEY_ENTITY, Config.COMMON.InfectedMobWeight.get(), 1, 3));
            }
        }

        for (BiomeDictionary.Type t : InfectedHorseTypes) {
            List<MobSpawnInfo.Spawners> spawns = event.getSpawns().getSpawner(EntityClassification.MONSTER);
            if (types.contains(t)) {
                spawns.add(new MobSpawnInfo.Spawners(INFECTED_HORSE_ENTITY, Config.COMMON.InfectedMobWeight.get(), 2, 6));
            }
        }

        for (BiomeDictionary.Type t : InfectedVillagerTypes) {
            List<MobSpawnInfo.Spawners> spawns = event.getSpawns().getSpawner(EntityClassification.MONSTER);
            if (types.contains(t)) {
                spawns.add(new MobSpawnInfo.Spawners(INFECTED_VILLAGER_ENTITY, Config.COMMON.InfectedMobWeight.get(), 1, 4));
            }
        }

        for (BiomeDictionary.Type t : InfectedRabbitTypes) {
            List<MobSpawnInfo.Spawners> spawns = event.getSpawns().getSpawner(EntityClassification.MONSTER);
            if (types.contains(t)) {
                spawns.add(new MobSpawnInfo.Spawners(INFECTED_RABBIT_ENTITY, Config.COMMON.InfectedMobWeight.get(), 2, 3));
            }
        }
    }
}