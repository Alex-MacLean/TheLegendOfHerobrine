package com.herobrine.mod.entities;

import com.herobrine.mod.util.entities.EntityRegistry;
import com.herobrine.mod.util.savedata.SaveDataUtil;
import net.minecraft.block.BlockState;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.passive.GolemEntity;
import net.minecraft.entity.passive.IFlyingAnimal;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.Random;

public class InfectedBatEntity extends AbstractInfectedEntity implements IFlyingAnimal {
    private static final DataParameter<Byte> HANGING = EntityDataManager.defineId(InfectedBatEntity.class, DataSerializers.BYTE);
    private static final EntityPredicate BAT_RESTING_TARGETING = (new EntityPredicate()).range(4.0D).allowSameTeam();
    private BlockPos spawnPosition;

    public InfectedBatEntity(EntityType<InfectedBatEntity> type, World worldIn) {
        super(type, worldIn);
        xpReward = 3;
        this.setIsBatHanging(true);
    }

    public InfectedBatEntity(World worldIn) {
        this(EntityRegistry.INFECTED_BAT_ENTITY, worldIn);
    }

    public static AttributeModifierMap.MutableAttribute registerAttributes() {
        return MonsterEntity.createMonsterAttributes()
                .add(Attributes.MAX_HEALTH, 6.0D)
                .add(Attributes.ATTACK_DAMAGE, 1.0D)
                .add(Attributes.FOLLOW_RANGE, 16.0D);
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

    public static boolean canSpawn(EntityType<? extends AbstractInfectedEntity> batIn, @NotNull IServerWorld worldIn, SpawnReason reason, @NotNull BlockPos pos, Random randomIn) {
        if (pos.getY() >= worldIn.getSeaLevel() || !SaveDataUtil.canHerobrineSpawn(worldIn)) {
            return false;
        } else {
            int i = worldIn.getMaxLocalRawBrightness(pos);
            int j = 4;
            if (randomIn.nextBoolean()) {
                return false;
            }
            return i <= randomIn.nextInt(j) && checkMobSpawnRules(batIn, worldIn, reason, pos, randomIn);
        }
    }

    @Override
    public boolean hurt(@NotNull DamageSource source, float amount) {
        if (source.getDirectEntity() instanceof HolyWaterEntity) {
            MobEntity entity = this.convertTo(EntityType.BAT, false);
            assert entity != null;
            entity.finalizeSpawn((IServerWorld) this.level, this.level.getCurrentDifficultyAt(entity.blockPosition()), SpawnReason.CONVERSION, null, null);
            this.level.broadcastEntityEvent(this, (byte) 16);
        }
        if (!this.level.isClientSide && this.getIsBatHanging() && !this.isInvulnerableTo(source)) {
            this.setIsBatHanging(false);
        }
        return super.hurt(source, amount);
    }

    @Override
    protected float getSoundVolume() {
        return 0.1F;
    }

    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(HANGING, (byte) 0);
    }

    @Override
    protected float getVoicePitch() {
        return super.getVoicePitch() * 0.95F;
    }

    @Nullable
    @Override
    public SoundEvent getAmbientSound() {
        return this.getIsBatHanging() && this.random.nextInt(4) != 0 ? null : SoundEvents.BAT_AMBIENT;
    }

    @Override
    protected SoundEvent getHurtSound(@NotNull DamageSource damageSourceIn) {
        return SoundEvents.BAT_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.BAT_DEATH;
    }

    @Override
    public boolean isPushable() {
        return false;
    }

    @Override
    protected void doPush(@NotNull Entity entity) {
    }

    @Override
    protected void pushEntities() {
    }

    public boolean getIsBatHanging() {
        return (this.entityData.get(HANGING) & 1) != 0;
    }

    public void setIsBatHanging(boolean isHanging) {
        byte b0 = this.entityData.get(HANGING);
        if (isHanging) {
            this.entityData.set(HANGING, (byte) (b0 | 1));
        } else {
            this.entityData.set(HANGING, (byte) (b0 & -2));
        }

    }

    public void aiStep() {
        super.aiStep();
        if (this.getIsBatHanging()) {
            this.setDeltaMovement(Vector3d.ZERO);
            this.setPosRaw(this.getX(), (double) MathHelper.floor(this.getY()) + 1.0D - (double) this.getBbHeight(), this.getZ());
        } else {
            this.setDeltaMovement(this.getDeltaMovement().multiply(1.0D, 0.6D, 1.0D));
        }

    }

    protected void customServerAiStep() {
        super.customServerAiStep();
        BlockPos blockpos = this.blockPosition();
        BlockPos blockpos1 = blockpos.above();
        if (this.getIsBatHanging()) {
            boolean flag = this.isSilent();
            if (this.level.getBlockState(blockpos1).isRedstoneConductor(this.level, blockpos)) {
                if (this.random.nextInt(200) == 0) {
                    this.yHeadRot = (float) this.random.nextInt(360);
                }

                if (this.level.getNearestPlayer(BAT_RESTING_TARGETING, this) != null) {
                    this.setIsBatHanging(false);
                    if (!flag) {
                        this.level.levelEvent(null, 1025, blockpos, 0);
                    }
                }
            } else {
                this.setIsBatHanging(false);
                if (!flag) {
                    this.level.levelEvent(null, 1025, blockpos, 0);
                }
            }
        } else {
            if (this.spawnPosition != null && (!this.level.isEmptyBlock(this.spawnPosition) || this.spawnPosition.getY() < 1)) {
                this.spawnPosition = null;
            }

            if (this.spawnPosition == null || this.random.nextInt(30) == 0 || this.spawnPosition.closerThan(this.position(), 2.0D)) {
                this.spawnPosition = new BlockPos(this.getX() + (double) this.random.nextInt(7) - (double) this.random.nextInt(7), this.getY() + (double) this.random.nextInt(6) - 2.0D, this.getZ() + (double) this.random.nextInt(7) - (double) this.random.nextInt(7));
            }

            double d2 = (double) this.spawnPosition.getX() + 0.5D - this.getX();
            double d0 = (double) this.spawnPosition.getY() + 0.1D - this.getY();
            double d1 = (double) this.spawnPosition.getZ() + 0.5D - this.getZ();
            Vector3d vector3d = this.getDeltaMovement();
            Vector3d vector3d1 = vector3d.add((Math.signum(d2) * 0.5D - vector3d.x) * (double) 0.1F, (Math.signum(d0) * (double) 0.7F - vector3d.y) * (double) 0.1F, (Math.signum(d1) * 0.5D - vector3d.z) * (double) 0.1F);
            this.setDeltaMovement(vector3d1);
            float f = (float) (MathHelper.atan2(vector3d1.z, vector3d1.x) * (double) (180F / (float) Math.PI)) - 90.0F;
            float f1 = MathHelper.wrapDegrees(f - this.yRot);
            if (this.getTarget() != null) {
                this.moveToEntity(this.getTarget());
            } else {
                this.zza = 0.5F;
            }
            this.yRot += f1;
            if (this.random.nextInt(100) == 0 && this.level.getBlockState(blockpos1).isRedstoneConductor(this.level, blockpos1)) {
                this.setIsBatHanging(true);
            }
        }
    }

    public void moveToEntity(@NotNull Entity entity) {
        Vector3d motion = new Vector3d((entity.getX() - this.getX()) / 10, ((entity.getY() + 1) - this.getY()) / 10, (entity.getZ() - this.getZ()) / 10);
        this.setDeltaMovement(motion);
    }

    protected boolean isMovementNoisy() {
        return false;
    }

    public boolean causeFallDamage(float p_225503_1_, float p_225503_2_) {
        return false;
    }

    protected void checkFallDamage(double p_184231_1_, boolean p_184231_3_, @NotNull BlockState p_184231_4_, @NotNull BlockPos p_184231_5_) {
    }

    public boolean isIgnoringBlockTriggers() {
        return true;
    }

    public void readAdditionalSaveData(@NotNull CompoundNBT p_70037_1_) {
        super.readAdditionalSaveData(p_70037_1_);
        this.entityData.set(HANGING, p_70037_1_.getByte("BatFlags"));
    }

    @Override
    protected float getStandingEyeHeight(@NotNull Pose poseIn, @NotNull EntitySize sizeIn) {
        return sizeIn.height / 2.0F;
    }

    public void addAdditionalSaveData(@NotNull CompoundNBT p_213281_1_) {
        super.addAdditionalSaveData(p_213281_1_);
        p_213281_1_.putByte("BatFlags", this.entityData.get(HANGING));
    }

    @Override
    public @NotNull ResourceLocation getDefaultLootTable() {
        return EntityType.BAT.getDefaultLootTable();
    }
}
