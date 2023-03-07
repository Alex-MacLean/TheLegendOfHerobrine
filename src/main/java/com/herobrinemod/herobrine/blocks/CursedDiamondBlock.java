package com.herobrinemod.herobrine.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

public class CursedDiamondBlock extends Block {
    public CursedDiamondBlock(Settings settings) {
        super(settings);
    }

    @Override
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, net.minecraft.util.math.random.Random random) {
        Direction[] directions = Direction.values();
        for (Direction direction : directions) {
            BlockPos blockPos = pos.offset(direction);
            if (!world.getBlockState(blockPos).isOpaqueFullCube(world, blockPos)) {
                world.addParticle(ParticleTypes.PORTAL, pos.getX() + 0.5 + (random.nextDouble() - 0.5) * 1.5, pos.getY() + 0.05, pos.getZ() + 0.5 + (random.nextDouble() - 0.5) * 1.5, 0.0, 0.0, 0.0);
            }
        }
    }
}