package com.herobrine.mod.entities;

import com.herobrine.mod.config.Config;
import com.herobrine.mod.util.entities.EntityRegistry;
import com.herobrine.mod.util.savedata.Variables;
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
    private static final DataParameter<String> MOOSHROOM_TYPE = EntityDataManager.createKey(InfectedMooshroomEntity.class, DataSerializers.STRING);
    private Effect hasStewEffect;
    private int effectDuration;
    private UUID lightningUUID;

    public InfectedMooshroomEntity(EntityType<? extends InfectedMooshroomEntity> type, World worldIn) {
        super(type, worldIn);
        experienceValue = 3;
    }

    @Override
    public boolean attackEntityFrom(@NotNull DamageSource source, float amount) {
        if (source.getImmediateSource() instanceof UnholyWaterEntity) {
            return false;
        }
        if (source.getImmediateSource() instanceof HolyWaterEntity) {
            MooshroomEntity mooshroomEntity = EntityType.MOOSHROOM.create(this.world);
            assert mooshroomEntity != null;
            mooshroomEntity.setLocationAndAngles(this.getPosX(), this.getPosY(), this.getPosZ(), this.rotationYaw, this.rotationPitch);
            mooshroomEntity.onInitialSpawn((IServerWorld) this.world, this.world.getDifficultyForLocation(this.getPosition()), SpawnReason.CONVERSION, null, null);
            mooshroomEntity.setNoAI(this.isAIDisabled());
            if (this.hasCustomName()) {
                mooshroomEntity.setCustomName(this.getCustomName());
                mooshroomEntity.setCustomNameVisible(this.isCustomNameVisible());
            }
            mooshroomEntity.enablePersistence();
            mooshroomEntity.setGrowingAge(0);
            mooshroomEntity.setMooshroomType(MooshroomEntity.Type.valueOf(this.getMooshroomType().toString()));
            this.world.setEntityState(this, (byte)16);
            this.world.addEntity(mooshroomEntity);
            this.remove();
        }
        return super.attackEntityFrom(source, amount);
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

    public static AttributeModifierMap.MutableAttribute registerAttributes() {
        return MonsterEntity.func_234295_eP_()
                .createMutableAttribute(Attributes.MAX_HEALTH, 10.0D)
                .createMutableAttribute(Attributes.ATTACK_DAMAGE, 3.0D)
                .createMutableAttribute(Attributes.FOLLOW_RANGE, 16.0D)
                .createMutableAttribute(Attributes.MOVEMENT_SPEED, 0.2D);
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.ENTITY_COW_AMBIENT;
    }

    @Override
    protected SoundEvent getHurtSound(@NotNull DamageSource damageSourceIn) {
        return SoundEvents.ENTITY_COW_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_COW_DEATH;
    }

    @Override
    protected void playStepSound(@NotNull BlockPos pos, @NotNull BlockState blockIn) {
        this.playSound(SoundEvents.ENTITY_COW_STEP, 0.15F, 1.0F);
    }

    @Override
    protected float getSoundVolume() {
        return 0.4F;
    }

    @Override
    public void func_241841_a(@NotNull ServerWorld world, LightningBoltEntity lightningBolt) {
        UUID uuid = lightningBolt.getUniqueID();
        if (!uuid.equals(this.lightningUUID)) {
            this.setMooshroomType(this.getMooshroomType() == InfectedMooshroomEntity.Type.RED ? InfectedMooshroomEntity.Type.BROWN : InfectedMooshroomEntity.Type.RED);
            this.lightningUUID = uuid;
            this.playSound(SoundEvents.ENTITY_MOOSHROOM_CONVERT, 2.0F, 1.0F);
        }

    }

    @Override
    protected void registerData() {
        super.registerData();
        this.dataManager.register(MOOSHROOM_TYPE, InfectedMooshroomEntity.Type.RED.name);
    }

    @Override
    public @NotNull ActionResultType func_230254_b_(@NotNull PlayerEntity player, @NotNull Hand hand) {
        ItemStack itemstack = player.getHeldItem(hand);
         if (itemstack.getItem() == Items.SHEARS) {
            this.world.addParticle(ParticleTypes.EXPLOSION, this.getPosX(), this.getPosYHeight(0.5D), this.getPosZ(), 0.0D, 0.0D, 0.0D);
            if (!this.world.isRemote) {
                this.remove();
                InfectedCowEntity cowentity = EntityRegistry.INFECTED_COW_ENTITY.create(this.world);
                assert cowentity != null;
                cowentity.setLocationAndAngles(this.getPosX(), this.getPosY(), this.getPosZ(), this.rotationYaw, this.rotationPitch);
                cowentity.setHealth(this.getHealth());
                cowentity.renderYawOffset = this.renderYawOffset;
                if (this.hasCustomName()) {
                    cowentity.setCustomName(this.getCustomName());
                    cowentity.setCustomNameVisible(this.isCustomNameVisible());
                }

                if (this.isNoDespawnRequired()) {
                    cowentity.enablePersistence();
                }

                cowentity.setInvulnerable(this.isInvulnerable());
                this.world.addEntity(cowentity);

                for(int k = 0; k < 5; ++k) {
                    this.world.addEntity(new ItemEntity(this.world, this.getPosX(), this.getPosYHeight(1.0D), this.getPosZ(), new ItemStack(this.getMooshroomType().renderState.getBlock())));
                }

                itemstack.damageItem(1, player, (p_213442_1_) -> p_213442_1_.sendBreakAnimation(hand));
                this.playSound(SoundEvents.ENTITY_MOOSHROOM_SHEAR, 1.0F, 1.0F);
            }

            return ActionResultType.SUCCESS;
        } else {
            return super.func_230254_b_(player, hand);
        }
    }

    @Override
    public void writeAdditional(@NotNull CompoundNBT compound) {
        super.writeAdditional(compound);
        compound.putString("Type", this.getMooshroomType().name);
        if (this.hasStewEffect != null) {
            compound.putByte("EffectId", (byte)Effect.getId(this.hasStewEffect));
            compound.putInt("EffectDuration", this.effectDuration);
        }
    }

    @Override
    public void readAdditional(@NotNull CompoundNBT compound) {
        super.readAdditional(compound);
        this.setMooshroomType(InfectedMooshroomEntity.Type.getTypeByName(compound.getString("Type")));
        if (compound.contains("EffectId", 1)) {
            this.hasStewEffect = Effect.get(compound.getByte("EffectId"));
        }

        if (compound.contains("EffectDuration", 3)) {
            this.effectDuration = compound.getInt("EffectDuration");
        }
    }

    private void setMooshroomType(@NotNull InfectedMooshroomEntity.Type typeIn) {
        this.dataManager.set(MOOSHROOM_TYPE, typeIn.name);
    }

    public InfectedMooshroomEntity.Type getMooshroomType() {
        return InfectedMooshroomEntity.Type.getTypeByName(this.dataManager.get(MOOSHROOM_TYPE));
    }

    @Override
    public void shear(@NotNull SoundCategory category) {
        this.world.playMovingSound(null, this, SoundEvents.ENTITY_MOOSHROOM_SHEAR, category, 1.0F, 1.0F);
        if (!this.world.isRemote()) {
            ((ServerWorld)this.world).spawnParticle(ParticleTypes.EXPLOSION, this.getPosX(), this.getPosYHeight(0.5D), this.getPosZ(), 1, 0.0D, 0.0D, 0.0D, 0.0D);
            this.remove();
            InfectedCowEntity entity = EntityRegistry.INFECTED_COW_ENTITY.create(this.world);
            assert entity != null;
            entity.setLocationAndAngles(this.getPosX(), this.getPosY(), this.getPosZ(), this.rotationYaw, this.rotationPitch);
            entity.setHealth(this.getHealth());
            entity.renderYawOffset = this.renderYawOffset;
            if (this.hasCustomName()) {
                entity.setCustomName(this.getCustomName());
                entity.setCustomNameVisible(this.isCustomNameVisible());
            }

            if (this.isNoDespawnRequired()) {
                entity.enablePersistence();
            }

            entity.setInvulnerable(this.isInvulnerable());
            this.world.addEntity(entity);

            for(int i = 0; i < 5; ++i) {
                this.world.addEntity(new ItemEntity(this.world, this.getPosX(), this.getPosYHeight(1.0D), this.getPosZ(), new ItemStack(this.getMooshroomType().renderState.getBlock())));
            }
        }

    }

    @Override
    public boolean isShearable() {
        return true;
    }

    public enum Type {
        RED("red", Blocks.RED_MUSHROOM.getDefaultState()),
        BROWN("brown", Blocks.BROWN_MUSHROOM.getDefaultState());

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

    public static boolean isValidLightLevel(@NotNull IServerWorld worldIn, @NotNull BlockPos pos, @NotNull Random randomIn) {
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

    public static boolean canSpawn(EntityType<? extends AbstractInfectedEntity> type, @NotNull IServerWorld worldIn, SpawnReason reason, BlockPos pos, Random randomIn) {
        return worldIn.getDifficulty() != Difficulty.PEACEFUL && hasViewOfSky(worldIn, pos) && isValidLightLevel(worldIn, pos, randomIn) && canSpawnOn(type, worldIn, reason, pos, randomIn) && Variables.SaveData.get(worldIn.getWorld()).Spawn || worldIn.getDifficulty() != Difficulty.PEACEFUL && hasViewOfSky(worldIn, pos) && isValidLightLevel(worldIn, pos, randomIn) && canSpawnOn(type, worldIn, reason, pos, randomIn) && Config.COMMON.HerobrineAlwaysSpawns.get();
    }

    @Override
    public @NotNull ResourceLocation getLootTable() {
        return EntityType.MOOSHROOM.getLootTable();
    }
}