package com.herobrine.mod.entities;

import com.herobrine.mod.util.entities.EntityRegistry;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.controller.JumpController;
import net.minecraft.entity.ai.controller.MovementController;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.passive.GolemEntity;
import net.minecraft.entity.passive.RabbitEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.pathfinding.Path;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

public class InfectedRabbitEntity extends AbstractInfectedEntity {
    private static final DataParameter<Integer> RABBIT_TYPE = EntityDataManager.defineId(InfectedRabbitEntity.class, DataSerializers.INT);
    private int jumpTicks;
    private int jumpDuration;
    private boolean wasOnGround;
    private int currentMoveTypeDuration;
    public InfectedRabbitEntity(EntityType<? extends InfectedRabbitEntity> type, World worldIn) {
        super(type, worldIn);
        this.jumpControl = new JumpHelperController(this);
        this.moveControl = new InfectedRabbitEntity.MoveHelperController(this);
        this.setSpeedModifier(0.0D);
        xpReward = 3;
    }

    public InfectedRabbitEntity(World worldIn) {
        this(EntityRegistry.INFECTED_RABBIT_ENTITY, worldIn);
    }

    public static AttributeModifierMap.MutableAttribute registerAttributes() {
        return MonsterEntity.createMonsterAttributes()
                .add(Attributes.MAX_HEALTH, 3.0D)
                .add(Attributes.ATTACK_DAMAGE, 1.0D)
                .add(Attributes.FOLLOW_RANGE, 16.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.3D);
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
    public boolean hurt(@NotNull DamageSource source, float amount) {
        if (source.getDirectEntity() instanceof HolyWaterEntity) {
            RabbitEntity entity = this.convertTo(EntityType.RABBIT, false);
            assert entity != null;
            entity.finalizeSpawn((IServerWorld) this.level, this.level.getCurrentDifficultyAt(entity.blockPosition()), SpawnReason.CONVERSION, null, null);
            entity.setRabbitType(this.getRabbitType());
            this.level.broadcastEntityEvent(this, (byte) 16);
        }
        return super.hurt(source, amount);
    }

    protected float getJumpPower() {
        if (!this.horizontalCollision && (!this.moveControl.hasWanted() || !(this.moveControl.getWantedY() > this.getY() + 0.5D))) {
            Path path = this.navigation.getPath();
            if (path != null && !path.isDone()) {
                Vector3d vector3d = path.getNextEntityPos(this);
                if (vector3d.y > this.getY() + 0.5D) {
                    return 0.5F;
                }
            }

            return this.moveControl.getSpeedModifier() <= 0.6D ? 0.2F : 0.3F;
        } else {
            return 0.5F;
        }
    }

    protected void jumpFromGround() {
        super.jumpFromGround();
        double d0 = this.moveControl.getSpeedModifier();
        if (d0 > 0.0D) {
            double d1 = getHorizontalDistanceSqr(this.getDeltaMovement());
            if (d1 < 0.01D) {
                this.moveRelative(0.1F, new Vector3d(0.0D, 0.0D, 1.0D));
            }
        }
        if (!this.level.isClientSide) {
            this.level.broadcastEntityEvent(this, (byte) 1);
        }
    }

    @OnlyIn(Dist.CLIENT)
    public float getJumpCompletion(float p_175521_1_) {
        return this.jumpDuration == 0 ? 0.0F : ((float) this.jumpTicks + p_175521_1_) / (float) this.jumpDuration;
    }


    public void setSpeedModifier(double p_175515_1_) {
        this.getNavigation().setSpeedModifier(p_175515_1_);
        this.moveControl.setWantedPosition(this.moveControl.getWantedX(), this.moveControl.getWantedY(), this.moveControl.getWantedZ(), p_175515_1_);
    }

    @Override
    public void setJumping(boolean jumping) {
        super.setJumping(jumping);
        if (jumping) {
            this.playSound(this.getJumpSound(), this.getSoundVolume(), ((this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F) * 0.8F);
        }

    }

    public void startJumping() {
        this.setJumping(true);
        this.jumpDuration = 10;
        this.jumpTicks = 0;
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(RABBIT_TYPE, 0);
    }

    public int getRabbitType() {
        return this.entityData.get(RABBIT_TYPE);
    }

    public void setRabbitType(int rabbitTypeId) {
        this.entityData.set(RABBIT_TYPE, rabbitTypeId);
    }

    @Override
    public void customServerAiStep() {
        if (this.currentMoveTypeDuration > 0) {
            --this.currentMoveTypeDuration;
        }

        if (this.onGround) {
            if (!this.wasOnGround) {
                this.setJumping(false);
                this.checkLandingDelay();
            }

            if (this.getRabbitType() == 99 && this.currentMoveTypeDuration == 0) {
                LivingEntity livingentity = this.getTarget();
                if (livingentity != null && this.distanceToSqr(livingentity) < 16.0D) {
                    this.calculateRotationYaw(livingentity.getX(), livingentity.getZ());
                    this.moveControl.setWantedPosition(livingentity.getX(), livingentity.getY(), livingentity.getZ(), this.moveControl.getSpeedModifier());
                    this.startJumping();
                    this.wasOnGround = true;
                }
            }

            InfectedRabbitEntity.JumpHelperController infectedrabbitentity$jumphelpercontroller = (InfectedRabbitEntity.JumpHelperController) this.jumpControl;
            if (infectedrabbitentity$jumphelpercontroller.getIsNotJumping()) {
                if (this.moveControl.hasWanted() && this.currentMoveTypeDuration == 0) {
                    Path path = this.navigation.getPath();
                    Vector3d vec3d = new Vector3d(this.moveControl.getWantedX(), this.moveControl.getWantedY(), this.moveControl.getWantedZ());
                    if (path != null && path.isDone()) {
                        vec3d = path.getNextEntityPos(this);
                    }

                    this.calculateRotationYaw(vec3d.x, vec3d.z);
                    this.startJumping();
                }
            } else if (!infectedrabbitentity$jumphelpercontroller.canJump()) {
                this.enableJumpControl();
            }
        }

        this.wasOnGround = this.onGround;
    }

    @Override
    public void aiStep() {
        super.aiStep();
        if (this.jumpTicks != this.jumpDuration) {
            ++this.jumpTicks;
        } else if (this.jumpDuration != 0) {
            this.jumpTicks = 0;
            this.jumpDuration = 0;
            this.setJumping(false);
        }

    }

    private void calculateRotationYaw(double x, double z) {
        this.yRot = (float) (MathHelper.atan2(z - this.getZ(), x - this.getX()) * (double) (180F / (float) Math.PI)) - 90.0F;
    }

    private void enableJumpControl() {
        ((InfectedRabbitEntity.JumpHelperController) this.jumpControl).setCanJump(true);
    }

    private void disableJumpControl() {
        ((InfectedRabbitEntity.JumpHelperController) this.jumpControl).setCanJump(false);
    }

    private void checkLandingDelay() {
        this.updateMoveTypeDuration();
        this.disableJumpControl();
    }

    private void updateMoveTypeDuration() {
        if (this.moveControl.getSpeedModifier() < 2.2D) {
            this.currentMoveTypeDuration = 10;
        } else {
            this.currentMoveTypeDuration = 1;
        }

    }

    @Override
    public void addAdditionalSaveData(@NotNull CompoundNBT compound) {
        super.addAdditionalSaveData(compound);
        compound.putInt("RabbitType", this.getRabbitType());
    }

    @Override
    public void readAdditionalSaveData(@NotNull CompoundNBT compound) {
        super.readAdditionalSaveData(compound);
        this.setRabbitType(compound.getInt("RabbitType"));
    }

    @Override
    public boolean doHurtTarget(@NotNull Entity entityIn) {
        if (this.getRabbitType() == 99) {
            this.playSound(SoundEvents.RABBIT_ATTACK, 1.0F, (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F);
            return entityIn.hurt(DamageSource.mobAttack(this), 8.0F);
        } else {
            return entityIn.hurt(DamageSource.mobAttack(this), 3.0F);
        }
    }

    @Nullable
    @Override
    public ILivingEntityData finalizeSpawn(@NotNull IServerWorld worldIn, @NotNull DifficultyInstance difficultyIn, @NotNull SpawnReason reason, @Nullable ILivingEntityData spawnDataIn, @Nullable CompoundNBT dataTag) {
        int i = this.getRandomRabbitType(worldIn);
        if (spawnDataIn instanceof InfectedRabbitEntity.RabbitData) {
            i = ((InfectedRabbitEntity.RabbitData) spawnDataIn).typeData;
        } else {
            spawnDataIn = new InfectedRabbitEntity.RabbitData(i);
        }

        this.setRabbitType(i);
        return super.finalizeSpawn(worldIn, difficultyIn, reason, spawnDataIn, dataTag);
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void handleEntityEvent(byte id) {
        if (id == 1) {
            this.spawnSprintParticle();
            this.jumpDuration = 10;
            this.jumpTicks = 0;
        } else {
            super.handleEntityEvent(id);
        }

    }

    private int getRandomRabbitType(@NotNull IWorld p_213610_1_) {
        Biome biome = p_213610_1_.getBiome(this.blockPosition());
        int i = this.random.nextInt(100);
        if (biome.getPrecipitation() == Biome.RainType.SNOW) {
            return i < 80 ? 1 : 3;
        } else if (biome.getBiomeCategory() == Biome.Category.DESERT) {
            return 4;
        } else {
            return i < 50 ? 0 : (i < 90 ? 5 : 2);
        }
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.RABBIT_AMBIENT;
    }

    @Override
    protected SoundEvent getHurtSound(@NotNull DamageSource damageSourceIn) {
        return SoundEvents.RABBIT_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.RABBIT_DEATH;
    }

    protected SoundEvent getJumpSound() {
        return SoundEvents.RABBIT_JUMP;
    }

    @Override
    public @NotNull ResourceLocation getDefaultLootTable() {
        return EntityType.RABBIT.getDefaultLootTable();
    }

    public static class JumpHelperController extends JumpController {
        private final InfectedRabbitEntity rabbit;
        private boolean canJump;

        public JumpHelperController(InfectedRabbitEntity rabbit) {
            super(rabbit);
            this.rabbit = rabbit;
        }

        public boolean getIsNotJumping() {
            return !this.jump;
        }

        public boolean canJump() {
            return this.canJump;
        }

        public void setCanJump(boolean canJumpIn) {
            this.canJump = canJumpIn;
        }

        @Override
        public void tick() {
            if (this.jump) {
                this.rabbit.startJumping();
                this.jump = false;
            }

        }
    }

    public static class RabbitData extends AgeableEntity.AgeableData {
        public final int typeData;

        public RabbitData(int type) {
            super(false);
            this.typeData = type;
        }
    }

    static class MoveHelperController extends MovementController {
        private final InfectedRabbitEntity rabbit;
        private double nextJumpSpeed;

        public MoveHelperController(InfectedRabbitEntity rabbit) {
            super(rabbit);
            this.rabbit = rabbit;
        }

        @Override
        public void tick() {
            if (this.rabbit.onGround && !this.rabbit.jumping && !((JumpHelperController) this.rabbit.jumpControl).canJump) {
                this.rabbit.setSpeedModifier(0.0D);
            } else if (this.hasWanted()) {
                this.rabbit.setSpeedModifier(this.nextJumpSpeed);
            }

            super.tick();
        }

        @Override
        public void setWantedPosition(double p_75642_1_, double p_75642_3_, double p_75642_5_, double p_75642_7_) {
            if (this.rabbit.isInWater()) {
                p_75642_7_ = 1.5D;
            }

            super.setWantedPosition(p_75642_1_, p_75642_3_, p_75642_5_, p_75642_7_);
            if (p_75642_7_ > 0.0D) {
                this.nextJumpSpeed = p_75642_7_;
            }

        }
    }
}