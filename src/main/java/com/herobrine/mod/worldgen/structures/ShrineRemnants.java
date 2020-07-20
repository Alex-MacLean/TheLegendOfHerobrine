package com.herobrine.mod.worldgen.structures;

import com.herobrine.mod.HerobrineMod;
import com.herobrine.mod.config.Config;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.Mirror;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.structure.template.PlacementSettings;
import net.minecraft.world.gen.structure.template.Template;
import net.minecraftforge.common.BiomeDictionary;
import org.jetbrains.annotations.NotNull;

import java.util.Random;

public class ShrineRemnants {
    @SuppressWarnings("ConstantConditions")
    public static void generateWorld(@NotNull Random random, int i2, int k2, @NotNull World world) {
        if ((random.nextInt(1000000) + 1) <= Config.ShrineRemnantWeight) {
            int count = random.nextInt(1) + 1;
            for (int a = 0; a < count; a++) {
                int i = i2 + random.nextInt(16) + 8;
                int k = k2 + random.nextInt(16) + 8;
                int height = 255;
                while (height > 0) {
                    if (!world.isAirBlock(new BlockPos(i, height, k)) && world.getBlockState(new BlockPos(i, height, k)).getBlock().getMaterial(world.getBlockState(new BlockPos(i, height, k))).blocksMovement()) {
                        break;
                    }
                    height--;
                }
                int j = height;
                IBlockState blockAt = world.getBlockState(new BlockPos(i, j, k));
                boolean blockCriteria = false;
                if (blockAt.getMaterial() == Material.GRASS || blockAt.getMaterial() == Material.GROUND || blockAt.getMaterial() == Material.ROCK) {
                    blockCriteria = true;
                }
                if (!blockCriteria) {
                    continue;
                }
                boolean biomeCriteria = false;
                Biome biome = world.getBiome(new BlockPos(i, j, k));
                if (BiomeDictionary.getTypes(biome).contains(BiomeDictionary.Type.SPOOKY)) {
                    biomeCriteria = true;
                }
                if (!biomeCriteria) {
                    continue;
                }
                if (world.isRemote) {
                    return;
                }
                int type = random.nextInt(4);
                Template template;
                switch(type) {
                    case 0:
                        template = ((WorldServer) world).getStructureTemplateManager().getTemplate(world.getMinecraftServer(), new ResourceLocation(HerobrineMod.MODID, "ruined_shrine"));
                        break;
                    case 1:
                        template = ((WorldServer) world).getStructureTemplateManager().getTemplate(world.getMinecraftServer(), new ResourceLocation(HerobrineMod.MODID, "ruined_shrine_alt"));
                        break;
                    case 2:
                        template = ((WorldServer) world).getStructureTemplateManager().getTemplate(world.getMinecraftServer(), new ResourceLocation(HerobrineMod.MODID, "ruined_shrine_alt_1"));
                        break;
                    case 3:
                        template = ((WorldServer) world).getStructureTemplateManager().getTemplate(world.getMinecraftServer(), new ResourceLocation(HerobrineMod.MODID, "ruined_shrine_alt_2"));
                        break;
                    case 4:
                        template = ((WorldServer) world).getStructureTemplateManager().getTemplate(world.getMinecraftServer(), new ResourceLocation(HerobrineMod.MODID, "ruined_shrine_alt_3"));
                        break;
                    default:
                        //I don't know how a value below zero or above four would happen with the bound of 4, but the IDE would error if a default state is not set.
                        throw new IllegalStateException("[The Legend of Herobrine] Illegal type for Shrine Remnants: " + type + ". Please report this to the issue tracker.");
                }
                Rotation rotation = Rotation.values()[random.nextInt(3)];
                Mirror mirror = Mirror.values()[random.nextInt(2)];
                BlockPos spawnTo = new BlockPos(i, j, k);
                IBlockState iblockstate = world.getBlockState(spawnTo);
                world.notifyBlockUpdate(spawnTo, iblockstate, iblockstate, 3);
                template.addBlocksToWorldChunk(world, spawnTo, new PlacementSettings().setRotation(rotation).setMirror(mirror).setChunk(null).setReplacedBlock(null).setIgnoreStructureBlock(false).setIgnoreEntities(false));
            }
        }
    }
}
