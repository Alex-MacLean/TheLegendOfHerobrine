package com.herobrine.mod.blocks;

import com.herobrine.mod.HerobrineMod;
import com.herobrine.mod.util.blocks.ModBlockStates;
import com.herobrine.mod.util.items.ItemList;
import com.herobrine.mod.util.savedata.Variables;
import com.sun.org.apache.xpath.internal.objects.XNull;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.IWaterLoggable;
import net.minecraft.block.SoundType;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.effect.LightningBoltEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.Fluids;
import net.minecraft.fluid.IFluidState;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.pathfinding.PathType;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.registries.ObjectHolder;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.Random;

import static com.herobrine.mod.HerobrineMod.RegistryEvents.HEROBRINE_ALTER_MATERIAL;
import static com.herobrine.mod.HerobrineMod.location;

public class HerobrineAlter extends Block implements IWaterLoggable {
    @ObjectHolder(HerobrineMod.MODID + ":herobrine_alter")
    public static final Block block = null;
    public static final VoxelShape SHAPE = VoxelShapes.or(Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 8.0D, 16.0D), Block.makeCuboidShape(0.0D, 9.0D, 0.0D, 1.0D, 15.0D, 1.0D), Block.makeCuboidShape(16.0D, 9.0D, 0.0D, 15.0D, 15.0D, 1.0D), Block.makeCuboidShape(16.0D, 9.0D, 16.0D, 15.0D, 15.0D, 15.0D), Block.makeCuboidShape(0.0D, 9.0D, 16.0D, 1.0D, 15.0D, 15.0D), Block.makeCuboidShape(0.0D, 15.0D, 0.0D, 2.0D, 16.0D, 2.0D), Block.makeCuboidShape(14.0D, 15.0D, 0.0D, 16.0D, 16.0D, 2.0D), Block.makeCuboidShape(14.0D, 15.0D, 14.0D, 16.0D, 16.0D, 16.0D), Block.makeCuboidShape(0.0D, 15.0D, 14.0D, 2.0D, 16.0D, 16.0D), Block.makeCuboidShape(0.0D, 8.0D, 0.0D, 2.0D, 9.0D, 2.0D), Block.makeCuboidShape(14.0D, 8.0D, 0.0D, 16.0D, 9.0D, 2.0D), Block.makeCuboidShape(14.0D, 8.0D, 14.0D, 16.0D, 9.0D, 16.0D), Block.makeCuboidShape(0.0D, 8.0D, 14.0D, 2.0D, 9.0D, 16.0D));
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

    public HerobrineAlter() {
        super(Properties.create(HEROBRINE_ALTER_MATERIAL).hardnessAndResistance(1.5F).sound(SoundType.METAL).harvestTool(ToolType.PICKAXE).harvestLevel(0).variableOpacity());
        this.setDefaultState(this.getDefaultState().with(BlockStateProperties.WATERLOGGED, Boolean.FALSE).with(ModBlockStates.ACTIVE, Boolean.FALSE));
        setRegistryName(location("herobrine_alter"));
    }

    @Override
    public int getLightValue(@NotNull BlockState state) {
        boolean i = state.get(ModBlockStates.ACTIVE);
        if (i == Boolean.TRUE) {
            return 8;
        } else {
            return 0;
        }
    }

    @NotNull
    @Override
    public VoxelShape getShape(@NotNull BlockState state, @NotNull IBlockReader worldIn, @NotNull BlockPos pos, @NotNull ISelectionContext context) {
        return SHAPE;
    }

    @Override
    public boolean canEntitySpawn(@NotNull BlockState state, @NotNull IBlockReader worldIn, @NotNull BlockPos pos, @NotNull EntityType<?> type) {
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
    public boolean receiveFluid(@NotNull IWorld worldIn, @NotNull BlockPos pos, @NotNull BlockState state, @NotNull IFluidState fluidStateIn) {
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

    @Nullable
    @Override
    public BlockState getStateForPlacement(@NotNull BlockItemUseContext context) {
        BlockPos blockpos = context.getPos();
        IFluidState ifluidstate = context.getWorld().getFluidState(blockpos);
        return this.getDefaultState().with(BlockStateProperties.WATERLOGGED, ifluidstate.getFluid() == Fluids.WATER);
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
    public void animateTick(@NotNull BlockState stateIn, @NotNull World worldIn, @NotNull BlockPos pos, @NotNull Random rand) {
        boolean i = stateIn.get(ModBlockStates.ACTIVE);
        if (i == Boolean.TRUE) {
            double d0 = (double) pos.getX() + 0.5D + ((double) rand.nextFloat() - 0.5D) * 0.2D;
            double d1 = (double) pos.getY() + 0.05F;
            double d2 = (double) pos.getZ() + 0.5D + ((double) rand.nextFloat() - 0.5D) * 0.2D;
            worldIn.addParticle(ParticleTypes.PORTAL, d0, d1, d2, 0.0D, 0.0D, 0.0D);
        }
    }

    @Override
    public boolean hasComparatorInputOverride(@NotNull BlockState state) {
        return true;
    }

    @Override
    public int getComparatorInputOverride(@NotNull BlockState blockState, @NotNull World worldIn, @NotNull BlockPos pos) {
        return blockState.get(ModBlockStates.ACTIVE) ? 15 : 0;
    }

    @Override
    public boolean onBlockActivated(@NotNull BlockState state, @NotNull World world, @NotNull BlockPos pos, @NotNull PlayerEntity entity, @NotNull Hand hand, @NotNull BlockRayTraceResult hit) {
        Variables.WorldVariables.get(world).syncData(world);
        boolean i = state.get(ModBlockStates.ACTIVE);
        if (i == Boolean.FALSE) {
            int x = pos.getX();
            int y = pos.getY();
            int z = pos.getZ();
            ItemStack itemstack = entity.getHeldItem(hand);
            if (itemstack.getItem() == ItemList.cursed_diamond) {
                assert false;
                state = state.getBlockState().with(ModBlockStates.ACTIVE, Boolean.TRUE);
                if (entity instanceof PlayerEntity && !entity.abilities.isCreativeMode) {
                    itemstack.shrink(1);
                }
                entity.swingArm(hand);
                world.setBlockState(pos, state, 2);
                if (state.get(WATERLOGGED)) {
                    world.getPendingFluidTicks().scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickRate(world));
                }
                if (world instanceof ServerWorld) {
                    ((ServerWorld) world).addLightningBolt(new LightningBoltEntity(world, x, y, z, false));
                }
                if (!(Variables.WorldVariables.get(world).Spawn)) {
                    if (((entity instanceof PlayerEntity) && (!(Variables.WorldVariables.get(world).Spawn)))) {
                        assert false;
                        if(world.isRemote) {
                            entity.sendMessage(new StringTextComponent("<Herobrine> You have no idea what you have done!"));
                        }
                        Variables.WorldVariables.get(world).Spawn = true;
                        Variables.WorldVariables.get(world).syncData(world);
                        return true;
                    }
                }
            }
        }
        return super.onBlockActivated(state, world, pos, entity, hand, hit);
    }
}