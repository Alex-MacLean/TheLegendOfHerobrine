package com.herobrinemod.herobrine.blocks;

import com.herobrinemod.herobrine.blocks.material.BlockMaterialList;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.sound.BlockSoundGroup;

public class BlockList {
    // Initialize blocks
    public static final Block PURIFIED_DIAMOND_BLOCK = new Block(FabricBlockSettings.of(BlockMaterialList.PURIFIED_DIAMOND_BLOCK_MATERIAL).strength(1.5f).requiresTool().sounds(BlockSoundGroup.METAL));
    public static final Block CURSED_DIAMOND_BLOCK = new CursedDiamondBlock(FabricBlockSettings.of(BlockMaterialList.CURSED_DIAMOND_BLOCK_MATERIAL).strength(1.5f).requiresTool().sounds(BlockSoundGroup.METAL));
    public static final Block HEROBRINE_ALTAR_BLOCK = new HerobrineAltarBlock(FabricBlockSettings.of(BlockMaterialList.HEROBRINE_ALTAR_MATERIAL).strength(1.5f).requiresTool().sounds(BlockSoundGroup.METAL).nonOpaque().allowsSpawning((state, world, pos, type) -> false).lightLevel(HerobrineAltarBlock::getLightValue));
    public static final Block HEROBRINE_STATUE_BLOCK = new HerobrineStatueBlock(FabricBlockSettings.of(BlockMaterialList.HEROBRINE_STATUE_MATERIAL).strength(1.5f).requiresTool().sounds(BlockSoundGroup.STONE).nonOpaque().allowsSpawning((state, world, pos, type) -> false));
}