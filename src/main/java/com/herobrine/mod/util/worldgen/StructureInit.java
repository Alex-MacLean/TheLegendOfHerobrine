package com.herobrine.mod.util.worldgen;

import com.herobrine.mod.worldgen.structures.ShrineRemnants;
import com.herobrine.mod.worldgen.structures.Statue;
import com.herobrine.mod.worldgen.structures.SurvivorBase;
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
        ShrineRemnants.generateWorld(random, chunkX * 16, chunkZ * 16, world);
        Statue.generateWorld(random, chunkX * 16, chunkZ * 16, world);
        SurvivorBase.generateWorld(random, chunkX * 16, chunkZ * 16, world);
    }
}