package com.herobrine.mod.entities;

import com.herobrine.mod.config.Config;
import com.herobrine.mod.util.entities.EntityRegistry;
import com.herobrine.mod.util.savedata.Variables;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.merchant.villager.VillagerEntity;
import net.minecraft.entity.passive.GolemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Difficulty;
import net.minecraft.world.IWorld;
import net.minecraft.world.LightType;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;

import java.util.Random;

public class InfectedVillagerEntity extends AbstractInfectedEntity {

    public InfectedVillagerEntity(EntityType<InfectedVillagerEntity> type, World worldIn) {
        super(type, worldIn);
        experienceValue = 3;
    }

    public InfectedVillagerEntity(World world) {
        this(EntityRegistry.INFECTED_VILLAGER_ENTITY, world);
    }


    @Override
    public boolean attackEntityFrom(@NotNull DamageSource source, float amount) {
        if (source.getImmediateSource() instanceof HolyWaterEntity) {
            VillagerEntity villagerEntity = EntityType.VILLAGER.create(this.world);
            assert villagerEntity != null;
            villagerEntity.setLocationAndAngles(this.getPosX(), this.getPosY(), this.getPosZ(), this.rotationYaw, this.rotationPitch);
            villagerEntity.onInitialSpawn(this.world, this.world.getDifficultyForLocation(new BlockPos(villagerEntity)), SpawnReason.CONVERSION, null, null);
            villagerEntity.setNoAI(this.isAIDisabled());
            if (this.hasCustomName()) {
                villagerEntity.setCustomName(this.getCustomName());
                villagerEntity.setCustomNameVisible(this.isCustomNameVisible());
            }
            villagerEntity.enablePersistence();
            villagerEntity.setGrowingAge(0);
            this.world.setEntityState(this, (byte)16);
            this.world.addEntity(villagerEntity);
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
        this.goalSelector.addGoal(6, new WaterAvoidingRandomWalkingGoal(this, 0.5D));
        this.goalSelector.addGoal(7, new LookAtGoal(this, PlayerEntity.class, 8.0F));
        this.goalSelector.addGoal(8, new LookAtGoal(this, AbstractSurvivorEntity.class, 8.0F));
        this.goalSelector.addGoal(9, new LookAtGoal(this, GolemEntity.class, 8.0F));
        this.goalSelector.addGoal(10, new LookRandomlyGoal(this));
    }

    @Override
    protected void registerAttributes() {
        super.registerAttributes();
        this.getAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(20.0D);
        this.getAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(2.0D);
        this.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.5D);
        this.getAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(48.0D);
    }

    protected SoundEvent getHurtSound(@NotNull DamageSource damageSourceIn) {
        return SoundEvents.ENTITY_VILLAGER_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_VILLAGER_DEATH;
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.ENTITY_VILLAGER_AMBIENT;
    }

    public static boolean isValidLightLevel(@NotNull IWorld worldIn, @NotNull BlockPos pos, @NotNull Random randomIn) {
        if (worldIn.getLightFor(LightType.SKY, pos) > randomIn.nextInt(32)) {
            return false;
        } else {
            int i = worldIn.getWorld().isThundering() ? worldIn.getNeighborAwareLightSubtracted(pos, 10) : worldIn.getLight(pos);
            return i <= randomIn.nextInt(8);
        }
    }

    public static boolean hasViewOfSky(@NotNull IWorld worldIn, @NotNull BlockPos pos) {
        return worldIn.canSeeSky(pos);
    }

    public static boolean canSpawn(EntityType<? extends AbstractInfectedEntity> type, @NotNull IWorld worldIn, SpawnReason reason, BlockPos pos, Random randomIn) {
        return worldIn.getDifficulty() != Difficulty.PEACEFUL && hasViewOfSky(worldIn, pos) && isValidLightLevel(worldIn, pos, randomIn) && canSpawnOn(type, worldIn, reason, pos, randomIn) && Variables.SaveData.get(worldIn.getWorld()).Spawn || worldIn.getDifficulty() != Difficulty.PEACEFUL && hasViewOfSky(worldIn, pos) && isValidLightLevel(worldIn, pos, randomIn) && canSpawnOn(type, worldIn, reason, pos, randomIn) && Config.COMMON.HerobrineAlwaysSpawns.get();
    }

    @Override
    public @NotNull ResourceLocation getLootTable() {
        return EntityType.VILLAGER.getLootTable();
    }
}