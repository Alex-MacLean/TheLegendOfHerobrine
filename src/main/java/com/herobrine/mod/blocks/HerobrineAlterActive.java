package com.herobrine.mod.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.IWaterLoggable;
import net.minecraft.block.SoundType;
import net.minecraft.entity.EntityType;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.Fluids;
import net.minecraft.fluid.IFluidState;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.pathfinding.PathType;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.common.extensions.IForgeFluidState;
import net.minecraftforge.registries.ObjectHolder;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.Random;

import static com.herobrine.mod.HerobrineMod.RegistryEvents.HEROBRINE_ALTER_MATERIAL;
import static com.herobrine.mod.HerobrineMod.location;

public class HerobrineAlterActive extends Block implements IWaterLoggable {

    @ObjectHolder("herobrine:herobrine_alter_active")
    public static final Block block = null;

    public HerobrineAlterActive() {
        super(Properties.create(HEROBRINE_ALTER_MATERIAL).hardnessAndResistance(1.5f).sound(SoundType.METAL).lightValue(12).harvestTool(ToolType.PICKAXE).harvestLevel(0).func_226896_b_());
        this.setDefaultState(this.getDefaultState().with(BlockStateProperties.WATERLOGGED, Boolean.FALSE));
        setRegistryName(location("herobrine_alter_active"));
    }

    @Override
    public boolean canEntitySpawn(BlockState state, @NotNull IBlockReader worldIn, @NotNull BlockPos pos, EntityType<?> type) {
        return false;
    }

    @Override
    protected void fillStateContainer(@NotNull StateContainer.Builder<Block, BlockState> builder) {
        builder.add(BlockStateProperties.WATERLOGGED);
    }

    @NotNull
    @Override
    public IFluidState getFluidState(@NotNull BlockState state) {
        return state.get(BlockStateProperties.WATERLOGGED) ? Fluids.WATER.getStillFluidState(false) : super.getFluidState(state);
    }

    @Override
    public boolean receiveFluid(@NotNull IWorld worldIn, @NotNull BlockPos pos, BlockState state, @NotNull IFluidState fluidStateIn) {
        return IWaterLoggable.super.receiveFluid(worldIn, pos, state, fluidStateIn);
    }

    @Override
    public boolean canContainFluid(IBlockReader worldIn, BlockPos pos, BlockState state, Fluid fluidIn) {
        return IWaterLoggable.super.canContainFluid(worldIn, pos, state, fluidIn);
    }

    @NotNull
    @Override
    public BlockState updatePostPlacement(@NotNull BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos) {
        if (stateIn.get(BlockStateProperties.WATERLOGGED)) {
            worldIn.getPendingFluidTicks().scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickRate(worldIn));
        }
        return super.updatePostPlacement(stateIn, facing, facingState, worldIn, currentPos, facingPos);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(@NotNull BlockItemUseContext context) {
        BlockPos blockpos = context.getPos();
        IFluidState ifluidstate = context.getWorld().getFluidState(blockpos);
        return this.getDefaultState().with(BlockStateProperties.WATERLOGGED, ifluidstate.getFluid() == Fluids.WATER);
    }

    @Override
    public boolean allowsMovement(@NotNull BlockState state, @NotNull IBlockReader worldIn, @NotNull BlockPos pos, PathType type) {
        if (type == PathType.WATER) {
            return worldIn.getFluidState(pos).isTagged(FluidTags.WATER);
        }
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