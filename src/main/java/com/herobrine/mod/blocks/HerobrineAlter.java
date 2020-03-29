package com.herobrine.mod.blocks;

import com.herobrine.mod.Variables;
import com.herobrine.mod.items.ItemList;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.IWaterLoggable;
import net.minecraft.block.SoundType;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.Fluids;
import net.minecraft.fluid.IFluidState;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.pathfinding.PathType;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.registries.ObjectHolder;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

import static com.herobrine.mod.HerobrineMod.RegistryEvents.HEROBRINE_ALTER_MATERIAL;
import static com.herobrine.mod.HerobrineMod.location;

public class HerobrineAlter extends Block implements IWaterLoggable {
    @ObjectHolder("herobrine:herobrine_alter")
    public static final Block block = null;
    public static final VoxelShape SHAPE = VoxelShapes.or(Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 8.0D, 16.0D), Block.makeCuboidShape(0.0D, 8.0D, 0.0D, 1.0D, 16.0D, 1.0D), Block.makeCuboidShape(16.0D, 8.0D, 0.0D, 15.0D, 16.0D, 1.0D), Block.makeCuboidShape(16.0D, 8.0D, 16.0D, 15.0D, 16.0D, 15.0D), Block.makeCuboidShape(0.0D, 8.0D, 16.0D, 1.0D, 16.0D, 15.0D));
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

    public HerobrineAlter() {
        super(Properties.create(HEROBRINE_ALTER_MATERIAL).hardnessAndResistance(1.5f).sound(SoundType.METAL).harvestTool(ToolType.PICKAXE).harvestLevel(0).notSolid());
        this.setDefaultState(this.getDefaultState().with(BlockStateProperties.WATERLOGGED, Boolean.FALSE).with(ModBlockStates.ACTIVE, Boolean.FALSE));
        setRegistryName(location("herobrine_alter"));
    }

    @NotNull
    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return SHAPE;
    }

    @Override
    public boolean canEntitySpawn(BlockState state, @NotNull IBlockReader worldIn, @NotNull BlockPos pos, EntityType<?> type) {
        return false;
    }

    @Override
    protected void fillStateContainer(@NotNull StateContainer.Builder<Block, BlockState> builder) {
        builder.add(BlockStateProperties.WATERLOGGED);
        builder.add(ModBlockStates.ACTIVE);
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

    @NotNull
    @Override
    public ActionResultType onBlockActivated(BlockState state, World world, @NotNull BlockPos pos, PlayerEntity entity, Hand hand, BlockRayTraceResult hit) {
            int x = pos.getX();
            int y = pos.getY();
            int z = pos.getZ();
        {
            java.util.HashMap<String, Object> $_dependencies = new java.util.HashMap<>();
            $_dependencies.put("entity", entity);
            $_dependencies.put("x", x);
            $_dependencies.put("y", y);
            $_dependencies.put("z", z);
            $_dependencies.put("world", world);
            if (((entity != null) && entity.inventory.hasItemStack(new ItemStack(ItemList.cursed_diamond, 1))) && (!(Variables.WorldVariables.get(world).Spawn))) {
                assert false;
            state = state.getBlockState().with(ModBlockStates.ACTIVE, Boolean.TRUE);
            world.setBlockState(pos, state, 2);
            if (state.get(WATERLOGGED)) {
                world.getPendingFluidTicks().scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickRate(world));
            }
        }
                ActivateAlter.executeProcedure($_dependencies);
            }
            return ActionResultType.SUCCESS;
    }
}