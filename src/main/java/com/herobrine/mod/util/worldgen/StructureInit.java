package com.herobrine.mod.util.worldgen;

import com.herobrine.mod.worldgen.structures.TrappedHouse;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraftforge.fml.common.IWorldGenerator;

import java.util.Random;

public class StructureInit implements IWorldGenerator {
    @Override
    public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator cg, IChunkProvider cp) {
        TrappedHouse.generateWorld(random, chunkX * 16, chunkZ * 16, world);
    }
}