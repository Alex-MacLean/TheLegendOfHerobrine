package com.github.alexmaclean.herobrine.entities;

import com.github.alexmaclean.herobrine.util.entities.EntityTypeList;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.IllagerEntity;
import net.minecraft.entity.passive.GolemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class HerobrineMageEntity extends HerobrineEntity {
    private int illusionCastingInterval;
    private int weakenCastingInterval;
    private int warpCastingInterval;

    public HerobrineMageEntity(EntityType<? extends HostileEntity> entityType, World world) {
        super(entityType, world);
        this.experiencePoints = 5;
        this.illusionCastingInterval = 400;
        this.weakenCastingInterval = 250;
        this.warpCastingInterval = 500;
    }

    @Override
    protected void initGoals() {
        this.goalSelector.add(0, new SwimGoal(this));
        this.goalSelector.add(1, new MeleeAttackGoal(this, 0.6, false));
        this.goalSelector.add(2, new ActiveTargetGoal<>(this, IllagerEntity.class, false));
        this.goalSelector.add(3, new ActiveTargetGoal<>(this, PlayerEntity.class, false));
        //this.goalSelector.add(4, new ActiveTargetGoal<>(this, SurvivorEntity.class, false));
        this.goalSelector.add(5, new ActiveTargetGoal<>(this, GolemEntity.class, false));
        this.goalSelector.add(6, new WanderAroundFarGoal(this, 0.4));
        this.goalSelector.add(7, new LookAtEntityGoal(this, IllagerEntity.class, 0.8f));
        this.goalSelector.add(8, new LookAtEntityGoal(this, PlayerEntity.class, 0.8f));
        //this.goalSelector.add(9, new LookAtEntityGoal(this, SurvivorEntity.class, 0.8f));
        this.goalSelector.add(10, new LookAtEntityGoal(this, GolemEntity.class, 0.8f));
        this.goalSelector.add(11, new LookAroundGoal(this));
    }

    public static DefaultAttributeContainer.Builder registerAttributes() {
        return HerobrineEntity.createHostileAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 40.0)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 4.0)
                .add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 1.0)
                .add(EntityAttributes.GENERIC_ARMOR, 1.0)
                .add(EntityAttributes.GENERIC_ARMOR_TOUGHNESS, 1.0)
                .add(EntityAttributes.GENERIC_FOLLOW_RANGE, 64.0)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.6);
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        nbt.putInt("IllusionCastingInterval", this.illusionCastingInterval);
        nbt.putInt("WarpCastingInterval", this.warpCastingInterval);
        nbt.putInt("WeakenCastingInterval", this.weakenCastingInterval);
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        this.illusionCastingInterval = nbt.getInt("IllusionCastingInterval");
        this.warpCastingInterval = nbt.getInt("WarpCastingInterval");
        this.weakenCastingInterval = nbt.getInt("WeakenCastingInterval");
    }

    @Override
    public void mobTick() {
        if (this.illusionCastingInterval < 1) {
            if(this.getTarget() != null) {
                for (int i = 0; i < 4; i++) {
                    FakeHerobrineMageEntity entity = new FakeHerobrineMageEntity(EntityTypeList.FAKE_HEROBRINE_MAGE, this.world);
                    entity.updatePositionAndAngles(this.getX(), this.getY(), this.getZ(), this.getYaw(), this.getPitch());
                    this.world.spawnEntity(entity);
                }
                this.world.sendEntityStatus(this, (byte) 4);
            }
            this.illusionCastingInterval = 400;
        }
        --this.illusionCastingInterval;

        if (this.weakenCastingInterval < 1) {
            if(this.getTarget() != null) {
                this.getTarget().addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 400, 1));
                this.getTarget().addStatusEffect(new StatusEffectInstance(StatusEffects.WEAKNESS, 400));
                this.world.sendEntityStatus(this, (byte) 4);
            }
            this.weakenCastingInterval = 250;
        }
        --this.weakenCastingInterval;

        if (this.warpCastingInterval < 1) {
            LivingEntity entity = HerobrineMageEntity.this.getTarget();
            if (entity != null) {
                double x = entity.getX();
                double y = entity.getY();
                double z = entity.getZ();
                BlockState block = world.getBlockState(new BlockPos(x, y + 3, z));
                BlockState blockAt = world.getBlockState(new BlockPos(x, y + 4, z));
                if (blockAt.isAir() && block.isAir()) {
                    if (entity.getVehicle() != null) {
                        entity.stopRiding();
                    }
                    entity.setPosition(x, y + 3, z);
                }
            }
            this.world.sendEntityStatus(this, (byte) 4);
            this.warpCastingInterval = 500;
        }
        --this.warpCastingInterval;

        super.mobTick();
    }

    @Override
    public void handleStatus(byte status) {
        if(status == 4) {
            if(this.world.isClient) {
                if (!this.isSilent()) {
                    this.world.playSound(this.getX(), this.getEyeY(), this.getZ(), SoundEvents.ENTITY_ILLUSIONER_CAST_SPELL, this.getSoundCategory(), 1.0f, (random.nextFloat() - random.nextFloat()) * 0.2f + 1.0f, false);
                }

                for (int i = 0; i < 20; ++i) {
                    double vX = this.random.nextGaussian() * 0.02;
                    double vY = this.random.nextGaussian() * 0.02;
                    double vZ = this.random.nextGaussian() * 0.02;
                    this.world.addParticle(ParticleTypes.EFFECT, this.getParticleX(1.0), this.getRandomBodyY(), this.getParticleZ(1.0), vX, vY, vZ);
                }
            }
        }
        super.handleStatus(status);
    }
}
