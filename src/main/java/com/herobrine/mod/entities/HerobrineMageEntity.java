package com.herobrine.mod.entities;

import com.herobrine.mod.util.entities.EntityRegistry;
import com.herobrine.mod.util.savedata.Variables;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.passive.GolemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.PotionEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

public class HerobrineMageEntity extends MonsterEntity {
    protected HerobrineMageEntity(EntityType<? extends HerobrineMageEntity> type, World worldIn) {
        super(type, worldIn);
        experienceValue = 5;
    }

    public HerobrineMageEntity(World worldIn) {
        this((EntityType<? extends HerobrineMageEntity>) EntityRegistry.HEROBRINE_MAGE_ENTITY, worldIn);
    }

    private int illusionCastingTime = 400;
    private int effectsCastingTime = 250;
    private int teleportCastingTime = 500;

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new SwimGoal(this));
        this.goalSelector.addGoal(1, new MeleeAttackGoal(this, 0.6D, true));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, PlayerEntity.class, false));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, GolemEntity.class, true));
        this.targetSelector.addGoal(4, new HurtByTargetGoal(this));
        this.goalSelector.addGoal(5, new WaterAvoidingRandomWalkingGoal(this, 0.4D));
        this.goalSelector.addGoal(6, new LookAtGoal(this, PlayerEntity.class, 8.0F));
        this.goalSelector.addGoal(7, new LookAtGoal(this, GolemEntity.class, 8.0F));
        this.goalSelector.addGoal(8, new LookRandomlyGoal(this));
    }

    @Override
    protected void registerAttributes() {
        super.registerAttributes();
        this.getAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(100.0D);
        this.getAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).setBaseValue(1.0D);
        this.getAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(2.0D);
        this.getAttribute(SharedMonsterAttributes.ARMOR_TOUGHNESS).setBaseValue(2.0D);
        this.getAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(64.0D);
        this.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.6D);
        this.getAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(5.0D);
    }

    @Override
    public boolean attackEntityFrom(@NotNull DamageSource source, float amount) {
        if (source.getImmediateSource() instanceof AreaEffectCloudEntity)
            return false;
        if (source.getImmediateSource() instanceof PotionEntity)
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
        if (source == DamageSource.DRYOUT)
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
        if (source == DamageSource.SWEET_BERRY_BUSH)
            return false;
        if (source == DamageSource.WITHER)
            return false;
        return super.attackEntityFrom(source, amount);
    }

    @Override
    public void writeAdditional(@NotNull CompoundNBT compound) {
        super.writeAdditional(compound);
        compound.putInt("IllusionCastingInterval", this.illusionCastingTime);
        compound.putInt("WeakenCastingInterval", this.effectsCastingTime);
        compound.putInt("WarpCastingInterval", this.teleportCastingTime);
    }

    @Override
    public void readAdditional(@NotNull CompoundNBT compound) {
        super.readAdditional(compound);
        this.illusionCastingTime = compound.getInt("IllusionCastingInterval");
        this.effectsCastingTime = compound.getInt("WeakenCastingInterval");
        this.teleportCastingTime = compound.getInt("WarpCastingInterval");
    }

    @Override
    public void baseTick() {
        super.baseTick();
        this.clearActivePotions();
    }

    @Override
    public ILivingEntityData onInitialSpawn(@NotNull IWorld worldIn, @NotNull DifficultyInstance difficultyIn, @NotNull SpawnReason reason, @Nullable ILivingEntityData spawnDataIn, @Nullable CompoundNBT dataTag) {
        Variables.WorldVariables.get(world).syncData(world);
        if ((!(Variables.WorldVariables.get(world).Spawn))) {
            this.remove();
        }
        return super.onInitialSpawn(worldIn, difficultyIn, reason, spawnDataIn, dataTag);
    }

    @Override
    public boolean attackEntityAsMob(@NotNull Entity entityIn) {
        boolean flag = super.attackEntityAsMob(entityIn);
        if (flag) {
            float f = this.world.getDifficultyForLocation(new BlockPos(this)).getAdditionalDifficulty();
            if (this.isBurning() && this.rand.nextFloat() < f * 0.3F) {
                entityIn.setFire(2 * (int)f);
            }
        }
        return flag;
    }

    @Override
    public void livingTick() {
        if(this.isAlive()) {
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

            if (this.illusionCastingTime == 0 && this.isAggressive()) {
                int x = (int) this.posX;
                int y = (int) this.posY;
                int z = (int) this.posZ;
                if (!world.isRemote) {
                    Entity entity1 = new FakeHerobrineMageEntity((EntityType<? extends FakeHerobrineMageEntity>) EntityRegistry.FAKE_HEROBRINE_MAGE_ENTITY, world);
                    Entity entity2 = new FakeHerobrineMageEntity((EntityType<? extends FakeHerobrineMageEntity>) EntityRegistry.FAKE_HEROBRINE_MAGE_ENTITY, world);
                    Entity entity3 = new FakeHerobrineMageEntity((EntityType<? extends FakeHerobrineMageEntity>) EntityRegistry.FAKE_HEROBRINE_MAGE_ENTITY, world);
                    Entity entity4 = new FakeHerobrineMageEntity((EntityType<? extends FakeHerobrineMageEntity>) EntityRegistry.FAKE_HEROBRINE_MAGE_ENTITY, world);
                    entity1.setLocationAndAngles(x, y, z, world.rand.nextFloat() * 360F, 0);
                    entity2.setLocationAndAngles(x, y, z, world.rand.nextFloat() * 360F, 0);
                    entity3.setLocationAndAngles(x, y, z, world.rand.nextFloat() * 360F, 0);
                    entity4.setLocationAndAngles(x, y, z, world.rand.nextFloat() * 360F, 0);
                    world.addEntity(entity1);
                    world.addEntity(entity2);
                    world.addEntity(entity3);
                    world.addEntity(entity4);
                }

                if (this.world.isRemote) {
                    if (!this.isSilent()) {
                        this.world.playSound(this.posX + 0.5D, this.posY + 0.5D, this.posZ + 0.5D, SoundEvents.ENTITY_ILLUSIONER_CAST_SPELL, this.getSoundCategory(), 1.0F + this.rand.nextFloat(), this.rand.nextFloat() * 0.7F + 0.3F, false);
                    }

                    for (int i = 0; i < 20; ++i) {
                        double d0 = this.rand.nextGaussian() * 0.02D;
                        double d1 = this.rand.nextGaussian() * 0.02D;
                        double d2 = this.rand.nextGaussian() * 0.02D;
                        this.world.addParticle(ParticleTypes.EFFECT, this.posX + (double)(this.rand.nextFloat() * this.getWidth() * 2.0F) - (double)this.getWidth(), this.posY + (double)(this.rand.nextFloat() * this.getHeight()), this.posZ + (double)(this.rand.nextFloat() * this.getWidth() * 2.0F) - (double)this.getWidth(), d2, d0, d1);
                    }
                }
            }

            if (this.effectsCastingTime == 0 && this.isAggressive()) {
                LivingEntity entity = HerobrineMageEntity.this.getAttackTarget();
                if (entity != null) {
                    entity.addPotionEffect(new EffectInstance(Effects.SLOWNESS, 400, 1));
                    entity.addPotionEffect(new EffectInstance(Effects.WEAKNESS, 400));
                }

                if (this.world.isRemote) {
                    if (!this.isSilent()) {
                        this.world.playSound(this.posX + 0.5D, this.posY + 0.5D, this.posZ + 0.5D, SoundEvents.ENTITY_ILLUSIONER_CAST_SPELL, this.getSoundCategory(), 1.0F + this.rand.nextFloat(), this.rand.nextFloat() * 0.7F + 0.3F, false);
                    }

                    for (int i = 0; i < 20; ++i) {
                        double d0 = this.rand.nextGaussian() * 0.02D;
                        double d1 = this.rand.nextGaussian() * 0.02D;
                        double d2 = this.rand.nextGaussian() * 0.02D;
                        this.world.addParticle(ParticleTypes.EFFECT, this.posX + (double)(this.rand.nextFloat() * this.getWidth() * 2.0F) - (double)this.getWidth(), this.posY + (double)(this.rand.nextFloat() * this.getHeight()), this.posZ + (double)(this.rand.nextFloat() * this.getWidth() * 2.0F) - (double)this.getWidth(), d2, d0, d1);
                    }
                }
            }

            if (this.teleportCastingTime == 0 && this.isAggressive()) {
                LivingEntity entity = HerobrineMageEntity.this.getAttackTarget();
                if (entity != null) {
                    int x = (int) entity.posX;
                    int y = (int) entity.posY;
                    int z = (int) entity.posZ;
                    BlockState block = world.getBlockState(new BlockPos(x, y + 3, z));
                    BlockState blockAt = world.getBlockState(new BlockPos(x, y + 4, z));
                    if (blockAt.getBlock() == Blocks.AIR.getDefaultState().getBlock() && block.getBlock() == Blocks.AIR.getDefaultState().getBlock() || blockAt.getBlock() == Blocks.CAVE_AIR.getDefaultState().getBlock() && block.getBlock() == Blocks.CAVE_AIR.getDefaultState().getBlock() || blockAt.getBlock() == Blocks.CAVE_AIR.getDefaultState().getBlock() && block.getBlock() == Blocks.AIR.getDefaultState().getBlock() || blockAt.getBlock() == Blocks.AIR.getDefaultState().getBlock() && block.getBlock() == Blocks.CAVE_AIR.getDefaultState().getBlock()) {
                        entity.setPositionAndUpdate(x, y + 4, z);
                        if (this.world.isRemote) {
                            if (!this.isSilent()) {
                                this.world.playSound(this.posX + 0.5D, this.posY + 0.5D, this.posZ + 0.5D, SoundEvents.ENTITY_ILLUSIONER_CAST_SPELL, this.getSoundCategory(), 1.0F + this.rand.nextFloat(), this.rand.nextFloat() * 0.7F + 0.3F, false);
                            }

                            for (int i = 0; i < 20; ++i) {
                                double d0 = this.rand.nextGaussian() * 0.02D;
                                double d1 = this.rand.nextGaussian() * 0.02D;
                                double d2 = this.rand.nextGaussian() * 0.02D;
                                this.world.addParticle(ParticleTypes.EFFECT, this.posX + (double)(this.rand.nextFloat() * this.getWidth() * 2.0F) - (double)this.getWidth(), this.posY + (double)(this.rand.nextFloat() * this.getHeight()), this.posZ + (double)(this.rand.nextFloat() * this.getWidth() * 2.0F) - (double)this.getWidth(), d2, d0, d1);
                            }
                        }
                    }
                }
            }
        }
        super.livingTick();
    }
}