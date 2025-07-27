package com.herobrinemod.herobrine.entities;

import com.herobrinemod.herobrine.items.ItemList;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
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
import net.minecraft.entity.passive.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import net.minecraft.world.WorldEvents;
import net.minecraft.world.biome.Biome;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class InfectedFoxEntity extends InfectedEntity implements VariantHolder<FoxEntity.Type> {
    private static final TrackedData<Integer> TYPE = DataTracker.registerData(InfectedFoxEntity.class, TrackedDataHandlerRegistry.INTEGER);
    private static final TrackedData<Byte> FOX_FLAGS = DataTracker.registerData(InfectedFoxEntity.class, TrackedDataHandlerRegistry.BYTE);
    private static final int WALKING_FLAG = 64;
    public static final int ROLLING_HEAD_FLAG = 8;
    private float headRollProgress;
    private float lastHeadRollProgress;
    float extraRollingHeight;
    float lastExtraRollingHeight;
    public InfectedFoxEntity(EntityType<? extends HostileEntity> entityType, World world) {
        super(entityType, world);
        this.experiencePoints = 3;
        this.setConversionEntity(EntityType.FOX);
    }

    @Override
    protected void initGoals() {
        this.goalSelector.add(0, new SwimGoal(this));
        this.goalSelector.add(1, new MeleeAttackGoal(this, 1.0, false));
        this.goalSelector.add(2, new ActiveTargetGoal<>(this, AnimalEntity.class, 10, false, false, entity -> entity instanceof ChickenEntity || entity instanceof RabbitEntity));
        this.goalSelector.add(2, new ActiveTargetGoal<>(this, FishEntity.class, 20, false, false, entity -> entity instanceof SchoolingFishEntity));
        this.goalSelector.add(2, new ActiveTargetGoal<>(this, TurtleEntity.class, 10, false, false, TurtleEntity.BABY_TURTLE_ON_LAND_FILTER));
        this.goalSelector.add(3, new ActiveTargetGoal<>(this, IllagerEntity.class, false));
        this.goalSelector.add(4, new ActiveTargetGoal<>(this, PlayerEntity.class, false));
        this.goalSelector.add(5, new ActiveTargetGoal<>(this, SurvivorEntity.class, false));
        this.goalSelector.add(6, new ActiveTargetGoal<>(this, GolemEntity.class, false));
        this.goalSelector.add(7, new WanderAroundFarGoal(this, 1.0));
        this.goalSelector.add(8, new LookAtEntityGoal(this, IllagerEntity.class, 8.0f));
        this.goalSelector.add(9, new LookAtEntityGoal(this, PlayerEntity.class, 8.0f));
        this.goalSelector.add(10, new LookAtEntityGoal(this, SurvivorEntity.class, 8.0f));
        this.goalSelector.add(11, new LookAtEntityGoal(this, GolemEntity.class, 8.0f));
        this.goalSelector.add(12, new LookAroundGoal(this));
    }

    public static DefaultAttributeContainer.Builder registerAttributes() {
        return createHostileAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 10.0)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 2.0)
                .add(EntityAttributes.GENERIC_FOLLOW_RANGE, 32.0)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.3);
    }

    @Nullable
    @Override
    public EntityData initialize(@NotNull ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, @Nullable EntityData entityData, NbtCompound entityNbt) {
        RegistryEntry<Biome> registryEntry = world.getBiome(this.getBlockPos());
        FoxEntity.Type type = FoxEntity.Type.fromBiome(registryEntry);
        if (entityData instanceof FoxEntity.FoxData foxData) {
            type = foxData.type;
        } else {
            entityData = new FoxEntity.FoxData(type);
        }

        this.setVariant(type);

        this.initEquipment(world.getRandom(), difficulty);
        return super.initialize(world, difficulty, spawnReason, entityData, entityNbt);
    }

    @Override
    public void convert() {
        this.getWorld().sendEntityStatus(this, (byte) 16);
        this.dropItem(ItemList.CURSED_DUST);
        MobEntity entity = this.convertTo(this.getConversionEntity(), false);
        assert entity != null;
        entity.addStatusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, 300, 1));
        entity.addStatusEffect(new StatusEffectInstance(StatusEffects.HEALTH_BOOST, 300, 1));
        entity.initialize((ServerWorldAccess) getWorld(), getWorld().getLocalDifficulty(this.getBlockPos()), SpawnReason.CONVERSION, null, null);
        entity.setStackInHand(Hand.MAIN_HAND, ItemStack.EMPTY);
        ((FoxEntity) entity).setVariant(this.getVariant());
    }

    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(TYPE, 0);
        this.dataTracker.startTracking(FOX_FLAGS, (byte)0);
    }

    @Override
    public void tickMovement() {
        if (!this.getWorld().isClient && this.isAlive() && this.canMoveVoluntarily()) {
            LivingEntity livingEntity = this.getTarget();
            if (livingEntity == null || !livingEntity.isAlive()) {
                this.setRollingHead(false);
            }
        }

        super.tickMovement();
    }

    @Override
    public void tick() {
        super.tick();
        if (this.canMoveVoluntarily()) {
            if (this.isWalking() && this.getWorld().random.nextFloat() < 0.2f) {
                BlockPos blockPos = this.getBlockPos();
                BlockState blockState = this.getWorld().getBlockState(blockPos);
                this.getWorld().syncWorldEvent(WorldEvents.BLOCK_BROKEN, blockPos, Block.getRawIdFromState(blockState));
            }
        }

        this.lastHeadRollProgress = this.headRollProgress;
        if (this.isRollingHead()) {
            this.headRollProgress = this.headRollProgress + (1.0F - this.headRollProgress) * 0.4f;
        } else {
            this.headRollProgress = this.headRollProgress + (0.0F - this.headRollProgress) * 0.4f;
        }

        this.lastExtraRollingHeight = this.extraRollingHeight;
        if (this.isInSneakingPose()) {
            this.extraRollingHeight += 0.2f;
            if (this.extraRollingHeight > 3.0f) {
                this.extraRollingHeight = 3.0f;
            }
        } else {
            this.extraRollingHeight = 0.0f;
        }
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        nbt.putString("Type", this.getVariant().asString());
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        this.setVariant(FoxEntity.Type.byName(nbt.getString("Type")));
    }

    public void setRollingHead(boolean rollingHead) {
        this.setFoxFlag(rollingHead);
    }

    public boolean isRollingHead() {
        return this.getFoxFlag(ROLLING_HEAD_FLAG);
    }

    public boolean isWalking() {
        return this.getFoxFlag(WALKING_FLAG);
    }

    private void setFoxFlag(boolean value) {
        if (value) {
            this.dataTracker.set(FOX_FLAGS, (byte)(this.dataTracker.get(FOX_FLAGS) | InfectedFoxEntity.ROLLING_HEAD_FLAG));
        } else {
            this.dataTracker.set(FOX_FLAGS, (byte)(this.dataTracker.get(FOX_FLAGS) & ~InfectedFoxEntity.ROLLING_HEAD_FLAG));
        }
    }

    private boolean getFoxFlag(int bitmask) {
        return (this.dataTracker.get(FOX_FLAGS) & bitmask) != 0;
    }

    public float getHeadRoll(float tickDelta) {
        return MathHelper.lerp(tickDelta, this.lastHeadRollProgress, this.headRollProgress) * 0.11F * (float) Math.PI;
    }

    @Override
    public void playAmbientSound() {
        SoundEvent soundEvent = this.getAmbientSound();
        if (soundEvent == SoundEvents.ENTITY_FOX_SCREECH) {
            this.playSound(soundEvent, 2.0F, this.getSoundPitch());
        } else {
            super.playAmbientSound();
        }
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        if (!this.getWorld().isDay() && this.random.nextFloat() < 0.1F) {
            List<PlayerEntity> list = this.getWorld().getEntitiesByClass(PlayerEntity.class, this.getBoundingBox().expand(16.0, 16.0, 16.0), EntityPredicates.EXCEPT_SPECTATOR);
            if (list.isEmpty()) {
                return SoundEvents.ENTITY_FOX_SCREECH;
            }
        }
        return SoundEvents.ENTITY_FOX_AMBIENT;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundEvents.ENTITY_FOX_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_FOX_DEATH;
    }

    public FoxEntity.Type getVariant() {
        return FoxEntity.Type.fromId(this.dataTracker.get(TYPE));
    }

    public void setVariant(FoxEntity.@NotNull Type type) {
        this.dataTracker.set(TYPE, type.getId());
    }
}
