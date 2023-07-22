package com.herobrinemod.herobrine.blocks;

import com.herobrinemod.herobrine.items.ItemList;
import com.herobrinemod.herobrine.savedata.ConfigHandler;
import com.herobrinemod.herobrine.savedata.SaveDataHandler;
import net.minecraft.block.*;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LightningEntity;
import net.minecraft.entity.ai.pathing.NavigationType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class HerobrineAltarBlock extends Block implements Waterloggable {
    public static final BooleanProperty WATERLOGGED = Properties.WATERLOGGED;
    public static final IntProperty TYPE = IntProperty.of("type", 0, 2);
    public static final VoxelShape SHAPE = VoxelShapes.union(Block.createCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 8.0D, 16.0D), Block.createCuboidShape(0.0D, 9.0D, 0.0D, 1.0D, 15.0D, 1.0D), Block.createCuboidShape(15.0D, 9.0D, 0.0D, 16.0D, 15.0D, 1.0D), Block.createCuboidShape(15.0D, 9.0D, 15.0D, 16.0D, 15.0D, 16.0D), Block.createCuboidShape(0.0D, 9.0D, 15.0D, 1.0D, 15.0D, 16.0D), Block.createCuboidShape(0.0D, 15.0D, 0.0D, 2.0D, 16.0D, 2.0D), Block.createCuboidShape(14.0D, 15.0D, 0.0D, 16.0D, 16.0D, 2.0D), Block.createCuboidShape(14.0D, 15.0D, 14.0D, 16.0D, 16.0D, 16.0D), Block.createCuboidShape(0.0D, 15.0D, 14.0D, 2.0D, 16.0D, 16.0D), Block.createCuboidShape(0.0D, 8.0D, 0.0D, 2.0D, 9.0D, 2.0D), Block.createCuboidShape(14.0D, 8.0D, 0.0D, 16.0D, 9.0D, 2.0D), Block.createCuboidShape(14.0D, 8.0D, 14.0D, 16.0D, 9.0D, 16.0D), Block.createCuboidShape(0.0D, 8.0D, 14.0D, 2.0D, 9.0D, 16.0D));

    public HerobrineAltarBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.getDefaultState().with(WATERLOGGED, false).with(TYPE, 0));
    }

    @Override
    protected void appendProperties(StateManager.@NotNull Builder<Block, BlockState> builder) {
        builder.add(WATERLOGGED);
        builder.add(TYPE);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }

    @Override
    public void randomDisplayTick(@NotNull BlockState state, World world, BlockPos pos, Random random) {
        switch (state.get(TYPE)) {
            case 1 -> {
                world.addParticle(ParticleTypes.PORTAL, pos.getX() + 0.5 + (random.nextDouble() - 0.5) * 0.2, pos.getY() + 0.25, pos.getZ() + 0.5 + (random.nextDouble() - 0.5) * 0.2, 0.0, 0.0, 0.0);
                world.playSound(pos.getX(), pos.getY(), pos.getZ(), SoundEvents.BLOCK_BEACON_AMBIENT, SoundCategory.BLOCKS, 1.0f, random.nextFloat() * 0.4f + 0.5f, false);
            }
            case 2 -> world.playSound(pos.getX(), pos.getY(), pos.getZ(), SoundEvents.BLOCK_BEACON_AMBIENT, SoundCategory.BLOCKS, 1.0f, 1.25f, false);
        }
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
    public boolean canFillWithFluid(BlockView world, BlockPos pos, @NotNull BlockState state, Fluid fluid) {
        return Waterloggable.super.canFillWithFluid(world, pos, state, fluid);
    }

    @Override
    public BlockState getStateForNeighborUpdate(@NotNull BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        if (state.get(WATERLOGGED)) {
            world.scheduleFluidTick(pos, Fluids.WATER, Fluids.WATER.getTickRate(world));
        }

        return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
    }

    private boolean canActivate(@NotNull World world, @NotNull BlockPos pos) {
        return !ConfigHandler.getHerobrineConfig().readBoolean("AltarRequiresShrine") || world.getBlockState(new BlockPos(pos.getX(), pos.getY() - 1, pos.getZ())) == Blocks.NETHERRACK.getDefaultState() && world.getBlockState(new BlockPos(pos.getX() - 1, pos.getY() - 1, pos.getZ())) == Blocks.GOLD_BLOCK.getDefaultState() && world.getBlockState(new BlockPos(pos.getX() + 1, pos.getY() - 1, pos.getZ())) == Blocks.GOLD_BLOCK.getDefaultState() && world.getBlockState(new BlockPos(pos.getX(), pos.getY() - 1, pos.getZ() - 1)) == Blocks.GOLD_BLOCK.getDefaultState() && world.getBlockState(new BlockPos(pos.getX(), pos.getY() - 1, pos.getZ() + 1)) == Blocks.GOLD_BLOCK.getDefaultState() && world.getBlockState(new BlockPos(pos.getX() - 1, pos.getY(), pos.getZ())) == Blocks.REDSTONE_TORCH.getDefaultState() && world.getBlockState(new BlockPos(pos.getX() + 1, pos.getY(), pos.getZ())) == Blocks.REDSTONE_TORCH.getDefaultState() && world.getBlockState(new BlockPos(pos.getX(), pos.getY(), pos.getZ() + 1)) == Blocks.REDSTONE_TORCH.getDefaultState() && world.getBlockState(new BlockPos(pos.getX(), pos.getY(), pos.getZ() - 1)) == Blocks.REDSTONE_TORCH.getDefaultState() && world.getBlockState(new BlockPos(pos.getX() - 1, pos.getY() - 1, pos.getZ() - 1)) == Blocks.LAVA.getDefaultState() && world.getBlockState(new BlockPos(pos.getX() + 1, pos.getY() - 1, pos.getZ() - 1)) == Blocks.LAVA.getDefaultState() && world.getBlockState(new BlockPos(pos.getX() - 1, pos.getY() - 1, pos.getZ() + 1)) == Blocks.LAVA.getDefaultState() && world.getBlockState(new BlockPos(pos.getX() + 1, pos.getY() - 1, pos.getZ() + 1)) == Blocks.LAVA.getDefaultState();
    }

    @Nullable
    @Override
    public BlockState getPlacementState(@NotNull ItemPlacementContext ctx) {
        BlockPos blockPos = ctx.getBlockPos();
        BlockState blockState = ctx.getWorld().getBlockState(blockPos);
        return this.getDefaultState().with(WATERLOGGED, blockState.getFluidState().getFluid() == Fluids.WATER);
    }

    static int getLightValue(@NotNull BlockState state) {
        return switch (state.get(TYPE)) {
            case 1 -> 8;
            case 2 -> 15;
            default -> 0;
        };
    }

    @Override
    public ActionResult onUse(@NotNull BlockState state, World world, BlockPos pos, @NotNull PlayerEntity player, Hand hand, BlockHitResult hit) {
        ItemStack itemStack = player.getStackInHand(hand);
        if (canActivate(world, pos) && state.get(TYPE) == 0 && itemStack.isOf(ItemList.CURSED_DIAMOND) || canActivate(world, pos) && state.get(TYPE) == 0 &&  itemStack.isOf(ItemList.PURIFIED_DIAMOND)) {
            if(itemStack.isOf(ItemList.CURSED_DIAMOND)) {
                world.setBlockState(pos, this.getDefaultState().with(TYPE, 1));
                if(!player.isCreative()) {
                    itemStack.decrement(1);
                }

                if(!world.isClient) {
                    if(!SaveDataHandler.getHerobrineSaveData().readBoolean("herobrineSummoned")) {
                        if(ConfigHandler.getHerobrineConfig().readBoolean("GlobalHerobrineMessages")) {
                            for(PlayerEntity p: world.getPlayers()) {
                                p.sendMessage(Text.translatable("herobrine.summon"), false);
                            }
                        } else {
                            player.sendMessage(Text.translatable("herobrine.summon"), false);
                        }
                        LightningEntity lightningentity = EntityType.LIGHTNING_BOLT.create(world);
                        assert lightningentity != null;
                        lightningentity.setPos(pos.getX(), pos.getY(), pos.getZ());
                        world.spawnEntity(lightningentity);
                        SaveDataHandler.getHerobrineSaveData().writeBoolean("herobrineSummoned", true);
                    }
                }

            } else {
                world.setBlockState(pos, this.getDefaultState().with(TYPE, 2));
                if(!player.isCreative()) {
                    itemStack.decrement(1);
                }

                if(!world.isClient) {
                    if(SaveDataHandler.getHerobrineSaveData().readBoolean("herobrineSummoned")) {
                        if(ConfigHandler.getHerobrineConfig().readBoolean("GlobalHerobrineMessages")) {
                            for(PlayerEntity p: world.getPlayers()) {
                                p.sendMessage(Text.translatable("herobrine.unsummon"), false);
                            }
                        } else {
                            player.sendMessage(Text.translatable("herobrine.unsummon"), false);
                        }
                        LightningEntity lightningentity = EntityType.LIGHTNING_BOLT.create(world);
                        assert lightningentity != null;
                        lightningentity.setPos(pos.getX(), pos.getY(), pos.getZ());
                        world.spawnEntity(lightningentity);
                        SaveDataHandler.getHerobrineSaveData().writeBoolean("herobrineSummoned", false);
                    }
                }
            }

            if (state.get(WATERLOGGED)) {
                world.scheduleFluidTick(pos, Fluids.WATER, Fluids.WATER.getTickRate(world));
            }
            return ActionResult.success(world.isClient);
        } else {
            switch (state.get(TYPE)) {
                case 1 -> {
                    world.setBlockState(pos, this.getDefaultState().with(TYPE, 0));
                    ItemStack stack = new ItemStack(ItemList.CURSED_DIAMOND);
                    if (!player.getInventory().insertStack(stack) || !stack.isEmpty()) {
                        ItemEntity itemEntity = player.dropItem(stack, false);
                        assert itemEntity != null;
                        itemEntity.resetPickupDelay();
                        itemEntity.setOwner(player.getUuid());
                    }
                    return ActionResult.success(world.isClient);
                }
                case 2 -> {
                    world.setBlockState(pos, this.getDefaultState().with(TYPE, 0));
                    ItemStack stack = new ItemStack(ItemList.PURIFIED_DIAMOND);
                    if (!player.getInventory().insertStack(stack) || !stack.isEmpty()) {
                        ItemEntity itemEntity = player.dropItem(stack, false);
                        assert itemEntity != null;
                        itemEntity.resetPickupDelay();
                        itemEntity.setOwner(player.getUuid());
                    }
                    return ActionResult.success(world.isClient);
                }
            }
        }
        return super.onUse(state, world, pos, player, hand, hit);
    }
}