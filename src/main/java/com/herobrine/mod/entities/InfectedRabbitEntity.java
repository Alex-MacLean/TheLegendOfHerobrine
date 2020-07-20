package com.herobrine.mod.entities;

import com.herobrine.mod.config.Config;
import com.herobrine.mod.util.savedata.Variables;
import net.minecraft.entity.Entity;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.monster.EntityGolem;
import net.minecraft.entity.passive.EntityRabbit;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.pathfinding.Path;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeDesert;
import net.minecraft.world.storage.loot.LootTableList;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

public class InfectedRabbitEntity extends AbstractInfectedEntity {
    private static final DataParameter<Integer> RABBIT_TYPE = EntityDataManager.createKey(InfectedRabbitEntity.class, DataSerializers.VARINT);
    private int jumpTicks;
    private int jumpDuration;
    private boolean wasOnGround;
    private int currentMoveTypeDuration;
    public InfectedRabbitEntity(World worldIn) {
        super(worldIn);
        this.jumpHelper = new RabbitJumpHelper(this);
        this.moveHelper = new InfectedRabbitEntity.RabbitMoveHelper(this);
        this.experienceValue = 3;
        this.setSize(0.4F, 0.5F);
    }

    @Override
    public boolean attackEntityFrom(@NotNull DamageSource source, float amount) {
        if (source.getImmediateSource() instanceof HolyWaterEntity) {
            EntityRabbit rabbitEntity = new EntityRabbit(this.world);
            rabbitEntity.copyLocationAndAnglesFrom(this);
            rabbitEntity.onInitialSpawn(this.world.getDifficultyForLocation(new BlockPos(rabbitEntity)), null);
            rabbitEntity.setNoAI(this.isAIDisabled());
            if (this.hasCustomName()) {
                rabbitEntity.setCustomNameTag(this.getCustomNameTag());
                rabbitEntity.setAlwaysRenderNameTag(this.getAlwaysRenderNameTag());
            }
            rabbitEntity.enablePersistence();
            rabbitEntity.setRabbitType(this.getRabbitType());
            rabbitEntity.setGrowingAge(0);
            this.world.setEntityState(this, (byte)16);
            this.world.spawnEntity(rabbitEntity);
            this.world.removeEntity(this);
        }
        return super.attackEntityFrom(source, amount);
    }

    @Override
    public void initEntityAI() {
        super.initEntityAI();
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.targetTasks.addTask(1, new EntityAINearestAttackableTarget<>(this, EntityPlayer.class, true));
        this.targetTasks.addTask(2, new EntityAINearestAttackableTarget<>(this, AbstractSurvivorEntity.class, true));
        this.targetTasks.addTask(3, new EntityAINearestAttackableTarget<>(this, EntityGolem.class, true));
        this.targetTasks.addTask(4, new EntityAIHurtByTarget(this, false));
        this.tasks.addTask(5, new EntityAIAttackMelee(this, 1.0d, true));
        this.tasks.addTask(6, new EntityAIWanderAvoidWater(this, 1.0d));
        this.tasks.addTask(7, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0f));
        this.tasks.addTask(8, new EntityAIWatchClosest(this, AbstractSurvivorEntity.class, 8.0f));
        this.tasks.addTask(9, new EntityAIWatchClosest(this, EntityGolem.class, 8.0f));
        this.tasks.addTask(10, new EntityAILookIdle(this));
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(3.0D);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(1.0D);
        this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(16.0D);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.3D);
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.ENTITY_RABBIT_AMBIENT;
    }

    @Override
    protected SoundEvent getHurtSound(@NotNull DamageSource damageSourceIn) {
        return SoundEvents.ENTITY_RABBIT_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
    return SoundEvents.ENTITY_RABBIT_DEATH;
    }

    @Nullable
    @Override
    protected ResourceLocation getLootTable() {
        return LootTableList.ENTITIES_RABBIT;
    }

    public boolean hasViewOfSky() {
        return world.canSeeSky(new BlockPos(this.posX, this.getEntityBoundingBox().minY, this.posZ));
    }

    @Override
    public boolean getCanSpawnHere() {
        return super.getCanSpawnHere() && hasViewOfSky() && Variables.SaveData.get(world).Spawn || super.getCanSpawnHere() && hasViewOfSky() && Config.HerobrineAlwaysSpawns;
    }

    @Override
    protected float getJumpUpwardsMotion() {
        if (!this.collidedHorizontally && (!this.moveHelper.isUpdating() || this.moveHelper.getY() <= this.posY + 0.5D)) {
            Path path = this.navigator.getPath();
            if (path != null && path.getCurrentPathIndex() < path.getCurrentPathLength()) {
                Vec3d vec3d = path.getPosition(this);
                if (vec3d.y > this.posY + 0.5D) {
                    return 0.5F;
                }
            }
            return this.moveHelper.getSpeed() <= 0.6D ? 0.2F : 0.3F;
        }else {
            return 0.5F;
        }
    }

    @Override
    protected void jump() {
        super.jump();
        double d0 = this.moveHelper.getSpeed();

        if (d0 > 0.0D) {
            double d1 = this.motionX * this.motionX + this.motionZ * this.motionZ;

            if (d1 < 0.010000000000000002D) {
                this.moveRelative(0.0F, 0.0F, 1.0F, 0.1F);
            }
        }

        if (!this.world.isRemote) {
            this.world.setEntityState(this, (byte)1);
        }
    }

    @SideOnly(Side.CLIENT)
    public float getJumpCompletion(float p_175521_1_) {
        return this.jumpDuration == 0 ? 0.0F : ((float)this.jumpTicks + p_175521_1_) / (float)this.jumpDuration;
    }

    public void setMovementSpeed(double newSpeed) {
        this.getNavigator().setSpeed(newSpeed);
        this.moveHelper.setMoveTo(this.moveHelper.getX(), this.moveHelper.getY(), this.moveHelper.getZ(), newSpeed);
    }

    @Override
    public void setJumping(boolean jumping) {
        super.setJumping(jumping);

        if (jumping) {
            this.playSound(this.getJumpSound(), this.getSoundVolume(), ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.0F) * 0.8F);
        }
    }

    public void startJumping() {
        this.setJumping(true);
        this.jumpDuration = 10;
        this.jumpTicks = 0;
    }

    @Override
    protected void entityInit() {
        super.entityInit();
        this.dataManager.register(RABBIT_TYPE, 0);
    }

    public int getRabbitType() {
        return this.dataManager.get(RABBIT_TYPE);
    }

    @Override
    public void updateAITasks() {
        if (this.currentMoveTypeDuration > 0) {
            --this.currentMoveTypeDuration;
        }
        if (this.onGround) {
            if (!this.wasOnGround) {
                this.setJumping(false);
                this.checkLandingDelay();
            }
            InfectedRabbitEntity.RabbitJumpHelper infectedrabbitentity$rabbitjumphelper = (InfectedRabbitEntity.RabbitJumpHelper)this.jumpHelper;
            if (infectedrabbitentity$rabbitjumphelper.getIsNotJumping()) {
                if (this.moveHelper.isUpdating() && this.currentMoveTypeDuration == 0) {
                    Path path = this.navigator.getPath();
                    Vec3d vec3d = new Vec3d(this.moveHelper.getX(), this.moveHelper.getY(), this.moveHelper.getZ());
                    if (path != null && path.getCurrentPathIndex() < path.getCurrentPathLength()) {
                        vec3d = path.getPosition(this);
                    }
                    this.calculateRotationYaw(vec3d.x, vec3d.z);
                    this.startJumping();
                }
            }else if (!infectedrabbitentity$rabbitjumphelper.canJump()) {
                this.enableJumpControl();
            }
        }
        this.wasOnGround = this.onGround;
    }

    @Override
    public void spawnRunningParticles() {
    }

    private void calculateRotationYaw(double x, double z) {
        this.rotationYaw = (float)(MathHelper.atan2(z - this.posZ, x - this.posX) * (180D / Math.PI)) - 90.0F;
    }

    private void enableJumpControl() {
        ((InfectedRabbitEntity.RabbitJumpHelper)this.jumpHelper).setCanJump(true);
    }

    private void disableJumpControl() {
        ((InfectedRabbitEntity.RabbitJumpHelper)this.jumpHelper).setCanJump(false);
    }

    private void updateMoveTypeDuration() {
        if (this.moveHelper.getSpeed() < 2.2D) {
            this.currentMoveTypeDuration = 10;
        }else {
            this.currentMoveTypeDuration = 1;
        }
    }

    private void checkLandingDelay() {
        this.updateMoveTypeDuration();
        this.disableJumpControl();
    }

    @Override
    public void onLivingUpdate() {
        super.onLivingUpdate();

        if (this.jumpTicks != this.jumpDuration) {
            ++this.jumpTicks;
        }else if (this.jumpDuration != 0) {
            this.jumpTicks = 0;
            this.jumpDuration = 0;
            this.setJumping(false);
        }
    }

    @Override
    public void writeEntityToNBT(@NotNull NBTTagCompound compound) {
        super.writeEntityToNBT(compound);
        compound.setInteger("RabbitType", this.getRabbitType());
    }

    @Override
    public void readEntityFromNBT(@NotNull NBTTagCompound compound) {
        super.readEntityFromNBT(compound);
        this.setRabbitType(compound.getInteger("RabbitType"));
    }

    protected SoundEvent getJumpSound() {
        return SoundEvents.ENTITY_RABBIT_JUMP;
    }

    @Override
    public boolean attackEntityAsMob(@NotNull Entity entityIn) {
        return entityIn.attackEntityFrom(DamageSource.causeMobDamage(this), 3.0F);
    }

    public void setRabbitType(int rabbitTypeId) {
        this.dataManager.set(RABBIT_TYPE, rabbitTypeId);
    }

    @Nullable
    public IEntityLivingData onInitialSpawn(@NotNull DifficultyInstance difficulty, @Nullable IEntityLivingData livingdata) {
        livingdata = super.onInitialSpawn(difficulty, livingdata);
        int i = this.getRandomRabbitType();
        if (livingdata instanceof InfectedRabbitEntity.RabbitTypeData) {
            i = ((InfectedRabbitEntity.RabbitTypeData)livingdata).typeData;
        } else {
            livingdata = new EntityRabbit.RabbitTypeData(i);
        }
        this.setRabbitType(i);
        return livingdata;
    }

    private int getRandomRabbitType() {
        Biome biome = this.world.getBiome(new BlockPos(this));
        int i = this.rand.nextInt(100);
        if (biome.isSnowyBiome()) {
            return i < 80 ? 1 : 3;
        }else if (biome instanceof BiomeDesert) {
            return 4;
        }else {
            return i < 50 ? 0 : (i < 90 ? 5 : 2);
        }
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void handleStatusUpdate(byte id) {
        if (id == 1) {
            this.createRunningParticles();
            this.jumpDuration = 10;
            this.jumpTicks = 0;
        }else {
            super.handleStatusUpdate(id);
        }
    }

    public static class RabbitJumpHelper extends EntityJumpHelper {
        private final InfectedRabbitEntity rabbit;
        private boolean canJump;

        public RabbitJumpHelper(InfectedRabbitEntity rabbit) {
            super(rabbit);
            this.rabbit = rabbit;
        }

        public boolean getIsNotJumping() {
            return !this.isJumping;
        }

        public boolean canJump() {
            return this.canJump;
        }

        public void setCanJump(boolean canJumpIn) {
            this.canJump = canJumpIn;
        }

        public void doJump() {
            if (this.isJumping) {
                this.rabbit.startJumping();
                this.isJumping = false;
            }
        }
    }

    static class RabbitMoveHelper extends EntityMoveHelper {
        private final InfectedRabbitEntity rabbit;
        private double nextJumpSpeed;
        public RabbitMoveHelper(InfectedRabbitEntity rabbit) {
            super(rabbit);
            this.rabbit = rabbit;
        }

        @Override
        public void onUpdateMoveHelper() {
            if (this.rabbit.onGround && !this.rabbit.isJumping && ((RabbitJumpHelper) this.rabbit.jumpHelper).getIsNotJumping()) {
                this.rabbit.setMovementSpeed(0.0D);
            }else if (this.isUpdating()) {
                this.rabbit.setMovementSpeed(this.nextJumpSpeed);
            }
            super.onUpdateMoveHelper();
        }

        @Override
        public void setMoveTo(double x, double y, double z, double speedIn) {
            if (this.rabbit.isInWater()) {
                speedIn = 1.5D;
            }
            super.setMoveTo(x, y, z, speedIn);
            if (speedIn > 0.0D) {
                this.nextJumpSpeed = speedIn;
            }
        }
    }

    public static class RabbitTypeData implements IEntityLivingData {
        public int typeData;
        public RabbitTypeData(int type)
        {
            this.typeData = type;
        }
    }
}
