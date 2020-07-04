package com.herobrine.mod.entities;

import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAvoidEntity;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

public class HerobrineSpyEntity extends AbstractHerobrineEntity {
    public HerobrineSpyEntity(World worldIn) {
        super(worldIn);
        this.setSize(0.6F, 1.95F);
        this.experienceValue = 5;
    }

    private int lifeTimer = 6000;

    @Override
    protected void initEntityAI() {
        super.initEntityAI();
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(1, new EntityAIWatchClosest(this, EntityPlayer.class, 1024));
        this.tasks.addTask(2, new EntityAIAvoidEntity<>(this, EntityPlayer.class, 48.0F, 1.0D, 1.2D));
        this.tasks.addTask(3, new EntityAIWatchClosest(this, AbstractSurvivorEntity.class, 1024));
        this.tasks.addTask(4, new EntityAIAvoidEntity<>(this, AbstractSurvivorEntity.class, 48.0F, 1.0D, 1.2D));
        this.tasks.addTask(5, new EntityAILookIdle(this));
    }

    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(20.0D);
        this.getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).setBaseValue(1.0D);
        this.getEntityAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(2.0D);
        this.getEntityAttribute(SharedMonsterAttributes.ARMOR_TOUGHNESS).setBaseValue(2.0D);
        this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(48.0D);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.6D);
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
        super.onUpdate();
        if (!this.world.isRemote && this.world.getDifficulty() == EnumDifficulty.PEACEFUL) {
            this.clearActivePotions();
        }
        --this.lifeTimer;
        if(this.lifeTimer > 6000) {
            this.lifeTimer = 6000;
        }
        if(this.lifeTimer <= 0) {
            this.world.removeEntity(this);
        }
    }

    @Override
    public IEntityLivingData onInitialSpawn(@NotNull DifficultyInstance difficulty, @Nullable IEntityLivingData livingdata) {
        this.enablePersistence();
        return super.onInitialSpawn(difficulty, livingdata);
    }
}