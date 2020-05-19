package com.herobrine.mod.entities;

import com.herobrine.mod.util.loot_tables.LootTableInit;
import com.herobrine.mod.util.savedata.Variables;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.monster.EntityGolem;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityPotion;
import net.minecraft.init.Blocks;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

public class HerobrineMageEntity extends EntityMob{

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
    protected void initEntityAI() {
        super.initEntityAI();
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.targetTasks.addTask(1, new EntityAINearestAttackableTarget<>(this, EntityPlayer.class, true));
        this.targetTasks.addTask(2, new EntityAINearestAttackableTarget<>(this, EntityGolem.class, true));
        this.targetTasks.addTask(3, new EntityAIHurtByTarget(this, false));
        this.tasks.addTask(4, new EntityAIAttackMelee(this, 0.6D, true));
        this.tasks.addTask(5, new EntityAIWanderAvoidWater(this, 0.4D));
        this.tasks.addTask(6, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        this.tasks.addTask(7, new EntityAIWatchClosest(this, EntityGolem.class, 8.0F));
        this.tasks.addTask(8, new EntityAILookIdle(this));
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(100.0D);
        this.getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).setBaseValue(1.0D);
        this.getEntityAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(2.0D);
        this.getEntityAttribute(SharedMonsterAttributes.ARMOR_TOUGHNESS).setBaseValue(2.0D);
        this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(64.0D);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.6D);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(5.0D);
    }

    @Override
    public boolean attackEntityFrom(@NotNull DamageSource source, float amount) {
        if (source.getImmediateSource() instanceof EntityAreaEffectCloud)
            return false;
        if (source.getImmediateSource() instanceof EntityPotion)
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
        if (source == DamageSource.WITHER)
            return false;
        return super.attackEntityFrom(source, amount);
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
        this.clearActivePotions();
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

        if (this.illusionCastingTime <= 0) {
            this.illusionCastingTime = 400;
        }
        if (this.illusionCastingTime > 400) {
            this.illusionCastingTime = 400;
        }
        --this.illusionCastingTime;

        if (this.effectsCastingTime <= 0) {
            this.effectsCastingTime = 250;
        }
        if (this.effectsCastingTime > 250) {
            this.effectsCastingTime = 250;
        }
        --this.effectsCastingTime;

        if (this.teleportCastingTime <= 0) {
            this.teleportCastingTime = 500;
        }
        if (this.teleportCastingTime > 500) {
            this.teleportCastingTime = 500;
        }
        --this.teleportCastingTime;
        super.onUpdate();
    }

    @Override
    public IEntityLivingData onInitialSpawn(@NotNull DifficultyInstance difficulty, @Nullable IEntityLivingData livingdata) {
        if ((!(Variables.WorldVariables.get(world).Spawn))) {
            this.world.removeEntity(this);
        }
        return super.onInitialSpawn(difficulty, livingdata);
    }

    @Nullable
    @Override
    protected ResourceLocation getLootTable() {
        return LootTableInit.HEROBRINE;
    }
}