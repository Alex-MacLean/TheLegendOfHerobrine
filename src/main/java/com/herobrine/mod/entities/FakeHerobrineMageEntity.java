package com.herobrine.mod.entities;

import com.herobrine.mod.util.entities.EntityRegistry;
import com.herobrine.mod.util.savedata.Variables;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.passive.GolemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.PotionEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

public class FakeHerobrineMageEntity extends MonsterEntity {
    protected FakeHerobrineMageEntity(EntityType<? extends FakeHerobrineMageEntity> type, World worldIn) {
        super(type, worldIn);
        experienceValue = 0;
    }

    public FakeHerobrineMageEntity(World worldIn) {
        this((EntityType<? extends FakeHerobrineMageEntity>) EntityRegistry.FAKE_HEROBRINE_MAGE_ENTITY, worldIn);
    }

    private int lifeTimer = 200;

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new SwimGoal(this));
        this.goalSelector.addGoal(1, new MeleeAttackGoal(this, 0.6D, true));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, PlayerEntity.class, false));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, GolemEntity.class, true));
        this.targetSelector.addGoal(4, new HurtByTargetGoal(this));
        this.goalSelector.addGoal(5, new WaterAvoidingRandomWalkingGoal(this, 0.4D));
        this.goalSelector.addGoal(6, new LookAtGoal(this, PlayerEntity.class, 8.0F));
        this.goalSelector.addGoal(7, new LookAtGoal(this, GolemEntity.class, 8.0F));
        this.goalSelector.addGoal(8, new LookRandomlyGoal(this));
    }

    @Override
    protected void registerAttributes() {
        super.registerAttributes();
        this.getAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(25.0D);
        this.getAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).setBaseValue(1.0D);
        this.getAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(64.0D);
        this.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.4D);
        this.getAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(0.0D);
    }

    @Override
    public boolean attackEntityFrom(@NotNull DamageSource source, float amount) {
        if(source.getImmediateSource() instanceof HolyWaterEntity)
            this.remove();
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
    }

    @Override
    public ILivingEntityData onInitialSpawn(@NotNull IWorld worldIn, @NotNull DifficultyInstance difficultyIn, @NotNull SpawnReason reason, @Nullable ILivingEntityData spawnDataIn, @Nullable CompoundNBT dataTag) {
        Variables.WorldVariables.get(world).syncData(world);
        if ((!(Variables.WorldVariables.get(world).Spawn))) {
            this.remove();
        }
        this.enablePersistence();
        return super.onInitialSpawn(worldIn, difficultyIn, reason, spawnDataIn, dataTag);
    }

    @Override
    public void livingTick() {
        if (this.lifeTimer <= 0) {
            if (this.world.isRemote) {
                if (!this.isSilent()) {
                    this.world.playSound(this.getPosX() + 0.5D, this.getPosY() + 0.5D, this.getPosZ() + 0.5D, SoundEvents.ITEM_FIRECHARGE_USE, this.getSoundCategory(), 1.0F + this.rand.nextFloat(), this.rand.nextFloat() * 0.7F + 0.3F, false);
                }

                for (int i = 0; i < 20; ++i) {
                    double d0 = this.rand.nextGaussian() * 0.02D;
                    double d1 = this.rand.nextGaussian() * 0.02D;
                    double d2 = this.rand.nextGaussian() * 0.02D;
                    this.world.addParticle(ParticleTypes.POOF, this.getPosXWidth(1.0D) - d0 * 10.0D, this.getPosYRandom() - d1 * 10.0D, this.getPosZRandom(1.0D) - d2 * 10.0D, d0, d1, d2);
                }
            }
            this.remove();
        }
        --this.lifeTimer;
        super.livingTick();
    }
}