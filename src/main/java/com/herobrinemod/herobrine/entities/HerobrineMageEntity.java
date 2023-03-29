package com.herobrinemod.herobrine.entities;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
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
    private int illusionCastingCounter;
    private int weakenCastingCounter;
    private int warpCastingCounter;
    private int holdCastingCounter;
    private int holdTicks;
    private boolean startedHold;

    public HerobrineMageEntity(EntityType<? extends HostileEntity> entityType, World world) {
        super(entityType, world);
        this.illusionCastingCounter = random.nextBetween(125, 500);
        this.weakenCastingCounter = random.nextBetween(100, 400);
        this.warpCastingCounter = random.nextBetween(90, 550);
        this.holdCastingCounter = random.nextBetween(150, 600);
        this.holdTicks = random.nextBetween(35, 65);
        this.experiencePoints = 5;
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
        this.goalSelector.add(7, new LookAtEntityGoal(this, IllagerEntity.class, 64.0f));
        this.goalSelector.add(8, new LookAtEntityGoal(this, PlayerEntity.class, 64.0f));
        //this.goalSelector.add(9, new LookAtEntityGoal(this, SurvivorEntity.class, 64.0f));
        this.goalSelector.add(10, new LookAtEntityGoal(this, GolemEntity.class, 64.0f));
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
        nbt.putInt("IllusionCastingInterval", this.illusionCastingCounter);
        nbt.putInt("WarpCastingInterval", this.warpCastingCounter);
        nbt.putInt("WeakenCastingInterval", this.weakenCastingCounter);
        nbt.putInt("HoldCastingInterval", this.holdCastingCounter);
        nbt.putInt("HoldTicks", this.holdTicks);
        nbt.putBoolean("StartedHold", this.startedHold);
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        this.illusionCastingCounter = nbt.getInt("IllusionCastingInterval");
        this.warpCastingCounter = nbt.getInt("WarpCastingInterval");
        this.weakenCastingCounter = nbt.getInt("WeakenCastingInterval");
        this.holdCastingCounter = nbt.getInt("HoldCastingInterval");
        this.holdTicks = nbt.getInt("HoldTicks");
        this.startedHold =  nbt.getBoolean("StartedHold");
    }

    @Override
    public void mobTick() {
        super.mobTick();
        if(this.getTarget() != null) {
            if (this.illusionCastingCounter < 1) {
                int duration = random.nextBetween(150, 400);
                for (int i = 0; i < 4; i ++) {
                    FakeHerobrineMageEntity entity = new FakeHerobrineMageEntity(EntityTypeList.FAKE_HEROBRINE_MAGE, this.world);
                    entity.setLifeTimer(duration);
                    entity.updatePositionAndAngles(this.getX(), this.getY(), this.getZ(), this.getYaw(), this.getPitch());
                    this.world.spawnEntity(entity);
                }
                this.world.sendEntityStatus(this, (byte) 4);
                this.illusionCastingCounter = random.nextBetween(125, 500);
            }
            --this.illusionCastingCounter;

            if (this.weakenCastingCounter < 1) {
                this.getTarget().addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 400, 1));
                this.getTarget().addStatusEffect(new StatusEffectInstance(StatusEffects.WEAKNESS, 400));
                this.world.sendEntityStatus(this, (byte) 4);
                this.weakenCastingCounter = random.nextBetween(100, 400);
            }
            --this.weakenCastingCounter;

            if (this.warpCastingCounter < 1) {
                if(this.world.getBlockState(new BlockPos((int) this.getTarget().getX(), (int) this.getTarget().getY() + 4, (int) this.getTarget().getZ())).isAir() && this.world.getBlockState(new BlockPos((int) this.getTarget().getX(), (int) this.getTarget().getY() + 5, (int) this.getTarget().getZ())).isAir()) {
                    if(this.getTarget().hasVehicle()) {
                        this.getTarget().dismountVehicle();
                    }
                    this.getTarget().requestTeleport(this.getTarget().getX(), this.getTarget().getY() + 4.0, this.getTarget().getZ());
                    this.world.sendEntityStatus(this, (byte) 4);
                }
                this.warpCastingCounter = random.nextBetween(90, 550);
            }
            --this.warpCastingCounter;

            if (this.holdCastingCounter < 1) {
                if(this.getTarget().hasVehicle()) {
                    this.getTarget().dismountVehicle();
                }
                if (this.holdTicks > 0) {
                    if(this.world.getBlockState(new BlockPos((int) this.getTarget().getX(), (int) this.getTarget().getY() + 1, (int) this.getTarget().getZ())).isAir() && !this.startedHold) {
                        this.getTarget().requestTeleport(this.getTarget().getX(), this.getTarget().getY() + 1.0, this.getTarget().getZ());
                        this.getTarget().damage(this.getDamageSources().magic(), 1);
                        this.startedHold = true;
                    } else {
                        this.getTarget().requestTeleport(this.getTarget().getX(),this.getTarget().getY(),this.getTarget().getZ());
                        this.getTarget().damage(this.getDamageSources().magic(), 1);
                    }
                    this.holdTicks --;
                }
                this.world.sendEntityStatus(this, (byte) 4);
                if(this.holdTicks <= 0) {
                    this.holdCastingCounter = random.nextBetween(150, 600);
                    this.holdTicks = random.nextBetween(35, 65);
                    this.startedHold = false;
                }
            }
            --this.holdCastingCounter;
        }
    }

    @Override
    public void handleStatus(byte status) {
        super.handleStatus(status);
        if(status == 4) {
            if(this.world.isClient) {
                if (!this.isSilent()) {
                    this.world.playSound(this.getX(), this.getEyeY(), this.getZ(), SoundEvents.ENTITY_ILLUSIONER_CAST_SPELL, this.getSoundCategory(), 1.0f, (random.nextFloat() - random.nextFloat()) * 0.2f + 1.0f, false);
                }

                for (int i = 0; i < 20; i ++) {
                    this.world.addParticle(ParticleTypes.EFFECT, this.getParticleX(1.0), this.getRandomBodyY(), this.getParticleZ(1.0), random.nextGaussian() * 0.02, random.nextGaussian() * 0.02, random.nextGaussian() * 0.02);
                }
            }
        }
    }
}
