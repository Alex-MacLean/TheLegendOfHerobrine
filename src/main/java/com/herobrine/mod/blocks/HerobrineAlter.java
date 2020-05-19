package com.herobrine.mod.blocks;

import com.herobrine.mod.HerobrineMod;
import com.herobrine.mod.util.blocks.ModBlockStates;
import com.herobrine.mod.util.items.ItemList;
import com.herobrine.mod.util.savedata.Variables;
import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.jetbrains.annotations.NotNull;

import java.util.Random;

import static com.herobrine.mod.HerobrineMod.registryEvents.HEROBRINE_ALTER_MATERIAL;

public class HerobrineAlter extends Block {
    @GameRegistry.ObjectHolder(HerobrineMod.MODID + ":herobrine_alter")
    public static final Block block = new Block(HEROBRINE_ALTER_MATERIAL);

    public HerobrineAlter() {
        super(HEROBRINE_ALTER_MATERIAL, MapColor.RED);
        this.setCreativeTab(CreativeTabs.DECORATIONS);
        this.setRegistryName(HerobrineMod.location("herobrine_alter"));
        this.setTranslationKey(HerobrineMod.MODID + "." + "herobrine_alter");
        this.setHardness(1.5f);
        this.setResistance(1.5f);
        this.setTickRandomly(true);
        this.setDefaultState(this.blockState.getBaseState().withProperty(ModBlockStates.ACTIVE, Boolean.FALSE));
    }

    @Override
    public boolean isOpaqueCube(@NotNull IBlockState state) {
        return false;
    }

    @Override
    public @NotNull IBlockState getStateForPlacement(@NotNull World worldIn, @NotNull BlockPos pos, @NotNull EnumFacing facing, float hitX, float hitY, float hitZ, int meta, @NotNull EntityLivingBase placer) {
        return this.getDefaultState().withProperty(ModBlockStates.ACTIVE, Boolean.FALSE);
    }

    @Override
    public boolean hasComparatorInputOverride(@NotNull IBlockState state) {
        return true;
    }

    @Override
    public int getComparatorInputOverride(@NotNull IBlockState blockState, @NotNull World worldIn, @NotNull BlockPos pos) {
        return blockState.getValue(ModBlockStates.ACTIVE) ? 15 : 0;
    }

    @Override
    public @NotNull IBlockState getStateFromMeta(int meta) {
        return this.getDefaultState().withProperty(ModBlockStates.ACTIVE, (meta & 4) != 0);
    }

    @Override
    public int getMetaFromState(@NotNull IBlockState state) {
        int i = 0;

        if (state.getValue(ModBlockStates.ACTIVE)) {
            i |= 4;
        }

        return i;
    }

    @Override
    protected @NotNull BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, ModBlockStates.ACTIVE);
    }

    @Override
    public boolean isFullCube(@NotNull IBlockState state) {
        return false;
    }

    @Override
    public int getLightValue(@NotNull IBlockState state) {
        boolean i = state.getValue(ModBlockStates.ACTIVE);
        if (i == Boolean.TRUE) {
            return 8;
        } else {
            return 0;
        }
    }

    @Override
    public boolean canSpawnInBlock() {
        return false;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void randomDisplayTick(@NotNull IBlockState stateIn, @NotNull World worldIn, @NotNull BlockPos pos, @NotNull Random rand) {
        boolean i = stateIn.getValue(ModBlockStates.ACTIVE);
        if (i == Boolean.TRUE) {
            double d0 = (double) pos.getX() + 0.5D + ((double) rand.nextFloat() - 0.5D) * 0.2D;
            double d1 = (double) pos.getY() + 0.05F;
            double d2 = (double) pos.getZ() + 0.5D + ((double) rand.nextFloat() - 0.5D) * 0.2D;
            worldIn.spawnParticle(EnumParticleTypes.PORTAL, d0, d1, d2, 0.0D, 0.0D, 0.0D);
        }
    }

    @Override
    public boolean onBlockActivated(@NotNull World world, @NotNull BlockPos pos, @NotNull IBlockState state, @NotNull EntityPlayer entity, @NotNull EnumHand hand, @NotNull EnumFacing facing, float hitX, float hitY, float hitZ) {
        boolean i = state.getValue(ModBlockStates.ACTIVE);
        if (i == Boolean.FALSE) {
            int x = pos.getX();
            int y = pos.getY();
            int z = pos.getZ();
            ItemStack itemstack = entity.getHeldItem(hand);
            if (itemstack.getItem() == ItemList.cursed_diamond) {
                assert false;
                world.setBlockState(new BlockPos(x, y, z), this.getDefaultState().withProperty(ModBlockStates.ACTIVE, true), 4);
                if (entity instanceof EntityPlayer && !entity.isCreative()) {
                    itemstack.shrink(1);
                }
                entity.swingArm(EnumHand.MAIN_HAND);
                world.addWeatherEffect(new EntityLightningBolt(world, x, y, z, false));
                if ((!(Variables.WorldVariables.get(world).Spawn))) {
                    Variables.WorldVariables.get(world).Spawn = true;
                    Variables.WorldVariables.get(world).syncData(world);
                    if(world.isRemote) {
                        entity.sendMessage(new TextComponentString("<Herobrine> You have no idea what you have done!"));
                    }
                }

            }
        }
        return super.onBlockActivated(world, pos, state, entity, hand, facing, hitX, hitY, hitZ);
    }
}