package com.github.alexmaclean.herobrine;

import com.github.alexmaclean.herobrine.blocks.BlockList;
import com.github.alexmaclean.herobrine.entities.HerobrineWarriorEntity;
import com.github.alexmaclean.herobrine.items.ItemList;
import com.github.alexmaclean.herobrine.savedata.WorldSaveData;
import com.github.alexmaclean.herobrine.util.entities.EntityTypeList;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class HerobrineMod implements ModInitializer {
    // Public String reference for mod id
    public static final String MODID = "herobrine";

    @Override
    public void onInitialize() {
        registerCallbacks();
        registerBlocks();
        registerItems();
        registerEntities();
    }

    // Register blocks
    private void registerBlocks() {
        Registry.register(Registry.BLOCK, new Identifier(MODID, "herobrine_altar"), BlockList.HEROBRINE_ALTAR_BLOCK);
        Registry.register(Registry.BLOCK, new Identifier(MODID, "cursed_diamond_block"), BlockList.CURSED_DIAMOND_BLOCK);
        Registry.register(Registry.BLOCK, new Identifier(MODID, "purified_diamond_block"), BlockList.PURIFIED_DIAMOND_BLOCK);
        Registry.register(Registry.BLOCK, new Identifier(MODID, "herobrine_statue"), BlockList.HEROBRINE_STATUE_BLOCK);
    }

    // Register items
    private void registerItems() {
        Registry.register(Registry.ITEM, new Identifier(MODID, "herobrine_statue"), ItemList.HEROBRINE_STATUE);
        Registry.register(Registry.ITEM, new Identifier(MODID, "herobrine_altar"), ItemList.HEROBRINE_ALTAR);
        Registry.register(Registry.ITEM, new Identifier(MODID, "cursed_diamond_block"), ItemList.CURSED_DIAMOND_BLOCK);
        Registry.register(Registry.ITEM, new Identifier(MODID, "purified_diamond_block"), ItemList.PURIFIED_DIAMOND_BLOCK);
        Registry.register(Registry.ITEM, new Identifier(MODID, "bedrock_sword"), ItemList.BEDROCK_SWORD);
        Registry.register(Registry.ITEM, new Identifier(MODID, "cursed_diamond_sword"), ItemList.CURSED_DIAMOND_SWORD);
        Registry.register(Registry.ITEM, new Identifier(MODID, "cursed_diamond_shovel"), ItemList.CURSED_DIAMOND_SHOVEL);
        Registry.register(Registry.ITEM, new Identifier(MODID, "cursed_diamond_pickaxe"), ItemList.CURSED_DIAMOND_PICKAXE);
        Registry.register(Registry.ITEM, new Identifier(MODID, "cursed_diamond_axe"), ItemList.CURSED_DIAMOND_AXE);
        Registry.register(Registry.ITEM, new Identifier(MODID, "cursed_diamond_hoe"), ItemList.CURSED_DIAMOND_HOE);
        Registry.register(Registry.ITEM, new Identifier(MODID, "cursed_diamond_helmet"), ItemList.CURSED_DIAMOND_HELMET);
        Registry.register(Registry.ITEM, new Identifier(MODID, "cursed_diamond_chestplate"), ItemList.CURSED_DIAMOND_CHESTPLATE);
        Registry.register(Registry.ITEM, new Identifier(MODID, "cursed_diamond_leggings"), ItemList.CURSED_DIAMOND_LEGGINGS);
        Registry.register(Registry.ITEM, new Identifier(MODID, "cursed_diamond_boots"), ItemList.CURSED_DIAMOND_BOOTS);
        Registry.register(Registry.ITEM, new Identifier(MODID, "holy_water"), ItemList.HOLY_WATER);
        Registry.register(Registry.ITEM, new Identifier(MODID, "unholy_water"), ItemList.UNHOLY_WATER);
        Registry.register(Registry.ITEM, new Identifier(MODID, "cursed_dust"), ItemList.CURSED_DUST);
        Registry.register(Registry.ITEM, new Identifier(MODID, "cursed_diamond"), ItemList.CURSED_DIAMOND);
        Registry.register(Registry.ITEM, new Identifier(MODID, "purified_diamond"), ItemList.PURIFIED_DIAMOND);
        Registry.register(Registry.ITEM, new Identifier(MODID, "music_disc_dog"), ItemList.MUSIC_DISC_DOG);
        Registry.register(Registry.ITEM, new Identifier(MODID, "herobrine_warrior_spawn_egg"), ItemList.HEROBRINE_WARRIOR_SPAWN_EGG);
    }

    // Register entities
    private void registerEntities() {
        FabricDefaultAttributeRegistry.register(EntityTypeList.HEROBRINE_WARRIOR, HerobrineWarriorEntity.registerAttributes());
    }

    private void registerCallbacks() {
        ServerLifecycleEvents.SERVER_STARTED.register(WorldSaveData::handleServerStart);
    }
}