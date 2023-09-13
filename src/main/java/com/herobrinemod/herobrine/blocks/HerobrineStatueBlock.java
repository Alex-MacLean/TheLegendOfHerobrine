package com.herobrinemod.herobrine.blocks;

import net.minecraft.block.*;
import net.minecraft.block.enums.DoubleBlockHalf;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.pathing.NavigationType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class HerobrineStatueBlock extends Block implements Waterloggable{
    public static final BooleanProperty WATERLOGGED = Properties.WATERLOGGED;
    public static final DirectionProperty FACING = HorizontalFacingBlock.FACING;
    public static final EnumProperty<DoubleBlockHalf> HALF = Properties.DOUBLE_BLOCK_HALF;
    public static final VoxelShape NORTH_SOUTH_TOP = VoxelShapes.union(Block.createCuboidShape(0, 0, 6, 16, 8, 10), Block.createCuboidShape(4, 8, 4, 12, 16, 12));
    public static final VoxelShape EAST_WEST_TOP = VoxelShapes.union(Block.createCuboidShape(6, 0, 0, 10, 8, 16), Block.createCuboidShape(4, 8, 4, 12, 16, 12));
    public static final VoxelShape NORTH_SOUTH_BOTTOM = VoxelShapes.union(Block.createCuboidShape(4, 0, 6, 12, 12, 10), Block.createCuboidShape(0, 12, 6, 16, 16, 10));
    public static final VoxelShape EAST_WEST_BOTTOM = VoxelShapes.union(Block.createCuboidShape(6, 0, 4, 10, 12, 12), Block.createCuboidShape(6, 12, 0, 10, 16, 16));

    public HerobrineStatueBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.getDefaultState().with(WATERLOGGED, false).with(FACING, Direction.NORTH).with(HALF, DoubleBlockHalf.LOWER));
    }

    @Override
    protected void appendProperties(StateManager.@NotNull Builder<Block, BlockState> builder) {
        builder.add(WATERLOGGED);
        builder.add(FACING);
        builder.add(HALF);
    }

    @NotNull
    @Override
    public VoxelShape getOutlineShape(@NotNull BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        if(state.get(HALF) == DoubleBlockHalf.LOWER) {
            if(state.get(FACING) == Direction.NORTH || state.get(FACING) == Direction.SOUTH) {
                return NORTH_SOUTH_BOTTOM;
            }

            return EAST_WEST_BOTTOM;
        }
        if(state.get(FACING) == Direction.NORTH || state.get(FACING) == Direction.SOUTH) {
            return NORTH_SOUTH_TOP;
        }

        return EAST_WEST_TOP;
    }


    @Override
    public boolean canPathfindThrough(BlockState state, BlockView world, BlockPos pos, NavigationType type) {
        return false;
    }

    @Override
    public FluidState getFluidState(@NotNull BlockState state) {
        return state.get(WATERLOGGED) ? Fluids.WATER.getStill(false) : super.getFluidState(state);
    }

    @Override
    public boolean tryFillWithFluid(WorldAccess world, BlockPos pos, @NotNull BlockState state, FluidState fluidState) {
        return Waterloggable.super.tryFillWithFluid(world, pos, state, fluidState);
    }

    @Override
    public boolean canFillWithFluid(PlayerEntity player, BlockView world, BlockPos pos, BlockState state, Fluid fluid) {
        return Waterloggable.super.canFillWithFluid(player, world, pos, state, fluid);
    }

    @Nullable
    @Override
    public BlockState getPlacementState(@NotNull ItemPlacementContext ctx) {
        if (ctx.getBlockPos().getY() < ctx.getWorld().getTopY() - 1 && ctx.getWorld().getBlockState(ctx.getBlockPos().up()).canReplace(ctx)) {
            return this.getDefaultState().with(WATERLOGGED, ctx.getWorld().getBlockState(ctx.getBlockPos()).getFluidState().getFluid() == Fluids.WATER).with(FACING, ctx.getHorizontalPlayerFacing().getOpposite());
        }

        return null;
    }

    @Override
    public BlockState getStateForNeighborUpdate(@NotNull BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        if (state.get(WATERLOGGED)) {
            world.scheduleFluidTick(pos, Fluids.WATER, Fluids.WATER.getTickRate(world));
        }

        if (!(direction.getAxis() != Direction.Axis.Y || state.get(HALF) == DoubleBlockHalf.LOWER != (direction == Direction.UP) || neighborState.isOf(this) && neighborState.get(HALF) != state.get(HALF))) {
            return Blocks.AIR.getDefaultState();
        }

        return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
    }

    @Override
    public void onPlaced(@NotNull World world, @NotNull BlockPos pos, @NotNull BlockState state, LivingEntity placer, ItemStack itemStack) {
        BlockPos blockPos = pos.up();
        world.setBlockState(blockPos, withWaterloggedState(world, blockPos, this.getDefaultState().with(HALF, DoubleBlockHalf.UPPER).with(FACING, state.get(FACING))), Block.NOTIFY_ALL);
    }

    @Override
    public boolean canPlaceAt(@NotNull BlockState state, WorldView world, BlockPos pos) {
        if (state.get(HALF) == DoubleBlockHalf.UPPER) {
            BlockState blockState = world.getBlockState(pos.down());
            return blockState.isOf(this) && blockState.get(HALF) == DoubleBlockHalf.LOWER;
        }

        return super.canPlaceAt(state, world, pos);
    }

    public static BlockState withWaterloggedState(WorldView world, BlockPos pos, @NotNull BlockState state) {
        if (state.contains(Properties.WATERLOGGED)) {
            return state.with(Properties.WATERLOGGED, world.isWater(pos));
        }

        return state;
    }

    @Override
    public void onBreak(@NotNull World world, BlockPos pos, BlockState state, PlayerEntity player) {
        if (!world.isClient && player.isCreative() || !player.getMainHandStack().isSuitableFor(state)) {
            TallPlantBlock.onBreakInCreative(world, pos, state, player);
        }
        super.onBreak(world, pos, state, player);
    }
}