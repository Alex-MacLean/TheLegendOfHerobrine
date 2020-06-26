package com.herobrine.mod.entities;

import com.herobrine.mod.util.entities.EntityRegistry;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.passive.horse.DonkeyEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

public class InfectedDonkeyEntity extends AbstractInfectedEntity {
    public int tailCounter;
    private float prevHeadLean;
    private float headLean;
    private float rearingAmount;
    private float prevRearingAmount;
    public float mouthOpenness;
    public float prevMouthOpenness;
    public InfectedDonkeyEntity(EntityType<? extends InfectedDonkeyEntity> type, World worldIn) {
        super(type, worldIn);
        experienceValue = 3;
    }

    public InfectedDonkeyEntity(World worldIn) {
        this(EntityRegistry.INFECTED_DONKEY_ENTITY, worldIn);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new SwimGoal(this));
        this.goalSelector.addGoal(1, new MeleeAttackGoal(this, 0.85D, true));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, PlayerEntity.class, true));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, AbstractSurvivorEntity.class, true));
        this.targetSelector.addGoal(4, new HurtByTargetGoal(this));
        this.goalSelector.addGoal(5, new WaterAvoidingRandomWalkingGoal(this, 0.43D));
        this.goalSelector.addGoal(6, new LookAtGoal(this, PlayerEntity.class, 8.0F));
        this.goalSelector.addGoal(7, new LookAtGoal(this, AbstractSurvivorEntity.class, 8.0F));
        this.goalSelector.addGoal(8, new LookRandomlyGoal(this));
    }

    @Override
    protected void registerAttributes() {
        super.registerAttributes();
        this.getAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(25.0D);
        this.getAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(3.0D);
        this.getAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(16.0D);
        this.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.35D);
    }

    @Override
    public boolean attackEntityFrom(@NotNull DamageSource source, float amount) {
        if (source.getImmediateSource() instanceof HolyWaterEntity) {
            DonkeyEntity donkeyEntity = EntityType.DONKEY.create(this.world);
            assert donkeyEntity != null;
            donkeyEntity.setLocationAndAngles(this.getPosX(), this.getPosY(), this.getPosZ(), this.rotationYaw, this.rotationPitch);
            donkeyEntity.onInitialSpawn(this.world, this.world.getDifficultyForLocation(new BlockPos(donkeyEntity)), SpawnReason.CONVERSION, null, null);
            donkeyEntity.setNoAI(this.isAIDisabled());
            if (this.hasCustomName()) {
                donkeyEntity.setCustomName(this.getCustomName());
                donkeyEntity.setCustomNameVisible(this.isCustomNameVisible());
            }
            donkeyEntity.enablePersistence();
            this.world.setEntityState(this, (byte)16);
            this.world.addEntity(donkeyEntity);
            this.remove();
        }
        return super.attackEntityFrom(source, amount);
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.ENTITY_DONKEY_AMBIENT;
    }

    @Override
    protected SoundEvent getHurtSound(@NotNull DamageSource damageSourceIn) {
        return SoundEvents.ENTITY_DONKEY_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_DONKEY_DEATH;
    }

    @Override
    protected void playStepSound(@NotNull BlockPos pos, @NotNull BlockState blockIn) {
        this.playSound(SoundEvents.ENTITY_HORSE_STEP, 0.15F, 1.0F);
    }

    public boolean isHorseSaddled() {
        return false;
    }

    @Override
    public void tick() {
        super.tick();
        if (this.rand.nextInt(200) == 0) {
            this.moveTail();
        }

        if (this.tailCounter > 0 && ++this.tailCounter > 8) {
            this.tailCounter = 0;
        }

        this.prevHeadLean = this.headLean;
        this.headLean += (0.0F - this.headLean) * 0.4F - 0.05F;
        if (this.headLean < 0.0F) {
            this.headLean = 0.0F;
        }

        this.prevRearingAmount = this.rearingAmount;
        if (this.isRearing()) {
            this.headLean = 0.0F;
            this.prevHeadLean = this.headLean;
            this.rearingAmount += (1.0F - this.rearingAmount) * 0.4F + 0.05F;
            if (this.rearingAmount > 1.0F) {
                this.rearingAmount = 1.0F;
            }
        } else {
            this.rearingAmount += (0.8F * this.rearingAmount * this.rearingAmount * this.rearingAmount - this.rearingAmount) * 0.6F - 0.05F;
            if (this.rearingAmount < 0.0F) {
                this.rearingAmount = 0.0F;
            }
        }
    }

    private void moveTail() {
        this.tailCounter = 1;
    }

    public boolean isRearing() {
        return false;
    }

    @OnlyIn(Dist.CLIENT)
    public float getGrassEatingAmount(float p_110258_1_) {
        return MathHelper.lerp(p_110258_1_, this.prevHeadLean, this.headLean);
    }

    @OnlyIn(Dist.CLIENT)
    public float getRearingAmount(float p_110223_1_) {
        return MathHelper.lerp(p_110223_1_, this.prevRearingAmount, this.rearingAmount);
    }

    @OnlyIn(Dist.CLIENT)
    public float getMouthOpennessAngle(float p_110201_1_) {
        return MathHelper.lerp(p_110201_1_, this.prevMouthOpenness, this.mouthOpenness);
    }
}
