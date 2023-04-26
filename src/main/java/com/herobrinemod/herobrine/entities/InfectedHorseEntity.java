package com.herobrinemod.herobrine.entities;

import com.herobrinemod.herobrine.entities.goals.InfectedHorseAmbientStandGoal;
import com.herobrinemod.herobrine.items.ItemList;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.IllagerEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.GolemEntity;
import net.minecraft.entity.passive.HorseColor;
import net.minecraft.entity.passive.HorseEntity;
import net.minecraft.entity.passive.HorseMarking;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class InfectedHorseEntity extends InfectedEntity {
    private static final TrackedData<Integer> VARIANT = DataTracker.registerData(InfectedHorseEntity.class, TrackedDataHandlerRegistry.INTEGER);
    private static final int ANGRY_FLAG = 32;
    private static final int EATING_GRASS_FLAG = 16;
    private static final TrackedData<Byte> FLAGS = DataTracker.registerData(InfectedHorseEntity.class, TrackedDataHandlerRegistry.BYTE);
    private int tailWagTicks;
    private int eatingGrassTicks;
    private float eatingGrassAnimationProgress;
    private float lastEatingGrassAnimationProgress;
    private float angryAnimationProgress;
    private float lastAngryAnimationProgress;
    private int angryTicks;

    public InfectedHorseEntity(EntityType<? extends HostileEntity> entityType, World world) {
        super(entityType, world);
        this.experiencePoints = 3;
        this.setStepHeight(1.0f);
        this.setConversionEntity(EntityType.HORSE);
    }

    @Override
    protected void initGoals() {
        this.goalSelector.add(0, new SwimGoal(this));
        this.goalSelector.add(1, new MeleeAttackGoal(this, 1.0, false));
        this.goalSelector.add(2, new ActiveTargetGoal<>(this, IllagerEntity.class, false));
        this.goalSelector.add(3, new ActiveTargetGoal<>(this, PlayerEntity.class, false));
        //this.goalSelector.add(4, new ActiveTargetGoal<>(this, SurvivorEntity.class, false));
        this.goalSelector.add(5, new ActiveTargetGoal<>(this, GolemEntity.class, false));
        this.goalSelector.add(6, new WanderAroundFarGoal(this, 0.3));
        this.goalSelector.add(7, new LookAtEntityGoal(this, IllagerEntity.class, 8.0f));
        this.goalSelector.add(8, new LookAtEntityGoal(this, PlayerEntity.class, 8.0f));
        //this.goalSelector.add(9, new LookAtEntityGoal(this, SurvivorEntity.class, 8.0f));
        this.goalSelector.add(10, new LookAtEntityGoal(this, GolemEntity.class, 8.0f));
        this.goalSelector.add(11, new LookAroundGoal(this));
        this.goalSelector.add(12, new InfectedHorseAmbientStandGoal(this));
    }

    public static DefaultAttributeContainer.Builder registerAttributes() {
        return createHostileAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 30.0)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 4.0)
                .add(EntityAttributes.GENERIC_FOLLOW_RANGE, 16.0)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.4);
    }

    public void convert() {
        this.world.sendEntityStatus(this, (byte) 16);
        this.dropItem(ItemList.CURSED_DUST);
        MobEntity entity = this.convertTo(this.getConversionEntity(), false);
        assert entity != null;
        entity.addStatusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, 300, 1));
        entity.addStatusEffect(new StatusEffectInstance(StatusEffects.HEALTH_BOOST, 300, 1));
        entity.initialize((ServerWorldAccess) world, world.getLocalDifficulty(this.getBlockPos()), SpawnReason.CONVERSION, null, null);
        ((HorseEntity) entity).setHorseVariant(this.getVariant(), this.getMarking());
    }

    @Override
    public boolean isImmobile() {
        return super.isImmobile() || this.isEatingGrass() || this.isAngry();
    }

    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(VARIANT, 0);
        this.dataTracker.startTracking(FLAGS, (byte)0);
    }

    protected boolean getFlag(int bitmask) {
        return (this.dataTracker.get(FLAGS) & bitmask) != 0;
    }

    protected void setFlag(int bitmask, boolean flag) {
        byte b = this.dataTracker.get(FLAGS);
        if (flag) {
            this.dataTracker.set(FLAGS, (byte)(b | bitmask));
        } else {
            this.dataTracker.set(FLAGS, (byte)(b & ~bitmask));
        }
    }

    public boolean isEatingGrass() {
        return this.getFlag(EATING_GRASS_FLAG);
    }

    public boolean isAngry() {
        return this.getFlag(ANGRY_FLAG);
    }

    @Override
    public void tickMovement() {
        if (this.random.nextInt(200) == 0) {
            this.wagTail();
        }
        super.tickMovement();
        if (this.world.isClient || !this.isAlive()) {
            return;
        }
        if (!this.isEatingGrass() && this.random.nextInt(300) == 0 && this.world.getBlockState(this.getBlockPos().down()).isOf(Blocks.GRASS_BLOCK) && this.getTarget() == null) {
            this.setEatingGrass(true);
        }
        if (this.isEatingGrass() && ++this.eatingGrassTicks > 50) {
            this.eatingGrassTicks = 0;
            this.setEatingGrass(false);
        }
    }

    @Override
    public void tick() {
        super.tick();
        if (this.canMoveVoluntarily() && this.angryTicks > 0 && ++this.angryTicks > 20) {
            this.angryTicks = 0;
            this.setAngry(false);
        }
        if (this.tailWagTicks > 0 && ++this.tailWagTicks > 8) {
            this.tailWagTicks = 0;
        }
        this.lastEatingGrassAnimationProgress = this.eatingGrassAnimationProgress;
        if (this.isEatingGrass()) {
            this.eatingGrassAnimationProgress += (1.0f - this.eatingGrassAnimationProgress) * 0.4f + 0.05f;
            if (this.eatingGrassAnimationProgress > 1.0f) {
                this.eatingGrassAnimationProgress = 1.0f;
            }
        } else {
            this.eatingGrassAnimationProgress += (0.0f - this.eatingGrassAnimationProgress) * 0.4f - 0.05f;
            if (this.eatingGrassAnimationProgress < 0.0f) {
                this.eatingGrassAnimationProgress = 0.0f;
            }
        }
        this.lastAngryAnimationProgress = this.angryAnimationProgress;
        if (this.isAngry()) {
            this.lastEatingGrassAnimationProgress = this.eatingGrassAnimationProgress = 0.0f;
            this.angryAnimationProgress += (1.0f - this.angryAnimationProgress) * 0.4f + 0.05f;
            if (this.angryAnimationProgress > 1.0f) {
                this.angryAnimationProgress = 1.0f;
            }
        } else {
            this.jumping = false;
            this.angryAnimationProgress += (0.8f * this.angryAnimationProgress * this.angryAnimationProgress * this.angryAnimationProgress - this.angryAnimationProgress) * 0.6f - 0.05f;
            if (this.angryAnimationProgress < 0.0f) {
                this.angryAnimationProgress = 0.0f;
            }
        }
    }

    public void setEatingGrass(boolean eatingGrass) {
        this.setFlag(EATING_GRASS_FLAG, eatingGrass);
    }

    public float getEatingGrassAnimationProgress(float tickDelta) {
        return MathHelper.lerp(tickDelta, this.lastEatingGrassAnimationProgress, this.eatingGrassAnimationProgress);
    }

    public void setAngry(boolean angry) {
        if (angry) {
            this.setEatingGrass(false);
        }
        this.setFlag(ANGRY_FLAG, angry);
    }

    public void updateAnger() {
        if ( this.canMoveVoluntarily()) {
            this.angryTicks = 1;
            this.setAngry(true);
        }
    }

    private void wagTail() {
        this.tailWagTicks = 1;
    }

    public int getTailWagTicks() {
        return this.tailWagTicks;
    }

    public float getEatingAnimationProgress(float tickDelta) {
        return MathHelper.lerp(tickDelta, 0, 0);
    }

    public float getAngryAnimationProgress(float tickDelta) {
        return MathHelper.lerp(tickDelta, this.lastAngryAnimationProgress, this.angryAnimationProgress);
    }

    @Override
    protected float getActiveEyeHeight(EntityPose pose, @NotNull EntityDimensions dimensions) {
        return dimensions.height * 0.95f;
    }

    @Override
    public boolean handleFallDamage(float fallDistance, float damageMultiplier, DamageSource damageSource) {
        int i;
        if (fallDistance > 1.0f) {
            this.playSound(SoundEvents.ENTITY_HORSE_LAND, 0.4f, 1.0f);
        }
        if ((i = this.computeFallDamage(fallDistance, damageMultiplier)) <= 0) {
            return false;
        }
        this.damage(damageSource, i);
        if (this.hasPassengers()) {
            for (Entity entity : this.getPassengersDeep()) {
                entity.damage(damageSource, i);
            }
        }
        this.playBlockFallSound();
        return true;
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        nbt.putInt("Variant", this.getHorseVariant());
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        this.setHorseVariant(nbt.getInt("Variant"));
    }

    private void setHorseVariant(int variant) {
        this.dataTracker.set(VARIANT, variant);
    }

    private int getHorseVariant() {
        return this.dataTracker.get(VARIANT);
    }

    public HorseColor getVariant() {
        return HorseColor.byId(this.getHorseVariant() & 255);
    }

    private void setHorseVariant(@NotNull HorseColor color, @NotNull HorseMarking marking) {
        this.setHorseVariant(color.getId() & 0xFF | marking.getId() << 8 & 0xFF00);
    }

    public HorseMarking getMarking() {
        return HorseMarking.byIndex((this.getHorseVariant() & 0xFF00) >> 8);
    }

    @Override
    @Nullable
    public EntityData initialize(@NotNull ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, @Nullable EntityData entityData, @Nullable NbtCompound entityNbt) {
        HorseColor horseColor;
        Random random = world.getRandom();
        if (entityData instanceof HorseEntity.HorseData) {
            horseColor = ((HorseEntity.HorseData)entityData).color;
        } else {
            horseColor = Util.getRandom(HorseColor.values(), random);
            entityData = new HorseEntity.HorseData(horseColor);
        }
        this.setHorseVariant(horseColor, Util.getRandom(HorseMarking.values(), random));
        return super.initialize(world, difficulty, spawnReason, entityData, entityNbt);
    }

    @Override
    public int getMinAmbientSoundDelay() {
        return 400;
    }

    public int getMinAmbientStandDelay() {
        return this.getMinAmbientSoundDelay();
    }

    @Override
    protected int computeFallDamage(float fallDistance, float damageMultiplier) {
        return MathHelper.ceil((fallDistance * 0.5f - 3.0f) * damageMultiplier);
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.ENTITY_HORSE_AMBIENT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_HORSE_DEATH;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundEvents.ENTITY_HORSE_HURT;
    }

    @Nullable
    public SoundEvent getAmbientStandSound() {
        return SoundEvents.ENTITY_HORSE_AMBIENT;
    }

    @Override
    protected void playStepSound(BlockPos pos, @NotNull BlockState state) {
        if (state.getMaterial().isLiquid()) {
            return;
        }
        BlockSoundGroup blockSoundGroup = state.getSoundGroup();
        if (blockSoundGroup == BlockSoundGroup.WOOD) {
            this.playSound(SoundEvents.ENTITY_HORSE_STEP_WOOD, blockSoundGroup.getVolume() * 0.15f, blockSoundGroup.getPitch());
        } else {
            this.playSound(SoundEvents.ENTITY_HORSE_STEP, blockSoundGroup.getVolume() * 0.15f, blockSoundGroup.getPitch());
        }
    }
}