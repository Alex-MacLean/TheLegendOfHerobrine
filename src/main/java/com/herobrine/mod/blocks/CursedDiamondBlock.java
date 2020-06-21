package com.herobrine.mod.blocks;

import com.herobrine.mod.HerobrineMod;
import com.herobrine.mod.util.blocks.BlockMaterialList;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.registries.ObjectHolder;
import org.jetbrains.annotations.NotNull;

import java.util.Random;

public class CursedDiamondBlock extends Block {
    @ObjectHolder(HerobrineMod.MODID + ":cursed_diamond_block")
    public static final Block block = null;

    public CursedDiamondBlock() {
        super(Properties.create(BlockMaterialList.CURSED_DIAMOND_BLOCK_MATERIAL).hardnessAndResistance(1.5F).sound(SoundType.METAL).harvestTool(ToolType.PICKAXE).harvestLevel(0));
        setRegistryName("cursed_diamond_block");
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void animateTick(@NotNull BlockState stateIn, @NotNull World worldIn, @NotNull BlockPos pos, @NotNull Random rand) {
        spawnParticles(worldIn, pos);
    }

    private static void spawnParticles(@NotNull World world, BlockPos pos) {
        Random random = world.rand;
        for(Direction direction : Direction.values()) {
            BlockPos blockpos = pos.offset(direction);
            if (!world.getBlockState(blockpos).isOpaqueCube(world, blockpos)) {
                Direction.Axis direction$axis = direction.getAxis();
                double d1 = direction$axis == Direction.Axis.X ? 0.5D + 0.5625D * (double)direction.getXOffset() : (double)random.nextFloat();
                double d2 = 0.5D * (double) random.nextFloat();
                double d3 = direction$axis == Direction.Axis.Z ? 0.5D + 0.5625D * (double)direction.getZOffset() : (double)random.nextFloat();
                world.addParticle(ParticleTypes.PORTAL, (double) pos.getX() + d1, (double) pos.getY() + d2, (double) pos.getZ() + d3, 0.0D, 0.0D, 0.0D);
            }
        }
    }
}
