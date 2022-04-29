package com.github.alexmaclean.herobrine.entities;

import net.minecraft.entity.EntityData;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.IllagerEntity;
import net.minecraft.entity.passive.GolemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundEvents;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class HerobrineBuilderEntity extends HerobrineEntity {
    private int lifeTimer;
    private int buildTimer;

    public HerobrineBuilderEntity(EntityType<? extends HostileEntity> entityType, World world) {
        super(entityType, world);
        this.lifeTimer = 5100;
        this.buildTimer = 1000;
        this.experiencePoints = 5;
    }

    @Override
    protected void initGoals() {
        this.goalSelector.add(0, new SwimGoal(this));
        this.goalSelector.add(1, new MeleeAttackGoal(this, 0.6 ,false));
        this.goalSelector.add(2, new ActiveTargetGoal<>(this, IllagerEntity.class, false));
        this.goalSelector.add(3, new ActiveTargetGoal<>(this, PlayerEntity.class, false));
        this.goalSelector.add(4, new ActiveTargetGoal<>(this, GolemEntity.class, false));
        //this.goalSelector.add(5, new ActiveTargetGoal<>(this, SurvivorEntity.class, false));
        this.goalSelector.add(6, new WanderAroundFarGoal(this, 0.4));
        this.goalSelector.add(7, new LookAtEntityGoal(this, IllagerEntity.class, 32.0f));
        this.goalSelector.add(8, new LookAtEntityGoal(this, PlayerEntity.class, 32.0f));
        //this.goalSelector.add(9, new LookAtEntityGoal(this, SurvivorEntity.class, 32.0f));
        this.goalSelector.add(10, new LookAtEntityGoal(this, GolemEntity.class, 32.0f));
        this.goalSelector.add(11, new LookAroundGoal(this));

    }

    public static DefaultAttributeContainer.Builder registerAttributes() {
        return HerobrineEntity.createHostileAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 20.0)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 5.0)
                .add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 1.0)
                .add(EntityAttributes.GENERIC_ARMOR, 0.0)
                .add(EntityAttributes.GENERIC_ARMOR_TOUGHNESS, 0.0)
                .add(EntityAttributes.GENERIC_FOLLOW_RANGE, 32.0)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.45);
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        nbt.putInt("LifeTimer", this.lifeTimer);
        nbt.putInt("BuildingInterval", this.buildTimer);
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        this.lifeTimer = nbt.getInt("LifeTimer");
        this.buildTimer = nbt.getInt("BuildingInterval");
    }

    @Override
    public void mobTick() {
        if(this.lifeTimer < 1) {
            this.world.sendEntityStatus(this, (byte) 4);
            this.remove(RemovalReason.DISCARDED);
        }

        this.lifeTimer --;

        if(this.buildTimer < 1) {
            this.buildTimer = 1000;

            if (!world.isClient) {

            }
        }
        super.mobTick();
    }

    @Override
    public void handleStatus(byte status) {
        if(status == 5) {
            if(this.world.isClient && !this.isSilent()) {
                this.world.playSound(this.getX(), this.getEyeY(), this.getZ(), SoundEvents.AMBIENT_CAVE, this.getSoundCategory(), 1.0f, (random.nextFloat() - random.nextFloat()) * 0.2f + 1.0f, false);
            }
        } else if(status == 4) {
            if(this.world.isClient) {
                if (!this.isSilent()) {
                    this.world.playSound(this.getX(), this.getEyeY(), this.getZ(), SoundEvents.ITEM_FIRECHARGE_USE, this.getSoundCategory(), 1.0f, (random.nextFloat() - random.nextFloat()) * 0.2f + 1.0f, false);
                }

                for (int i = 0; i < 20; ++i) {
                    this.world.addParticle(ParticleTypes.POOF, this.getParticleX(1.0), this.getRandomBodyY(), this.getParticleZ(1.0), random.nextGaussian() * 0.02, random.nextGaussian() * 0.02, random.nextGaussian() * 0.02);
                }
            }
        }
        super.handleStatus(status);
    }

    @Override
    public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, @Nullable EntityData entityData, @Nullable NbtCompound entityNbt) {
        this.setPersistent();
        return super.initialize(world, difficulty, spawnReason, entityData, entityNbt);
    }
}
