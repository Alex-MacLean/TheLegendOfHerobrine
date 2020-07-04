package com.herobrine.mod.entities;

import com.herobrine.mod.config.Config;
import com.herobrine.mod.util.loot_tables.LootTableInit;
import com.herobrine.mod.util.savedata.Variables;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.monster.EntityGolem;
import net.minecraft.entity.passive.EntityBat;
import net.minecraft.entity.passive.EntityFlying;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

public class InfectedBatEntity extends AbstractInfectedEntity implements EntityFlying {
    private static final DataParameter<Byte> HANGING = EntityDataManager.createKey(InfectedBatEntity.class, DataSerializers.BYTE);
    private BlockPos spawnPosition;

    public InfectedBatEntity(World worldIn) {
        super(worldIn);
        this.setSize(0.5F, 0.9F);
        this.setIsBatHanging(true);
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
    protected void entityInit() {
        super.entityInit();
        this.dataManager.register(HANGING, (byte) 0);
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

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(6.0D);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(1.0D);
        this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(16.0D);
    }

    public boolean getIsBatHanging() {
        return (this.dataManager.get(HANGING) & 1) != 0;
    }

    public void setIsBatHanging(boolean isHanging) {
        byte b0 = this.dataManager.get(HANGING);

        if (isHanging) {
            this.dataManager.set(HANGING, (byte) (b0 | 1));
        }
        else {
            this.dataManager.set(HANGING, (byte) (b0 & -2));
        }
    }

    @Override
    public void onUpdate() {
        super.onUpdate();
        if (this.getIsBatHanging()) {
            this.motionX = 0.0D;
            this.motionY = 0.0D;
            this.motionZ = 0.0D;
            this.posY = (double) MathHelper.floor(this.posY) + 1.0D - (double)this.height;
        }
        else {
            this.motionY *= 0.6000000238418579D;
        }
    }

    @Override
    protected void updateAITasks() {
        super.updateAITasks();
        BlockPos blockpos = new BlockPos(this);
        BlockPos blockpos1 = blockpos.up();

        if (this.getIsBatHanging()) {
            if (this.world.getBlockState(blockpos1).isNormalCube()) {
                if (this.rand.nextInt(200) == 0) {
                    this.rotationYawHead = (float)this.rand.nextInt(360);
                }
                if (this.world.getNearestPlayerNotCreative(this, 4.0D) != null) {
                    this.setIsBatHanging(false);
                    this.world.playEvent(null, 1025, blockpos, 0);
                }
            }else {
                this.setIsBatHanging(false);
                this.world.playEvent(null, 1025, blockpos, 0);
            }
        }else {
            if (this.spawnPosition != null && (!this.world.isAirBlock(this.spawnPosition) || this.spawnPosition.getY() < 1)) {
                this.spawnPosition = null;
            }
            if (this.spawnPosition == null || this.rand.nextInt(30) == 0 || this.spawnPosition.distanceSq((int)this.posX, (int)this.posY, (int)this.posZ) < 4.0D) {
                this.spawnPosition = new BlockPos((int)this.posX + this.rand.nextInt(7) - this.rand.nextInt(7), (int)this.posY + this.rand.nextInt(6) - 2, (int)this.posZ + this.rand.nextInt(7) - this.rand.nextInt(7));
            }
            double d0 = (double)this.spawnPosition.getX() + 0.5D - this.posX;
            double d1 = (double)this.spawnPosition.getY() + 0.1D - this.posY;
            double d2 = (double)this.spawnPosition.getZ() + 0.5D - this.posZ;
            this.motionX += (Math.signum(d0) * 0.5D - this.motionX) * 0.10000000149011612D;
            this.motionY += (Math.signum(d1) * 0.699999988079071D - this.motionY) * 0.10000000149011612D;
            this.motionZ += (Math.signum(d2) * 0.5D - this.motionZ) * 0.10000000149011612D;
            float f = (float)(MathHelper.atan2(this.motionZ, this.motionX) * (180D / Math.PI)) - 90.0F;
            float f1 = MathHelper.wrapDegrees(f - this.rotationYaw);
            if(this.getAttackTarget() != null) {
                this.moveToEntity(this.getAttackTarget());
            } else {
                this.moveForward = 0.5F;
            }
            this.rotationYaw += f1;
            if (this.rand.nextInt(100) == 0 && this.world.getBlockState(blockpos1).isNormalCube()) {
                this.setIsBatHanging(true);
            }
        }
    }

    public void moveToEntity(@NotNull Entity entity) {
        Vec3d motion = new Vec3d((entity.posX - this.posX) / 10, ((entity.posY + 1) - this.posY) / 10,(entity.posZ - this.posZ) / 10);
        this.motionX = motion.x;
        this.motionY = motion.y;
        this.motionZ = motion.z;
    }

    @Override
    protected boolean canTriggerWalking() {
        return false;
    }

    @Override
    public void fall(float distance, float damageMultiplier) {
    }

    @Override
    protected void updateFallState(double y, boolean onGroundIn, @NotNull IBlockState state, @NotNull BlockPos pos) {
    }

    @Override
    public boolean doesEntityNotTriggerPressurePlate() {
        return true;
    }

    @Override
    public boolean attackEntityFrom(@NotNull DamageSource source, float amount) {
        if (!this.world.isRemote && this.getIsBatHanging()) {
            this.setIsBatHanging(false);
        }
        if (source.getImmediateSource() instanceof HolyWaterEntity) {
            EntityBat batEntity = new EntityBat(this.world);
            batEntity.copyLocationAndAnglesFrom(this);
            batEntity.onInitialSpawn(this.world.getDifficultyForLocation(new BlockPos(batEntity)), null);
            batEntity.setNoAI(this.isAIDisabled());
            if (this.hasCustomName()) {
                batEntity.setCustomNameTag(this.getCustomNameTag());
                batEntity.setAlwaysRenderNameTag(this.getAlwaysRenderNameTag());
            }
            batEntity.enablePersistence();
            this.world.setEntityState(this, (byte)16);
            this.world.spawnEntity(batEntity);
            this.world.removeEntity(this);
        }
        return super.attackEntityFrom(source, amount);
    }

    @Override
    public void readEntityFromNBT(@NotNull NBTTagCompound compound) {
        super.readEntityFromNBT(compound);
        this.dataManager.set(HANGING, compound.getByte("BatFlags"));
    }

    @Override
    public void writeEntityToNBT(@NotNull NBTTagCompound compound) {
        super.writeEntityToNBT(compound);
        compound.setByte("BatFlags", this.dataManager.get(HANGING));
    }

    @Override
    public boolean getCanSpawnHere() {
        BlockPos blockpos = new BlockPos(this.posX, this.getEntityBoundingBox().minY, this.posZ);
        if (blockpos.getY() >= this.world.getSeaLevel() || !Variables.SaveData.get(world).Spawn && !Config.HerobrineAlwaysSpawns) {
            return false;
        } else {
            int i = this.world.getLightFromNeighbors(blockpos);
            int j = 4;
            if (this.rand.nextBoolean()) {
                return false;
            }
            return i <= this.rand.nextInt(j) && super.getCanSpawnHere();
        }
    }

    @Override
    public float getEyeHeight() {
        return this.height / 2.0F;
    }

    @Nullable
    protected ResourceLocation getLootTable() {
        return LootTableInit.INFECTED_BAT;
    }
}
