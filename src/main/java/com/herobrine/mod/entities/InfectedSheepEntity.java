package com.herobrine.mod.entities;

import com.google.common.collect.Maps;
import com.herobrine.mod.util.entities.EntityRegistry;
import com.herobrine.mod.util.misc.Variables;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.DyeColor;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.storage.loot.LootTables;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.IShearable;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

public class InfectedSheepEntity extends MonsterEntity implements IShearable {
    private static final DataParameter<Byte> DYE_COLOR = EntityDataManager.createKey(InfectedSheepEntity.class, DataSerializers.BYTE);
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

    @NotNull
    @Contract(value = "_ -> new", pure = true)
    private static float[] createSheepColor(DyeColor dyeColorIn) {
        if (dyeColorIn == DyeColor.WHITE) {
            return new float[]{0.9019608F, 0.9019608F, 0.9019608F};
        } else {
            float[] afloat = dyeColorIn.getColorComponentValues();
            return new float[]{afloat[0] * 0.75F, afloat[1] * 0.75F, afloat[2] * 0.75F};
        }
    }

    @OnlyIn(Dist.CLIENT)
    public static float[] getDyeRgb(DyeColor dyeColor) {
        return DYE_TO_RGB.get(dyeColor);
    }

    public InfectedSheepEntity(EntityType<? extends InfectedSheepEntity> type, World worldIn) {
        super(type, worldIn);
        experienceValue = 3;
    }

    public InfectedSheepEntity(World worldIn) {
        this((EntityType<? extends InfectedSheepEntity>) EntityRegistry.INFECTED_SHEEP_ENTITY, worldIn);
    }

    @Override
    protected void registerGoals() {
        this.eatGrassGoal = new EatGrassGoal(this);
        this.goalSelector.addGoal(0, new SwimGoal(this));
        this.goalSelector.addGoal(1, new MeleeAttackGoal(this, 1.0D, true));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, PlayerEntity.class, true));
        this.targetSelector.addGoal(3, new HurtByTargetGoal(this));
        this.goalSelector.addGoal(4, this.eatGrassGoal);
        this.goalSelector.addGoal(5, new WaterAvoidingRandomWalkingGoal(this, 1.0D));
        this.goalSelector.addGoal(6, new LookAtGoal(this, PlayerEntity.class, 8.0F));
        this.goalSelector.addGoal(7, new LookRandomlyGoal(this));
    }

    @Override
    protected void updateAITasks() {
        this.sheepTimer = this.eatGrassGoal.getEatingGrassTimer();
        super.updateAITasks();
    }

    @Override
    protected void registerAttributes() {
        super.registerAttributes();
        this.getAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(8.0D);
        this.getAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(2.0D);
        this.getAttribute(SharedMonsterAttributes.ATTACK_KNOCKBACK).setBaseValue(2.0D);
        this.getAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(16.0D);
        this.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.23D);
    }

    @Override
    public boolean attackEntityFrom(@NotNull DamageSource source, float amount) {
        if (source.getImmediateSource() instanceof UnholyWaterEntity)
            return false;
        return super.attackEntityFrom(source, amount);
    }

    @Override
    public boolean attackEntityAsMob(Entity entityIn) {
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
        if (this.world.isRemote) {
            this.sheepTimer = Math.max(0, this.sheepTimer - 1);
        }

        super.livingTick();
    }

    @Override
    protected void registerData() {
        super.registerData();
        this.dataManager.register(DYE_COLOR, (byte)0);
    }

    @NotNull
    @Override
    public ResourceLocation getLootTable() {
        if (this.getSheared()) {
            return this.getType().getLootTable();
        } else {
            switch(this.getFleeceColor()) {
                case WHITE:
                default:
                    return LootTables.ENTITIES_SHEEP_WHITE;
                case ORANGE:
                    return LootTables.ENTITIES_SHEEP_ORANGE;
                case MAGENTA:
                    return LootTables.ENTITIES_SHEEP_MAGENTA;
                case LIGHT_BLUE:
                    return LootTables.ENTITIES_SHEEP_LIGHT_BLUE;
                case YELLOW:
                    return LootTables.ENTITIES_SHEEP_YELLOW;
                case LIME:
                    return LootTables.ENTITIES_SHEEP_LIME;
                case PINK:
                    return LootTables.ENTITIES_SHEEP_PINK;
                case GRAY:
                    return LootTables.ENTITIES_SHEEP_GRAY;
                case LIGHT_GRAY:
                    return LootTables.ENTITIES_SHEEP_LIGHT_GRAY;
                case CYAN:
                    return LootTables.ENTITIES_SHEEP_CYAN;
                case PURPLE:
                    return LootTables.ENTITIES_SHEEP_PURPLE;
                case BLUE:
                    return LootTables.ENTITIES_SHEEP_BLUE;
                case BROWN:
                    return LootTables.ENTITIES_SHEEP_BROWN;
                case GREEN:
                    return LootTables.ENTITIES_SHEEP_GREEN;
                case RED:
                    return LootTables.ENTITIES_SHEEP_RED;
                case BLACK:
                    return LootTables.ENTITIES_SHEEP_BLACK;
            }
        }
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void handleStatusUpdate(byte id) {
        if (id == 10) {
            this.sheepTimer = 40;
        } else {
            super.handleStatusUpdate(id);
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
            return this.sheepTimer > 0 ? ((float)Math.PI / 5F) : this.rotationPitch * ((float)Math.PI / 180F);
        }
    }

    @Override
    public boolean processInteract(@NotNull PlayerEntity player, Hand hand) {
        ItemStack itemstack = player.getHeldItem(hand);
        if (false && itemstack.getItem() == Items.SHEARS && !this.getSheared()) {
            this.func_213612_dV();
            if (!this.world.isRemote) {
                itemstack.damageItem(1, player, (p_213613_1_) -> p_213613_1_.sendBreakAnimation(hand));
            }

            return true;
        } else {
            return super.processInteract(player, hand);
        }
    }

    public void func_213612_dV() {
        if (!this.world.isRemote) {
            this.setSheared(true);
            int i = 1 + this.rand.nextInt(3);

            for(int j = 0; j < i; ++j) {
                ItemEntity itementity = this.entityDropItem(WOOL_BY_COLOR.get(this.getFleeceColor()), 1);
                if (itementity != null) {
                    itementity.setMotion(itementity.getMotion().add((this.rand.nextFloat() - this.rand.nextFloat()) * 0.1F, this.rand.nextFloat() * 0.05F, (this.rand.nextFloat() - this.rand.nextFloat()) * 0.1F));
                }
            }
        }

        this.playSound(SoundEvents.ENTITY_SHEEP_SHEAR, 1.0F, 1.0F);
    }

    @Override
    public void writeAdditional(CompoundNBT compound) {
        super.writeAdditional(compound);
        compound.putBoolean("Sheared", this.getSheared());
        compound.putByte("Color", (byte)this.getFleeceColor().getId());
    }

    @Override
    public void readAdditional(CompoundNBT compound) {
        super.readAdditional(compound);
        this.setSheared(compound.getBoolean("Sheared"));
        this.setFleeceColor(DyeColor.byId(compound.getByte("Color")));
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.ENTITY_SHEEP_AMBIENT;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return SoundEvents.ENTITY_SHEEP_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_SHEEP_DEATH;
    }

    @Override
    protected void playStepSound(@NotNull BlockPos pos, BlockState blockIn) {
        this.playSound(SoundEvents.ENTITY_SHEEP_STEP, 0.15F, 1.0F);
    }

    public DyeColor getFleeceColor() {
        return DyeColor.byId(this.dataManager.get(DYE_COLOR) & 15);
    }

    public void setFleeceColor(@NotNull DyeColor color) {
        byte b0 = this.dataManager.get(DYE_COLOR);
        this.dataManager.set(DYE_COLOR, (byte)(b0 & 240 | color.getId() & 15));
    }

    public boolean getSheared() {
        return (this.dataManager.get(DYE_COLOR) & 16) != 0;
    }

    public void setSheared(boolean sheared) {
        byte b0 = this.dataManager.get(DYE_COLOR);
        if (sheared) {
            this.dataManager.set(DYE_COLOR, (byte)(b0 | 16));
        } else {
            this.dataManager.set(DYE_COLOR, (byte)(b0 & -17));
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

    @Override
    public void eatGrassBonus() {
        this.setSheared(false);
    }

    @Nullable
    @Override
    public ILivingEntityData onInitialSpawn(@NotNull IWorld worldIn, DifficultyInstance difficultyIn, SpawnReason reason, @Nullable ILivingEntityData spawnDataIn, @Nullable CompoundNBT dataTag) {
        this.setFleeceColor(getRandomSheepColor(worldIn.getRandom()));
        Variables.WorldVariables.get(world).syncData(world);
        if ((!(Variables.WorldVariables.get(world).Spawn))) {
            this.remove();
        }
        return super.onInitialSpawn(worldIn, difficultyIn, reason, spawnDataIn, dataTag);
    }

    @Override
    protected float getStandingEyeHeight(Pose poseIn, @NotNull EntitySize sizeIn) {
        return 0.95F * sizeIn.height;
    }

    @Override
    public boolean isShearable(@NotNull ItemStack item, net.minecraft.world.IWorldReader world, BlockPos pos) {
        return !this.getSheared();
    }

    @NotNull
    @Override
    public java.util.List<ItemStack> onSheared(@NotNull ItemStack item, net.minecraft.world.IWorld world, BlockPos pos, int fortune) {
        java.util.List<ItemStack> ret = new java.util.ArrayList<>();
        if (!this.world.isRemote) {
            this.setSheared(true);
            int i = 1 + this.rand.nextInt(3);

            for(int j = 0; j < i; ++j) {
                ret.add(new ItemStack(WOOL_BY_COLOR.get(this.getFleeceColor())));
            }
        }
        this.playSound(SoundEvents.ENTITY_SHEEP_SHEAR, 1.0F, 1.0F);
        return ret;
    }
}