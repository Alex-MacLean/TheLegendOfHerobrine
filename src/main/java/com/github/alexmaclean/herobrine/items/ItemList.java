package com.github.alexmaclean.herobrine.items;

import com.github.alexmaclean.herobrine.HerobrineMod;
import com.github.alexmaclean.herobrine.blocks.BlockList;
import com.github.alexmaclean.herobrine.items.material.ArmorMaterialList;
import com.github.alexmaclean.herobrine.items.material.ToolMaterialList;
import com.github.alexmaclean.herobrine.util.entities.EntityTypeList;
import com.github.alexmaclean.herobrine.util.items.ModdedAxeItem;
import com.github.alexmaclean.herobrine.util.items.ModdedHoeItem;
import com.github.alexmaclean.herobrine.util.items.ModdedMusicDiscItem;
import com.github.alexmaclean.herobrine.util.items.ModdedPickaxeItem;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.*;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;

public class ItemList {
    // Initialize items
    public static final Item BEDROCK_SWORD = new SwordItem(ToolMaterialList.BEDROCK_TOOL_MATERIAL, 0, -2.4f, new Item.Settings().group(ItemGroup.COMBAT).rarity(Rarity.EPIC));
    public static final Item CURSED_DIAMOND = new Item(new Item.Settings().group(ItemGroup.MISC));
    public static final Item CURSED_DUST = new Item(new Item.Settings().group(ItemGroup.MISC));
    public static final Item PURIFIED_DIAMOND = new Item(new Item.Settings().group(ItemGroup.MISC));
    public static final Item CURSED_DIAMOND_HELMET = new ArmorItem(ArmorMaterialList.CURSED_DIAMOND_ARMOR_MATERIAL, EquipmentSlot.HEAD, new Item.Settings().group(ItemGroup.COMBAT));
    public static final Item CURSED_DIAMOND_CHESTPLATE = new ArmorItem(ArmorMaterialList.CURSED_DIAMOND_ARMOR_MATERIAL, EquipmentSlot.CHEST, new Item.Settings().group(ItemGroup.COMBAT));
    public static final Item CURSED_DIAMOND_LEGGINGS = new ArmorItem(ArmorMaterialList.CURSED_DIAMOND_ARMOR_MATERIAL, EquipmentSlot.LEGS, new Item.Settings().group(ItemGroup.COMBAT));
    public static final Item CURSED_DIAMOND_BOOTS = new ArmorItem(ArmorMaterialList.CURSED_DIAMOND_ARMOR_MATERIAL, EquipmentSlot.FEET, new Item.Settings().group(ItemGroup.COMBAT));
    public static final Item CURSED_DIAMOND_SWORD = new SwordItem(ToolMaterialList.CURSED_DIAMOND_TOOL_MATERIAL, 3, -2.4f, new Item.Settings().group(ItemGroup.COMBAT));
    public static final Item CURSED_DIAMOND_AXE = new ModdedAxeItem(ToolMaterialList.CURSED_DIAMOND_TOOL_MATERIAL, 5, -3.0f, new Item.Settings().group(ItemGroup.TOOLS));
    public static final Item CURSED_DIAMOND_PICKAXE = new ModdedPickaxeItem(ToolMaterialList.CURSED_DIAMOND_TOOL_MATERIAL, 1, -2.8f, new Item.Settings().group(ItemGroup.TOOLS));
    public static final Item CURSED_DIAMOND_SHOVEL = new ShovelItem(ToolMaterialList.CURSED_DIAMOND_TOOL_MATERIAL, 1.5f, -3.0f, new Item.Settings().group(ItemGroup.TOOLS));
    public static final Item CURSED_DIAMOND_HOE = new ModdedHoeItem(ToolMaterialList.CURSED_DIAMOND_TOOL_MATERIAL, -4, 0.0f, new Item.Settings().group(ItemGroup.TOOLS));
    public static final Item MUSIC_DISC_DOG = new ModdedMusicDiscItem(14, new SoundEvent(new Identifier(HerobrineMod.MODID, "music_disc_dog")), new Item.Settings().group(ItemGroup.MISC).rarity(Rarity.RARE).maxCount(1));
    public static final Item HOLY_WATER = new HolyWaterItem(new Item.Settings().group(ItemGroup.MISC));
    public static final Item UNHOLY_WATER = new UnholyWaterItem(new Item.Settings().group(ItemGroup.MISC));
    public static final Item CURSED_DIAMOND_BLOCK = new BlockItem(BlockList.CURSED_DIAMOND_BLOCK, new FabricItemSettings().group(ItemGroup.DECORATIONS));
    public static final Item PURIFIED_DIAMOND_BLOCK = new BlockItem(BlockList.PURIFIED_DIAMOND_BLOCK, new FabricItemSettings().group(ItemGroup.DECORATIONS));
    public static final Item HEROBRINE_ALTAR = new BlockItem(BlockList.HEROBRINE_ALTAR_BLOCK, new FabricItemSettings().group(ItemGroup.DECORATIONS).rarity(Rarity.UNCOMMON));
    public static final Item HEROBRINE_STATUE = new BlockItem(BlockList.HEROBRINE_STATUE_BLOCK, new FabricItemSettings().group(ItemGroup.DECORATIONS));
    public static final SpawnEggItem HEROBRINE_WARRIOR_SPAWN_EGG = new SpawnEggItem(EntityTypeList.HEROBRINE_WARRIOR, 0x000000, 0xFF0000, new Item.Settings().group(ItemGroup.MISC));
}
