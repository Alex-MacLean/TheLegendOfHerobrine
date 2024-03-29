package com.herobrinemod.herobrine.items;

import com.herobrinemod.herobrine.blocks.BlockList;
import com.herobrinemod.herobrine.entities.EntityTypeList;
import com.herobrinemod.herobrine.items.material.ArmorMaterialList;
import com.herobrinemod.herobrine.items.material.ToolMaterialList;
import com.herobrinemod.herobrine.sounds.SoundList;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.*;
import net.minecraft.util.Rarity;

public class ItemList {
    // Initialize items
    public static final Item BEDROCK_SWORD = new SwordItem(ToolMaterialList.BEDROCK_TOOL_MATERIAL, 0, -2.4f, new Item.Settings().rarity(Rarity.EPIC));
    public static final Item CURSED_DIAMOND = new Item(new Item.Settings());
    public static final Item CURSED_DUST = new Item(new Item.Settings());
    public static final Item PURIFIED_DIAMOND = new Item(new Item.Settings());
    public static final Item CURSED_DIAMOND_HELMET = new ArmorItem(ArmorMaterialList.CURSED_DIAMOND_ARMOR_MATERIAL, ArmorItem.Type.HELMET, new Item.Settings());
    public static final Item CURSED_DIAMOND_CHESTPLATE = new ArmorItem(ArmorMaterialList.CURSED_DIAMOND_ARMOR_MATERIAL, ArmorItem.Type.CHESTPLATE, new Item.Settings());
    public static final Item CURSED_DIAMOND_LEGGINGS = new ArmorItem(ArmorMaterialList.CURSED_DIAMOND_ARMOR_MATERIAL, ArmorItem.Type.LEGGINGS, new Item.Settings());
    public static final Item CURSED_DIAMOND_BOOTS = new ArmorItem(ArmorMaterialList.CURSED_DIAMOND_ARMOR_MATERIAL, ArmorItem.Type.BOOTS, new Item.Settings());
    public static final Item CURSED_DIAMOND_SWORD = new CursedDiamondSwordItem(ToolMaterialList.CURSED_DIAMOND_TOOL_MATERIAL, 3, -2.4f, new Item.Settings());
    public static final Item CURSED_DIAMOND_AXE = new CursedDiamondAxeItem(ToolMaterialList.CURSED_DIAMOND_TOOL_MATERIAL, 5, -3.0f, new Item.Settings());
    public static final Item CURSED_DIAMOND_PICKAXE = new PickaxeItem(ToolMaterialList.CURSED_DIAMOND_TOOL_MATERIAL, 1, -2.8f, new Item.Settings());
    public static final Item CURSED_DIAMOND_SHOVEL = new ShovelItem(ToolMaterialList.CURSED_DIAMOND_TOOL_MATERIAL, 1.5f, -3.0f, new Item.Settings());
    public static final Item CURSED_DIAMOND_HOE = new CursedDiamondHoeItem(ToolMaterialList.CURSED_DIAMOND_TOOL_MATERIAL, -4, 0.0f, new Item.Settings());
    public static final Item MUSIC_DISC_DOG = new MusicDiscItem(15, SoundList.MUSIC_DISC_DOG, new Item.Settings().rarity(Rarity.RARE).maxCount(1), 145);
    public static final Item HOLY_WATER = new HolyWaterItem(new Item.Settings());
    public static final Item UNHOLY_WATER = new UnholyWaterItem(new Item.Settings());
    public static final Item CURSED_DIAMOND_BLOCK = new BlockItem(BlockList.CURSED_DIAMOND_BLOCK, new FabricItemSettings());
    public static final Item PURIFIED_DIAMOND_BLOCK = new BlockItem(BlockList.PURIFIED_DIAMOND_BLOCK, new FabricItemSettings());
    public static final Item HEROBRINE_ALTAR = new BlockItem(BlockList.HEROBRINE_ALTAR_BLOCK, new FabricItemSettings().rarity(Rarity.UNCOMMON));
    public static final Item HEROBRINE_STATUE = new BlockItem(BlockList.HEROBRINE_STATUE_BLOCK, new FabricItemSettings());
    public static final SpawnEggItem HEROBRINE_WARRIOR_SPAWN_EGG = new SpawnEggItem(EntityTypeList.HEROBRINE_WARRIOR, 0x000000, 0xFF0000, new Item.Settings());
    public static final SpawnEggItem HEROBRINE_SPY_SPAWN_EGG = new SpawnEggItem(EntityTypeList.HEROBRINE_SPY, 0x000000, 0x00FF00, new Item.Settings());
    public static final SpawnEggItem HEROBRINE_MAGE_SPAWN_EGG = new SpawnEggItem(EntityTypeList.HEROBRINE_MAGE, 0x000000, 0x0000FF, new Item.Settings());
    public static final SpawnEggItem HEROBRINE_BUILDER_SPAWN_EGG = new SpawnEggItem(EntityTypeList.HEROBRINE_BUILDER, 0x000000, 0xFFFF00, new Item.Settings());
    public static final SpawnEggItem HEROBRINE_STALKER_SPAWN_EGG = new SpawnEggItem(EntityTypeList.HEROBRINE_STALKER, 0x000000, 0xFFA500, new Item.Settings());
    public static final SpawnEggItem INFECTED_PIG_SPAWN_EGG = new SpawnEggItem(EntityTypeList.INFECTED_PIG, 0xF0A5A2, 0xFFFFFF, new Item.Settings());
    public static final SpawnEggItem INFECTED_COW_SPAWN_EGG = new SpawnEggItem(EntityTypeList.INFECTED_COW, 0x443626, 0xFFFFFF, new Item.Settings());
    public static final SpawnEggItem INFECTED_VILLAGER_SPAWN_EGG = new SpawnEggItem(EntityTypeList.INFECTED_VILLAGER, 0x563C33, 0xFFFFFF, new Item.Settings());
    public static final SpawnEggItem INFECTED_CHICKEN_SPAWN_EGG = new SpawnEggItem(EntityTypeList.INFECTED_CHICKEN, 0xA1A1A1, 0xFFFFFF, new Item.Settings());
    public static final SpawnEggItem INFECTED_SHEEP_SPAWN_EGG = new SpawnEggItem(EntityTypeList.INFECTED_SHEEP, 0xE7E7E7, 0xFFFFFF, new Item.Settings());
    public static final SpawnEggItem INFECTED_BAT_SPAWN_EGG = new SpawnEggItem(EntityTypeList.INFECTED_BAT, 0x4C3E30, 0xFFFFFF, new Item.Settings());
    public static final SpawnEggItem INFECTED_WOLF_SPAWN_EGG = new SpawnEggItem(EntityTypeList.INFECTED_WOLF, 0xD7D3D3, 0xFFFFFF, new Item.Settings());
    public static final SpawnEggItem INFECTED_MOOSHROOM_SPAWN_EGG = new SpawnEggItem(EntityTypeList.INFECTED_MOOSHROOM, 0xA00F10, 0xFFFFFF, new Item.Settings());
    public static final SpawnEggItem INFECTED_DONKEY_SPAWN_EGG = new SpawnEggItem(EntityTypeList.INFECTED_DONKEY, 0x534539, 0xFFFFFF, new Item.Settings());
    public static final SpawnEggItem INFECTED_HORSE_SPAWN_EGG = new SpawnEggItem(EntityTypeList.INFECTED_HORSE, 0xC09E7D, 0xFFFFFF, new Item.Settings());
    public static final SpawnEggItem INFECTED_LLAMA_SPAWN_EGG = new SpawnEggItem(EntityTypeList.INFECTED_LLAMA, 0xC09E7D, 0xFFFFFF, new Item.Settings());
    public static final SpawnEggItem INFECTED_RABBIT_SPAWN_EGG = new SpawnEggItem(EntityTypeList.INFECTED_RABBIT, 0x995F40, 0xFFFFFF, new Item.Settings());
    public static final SpawnEggItem SURVIVOR_SPAWN_EGG = new SpawnEggItem(EntityTypeList.SURVIVOR, 0x46B59C, 0xCEAC90, new Item.Settings());
}