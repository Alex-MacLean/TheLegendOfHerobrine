package com.herobrine.mod.entities;

import com.herobrine.mod.config.Config;
import com.herobrine.mod.util.entities.EntityRegistry;
import com.herobrine.mod.util.savedata.Variables;
import net.minecraft.block.BlockState;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.passive.BatEntity;
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
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.Random;

public class InfectedBatEntity extends AbstractInfectedEntity implements IFlyingAnimal {
    private static final DataParameter<Byte> HANGING = EntityDataManager.createKey(InfectedBatEntity.class, DataSerializers.BYTE);
    private static final EntityPredicate field_213813_c = (new EntityPredicate()).setDistance(4.0D).allowFriendlyFire();
    private BlockPos spawnPosition;
    public InfectedBatEntity(EntityType<InfectedBatEntity> type, World worldIn) {
        super(type, worldIn);
        experienceValue = 3;
        this.setIsBatHanging(true);
    }

    public InfectedBatEntity(World worldIn) {
        this(EntityRegistry.INFECTED_BAT_ENTITY, worldIn);
    }

    @Override
    public boolean attackEntityFrom(@NotNull DamageSource source, float amount) {
        if (source.getImmediateSource() instanceof HolyWaterEntity) {
            BatEntity batEntity = EntityType.BAT.create(this.world);
            assert batEntity != null;
            batEntity.setLocationAndAngles(this.getPosX(), this.getPosY(), this.getPosZ(), this.rotationYaw, this.rotationPitch);
            batEntity.onInitialSpawn(this.world, this.world.getDifficultyForLocation(new BlockPos(batEntity)), SpawnReason.CONVERSION, null, null);
            batEntity.setNoAI(this.isAIDisabled());
            if (this.hasCustomName()) {
                batEntity.setCustomName(this.getCustomName());
                batEntity.setCustomNameVisible(this.isCustomNameVisible());
            }
            batEntity.enablePersistence();
            this.world.setEntityState(this, (byte)16);
            this.world.addEntity(batEntity);
            this.remove();
        }
        if (this.isInvulnerableTo(source)) {
            return false;
        } else {
            if (!this.world.isRemote && this.getIsBatHanging()) {
                this.setIsBatHanging(false);
            }
        }
        return super.attackEntityFrom(source, amount);
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
        this.getAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(6.0D);
        this.getAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(1.0D);
        this.getAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(16.0D);
    }

    protected void registerData() {
        super.registerData();
        this.dataManager.register(HANGING, (byte)0);
    }

    @Override
    protected float getSoundVolume() {
        return 0.1F;
    }

    @Override
    protected float getSoundPitch() {
        return super.getSoundPitch() * 0.95F;
    }

    @Nullable
    @Override
    public SoundEvent getAmbientSound() {
        return this.getIsBatHanging() && this.rand.nextInt(4) != 0 ? null : SoundEvents.ENTITY_BAT_AMBIENT;
    }

    @Override
    protected SoundEvent getHurtSound(@NotNull DamageSource damageSourceIn) {
        return SoundEvents.ENTITY_BAT_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_BAT_DEATH;
    }

    @Override
    public boolean canBePushed() {
        return false;
    }

    @Override
    protected void collideWithEntity(@NotNull Entity entityIn) {
    }

    @Override
    protected void collideWithNearbyEntities() {
    }

    public boolean getIsBatHanging() {
        return (this.dataManager.get(HANGING) & 1) != 0;
    }

    public void setIsBatHanging(boolean isHanging) {
        byte b0 = this.dataManager.get(HANGING);
        if (isHanging) {
            this.dataManager.set(HANGING, (byte)(b0 | 1));
        } else {
            this.dataManager.set(HANGING, (byte)(b0 & -2));
        }

    }

    public void tick() {
        super.tick();
        if (this.getIsBatHanging()) {
            this.setMotion(Vec3d.ZERO);
            this.setRawPosition(this.getPosX(), (double) MathHelper.floor(this.getPosY()) + 1.0D - (double)this.getHeight(), this.getPosZ());
        } else {
            this.setMotion(this.getMotion().mul(1.0D, 0.6D, 1.0D));
        }

    }

    @Override
    protected void updateAITasks() {
        super.updateAITasks();
        BlockPos blockpos = new BlockPos(this);
        BlockPos blockpos1 = blockpos.up();
        if (this.getIsBatHanging()) {
            if (this.world.getBlockState(blockpos1).isNormalCube(this.world, blockpos)) {
                if (this.rand.nextInt(200) == 0) {
                    this.rotationYawHead = (float)this.rand.nextInt(360);
                }

                if (this.world.getClosestPlayer(field_213813_c, this) != null) {
                    this.setIsBatHanging(false);
                    this.world.playEvent(null, 1025, blockpos, 0);
                }
            } else {
                this.setIsBatHanging(false);
                this.world.playEvent(null, 1025, blockpos, 0);
            }
        } else {
            if (this.spawnPosition != null && (!this.world.isAirBlock(this.spawnPosition) || this.spawnPosition.getY() < 1)) {
                this.spawnPosition = null;
            }

            if (this.spawnPosition == null || this.rand.nextInt(30) == 0 || this.spawnPosition.withinDistance(this.getPositionVec(), 2.0D)) {
                this.spawnPosition = new BlockPos(this.getPosX() + (double)this.rand.nextInt(7) - (double)this.rand.nextInt(7), this.getPosY() + (double)this.rand.nextInt(6) - 2.0D, this.getPosZ() + (double)this.rand.nextInt(7) - (double)this.rand.nextInt(7));
            }

            double d0 = (double)this.spawnPosition.getX() + 0.5D - this.getPosX();
            double d1 = (double)this.spawnPosition.getY() + 0.1D - this.getPosY();
            double d2 = (double)this.spawnPosition.getZ() + 0.5D - this.getPosZ();
            Vec3d vec3d = this.getMotion();
            Vec3d vec3d1 = vec3d.add((Math.signum(d0) * 0.5D - vec3d.x) * (double)0.1F, (Math.signum(d1) * (double)0.7F - vec3d.y) * (double)0.1F, (Math.signum(d2) * 0.5D - vec3d.z) * (double)0.1F);
            this.setMotion(vec3d1);
            float f = (float)(MathHelper.atan2(vec3d1.z, vec3d1.x) * (double)(180F / (float)Math.PI)) - 90.0F;
            float f1 = MathHelper.wrapDegrees(f - this.rotationYaw);
            if(this.getAttackTarget() != null) {
                this.moveToEntity(this.getAttackTarget());
            } else {
                this.moveForward = 0.5F;
            }
            this.rotationYaw += f1;
            if (this.rand.nextInt(100) == 0 && this.world.getBlockState(blockpos1).isNormalCube(this.world, blockpos1)) {
                this.setIsBatHanging(true);
            }
        }
    }

    public void moveToEntity(@NotNull Entity entity) {
        Vec3d motion = new Vec3d((entity.getPosX() - this.getPosX()) / 10, ((entity.getPosY() + 1) - this.getPosY()) / 10,(entity.getPosZ() - this.getPosZ()) / 10);
        this.setMotion(motion);
    }

    @Override
    protected boolean canTriggerWalking() {
        return false;
    }

    @Override
    public boolean onLivingFall(float distance, float damageMultiplier) {
        return false;
    }

    @Override
    protected void updateFallState(double y, boolean onGroundIn, @NotNull BlockState state, @NotNull BlockPos pos) {
    }

    @Override
    public boolean doesEntityNotTriggerPressurePlate() {
        return true;
    }

    @Override
    public void readAdditional(@NotNull CompoundNBT compound) {
        super.readAdditional(compound);
        this.dataManager.set(HANGING, compound.getByte("BatFlags"));
    }

    @Override
    public void writeAdditional(@NotNull CompoundNBT compound) {
        super.writeAdditional(compound);
        compound.putByte("BatFlags", this.dataManager.get(HANGING));
    }

    @Override
    protected float getStandingEyeHeight(@NotNull Pose poseIn, @NotNull EntitySize sizeIn) {
        return sizeIn.height / 2.0F;
    }

    public static boolean canSpawn(EntityType<? extends AbstractInfectedEntity> batIn, @NotNull IWorld worldIn, SpawnReason reason, @NotNull BlockPos pos, Random randomIn) {
        if (pos.getY() >= worldIn.getSeaLevel() || !Variables.SaveData.get(worldIn.getWorld()).Spawn && !Config.COMMON.HerobrineAlwaysSpawns.get()) {
            return false;
        } else {
            int i = worldIn.getLight(pos);
            int j = 4;
            if (randomIn.nextBoolean()) {
                return false;
            }
            return i <= randomIn.nextInt(j) && canSpawnOn(batIn, worldIn, reason, pos, randomIn);
        }
    }

    @Override
    public @NotNull ResourceLocation getLootTable() {
        return EntityType.BAT.getLootTable();
    }
}
