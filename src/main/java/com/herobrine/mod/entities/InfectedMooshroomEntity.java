package com.herobrine.mod.entities;

import com.herobrine.mod.util.loot_tables.LootTableInit;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.common.IShearable;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

public class InfectedMooshroomEntity extends InfectedCowEntity implements IShearable {
    public InfectedMooshroomEntity(World worldIn) {
        super(worldIn);
        this.experienceValue = 3;
    }

    @Override
    public boolean processInteract(@NotNull EntityPlayer player, @NotNull EnumHand hand) {
        ItemStack itemstack = player.getHeldItem(hand);
        if (false && itemstack.getItem() == Items.SHEARS) {
            this.setDead();
            this.world.spawnParticle(EnumParticleTypes.EXPLOSION_LARGE, this.posX, this.posY + (double)(this.height / 2.0F), this.posZ, 0.0D, 0.0D, 0.0D);

            if (!this.world.isRemote) {
                InfectedCowEntity entity = new InfectedCowEntity(this.world);
                entity.setLocationAndAngles(this.posX, this.posY, this.posZ, this.rotationYaw, this.rotationPitch);
                entity.setHealth(this.getHealth());
                entity.renderYawOffset = this.renderYawOffset;

                if (this.hasCustomName()) {
                    entity.setCustomNameTag(this.getCustomNameTag());
                }

                this.world.spawnEntity(entity);

                for (int i = 0; i < 5; ++i) {
                    this.world.spawnEntity(new EntityItem(this.world, this.posX, this.posY + (double)this.height, this.posZ, new ItemStack(Blocks.RED_MUSHROOM)));
                }

                itemstack.damageItem(1, player);
                this.playSound(SoundEvents.ENTITY_MOOSHROOM_SHEAR, 1.0F, 1.0F);
            }

            return true;
        }
        else {
            return super.processInteract(player, hand);
        }
    }

    @Override
    public boolean isShearable(@NotNull ItemStack item, net.minecraft.world.IBlockAccess world, net.minecraft.util.math.BlockPos pos) {
        return true;
    }

    @Override
    public java.util.@NotNull List<ItemStack> onSheared(@NotNull ItemStack item, net.minecraft.world.IBlockAccess world, net.minecraft.util.math.BlockPos pos, int fortune)
    {
        this.setDead();
        ((net.minecraft.world.WorldServer)this.world).spawnParticle(EnumParticleTypes.EXPLOSION_LARGE, false, this.posX, this.posY + (double)(this.height / 2.0F), this.posZ, 1, 0.0D, 0.0D, 0.0D, 0.0D);

        InfectedCowEntity entity = new InfectedCowEntity(this.world);
        entity.setLocationAndAngles(this.posX, this.posY, this.posZ, this.rotationYaw, this.rotationPitch);
        entity.setHealth(this.getHealth());
        entity.renderYawOffset = this.renderYawOffset;

        if (this.hasCustomName())
        {
            entity.setCustomNameTag(this.getCustomNameTag());
        }

        this.world.spawnEntity(entity);

        java.util.List<ItemStack> ret = new java.util.ArrayList<>();
        for (int i = 0; i < 5; ++i)
        {
            ret.add(new ItemStack(Blocks.RED_MUSHROOM));
        }

        this.playSound(SoundEvents.ENTITY_MOOSHROOM_SHEAR, 1.0F, 1.0F);
        return ret;
    }

    @Nullable
    @Override
    protected ResourceLocation getLootTable() {
        return LootTableInit.INFECTED_COW;
    }
}