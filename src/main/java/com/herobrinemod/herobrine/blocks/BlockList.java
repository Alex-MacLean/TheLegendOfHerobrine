package com.herobrinemod.herobrine.blocks;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.MapColor;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.sound.BlockSoundGroup;

public class BlockList {
    // Initialize blocks
    public static final Block PURIFIED_DIAMOND_BLOCK = new Block(FabricBlockSettings.create().strength(1.5f).mapColor(MapColor.DIAMOND_BLUE).pistonBehavior(PistonBehavior.NORMAL).requiresTool().sounds(BlockSoundGroup.METAL));
    public static final Block CURSED_DIAMOND_BLOCK = new CursedDiamondBlock(FabricBlockSettings.create().mapColor(MapColor.PURPLE).pistonBehavior(PistonBehavior.NORMAL).strength(1.5f).requiresTool().sounds(BlockSoundGroup.METAL));
    public static final Block HEROBRINE_ALTAR_BLOCK = new HerobrineAltarBlock(FabricBlockSettings.create().mapColor(MapColor.RED).pistonBehavior(PistonBehavior.NORMAL).strength(1.5f).requiresTool().sounds(BlockSoundGroup.METAL).nonOpaque().allowsSpawning((state, world, pos, type) -> false).lightLevel(HerobrineAltarBlock::getLightValue));
    public static final Block HEROBRINE_STATUE_BLOCK = new HerobrineStatueBlock(FabricBlockSettings.create().mapColor(MapColor.STONE_GRAY).pistonBehavior(PistonBehavior.NORMAL).strength(1.5f).requiresTool().sounds(BlockSoundGroup.STONE).nonOpaque().allowsSpawning((state, world, pos, type) -> false));
}