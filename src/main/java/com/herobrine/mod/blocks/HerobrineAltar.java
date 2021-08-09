package com.herobrine.mod.blocks;

import com.herobrine.mod.config.Config;
import com.herobrine.mod.util.blocks.BlockMaterialList;
import com.herobrine.mod.util.blocks.ModBlockStates;
import com.herobrine.mod.util.items.ItemList;
import com.herobrine.mod.util.savedata.SaveDataUtil;
import net.minecraft.block.*;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.effect.LightningBoltEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.particles.ParticleTypes;
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
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.ToolType;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.Random;
import java.util.UUID;

public class HerobrineAltar extends Block implements IWaterLoggable {
    public static final VoxelShape SHAPE = VoxelShapes.or(Block.box(0.0D, 0.0D, 0.0D, 16.0D, 8.0D, 16.0D), Block.box(0.0D, 9.0D, 0.0D, 1.0D, 15.0D, 1.0D), Block.box(16.0D, 9.0D, 0.0D, 15.0D, 15.0D, 1.0D), Block.box(16.0D, 9.0D, 16.0D, 15.0D, 15.0D, 15.0D), Block.box(0.0D, 9.0D, 16.0D, 1.0D, 15.0D, 15.0D), Block.box(0.0D, 15.0D, 0.0D, 2.0D, 16.0D, 2.0D), Block.box(14.0D, 15.0D, 0.0D, 16.0D, 16.0D, 2.0D), Block.box(14.0D, 15.0D, 14.0D, 16.0D, 16.0D, 16.0D), Block.box(0.0D, 15.0D, 14.0D, 2.0D, 16.0D, 16.0D), Block.box(0.0D, 8.0D, 0.0D, 2.0D, 9.0D, 2.0D), Block.box(14.0D, 8.0D, 0.0D, 16.0D, 9.0D, 2.0D), Block.box(14.0D, 8.0D, 14.0D, 16.0D, 9.0D, 16.0D), Block.box(0.0D, 8.0D, 14.0D, 2.0D, 9.0D, 16.0D));
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    public HerobrineAltar() {
        super(Properties.of(BlockMaterialList.HEROBRINE_ALTER_MATERIAL).strength(1.5F).sound(SoundType.METAL).harvestTool(ToolType.PICKAXE).harvestLevel(0).noOcclusion().requiresCorrectToolForDrops().lightLevel(HerobrineAltar::getLightValue).isValidSpawn(HerobrineAltar::neverAllowSpawn));
        this.registerDefaultState(this.defaultBlockState().setValue(BlockStateProperties.WATERLOGGED, Boolean.FALSE).setValue(ModBlockStates.TYPE, 0));
    }

    private static int getLightValue(BlockState state) {
        int i = state.getValue(ModBlockStates.TYPE);
        switch (i) {
            case 1:
                return 8;
            case 2:
                return 15;
        }
        return 0;
    }

    private static Boolean neverAllowSpawn(BlockState state, IBlockReader reader, BlockPos pos, EntityType<?> entity) {
        return false;
    }

    @NotNull
    @Override
    public VoxelShape getShape(@NotNull BlockState state, @NotNull IBlockReader worldIn, @NotNull BlockPos pos, @NotNull ISelectionContext context) {
        return SHAPE;
    }

    @Override
    protected void createBlockStateDefinition(@NotNull StateContainer.Builder<Block, BlockState> builder) {
        builder.add(BlockStateProperties.WATERLOGGED);
        builder.add(ModBlockStates.TYPE);
    }

    @NotNull
    @Override
    public FluidState getFluidState(@NotNull BlockState state) {
        return state.getValue(BlockStateProperties.WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
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

    @Nullable
    @Override
    public BlockState getStateForPlacement(@NotNull BlockItemUseContext context) {
        BlockPos blockpos = context.getClickedPos();
        FluidState ifluidstate = context.getLevel().getFluidState(blockpos);
        return this.defaultBlockState().setValue(BlockStateProperties.WATERLOGGED, ifluidstate.getType() == Fluids.WATER);
    }

    @Override
    public boolean isPathfindable(@NotNull BlockState state, @NotNull IBlockReader worldIn, @NotNull BlockPos pos, @NotNull PathType type) {
        if (type == PathType.WATER) {
            return worldIn.getFluidState(pos).is(FluidTags.WATER);
        }
        return false;
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void animateTick(@NotNull BlockState stateIn, @NotNull World worldIn, @NotNull BlockPos pos, @NotNull Random rand) {
        int i = stateIn.getValue(ModBlockStates.TYPE);
        if (i == 1) {
            double d0 = (double) pos.getX() + 0.5D + ((double) rand.nextFloat() - 0.5D) * 0.2D;
            double d1 = (double) pos.getY() + 0.05F;
            double d2 = (double) pos.getZ() + 0.5D + ((double) rand.nextFloat() - 0.5D) * 0.2D;
            worldIn.addParticle(ParticleTypes.PORTAL, d0, d1, d2, 0.0D, 0.0D, 0.0D);
        }
    }

    @Override
    public boolean hasAnalogOutputSignal(@NotNull BlockState state) {
        return true;
    }

    @Override
    public int getAnalogOutputSignal(@NotNull BlockState blockState, @NotNull World worldIn, @NotNull BlockPos pos) {
        int i = blockState.getValue(ModBlockStates.TYPE);
        switch (i) {
            case 1:
                return 8;
            case 2:
                return 15;
        }
        return 0;
    }

    private boolean shrineAccepted(@NotNull BlockPos pos, World world) {
        int x = pos.getX();
        int y = pos.getY();
        int z = pos.getZ();
        if(!Config.COMMON.AltarRequiresShrine.get()) {
            return true;
        } else {
            return world.getBlockState(new BlockPos(x, y - 1, z)) == Blocks.NETHERRACK.defaultBlockState() && world.getBlockState(new BlockPos(x, y - 1, z + 1)) == Blocks.GOLD_BLOCK.defaultBlockState() && world.getBlockState(new BlockPos(x, y - 1, z - 1)) == Blocks.GOLD_BLOCK.defaultBlockState() && world.getBlockState(new BlockPos(x + 1, y - 1, z)) == Blocks.GOLD_BLOCK.defaultBlockState() && world.getBlockState(new BlockPos(x - 1, y - 1, z)) == Blocks.GOLD_BLOCK.defaultBlockState() && world.getBlockState(new BlockPos(x + 1, y, z)) == Blocks.REDSTONE_TORCH.defaultBlockState() && world.getBlockState(new BlockPos(x - 1, y, z)) == Blocks.REDSTONE_TORCH.defaultBlockState() && world.getBlockState(new BlockPos(x, y, z + 1)) == Blocks.REDSTONE_TORCH.defaultBlockState() && world.getBlockState(new BlockPos(x, y, z - 1)) == Blocks.REDSTONE_TORCH.defaultBlockState() && world.getBlockState(new BlockPos(x - 1, y - 1, z - 1)) == Blocks.LAVA.defaultBlockState() && world.getBlockState(new BlockPos(x + 1, y - 1, z + 1)) == Blocks.LAVA.defaultBlockState() && world.getBlockState(new BlockPos(x + 1, y - 1, z - 1)) == Blocks.LAVA.defaultBlockState() && world.getBlockState(new BlockPos(x - 1, y - 1, z + 1)) == Blocks.LAVA.defaultBlockState();
        }
    }

    @NotNull
    @Override
    public ActionResultType use(@NotNull BlockState state, @NotNull World world, @NotNull BlockPos pos, @NotNull PlayerEntity player, @NotNull Hand hand, @NotNull BlockRayTraceResult hit) {
        ItemStack itemStack = player.getItemInHand(hand);
        if (this.shrineAccepted(pos, world)) {
            int i = state.getValue(ModBlockStates.TYPE);
            if (i == 0 && itemStack.getItem() == ItemList.cursed_diamond && !Config.COMMON.HerobrineAlwaysSpawns.get() || i == 0 && itemStack.getItem() == ItemList.purified_diamond && !Config.COMMON.HerobrineAlwaysSpawns.get()) {
                if (itemStack.getItem() == ItemList.cursed_diamond) {
                    if (!player.abilities.instabuild) {
                        itemStack.shrink(1);
                    }
                    state = state.getBlockState().setValue(ModBlockStates.TYPE, 1);
                    world.setBlockAndUpdate(pos, state);
                    if (state.getValue(WATERLOGGED)) {
                        world.getLiquidTicks().scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickDelay(world));
                    }
                    if (world instanceof ServerWorld) {
                        LightningBoltEntity lightningboltentity = EntityType.LIGHTNING_BOLT.create(world);
                        assert lightningboltentity != null;
                        lightningboltentity.moveTo(pos.getX(), pos.getY(), pos.getZ());
                        world.addFreshEntity(lightningboltentity);
                    }
                    if (!world.isClientSide) {
                        if (!SaveDataUtil.readBoolean(world, "Spawn")) {
                            SaveDataUtil.writeBoolean(world, "Spawn", true);
                        }
                    }
                    if (Config.COMMON.GlobalHerobrineMessages.get() && !world.isClientSide) {
                        player.sendMessage(new StringTextComponent("<Herobrine> You have no idea what you have done!"), UUID.randomUUID());
                    }

                    if (!Config.COMMON.GlobalHerobrineMessages.get() && world.isClientSide) {
                        player.sendMessage(new StringTextComponent("<Herobrine> You have no idea what you have done!"), UUID.randomUUID());
                    }
                }
                if(itemStack.getItem() == ItemList.purified_diamond) {
                    if (!player.abilities.instabuild) {
                        itemStack.shrink(1);
                    }
                    state = state.getBlockState().setValue(ModBlockStates.TYPE, 2);
                    world.setBlockAndUpdate(pos, state);
                    if (state.getValue(WATERLOGGED)) {
                        world.getLiquidTicks().scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickDelay(world));
                    }
                    if (world instanceof ServerWorld) {
                        LightningBoltEntity lightningboltentity = EntityType.LIGHTNING_BOLT.create(world);
                        assert lightningboltentity != null;
                        lightningboltentity.moveTo(pos.getX(), pos.getY(), pos.getZ());
                        world.addFreshEntity(lightningboltentity);
                    }
                    if (!world.isClientSide) {
                        if (SaveDataUtil.readBoolean(world, "Spawn")) {
                            SaveDataUtil.writeBoolean(world, "Spawn", false);
                        }
                    }

                    if (Config.COMMON.GlobalHerobrineMessages.get() && !world.isClientSide) {
                        player.sendMessage(new StringTextComponent("<Herobrine> I shall return!"), UUID.randomUUID());
                    }

                    if (!Config.COMMON.GlobalHerobrineMessages.get() && world.isClientSide) {
                        player.sendMessage(new StringTextComponent("<Herobrine> I shall return!"), UUID.randomUUID());
                    }
                }
            } else return ActionResultType.FAIL;
        } else return ActionResultType.FAIL;
        return ActionResultType.SUCCESS;
    }
}