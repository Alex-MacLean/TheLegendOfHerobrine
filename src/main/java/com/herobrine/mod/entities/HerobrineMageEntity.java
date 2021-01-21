package com.herobrine.mod.entities;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.monster.AbstractIllager;
import net.minecraft.entity.monster.EntityGolem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;

public class HerobrineMageEntity extends AbstractHerobrineEntity {

    private static final DataParameter<Byte> AI_FLAGS = EntityDataManager.createKey(HerobrineMageEntity.class, DataSerializers.BYTE);

    public HerobrineMageEntity(World worldIn) {
        super(worldIn);
        this.setSize(0.6F, 1.95F);
        this.experienceValue = 5;
    }

    private int illusionCastingTime = 400;
    private int effectsCastingTime = 250;
    private int teleportCastingTime = 500;

    @Override
    public void entityInit() {
        super.entityInit();
        this.dataManager.register(AI_FLAGS, (byte)0);
    }

    @Override
    public void initEntityAI() {
        super.initEntityAI();
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.targetTasks.addTask(1, new EntityAINearestAttackableTarget<>(this, AbstractIllager.class, true));
        this.targetTasks.addTask(2, new EntityAINearestAttackableTarget<>(this, EntityPlayer.class, true));
        this.targetTasks.addTask(3, new EntityAINearestAttackableTarget<>(this, AbstractSurvivorEntity.class, true));
        this.targetTasks.addTask(4, new EntityAINearestAttackableTarget<>(this, EntityGolem.class, true));
        this.targetTasks.addTask(5, new EntityAIHurtByTarget(this, false));
        this.tasks.addTask(6, new EntityAIAttackMelee(this, 1.0d, true));
        this.tasks.addTask(7, new EntityAIWanderAvoidWater(this, 0.4d));
        this.tasks.addTask(8, new EntityAIWatchClosest(this, AbstractIllager.class, 8.0f));
        this.tasks.addTask(9, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0f));
        this.tasks.addTask(10, new EntityAIWatchClosest(this, AbstractSurvivorEntity.class, 8.0f));
        this.tasks.addTask(11, new EntityAIWatchClosest(this, EntityGolem.class, 8.0f));
        this.tasks.addTask(12, new EntityAILookIdle(this));
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(35.0D);
        this.getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).setBaseValue(1.0D);
        this.getEntityAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(2.0D);
        this.getEntityAttribute(SharedMonsterAttributes.ARMOR_TOUGHNESS).setBaseValue(2.0D);
        this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(64.0D);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.6D);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(5.0D);
    }

    @Override
    public void writeEntityToNBT(@NotNull NBTTagCompound compound) {
        super.writeEntityToNBT(compound);
        compound.setInteger("IllusionCastingInterval", this.illusionCastingTime);
        compound.setInteger("WeakenCastingInterval", this.effectsCastingTime);
        compound.setInteger("WarpCastingInterval", this.teleportCastingTime);
    }

    @Override
    public void readEntityFromNBT(@NotNull NBTTagCompound compound) {
        super.readEntityFromNBT(compound);
        this.illusionCastingTime = compound.getInteger("IllusionCastingInterval");
        this.effectsCastingTime = compound.getInteger("WeakenCastingInterval");
        this.teleportCastingTime = compound.getInteger("WarpCastingInterval");
    }

    @Override
    public void onUpdate() {
        if(this.dataManager.get(AI_FLAGS) == (byte) 1284594) {
            if(this.world.isRemote) {
                if (!this.isSilent()) {
                    this.world.playSound(this.posX + 0.5D, this.posY + 0.5D, this.posZ + 0.5D, SoundEvents.ENTITY_ILLAGER_CAST_SPELL, this.getSoundCategory(), 1.0F + this.rand.nextFloat(), this.rand.nextFloat() * 0.7F + 0.3F, false);
                }

                for (int i = 0; i < 20; ++i) {
                    double d0 = this.rand.nextGaussian() * 0.02D;
                    double d1 = this.rand.nextGaussian() * 0.02D;
                    double d2 = this.rand.nextGaussian() * 0.02D;
                    this.world.spawnParticle(EnumParticleTypes.SPELL, this.posX + (double) (this.rand.nextFloat() * this.width * 2.0F) - (double) this.width, this.posY + (double) (this.rand.nextFloat() * this.height), this.posZ + (double) (this.rand.nextFloat() * this.width * 2.0F) - (double) this.width, d2, d0, d1);
                }
            }
            this.dataManager.set(AI_FLAGS, (byte) 0);
        }

        if (this.illusionCastingTime <= 0 && this.getAttackTarget() != null) {
            int x = (int) this.posX;
            int y = (int) this.posY;
            int z = (int) this.posZ;
            if (!world.isRemote) {
                Entity entity1 = new FakeHerobrineMageEntity(world);
                Entity entity2 = new FakeHerobrineMageEntity(world);
                Entity entity3 = new FakeHerobrineMageEntity(world);
                Entity entity4 = new FakeHerobrineMageEntity(world);
                entity1.setLocationAndAngles(x, y, z, world.rand.nextFloat() * 360F, 0);
                entity2.setLocationAndAngles(x, y, z, world.rand.nextFloat() * 360F, 0);
                entity3.setLocationAndAngles(x, y, z, world.rand.nextFloat() * 360F, 0);
                entity4.setLocationAndAngles(x, y, z, world.rand.nextFloat() * 360F, 0);
                world.spawnEntity(entity1);
                world.spawnEntity(entity2);
                world.spawnEntity(entity3);
                world.spawnEntity(entity4);
            }
            this.dataManager.set(AI_FLAGS, (byte) 1284594);
        }

        if (this.effectsCastingTime <= 0 && this.getAttackTarget() != null) {
            EntityLivingBase entity = HerobrineMageEntity.this.getAttackTarget();
            if (entity != null) {
                entity.addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 400, 1));
                entity.addPotionEffect(new PotionEffect(MobEffects.WEAKNESS, 400));
                this.dataManager.set(AI_FLAGS, (byte) 1284594);
            }
        }

        if (this.teleportCastingTime <= 0 && this.getAttackTarget() != null) {
            EntityLivingBase entity = HerobrineMageEntity.this.getAttackTarget();
            if (entity != null) {
                int x = (int) entity.posX;
                int y = (int) entity.posY;
                int z = (int) entity.posZ;
                IBlockState block = world.getBlockState(new BlockPos(x, y + 3, z));
                IBlockState blockAt = world.getBlockState(new BlockPos(x, y + 4, z));
                if (blockAt.getBlock() == Blocks.AIR.getDefaultState().getBlock() && block.getBlock() == Blocks.AIR.getDefaultState().getBlock()) {
                    entity.setPositionAndUpdate(x, y + 4, z);
                    this.dataManager.set(AI_FLAGS, (byte) 1284594);
                }
            }
        }

        if (this.illusionCastingTime < 1 || this.illusionCastingTime > 400) {
            this.illusionCastingTime = 400;
        }
        --this.illusionCastingTime;

        if (this.effectsCastingTime < 1 || this.effectsCastingTime > 250) {
            this.effectsCastingTime = 250;
        }
        --this.effectsCastingTime;

        if (this.teleportCastingTime < 1 || this.teleportCastingTime > 500) {
            this.teleportCastingTime = 500;
        }
        --this.teleportCastingTime;
        super.onUpdate();
    }
}