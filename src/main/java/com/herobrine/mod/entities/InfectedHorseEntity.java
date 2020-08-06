package com.herobrine.mod.entities;

import com.herobrine.mod.config.Config;
import com.herobrine.mod.util.savedata.Variables;
import net.minecraft.block.Block;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.monster.EntityGolem;
import net.minecraft.entity.passive.EntityHorse;
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
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;
import net.minecraft.world.storage.loot.LootTableList;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

public class InfectedHorseEntity extends AbstractInfectedEntity {
    private static final DataParameter<Integer> HORSE_VARIANT = EntityDataManager.createKey(InfectedHorseEntity.class, DataSerializers.VARINT);
    private static final String[] HORSE_TEXTURES = new String[]{"herobrine:textures/entity/infected_horse/horse_white.png", "herobrine:textures/entity/infected_horse/horse_creamy.png", "herobrine:textures/entity/infected_horse/horse_chestnut.png", "herobrine:textures/entity/infected_horse/horse_brown.png", "herobrine:textures/entity/infected_horse/horse_black.png", "herobrine:textures/entity/infected_horse/horse_gray.png", "herobrine:textures/entity/infected_horse/horse_darkbrown.png"};
    private static final String[] HORSE_TEXTURES_ABBR = new String[]{"hwh", "hcr", "hch", "hbr", "hbl", "hgr", "hdb"};
    private static final String[] HORSE_MARKING_TEXTURES = new String[]{null, "textures/entity/horse/horse_markings_white.png", "textures/entity/horse/horse_markings_whitefield.png", "textures/entity/horse/horse_markings_whitedots.png", "textures/entity/horse/horse_markings_blackdots.png"};
    private static final String[] HORSE_MARKING_TEXTURES_ABBR = new String[]{"", "wo_", "wmo", "wdo", "bdo"};
    private String texturePrefix;
    private final String[] horseTexturesArray = new String[3];
    private int openMouthCounter;
    public int tailCounter;
    private float headLean;
    private float prevHeadLean;
    public float rearingAmount;
    private float prevRearingAmount;
    private float mouthOpenness;
    private float prevMouthOpenness;
    private static final DataParameter<Byte> STATUS = EntityDataManager.createKey(InfectedHorseEntity.class, DataSerializers.BYTE);
    public InfectedHorseEntity(World worldIn) {
        super(worldIn);
        this.experienceValue = 3;
        this.setSize(1.3964844F, 1.6F);
    }

    @Override
    protected void entityInit() {
        super.entityInit();
        this.dataManager.register(STATUS, (byte) 0);
        this.dataManager.register(HORSE_VARIANT, 0);
    }

    @Override
    public boolean attackEntityFrom(@NotNull DamageSource source, float amount) {
        if (source.getImmediateSource() instanceof HolyWaterEntity) {
            EntityHorse horseEntity = new EntityHorse(this.world);
            horseEntity.copyLocationAndAnglesFrom(this);
            horseEntity.onInitialSpawn(this.world.getDifficultyForLocation(new BlockPos(horseEntity)), null);
            horseEntity.setNoAI(this.isAIDisabled());
            if (this.hasCustomName()) {
                horseEntity.setCustomNameTag(this.getCustomNameTag());
                horseEntity.setAlwaysRenderNameTag(this.getAlwaysRenderNameTag());
            }
            horseEntity.enablePersistence();
            horseEntity.setGrowingAge(0);
            horseEntity.setHorseVariant(this.getHorseVariant());
            this.world.setEntityState(this, (byte)16);
            this.world.spawnEntity(horseEntity);
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
        this.tasks.addTask(6, new EntityAIWanderAvoidWater(this, 0.7d));
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
        return SoundEvents.ENTITY_HORSE_AMBIENT;
    }

    @Override
    protected SoundEvent getHurtSound(@NotNull DamageSource damageSourceIn) {
        return SoundEvents.ENTITY_HORSE_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_HORSE_DEATH;
    }

    @Override
    protected void playStepSound(@NotNull BlockPos pos, @NotNull Block blockIn) {
        this.playSound(SoundEvents.ENTITY_HORSE_STEP, 0.15F, 1.0F);
    }

    @Override
    public void writeEntityToNBT(@NotNull NBTTagCompound compound) {
        super.writeEntityToNBT(compound);
        compound.setInteger("Variant", this.getHorseVariant());
    }

    @Override
    public void readEntityFromNBT(@NotNull NBTTagCompound compound) {
        super.readEntityFromNBT(compound);
        this.setHorseVariant(compound.getInteger("Variant"));
    }

    public void setHorseVariant(int variant) {
        this.dataManager.set(HORSE_VARIANT, variant);
        this.resetTexturePrefix();
    }

    public int getHorseVariant() {
        return this.dataManager.get(HORSE_VARIANT);
    }

    private void resetTexturePrefix() {
        this.texturePrefix = null;
    }

    @SideOnly(Side.CLIENT)
    private void setHorseTexturePaths()
    {
        int i = this.getHorseVariant();
        int j = (i & 255) % 7;
        int k = ((i & 65280) >> 8) % 5;
        this.horseTexturesArray[0] = HORSE_TEXTURES[j];
        this.horseTexturesArray[1] = HORSE_MARKING_TEXTURES[k];
        this.texturePrefix = "infected_horse/" + HORSE_TEXTURES_ABBR[j] + HORSE_MARKING_TEXTURES_ABBR[k];
    }

    @SideOnly(Side.CLIENT)
    public String getHorseTexture() {
        if (this.texturePrefix == null) {
            this.setHorseTexturePaths();
        }

        return this.texturePrefix;
    }

    @Nullable
    public IEntityLivingData onInitialSpawn(@NotNull DifficultyInstance difficulty, @Nullable IEntityLivingData livingdata) {
        livingdata = super.onInitialSpawn(difficulty, livingdata);
        int i;
        if (livingdata instanceof InfectedHorseEntity.GroupData) {
            i = ((InfectedHorseEntity.GroupData)livingdata).variant;
        } else {
            i = this.rand.nextInt(7);
            livingdata = new InfectedHorseEntity.GroupData(i);
        }
        this.setHorseVariant(i | this.rand.nextInt(5) << 8);
        return livingdata;
    }

    public static class GroupData implements IEntityLivingData
    {
        public int variant;

        public GroupData(int variantIn)
        {
            this.variant = variantIn;
        }
    }

    @SideOnly(Side.CLIENT)
    public String[] getVariantTexturePaths() {
        if (this.texturePrefix == null) {
            this.setHorseTexturePaths();
        }
        return this.horseTexturesArray;
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
        return LootTableList.ENTITIES_HORSE;
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
