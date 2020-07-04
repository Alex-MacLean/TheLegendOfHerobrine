package com.herobrine.mod.blocks;

import com.herobrine.mod.HerobrineMod;
import com.herobrine.mod.util.blocks.ModMaterial;
import net.minecraft.block.Block;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.EnumPushReaction;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;
import org.jetbrains.annotations.NotNull;

public class HerobrineStatue extends Block {
    @GameRegistry.ObjectHolder(HerobrineMod.MODID + ":herobrine_statue")
    public static final Block block = new Block(ModMaterial.HEROBRINE_STATUE_MATERIAL);
    public static final PropertyDirection FACING = BlockHorizontal.FACING;

    public HerobrineStatue() {
        super(ModMaterial.HEROBRINE_STATUE_MATERIAL, MapColor.STONE);
        this.setCreativeTab(CreativeTabs.DECORATIONS);
        this.setRegistryName(HerobrineMod.location("herobrine_statue"));
        this.setTranslationKey(HerobrineMod.MODID + "." + "herobrine_statue");
        this.setHardness(1.5f);
        this.setResistance(1.5f);
        this.setSoundType(SoundType.STONE);
        this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH));
        this.setHarvestLevel("pickaxe", 0);
        this.translucent = true;
    }

    @Override
    public boolean isOpaqueCube(@NotNull IBlockState state) {
        return false;
    }

    @Override
    public boolean isFullCube(@NotNull IBlockState state) {
        return false;
    }

    @Override
    public boolean canSpawnInBlock() {
        return false;
    }

    @Override
    public void onExplosionDestroy(@NotNull World world, @NotNull BlockPos pos, @NotNull Explosion explosion) {
        super.onExplosionDestroy(world, pos, explosion);
        int x = pos.getX();
        int y = pos.getY();
        int z = pos.getZ();
        if (world.getBlockState(new BlockPos(x, y + 1, z)).getMaterial() == ModMaterial.HEROBRINE_STATUE_MATERIAL) {
            world.setBlockState(new BlockPos(x, y + 1, z), Blocks.AIR.getDefaultState(), 3);
        }
    }

    @Override
    public boolean removedByPlayer(@NotNull IBlockState state, @NotNull World world, @NotNull BlockPos pos, @NotNull EntityPlayer entity, boolean willHarvest) {
        int x = pos.getX();
        int y = pos.getY();
        int z = pos.getZ();
        if (world.getBlockState(new BlockPos(x, y + 1, z)).getMaterial() == ModMaterial.HEROBRINE_STATUE_MATERIAL) {
            world.setBlockState(new BlockPos(x, y + 1, z), Blocks.AIR.getDefaultState(), 3);
        }
        return super.removedByPlayer(state, world, pos, entity, willHarvest);
    }

    @Override
    public @NotNull IBlockState getStateForPlacement(@NotNull World worldIn, @NotNull BlockPos pos, @NotNull EnumFacing facing, float hitX, float hitY, float hitZ, int meta, @NotNull EntityLivingBase placer) {
        return this.getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite());
    }

    @Override
    public @NotNull IBlockState getStateFromMeta(int meta) {
        return this.getDefaultState().withProperty(FACING, EnumFacing.byHorizontalIndex(meta));
    }

    @Override
    public int getMetaFromState(@NotNull IBlockState state) {
        return state.getValue(FACING).getHorizontalIndex();
    }

    @Override
    protected @NotNull BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, FACING);
    }

    @Override
    public @NotNull IBlockState withRotation(@NotNull IBlockState state, @NotNull Rotation rot) {
        return state.withProperty(FACING, rot.rotate(state.getValue(FACING)));
    }

    @Override
    public @NotNull IBlockState withMirror(@NotNull IBlockState state, @NotNull Mirror mirrorIn) {
        return state.withRotation(mirrorIn.toRotation(state.getValue(FACING)));
    }

    @Override
    public void onBlockPlacedBy(@NotNull World world, @NotNull BlockPos pos, @NotNull IBlockState state, @NotNull EntityLivingBase placer, @NotNull ItemStack stack) {
        super.onBlockPlacedBy(world, pos, state, placer, stack);
        int x = pos.getX();
        int y = pos.getY();
        int z = pos.getZ();
        world.setBlockState(new BlockPos(x, y + 1, z), HerobrineStatueTop.block.getDefaultState().withProperty(FACING, state.getValue(FACING)));
    }

    @Override
    public @NotNull EnumPushReaction getPushReaction(@NotNull IBlockState state) {
        return EnumPushReaction.BLOCK;
    }
}
