package com.herobrine.mod.entities;

import com.herobrine.mod.util.entities.EntityRegistry;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.List;

public class HerobrineStalkerEntity extends AbstractHerobrineEntity {
    protected HerobrineStalkerEntity(EntityType<? extends HerobrineStalkerEntity> type, World worldIn) {
        super(type, worldIn);
        xpReward = 5;
    }

    private int lifeTimer = 6000;
    private int runAtTargetDelay = 500;
    private int runAtTargetTime = 1000;
    private boolean isRunningAtTarget = false;

    public HerobrineStalkerEntity(World worldIn) {
        this(EntityRegistry.HEROBRINE_STALKER_ENTITY, worldIn);
    }

    MeleeAttackGoal runAtTargetGoal = new MeleeAttackGoal(this, 1.0D, true);

    public static AttributeModifierMap.MutableAttribute registerAttributes() {
        return MonsterEntity.createMonsterAttributes()
                .add(Attributes.ATTACK_DAMAGE, 0.0D)
                .add(Attributes.MAX_HEALTH, 20.0D)
                .add(Attributes.KNOCKBACK_RESISTANCE, 1.0D)
                .add(Attributes.ARMOR, 2.0D)
                .add(Attributes.ARMOR_TOUGHNESS, 2.0D)
                .add(Attributes.FOLLOW_RANGE, 64.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.6D);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new SwimGoal(this));
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, PlayerEntity.class, false));
        this.goalSelector.addGoal(3, new WaterAvoidingRandomWalkingGoal(this, 0.4D));
        this.goalSelector.addGoal(4, new LookAtGoal(this, PlayerEntity.class, 64.0F));
        this.goalSelector.addGoal(5, new LookAtGoal(this, AbstractSurvivorEntity.class, 64.0F));
        this.goalSelector.addGoal(6, new LookRandomlyGoal(this));
    }

    @Override
    protected boolean shouldDespawnInPeaceful() {
        return false;
    }

    @Override
    public void addAdditionalSaveData(@NotNull CompoundNBT compound) {
        super.addAdditionalSaveData(compound);
        compound.putInt("LifeTime", this.lifeTimer);
        compound.putInt("ChargeTargetDelay", this.runAtTargetDelay);
        compound.putInt("ChargeTargetTime", this.runAtTargetTime);
        compound.putBoolean("RunningAtTarget", this.isRunningAtTarget);
    }

    @Override
    public void readAdditionalSaveData(@NotNull CompoundNBT compound) {
        super.readAdditionalSaveData(compound);
        this.lifeTimer = compound.getInt("LifeTime");
        this.runAtTargetDelay = compound.getInt("ChargeTargetDelay");
        this.runAtTargetTime = compound.getInt("ChargeTargetTime");
        this.isRunningAtTarget = compound.getBoolean("RunningAtTarget");
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void handleEntityEvent(byte id) {
        super.handleEntityEvent(id);
        if (id == 4) {
            for (int i = 0; i < 20; ++i) {
                double d0 = this.random.nextGaussian() * 0.02D;
                double d1 = this.random.nextGaussian() * 0.02D;
                double d2 = this.random.nextGaussian() * 0.02D;
                this.level.addParticle(ParticleTypes.POOF, this.getRandomX(1.0D) - d0 * 10.0D, this.getRandomY() - d1 * 10.0D, this.getRandomZ(1.0D) - d2 * 10.0D, d0, d1, d2);
            }
            if (!this.isSilent()) {
                this.level.playLocalSound(this.getX() + 0.5D, this.getY() + 0.5D, this.getZ() + 0.5D, SoundEvents.FIRECHARGE_USE, this.getSoundSource(), 1.0F + this.random.nextFloat(), this.random.nextFloat() * 0.7F + 0.3F, false);
            }
        }
        if(id == 5) {
            for (int i = 0; i < 20; ++i) {
                double d0 = this.random.nextGaussian() * 0.02D;
                double d1 = this.random.nextGaussian() * 0.02D;
                double d2 = this.random.nextGaussian() * 0.02D;
                this.level.addParticle(ParticleTypes.POOF, this.getRandomX(1.0D) - d0 * 10.0D, this.getRandomY() - d1 * 10.0D, this.getRandomZ(1.0D) - d2 * 10.0D, d0, d1, d2);
            }
            if(!this.isSilent()) {
                this.level.playLocalSound(this.getX() + 0.5D, this.getY() + 0.5D, this.getZ() + 0.5D, SoundEvents.AMBIENT_CAVE, this.getSoundSource(), 1.0F + this.random.nextFloat(), this.random.nextFloat() * 0.7F + 0.3F, false);
            }
        }
    }

    @Override
    public void aiStep() {
        super.aiStep();
        --this.lifeTimer;

        if(this.isRunningAtTarget) {
            --this.runAtTargetTime;
        } else {
            --this.runAtTargetDelay;
        }

        if(this.runAtTargetDelay < 1) {
            this.goalSelector.addGoal(2, this.runAtTargetGoal);
            this.isRunningAtTarget = true;
            this.runAtTargetDelay = 500;
        }

        if (this.runAtTargetTime < 1) {
            this.goalSelector.removeGoal(this.runAtTargetGoal);
            this.runAtTargetTime = 1000;
            this.isRunningAtTarget = false;
        }

        if (this.runAtTargetDelay >= 1 && this.isRunningAtTarget) {
            this.runAtTargetDelay = 500;
            this.runAtTargetTime = 1000;
        }

        AxisAlignedBB axisalignedbb = this.getBoundingBox().inflate(1.0D);
        List<LivingEntity> list = this.level.getEntitiesOfClass(LivingEntity.class, axisalignedbb);
        if (!list.isEmpty()) {
            for (LivingEntity entity : list) {
                if (entity instanceof PlayerEntity && this.getTarget() == entity && this.isRunningAtTarget) {
                    entity.addEffect(new EffectInstance(Effects.CONFUSION, 300, 0));
                    entity.addEffect(new EffectInstance(Effects.BLINDNESS, 600, 0));
                    if (!this.level.isClientSide) {
                        this.level.broadcastEntityEvent(this, (byte) 5);
                    }
                    this.remove();
                }
            }
        }

        if(this.lifeTimer < 1) {
            if (!this.level.isClientSide) {
                this.level.broadcastEntityEvent(this, (byte) 4);
            }
            this.remove();
        }
    }

    @Override
    public ILivingEntityData finalizeSpawn(@NotNull IServerWorld worldIn, @NotNull DifficultyInstance difficultyIn, @NotNull SpawnReason reason, @Nullable ILivingEntityData spawnDataIn, @Nullable CompoundNBT dataTag) {
        this.setPersistenceRequired();
        return super.finalizeSpawn(worldIn, difficultyIn, reason, spawnDataIn, dataTag);
    }
}