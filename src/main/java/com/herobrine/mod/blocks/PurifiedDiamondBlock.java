package com.herobrine.mod.blocks;

import com.herobrine.mod.util.blocks.BlockMaterialList;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraftforge.common.ToolType;

public class PurifiedDiamondBlock extends Block {
    public PurifiedDiamondBlock() {
        super(Properties.of(BlockMaterialList.PURIFIED_DIAMOND_BLOCK_MATERIAL).strength(1.5F).sound(SoundType.METAL).harvestTool(ToolType.PICKAXE).harvestLevel(1));
    }
}
