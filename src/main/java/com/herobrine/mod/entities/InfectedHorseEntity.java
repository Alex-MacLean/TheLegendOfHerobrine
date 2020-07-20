package com.herobrine.mod.entities;

import com.herobrine.mod.util.entities.EntityRegistry;
import net.minecraft.block.BlockState;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.passive.GolemEntity;
import net.minecraft.entity.passive.horse.HorseEntity;
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
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

public class InfectedHorseEntity extends AbstractInfectedEntity {
    private static final DataParameter<Integer> HORSE_VARIANT = EntityDataManager.createKey(InfectedHorseEntity.class, DataSerializers.VARINT);
    private static final String[] HORSE_TEXTURES = new String[]{"herobrine:textures/entity/infected_horse/horse_white.png", "herobrine:textures/entity/infected_horse/horse_creamy.png", "herobrine:textures/entity/infected_horse/horse_chestnut.png", "herobrine:textures/entity/infected_horse/horse_brown.png", "herobrine:textures/entity/infected_horse/horse_black.png", "herobrine:textures/entity/infected_horse/horse_gray.png", "herobrine:textures/entity/infected_horse/horse_darkbrown.png"};
    private static final String[] HORSE_TEXTURES_ABBR = new String[]{"hwh", "hcr", "hch", "hbr", "hbl", "hgr", "hdb"};
    private static final String[] HORSE_MARKING_TEXTURES = new String[]{null, "textures/entity/horse/horse_markings_white.png", "textures/entity/horse/horse_markings_whitefield.png", "textures/entity/horse/horse_markings_whitedots.png", "textures/entity/horse/horse_markings_blackdots.png"};
    private static final String[] HORSE_MARKING_TEXTURES_ABBR = new String[]{"", "wo_", "wmo", "wdo", "bdo"};
    @Nullable
    private String texturePrefix;
    private final String[] horseTexturesArray = new String[2];
    public int tailCounter;
    private float prevHeadLean;
    private float headLean;
    private float rearingAmount;
    private float prevRearingAmount;
    public float mouthOpenness;
    public float prevMouthOpenness;
    public InfectedHorseEntity(EntityType<? extends InfectedHorseEntity> type, World worldIn) {
        super(type, worldIn);
        experienceValue = 3;
    }

    public InfectedHorseEntity(World worldIn) {
        this(EntityRegistry.INFECTED_HORSE_ENTITY, worldIn);
    }

    @Override
    public boolean attackEntityFrom(@NotNull DamageSource source, float amount) {
        if (source.getImmediateSource() instanceof HolyWaterEntity) {
            HorseEntity horseEntity = EntityType.HORSE.create(this.world);
            assert horseEntity != null;
            horseEntity.setLocationAndAngles(this.getPosX(), this.getPosY(), this.getPosZ(), this.rotationYaw, this.rotationPitch);
            horseEntity.onInitialSpawn(this.world, this.world.getDifficultyForLocation(new BlockPos(horseEntity)), SpawnReason.CONVERSION, null, null);
            horseEntity.setNoAI(this.isAIDisabled());
            if (this.hasCustomName()) {
                horseEntity.setCustomName(this.getCustomName());
                horseEntity.setCustomNameVisible(this.isCustomNameVisible());
            }
            horseEntity.enablePersistence();
            horseEntity.setHorseVariant(this.getHorseVariant());
            horseEntity.setGrowingAge(0);
            this.world.setEntityState(this, (byte)16);
            this.world.addEntity(horseEntity);
            this.remove();
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
        this.getAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(25.0D);
        this.getAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(3.0D);
        this.getAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(16.0D);
        this.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.35D);
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
    protected void playStepSound(@NotNull BlockPos pos, @NotNull BlockState blockIn) {
        this.playSound(SoundEvents.ENTITY_HORSE_STEP, 0.15F, 1.0F);
    }

    @Override
    protected void registerData() {
        super.registerData();
        this.dataManager.register(HORSE_VARIANT, 0);
    }

    @Override
    public void writeAdditional(@NotNull CompoundNBT compound) {
        super.writeAdditional(compound);
        compound.putInt("Variant", this.getHorseVariant());
    }

    @Override
    public void readAdditional(@NotNull CompoundNBT compound) {
        super.readAdditional(compound);
        this.setHorseVariant(compound.getInt("Variant"));
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

    @OnlyIn(Dist.CLIENT)
    private void setHorseTexturePaths() {
        int i = this.getHorseVariant();
        int j = (i & 255) % 7;
        int k = ((i & '\uff00') >> 8) % 5;
        this.horseTexturesArray[0] = HORSE_TEXTURES[j];
        this.horseTexturesArray[1] = HORSE_MARKING_TEXTURES[k];
        this.texturePrefix = "infected_horse/" + HORSE_TEXTURES_ABBR[j] + HORSE_MARKING_TEXTURES_ABBR[k];
    }

    @OnlyIn(Dist.CLIENT)
    public String getHorseTexture() {
        if (this.texturePrefix == null) {
            this.setHorseTexturePaths();
        }

        return this.texturePrefix;
    }

    @OnlyIn(Dist.CLIENT)
    public String[] getVariantTexturePaths() {
        if (this.texturePrefix == null) {
            this.setHorseTexturePaths();
        }

        return this.horseTexturesArray;
    }

    @OnlyIn(Dist.CLIENT)
    public float getRearingAmount(float p_110223_1_) {
        return MathHelper.lerp(p_110223_1_, this.prevRearingAmount, this.rearingAmount);
    }

    @OnlyIn(Dist.CLIENT)
    public float getMouthOpennessAngle(float p_110201_1_) {
        return MathHelper.lerp(p_110201_1_, this.prevMouthOpenness, this.mouthOpenness);
    }

    @Override
    public void tick() {
        super.tick();
        if (this.rand.nextInt(200) == 0) {
            this.moveTail();
        }
        if (this.world.isRemote && this.dataManager.isDirty()) {
            this.dataManager.setClean();
            this.resetTexturePrefix();
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
        if (this.isRearing()) {
            this.headLean = 0.0F;
            this.prevHeadLean = this.headLean;
            this.rearingAmount += (1.0F - this.rearingAmount) * 0.4F + 0.05F;
            if (this.rearingAmount > 1.0F) {
                this.rearingAmount = 1.0F;
            }
        } else {
            this.rearingAmount += (0.8F * this.rearingAmount * this.rearingAmount * this.rearingAmount - this.rearingAmount) * 0.6F - 0.05F;
            if (this.rearingAmount < 0.0F) {
                this.rearingAmount = 0.0F;
            }
        }
    }

    @OnlyIn(Dist.CLIENT)
    public float getGrassEatingAmount(float p_110258_1_) {
        return MathHelper.lerp(p_110258_1_, this.prevHeadLean, this.headLean);
    }

    public boolean isHorseSaddled() {
        return false;
    }

    public boolean isRearing() {
        return false;
    }

    @Nullable
    @Override
    public ILivingEntityData onInitialSpawn(@NotNull IWorld worldIn, @NotNull DifficultyInstance difficultyIn, @NotNull SpawnReason reason, @Nullable ILivingEntityData spawnDataIn, @Nullable CompoundNBT dataTag) {
        int i;
        if (spawnDataIn instanceof InfectedHorseEntity.HorseData) {
            i = ((InfectedHorseEntity.HorseData)spawnDataIn).variant;
        } else {
            i = this.rand.nextInt(7);
            spawnDataIn = new InfectedHorseEntity.HorseData(i);
        }

        this.setHorseVariant(i | this.rand.nextInt(5) << 8);
        return super.onInitialSpawn(worldIn, difficultyIn, reason, spawnDataIn, dataTag);
    }

    public static class HorseData extends AgeableEntity.AgeableData {
        public final int variant;

        public HorseData(int variantIn) {
            this.variant = variantIn;
        }
    }

    private void moveTail() {
        this.tailCounter = 1;
    }

    @Override
    public @NotNull ResourceLocation getLootTable() {
        return EntityType.HORSE.getLootTable();
    }
}