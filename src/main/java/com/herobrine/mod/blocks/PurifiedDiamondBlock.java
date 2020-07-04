package com.herobrine.mod.blocks;

import com.herobrine.mod.HerobrineMod;
import com.herobrine.mod.util.blocks.ModMaterial;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class PurifiedDiamondBlock extends Block{
    @GameRegistry.ObjectHolder(HerobrineMod.MODID + ":purified_diamond_block")
    public static final Block block = new Block(ModMaterial.PURIFIED_DIAMOND_BLOCK_MATERIAL);

    public PurifiedDiamondBlock() {
        super(ModMaterial.PURIFIED_DIAMOND_BLOCK_MATERIAL, MapColor.LIGHT_BLUE);
        this.setCreativeTab(CreativeTabs.DECORATIONS);
        this.setRegistryName(HerobrineMod.location("purified_diamond_block"));
        this.setTranslationKey(HerobrineMod.MODID + "." + "purified_diamond_block");
        this.setHardness(1.5f);
        this.setResistance(1.5f);
        this.setSoundType(SoundType.METAL);
        this.setHarvestLevel("pickaxe", 1);
    }
}
