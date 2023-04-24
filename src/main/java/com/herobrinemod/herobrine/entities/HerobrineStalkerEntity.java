package com.herobrinemod.herobrine.entities;

import net.minecraft.entity.EntityData;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.IllagerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.Box;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class HerobrineStalkerEntity extends HerobrineEntity {
    private int lifeTimer;
    private int runAtTargetDelay;
    private int runAtTargetTime;
    private boolean isRunningAtTarget;
    private final MeleeAttackGoal runAtTargetGoal = new MeleeAttackGoal(this, 0.8, true);

    public HerobrineStalkerEntity(EntityType<? extends HostileEntity> entityType, World world) {
        super(entityType, world);
        this.lifeTimer = 6000;
        this.runAtTargetDelay = 500;
        this.runAtTargetTime = 250;
        this.isRunningAtTarget = false;
        this.experiencePoints = 5;
    }

    @Override
    protected void initGoals() {
        this.goalSelector.add(0, new SwimGoal(this));
        this.goalSelector.add(1, new ActiveTargetGoal<>(this, PlayerEntity.class, false));
        this.goalSelector.add(3, new WanderAroundFarGoal(this, 0.4));
        this.goalSelector.add(4, new LookAtEntityGoal(this, IllagerEntity.class, 64.0f));
        this.goalSelector.add(5, new LookAtEntityGoal(this, PlayerEntity.class, 64.0f));
        //this.goalSelector.add(6, new LookAtEntityGoal(this, SurvivorEntity.class, 64.0f));
        this.goalSelector.add(7, new LookAroundGoal(this));
    }

    public static DefaultAttributeContainer.Builder registerAttributes() {
        return createHostileAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 20.0)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 0.0)
                .add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 1.0)
                .add(EntityAttributes.GENERIC_ARMOR, 0.0)
                .add(EntityAttributes.GENERIC_ARMOR_TOUGHNESS, 0.0)
                .add(EntityAttributes.GENERIC_FOLLOW_RANGE, 64.0)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.6);
    }

    @Override
    protected boolean isDisallowedInPeaceful() {
        return false;
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        nbt.putInt("LifeTimer", this.lifeTimer);
        nbt.putInt("RunAtTargetDelay", this.runAtTargetDelay);
        nbt.putInt("RunAtTargetTime", this.runAtTargetTime);
        nbt.putBoolean("RunningAtTarget", this.isRunningAtTarget);
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        this.lifeTimer = nbt.getInt("LifeTimer");
        this.runAtTargetDelay = nbt.getInt("RunAtTargetDelay");
        this.runAtTargetTime = nbt.getInt("RunAtTargetTime");
        this.isRunningAtTarget = nbt.getBoolean("RunningAtTarget");
    }

    @Override
    public void mobTick() {
        if(this.lifeTimer < 1) {
            this.world.sendEntityStatus(this, (byte) 4);
            this.remove(RemovalReason.DISCARDED);
        }
        this.lifeTimer --;

        super.mobTick();

        if (this.runAtTargetDelay > 0 && this.isRunningAtTarget) {
            this.resetAIState();
        }

        if(this.isRunningAtTarget) {
            this.runAtTargetTime --;
        } else {
            this.runAtTargetDelay --;
        }

        if(this.runAtTargetDelay < 1) {
            this.goalSelector.add(2, this.runAtTargetGoal);
            this.isRunningAtTarget = true;
        }

        if (this.runAtTargetTime < 1) {
            this.goalSelector.remove(this.runAtTargetGoal);
            this.resetAIState();
        }

        Box nearBox = this.getBoundingBox().expand(1.0);
        List<LivingEntity> list = this.world.getEntitiesByClass(LivingEntity.class, nearBox, LivingEntity::isAlive);
        if (!list.isEmpty()) {
            for (LivingEntity entity : list) {
                if (entity instanceof PlayerEntity && this.getTarget() == entity && this.isRunningAtTarget) {
                    entity.addStatusEffect(new StatusEffectInstance(StatusEffects.NAUSEA, 300, 0, false, false));
                    entity.addStatusEffect(new StatusEffectInstance(StatusEffects.DARKNESS, 600, 0, false, false));
                    entity.addStatusEffect(new StatusEffectInstance(StatusEffects.BLINDNESS, 400, 0, false, false));
                    this.world.sendEntityStatus(this, (byte) 5);
                    this.remove(RemovalReason.DISCARDED);
                }
            }
        }
    }

    @Override
    public void handleStatus(byte status) {
        super.handleStatus(status);
        switch (status) {
            case 4 -> {
                if (this.world.isClient) {
                    if (!this.isSilent()) {
                        this.world.playSound(this.getX(), this.getEyeY(), this.getZ(), SoundEvents.ITEM_FIRECHARGE_USE, this.getSoundCategory(), 1.0f, (random.nextFloat() - random.nextFloat()) * 0.2f + 1.0f, false);
                    }

                    for (int i = 0; i < 20; i++) {
                        this.world.addParticle(ParticleTypes.POOF, this.getParticleX(1.0), this.getRandomBodyY(), this.getParticleZ(1.0), random.nextGaussian() * 0.02, random.nextGaussian() * 0.02, random.nextGaussian() * 0.02);
                    }
                }
            }
            case 5 -> {
                if (this.world.isClient) {
                    if (!this.isSilent()) {
                        this.world.playSound(this.getX(), this.getEyeY(), this.getZ(), SoundEvents.AMBIENT_CAVE.value(), this.getSoundCategory(), 1.0f, (random.nextFloat() - random.nextFloat()) * 0.2f + 1.0f, false);
                    }

                    for (int i = 0; i < 20; i++) {
                        this.world.addParticle(ParticleTypes.POOF, this.getParticleX(1.0), this.getRandomBodyY(), this.getParticleZ(1.0), random.nextGaussian() * 0.02, random.nextGaussian() * 0.02, random.nextGaussian() * 0.02);
                    }
                }
            }
        }
    }

    private void resetAIState() {
        this.isRunningAtTarget = false;
        this.runAtTargetDelay = 500;
        this.runAtTargetTime = 250;
    }

    @Override
    public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, @Nullable EntityData entityData, @Nullable NbtCompound entityNbt) {
        this.setPersistent();
        return super.initialize(world, difficulty, spawnReason, entityData, entityNbt);
    }
}
