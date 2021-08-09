package com.herobrine.mod.entities;

import com.herobrine.mod.config.Config;
import com.herobrine.mod.util.entities.EntityRegistry;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

public class HerobrineSpyEntity extends AbstractHerobrineEntity {
    protected HerobrineSpyEntity(EntityType<? extends HerobrineSpyEntity> type, World worldIn) {
        super(type, worldIn);
        xpReward = 5;
    }

    private int lifeTimer = 6000;

    public HerobrineSpyEntity(World worldIn) {
        this(EntityRegistry.HEROBRINE_SPY_ENTITY, worldIn);
    }

    public static AttributeModifierMap.MutableAttribute registerAttributes() {
        return MonsterEntity.createMonsterAttributes()
                .add(Attributes.MAX_HEALTH, 20.0D)
                .add(Attributes.KNOCKBACK_RESISTANCE, 1.0D)
                .add(Attributes.ARMOR, 2.0D)
                .add(Attributes.ARMOR_TOUGHNESS, 2.0D)
                .add(Attributes.FOLLOW_RANGE, 128.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.6D);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new SwimGoal(this));
        this.goalSelector.addGoal(1, new LookAtGoal(this, PlayerEntity.class, 1024.0F));
        this.goalSelector.addGoal(2, new AvoidEntityGoal<>(this, PlayerEntity.class, Config.COMMON.HerobrineSpyObservationDistance.get(), 0.7D, 1.0D));
        this.goalSelector.addGoal(3, new LookAtGoal(this, AbstractSurvivorEntity.class, 1024.0F));
        this.goalSelector.addGoal(4, new AvoidEntityGoal<>(this, AbstractSurvivorEntity.class, Config.COMMON.HerobrineSpyObservationDistance.get(), 0.7D, 1.0D));
    }

    @Override
    protected boolean shouldDespawnInPeaceful() {
        return false;
    }

    @Override
    public void addAdditionalSaveData(@NotNull CompoundNBT compound) {
        super.addAdditionalSaveData(compound);
        compound.putInt("LifeTime", this.lifeTimer);
    }

    @Override
    public void readAdditionalSaveData(@NotNull CompoundNBT compound) {
        super.readAdditionalSaveData(compound);
        this.lifeTimer = compound.getInt("LifeTime");
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
                this.level.playLocalSound(this.getX() + 0.5D, this.getZ() + 0.5D, this.getZ() + 0.5D, SoundEvents.FIRECHARGE_USE, this.getSoundSource(), 1.0F + this.random.nextFloat(), this.random.nextFloat() * 0.7F + 0.3F, false);
            }
        }
    }

    @Override
    public void aiStep() {
        super.aiStep();
        if (this.lifeTimer > 6000) {
            this.lifeTimer = 6000;
        }
        if (this.lifeTimer < 1) {
            if (!this.level.isClientSide) {
                this.level.broadcastEntityEvent(this, (byte) 4);
            }
            this.remove();
        }
        --this.lifeTimer;
    }

    @Override
    public ILivingEntityData finalizeSpawn(@NotNull IServerWorld worldIn, @NotNull DifficultyInstance difficultyIn, @NotNull SpawnReason reason, @Nullable ILivingEntityData spawnDataIn, @Nullable CompoundNBT dataTag) {
        this.setPersistenceRequired();
        return super.finalizeSpawn(worldIn, difficultyIn, reason, spawnDataIn, dataTag);
    }
}