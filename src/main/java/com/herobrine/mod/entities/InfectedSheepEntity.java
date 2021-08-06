package com.herobrine.mod.entities;

import com.google.common.collect.Maps;
import com.herobrine.mod.util.entities.EntityRegistry;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.passive.GolemEntity;
import net.minecraft.entity.passive.SheepEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.DyeColor;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.loot.LootTables;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.IForgeShearable;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

public class InfectedSheepEntity extends AbstractInfectedEntity implements IShearable, IForgeShearable {
    private static final DataParameter<Byte> DYE_COLOR = EntityDataManager.defineId(InfectedSheepEntity.class, DataSerializers.BYTE);
    private static final Map<DyeColor, IItemProvider> WOOL_BY_COLOR = Util.make(Maps.newEnumMap(DyeColor.class), (woolDrop) -> {
        woolDrop.put(DyeColor.WHITE, Blocks.WHITE_WOOL);
        woolDrop.put(DyeColor.ORANGE, Blocks.ORANGE_WOOL);
        woolDrop.put(DyeColor.MAGENTA, Blocks.MAGENTA_WOOL);
        woolDrop.put(DyeColor.LIGHT_BLUE, Blocks.LIGHT_BLUE_WOOL);
        woolDrop.put(DyeColor.YELLOW, Blocks.YELLOW_WOOL);
        woolDrop.put(DyeColor.LIME, Blocks.LIME_WOOL);
        woolDrop.put(DyeColor.PINK, Blocks.PINK_WOOL);
        woolDrop.put(DyeColor.GRAY, Blocks.GRAY_WOOL);
        woolDrop.put(DyeColor.LIGHT_GRAY, Blocks.LIGHT_GRAY_WOOL);
        woolDrop.put(DyeColor.CYAN, Blocks.CYAN_WOOL);
        woolDrop.put(DyeColor.PURPLE, Blocks.PURPLE_WOOL);
        woolDrop.put(DyeColor.BLUE, Blocks.BLUE_WOOL);
        woolDrop.put(DyeColor.BROWN, Blocks.BROWN_WOOL);
        woolDrop.put(DyeColor.GREEN, Blocks.GREEN_WOOL);
        woolDrop.put(DyeColor.RED, Blocks.RED_WOOL);
        woolDrop.put(DyeColor.BLACK, Blocks.BLACK_WOOL);
    });

    private static final Map<DyeColor, float[]> DYE_TO_RGB = Maps.newEnumMap(Arrays.stream(DyeColor.values()).collect(Collectors.toMap((DyeColor color) -> color, InfectedSheepEntity::createSheepColor)));
    private int sheepTimer;
    private EatGrassGoal eatGrassGoal;

    public InfectedSheepEntity(EntityType<? extends InfectedSheepEntity> type, World worldIn) {
        super(type, worldIn);
        xpReward = 3;
    }

    @OnlyIn(Dist.CLIENT)
    public static float[] getDyeRgb(DyeColor dyeColor) {
        return DYE_TO_RGB.get(dyeColor);
    }

    @Contract(value = "_ -> new", pure = true)
    private static float[] createSheepColor(DyeColor dyeColorIn) {
        if (dyeColorIn == DyeColor.WHITE) {
            return new float[]{0.9019608F, 0.9019608F, 0.9019608F};
        } else {
            float[] afloat = dyeColorIn.getTextureDiffuseColors();
            return new float[]{afloat[0] * 0.75F, afloat[1] * 0.75F, afloat[2] * 0.75F};
        }
    }

    public static AttributeModifierMap.MutableAttribute registerAttributes() {
        return MonsterEntity.createMonsterAttributes()
                .add(Attributes.MAX_HEALTH, 8.0D)
                .add(Attributes.ATTACK_DAMAGE, 2.0D)
                .add(Attributes.FOLLOW_RANGE, 16.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.23D);
    }

    public InfectedSheepEntity(World worldIn) {
        this(EntityRegistry.INFECTED_SHEEP_ENTITY, worldIn);
    }

    @Override
    protected void registerGoals() {
        this.eatGrassGoal = new EatGrassGoal(this);
        this.goalSelector.addGoal(0, new SwimGoal(this));
        this.goalSelector.addGoal(1, new MeleeAttackGoal(this, 1.0D, true));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, PlayerEntity.class, true));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, AbstractSurvivorEntity.class, true));
        this.targetSelector.addGoal(4, new NearestAttackableTargetGoal<>(this, GolemEntity.class, true));
        this.targetSelector.addGoal(5, new HurtByTargetGoal(this));
        this.goalSelector.addGoal(6, this.eatGrassGoal);
        this.goalSelector.addGoal(7, new WaterAvoidingRandomWalkingGoal(this, 1.0D));
        this.goalSelector.addGoal(8, new LookAtGoal(this, PlayerEntity.class, 8.0F));
        this.goalSelector.addGoal(9, new LookAtGoal(this, AbstractSurvivorEntity.class, 8.0F));
        this.goalSelector.addGoal(10, new LookAtGoal(this, GolemEntity.class, 8.0F));
        this.goalSelector.addGoal(11, new LookRandomlyGoal(this));
    }

    @Override
    public boolean hurt(@NotNull DamageSource source, float amount) {
        if (source.getDirectEntity() instanceof HolyWaterEntity) {
            SheepEntity entity = this.convertTo(EntityType.SHEEP, false);
            assert entity != null;
            entity.setColor(this.getFleeceColor());
            entity.finalizeSpawn((IServerWorld) this.level, this.level.getCurrentDifficultyAt(entity.blockPosition()), SpawnReason.CONVERSION, null, null);
            this.level.broadcastEntityEvent(this, (byte) 16);
        }
        return super.hurt(source, amount);
    }

    @Override
    protected void customServerAiStep() {
        this.sheepTimer = this.eatGrassGoal.getEatAnimationTick();
        super.customServerAiStep();
    }

    @Override
    public void aiStep() {
        if (this.level.isClientSide) {
            this.sheepTimer = Math.max(0, this.sheepTimer - 1);
        }

        super.aiStep();
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DYE_COLOR, (byte) 0);
    }

    @NotNull
    @Override
    public ResourceLocation getDefaultLootTable() {
        if (this.getSheared()) {
            return EntityType.SHEEP.getDefaultLootTable();
        } else {
            switch (this.getFleeceColor()) {
                case WHITE:
                default:
                    return LootTables.SHEEP_WHITE;
                case ORANGE:
                    return LootTables.SHEEP_ORANGE;
                case MAGENTA:
                    return LootTables.SHEEP_MAGENTA;
                case LIGHT_BLUE:
                    return LootTables.SHEEP_LIGHT_BLUE;
                case YELLOW:
                    return LootTables.SHEEP_YELLOW;
                case LIME:
                    return LootTables.SHEEP_LIME;
                case PINK:
                    return LootTables.SHEEP_PINK;
                case GRAY:
                    return LootTables.SHEEP_GRAY;
                case LIGHT_GRAY:
                    return LootTables.SHEEP_LIGHT_GRAY;
                case CYAN:
                    return LootTables.SHEEP_CYAN;
                case PURPLE:
                    return LootTables.SHEEP_PURPLE;
                case BLUE:
                    return LootTables.SHEEP_BLUE;
                case BROWN:
                    return LootTables.SHEEP_BROWN;
                case GREEN:
                    return LootTables.SHEEP_GREEN;
                case RED:
                    return LootTables.SHEEP_RED;
                case BLACK:
                    return LootTables.SHEEP_BLACK;
            }
        }
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void handleEntityEvent(byte id) {
        if (id == 10) {
            this.sheepTimer = 40;
        } else {
            super.handleEntityEvent(id);
        }

    }

    @OnlyIn(Dist.CLIENT)
    public float getHeadRotationPointY(float p_70894_1_) {
        if (this.sheepTimer <= 0) {
            return 0.0F;
        } else if (this.sheepTimer >= 4 && this.sheepTimer <= 36) {
            return 1.0F;
        } else {
            return this.sheepTimer < 4 ? ((float)this.sheepTimer - p_70894_1_) / 4.0F : -((float)(this.sheepTimer - 40) - p_70894_1_) / 4.0F;
        }
    }

    @OnlyIn(Dist.CLIENT)
    public float getHeadRotationAngleX(float p_70890_1_) {
        if (this.sheepTimer > 4 && this.sheepTimer <= 36) {
            float f = ((float)(this.sheepTimer - 4) - p_70890_1_) / 32.0F;
            return ((float)Math.PI / 5F) + 0.21991149F * MathHelper.sin(f * 28.7F);
        } else {
            return this.sheepTimer > 0 ? ((float) Math.PI / 5F) : this.xRot * ((float) Math.PI / 180F);
        }
    }

    @Override
    public @NotNull ActionResultType mobInteract(@NotNull PlayerEntity player, @NotNull Hand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        if (itemstack.getItem() == Items.SHEARS) {
            if (!this.level.isClientSide && this.readyForShearing()) {
                this.shear(SoundCategory.PLAYERS);
                itemstack.hurtAndBreak(1, player, (p_213613_1_) -> {
                    p_213613_1_.broadcastBreakEvent(hand);
                });
                return ActionResultType.SUCCESS;
            } else {
                return ActionResultType.CONSUME;
            }
        } else {
            return super.mobInteract(player, hand);
        }
    }

    @Override
    public void shear(@NotNull SoundCategory soundCategory) {
        this.level.playSound(null, this, SoundEvents.SHEEP_SHEAR, soundCategory, 1.0F, 1.0F);
        this.setSheared(true);
        int i = 1 + this.random.nextInt(3);

        for (int j = 0; j < i; ++j) {
            ItemEntity itementity = this.spawnAtLocation(WOOL_BY_COLOR.get(this.getFleeceColor()), 1);
            if (itementity != null) {
                itementity.setDeltaMovement(itementity.getDeltaMovement().add((this.random.nextFloat() - this.random.nextFloat()) * 0.1F, this.random.nextFloat() * 0.05F, (this.random.nextFloat() - this.random.nextFloat()) * 0.1F));
            }
        }

    }

    @Override
    public boolean readyForShearing() {
        return this.isAlive() && !this.getSheared();
    }

    @Override
    public void addAdditionalSaveData(@NotNull CompoundNBT compound) {
        super.addAdditionalSaveData(compound);
        compound.putBoolean("Sheared", this.getSheared());
        compound.putByte("Color", (byte) this.getFleeceColor().getId());
    }

    @Override
    public void readAdditionalSaveData(@NotNull CompoundNBT compound) {
        super.readAdditionalSaveData(compound);
        this.setSheared(compound.getBoolean("Sheared"));
        this.setFleeceColor(DyeColor.byId(compound.getByte("Color")));
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.SHEEP_AMBIENT;
    }

    @Override
    protected SoundEvent getHurtSound(@NotNull DamageSource damageSourceIn) {
        return SoundEvents.SHEEP_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.SHEEP_DEATH;
    }

    @Override
    protected void playStepSound(@NotNull BlockPos pos, @NotNull BlockState blockIn) {
        this.playSound(SoundEvents.SHEEP_STEP, 0.15F, 1.0F);
    }

    public DyeColor getFleeceColor() {
        return DyeColor.byId(this.entityData.get(DYE_COLOR) & 15);
    }

    public void setFleeceColor(@NotNull DyeColor color) {
        byte b0 = this.entityData.get(DYE_COLOR);
        this.entityData.set(DYE_COLOR, (byte) (b0 & 240 | color.getId() & 15));
    }

    public boolean getSheared() {
        return (this.entityData.get(DYE_COLOR) & 16) != 0;
    }

    public void setSheared(boolean sheared) {
        byte b0 = this.entityData.get(DYE_COLOR);
        if (sheared) {
            this.entityData.set(DYE_COLOR, (byte) (b0 | 16));
        } else {
            this.entityData.set(DYE_COLOR, (byte) (b0 & -17));
        }

    }

    public static DyeColor getRandomSheepColor(@NotNull Random random) {
        int i = random.nextInt(100);
        if (i < 5) {
            return DyeColor.BLACK;
        } else if (i < 10) {
            return DyeColor.GRAY;
        } else if (i < 15) {
            return DyeColor.LIGHT_GRAY;
        } else if (i < 18) {
            return DyeColor.BROWN;
        } else {
            return random.nextInt(500) == 0 ? DyeColor.PINK : DyeColor.WHITE;
        }
    }

    public void ate() {
        this.setSheared(false);
    }

    @Nullable
    @Override
    public ILivingEntityData finalizeSpawn(@NotNull IServerWorld worldIn, @NotNull DifficultyInstance difficultyIn, @NotNull SpawnReason reason, @Nullable ILivingEntityData spawnDataIn, @Nullable CompoundNBT dataTag) {
        this.setFleeceColor(getRandomSheepColor(worldIn.getRandom()));
        return super.finalizeSpawn(worldIn, difficultyIn, reason, spawnDataIn, dataTag);
    }

    @Override
    protected float getStandingEyeHeight(@NotNull Pose poseIn, @NotNull EntitySize sizeIn) {
        return 0.95F * sizeIn.height;
    }

    @Override
    public boolean isShearable(@javax.annotation.Nonnull ItemStack item, World world, BlockPos pos) {
        return readyForShearing();
    }

    @javax.annotation.Nonnull
    @Override
    public java.util.List<ItemStack> onSheared(@Nullable PlayerEntity player, @javax.annotation.Nonnull ItemStack item, @NotNull World world, BlockPos pos, int fortune) {
        world.playSound(null, this, SoundEvents.SHEEP_SHEAR, player == null ? SoundCategory.BLOCKS : SoundCategory.PLAYERS, 1.0F, 1.0F);
        if (!world.isClientSide) {
            this.setSheared(true);
            int i = 1 + this.random.nextInt(3);

            java.util.List<ItemStack> items = new java.util.ArrayList<>();
            for (int j = 0; j < i; ++j) {
                items.add(new ItemStack(WOOL_BY_COLOR.get(this.getFleeceColor())));
            }
            return items;
        }
        return java.util.Collections.emptyList();
    }
}