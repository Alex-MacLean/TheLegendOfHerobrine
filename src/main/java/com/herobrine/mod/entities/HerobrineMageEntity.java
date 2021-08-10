package com.herobrine.mod.entities;

import com.herobrine.mod.util.entities.EntityRegistry;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.monster.AbstractIllagerEntity;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.passive.GolemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

public class HerobrineMageEntity extends AbstractHerobrineEntity {
    protected HerobrineMageEntity(EntityType<? extends HerobrineMageEntity> type, World worldIn) {
        super(type, worldIn);
        xpReward = 5;
    }

    public HerobrineMageEntity(World worldIn) {
        this(EntityRegistry.HEROBRINE_MAGE_ENTITY, worldIn);
    }

    private int illusionCastingTime = 400;
    private int effectsCastingTime = 250;
    private int teleportCastingTime = 500;

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new SwimGoal(this));
        this.goalSelector.addGoal(1, new MeleeAttackGoal(this, 0.6D, true));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, AbstractIllagerEntity.class, true));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, PlayerEntity.class, true));
        this.targetSelector.addGoal(4, new NearestAttackableTargetGoal<>(this, AbstractSurvivorEntity.class, true));
        this.targetSelector.addGoal(5, new NearestAttackableTargetGoal<>(this, GolemEntity.class, true));
        this.targetSelector.addGoal(6, new HurtByTargetGoal(this));
        this.goalSelector.addGoal(7, new WaterAvoidingRandomWalkingGoal(this, 0.4D));
        this.goalSelector.addGoal(8, new LookAtGoal(this, AbstractIllagerEntity.class, 8.0F));
        this.goalSelector.addGoal(9, new LookAtGoal(this, PlayerEntity.class, 8.0F));
        this.goalSelector.addGoal(10, new LookAtGoal(this, AbstractSurvivorEntity.class, 8.0F));
        this.goalSelector.addGoal(11, new LookAtGoal(this, GolemEntity.class, 8.0F));
        this.goalSelector.addGoal(12, new LookRandomlyGoal(this));
    }

    public static AttributeModifierMap.@NotNull MutableAttribute registerAttributes() {
        return MonsterEntity.createMonsterAttributes()
                .add(Attributes.MAX_HEALTH, 40.0D)
                .add(Attributes.ATTACK_DAMAGE, 4.0D)
                .add(Attributes.KNOCKBACK_RESISTANCE, 1.0D)
                .add(Attributes.ARMOR, 1.0D)
                .add(Attributes.ARMOR_TOUGHNESS, 1.0D)
                .add(Attributes.FOLLOW_RANGE, 64.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.6D);
    }

    @Override
    public void addAdditionalSaveData(@NotNull CompoundNBT compound) {
        super.addAdditionalSaveData(compound);
        compound.putInt("IllusionCastingInterval", this.illusionCastingTime);
        compound.putInt("WeakenCastingInterval", this.effectsCastingTime);
        compound.putInt("WarpCastingInterval", this.teleportCastingTime);
    }

    @Override
    public void readAdditionalSaveData(@NotNull CompoundNBT compound) {
        super.readAdditionalSaveData(compound);
        this.illusionCastingTime = compound.getInt("IllusionCastingInterval");
        this.effectsCastingTime = compound.getInt("WeakenCastingInterval");
        this.teleportCastingTime = compound.getInt("WarpCastingInterval");
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void handleEntityEvent(byte id) {
        super.handleEntityEvent(id);
        if (id == 4) {
            if (!this.isSilent()) {
                this.level.playLocalSound(this.getX() + 0.5D, this.getY() + 0.5D, this.getZ() + 0.5D, SoundEvents.ILLUSIONER_CAST_SPELL, this.getSoundSource(), 1.0F + this.random.nextFloat(), this.random.nextFloat() * 0.7F + 0.3F, false);
            }
            for (int i = 0; i < 20; ++i) {
                double d0 = this.random.nextGaussian() * 0.02D;
                double d1 = this.random.nextGaussian() * 0.02D;
                double d2 = this.random.nextGaussian() * 0.02D;
                this.level.addParticle(ParticleTypes.EFFECT, this.getRandomX(1.0D) - d0 * 10.0D, this.getRandomY() - d1 * 10.0D, this.getRandomZ(1.0D) - d2 * 10.0D, d0, d1, d2);
            }
        }
    }


    @Override
    public void aiStep() {
        if (this.isAlive()) {
            if (this.illusionCastingTime < 1) {
                this.illusionCastingTime = 400;
            }
            --this.illusionCastingTime;

            if (this.effectsCastingTime < 1 || this.effectsCastingTime > 250) {
                this.effectsCastingTime = 250;
            }
            --this.effectsCastingTime;

            if (this.teleportCastingTime < 1 || this.teleportCastingTime > 500) {
                this.teleportCastingTime = 500;
            }
            --this.teleportCastingTime;

            if (this.illusionCastingTime == 0 && this.isAggressive() && this.getTarget() != null) {
                int x = (int) this.getX();
                int y = (int) this.getY();
                int z = (int) this.getZ();
                if (!level.isClientSide) {
                    Entity entity1 = new FakeHerobrineMageEntity(EntityRegistry.FAKE_HEROBRINE_MAGE_ENTITY, level);
                    Entity entity2 = new FakeHerobrineMageEntity(EntityRegistry.FAKE_HEROBRINE_MAGE_ENTITY, level);
                    Entity entity3 = new FakeHerobrineMageEntity(EntityRegistry.FAKE_HEROBRINE_MAGE_ENTITY, level);
                    Entity entity4 = new FakeHerobrineMageEntity(EntityRegistry.FAKE_HEROBRINE_MAGE_ENTITY, level);
                    entity1.moveTo(x, y, z, level.random.nextFloat() * 360F, 0);
                    entity2.moveTo(x, y, z, level.random.nextFloat() * 360F, 0);
                    entity3.moveTo(x, y, z, level.random.nextFloat() * 360F, 0);
                    entity4.moveTo(x, y, z, level.random.nextFloat() * 360F, 0);
                    level.addFreshEntity(entity1);
                    level.addFreshEntity(entity2);
                    level.addFreshEntity(entity3);
                    level.addFreshEntity(entity4);
                }

                if (!this.level.isClientSide) {
                    this.level.broadcastEntityEvent(this, (byte) 4);
                }
            }

            if (this.effectsCastingTime == 0 && this.isAggressive() && this.getTarget() != null) {
                LivingEntity entity = HerobrineMageEntity.this.getTarget();
                if (entity != null) {
                    entity.addEffect(new EffectInstance(Effects.MOVEMENT_SLOWDOWN, 400, 1));
                    entity.addEffect(new EffectInstance(Effects.WEAKNESS, 400));
                }

                if (!this.level.isClientSide) {
                    this.level.broadcastEntityEvent(this, (byte) 4);
                }
            }

            if (this.teleportCastingTime == 0 && this.isAggressive() && this.getTarget() != null) {
                LivingEntity entity = HerobrineMageEntity.this.getTarget();
                if (entity != null) {
                    int x = (int) entity.getX();
                    int y = (int) entity.getY();
                    int z = (int) entity.getZ();
                    BlockState block = level.getBlockState(new BlockPos(x, y + 3, z));
                    BlockState blockAt = level.getBlockState(new BlockPos(x, y + 4, z));
                    if (blockAt.getBlockState().isAir() && block.getBlockState().isAir()) {
                        if (entity.getVehicle() != null) {
                            entity.stopRiding();
                        }
                        entity.moveTo(x, y + 4, z);
                        if (!this.level.isClientSide) {
                            this.level.broadcastEntityEvent(this, (byte) 4);
                        }
                    }
                }
            }
        }
        super.aiStep();
    }
}