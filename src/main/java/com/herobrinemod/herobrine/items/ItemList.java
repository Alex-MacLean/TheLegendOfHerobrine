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
    public static final Item CURSED_DIAMOND_SWORD = new SwordItem(ToolMaterialList.CURSED_DIAMOND_TOOL_MATERIAL, 3, -2.4f, new Item.Settings());
    public static final Item CURSED_DIAMOND_AXE = new AxeItem(ToolMaterialList.CURSED_DIAMOND_TOOL_MATERIAL, 5, -3.0f, new Item.Settings());
    public static final Item CURSED_DIAMOND_PICKAXE = new PickaxeItem(ToolMaterialList.CURSED_DIAMOND_TOOL_MATERIAL, 1, -2.8f, new Item.Settings());
    public static final Item CURSED_DIAMOND_SHOVEL = new ShovelItem(ToolMaterialList.CURSED_DIAMOND_TOOL_MATERIAL, 1.5f, -3.0f, new Item.Settings());
    public static final Item CURSED_DIAMOND_HOE = new HoeItem(ToolMaterialList.CURSED_DIAMOND_TOOL_MATERIAL, -4, 0.0f, new Item.Settings());
    public static final Item MUSIC_DISC_DOG = new MusicDiscItem(14, SoundList.MUSIC_DISC_DOG, new Item.Settings().rarity(Rarity.RARE).maxCount(1), 146);
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
}
