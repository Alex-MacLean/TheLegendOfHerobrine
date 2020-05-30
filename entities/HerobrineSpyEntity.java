package com.herobrine.mod.entities;

import com.herobrine.mod.config.Config;
import com.herobrine.mod.util.entities.EntityRegistry;
import com.herobrine.mod.util.savedata.Variables;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.PotionEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.DamageSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

public class HerobrineSpyEntity extends MonsterEntity {
    protected HerobrineSpyEntity(EntityType<? extends HerobrineSpyEntity> type, World worldIn) {
        super(type, worldIn);
        experienceValue = 5;
    }

    private int lifeTimer = 6000;

    @SuppressWarnings("unchecked")
    public HerobrineSpyEntity(World worldIn) {
        this((EntityType<? extends HerobrineSpyEntity>) EntityRegistry.HEROBRINE_SPY_ENTITY, worldIn);
    }

    @Override
    protected boolean isDespawnPeaceful() {
        return false;
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new SwimGoal(this));
        this.goalSelector.addGoal(1, new LookAtGoal(this, PlayerEntity.class, 1024.0F));
        this.goalSelector.addGoal(2, new AvoidEntityGoal<>(this, PlayerEntity.class, 48.0F, 0.7D, 1.0D));
    }

    @Override
    protected void registerAttributes() {
        super.registerAttributes();
        this.getAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(20.0D);
        this.getAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).setBaseValue(1.0D);
        this.getAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(2.0D);
        this.getAttribute(SharedMonsterAttributes.ARMOR_TOUGHNESS).setBaseValue(2.0D);
        this.getAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(128.0D);
        this.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.6D);
    }

    @Override
    public boolean attackEntityFrom(@NotNull DamageSource source, float amount) {
        if (source.getImmediateSource() instanceof AreaEffectCloudEntity)
            return false;
        if (source.getImmediateSource() instanceof PotionEntity)
            return false;
        if (source.getImmediateSource() instanceof UnholyWaterEntity)
            return false;
        if (source == DamageSource.FALL)
            return false;
        if (source == DamageSource.CACTUS)
            return false;
        if (source == DamageSource.DROWN)
            return false;
        if (source == DamageSource.LIGHTNING_BOLT)
            return false;
        if (source == DamageSource.IN_FIRE)
            return false;
        if (source == DamageSource.ON_FIRE)
            return false;
        if (source == DamageSource.ANVIL)
            return false;
        if (source == DamageSource.CRAMMING)
            return false;
        if (source == DamageSource.DRAGON_BREATH)
            return false;
        if (source == DamageSource.DRYOUT)
            return false;
        if (source == DamageSource.FALLING_BLOCK)
            return false;
        if (source == DamageSource.FIREWORKS)
            return false;
        if (source == DamageSource.FLY_INTO_WALL)
            return false;
        if (source == DamageSource.HOT_FLOOR)
            return false;
        if (source == DamageSource.LAVA)
            return false;
        if (source == DamageSource.IN_WALL)
            return false;
        if (source == DamageSource.MAGIC)
            return false;
        if (source == DamageSource.STARVE)
            return false;
        if (source == DamageSource.SWEET_BERRY_BUSH)
            return false;
        if (source == DamageSource.WITHER)
            return false;
        return super.attackEntityFrom(source, amount);
    }

    @Override
    public void writeAdditional(@NotNull CompoundNBT compound) {
        super.writeAdditional(compound);
        compound.putInt("LifeTime", this.lifeTimer);
    }

    @Override
    public void readAdditional(@NotNull CompoundNBT compound) {
        super.readAdditional(compound);
        this.lifeTimer = compound.getInt("LifeTime");
    }

    @Override
    public void baseTick() {
        super.baseTick();
        this.clearActivePotions();
        --this.lifeTimer;
        if(this.lifeTimer > 6000) {
            this.lifeTimer = 6000;
        }
        if(this.lifeTimer <= 0) {
            this.remove();
        }
    }

    @Override
    public ILivingEntityData onInitialSpawn(@NotNull IWorld worldIn, @NotNull DifficultyInstance difficultyIn, @NotNull SpawnReason reason, @Nullable ILivingEntityData spawnDataIn, @Nullable CompoundNBT dataTag) {
        Variables.WorldVariables.get(world).syncData(world);
        if (!Variables.WorldVariables.get(world).Spawn && !Config.COMMON.IgnoreWorldData.get()) {
            this.remove();
        }
        this.enablePersistence();
        return super.onInitialSpawn(worldIn, difficultyIn, reason, spawnDataIn, dataTag);
    }
}