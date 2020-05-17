package com.herobrine.mod.entities;

import com.herobrine.mod.util.items.ItemList;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntitySnowball;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class UnholyWaterEntity extends EntitySnowball {
    public UnholyWaterEntity(World worldIn) {
        super(worldIn);
    }

    public UnholyWaterEntity(World worldIn, EntityLivingBase throwerIn) {
        super(worldIn, throwerIn);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void handleStatusUpdate(byte id) {
        ItemStack stack = new ItemStack(ItemList.unholy_water, 1);
        if (id == 3) {
            for (int i = 0; i < 8; ++i) {
                this.world.spawnAlwaysVisibleParticle(EnumParticleTypes.ITEM_CRACK.getParticleID(), this.posX, this.posY, this.posZ, 0.0D, 0.0D, 0.0D, Item.getIdFromItem(stack.getItem()), stack.getMetadata());
            }
        }
    }

    @Override
    protected void onImpact(@NotNull RayTraceResult result) {
        if (!this.world.isRemote) {
            AxisAlignedBB axisalignedbb = this.getEntityBoundingBox().grow(1.0D, 1.0D, 1.0D);
            List<EntityLivingBase> list = this.world.getEntitiesWithinAABB(EntityLivingBase.class, axisalignedbb);
            if (!list.isEmpty()) {
                for (EntityLivingBase entity : list) {
                    entity.addPotionEffect(new PotionEffect(MobEffects.WITHER, 300, 1));
                    entity.addPotionEffect(new PotionEffect(MobEffects.HUNGER, 300, 1));
                    entity.setFire(15);
                    entity.attackEntityFrom(DamageSource.causeThrownDamage(this, this.getThrower()),4.0F);
                }
            }

            this.playSound(SoundEvents.ENTITY_SPLASH_POTION_BREAK, 0.8F, 0.9F / (rand.nextFloat() * 0.4F + 0.8F));
            this.world.setEntityState(this, (byte) 3);
            this.setDead();
        }
    }
}
