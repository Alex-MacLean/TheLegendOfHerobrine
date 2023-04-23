package com.herobrinemod.herobrine.entities;

import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityPose;
import net.minecraft.entity.EntityStatuses;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.IllagerEntity;
import net.minecraft.entity.passive.GolemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import org.jetbrains.annotations.NotNull;

public class InfectedWolfEntity extends InfectedEntity {
    private boolean furWet;
    private boolean canShakeWaterOff;
    private float shakeProgress;
    private float lastShakeProgress;
    public InfectedWolfEntity(EntityType<? extends HostileEntity> entityType, World world) {
        super(entityType, world);
        this.experiencePoints = 3;
        this.setConversionEntity(EntityType.WOLF);
    }

    @Override
    protected void initGoals() {
        this.goalSelector.add(0, new SwimGoal(this));
        this.goalSelector.add(1, new MeleeAttackGoal(this, 1.0, false));
        this.goalSelector.add(2, new ActiveTargetGoal<>(this, IllagerEntity.class, false));
        this.goalSelector.add(3, new ActiveTargetGoal<>(this, PlayerEntity.class, false));
        //this.goalSelector.add(4, new ActiveTargetGoal<>(this, SurvivorEntity.class, false));
        this.goalSelector.add(5, new ActiveTargetGoal<>(this, GolemEntity.class, false));
        this.goalSelector.add(6, new WanderAroundFarGoal(this, 1.0));
        this.goalSelector.add(7, new LookAtEntityGoal(this, IllagerEntity.class, 8.0f));
        this.goalSelector.add(8, new LookAtEntityGoal(this, PlayerEntity.class, 8.0f));
        //this.goalSelector.add(9, new LookAtEntityGoal(this, SurvivorEntity.class, 8.0f));
        this.goalSelector.add(10, new LookAtEntityGoal(this, GolemEntity.class, 8.0f));
        this.goalSelector.add(11, new LookAroundGoal(this));
    }

    public static DefaultAttributeContainer.Builder registerAttributes() {
        return createHostileAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 8.0)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 4.0)
                .add(EntityAttributes.GENERIC_FOLLOW_RANGE, 16.0)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.3);
    }

    @Override
    public void tickMovement() {
        super.tickMovement();
        if (!this.world.isClient && this.furWet && !this.canShakeWaterOff && !this.isNavigating() && this.onGround) {
            this.canShakeWaterOff = true;
            this.shakeProgress = 0.0f;
            this.lastShakeProgress = 0.0f;
            this.world.sendEntityStatus(this, EntityStatuses.SHAKE_OFF_WATER);
        }
    }

    @Override
    public void tick() {
        super.tick();
        if (!this.isAlive()) {
            return;
        }
        if (this.isWet()) {
            this.furWet = true;
            if (this.canShakeWaterOff && !this.world.isClient) {
                this.world.sendEntityStatus(this, EntityStatuses.RESET_WOLF_SHAKE);
                this.resetShake();
            }
        } else if ((this.furWet || this.canShakeWaterOff) && this.canShakeWaterOff) {
            if (this.shakeProgress == 0.0f) {
                this.playSound(SoundEvents.ENTITY_WOLF_SHAKE, this.getSoundVolume(), (this.random.nextFloat() - this.random.nextFloat()) * 0.2f + 1.0f);
                this.emitGameEvent(GameEvent.ENTITY_SHAKE);
            }
            this.lastShakeProgress = this.shakeProgress;
            this.shakeProgress += 0.05f;
            if (this.lastShakeProgress >= 2.0f) {
                this.furWet = false;
                this.canShakeWaterOff = false;
                this.lastShakeProgress = 0.0f;
                this.shakeProgress = 0.0f;
            }
            if (this.shakeProgress > 0.4f) {
                float f = (float)this.getY();
                int i = (int)(MathHelper.sin((this.shakeProgress - 0.4f) * (float)Math.PI) * 7.0f);
                Vec3d vec3d = this.getVelocity();
                for (int j = 0; j < i; ++j) {
                    float g = (this.random.nextFloat() * 2.0f - 1.0f) * this.getWidth() * 0.5f;
                    float h = (this.random.nextFloat() * 2.0f - 1.0f) * this.getWidth() * 0.5f;
                    this.world.addParticle(ParticleTypes.SPLASH, this.getX() + (double)g, f + 0.8f, this.getZ() + (double)h, vec3d.x, vec3d.y, vec3d.z);
                }
            }
        }
    }

    private void resetShake() {
        this.canShakeWaterOff = false;
        this.shakeProgress = 0.0f;
        this.lastShakeProgress = 0.0f;
    }

    @Override
    public void onDeath(DamageSource damageSource) {
        this.furWet = false;
        this.canShakeWaterOff = false;
        this.lastShakeProgress = 0.0f;
        this.shakeProgress = 0.0f;
        super.onDeath(damageSource);
    }

    public boolean isFurWet() {
        return this.furWet;
    }

    public float getFurWetBrightnessMultiplier(float tickDelta) {
        return Math.min(0.5f + MathHelper.lerp(tickDelta, this.lastShakeProgress, this.shakeProgress) / 2.0f * 0.5f, 1.0f);
    }

    public float getShakeAnimationProgress(float tickDelta, float f) {
        float g = (MathHelper.lerp(tickDelta, this.lastShakeProgress, this.shakeProgress) + f) / 1.8f;
        if (g < 0.0f) {
            g = 0.0f;
        } else if (g > 1.0f) {
            g = 1.0f;
        }
        return MathHelper.sin(g * (float)Math.PI) * MathHelper.sin(g * (float)Math.PI * 11.0f) * 0.15f * (float)Math.PI;
    }

    @Override
    public void handleStatus(byte status) {
        if (status == EntityStatuses.SHAKE_OFF_WATER) {
            this.canShakeWaterOff = true;
            this.shakeProgress = 0.0f;
            this.lastShakeProgress = 0.0f;
        } else if (status == EntityStatuses.RESET_WOLF_SHAKE) {
            this.resetShake();
        } else {
            super.handleStatus(status);
        }
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.ENTITY_WOLF_AMBIENT;
    }

    @Override
    protected float getActiveEyeHeight(EntityPose pose, @NotNull EntityDimensions dimensions) {
        return dimensions.height * 0.8f;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundEvents.ENTITY_WOLF_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_WOLF_DEATH;
    }

    @Override
    protected void playStepSound(BlockPos pos, BlockState state) {
        this.playSound(SoundEvents.ENTITY_WOLF_STEP, 0.15f, 1.0f);
    }
}
