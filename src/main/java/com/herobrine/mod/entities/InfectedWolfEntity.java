package com.herobrine.mod.entities;

import com.herobrine.mod.config.Config;
import com.herobrine.mod.util.loot_tables.LootTableInit;
import com.herobrine.mod.util.savedata.Variables;
import net.minecraft.block.Block;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.monster.EntityGolem;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

public class InfectedWolfEntity extends AbstractInfectedEntity {
    public InfectedWolfEntity(World worldIn) {
        super(worldIn);
        this.experienceValue = 3;
        this.setSize(0.6F, 0.85F);
    }

    @Override
    public boolean attackEntityFrom(@NotNull DamageSource source, float amount) {
        if (source.getImmediateSource() instanceof HolyWaterEntity) {
            EntityWolf wolfEntity = new EntityWolf(this.world);
            wolfEntity.copyLocationAndAnglesFrom(this);
            wolfEntity.onInitialSpawn(this.world.getDifficultyForLocation(new BlockPos(wolfEntity)), null);
            wolfEntity.setNoAI(this.isAIDisabled());
            if (this.hasCustomName()) {
                wolfEntity.setCustomNameTag(this.getCustomNameTag());
                wolfEntity.setAlwaysRenderNameTag(this.getAlwaysRenderNameTag());
            }
            wolfEntity.enablePersistence();
            wolfEntity.setGrowingAge(0);
            this.world.setEntityState(this, (byte)16);
            this.world.spawnEntity(wolfEntity);
            this.world.removeEntity(this);
        }
        return super.attackEntityFrom(source, amount);
    }

    @Override
    public void initEntityAI() {
        super.initEntityAI();
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.targetTasks.addTask(1, new EntityAINearestAttackableTarget<>(this, EntityPlayer.class, true));
        this.targetTasks.addTask(2, new EntityAINearestAttackableTarget<>(this, AbstractSurvivorEntity.class, true));
        this.targetTasks.addTask(3, new EntityAINearestAttackableTarget<>(this, EntityGolem.class, true));
        this.targetTasks.addTask(4, new EntityAIHurtByTarget(this, false));
        this.tasks.addTask(5, new EntityAIAttackMelee(this, 1.0d, true));
        this.tasks.addTask(6, new EntityAIWanderAvoidWater(this, 1.0d));
        this.tasks.addTask(7, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0f));
        this.tasks.addTask(8, new EntityAIWatchClosest(this, AbstractSurvivorEntity.class, 8.0f));
        this.tasks.addTask(9, new EntityAIWatchClosest(this, EntityGolem.class, 8.0f));
        this.tasks.addTask(10, new EntityAILookIdle(this));
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(8.0D);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(4.0D);
        this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(16.0D);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.3D);
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.ENTITY_WOLF_AMBIENT;
    }

    @Override
    protected SoundEvent getHurtSound(@NotNull DamageSource damageSourceIn) {
        return SoundEvents.ENTITY_WOLF_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_WOLF_DEATH;
    }

    @Override
    protected void playStepSound(@NotNull BlockPos pos, @NotNull Block blockIn) {
        this.playSound(SoundEvents.ENTITY_WOLF_STEP, 0.15F, 1.0F);
    }

    @Nullable
    @Override
    protected ResourceLocation getLootTable() {
        return LootTableInit.INFECTED_WOLF;
    }

    public boolean hasViewOfSky() {
        return world.canSeeSky(new BlockPos(this.posX, this.getEntityBoundingBox().minY, this.posZ));
    }

    @Override
    public boolean getCanSpawnHere() {
        return super.getCanSpawnHere() && hasViewOfSky() && Variables.SaveData.get(world).Spawn || super.getCanSpawnHere() && hasViewOfSky() && Config.HerobrineAlwaysSpawns;
    }

    @SideOnly(Side.CLIENT)
    public float getTailRotation() {
        return ((float)Math.PI / 5F);
    }
}
