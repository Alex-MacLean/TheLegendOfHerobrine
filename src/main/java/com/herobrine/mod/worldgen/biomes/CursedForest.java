package com.herobrine.mod.worldgen.biomes;

import com.herobrine.mod.util.worldgen.CursedBirchTreeFeature;
import com.herobrine.mod.util.worldgen.CursedTreeFeature;
import net.minecraft.block.BlockDoublePlant;
import net.minecraft.block.BlockFlower;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeDecorator;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;
import net.minecraft.world.gen.feature.WorldGenBirchTree;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.jetbrains.annotations.NotNull;

import java.util.Random;

public class CursedForest extends Biome {
    protected static final CursedTreeFeature CURSED_OAK_TREES = new CursedTreeFeature(false, false);
    protected static final CursedBirchTreeFeature CURSED_BIRCH_TREES = new CursedBirchTreeFeature(false, false);
    protected static final WorldGenBirchTree BIRCH_TREE = new WorldGenBirchTree(false, false);

    public CursedForest() {
        super(new BiomeProperties("cursed_forest").setTemperature(0.7F).setRainfall(0.8F));
        this.decorator.treesPerChunk = 10;
        this.decorator.grassPerChunk = 2;
        this.spawnableCreatureList.add(new Biome.SpawnListEntry(EntityWolf.class, 5, 4, 4));
    }

    @Override
    public @NotNull WorldGenAbstractTree getRandomTreeFeature(@NotNull Random rand)
    {
        if(rand.nextInt(7) > 0) {
            return CURSED_OAK_TREES;
        } else if(rand.nextInt(10) != 0) {
            return CURSED_BIRCH_TREES;
        } else if (rand.nextInt(5) == 0) {
            return BIRCH_TREE;
        } else {
            return rand.nextInt(10) == 0 ? BIG_TREE_FEATURE : TREE_FEATURE;
        }
    }

    @Override
    public BlockFlower.@NotNull EnumFlowerType pickRandomFlower(@NotNull Random rand, @NotNull BlockPos pos) {
        return super.pickRandomFlower(rand, pos);
    }

    public void decorate(@NotNull World worldIn, @NotNull Random rand, @NotNull BlockPos pos) {
        if(net.minecraftforge.event.terraingen.TerrainGen.decorate(worldIn, rand, new net.minecraft.util.math.ChunkPos(pos), net.minecraftforge.event.terraingen.DecorateBiomeEvent.Decorate.EventType.FLOWERS)) {
            int i = rand.nextInt(5) - 3;
            this.addDoublePlants(worldIn, rand, pos, i);
        }
        super.decorate(worldIn, rand, pos);
    }

    public void addDoublePlants(World p_185378_1_, Random p_185378_2_, BlockPos p_185378_3_, int p_185378_4_) {
        for (int i = 0; i < p_185378_4_; ++i) {
            int j = p_185378_2_.nextInt(3);

            if (j == 0) {
                DOUBLE_PLANT_GENERATOR.setPlantType(BlockDoublePlant.EnumPlantType.SYRINGA);
            }
            else if (j == 1) {
                DOUBLE_PLANT_GENERATOR.setPlantType(BlockDoublePlant.EnumPlantType.ROSE);
            }
            else if (j == 2) {
                DOUBLE_PLANT_GENERATOR.setPlantType(BlockDoublePlant.EnumPlantType.PAEONIA);
            }

            for (int k = 0; k < 5; ++k) {
                int l = p_185378_2_.nextInt(16) + 8;
                int i1 = p_185378_2_.nextInt(16) + 8;
                int j1 = p_185378_2_.nextInt(p_185378_1_.getHeight(p_185378_3_.add(l, 0, i1)).getY() + 32);

                if (DOUBLE_PLANT_GENERATOR.generate(p_185378_1_, p_185378_2_, new BlockPos(p_185378_3_.getX() + l, j1, p_185378_3_.getZ() + i1))) {
                    break;
                }
            }
        }
    }

    @Override
    public @NotNull BiomeDecorator createBiomeDecorator() {
        return new CursedForest.Decorator();
    }

    static class Decorator extends BiomeDecorator {
        private Decorator() {
        }
        protected void generateOres(@NotNull World worldIn, @NotNull Random random) {
            super.generateOres(worldIn, random);
            if (net.minecraftforge.event.terraingen.TerrainGen.generateOre(worldIn, random, goldGen, chunkPos, net.minecraftforge.event.terraingen.OreGenEvent.GenerateMinable.EventType.GOLD)) this.genStandardOre1(worldIn, random, 20, this.goldGen, 32, 80);
        }
    }

    @SideOnly(Side.CLIENT)
    @Override
    public int getFoliageColorAtPos(@NotNull BlockPos pos) {
        return getModdedBiomeFoliageColor(0x9EA94D);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public int getGrassColorAtPos(@NotNull BlockPos pos) {
        return getModdedBiomeGrassColor(0x9EA94D);
    }
}