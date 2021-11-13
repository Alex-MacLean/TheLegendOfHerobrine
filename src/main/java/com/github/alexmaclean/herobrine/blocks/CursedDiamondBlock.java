package com.github.alexmaclean.herobrine.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;

import java.util.Random;

public class CursedDiamondBlock extends Block {
    public CursedDiamondBlock(Settings settings) {
        super(settings);
    }

    @Override
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
        spawnParticles(world, pos);
    }

    private static void spawnParticles(@NotNull World world, BlockPos pos) {
        Random random = world.random;
        Direction[] var5 = Direction.values();

        for (Direction direction : var5) {
            BlockPos blockPos = pos.offset(direction);
            if (!world.getBlockState(blockPos).isOpaqueFullCube(world, blockPos)) {
                double d0 = pos.getX() + 0.5 + (random.nextDouble() - 0.5) * 1.5;
                double d1 = pos.getY() + 0.05;
                double d2 = pos.getZ() + 0.5 + (random.nextDouble() - 0.5) * 1.5;
                world.addParticle(ParticleTypes.PORTAL, d0, d1, d2, 0.0, 0.0, 0.0);
            }
        }
    }
}