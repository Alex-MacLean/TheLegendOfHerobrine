package com.herobrine.mod.blocks;

import com.herobrine.mod.util.blocks.BlockMaterialList;
import net.minecraft.block.*;
import net.minecraft.block.material.PushReaction;
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
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.ToolType;
import org.jetbrains.annotations.NotNull;

public class HerobrineStatueTop extends HorizontalBlock implements IWaterLoggable {
    public static final DirectionProperty FACING = HorizontalBlock.HORIZONTAL_FACING;
    public static final VoxelShape North_South = VoxelShapes.or(Block.makeCuboidShape(0, 0, 6, 16, 8, 10), Block.makeCuboidShape(4, 8, 4, 12, 16, 12));
    public static final VoxelShape East_West = VoxelShapes.or(Block.makeCuboidShape(6, 0, 0, 10, 8, 16), Block.makeCuboidShape(4, 8, 4, 12, 16, 12));

    private static Boolean neverAllowSpawn(BlockState state, IBlockReader reader, BlockPos pos, EntityType<?> entity) {
        return false;
    }
    private static boolean isntSolid(BlockState state, IBlockReader reader, BlockPos pos) {
        return false;
    }

    public HerobrineStatueTop() {
        super(Properties.create(BlockMaterialList.HEROBRINE_STATUE_MATERIAL).hardnessAndResistance(1.5F).sound(SoundType.STONE).harvestTool(ToolType.PICKAXE).harvestLevel(0).notSolid().variableOpacity().setAllowsSpawn(HerobrineStatueTop::neverAllowSpawn).setSuffocates(HerobrineStatueTop::isntSolid));
        this.setDefaultState(this.stateContainer.getBaseState().with(FACING, Direction.NORTH).with(BlockStateProperties.WATERLOGGED, Boolean.FALSE));
    }

    @NotNull
    @Override
    public VoxelShape getShape(@NotNull BlockState state, @NotNull IBlockReader worldIn, @NotNull BlockPos pos, @NotNull ISelectionContext context) {
        Direction i = state.get(FACING);
        if(i == Direction.NORTH || i == Direction.SOUTH) {
            return North_South;
        } else return East_West;
    }

    @Override
    public void onExplosionDestroy(@NotNull World world, @NotNull BlockPos pos, @NotNull Explosion explosion) {
        super.onExplosionDestroy(world, pos, explosion);
        int x = pos.getX();
        int y = pos.getY();
        int z = pos.getZ();
        if (world.getBlockState(new BlockPos(x, y - 1, z)).getMaterial() == BlockMaterialList.HEROBRINE_STATUE_MATERIAL) {
            if(world.getBlockState(new BlockPos(x, y - 1, z)).get(BlockStateProperties.WATERLOGGED)) {
                world.setBlockState(new BlockPos(x, y - 1, z), Blocks.WATER.getDefaultState(), 3);
            } else world.setBlockState(new BlockPos(x, y - 1, z), Blocks.AIR.getDefaultState(), 3);
        }
    }

    @Override
    public @NotNull PushReaction getPushReaction(@NotNull BlockState state) {
        return PushReaction.BLOCK;
    }

    @Override
    public boolean removedByPlayer(BlockState state, @NotNull World world, @NotNull BlockPos pos, PlayerEntity entity, boolean willHarvest, FluidState fluid) {
        int x = pos.getX();
        int y = pos.getY();
        int z = pos.getZ();
        if (world.getBlockState(new BlockPos(x, y - 1, z)).getMaterial() == BlockMaterialList.HEROBRINE_STATUE_MATERIAL) {
            if(world.getBlockState(new BlockPos(x, y - 1, z)).get(BlockStateProperties.WATERLOGGED)) {
                world.setBlockState(new BlockPos(x, y - 1, z), Blocks.WATER.getDefaultState(), 3);
            } else world.setBlockState(new BlockPos(x, y - 1, z), Blocks.AIR.getDefaultState(), 3);
        }
        return super.removedByPlayer(state, world, pos, entity, willHarvest, fluid);
    }

    @Override
    public BlockState getStateForPlacement(@NotNull BlockItemUseContext context) {
        BlockPos blockpos = context.getPos();
        FluidState FluidState = context.getWorld().getFluidState(blockpos);
        return this.getDefaultState().with(BlockStateProperties.WATERLOGGED, FluidState.getFluid() == Fluids.WATER).with(FACING, context.getPlacementHorizontalFacing().getOpposite());
    }

    @Override
    protected void fillStateContainer(StateContainer.@NotNull Builder<Block, BlockState> builder) {
        builder.add(FACING);
        builder.add(BlockStateProperties.WATERLOGGED);
    }

    @Override
    public boolean receiveFluid(@NotNull IWorld worldIn, @NotNull BlockPos pos, @NotNull BlockState state, @NotNull FluidState fluidStateIn) {
        return IWaterLoggable.super.receiveFluid(worldIn, pos, state, fluidStateIn);
    }

    @Override
    public boolean canContainFluid(@NotNull IBlockReader worldIn, @NotNull BlockPos pos, @NotNull BlockState state, @NotNull Fluid fluidIn) {
        return IWaterLoggable.super.canContainFluid(worldIn, pos, state, fluidIn);
    }

    @NotNull
    @Override
    public BlockState updatePostPlacement(@NotNull BlockState stateIn, @NotNull Direction facing, @NotNull BlockState facingState, @NotNull IWorld worldIn, @NotNull BlockPos currentPos, @NotNull BlockPos facingPos) {
        if (stateIn.get(BlockStateProperties.WATERLOGGED)) {
            worldIn.getPendingFluidTicks().scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickRate(worldIn));
        }
        return super.updatePostPlacement(stateIn, facing, facingState, worldIn, currentPos, facingPos);
    }

    @NotNull
    @Override
    public FluidState getFluidState(@NotNull BlockState state) {
        return state.get(BlockStateProperties.WATERLOGGED) ? Fluids.WATER.getStillFluidState(false) : super.getFluidState(state);
    }

    @Override
    public boolean allowsMovement(@NotNull BlockState state, @NotNull IBlockReader worldIn, @NotNull BlockPos pos, @NotNull PathType type) {
        if (type == PathType.WATER) {
            return worldIn.getFluidState(pos).isTagged(FluidTags.WATER);
        }
        return false;
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public float getAmbientOcclusionLightValue(@NotNull BlockState state, @NotNull IBlockReader worldIn, @NotNull BlockPos pos) {
        return 1.0F;
    }

    @Override
    public boolean propagatesSkylightDown(@NotNull BlockState state, @NotNull IBlockReader reader, @NotNull BlockPos pos) {
        return true;
    }

/*    @Override
    public boolean isNormalCube(@NotNull BlockState state, @NotNull IBlockReader worldIn, @NotNull BlockPos pos) {
        return false;
    }*/
}
