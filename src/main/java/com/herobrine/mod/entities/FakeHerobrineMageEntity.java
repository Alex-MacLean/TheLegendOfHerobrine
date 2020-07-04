package com.herobrine.mod.entities;

import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.monster.AbstractIllager;
import net.minecraft.entity.monster.EntityGolem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

public class FakeHerobrineMageEntity extends AbstractHerobrineEntity {
    public FakeHerobrineMageEntity(World worldIn) {
        super(worldIn);
        this.experienceValue = 0;
    }

    private int lifeTimer = 200;

    @Override
    public void initEntityAI() {
        super.initEntityAI();
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.targetTasks.addTask(1, new EntityAINearestAttackableTarget<>(this, AbstractIllager.class, true));
        this.targetTasks.addTask(2, new EntityAINearestAttackableTarget<>(this, EntityPlayer.class, true));
        this.targetTasks.addTask(3, new EntityAINearestAttackableTarget<>(this, AbstractSurvivorEntity.class, true));
        this.targetTasks.addTask(4, new EntityAINearestAttackableTarget<>(this, EntityGolem.class, true));
        this.targetTasks.addTask(5, new EntityAIHurtByTarget(this, false));
        this.tasks.addTask(6, new EntityAIAttackMelee(this, 1.0d, true));
        this.tasks.addTask(7, new EntityAIWanderAvoidWater(this, 1.0d));
        this.tasks.addTask(8, new EntityAIWatchClosest(this, AbstractIllager.class, 8.0f));
        this.tasks.addTask(9, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0f));
        this.tasks.addTask(10, new EntityAIWatchClosest(this, AbstractSurvivorEntity.class, 8.0f));
        this.tasks.addTask(11, new EntityAIWatchClosest(this, EntityGolem.class, 8.0f));
        this.tasks.addTask(12, new EntityAILookIdle(this));
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(25.0D);
        this.getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).setBaseValue(1.0D);
        this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(64.0D);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.6D);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(0.0D);
    }

    @Override
    public void writeEntityToNBT(@NotNull NBTTagCompound compound) {
        super.writeEntityToNBT(compound);
        compound.setInteger("LifeTime", this.lifeTimer);
    }

    @Override
    public void readEntityFromNBT(@NotNull NBTTagCompound compound) {
        super.readEntityFromNBT(compound);
        this.lifeTimer = compound.getInteger("LifeTime");
    }

    @Override
    public void onUpdate() {
        if (this.lifeTimer <= 0) {
            if (this.world.isRemote) {
                if (!this.isSilent()) {
                    this.world.playSound(this.posX + 0.5D, this.posY + 0.5D, this.posZ + 0.5D, SoundEvents.ITEM_FIRECHARGE_USE, this.getSoundCategory(), 1.0F + this.rand.nextFloat(), this.rand.nextFloat() * 0.7F + 0.3F, false);
                }
                for (int i = 0; i < 20; ++i) {
                    double d0 = this.rand.nextGaussian() * 0.02D;
                    double d1 = this.rand.nextGaussian() * 0.02D;
                    double d2 = this.rand.nextGaussian() * 0.02D;
                    this.world.spawnParticle(EnumParticleTypes.EXPLOSION_NORMAL, this.posX + (double)(this.rand.nextFloat() * this.width * 2.0F) - (double)this.width, this.posY + (double)(this.rand.nextFloat() * this.height), this.posZ + (double)(this.rand.nextFloat() * this.width * 2.0F) - (double)this.width, d2, d0, d1);
                }
            }
            this.world.removeEntity(this);
        }
        --this.lifeTimer;
        super.onUpdate();
    }

    @Nullable
    @Override
    protected ResourceLocation getLootTable() {
        return null;
    }
}