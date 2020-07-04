package com.herobrine.mod.entities;

import com.herobrine.mod.util.entities.EntityRegistry;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.passive.ChickenEntity;
import net.minecraft.entity.passive.GolemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;

public class InfectedChickenEntity extends AbstractInfectedEntity {
    public float wingRotation;
    public float destPos;
    public float oFlapSpeed;
    public float oFlap;
    public float wingRotDelta = 1.0F;

    public InfectedChickenEntity(EntityType<? extends InfectedChickenEntity> type, World worldIn) {
        super(type, worldIn);
        experienceValue = 3;
    }

    @Override
    public boolean attackEntityFrom(@NotNull DamageSource source, float amount) {
        if (source.getImmediateSource() instanceof HolyWaterEntity) {
            ChickenEntity chickenEntity = EntityType.CHICKEN.create(this.world);
            assert chickenEntity != null;
            chickenEntity.setLocationAndAngles(this.posX, this.posY, this.posZ, this.rotationYaw, this.rotationPitch);
            chickenEntity.onInitialSpawn(this.world, this.world.getDifficultyForLocation(new BlockPos(chickenEntity)), SpawnReason.CONVERSION, null, null);
            chickenEntity.setNoAI(this.isAIDisabled());
            if (this.hasCustomName()) {
                chickenEntity.setCustomName(this.getCustomName());
                chickenEntity.setCustomNameVisible(this.isCustomNameVisible());
            }
            chickenEntity.enablePersistence();
            chickenEntity.setGrowingAge(0);
            this.world.setEntityState(this, (byte)16);
            this.world.addEntity(chickenEntity);
            this.remove();
        }
        return super.attackEntityFrom(source, amount);
    }

    public InfectedChickenEntity(World worldIn) {
        this(EntityRegistry.INFECTED_CHICKEN_ENTITY, worldIn);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new SwimGoal(this));
        this.goalSelector.addGoal(1, new MeleeAttackGoal(this, 1.0D, true));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, PlayerEntity.class, true));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, AbstractSurvivorEntity.class, true));
        this.targetSelector.addGoal(4, new NearestAttackableTargetGoal<>(this, GolemEntity.class, true));
        this.targetSelector.addGoal(5, new HurtByTargetGoal(this));
        this.goalSelector.addGoal(6, new WaterAvoidingRandomWalkingGoal(this, 1.0D));
        this.goalSelector.addGoal(7, new LookAtGoal(this, PlayerEntity.class, 8.0F));
        this.goalSelector.addGoal(8, new LookAtGoal(this, AbstractSurvivorEntity.class, 8.0F));
        this.goalSelector.addGoal(9, new LookAtGoal(this, GolemEntity.class, 8.0F));
        this.goalSelector.addGoal(10, new LookRandomlyGoal(this));
    }

    @Override
    protected void registerAttributes() {
        super.registerAttributes();
        this.getAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(4.0D);
        this.getAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(1.0D);
        this.getAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(16.0D);
        this.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.25D);
    }

    @Override
    public void livingTick() {
        super.livingTick();
        this.oFlap = this.wingRotation;
        this.oFlapSpeed = this.destPos;
        this.destPos = (float)((double)this.destPos + (double)(this.onGround ? -1 : 4) * 0.3D);
        this.destPos = MathHelper.clamp(this.destPos, 0.0F, 1.0F);
        if (!this.onGround && this.wingRotDelta < 1.0F) {
            this.wingRotDelta = 1.0F;
        }

        this.wingRotDelta = (float)((double)this.wingRotDelta * 0.9D);
        Vec3d vec3d = this.getMotion();
        if (!this.onGround && vec3d.y < 0.0D) {
            this.setMotion(vec3d.mul(1.0D, 0.6D, 1.0D));
        }

        this.wingRotation += this.wingRotDelta * 2.0F;
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.ENTITY_CHICKEN_AMBIENT;
    }

    @Override
    protected SoundEvent getHurtSound(@NotNull DamageSource damageSourceIn) {
        return SoundEvents.ENTITY_CHICKEN_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_CHICKEN_DEATH;
    }

    @Override
    protected void playStepSound(@NotNull BlockPos pos, @NotNull BlockState blockIn) {
        this.playSound(SoundEvents.ENTITY_CHICKEN_STEP, 0.15F, 1.0F);
    }

    @Override
    public void fall(float distance, float damageMultiplier) {
    }
}
