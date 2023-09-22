package com.herobrinemod.herobrine;

import com.herobrinemod.herobrine.blocks.BlockList;
import com.herobrinemod.herobrine.entities.*;
import com.herobrinemod.herobrine.items.ItemList;
import com.herobrinemod.herobrine.savedata.ConfigHandler;
import com.herobrinemod.herobrine.savedata.SaveDataHandler;
import com.herobrinemod.herobrine.sounds.SoundList;
import com.herobrinemod.herobrine.worldgen.BiomeKeyList;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.SpawnRestriction;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.Items;
import net.minecraft.registry.Registry;
import net.minecraft.registry.tag.BiomeTags;
import net.minecraft.util.Identifier;
import net.minecraft.world.Heightmap;
import net.minecraft.world.biome.BiomeKeys;

import static net.minecraft.registry.Registries.*;

public class HerobrineMod implements ModInitializer {
    // Public String reference for mod id
    public static final String MODID = "herobrine";

    @Override // "Main" method
    public void onInitialize() {
        ConfigHandler.registerHerobrineConfig("herobrine.json");
        registerCallbacks();
        registerSounds();
        registerBlocks();
        registerItems();
        registerEntityAttributes();
        registerItemGroups();
        registerEntitySpawns();
        registerSurvivorSkins();
    }

    // Register sounds
    private void registerSounds() {
        Registry.register(SOUND_EVENT, SoundList.MUSIC_DISC_DOG_IDENTIFIER, SoundList.MUSIC_DISC_DOG);
    }

    // Register blocks
    private void registerBlocks() {
        Registry.register(BLOCK, new Identifier(MODID, "herobrine_altar"), BlockList.HEROBRINE_ALTAR_BLOCK);
        Registry.register(BLOCK, new Identifier(MODID, "cursed_diamond_block"), BlockList.CURSED_DIAMOND_BLOCK);
        Registry.register(BLOCK, new Identifier(MODID, "purified_diamond_block"), BlockList.PURIFIED_DIAMOND_BLOCK);
        Registry.register(BLOCK, new Identifier(MODID, "herobrine_statue"), BlockList.HEROBRINE_STATUE_BLOCK);
    }

    // Register items
    private void registerItems() {
        Registry.register(ITEM, new Identifier(MODID, "herobrine_statue"), ItemList.HEROBRINE_STATUE);
        Registry.register(ITEM, new Identifier(MODID, "herobrine_altar"), ItemList.HEROBRINE_ALTAR);
        Registry.register(ITEM, new Identifier(MODID, "cursed_diamond_block"), ItemList.CURSED_DIAMOND_BLOCK);
        Registry.register(ITEM, new Identifier(MODID, "purified_diamond_block"), ItemList.PURIFIED_DIAMOND_BLOCK);
        Registry.register(ITEM, new Identifier(MODID, "bedrock_sword"), ItemList.BEDROCK_SWORD);
        Registry.register(ITEM, new Identifier(MODID, "cursed_diamond_sword"), ItemList.CURSED_DIAMOND_SWORD);
        Registry.register(ITEM, new Identifier(MODID, "cursed_diamond_shovel"), ItemList.CURSED_DIAMOND_SHOVEL);
        Registry.register(ITEM, new Identifier(MODID, "cursed_diamond_pickaxe"), ItemList.CURSED_DIAMOND_PICKAXE);
        Registry.register(ITEM, new Identifier(MODID, "cursed_diamond_axe"), ItemList.CURSED_DIAMOND_AXE);
        Registry.register(ITEM, new Identifier(MODID, "cursed_diamond_hoe"), ItemList.CURSED_DIAMOND_HOE);
        Registry.register(ITEM, new Identifier(MODID, "cursed_diamond_helmet"), ItemList.CURSED_DIAMOND_HELMET);
        Registry.register(ITEM, new Identifier(MODID, "cursed_diamond_chestplate"), ItemList.CURSED_DIAMOND_CHESTPLATE);
        Registry.register(ITEM, new Identifier(MODID, "cursed_diamond_leggings"), ItemList.CURSED_DIAMOND_LEGGINGS);
        Registry.register(ITEM, new Identifier(MODID, "cursed_diamond_boots"), ItemList.CURSED_DIAMOND_BOOTS);
        Registry.register(ITEM, new Identifier(MODID, "holy_water"), ItemList.HOLY_WATER);
        Registry.register(ITEM, new Identifier(MODID, "unholy_water"), ItemList.UNHOLY_WATER);
        Registry.register(ITEM, new Identifier(MODID, "cursed_dust"), ItemList.CURSED_DUST);
        Registry.register(ITEM, new Identifier(MODID, "cursed_diamond"), ItemList.CURSED_DIAMOND);
        Registry.register(ITEM, new Identifier(MODID, "purified_diamond"), ItemList.PURIFIED_DIAMOND);
        Registry.register(ITEM, new Identifier(MODID, "music_disc_dog"), ItemList.MUSIC_DISC_DOG);
        Registry.register(ITEM, new Identifier(MODID, "herobrine_warrior_spawn_egg"), ItemList.HEROBRINE_WARRIOR_SPAWN_EGG);
        Registry.register(ITEM, new Identifier(MODID, "herobrine_spy_spawn_egg"), ItemList.HEROBRINE_SPY_SPAWN_EGG);
        Registry.register(ITEM, new Identifier(MODID, "herobrine_mage_spawn_egg"), ItemList.HEROBRINE_MAGE_SPAWN_EGG);
        Registry.register(ITEM, new Identifier(MODID, "herobrine_builder_spawn_egg"), ItemList.HEROBRINE_BUILDER_SPAWN_EGG);
        Registry.register(ITEM, new Identifier(MODID, "herobrine_stalker_spawn_egg"), ItemList.HEROBRINE_STALKER_SPAWN_EGG);
        Registry.register(ITEM, new Identifier(MODID, "infected_pig_spawn_egg"), ItemList.INFECTED_PIG_SPAWN_EGG);
        Registry.register(ITEM, new Identifier(MODID, "infected_cow_spawn_egg"), ItemList.INFECTED_COW_SPAWN_EGG);
        Registry.register(ITEM, new Identifier(MODID, "infected_villager_spawn_egg"), ItemList.INFECTED_VILLAGER_SPAWN_EGG);
        Registry.register(ITEM, new Identifier(MODID, "infected_chicken_spawn_egg"), ItemList.INFECTED_CHICKEN_SPAWN_EGG);
        Registry.register(ITEM, new Identifier(MODID, "infected_sheep_spawn_egg"), ItemList.INFECTED_SHEEP_SPAWN_EGG);
        Registry.register(ITEM, new Identifier(MODID, "infected_bat_spawn_egg"), ItemList.INFECTED_BAT_SPAWN_EGG);
        Registry.register(ITEM, new Identifier(MODID, "infected_wolf_spawn_egg"), ItemList.INFECTED_WOLF_SPAWN_EGG);
        Registry.register(ITEM, new Identifier(MODID, "infected_mooshroom_spawn_egg"), ItemList.INFECTED_MOOSHROOM_SPAWN_EGG);
        Registry.register(ITEM, new Identifier(MODID, "infected_donkey_spawn_egg"), ItemList.INFECTED_DONKEY_SPAWN_EGG);
        Registry.register(ITEM, new Identifier(MODID, "infected_horse_spawn_egg"), ItemList.INFECTED_HORSE_SPAWN_EGG);
        Registry.register(ITEM, new Identifier(MODID, "infected_llama_spawn_egg"), ItemList.INFECTED_LLAMA_SPAWN_EGG);
        Registry.register(ITEM, new Identifier(MODID, "infected_rabbit_spawn_egg"), ItemList.INFECTED_RABBIT_SPAWN_EGG);
        Registry.register(ITEM, new Identifier(MODID, "survivor_spawn_egg"), ItemList.SURVIVOR_SPAWN_EGG);
    }

    // Register entity attributes
    private void registerEntityAttributes() {
        FabricDefaultAttributeRegistry.register(EntityTypeList.HEROBRINE_WARRIOR, HerobrineWarriorEntity.registerAttributes());
        FabricDefaultAttributeRegistry.register(EntityTypeList.HEROBRINE_SPY, HerobrineSpyEntity.registerAttributes());
        FabricDefaultAttributeRegistry.register(EntityTypeList.HEROBRINE_MAGE, HerobrineMageEntity.registerAttributes());
        FabricDefaultAttributeRegistry.register(EntityTypeList.FAKE_HEROBRINE_MAGE, FakeHerobrineMageEntity.registerAttributes());
        FabricDefaultAttributeRegistry.register(EntityTypeList.HEROBRINE_BUILDER, HerobrineBuilderEntity.registerAttributes());
        FabricDefaultAttributeRegistry.register(EntityTypeList.HEROBRINE_STALKER, HerobrineStalkerEntity.registerAttributes());
        FabricDefaultAttributeRegistry.register(EntityTypeList.INFECTED_PIG, InfectedPigEntity.registerAttributes());
        FabricDefaultAttributeRegistry.register(EntityTypeList.INFECTED_COW, InfectedCowEntity.registerAttributes());
        FabricDefaultAttributeRegistry.register(EntityTypeList.INFECTED_VILLAGER, InfectedVillagerEntity.registerAttributes());
        FabricDefaultAttributeRegistry.register(EntityTypeList.INFECTED_CHICKEN, InfectedChickenEntity.registerAttributes());
        FabricDefaultAttributeRegistry.register(EntityTypeList.INFECTED_SHEEP, InfectedSheepEntity.registerAttributes());
        FabricDefaultAttributeRegistry.register(EntityTypeList.INFECTED_BAT, InfectedBatEntity.registerAttributes());
        FabricDefaultAttributeRegistry.register(EntityTypeList.INFECTED_WOLF, InfectedWolfEntity.registerAttributes());
        FabricDefaultAttributeRegistry.register(EntityTypeList.INFECTED_MOOSHROOM, InfectedMooshroomEntity.registerAttributes());
        FabricDefaultAttributeRegistry.register(EntityTypeList.INFECTED_DONKEY, InfectedDonkeyEntity.registerAttributes());
        FabricDefaultAttributeRegistry.register(EntityTypeList.INFECTED_HORSE, InfectedHorseEntity.registerAttributes());
        FabricDefaultAttributeRegistry.register(EntityTypeList.INFECTED_LLAMA, InfectedLlamaEntity.registerAttributes());
        FabricDefaultAttributeRegistry.register(EntityTypeList.INFECTED_RABBIT, InfectedRabbitEntity.registerAttributes());
        FabricDefaultAttributeRegistry.register(EntityTypeList.SURVIVOR, SurvivorEntity.registerAttributes());
    }

    // Register creative tabs and add items to them
    private void registerItemGroups() {
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register(content -> content.addAfter(Items.DIAMOND, ItemList.CURSED_DIAMOND));
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register(content -> content.addAfter(Items.BLAZE_POWDER, ItemList.CURSED_DUST));
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register(content -> content.addAfter(ItemList.CURSED_DIAMOND, ItemList.PURIFIED_DIAMOND));
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.COMBAT).register(content -> content.addAfter(Items.NETHERITE_BOOTS, ItemList.CURSED_DIAMOND_HELMET));
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.COMBAT).register(content -> content.addAfter(ItemList.CURSED_DIAMOND_HELMET, ItemList.CURSED_DIAMOND_CHESTPLATE));
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.COMBAT).register(content -> content.addAfter(ItemList.CURSED_DIAMOND_CHESTPLATE, ItemList.CURSED_DIAMOND_LEGGINGS));
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.COMBAT).register(content -> content.addAfter(ItemList.CURSED_DIAMOND_LEGGINGS, ItemList.CURSED_DIAMOND_BOOTS));
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.COMBAT).register(content -> content.addAfter(Items.NETHERITE_SWORD, ItemList.CURSED_DIAMOND_SWORD));
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.COMBAT).register(content -> content.addAfter(ItemList.CURSED_DIAMOND_SWORD, ItemList.BEDROCK_SWORD));
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.TOOLS).register(content -> content.addAfter(Items.NETHERITE_HOE, ItemList.CURSED_DIAMOND_SHOVEL));
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.COMBAT).register(content -> content.addAfter(Items.NETHERITE_AXE, ItemList.CURSED_DIAMOND_AXE));
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.TOOLS).register(content -> content.addAfter(ItemList.CURSED_DIAMOND_SHOVEL, ItemList.CURSED_DIAMOND_PICKAXE));
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.TOOLS).register(content -> content.addAfter(ItemList.CURSED_DIAMOND_PICKAXE, ItemList.CURSED_DIAMOND_AXE));
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.TOOLS).register(content -> content.addAfter(ItemList.CURSED_DIAMOND_AXE, ItemList.CURSED_DIAMOND_HOE));
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.TOOLS).register(content -> content.addAfter(Items.MUSIC_DISC_CAT, ItemList.MUSIC_DISC_DOG));
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.COMBAT).register(content -> content.addAfter(Items.EGG, ItemList.HOLY_WATER));
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.COMBAT).register(content -> content.addAfter(ItemList.HOLY_WATER, ItemList.UNHOLY_WATER));
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.BUILDING_BLOCKS).register(content -> content.addAfter(Items.DIAMOND_BLOCK, ItemList.CURSED_DIAMOND_BLOCK));
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.BUILDING_BLOCKS).register(content -> content.addAfter(ItemList.CURSED_DIAMOND_BLOCK, ItemList.PURIFIED_DIAMOND_BLOCK));
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.FUNCTIONAL).register(content -> content.addAfter(Items.ENCHANTING_TABLE, ItemList.HEROBRINE_ALTAR));
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.BUILDING_BLOCKS).register(content -> content.addAfter(Items.CHISELED_STONE_BRICKS, ItemList.HEROBRINE_STATUE));
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.SPAWN_EGGS).register(content -> content.addAfter(Items.ZOMBIFIED_PIGLIN_SPAWN_EGG, ItemList.HEROBRINE_WARRIOR_SPAWN_EGG));
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.SPAWN_EGGS).register(content -> content.addAfter(ItemList.HEROBRINE_WARRIOR_SPAWN_EGG, ItemList.HEROBRINE_MAGE_SPAWN_EGG));
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.SPAWN_EGGS).register(content -> content.addAfter(ItemList.HEROBRINE_MAGE_SPAWN_EGG, ItemList.HEROBRINE_BUILDER_SPAWN_EGG));
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.SPAWN_EGGS).register(content -> content.addAfter(ItemList.HEROBRINE_BUILDER_SPAWN_EGG, ItemList.HEROBRINE_STALKER_SPAWN_EGG));
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.SPAWN_EGGS).register(content -> content.addAfter(ItemList.HEROBRINE_STALKER_SPAWN_EGG, ItemList.HEROBRINE_SPY_SPAWN_EGG));
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.SPAWN_EGGS).register(content -> content.addAfter(ItemList.HEROBRINE_SPY_SPAWN_EGG, ItemList.INFECTED_PIG_SPAWN_EGG));
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.SPAWN_EGGS).register(content -> content.addAfter(ItemList.INFECTED_PIG_SPAWN_EGG, ItemList.INFECTED_COW_SPAWN_EGG));
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.SPAWN_EGGS).register(content -> content.addAfter(ItemList.INFECTED_COW_SPAWN_EGG, ItemList.INFECTED_VILLAGER_SPAWN_EGG));
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.SPAWN_EGGS).register(content -> content.addAfter(ItemList.INFECTED_VILLAGER_SPAWN_EGG, ItemList.INFECTED_CHICKEN_SPAWN_EGG));
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.SPAWN_EGGS).register(content -> content.addAfter(ItemList.INFECTED_CHICKEN_SPAWN_EGG, ItemList.INFECTED_SHEEP_SPAWN_EGG));
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.SPAWN_EGGS).register(content -> content.addAfter(ItemList.INFECTED_SHEEP_SPAWN_EGG, ItemList.INFECTED_BAT_SPAWN_EGG));
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.SPAWN_EGGS).register(content -> content.addAfter(ItemList.INFECTED_BAT_SPAWN_EGG, ItemList.INFECTED_WOLF_SPAWN_EGG));
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.SPAWN_EGGS).register(content -> content.addAfter(ItemList.INFECTED_WOLF_SPAWN_EGG, ItemList.INFECTED_MOOSHROOM_SPAWN_EGG));
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.SPAWN_EGGS).register(content -> content.addAfter(ItemList.INFECTED_MOOSHROOM_SPAWN_EGG, ItemList.INFECTED_DONKEY_SPAWN_EGG));
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.SPAWN_EGGS).register(content -> content.addAfter(ItemList.INFECTED_DONKEY_SPAWN_EGG, ItemList.INFECTED_HORSE_SPAWN_EGG));
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.SPAWN_EGGS).register(content -> content.addAfter(ItemList.INFECTED_HORSE_SPAWN_EGG, ItemList.INFECTED_LLAMA_SPAWN_EGG));
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.SPAWN_EGGS).register(content -> content.addAfter(ItemList.INFECTED_LLAMA_SPAWN_EGG, ItemList.INFECTED_RABBIT_SPAWN_EGG));
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.SPAWN_EGGS).register(content -> content.addAfter(ItemList.INFECTED_RABBIT_SPAWN_EGG, ItemList.SURVIVOR_SPAWN_EGG));
    }

    // Register entity spawning
    private void registerEntitySpawns() {
        BiomeModifications.addSpawn(BiomeSelectors.excludeByKey(BiomeKeys.THE_VOID), SpawnGroup.MONSTER, EntityTypeList.HEROBRINE_WARRIOR, ConfigHandler.getHerobrineConfig().readInt("HerobrineWarriorWeight"), 1, 1);
        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(BiomeKeyList.CURSED_FOREST), SpawnGroup.MONSTER, EntityTypeList.HEROBRINE_WARRIOR, ConfigHandler.getHerobrineConfig().readInt("HerobrineWarriorWeight") * 2, 1, 1);
        BiomeModifications.addSpawn(BiomeSelectors.excludeByKey(BiomeKeys.THE_VOID), SpawnGroup.MONSTER, EntityTypeList.HEROBRINE_BUILDER, ConfigHandler.getHerobrineConfig().readInt("HerobrineBuilderWeight"), 1, 1);
        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(BiomeKeyList.CURSED_FOREST), SpawnGroup.MONSTER, EntityTypeList.HEROBRINE_BUILDER, ConfigHandler.getHerobrineConfig().readInt("HerobrineBuilderWeight") * 2, 1, 1);
        BiomeModifications.addSpawn(BiomeSelectors.excludeByKey(BiomeKeys.THE_VOID), SpawnGroup.MONSTER, EntityTypeList.HEROBRINE_MAGE, ConfigHandler.getHerobrineConfig().readInt("HerobrineMageWeight"), 1, 1);
        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(BiomeKeyList.CURSED_FOREST), SpawnGroup.MONSTER, EntityTypeList.HEROBRINE_MAGE, ConfigHandler.getHerobrineConfig().readInt("HerobrineMageWeight") * 2, 1, 1);
        BiomeModifications.addSpawn(BiomeSelectors.excludeByKey(BiomeKeys.THE_VOID), SpawnGroup.MONSTER, EntityTypeList.HEROBRINE_SPY, ConfigHandler.getHerobrineConfig().readInt("HerobrineSpyWeight"), 1, 1);
        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(BiomeKeyList.CURSED_FOREST), SpawnGroup.MONSTER, EntityTypeList.HEROBRINE_SPY, ConfigHandler.getHerobrineConfig().readInt("HerobrineSpyWeight") * 2, 1, 1);
        BiomeModifications.addSpawn(BiomeSelectors.excludeByKey(BiomeKeys.THE_VOID), SpawnGroup.MONSTER, EntityTypeList.HEROBRINE_STALKER, ConfigHandler.getHerobrineConfig().readInt("HerobrineStalkerWeight"), 1, 1);
        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(BiomeKeyList.CURSED_FOREST), SpawnGroup.MONSTER, EntityTypeList.HEROBRINE_STALKER, ConfigHandler.getHerobrineConfig().readInt("HerobrineStalkerWeight") * 2, 1, 1);
        BiomeModifications.addSpawn(BiomeSelectors.spawnsOneOf(EntityType.PIG), SpawnGroup.MONSTER, EntityTypeList.INFECTED_PIG, ConfigHandler.getHerobrineConfig().readInt("InfectedMobWeight"), 3, 6);
        BiomeModifications.addSpawn(BiomeSelectors.spawnsOneOf(EntityType.COW), SpawnGroup.MONSTER, EntityTypeList.INFECTED_COW, ConfigHandler.getHerobrineConfig().readInt("InfectedMobWeight"), 2, 4);
        BiomeModifications.addSpawn(BiomeSelectors.tag(BiomeTags.VILLAGE_DESERT_HAS_STRUCTURE).or(BiomeSelectors.tag(BiomeTags.VILLAGE_PLAINS_HAS_STRUCTURE)).or(BiomeSelectors.tag(BiomeTags.VILLAGE_SNOWY_HAS_STRUCTURE)).or(BiomeSelectors.tag(BiomeTags.VILLAGE_SAVANNA_HAS_STRUCTURE)).or(BiomeSelectors.tag(BiomeTags.VILLAGE_TAIGA_HAS_STRUCTURE)), SpawnGroup.MONSTER, EntityTypeList.INFECTED_VILLAGER, ConfigHandler.getHerobrineConfig().readInt("InfectedMobWeight"), 1, 1);
        BiomeModifications.addSpawn(BiomeSelectors.spawnsOneOf(EntityType.CHICKEN), SpawnGroup.MONSTER, EntityTypeList.INFECTED_CHICKEN, ConfigHandler.getHerobrineConfig().readInt("InfectedMobWeight"), 4, 8);
        BiomeModifications.addSpawn(BiomeSelectors.spawnsOneOf(EntityType.SHEEP), SpawnGroup.MONSTER, EntityTypeList.INFECTED_SHEEP, ConfigHandler.getHerobrineConfig().readInt("InfectedMobWeight"), 3, 6);
        BiomeModifications.addSpawn(BiomeSelectors.spawnsOneOf(EntityType.BAT), SpawnGroup.MONSTER, EntityTypeList.INFECTED_BAT, ConfigHandler.getHerobrineConfig().readInt("InfectedMobWeight") * 3, 1, 1);
        BiomeModifications.addSpawn(BiomeSelectors.spawnsOneOf(EntityType.WOLF), SpawnGroup.MONSTER, EntityTypeList.INFECTED_WOLF, ConfigHandler.getHerobrineConfig().readInt("InfectedMobWeight"), 4, 4);
        BiomeModifications.addSpawn(BiomeSelectors.spawnsOneOf(EntityType.MOOSHROOM), SpawnGroup.MONSTER, EntityTypeList.INFECTED_MOOSHROOM, ConfigHandler.getHerobrineConfig().readInt("InfectedMobWeight"), 4, 8);
        BiomeModifications.addSpawn(BiomeSelectors.spawnsOneOf(EntityType.DONKEY), SpawnGroup.MONSTER, EntityTypeList.INFECTED_DONKEY, ConfigHandler.getHerobrineConfig().readInt("InfectedMobWeight"), 1, 3);
        BiomeModifications.addSpawn(BiomeSelectors.spawnsOneOf(EntityType.HORSE), SpawnGroup.MONSTER, EntityTypeList.INFECTED_HORSE, ConfigHandler.getHerobrineConfig().readInt("InfectedMobWeight"), 2, 6);
        BiomeModifications.addSpawn(BiomeSelectors.spawnsOneOf(EntityType.LLAMA), SpawnGroup.MONSTER, EntityTypeList.INFECTED_LLAMA, ConfigHandler.getHerobrineConfig().readInt("InfectedMobWeight"), 4, 6);
        BiomeModifications.addSpawn(BiomeSelectors.spawnsOneOf(EntityType.RABBIT), SpawnGroup.MONSTER, EntityTypeList.INFECTED_RABBIT, ConfigHandler.getHerobrineConfig().readInt("InfectedMobWeight"), 2, 3);
        if(ConfigHandler.getHerobrineConfig().readInt("HerobrineEndSpawnType") > 0 && ConfigHandler.getHerobrineConfig().readInt("HerobrineEndSpawnType") < 3) {
            BiomeModifications.addSpawn(BiomeSelectors.foundInTheEnd(), SpawnGroup.MONSTER, EntityTypeList.HEROBRINE_SPY, ConfigHandler.getHerobrineConfig().readInt("HerobrineSpyWeight"), 1, 1);
            BiomeModifications.addSpawn(BiomeSelectors.foundInTheEnd(), SpawnGroup.MONSTER, EntityTypeList.HEROBRINE_STALKER, ConfigHandler.getHerobrineConfig().readInt("HerobrineStalkerWeight"), 1, 1);
            if(ConfigHandler.getHerobrineConfig().readInt("HerobrineEndSpawnType") == 2) {
                BiomeModifications.addSpawn(BiomeSelectors.foundInTheEnd(), SpawnGroup.MONSTER, EntityTypeList.HEROBRINE_WARRIOR, ConfigHandler.getHerobrineConfig().readInt("HerobrineWarriorWeight"), 1, 1);
                BiomeModifications.addSpawn(BiomeSelectors.foundInTheEnd(), SpawnGroup.MONSTER, EntityTypeList.HEROBRINE_MAGE, ConfigHandler.getHerobrineConfig().readInt("HerobrineMageWeight"), 1, 1);
                BiomeModifications.addSpawn(BiomeSelectors.foundInTheEnd(), SpawnGroup.MONSTER, EntityTypeList.HEROBRINE_BUILDER, ConfigHandler.getHerobrineConfig().readInt("HerobrineBuilderWeight"), 1, 1);
            }
        }
        SpawnRestriction.register(EntityTypeList.HEROBRINE_WARRIOR, SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, HerobrineEntity::canSpawnStage3);
        SpawnRestriction.register(EntityTypeList.HEROBRINE_BUILDER, SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, HerobrineEntity::canSpawnStage2);
        SpawnRestriction.register(EntityTypeList.HEROBRINE_MAGE, SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, HerobrineEntity::canSpawnStage3);
        SpawnRestriction.register(EntityTypeList.HEROBRINE_SPY, SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, HerobrineEntity::canSpawnPeacefulMode);
        SpawnRestriction.register(EntityTypeList.HEROBRINE_STALKER, SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, HerobrineEntity::canSpawnPeacefulModeStage1);
        SpawnRestriction.register(EntityTypeList.INFECTED_PIG, SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, InfectedEntity::canSpawn);
        SpawnRestriction.register(EntityTypeList.INFECTED_COW, SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, InfectedEntity::canSpawn);
        SpawnRestriction.register(EntityTypeList.INFECTED_VILLAGER, SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, InfectedVillagerEntity::canSpawn);
        SpawnRestriction.register(EntityTypeList.INFECTED_CHICKEN, SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, InfectedEntity::canSpawn);
        SpawnRestriction.register(EntityTypeList.INFECTED_SHEEP, SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, InfectedEntity::canSpawn);
        SpawnRestriction.register(EntityTypeList.INFECTED_BAT, SpawnRestriction.Location.NO_RESTRICTIONS, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, InfectedBatEntity::canBatSpawn);
        SpawnRestriction.register(EntityTypeList.INFECTED_WOLF, SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, InfectedEntity::canSpawn);
        SpawnRestriction.register(EntityTypeList.INFECTED_MOOSHROOM, SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, InfectedMooshroomEntity::canMooshroomSpawn);
        SpawnRestriction.register(EntityTypeList.INFECTED_DONKEY, SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, InfectedEntity::canSpawn);
        SpawnRestriction.register(EntityTypeList.INFECTED_HORSE, SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, InfectedEntity::canSpawn);
        SpawnRestriction.register(EntityTypeList.INFECTED_LLAMA, SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, InfectedEntity::canSpawn);
        SpawnRestriction.register(EntityTypeList.INFECTED_RABBIT, SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, InfectedEntity::canSpawn);
    }

    private void registerSurvivorSkins() {
        SurvivorSkinRegistry.addSkin(new Identifier("textures/entity/player/wide/alex.png"));
        SurvivorSkinRegistry.addSkin(new Identifier("textures/entity/player/wide/ari.png"));
        SurvivorSkinRegistry.addSkin(new Identifier("textures/entity/player/wide/efe.png"));
        SurvivorSkinRegistry.addSkin(new Identifier("textures/entity/player/wide/kai.png"));
        SurvivorSkinRegistry.addSkin(new Identifier("textures/entity/player/wide/makena.png"));
        SurvivorSkinRegistry.addSkin(new Identifier("textures/entity/player/wide/noor.png"));
        SurvivorSkinRegistry.addSkin(new Identifier("textures/entity/player/wide/steve.png"));
        SurvivorSkinRegistry.addSkin(new Identifier("textures/entity/player/wide/sunny.png"));
        SurvivorSkinRegistry.addSkin(new Identifier("textures/entity/player/wide/zuri.png"));
        SurvivorSkinRegistry.addSkin(new Identifier(MODID, "textures/entity/survivor/amaclean04.png"));
    }

    // Register callbacks. Used to properly load and unload each instance of WorldSaveData
    private void registerCallbacks() {
        ServerLifecycleEvents.SERVER_STARTED.register(SaveDataHandler::handleServerStart);
        ServerLifecycleEvents.SERVER_STOPPED.register(SaveDataHandler::handleServerStop);
    }
}