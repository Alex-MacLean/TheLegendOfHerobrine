package com.herobrine.mod.blocks;

import com.herobrine.mod.util.blocks.BlockList;
import com.herobrine.mod.util.blocks.BlockMaterialList;
import net.minecraft.block.*;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.pathfinding.PathType;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraftforge.common.ToolType;
import org.jetbrains.annotations.NotNull;

public class HerobrineStatue extends HorizontalBlock implements IWaterLoggable {
    public static final DirectionProperty FACING = HorizontalBlock.FACING;
    public static final VoxelShape North_South = VoxelShapes.or(Block.box(4, 0, 6, 12, 12, 10), Block.box(0, 12, 6, 16, 16, 10));
    public static final VoxelShape East_West = VoxelShapes.or(Block.box(6, 0, 4, 10, 12, 12), Block.box(6, 12, 0, 10, 16, 16));

    private static Boolean neverAllowSpawn(BlockState state, IBlockReader reader, BlockPos pos, EntityType<?> entity) {
        return false;
    }

    private static boolean isntSolid(BlockState state, IBlockReader reader, BlockPos pos) {
        return false;
    }

    public HerobrineStatue() {
        super(Properties.of(BlockMaterialList.HEROBRINE_STATUE_MATERIAL).strength(1.5F).sound(SoundType.STONE).harvestTool(ToolType.PICKAXE).harvestLevel(0).requiresCorrectToolForDrops().isValidSpawn(HerobrineStatue::neverAllowSpawn).isSuffocating(HerobrineStatue::isntSolid));
        this.registerDefaultState(this.defaultBlockState().setValue(FACING, Direction.NORTH).setValue(BlockStateProperties.WATERLOGGED, Boolean.FALSE));
    }

    @NotNull
    @Override
    public VoxelShape getShape(@NotNull BlockState state, @NotNull IBlockReader worldIn, @NotNull BlockPos pos, @NotNull ISelectionContext context) {
        Direction i = state.getValue(FACING);
        if(i == Direction.NORTH || i == Direction.SOUTH) {
            return North_South;
        } else return East_West;
    }

    @Override
    public void wasExploded(@NotNull World world, @NotNull BlockPos pos, @NotNull Explosion explosion) {
        super.wasExploded(world, pos, explosion);
        int x = pos.getX();
        int y = pos.getY();
        int z = pos.getZ();
        if (world.getBlockState(new BlockPos(x, y + 1, z)).getMaterial() == BlockMaterialList.HEROBRINE_STATUE_MATERIAL) {
            if (world.getBlockState(new BlockPos(x, y + 1, z)).getValue(BlockStateProperties.WATERLOGGED)) {
                world.setBlockAndUpdate(new BlockPos(x, y + 1, z), Blocks.WATER.defaultBlockState());
            } else world.setBlockAndUpdate(new BlockPos(x, y + 1, z), Blocks.AIR.defaultBlockState());
        }
    }

    @Override
    public boolean removedByPlayer(BlockState state, @NotNull World world, @NotNull BlockPos pos, PlayerEntity entity, boolean willHarvest, FluidState fluid) {
        int x = pos.getX();
        int y = pos.getY();
        int z = pos.getZ();
        if (world.getBlockState(new BlockPos(x, y + 1, z)).getMaterial() == BlockMaterialList.HEROBRINE_STATUE_MATERIAL) {
            if (world.getBlockState(new BlockPos(x, y + 1, z)).getValue(BlockStateProperties.WATERLOGGED)) {
                world.setBlockAndUpdate(new BlockPos(x, y + 1, z), Blocks.WATER.defaultBlockState());
            } else world.setBlockAndUpdate(new BlockPos(x, y + 1, z), Blocks.AIR.defaultBlockState());
        }
        return super.removedByPlayer(state, world, pos, entity, willHarvest, fluid);
    }

    @Override
    public void onPlace(@NotNull BlockState state, @NotNull World world, @NotNull BlockPos pos, @NotNull BlockState oldState, boolean moving) {
        super.onPlace(state, world, pos, oldState, moving);
        int x = pos.getX();
        int y = pos.getY();
        int z = pos.getZ();
        FluidState FluidState = world.getFluidState(new BlockPos(x, y + 1, z));
        assert false;
        world.setBlockAndUpdate(new BlockPos(x, y + 1, z), BlockList.herobrine_statue_top.defaultBlockState().setValue(FACING, state.getValue(FACING)).setValue(BlockStateProperties.WATERLOGGED, FluidState.getType() == Fluids.WATER));
    }

    @Override
    public BlockState getStateForPlacement(@NotNull BlockItemUseContext context) {
        BlockPos blockpos = context.getClickedPos();
        FluidState ifluidstate = context.getLevel().getFluidState(blockpos);
        return this.defaultBlockState().setValue(BlockStateProperties.WATERLOGGED, ifluidstate.getType() == Fluids.WATER).setValue(FACING, context.getHorizontalDirection().getOpposite());
    }

    @Override
    protected void createBlockStateDefinition(StateContainer.@NotNull Builder<Block, BlockState> builder) {
        builder.add(FACING);
        builder.add(BlockStateProperties.WATERLOGGED);
    }

    @Override
    public boolean placeLiquid(@NotNull IWorld worldIn, @NotNull BlockPos pos, @NotNull BlockState state, @NotNull FluidState fluidStateIn) {
        return IWaterLoggable.super.placeLiquid(worldIn, pos, state, fluidStateIn);
    }

    @Override
    public boolean canPlaceLiquid(@NotNull IBlockReader worldIn, @NotNull BlockPos pos, @NotNull BlockState state, @NotNull Fluid fluidIn) {
        return IWaterLoggable.super.canPlaceLiquid(worldIn, pos, state, fluidIn);
    }

    @NotNull
    @Override
    public BlockState updateShape(@NotNull BlockState stateIn, @NotNull Direction facing, @NotNull BlockState facingState, @NotNull IWorld worldIn, @NotNull BlockPos currentPos, @NotNull BlockPos facingPos) {
        if (stateIn.getValue(BlockStateProperties.WATERLOGGED)) {
            worldIn.getLiquidTicks().scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickDelay(worldIn));
        }
        return super.updateShape(stateIn, facing, facingState, worldIn, currentPos, facingPos);
    }

    @NotNull
    @Override
    public FluidState getFluidState(@NotNull BlockState state) {
        return state.getValue(BlockStateProperties.WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }

    @Override
    public boolean isPathfindable(@NotNull BlockState state, @NotNull IBlockReader worldIn, @NotNull BlockPos pos, @NotNull PathType type) {
        if (type == PathType.WATER) {
            return worldIn.getFluidState(pos).is(FluidTags.WATER);
        }
        return false;
    }

    @Override
    public boolean propagatesSkylightDown(@NotNull BlockState state, @NotNull IBlockReader reader, @NotNull BlockPos pos) {
        return true;
    }
}
