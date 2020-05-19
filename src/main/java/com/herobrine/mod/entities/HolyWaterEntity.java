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
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class HolyWaterEntity extends EntitySnowball {
    //Required or Minecraft will crash
    @SuppressWarnings("unused")
    public HolyWaterEntity(World worldIn) {
        super(worldIn);
    }

    public HolyWaterEntity(World worldIn, EntityLivingBase throwerIn) {
        super(worldIn, throwerIn);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void handleStatusUpdate(byte id) {
        ItemStack stack = new ItemStack(ItemList.holy_water, 1);
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
                    entity.clearActivePotions();
                    entity.addPotionEffect(new PotionEffect(MobEffects.REGENERATION, 300, 1));
                    entity.addPotionEffect(new PotionEffect(MobEffects.HEALTH_BOOST, 300, 1));
                    entity.extinguish();
                    if (entity instanceof HerobrineWarriorEntity) {
                        entity.attackEntityFrom(DamageSource.causeThrownDamage(this, this.getThrower()),11.0F);
                    }
                    if (entity instanceof HerobrineMageEntity) {
                        entity.attackEntityFrom(DamageSource.causeThrownDamage(this, this.getThrower()),11.0F);
                    }
                    if (entity instanceof HerobrineSpyEntity) {
                        entity.attackEntityFrom(DamageSource.causeThrownDamage(this, this.getThrower()),11.0F);
                    }
                    if (entity instanceof HerobrineBuilderEntity) {
                        entity.attackEntityFrom(DamageSource.causeThrownDamage(this, this.getThrower()),11.0F);
                    }
                    if (entity instanceof FakeHerobrineMageEntity) {
                        entity.world.removeEntity(entity);
                    }
                }
            }
            this.playSound(SoundEvents.ENTITY_SPLASH_POTION_BREAK, 0.8F, 0.9F / (rand.nextFloat() * 0.4F + 0.8F));
            this.world.setEntityState(this, (byte) 3);
            this.setDead();
        }
    }
}