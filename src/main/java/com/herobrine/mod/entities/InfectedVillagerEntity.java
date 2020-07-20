package com.herobrine.mod.entities;

import com.herobrine.mod.config.Config;
import com.herobrine.mod.util.savedata.Variables;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.monster.EntityGolem;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.storage.loot.LootTableList;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

public class InfectedVillagerEntity extends AbstractInfectedEntity {
    public InfectedVillagerEntity(World worldIn) {
        super(worldIn);
        this.experienceValue = 3;
        this.setSize(0.6f, 1.95f);
    }

    @Override
    public boolean attackEntityFrom(@NotNull DamageSource source, float amount) {
        if (source.getImmediateSource() instanceof HolyWaterEntity) {
            EntityVillager villagerEntity = new EntityVillager(this.world);
            villagerEntity.copyLocationAndAnglesFrom(this);
            villagerEntity.onInitialSpawn(this.world.getDifficultyForLocation(new BlockPos(villagerEntity)), null);
            villagerEntity.setNoAI(this.isAIDisabled());
            if (this.hasCustomName()) {
                villagerEntity.setCustomNameTag(this.getCustomNameTag());
                villagerEntity.setAlwaysRenderNameTag(this.getAlwaysRenderNameTag());
            }
            villagerEntity.enablePersistence();
            villagerEntity.setProfession(0);
            villagerEntity.setGrowingAge(0);
            this.world.setEntityState(this, (byte)16);
            this.world.spawnEntity(villagerEntity);
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
        this.tasks.addTask(5, new EntityAIAttackMelee(this, 0.8d, true));
        this.tasks.addTask(6, new EntityAIWanderAvoidWater(this, 0.6d));
        this.tasks.addTask(7, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0f));
        this.tasks.addTask(8, new EntityAIWatchClosest(this, AbstractSurvivorEntity.class, 8.0f));
        this.tasks.addTask(9, new EntityAIWatchClosest(this, EntityGolem.class, 8.0f));
        this.tasks.addTask(10, new EntityAILookIdle(this));
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(20.0D);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(2.0D);
        this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(48.0D);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(1.0D);
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.ENTITY_VILLAGER_AMBIENT;
    }

    @Override
    protected SoundEvent getHurtSound(@NotNull DamageSource damageSourceIn) {
        return SoundEvents.ENTITY_VILLAGER_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_VILLAGER_DEATH;
    }

    @Nullable
    @Override
    protected ResourceLocation getLootTable() {
        return LootTableList.ENTITIES_VILLAGER;
    }

    public boolean hasViewOfSky() {
        return world.canSeeSky(new BlockPos(this.posX, this.getEntityBoundingBox().minY, this.posZ));
    }

    @Override
    public boolean getCanSpawnHere() {
        return super.getCanSpawnHere() && hasViewOfSky() && Variables.SaveData.get(world).Spawn || super.getCanSpawnHere() && hasViewOfSky() && Config.HerobrineAlwaysSpawns;
    }
}
