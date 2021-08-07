package com.herobrine.mod.entities;

import com.herobrine.mod.util.entities.EntityRegistry;
import com.herobrine.mod.util.savedata.SaveDataUtil;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.IShearable;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.effect.LightningBoltEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.passive.GolemEntity;
import net.minecraft.entity.passive.MooshroomEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.potion.Effect;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.*;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.IForgeShearable;
import org.jetbrains.annotations.NotNull;

import java.util.Random;
import java.util.UUID;

public class InfectedMooshroomEntity extends AbstractInfectedEntity implements IShearable, IForgeShearable {
    private static final DataParameter<String> MOOSHROOM_TYPE = EntityDataManager.defineId(InfectedMooshroomEntity.class, DataSerializers.STRING);
    private Effect hasStewEffect;
    private int effectDuration;
    private UUID lightningUUID;

    public InfectedMooshroomEntity(EntityType<? extends InfectedMooshroomEntity> type, World worldIn) {
        super(type, worldIn);
        xpReward = 3;
    }

    public static AttributeModifierMap.MutableAttribute registerAttributes() {
        return MonsterEntity.createMonsterAttributes()
                .add(Attributes.MAX_HEALTH, 10.0D)
                .add(Attributes.ATTACK_DAMAGE, 3.0D)
                .add(Attributes.FOLLOW_RANGE, 16.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.2D);
    }


    public InfectedMooshroomEntity(World worldIn) {
        this(EntityRegistry.INFECTED_MOOSHROOM_ENTITY, worldIn);
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

    public static boolean isValidLightLevel(@NotNull IServerWorld worldIn, @NotNull BlockPos pos, @NotNull Random randomIn) {
        if (worldIn.getBrightness(LightType.SKY, pos) > randomIn.nextInt(32)) {
            return false;
        } else {
            int i = worldIn.getLevel().isThundering() ? worldIn.getMaxLocalRawBrightness(pos, 10) : worldIn.getLightEmission(pos);
            return i <= randomIn.nextInt(8);
        }
    }

    public static boolean canSpawn(EntityType<? extends AbstractInfectedEntity> type, @NotNull IServerWorld worldIn, SpawnReason reason, BlockPos pos, Random randomIn) {
        return worldIn.getDifficulty() != Difficulty.PEACEFUL && hasViewOfSky(worldIn, pos) && isValidLightLevel(worldIn, pos, randomIn) && checkMobSpawnRules(type, worldIn, reason, pos, randomIn) && SaveDataUtil.canHerobrineSpawn(worldIn);
    }

    @Override
    public boolean hurt(@NotNull DamageSource source, float amount) {
        if (source.getDirectEntity() instanceof HolyWaterEntity) {
            MooshroomEntity entity = this.convertTo(EntityType.MOOSHROOM, false);
            assert entity != null;
            entity.finalizeSpawn((IServerWorld) this.level, this.level.getCurrentDifficultyAt(entity.blockPosition()), SpawnReason.CONVERSION, null, null);
            entity.setMushroomType(MooshroomEntity.Type.valueOf(this.getMooshroomType().toString()));
            this.level.broadcastEntityEvent(this, (byte) 16);
        }
        return super.hurt(source, amount);
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.COW_AMBIENT;
    }

    @Override
    protected SoundEvent getHurtSound(@NotNull DamageSource damageSourceIn) {
        return SoundEvents.COW_HURT;
    }

    @Override
    protected float getSoundVolume() {
        return 0.4F;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.COW_DEATH;
    }

    @Override
    protected void playStepSound(@NotNull BlockPos pos, @NotNull BlockState blockIn) {
        this.playSound(SoundEvents.COW_STEP, 0.15F, 1.0F);
    }

    @Override
    public void thunderHit(@NotNull ServerWorld world, @NotNull LightningBoltEntity lightningBolt) {
        UUID uuid = lightningBolt.getUUID();
        if (!uuid.equals(this.lightningUUID)) {
            this.setMooshroomType(this.getMooshroomType() == InfectedMooshroomEntity.Type.RED ? InfectedMooshroomEntity.Type.BROWN : InfectedMooshroomEntity.Type.RED);
            this.lightningUUID = uuid;
            this.playSound(SoundEvents.MOOSHROOM_CONVERT, 2.0F, 1.0F);
        }

    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(MOOSHROOM_TYPE, InfectedMooshroomEntity.Type.RED.name);
    }

    @Override
    public @NotNull ActionResultType mobInteract(@NotNull PlayerEntity player, @NotNull Hand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        if (itemstack.getItem() == Items.SHEARS && this.readyForShearing()) {
            this.shear(SoundCategory.PLAYERS);
            if (!this.level.isClientSide) {
                itemstack.hurtAndBreak(1, player, (p_213442_1_) -> {
                    p_213442_1_.broadcastBreakEvent(hand);
                });
            }

            return ActionResultType.sidedSuccess(this.level.isClientSide);
        } else {
            return super.mobInteract(player, hand);
        }
    }

    @Override
    public void addAdditionalSaveData(@NotNull CompoundNBT compound) {
        super.addAdditionalSaveData(compound);
        compound.putString("Type", this.getMooshroomType().name);
        if (this.hasStewEffect != null) {
            compound.putByte("EffectId", (byte) Effect.getId(this.hasStewEffect));
            compound.putInt("EffectDuration", this.effectDuration);
        }
    }

    @Override
    public void readAdditionalSaveData(@NotNull CompoundNBT compound) {
        super.readAdditionalSaveData(compound);
        this.setMooshroomType(InfectedMooshroomEntity.Type.getTypeByName(compound.getString("Type")));
        if (compound.contains("EffectId", 1)) {
            this.hasStewEffect = Effect.byId(compound.getByte("EffectId"));
        }

        if (compound.contains("EffectDuration", 3)) {
            this.effectDuration = compound.getInt("EffectDuration");
        }
    }

    public InfectedMooshroomEntity.Type getMooshroomType() {
        return InfectedMooshroomEntity.Type.getTypeByName(this.entityData.get(MOOSHROOM_TYPE));
    }

    private void setMooshroomType(@NotNull InfectedMooshroomEntity.Type typeIn) {
        this.entityData.set(MOOSHROOM_TYPE, typeIn.name);
    }

    @Override
    public void shear(@NotNull SoundCategory soundCategory) {
        this.level.playSound(null, this, SoundEvents.MOOSHROOM_SHEAR, soundCategory, 1.0F, 1.0F);
        if (!this.level.isClientSide()) {
            ((ServerWorld) this.level).sendParticles(ParticleTypes.EXPLOSION, this.getX(), this.getY(0.5D), this.getZ(), 1, 0.0D, 0.0D, 0.0D, 0.0D);
            this.remove();
            InfectedCowEntity cowentity = EntityRegistry.INFECTED_COW_ENTITY.create(this.level);
            assert cowentity != null;
            cowentity.moveTo(this.getX(), this.getY(), this.getZ(), this.yRot, this.xRot);
            cowentity.setHealth(this.getHealth());
            cowentity.yBodyRot = this.yBodyRot;
            if (this.hasCustomName()) {
                cowentity.setCustomName(this.getCustomName());
                cowentity.setCustomNameVisible(this.isCustomNameVisible());
            }

            if (this.isPersistenceRequired()) {
                cowentity.setPersistenceRequired();
            }

            cowentity.setInvulnerable(this.isInvulnerable());
            this.level.addFreshEntity(cowentity);

            for (int i = 0; i < 5; ++i) {
                this.level.addFreshEntity(new ItemEntity(this.level, this.getX(), this.getY(1.0D), this.getZ(), new ItemStack(this.getMooshroomType().renderState.getBlock())));
            }
        }

    }

    @Override
    public boolean readyForShearing() {
        return this.isAlive();
    }

    public static boolean hasViewOfSky(@NotNull IWorld worldIn, @NotNull BlockPos pos) {
        return worldIn.canSeeSky(pos);
    }

    @Override
    public @NotNull ResourceLocation getDefaultLootTable() {
        return EntityType.MOOSHROOM.getDefaultLootTable();
    }

    public enum Type {
        RED("red", Blocks.RED_MUSHROOM.defaultBlockState()),
        BROWN("brown", Blocks.BROWN_MUSHROOM.defaultBlockState());

        private final String name;
        private final BlockState renderState;

        Type(String nameIn, BlockState renderStateIn) {
            this.name = nameIn;
            this.renderState = renderStateIn;
        }

        @OnlyIn(Dist.CLIENT)
        public BlockState getRenderState() {
            return this.renderState;
        }

        private static InfectedMooshroomEntity.Type getTypeByName(String nameIn) {
            for(InfectedMooshroomEntity.Type mooshroomentity$type : values()) {
                if (mooshroomentity$type.name.equals(nameIn)) {
                    return mooshroomentity$type;
                }
            }
            return RED;
        }
    }
}