package com.github.alexmaclean.herobrine.blocks.material;

import net.minecraft.block.MapColor;
import net.minecraft.block.Material;
import net.minecraft.block.piston.PistonBehavior;

public class BlockMaterialList {
    // Initialize block materials
    public static final Material HEROBRINE_ALTAR_MATERIAL = new Material(MapColor.RED, false, false, false, false, false, false, PistonBehavior.NORMAL);
    public static final Material CURSED_DIAMOND_BLOCK_MATERIAL = new Material(MapColor.PURPLE, false, true, true, true, false, false, PistonBehavior.NORMAL);
    public static final Material HEROBRINE_STATUE_MATERIAL = new Material(MapColor.STONE_GRAY, false, true, true, false, false, false, PistonBehavior.BLOCK);
    public static final Material PURIFIED_DIAMOND_BLOCK_MATERIAL = new Material(MapColor.LIGHT_BLUE, false, true, true, true, false, false, PistonBehavior.NORMAL);
}