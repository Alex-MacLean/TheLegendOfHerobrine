package com.herobrine.mod.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.entity.EntityType;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.registries.ObjectHolder;
import org.jetbrains.annotations.NotNull;

import java.util.Random;

import static com.herobrine.mod.HerobrineMod.RegistryEvents.HEROBRINE_ALTER_MATERIAL;
import static com.herobrine.mod.HerobrineMod.location;

public class HerobrineAlterActive extends Block {

    @ObjectHolder("herobrine:herobrine_alter_active")
    public static final Block block = null;

    public HerobrineAlterActive() {
        super(Properties.create(HEROBRINE_ALTER_MATERIAL).hardnessAndResistance(1.5f).sound(SoundType.METAL).lightValue(12).harvestTool(ToolType.PICKAXE).harvestLevel(0).func_226896_b_());
        setRegistryName(location("herobrine_alter_active"));
    }

    @Override
    public boolean canEntitySpawn(BlockState state, @NotNull IBlockReader worldIn, @NotNull BlockPos pos, EntityType<?> type) {
        return false;
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void animateTick(BlockState state, World world, BlockPos pos, Random random) {
        super.animateTick(state, world, pos, random);
        int x = pos.getX();
        int y = pos.getY();
        int z = pos.getZ();
        for (int l = 0; l < 1; ++l) {
            double d0 = (x + random.nextFloat());
            double d1 = (y + random.nextFloat());
            double d2 = (z + random.nextFloat());
            double d3 = (random.nextFloat() - 0.5D) * 0.5D;
            double d4 = (random.nextFloat() - 0.5D) * 0.5D;
            double d5 = (random.nextFloat() - 0.5D) * 0.5D;
            world.addParticle(ParticleTypes.SMOKE, d0, d1, d2, d3, d4, d5);
        }
    }
}