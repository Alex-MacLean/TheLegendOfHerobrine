package com.herobrinemod.herobrine.entities;

import net.minecraft.block.BlockState;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.TargetPredicate;
import net.minecraft.entity.ai.goal.ActiveTargetGoal;
import net.minecraft.entity.ai.goal.LookAroundGoal;
import net.minecraft.entity.ai.goal.LookAtEntityGoal;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.IllagerEntity;
import net.minecraft.entity.passive.GolemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.Difficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import net.minecraft.world.WorldEvents;
import net.minecraft.world.biome.BiomeKeys;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class InfectedBatEntity extends InfectedEntity{
    public static final int wingFlap = MathHelper.ceil(2.4166098f);
    private static final TrackedData<Byte> BAT_FLAGS = DataTracker.registerData(InfectedBatEntity.class, TrackedDataHandlerRegistry.BYTE);
    private static final TargetPredicate CLOSE_PLAYER_PREDICATE = TargetPredicate.createNonAttackable().setBaseMaxDistance(4.0);
    @Nullable
    private BlockPos hangingPosition;

    public InfectedBatEntity(EntityType<? extends HostileEntity> entityType, World world) {
        super(entityType, world);
        this.experiencePoints = 3;
        this.setConversionEntity(EntityType.BAT);
        if (!world.isClient) {
            this.setRoosting(true);
        }
    }

    @Override
    protected void initGoals() {
        this.goalSelector.add(0, new MeleeAttackGoal(this, 1.0, false));
        this.goalSelector.add(1, new ActiveTargetGoal<>(this, IllagerEntity.class, false));
        this.goalSelector.add(2, new ActiveTargetGoal<>(this, PlayerEntity.class, false));
        this.goalSelector.add(3, new ActiveTargetGoal<>(this, SurvivorEntity.class, false));
        this.goalSelector.add(4, new ActiveTargetGoal<>(this, GolemEntity.class, false));
        this.goalSelector.add(5, new LookAtEntityGoal(this, IllagerEntity.class, 8.0f));
        this.goalSelector.add(6, new LookAtEntityGoal(this, PlayerEntity.class, 8.0f));
        this.goalSelector.add(7, new LookAtEntityGoal(this, SurvivorEntity.class, 8.0f));
        this.goalSelector.add(8, new LookAtEntityGoal(this, GolemEntity.class, 8.0f));
        this.goalSelector.add(9, new LookAroundGoal(this));
    }

    public static DefaultAttributeContainer.Builder registerAttributes() {
        return createHostileAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 6.0)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 1.0)
                .add(EntityAttributes.GENERIC_FOLLOW_RANGE, 16.0);
    }

    @Override
    protected float getActiveEyeHeight(EntityPose pose, @NotNull EntityDimensions dimensions) {
        return dimensions.height / 2.0f;
    }

    @Override
    public boolean isFlappingWings() {
        return !this.isRoosting() && this.age % wingFlap == 0;
    }

    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(BAT_FLAGS, (byte)0);
    }

    @Override
    protected float getSoundVolume() {
        return 0.1f;
    }

    @Override
    public float getSoundPitch() {
        return super.getSoundPitch() * 0.95f;
    }

    @Override
    @Nullable
    public SoundEvent getAmbientSound() {
        if (this.isRoosting() && this.random.nextInt(4) != 0) {
            return null;
        }
        return SoundEvents.ENTITY_BAT_AMBIENT;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundEvents.ENTITY_BAT_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_BAT_DEATH;
    }

    @Override
    public boolean isPushable() {
        return false;
    }

    @Override
    protected void pushAway(Entity entity) {
    }

    @Override
    protected void tickCramming() {
    }

    public boolean isRoosting() {
        return (this.dataTracker.get(BAT_FLAGS) & 1) != 0;
    }

    public void setRoosting(boolean roosting) {
        byte b = this.dataTracker.get(BAT_FLAGS);
        if (roosting) {
            this.dataTracker.set(BAT_FLAGS, (byte)(b | 1));
        } else {
            this.dataTracker.set(BAT_FLAGS, (byte)(b & 0xFFFFFFFE));
        }
    }

    public static boolean canBatSpawn(EntityType<? extends InfectedBatEntity> type, @NotNull ServerWorldAccess world, SpawnReason spawnReason, @NotNull BlockPos pos, Random random) {
        if(world.getBiome(pos).matchesId(BiomeKeys.MUSHROOM_FIELDS.getValue()) && random.nextInt(20) > 1) {
            return false;
        }
        return pos.getY() < world.getSeaLevel() && world.getDifficulty() != Difficulty.PEACEFUL && isSpawnDark(world, pos, random) && canMobSpawn(type, world, spawnReason, pos, random) && HerobrineSpawnHelper.canHerobrineSpawn();
    }

    @Override
    public void tick() {
        super.tick();
        if (this.isRoosting()) {
            this.setVelocity(Vec3d.ZERO);
            this.setPos(this.getX(), (double)MathHelper.floor(this.getY()) + 1.0 - (double)this.getHeight(), this.getZ());
        } else {
            this.setVelocity(this.getVelocity().multiply(1.0, 0.6, 1.0));
        }
    }

    @Override
    protected void mobTick() {
        super.mobTick();
        BlockPos blockPos = this.getBlockPos();
        BlockPos blockPos2 = blockPos.up();
        if (this.isRoosting()) {
            boolean bl = this.isSilent();
            if (this.getWorld().getBlockState(blockPos2).isSolidBlock(this.getWorld(), blockPos)) {
                if (this.random.nextInt(200) == 0) {
                    this.headYaw = this.random.nextInt(360);
                }
                if (this.getWorld().getClosestPlayer(CLOSE_PLAYER_PREDICATE, this) != null) {
                    this.setRoosting(false);
                    if (!bl) {
                        this.getWorld().syncWorldEvent(null, WorldEvents.BAT_TAKES_OFF, blockPos, 0);
                    }
                }
            } else {
                this.setRoosting(false);
                if (!bl) {
                    this.getWorld().syncWorldEvent(null, WorldEvents.BAT_TAKES_OFF, blockPos, 0);
                }
            }
        } else {
            if (!(this.hangingPosition == null || this.getWorld().isAir(this.hangingPosition) && this.hangingPosition.getY() > this.getWorld().getBottomY())) {
                this.hangingPosition = null;
            }
            if (this.hangingPosition == null || this.random.nextInt(30) == 0 || this.hangingPosition.isWithinDistance(this.getPos(), 2.0)) {
                this.hangingPosition = BlockPos.ofFloored(this.getX() + (double)this.random.nextInt(7) - (double)this.random.nextInt(7), this.getY() + (double)this.random.nextInt(6) - 2.0, this.getZ() + (double)this.random.nextInt(7) - (double)this.random.nextInt(7));
            }
            double d = (double)this.hangingPosition.getX() + 0.5 - this.getX();
            assert this.hangingPosition != null;
            double e = (double)this.hangingPosition.getY() + 0.1 - this.getY();
            assert this.hangingPosition != null;
            double f = (double)this.hangingPosition.getZ() + 0.5 - this.getZ();
            Vec3d vec3d = this.getVelocity();
            Vec3d vec3d2 = vec3d.add((Math.signum(d) * 0.5 - vec3d.x) * (double)0.1f, (Math.signum(e) * (double)0.7f - vec3d.y) * (double)0.1f, (Math.signum(f) * 0.5 - vec3d.z) * (double)0.1f);
            this.setVelocity(vec3d2);
            float g = (float)(MathHelper.atan2(vec3d2.z, vec3d2.x) * 57.2957763671875) - 90.0f;
            float h = MathHelper.wrapDegrees(g - this.getYaw());
            if (this.getTarget() != null) {
                this.moveToEntity(this.getTarget());
            } else {
                this.forwardSpeed = 0.5f;
            }
            this.setYaw(this.getYaw() + h);
            if (this.random.nextInt(100) == 0 && this.getWorld().getBlockState(blockPos2).isSolidBlock(this.getWorld(), blockPos2)) {
                this.setRoosting(true);
            }
        }
    }

    public void moveToEntity(@NotNull Entity entity) {
        this.setRoosting(false);
        double x = entity.getX() - this.getX();
        double y = (entity.getY() + 1) - this.getY();
        double z = entity.getZ() - this.getZ();
        Vec3d motion = new Vec3d(x - (x * 0.9), y - (y * 0.9), z - (z * 0.9));
        this.setVelocity(motion);
    }

    @Override
    protected Entity.MoveEffect getMoveEffect() {
        return Entity.MoveEffect.EVENTS;
    }

    @Override
    protected void fall(double heightDifference, boolean onGround, BlockState state, BlockPos landedPosition) {
    }

    @Override
    public boolean canAvoidTraps() {
        return true;
    }

    @Override
    public boolean damage(DamageSource source, float amount) {
        if (!this.getWorld().isClient && this.isRoosting()) {
            this.setRoosting(false);
        }
        return super.damage(source, amount);
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        this.dataTracker.set(BAT_FLAGS, nbt.getByte("BatFlags"));
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        nbt.putByte("BatFlags", this.dataTracker.get(BAT_FLAGS));
    }
}