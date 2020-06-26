package com.herobrine.mod.entities;

import com.herobrine.mod.config.Config;
import com.herobrine.mod.util.entities.EntityRegistry;
import com.herobrine.mod.util.savedata.Variables;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.horse.LlamaEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.LlamaSpitEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.*;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.Random;

public class InfectedLlamaEntity extends LlamaEntity {
    private static final DataParameter<Integer> DATA_VARIANT_ID = EntityDataManager.createKey(InfectedLlamaEntity.class, DataSerializers.VARINT);
    public InfectedLlamaEntity(EntityType<? extends InfectedLlamaEntity> type, World worldIn) {
        super(type, worldIn);
        experienceValue = 3;
    }

    public InfectedLlamaEntity(World worldIn) {
        this(EntityRegistry.INFECTED_LLAMA_ENTITY, worldIn);
    }

    @Override
    public boolean isDespawnPeaceful() {
        return true;
    }

    @Override
    public @NotNull SoundCategory getSoundCategory() {
        return SoundCategory.HOSTILE;
    }

    @Override
    public boolean attackEntityFrom(@NotNull DamageSource source, float amount) {
        if (source.getImmediateSource() instanceof HolyWaterEntity) {
            LlamaEntity llamaEntity = EntityType.LLAMA.create(this.world);
            assert llamaEntity != null;
            llamaEntity.setLocationAndAngles(this.getPosX(), this.getPosY(), this.getPosZ(), this.rotationYaw, this.rotationPitch);
            llamaEntity.onInitialSpawn(this.world, this.world.getDifficultyForLocation(new BlockPos(llamaEntity)), SpawnReason.CONVERSION, null, null);
            llamaEntity.setNoAI(this.isAIDisabled());
            if (this.hasCustomName()) {
                llamaEntity.setCustomName(this.getCustomName());
                llamaEntity.setCustomNameVisible(this.isCustomNameVisible());
            }
            llamaEntity.enablePersistence();
            llamaEntity.setVariant(this.getVariant());
            llamaEntity.setGrowingAge(0);
            this.world.setEntityState(this, (byte)16);
            this.world.addEntity(llamaEntity);
            this.remove();
        }

        if (source.getImmediateSource() instanceof UnholyWaterEntity)
            return false;
        return super.attackEntityFrom(source, amount);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new SwimGoal(this));
        this.targetSelector.addGoal(1, new InfectedLlamaEntity.HurtByTargetGoal(this));
        this.goalSelector.addGoal(2, new RangedAttackGoal(this, 1.25D, 40, 20.0F));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, PlayerEntity.class, true));
        this.targetSelector.addGoal(4, new NearestAttackableTargetGoal<>(this, AbstractSurvivorEntity.class, true));
        this.goalSelector.addGoal(5, new WaterAvoidingRandomWalkingGoal(this, 0.7D));
        this.goalSelector.addGoal(6, new LookAtGoal(this, PlayerEntity.class, 8.0F));
        this.goalSelector.addGoal(7, new LookAtGoal(this, AbstractSurvivorEntity.class, 8.0F));
        this.goalSelector.addGoal(8, new LookRandomlyGoal(this));
    }

    @Override
    protected void registerAttributes() {
        super.registerAttributes();
        this.getAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(25.0D);
        this.getAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(16.0D);
        this.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.4D);
    }

    @Override
    protected void registerData() {
        super.registerData();
        this.dataManager.register(DATA_VARIANT_ID, 0);
    }

    @OnlyIn(Dist.CLIENT)
    public void handleStatusUpdate(byte id) {
        if (id == 16) {
            if (!this.isSilent()) {
                this.world.playSound(this.getPosX(), this.getPosYEye(), this.getPosZ(), SoundEvents.ENTITY_ZOMBIE_VILLAGER_CONVERTED, SoundCategory.NEUTRAL, 2.0F, (rand.nextFloat() - rand.nextFloat()) * 0.2F + 1.0F, false);
            }
        } else {
            super.handleStatusUpdate(id);
        }
    }

    @Override
    protected int getInventorySize() {
        return 0;
    }

    @Override
    protected boolean handleEating(@NotNull PlayerEntity player, @NotNull ItemStack stack) {
        return false;
    }

    public boolean wearsArmor() {
        return false;
    }

    @Override
    public boolean isArmor(@NotNull ItemStack stack) {
        return false;
    }

    @Override
    protected boolean isMovementBlocked() {
        return this.getHealth() <= 0.0F;
    }

    @Override
    public boolean canMateWith(@NotNull AnimalEntity otherAnimal) {
        return false;
    }

    @Override
    @SuppressWarnings("ConstantConditions")
    protected @NotNull LlamaEntity createChild() {
        return null;
    }

    @Override
    public LlamaEntity createChild(@NotNull AgeableEntity ageable) {
        return null;
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.ENTITY_LLAMA_AMBIENT;
    }

    @Override
    protected SoundEvent getHurtSound(@NotNull DamageSource damageSourceIn) {
        return SoundEvents.ENTITY_LLAMA_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_LLAMA_DEATH;
    }

    @Override
    protected void playStepSound(@NotNull BlockPos pos, @NotNull BlockState blockIn) {
        this.playSound(SoundEvents.ENTITY_LLAMA_STEP, 0.15F, 1.0F);
    }

    @Override
    public void writeAdditional(@NotNull CompoundNBT compound) {
        super.writeAdditional(compound);
        compound.putInt("Variant", this.getVariant());

    }

    @Override
    public void readAdditional(@NotNull CompoundNBT compound) {
        super.readAdditional(compound);
        this.setVariant(compound.getInt("Variant"));
    }

    @Override
    public boolean isChild() {
        return false;
    }

    public int getVariant() {
        return MathHelper.clamp(this.dataManager.get(DATA_VARIANT_ID), 0, 3);
    }

    public void setVariant(int variantIn) {
        this.dataManager.set(DATA_VARIANT_ID, variantIn);
    }

    @Override
    public void attackEntityWithRangedAttack(@NotNull LivingEntity target, float distanceFactor) {
        this.spit(target);
    }

    private void spit(@NotNull LivingEntity target) {
        LlamaSpitEntity llamaSpitEntity = new LlamaSpitEntity(this.world, this);
        double d0 = target.getPosX() - this.getPosX();
        double d1 = target.getPosYHeight(0.3333333333333333D) - llamaSpitEntity.getPosY();
        double d2 = target.getPosZ() - this.getPosZ();
        float f = MathHelper.sqrt(d0 * d0 + d2 * d2) * 0.2F;
        llamaSpitEntity.shoot(d0, d1 + (double)f, d2, 1.5F, 10.0F);
        this.world.playSound(null, this.getPosX(), this.getPosY(), this.getPosZ(), SoundEvents.ENTITY_LLAMA_SPIT, this.getSoundCategory(), 1.0F, 1.0F + (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F);
        this.world.addEntity(llamaSpitEntity);
    }

    @Override
    public boolean onLivingFall(float distance, float damageMultiplier) {
        int i = this.calculateFallDamage(distance, damageMultiplier);
        if (i <= 0) {
            return false;
        } else {
            if (distance >= 6.0F) {
                this.attackEntityFrom(DamageSource.FALL, (float)i);
                if (this.isBeingRidden()) {
                    for(Entity entity : this.getRecursivePassengers()) {
                        entity.attackEntityFrom(DamageSource.FALL, (float)i);
                    }
                }
            }

            this.playFallSound();
            return true;
        }
    }

    @Override
    public void baseTick() {
        Variables.SaveData.get(world).syncData(world);
        if (!Variables.SaveData.get(world).Spawn && !Config.COMMON.HerobrineAlwaysSpawns.get()) {
            this.remove();
        }
        super.baseTick();
    }

    @Nullable
    @Override
    public ILivingEntityData onInitialSpawn(@NotNull IWorld worldIn, @NotNull DifficultyInstance difficultyIn, @NotNull SpawnReason reason, @Nullable ILivingEntityData spawnDataIn, @Nullable CompoundNBT dataTag) {
        int i;
        if (spawnDataIn instanceof InfectedLlamaEntity.LlamaData) {
            i = ((InfectedLlamaEntity.LlamaData)spawnDataIn).variant;
        } else {
            i = this.rand.nextInt(4);
            spawnDataIn = new InfectedLlamaEntity.LlamaData(i);
        }
        this.setVariant(i);

        Variables.SaveData.get(world).syncData(world);
        if (!Variables.SaveData.get(world).Spawn && !Config.COMMON.HerobrineAlwaysSpawns.get()) {
            this.remove();
        }
        return super.onInitialSpawn(worldIn, difficultyIn, reason, spawnDataIn, dataTag);
    }

    static class LlamaData extends AgeableEntity.AgeableData {
        public final int variant;

        private LlamaData(int variantIn) {
            this.variant = variantIn;
        }
    }

    static class HurtByTargetGoal extends net.minecraft.entity.ai.goal.HurtByTargetGoal {
        public HurtByTargetGoal(InfectedLlamaEntity llama) {
            super(llama);
        }
        @Override
        public boolean shouldContinueExecuting() {
            if (this.goalOwner instanceof InfectedLlamaEntity) {
                return true;
            }
            return super.shouldContinueExecuting();
        }
    }

    @Override
    public void tick() {
        super.tick();
        this.setChested(false);
    }

    @Override
    public boolean processInteract(@NotNull PlayerEntity player, @NotNull Hand hand) {
        return false;
    }

    @Override
    public int getInventoryColumns() {
        return 0;
    }

    @Override
    public boolean isTame() {
        return false;
    }

    public static boolean isValidLightLevel(@NotNull IWorld worldIn, @NotNull BlockPos pos, @NotNull Random randomIn) {
        if (worldIn.getLightFor(LightType.SKY, pos) > randomIn.nextInt(32)) {
            return false;
        } else {
            int i = worldIn.getWorld().isThundering() ? worldIn.getNeighborAwareLightSubtracted(pos, 10) : worldIn.getLight(pos);
            return i <= randomIn.nextInt(8);
        }
    }

    public static boolean isValidBlock(@NotNull IWorld worldIn, @NotNull BlockPos pos) {
        return worldIn.getBlockState(pos.down()).getBlock() == Blocks.GRASS_BLOCK || worldIn.getBlockState(pos.down()).getBlock() == Blocks.GRAVEL || worldIn.getBlockState(pos.down()).getBlock() == Blocks.STONE;
    }

    public static boolean hasViewOfSky(@NotNull IWorld worldIn, @NotNull BlockPos pos) {
        return worldIn.canSeeSky(pos);
    }

    public static boolean canSpawn(EntityType<? extends InfectedLlamaEntity> type, @NotNull IWorld worldIn, SpawnReason reason, BlockPos pos, Random randomIn) {
        return worldIn.getDifficulty() != Difficulty.PEACEFUL && isValidBlock(worldIn, pos) && hasViewOfSky(worldIn, pos) && isValidLightLevel(worldIn, pos, randomIn) && canSpawnOn(type, worldIn, reason, pos, randomIn);
    }
}