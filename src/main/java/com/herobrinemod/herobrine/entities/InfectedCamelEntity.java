package com.herobrinemod.herobrine.entities;

import com.google.common.annotations.VisibleForTesting;
import com.herobrinemod.herobrine.entities.goals.InfectedCamelSitOrStandGoal;
import net.minecraft.block.BlockState;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.control.BodyControl;
import net.minecraft.entity.ai.control.MoveControl;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.ai.pathing.MobNavigation;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.IllagerEntity;
import net.minecraft.entity.passive.GolemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class InfectedCamelEntity extends InfectedEntity {
    private static final EntityDimensions SITTING_DIMENSIONS = EntityDimensions.changing(EntityTypeList.INFECTED_CAMEL.getWidth(), EntityTypeList.INFECTED_CAMEL.getHeight() - 1.43f);
    public final AnimationState sittingTransitionAnimationState = new AnimationState();
    public final AnimationState sittingAnimationState = new AnimationState();
    public final AnimationState standingTransitionAnimationState = new AnimationState();
    public final AnimationState idlingAnimationState = new AnimationState();
    private int idleAnimationCooldown = 0;
    public static final TrackedData<Long> LAST_POSE_TICK = DataTracker.registerData(InfectedCamelEntity.class, TrackedDataHandlerRegistry.LONG);
    private final WanderAroundFarGoal wanderAroundFarGoal = new WanderAroundFarGoal(this, 2.0);


    public InfectedCamelEntity(EntityType<? extends HostileEntity> entityType, World world) {
        super(entityType, world);
        this.setStepHeight(1.5f);
        this.experiencePoints = 3;
        this.moveControl = new InfectedCamelMoveControl();
        MobNavigation mobNavigation = (MobNavigation)this.getNavigation();
        mobNavigation.setCanSwim(true);
        mobNavigation.setCanWalkOverFences(true);
        this.setConversionEntity(EntityType.CAMEL);
    }

    @Override
    protected void initGoals() {
        this.goalSelector.add(0, new SwimGoal(this));
        this.goalSelector.add(1, new MeleeAttackGoal(this, 3.0, false));
        this.goalSelector.add(2, new ActiveTargetGoal<>(this, IllagerEntity.class, false));
        this.goalSelector.add(3, new ActiveTargetGoal<>(this, PlayerEntity.class, false));
        this.goalSelector.add(4, new ActiveTargetGoal<>(this, SurvivorEntity.class, false));
        this.goalSelector.add(5, new ActiveTargetGoal<>(this, GolemEntity.class, false));
        this.goalSelector.add(7, new LookAtEntityGoal(this, IllagerEntity.class, 8.0f));
        this.goalSelector.add(8, new LookAtEntityGoal(this, PlayerEntity.class, 8.0f));
        this.goalSelector.add(9, new LookAtEntityGoal(this, SurvivorEntity.class, 8.0f));
        this.goalSelector.add(10, new LookAtEntityGoal(this, GolemEntity.class, 8.0f));
        this.goalSelector.add(11, new LookAroundGoal(this));
        this.goalSelector.add(12, new InfectedCamelSitOrStandGoal(this, 20));
    }

    public static DefaultAttributeContainer.Builder registerAttributes() {
        return createHostileAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 32.0)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 4.0)
                .add(EntityAttributes.GENERIC_FOLLOW_RANGE, 16.0)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.09);
    }

    @Override
    public EntityDimensions getDimensions(EntityPose pose) {
        return pose == EntityPose.SITTING ? SITTING_DIMENSIONS.scaled(this.getScaleFactor()) : super.getDimensions(pose);
    }

    @Override
    protected float getActiveEyeHeight(EntityPose pose, @NotNull EntityDimensions dimensions) {
        return dimensions.height - 0.1f * this.getScaleFactor();
    }

    private void updateAnimations() {
        if (this.idleAnimationCooldown <= 0) {
            this.idleAnimationCooldown = this.random.nextInt(40) + 80;
            this.idlingAnimationState.start(this.age);
        } else {
            --this.idleAnimationCooldown;
        }
        if (this.shouldUpdateSittingAnimations()) {
            this.standingTransitionAnimationState.stop();
            if (this.shouldPlaySittingTransitionAnimation()) {
                this.sittingTransitionAnimationState.startIfNotRunning(this.age);
                this.sittingAnimationState.stop();
            } else {
                this.sittingTransitionAnimationState.stop();
                this.sittingAnimationState.startIfNotRunning(this.age);
            }
        } else {
            this.sittingTransitionAnimationState.stop();
            this.sittingAnimationState.stop();
            this.standingTransitionAnimationState.setRunning(this.isChangingPose() && this.getLastPoseTickDelta() >= 0L, this.age);
        }
    }

    @Override
    public float getMovementSpeed() {
        return this.isStationary() ? 0.0f : super.getMovementSpeed();
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        nbt.putLong("LastPoseTick", this.dataTracker.get(LAST_POSE_TICK));
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        long l = nbt.getLong("LastPoseTick");
        if (l < 0L) {
            this.setPose(EntityPose.SITTING);
        }
        this.setLastPoseTick(l);
    }

    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(LAST_POSE_TICK, 0L);
    }

    @Override
    public EntityData initialize(@NotNull ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, @Nullable EntityData entityData, @Nullable NbtCompound entityNbt) {
        this.initLastPoseTick(world.toServerWorld().getTime());
        this.goalSelector.add(6, wanderAroundFarGoal);
        return super.initialize(world, difficulty, spawnReason, entityData, entityNbt);
    }

    @Override
    public void tick() {
        super.tick();
        if (this.getWorld().isClient()) {
            this.updateAnimations();
        }
        if(this.isSitting()) {
            if(this.getTarget() != null) {
                this.startStanding();
            }
            this.goalSelector.remove(wanderAroundFarGoal);
        } else {
            this.goalSelector.add(6, wanderAroundFarGoal);
        }
        if (this.isStationary()) {
            this.clampHeadYaw(this);
        }
        if (this.isSitting() && this.isTouchingWater()) {
            this.setStanding();
        }
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.ENTITY_CAMEL_AMBIENT;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundEvents.ENTITY_CAMEL_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_CAMEL_DEATH;
    }

    public boolean canChangePose() {
        return this.wouldNotSuffocateInPose(this.isSitting() ? EntityPose.STANDING : EntityPose.SITTING);
    }

    @Override
    protected void applyDamage(DamageSource source, float amount) {
        this.setStanding();
        super.applyDamage(source, amount);
    }

    private double getPassengerAttachmentY(float tickDelta, @NotNull EntityDimensions dimensions, float scaleFactor) {
        double d = dimensions.height - 0.375f * scaleFactor;
        float f = scaleFactor * 1.43f;
        float g = f - scaleFactor * 0.2f;
        float h = f - g;
        boolean bl = this.isChangingPose();
        boolean bl2 = this.isSitting();
        if (bl) {
            float k;
            int j;
            int i;
            i = bl2 ? 40 : 52;
            if (bl2) {
                j = 28;
                k = 0.5f;
            } else {
                j = 24;
                k = 0.6f;
            }
            float l = MathHelper.clamp((float)this.getLastPoseTickDelta() + tickDelta, 0.0f, (float)i);
            boolean bl3 = l < (float)j;
            float m = bl3 ? l / (float)j : (l - (float)j) / (float)(i - j);
            float n2 = f - k * g;
            d += bl2 ? (double)MathHelper.lerp(m, bl3 ? f : n2, bl3 ? n2 : h) : (double)MathHelper.lerp(m, bl3 ? h - f : h - n2, bl3 ? h - n2 : 0.0f);
        }
        if (bl2 && !bl) {
            d += h;
        }
        return d;
    }

    @Override
    public Vec3d getLeashOffset(float tickDelta) {
        EntityDimensions entityDimensions = this.getDimensions(this.getPose());
        float f = this.getScaleFactor();
        return new Vec3d(0.0, this.getPassengerAttachmentY(tickDelta, entityDimensions, f) - (double)(0.2f * f), entityDimensions.width * 0.56f);
    }

    private void clampHeadYaw(@NotNull Entity entity) {
        float f = entity.getHeadYaw();
        float g = MathHelper.wrapDegrees(this.bodyYaw - f);
        float h = MathHelper.clamp(MathHelper.wrapDegrees(this.bodyYaw - f), -(float) 30.0, (float) 30.0);
        float i = f + g - h;
        entity.setHeadYaw(i);
    }

    @Override
    public int getMaxHeadRotation() {
        return 30;
    }

    public boolean isSitting() {
        return this.dataTracker.get(LAST_POSE_TICK) < 0L;
    }

    public boolean shouldUpdateSittingAnimations() {
        return this.getLastPoseTickDelta() < 0L != this.isSitting();
    }

    public boolean isChangingPose() {
        long l = this.getLastPoseTickDelta();
        return l < (long)(this.isSitting() ? 40 : 52);
    }

    private boolean shouldPlaySittingTransitionAnimation() {
        return this.isSitting() && this.getLastPoseTickDelta() < 40L && this.getLastPoseTickDelta() >= 0L;
    }

    public void startSitting() {
        if (!this.isSitting()) {
            this.playSound(SoundEvents.ENTITY_CAMEL_SIT, 1.0F, this.getSoundPitch());
            this.setPose(EntityPose.SITTING);
            this.emitGameEvent(GameEvent.ENTITY_ACTION);
            this.setLastPoseTick(-this.getWorld().getTime());
        }
    }

    public void startStanding() {
        if (this.isSitting()) {
            this.playSound(SoundEvents.ENTITY_CAMEL_STAND, 1.0F, this.getSoundPitch());
            this.setPose(EntityPose.STANDING);
            this.emitGameEvent(GameEvent.ENTITY_ACTION);
            this.setLastPoseTick(this.getWorld().getTime());
        }
    }

    public void setStanding() {
        this.setPose(EntityPose.STANDING);
        this.emitGameEvent(GameEvent.ENTITY_ACTION);
        this.initLastPoseTick(this.getWorld().getTime());
    }

    @VisibleForTesting
    public void setLastPoseTick(long lastPoseTick) {
        this.dataTracker.set(LAST_POSE_TICK, lastPoseTick);
    }

    private void initLastPoseTick(long time) {
        this.setLastPoseTick(Math.max(0L, time - 52L - 1L));
    }

    public long getLastPoseTickDelta() {
        return this.getWorld().getTime() - Math.abs(this.dataTracker.get(LAST_POSE_TICK));
    }

    @Override
    protected BodyControl createBodyControl() {
        return new InfectedCamelBodyControl(this);
    }

    @Override
    protected void updateForLeashLength(float leashLength) {
        if (leashLength > 6.0f && this.isSitting() && !this.isChangingPose() && this.canChangePose()) {
            this.startStanding();
        }
    }

    public boolean isStationary() {
        return this.isSitting() || this.isChangingPose();
    }

    @Override
    protected void updateLimbs(float posDelta) {
        float f = this.getPose() == EntityPose.STANDING ? Math.min(posDelta * 6.0f, 1.0f) : 0.0f;
        this.limbAnimator.updateLimbs(f, 0.2f);
    }

    @Override
    protected void playStepSound(BlockPos pos, @NotNull BlockState state) {
        if (state.isIn(BlockTags.CAMEL_SAND_STEP_SOUND_BLOCKS)) {
            this.playSound(SoundEvents.ENTITY_CAMEL_STEP_SAND, 1.0f, 1.0f);
        } else {
            this.playSound(SoundEvents.ENTITY_CAMEL_STEP, 1.0f, 1.0f);
        }
    }

    class InfectedCamelMoveControl extends MoveControl {
        public InfectedCamelMoveControl() {
            super(InfectedCamelEntity.this);
        }

        @Override
        public void tick() {
            if (this.state == MoveControl.State.MOVE_TO && !InfectedCamelEntity.this.isLeashed() && InfectedCamelEntity.this.isSitting() && !InfectedCamelEntity.this.isChangingPose() && InfectedCamelEntity.this.canChangePose()) {
                InfectedCamelEntity.this.startStanding();
            }
            super.tick();
        }
    }

    class InfectedCamelBodyControl extends BodyControl {
        public InfectedCamelBodyControl(InfectedCamelEntity camel) {
            super(camel);
        }

        @Override
        public void tick() {
            if (!InfectedCamelEntity.this.isStationary()) {
                super.tick();
            }
        }
    }
}
