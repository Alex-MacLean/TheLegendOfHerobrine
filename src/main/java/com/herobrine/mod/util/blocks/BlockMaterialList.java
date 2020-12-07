package com.herobrine.mod.util.blocks;

import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.block.material.PushReaction;

public class BlockMaterialList {
    public static final Material HEROBRINE_ALTER_MATERIAL = new Material(MaterialColor.RED, false, false, false, false, false, false, PushReaction.NORMAL);
    public static final Material CURSED_DIAMOND_BLOCK_MATERIAL = new Material(MaterialColor.PURPLE, false, true, true, true, false, false, PushReaction.NORMAL);
    public static final Material HEROBRINE_STATUE_MATERIAL = new Material(MaterialColor.STONE, false, true, true, false, false, false, PushReaction.BLOCK);
    public static final Material PURIFIED_DIAMOND_BLOCK_MATERIAL = new Material(MaterialColor.LIGHT_BLUE, false, true, true, true, false, false, PushReaction.NORMAL);
}
