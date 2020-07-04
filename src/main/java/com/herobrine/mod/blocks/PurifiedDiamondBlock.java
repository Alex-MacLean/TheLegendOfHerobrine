package com.herobrine.mod.blocks;

import com.herobrine.mod.HerobrineMod;
import com.herobrine.mod.util.blocks.BlockMaterialList;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.registries.ObjectHolder;

public class PurifiedDiamondBlock extends Block {
    @ObjectHolder(HerobrineMod.MODID + ":purified_diamond_block")
    public static final Block block = null;

    public PurifiedDiamondBlock() {
        super(Properties.create(BlockMaterialList.PURIFIED_DIAMOND_BLOCK_MATERIAL).hardnessAndResistance(1.5F).sound(SoundType.METAL).harvestTool(ToolType.PICKAXE).harvestLevel(1));
        setRegistryName("purified_diamond_block");
    }
}
