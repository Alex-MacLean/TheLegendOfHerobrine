package com.herobrinemod.herobrine.entities;

import com.google.common.collect.Maps;
import com.herobrinemod.herobrine.items.ItemList;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.IllagerEntity;
import net.minecraft.entity.passive.GolemEntity;
import net.minecraft.entity.passive.SheepEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.loot.LootTables;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.EnumMap;
import java.util.Map;
import java.util.stream.Collectors;

public class InfectedSheepEntity extends InfectedEntity implements Shearable {
    private static final TrackedData<Byte> COLOR = DataTracker.registerData(InfectedSheepEntity.class, TrackedDataHandlerRegistry.BYTE);
    private static final Map<DyeColor, ItemConvertible> DROPS = Util.make(Maps.newEnumMap(DyeColor.class), (map) -> {
        map.put(DyeColor.WHITE, Blocks.WHITE_WOOL);
        map.put(DyeColor.ORANGE, Blocks.ORANGE_WOOL);
        map.put(DyeColor.MAGENTA, Blocks.MAGENTA_WOOL);
        map.put(DyeColor.LIGHT_BLUE, Blocks.LIGHT_BLUE_WOOL);
        map.put(DyeColor.YELLOW, Blocks.YELLOW_WOOL);
        map.put(DyeColor.LIME, Blocks.LIME_WOOL);
        map.put(DyeColor.PINK, Blocks.PINK_WOOL);
        map.put(DyeColor.GRAY, Blocks.GRAY_WOOL);
        map.put(DyeColor.LIGHT_GRAY, Blocks.LIGHT_GRAY_WOOL);
        map.put(DyeColor.CYAN, Blocks.CYAN_WOOL);
        map.put(DyeColor.PURPLE, Blocks.PURPLE_WOOL);
        map.put(DyeColor.BLUE, Blocks.BLUE_WOOL);
        map.put(DyeColor.BROWN, Blocks.BROWN_WOOL);
        map.put(DyeColor.GREEN, Blocks.GREEN_WOOL);
        map.put(DyeColor.RED, Blocks.RED_WOOL);
        map.put(DyeColor.BLACK, Blocks.BLACK_WOOL);
    });
    private static final EnumMap<DyeColor, float[]> COLORS = Maps.newEnumMap((Map)Arrays.stream(DyeColor.values()).collect(Collectors.toMap((color) -> color, InfectedSheepEntity::getDyedColor)));
    private int eatGrassTimer;
    private EatGrassGoal eatGrassGoal;

    @Contract(value = "_ -> new", pure = true)
    private static float @NotNull [] getDyedColor(DyeColor color) {
        if (color == DyeColor.WHITE) {
            return new float[]{0.9019608f, 0.9019608f, 0.9019608f};
        }
        float[] fs = color.getColorComponents();
        return new float[]{fs[0] * 0.75f, fs[1] * 0.75f, fs[2] * 0.75f};
    }

    public static float[] getRgbColor(DyeColor dyeColor) {
        return COLORS.get(dyeColor);
    }

    public InfectedSheepEntity(EntityType<? extends HostileEntity> entityType, World world) {
        super(entityType, world);
        this.experiencePoints = 3;
        this.setConversionEntity(EntityType.SHEEP);
    }

    @Override
    public void convert() {
        this.world.sendEntityStatus(this, (byte) 16);
        this.dropItem(ItemList.CURSED_DUST);
        SheepEntity entity = (SheepEntity) this.convertTo(getConversionEntity(), false);
        assert entity != null;
        entity.setSheared(this.isSheared());
        entity.addStatusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, 300, 1));
        entity.addStatusEffect(new StatusEffectInstance(StatusEffects.HEALTH_BOOST, 300, 1));
        entity.initialize((ServerWorldAccess) world, world.getLocalDifficulty(this.getBlockPos()), SpawnReason.CONVERSION, null, null);
        entity.setColor(this.getColor());
    }

    @Override
    protected void initGoals() {
        this.eatGrassGoal = new EatGrassGoal(this);
        this.goalSelector.add(0, new SwimGoal(this));
        this.goalSelector.add(1, this.eatGrassGoal);
        this.goalSelector.add(2, new MeleeAttackGoal(this, 1.0, false));
        this.goalSelector.add(3, new ActiveTargetGoal<>(this, IllagerEntity.class, false));
        this.goalSelector.add(4, new ActiveTargetGoal<>(this, PlayerEntity.class, false));
        //this.goalSelector.add(5, new ActiveTargetGoal<>(this, SurvivorEntity.class, false));
        this.goalSelector.add(6, new ActiveTargetGoal<>(this, GolemEntity.class, false));
        this.goalSelector.add(7, new WanderAroundFarGoal(this, 1.0));
        this.goalSelector.add(8, new LookAtEntityGoal(this, IllagerEntity.class, 8.0f));
        this.goalSelector.add(9, new LookAtEntityGoal(this, PlayerEntity.class, 8.0f));
        //this.goalSelector.add(10, new LookAtEntityGoal(this, SurvivorEntity.class, 8.0f));
        this.goalSelector.add(11, new LookAtEntityGoal(this, GolemEntity.class, 8.0f));
        this.goalSelector.add(12, new LookAroundGoal(this));
    }

    public static DefaultAttributeContainer.Builder registerAttributes() {
        return createHostileAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 8.0)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 2.0)
                .add(EntityAttributes.GENERIC_FOLLOW_RANGE, 16.0)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.23);
    }

    @Override
    protected void mobTick() {
        this.eatGrassTimer = this.eatGrassGoal.getTimer();
        super.mobTick();
    }

    @Override
    public void tickMovement() {
        if (this.world.isClient) {
            this.eatGrassTimer = Math.max(0, this.eatGrassTimer - 1);
        }
        super.tickMovement();
    }

    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(COLOR, (byte)0);
    }

    @Override
    public Identifier getLootTableId() {
        if (this.isSheared()) {
            return super.getLootTableId();
        }
        return switch (this.getColor()) {
            case WHITE -> LootTables.WHITE_SHEEP_ENTITY;
            case ORANGE -> LootTables.ORANGE_SHEEP_ENTITY;
            case MAGENTA -> LootTables.MAGENTA_SHEEP_ENTITY;
            case LIGHT_BLUE -> LootTables.LIGHT_BLUE_SHEEP_ENTITY;
            case YELLOW -> LootTables.YELLOW_SHEEP_ENTITY;
            case LIME -> LootTables.LIME_SHEEP_ENTITY;
            case PINK -> LootTables.PINK_SHEEP_ENTITY;
            case GRAY -> LootTables.GRAY_SHEEP_ENTITY;
            case LIGHT_GRAY -> LootTables.LIGHT_GRAY_SHEEP_ENTITY;
            case CYAN -> LootTables.CYAN_SHEEP_ENTITY;
            case PURPLE -> LootTables.PURPLE_SHEEP_ENTITY;
            case BLUE -> LootTables.BLUE_SHEEP_ENTITY;
            case BROWN -> LootTables.BROWN_SHEEP_ENTITY;
            case GREEN -> LootTables.GREEN_SHEEP_ENTITY;
            case RED -> LootTables.RED_SHEEP_ENTITY;
            case BLACK -> LootTables.BLACK_SHEEP_ENTITY;
        };
    }
    @Override
    public void handleStatus(byte status) {
        if (status == EntityStatuses.SET_SHEEP_EAT_GRASS_TIMER_OR_PRIME_TNT_MINECART) {
            this.eatGrassTimer = 40;
        } else {
            super.handleStatus(status);
        }
    }

    public float getNeckAngle(float delta) {
        if (this.eatGrassTimer <= 0) {
            return 0.0f;
        }
        if (this.eatGrassTimer >= 4 && this.eatGrassTimer <= 36) {
            return 1.0f;
        }
        if (this.eatGrassTimer < 4) {
            return ((float)this.eatGrassTimer - delta) / 4.0f;
        }
        return -((float)(this.eatGrassTimer - 40) - delta) / 4.0f;
    }

    public float getHeadAngle(float delta) {
        if (this.eatGrassTimer > 4 && this.eatGrassTimer <= 36) {
            float f = ((float)(this.eatGrassTimer - 4) - delta) / 32.0f;
            return 0.62831855f + 0.21991149f * MathHelper.sin(f * 28.7f);
        }
        if (this.eatGrassTimer > 0) {
            return 0.62831855f;
        }
        return this.getPitch() * ((float)Math.PI / 180);
    }

    @Override
    public ActionResult interactMob(PlayerEntity player2, Hand hand) {
        ItemStack itemStack = player2.getStackInHand(hand);
        if (itemStack.isOf(Items.SHEARS)) {
            if (!this.world.isClient && this.isShearable()) {
                this.sheared(SoundCategory.PLAYERS);
                this.emitGameEvent(GameEvent.SHEAR, player2);
                itemStack.damage(1, player2, player -> player.sendToolBreakStatus(hand));
                return ActionResult.SUCCESS;
            }
            return ActionResult.CONSUME;
        }
        return super.interactMob(player2, hand);
    }

    @Override
    public boolean isShearable() {
        return this.isAlive() && !this.isSheared();
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        nbt.putBoolean("Sheared", this.isSheared());
        nbt.putByte("Color", (byte)this.getColor().getId());
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        this.setSheared(nbt.getBoolean("Sheared"));
        this.setColor(DyeColor.byId(nbt.getByte("Color")));
    }

    public DyeColor getColor() {
        return DyeColor.byId(this.dataTracker.get(COLOR) & 0xF);
    }

    public void setColor(DyeColor color) {
        byte b = this.dataTracker.get(COLOR);
        this.dataTracker.set(COLOR, (byte)(b & 0xF0 | color.getId() & 0xF));
    }

    public boolean isSheared() {
        return (this.dataTracker.get(COLOR) & 0x10) != 0;
    }

    public void setSheared(boolean sheared) {
        byte b = this.dataTracker.get(COLOR);
        if (sheared) {
            this.dataTracker.set(COLOR, (byte)(b | 0x10));
        } else {
            this.dataTracker.set(COLOR, (byte)(b & 0xFFFFFFEF));
        }
    }

    @Override
    public void onEatingGrass() {
        super.onEatingGrass();
        this.setSheared(false);
    }

    @Override
    @Nullable
    public EntityData initialize(@NotNull ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, @Nullable EntityData entityData, @Nullable NbtCompound entityNbt) {
        this.setColor(InfectedSheepEntity.generateDefaultColor(world.getRandom()));
        return super.initialize(world, difficulty, spawnReason, entityData, entityNbt);
    }

    public static DyeColor generateDefaultColor(@NotNull Random random) {
        int i = random.nextInt(100);
        if (i < 5) {
            return DyeColor.BLACK;
        }
        if (i < 10) {
            return DyeColor.GRAY;
        }
        if (i < 15) {
            return DyeColor.LIGHT_GRAY;
        }
        if (i < 18) {
            return DyeColor.BROWN;
        }
        if (random.nextInt(500) == 0) {
            return DyeColor.PINK;
        }
        return DyeColor.WHITE;
    }

    @Override
    protected float getActiveEyeHeight(EntityPose pose, EntityDimensions dimensions) {
        return 0.95f * dimensions.height;
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.ENTITY_SHEEP_AMBIENT;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundEvents.ENTITY_SHEEP_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_SHEEP_DEATH;
    }

    @Override
    protected void playStepSound(BlockPos pos, BlockState state) {
        this.playSound(SoundEvents.ENTITY_SHEEP_STEP, 0.15f, 1.0f);
    }

    @Override
    public void sheared(SoundCategory shearedSoundCategory) {
        this.world.playSoundFromEntity(null, this, SoundEvents.ENTITY_SHEEP_SHEAR, shearedSoundCategory, 1.0f, 1.0f);
        this.setSheared(true);
        int i = 1 + this.random.nextInt(3);
        for (int j = 0; j < i; ++j) {
            ItemEntity itemEntity = this.dropItem(DROPS.get(this.getColor()), 1);
            if (itemEntity == null) continue;
            itemEntity.setVelocity(itemEntity.getVelocity().add((this.random.nextFloat() - this.random.nextFloat()) * 0.1f, this.random.nextFloat() * 0.05f, (this.random.nextFloat() - this.random.nextFloat()) * 0.1f));
        }
    }
}
