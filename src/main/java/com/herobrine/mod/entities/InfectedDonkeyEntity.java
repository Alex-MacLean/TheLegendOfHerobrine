package com.herobrine.mod.entities;

import com.herobrine.mod.config.Config;
import com.herobrine.mod.util.savedata.Variables;
import net.minecraft.block.Block;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.monster.EntityGolem;
import net.minecraft.entity.passive.EntityDonkey;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.storage.loot.LootTableList;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

public class InfectedDonkeyEntity extends AbstractInfectedEntity {
    private int openMouthCounter;
    public int tailCounter;
    private float headLean;
    private float prevHeadLean;
    public float rearingAmount;
    private float prevRearingAmount;
    private float mouthOpenness;
    private float prevMouthOpenness;
    private static final DataParameter<Byte> STATUS = EntityDataManager.createKey(InfectedDonkeyEntity.class, DataSerializers.BYTE);
    public InfectedDonkeyEntity(World worldIn) {
        super(worldIn);
        this.experienceValue = 3;
        this.setSize(1.3964844F, 1.5F);
    }

    @Override
    protected void entityInit() {
        super.entityInit();
        this.dataManager.register(STATUS, (byte) 0);
    }

    @Override
    public boolean attackEntityFrom(@NotNull DamageSource source, float amount) {
        if (source.getImmediateSource() instanceof HolyWaterEntity) {
            EntityDonkey donkeyEntity = new EntityDonkey(this.world);
            donkeyEntity.copyLocationAndAnglesFrom(this);
            donkeyEntity.onInitialSpawn(this.world.getDifficultyForLocation(new BlockPos(donkeyEntity)), null);
            donkeyEntity.setNoAI(this.isAIDisabled());
            if (this.hasCustomName()) {
                donkeyEntity.setCustomNameTag(this.getCustomNameTag());
                donkeyEntity.setAlwaysRenderNameTag(this.getAlwaysRenderNameTag());
            }
            donkeyEntity.enablePersistence();
            donkeyEntity.setGrowingAge(0);
            this.world.setEntityState(this, (byte)16);
            this.world.spawnEntity(donkeyEntity);
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
        this.tasks.addTask(6, new EntityAIWanderAvoidWater(this, 0.6d));
        this.tasks.addTask(7, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0f));
        this.tasks.addTask(8, new EntityAIWatchClosest(this, AbstractSurvivorEntity.class, 8.0f));
        this.tasks.addTask(9, new EntityAIWatchClosest(this, EntityGolem.class, 8.0f));
        this.tasks.addTask(10, new EntityAILookIdle(this));
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(25.0D);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(3.0D);
        this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(16.0D);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.35D);
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
    protected void playStepSound(@NotNull BlockPos pos, @NotNull Block blockIn) {
        this.playSound(SoundEvents.ENTITY_HORSE_STEP, 0.15F, 1.0F);
    }

    @SideOnly(Side.CLIENT)
    public float getGrassEatingAmount(float p_110258_1_) {
        return this.prevHeadLean + (this.headLean - this.prevHeadLean) * p_110258_1_;
    }

    @SideOnly(Side.CLIENT)
    public float getRearingAmount(float p_110223_1_) {
        return this.prevRearingAmount + (this.rearingAmount - this.prevRearingAmount) * p_110223_1_;
    }

    @SideOnly(Side.CLIENT)
    public float getMouthOpennessAngle(float p_110201_1_) {
        return this.prevMouthOpenness + (this.mouthOpenness - this.prevMouthOpenness) * p_110201_1_;
    }

    @Nullable
    @Override
    protected ResourceLocation getLootTable() {
        return LootTableList.ENTITIES_DONKEY;
    }

    public boolean hasViewOfSky() {
        return world.canSeeSky(new BlockPos(this.posX, this.getEntityBoundingBox().minY, this.posZ));
    }

    @Override
    public boolean getCanSpawnHere() {
        return super.getCanSpawnHere() && hasViewOfSky() && Variables.SaveData.get(world).Spawn || super.getCanSpawnHere() && hasViewOfSky() && Config.HerobrineAlwaysSpawns;
    }

    public boolean isHorseSaddled() {
        return false;
    }

    protected void setHorseWatchableBoolean() {
        byte b0 = this.dataManager.get(STATUS);
        this.dataManager.set(STATUS, (byte) (b0 | 64));
    }

    protected boolean getHorseWatchableBoolean() {
        return (this.dataManager.get(STATUS) & 64) != 0;
    }

    @Override
    public void onUpdate()
    {
        super.onUpdate();

        if (this.openMouthCounter > 0 && ++this.openMouthCounter > 30) {
            this.openMouthCounter = 0;
            this.setHorseWatchableBoolean();
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
        this.prevMouthOpenness = this.mouthOpenness;
        if (this.getHorseWatchableBoolean()) {
            this.mouthOpenness += (1.0F - this.mouthOpenness) * 0.7F + 0.05F;
            if (this.mouthOpenness > 1.0F) {
                this.mouthOpenness = 1.0F;
            }
        } else {
            this.mouthOpenness += (0.0F - this.mouthOpenness) * 0.7F - 0.05F;
            if (this.mouthOpenness < 0.0F) {
                this.mouthOpenness = 0.0F;
            }
        }
    }
}
