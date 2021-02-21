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
        experienceValue = 5;
    }

    private int lifeTimer = 6000;
    private int runAtTargetDelay = 500;
    private int runAtTargetTime = 1000;
    private boolean isRunningAtTarget = false;

    public HerobrineStalkerEntity(World worldIn) {
        this(EntityRegistry.HEROBRINE_STALKER_ENTITY, worldIn);
    }

    MeleeAttackGoal runAtTargetGoal = new MeleeAttackGoal(this, 1.0D, true);

    @Override
    protected boolean isDespawnPeaceful() {
        return false;
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

    public static AttributeModifierMap.MutableAttribute registerAttributes() {
        return MonsterEntity.func_234295_eP_()
                .createMutableAttribute(Attributes.ATTACK_DAMAGE, 0.0D)
                .createMutableAttribute(Attributes.MAX_HEALTH, 20.0D)
                .createMutableAttribute(Attributes.KNOCKBACK_RESISTANCE, 1.0D)
                .createMutableAttribute(Attributes.ARMOR, 2.0D)
                .createMutableAttribute(Attributes.ARMOR_TOUGHNESS, 2.0D)
                .createMutableAttribute(Attributes.FOLLOW_RANGE, 64.0D)
                .createMutableAttribute(Attributes.MOVEMENT_SPEED, 0.6D);
    }

    @Override
    public void writeAdditional(@NotNull CompoundNBT compound) {
        super.writeAdditional(compound);
        compound.putInt("LifeTime", this.lifeTimer);
        compound.putInt("ChargeTargetDelay", this.runAtTargetDelay);
        compound.putInt("ChargeTargetTime", this.runAtTargetTime);
        compound.putBoolean("RunningAtTarget", this.isRunningAtTarget);
    }

    @Override
    public void readAdditional(@NotNull CompoundNBT compound) {
        super.readAdditional(compound);
        this.lifeTimer = compound.getInt("LifeTime");
        this.runAtTargetDelay = compound.getInt("ChargeTargetDelay");
        this.runAtTargetTime = compound.getInt("ChargeTargetTime");
        this.isRunningAtTarget = compound.getBoolean("RunningAtTarget");
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void handleStatusUpdate(byte id) {
        super.handleStatusUpdate(id);
        if (id == 4) {
            for (int i = 0; i < 20; ++i) {
                double d0 = this.rand.nextGaussian() * 0.02D;
                double d1 = this.rand.nextGaussian() * 0.02D;
                double d2 = this.rand.nextGaussian() * 0.02D;
                this.world.addParticle(ParticleTypes.POOF, this.getPosXWidth(1.0D) - d0 * 10.0D, this.getPosYRandom() - d1 * 10.0D, this.getPosZRandom(1.0D) - d2 * 10.0D, d0, d1, d2);
            }
            if (!this.isSilent()) {
                this.world.playSound(this.getPosX() + 0.5D, this.getPosY() + 0.5D, this.getPosZ() + 0.5D, SoundEvents.ITEM_FIRECHARGE_USE, this.getSoundCategory(), 1.0F + this.rand.nextFloat(), this.rand.nextFloat() * 0.7F + 0.3F, false);
            }
        }
        if(id == 5) {
            for (int i = 0; i < 20; ++i) {
                double d0 = this.rand.nextGaussian() * 0.02D;
                double d1 = this.rand.nextGaussian() * 0.02D;
                double d2 = this.rand.nextGaussian() * 0.02D;
                this.world.addParticle(ParticleTypes.POOF, this.getPosXWidth(1.0D) - d0 * 10.0D, this.getPosYRandom() - d1 * 10.0D, this.getPosZRandom(1.0D) - d2 * 10.0D, d0, d1, d2);
            }
            if(!this.isSilent()) {
                this.world.playSound(this.getPosX() + 0.5D, this.getPosY() + 0.5D, this.getPosZ() + 0.5D, SoundEvents.AMBIENT_CAVE, this.getSoundCategory(), 1.0F + this.rand.nextFloat(), this.rand.nextFloat() * 0.7F + 0.3F, false);
            }
        }
    }

    @Override
    public void baseTick() {
        super.baseTick();
        --this.lifeTimer;
        if(this.lifeTimer > 6000) {
            this.lifeTimer = 6000;
        }

        if(this.runAtTargetDelay > 500) {
            this.runAtTargetDelay = 500;
        }

        --this.runAtTargetTime;
        if(this.runAtTargetTime > 1000) {
            this.runAtTargetTime = 1000;
        }

        if(this.isRunningAtTarget) {
            --this.runAtTargetTime;
        } else {
            --this.runAtTargetDelay;
        }

        if(this.runAtTargetDelay < 1) {
            this.goalSelector.addGoal(2, this.runAtTargetGoal);
            this.runAtTargetDelay = 500;
            this.isRunningAtTarget = true;
        }

        if(this.runAtTargetTime < 1) {
            this.goalSelector.removeGoal(this.runAtTargetGoal);
            this.runAtTargetTime = 1000;
            this.isRunningAtTarget = false;
        }

        AxisAlignedBB axisalignedbb = this.getBoundingBox();
        List<LivingEntity> list = this.world.getEntitiesWithinAABB(LivingEntity.class, axisalignedbb);
        if (!list.isEmpty()) {
            for (LivingEntity entity : list) {
                if (entity instanceof PlayerEntity && this.getAttackTarget() == entity && this.isRunningAtTarget) {
                    entity.addPotionEffect(new EffectInstance(Effects.NAUSEA, 300, 0));
                    if (!this.world.isRemote) {
                        this.world.setEntityState(this, (byte)5);
                    }
                    this.remove();
                }
            }
        }

        if(this.lifeTimer < 1) {
            if (!this.world.isRemote) {
                this.world.setEntityState(this, (byte)4);
            }
            this.remove();
        }
    }

    @Override
    public ILivingEntityData onInitialSpawn(@NotNull IServerWorld worldIn, @NotNull DifficultyInstance difficultyIn, @NotNull SpawnReason reason, @Nullable ILivingEntityData spawnDataIn, @Nullable CompoundNBT dataTag) {
        this.enablePersistence();
        return super.onInitialSpawn(worldIn, difficultyIn, reason, spawnDataIn, dataTag);
    }
}