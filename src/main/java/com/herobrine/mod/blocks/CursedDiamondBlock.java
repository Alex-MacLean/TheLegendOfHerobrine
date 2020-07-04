package com.herobrine.mod.blocks;

import com.herobrine.mod.HerobrineMod;
import com.herobrine.mod.util.blocks.ModMaterial;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.jetbrains.annotations.NotNull;

import java.util.Random;

public class CursedDiamondBlock extends Block {
    @GameRegistry.ObjectHolder(HerobrineMod.MODID + ":cursed_diamond_block")
    public static final Block block = new Block(ModMaterial.CURSED_DIAMOND_BLOCK_MATERIAL);

    public CursedDiamondBlock() {
        super(ModMaterial.CURSED_DIAMOND_BLOCK_MATERIAL, MapColor.LIGHT_BLUE);
        this.setCreativeTab(CreativeTabs.DECORATIONS);
        this.setRegistryName(HerobrineMod.location("cursed_diamond_block"));
        this.setTranslationKey(HerobrineMod.MODID + "." + "cursed_diamond_block");
        this.setHardness(1.5f);
        this.setResistance(1.5f);
        this.setTickRandomly(true);
        this.setSoundType(SoundType.METAL);
        this.setHarvestLevel("pickaxe", 1);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void randomDisplayTick(@NotNull IBlockState stateIn, @NotNull World worldIn, @NotNull BlockPos pos, @NotNull Random rand) {
        spawnParticles(worldIn, pos);
    }

    private static void spawnParticles(@NotNull World world, BlockPos pos) {
        Random random = world.rand;
        for(EnumFacing direction : EnumFacing.values()) {
            BlockPos blockpos = pos.offset(direction);
            if (!world.getBlockState(blockpos).isOpaqueCube()) {
                EnumFacing.Axis direction$axis = direction.getAxis();
                double d1 = direction$axis == EnumFacing.Axis.X ? 0.5D + 0.5625D * (double)direction.getXOffset() : (double)random.nextFloat();
                double d2 = 0.5D * (double) random.nextFloat();
                double d3 = direction$axis == EnumFacing.Axis.Z ? 0.5D + 0.5625D * (double)direction.getZOffset() : (double)random.nextFloat();
                world.spawnParticle(EnumParticleTypes.PORTAL, (double) pos.getX() + d1, (double) pos.getY() + d2, (double) pos.getZ() + d3, 0.0D, 0.0D, 0.0D);
            }
        }
    }
}
