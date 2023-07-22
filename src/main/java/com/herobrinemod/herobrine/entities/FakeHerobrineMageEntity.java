package com.herobrinemod.herobrine.entities;

import net.minecraft.entity.EntityData;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.IllagerEntity;
import net.minecraft.entity.passive.GolemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;
import java.util.UUID;

public class FakeHerobrineMageEntity extends HerobrineEntity {
    private int lifeTimer;
    private UUID originalUUID;

    public FakeHerobrineMageEntity(EntityType<? extends HostileEntity> entityType, World world) {
        super(entityType, world);
        this.lifeTimer = 200;
        this.experiencePoints = 0;
    }

    @Override
    protected void initGoals() {
        this.goalSelector.add(0, new SwimGoal(this));
        this.goalSelector.add(1, new MeleeAttackGoal(this, 0.6, false));
        this.goalSelector.add(2, new ActiveTargetGoal<>(this, IllagerEntity.class, false));
        this.goalSelector.add(3, new ActiveTargetGoal<>(this, PlayerEntity.class, false));
        this.goalSelector.add(4, new ActiveTargetGoal<>(this, SurvivorEntity.class, false));
        this.goalSelector.add(5, new ActiveTargetGoal<>(this, GolemEntity.class, false));
        this.goalSelector.add(6, new WanderAroundFarGoal(this, 0.4));
        this.goalSelector.add(7, new LookAtEntityGoal(this, IllagerEntity.class, 0.8f));
        this.goalSelector.add(8, new LookAtEntityGoal(this, PlayerEntity.class, 0.8f));
        this.goalSelector.add(9, new LookAtEntityGoal(this, SurvivorEntity.class, 0.8f));
        this.goalSelector.add(10, new LookAtEntityGoal(this, GolemEntity.class, 0.8f));
        this.goalSelector.add(11, new LookAroundGoal(this));
    }

    public static DefaultAttributeContainer.Builder registerAttributes() {
        return createHostileAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 10.0)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 0.0)
                .add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 1.0)
                .add(EntityAttributes.GENERIC_FOLLOW_RANGE, 64.0)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.6);
    }

    public void setOriginal(@NotNull HerobrineMageEntity entity) {
        this.originalUUID = entity.getUuid();
    }

    public void setLifeTimer(int time) {
        this.lifeTimer = time;
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        nbt.putInt("LifeTimer", this.lifeTimer);
        nbt.putUuid("OriginalUUID", this.originalUUID);
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        this.lifeTimer = nbt.getInt("LifeTimer");
        this.originalUUID = nbt.getUuid("OriginalUUID");
    }

    @Override
    public boolean damage(@NotNull DamageSource source, float amount) {
        if(source.getSource() instanceof HolyWaterEntity) {
            this.getWorld().sendEntityStatus(this, (byte) 4);
            this.remove(RemovalReason.DISCARDED);
        }
        return super.damage(source, amount);
    }

    @Override
    public void onDeath(DamageSource damageSource) {
        this.getWorld().sendEntityStatus(this, (byte) 4);
        this.remove(RemovalReason.KILLED);
    }

    @Override
    public void mobTick() {
        if(this.getWorld() instanceof ServerWorld) {
            try {
                if(!Objects.requireNonNull(((ServerWorld) this.getWorld()).getEntity(originalUUID)).isAlive()) {
                    this.getWorld().sendEntityStatus(this, (byte) 4);
                    this.remove(RemovalReason.DISCARDED);
                }
            } catch (NullPointerException e) {
                this.getWorld().sendEntityStatus(this, (byte) 4);
                this.remove(RemovalReason.DISCARDED);
            }
        }
        if(this.lifeTimer < 1) {
            this.getWorld().sendEntityStatus(this, (byte) 4);
            this.remove(RemovalReason.DISCARDED);
        }
        this.lifeTimer --;
        super.mobTick();
    }

    @Override
    public void handleStatus(byte status) {
        super.handleStatus(status);
        if(status == 4) {
            if(this.getWorld().isClient) {
                if (!this.isSilent()) {
                    this.getWorld().playSound(this.getX(), this.getEyeY(), this.getZ(), SoundEvents.ITEM_FIRECHARGE_USE, this.getSoundCategory(), 1.0f, (random.nextFloat() - random.nextFloat()) * 0.2f + 1.0f, false);
                }

                for (int i = 0; i < 20; i ++) {
                    this.getWorld().addParticle(ParticleTypes.POOF, this.getParticleX(1.0), this.getRandomBodyY(), this.getParticleZ(1.0), random.nextGaussian() * 0.02, random.nextGaussian() * 0.02, random.nextGaussian() * 0.02);
                }
            }
        }

    }

    @Override
    public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, @Nullable EntityData entityData, @Nullable NbtCompound entityNbt) {
        this.setPersistent();
        return super.initialize(world, difficulty, spawnReason, entityData, entityNbt);
    }

    @Override
    public Identifier getLootTableId() {
        return null;
    }
}
