package com.herobrinemod.herobrine.entities;

import com.herobrinemod.herobrine.items.ItemList;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.control.JumpControl;
import net.minecraft.entity.ai.control.MoveControl;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.ai.pathing.Path;
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
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.GolemEntity;
import net.minecraft.entity.passive.RabbitEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.tag.BiomeTags;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.biome.Biome;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class InfectedRabbitEntity extends InfectedEntity implements VariantHolder<RabbitEntity.RabbitType> {
    private static final TrackedData<Integer> RABBIT_TYPE = DataTracker.registerData(InfectedRabbitEntity.class, TrackedDataHandlerRegistry.INTEGER);
    private int jumpTicks;
    private int jumpDuration;
    private boolean lastOnGround;
    private int ticksUntilJump;
    public InfectedRabbitEntity(EntityType<? extends HostileEntity> entityType, World world) {
        super(entityType, world);
        this.experiencePoints = 3;
        this.jumpControl = new InfectedRabbitEntity.RabbitJumpControl(this);
        this.moveControl = new InfectedRabbitEntity.RabbitMoveControl(this);
        this.setSpeed(0.0);
        this.setConversionEntity(EntityType.RABBIT);
    }

    @Override
    protected void initGoals() {
        this.goalSelector.add(0, new SwimGoal(this));
        this.goalSelector.add(1, new InfectedRabbitEntity.RabbitAttackGoal(this));
        this.goalSelector.add(2, new ActiveTargetGoal<>(this, IllagerEntity.class, false));
        this.goalSelector.add(3, new ActiveTargetGoal<>(this, PlayerEntity.class, false));
        //this.goalSelector.add(4, new ActiveTargetGoal<>(this, SurvivorEntity.class, false));
        this.goalSelector.add(5, new ActiveTargetGoal<>(this, GolemEntity.class, false));
        this.goalSelector.add(6, new WanderAroundFarGoal(this, 1.0));
        this.goalSelector.add(7, new LookAtEntityGoal(this, IllagerEntity.class, 8.0f));
        this.goalSelector.add(8, new LookAtEntityGoal(this, PlayerEntity.class, 8.0f));
        //this.goalSelector.add(9, new LookAtEntityGoal(this, SurvivorEntity.class, 8.0f));
        this.goalSelector.add(10, new LookAtEntityGoal(this, GolemEntity.class, 8.0f));
        this.goalSelector.add(11, new LookAroundGoal(this));
    }

    public static DefaultAttributeContainer.Builder registerAttributes() {
        return createHostileAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 3.0)
                .add(EntityAttributes.GENERIC_ARMOR, 8.0)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 2.0)
                .add(EntityAttributes.GENERIC_FOLLOW_RANGE, 16.0)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.3);
    }

    public void convert() {
        this.world.sendEntityStatus(this, (byte) 16);
        this.dropItem(ItemList.CURSED_DUST);
        MobEntity entity = this.convertTo(this.getConversionEntity(), false);
        assert entity != null;
        entity.addStatusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, 300, 1));
        entity.addStatusEffect(new StatusEffectInstance(StatusEffects.HEALTH_BOOST, 300, 1));
        entity.initialize((ServerWorldAccess) world, world.getLocalDifficulty(this.getBlockPos()), SpawnReason.CONVERSION, null, null);
        ((RabbitEntity) entity).setVariant(this.getVariant());
    }

    @Override
    protected float getJumpVelocity() {
        if (this.horizontalCollision || this.moveControl.isMoving() && this.moveControl.getTargetY() > this.getY() + 0.5) {
            return 0.5f;
        }
        Path path = this.navigation.getCurrentPath();
        if (path != null && !path.isFinished()) {
            Vec3d vec3d = path.getNodePosition(this);
            if (vec3d.y > this.getY() + 0.5) {
                return 0.5f;
            }
        }
        if (this.moveControl.getSpeed() <= 0.6) {
            return 0.2f;
        }
        return 0.3f;
    }

    @Override
    protected void jump() {
        super.jump();
        double d = this.moveControl.getSpeed();
        if (d > 0.0 && this.getVelocity().horizontalLengthSquared() < 0.01) {
            this.updateVelocity(0.1f, new Vec3d(0.0, 0.0, 1.0));
        }
        if (!this.world.isClient) {
            this.world.sendEntityStatus(this, EntityStatuses.ADD_SPRINTING_PARTICLES_OR_RESET_SPAWNER_MINECART_SPAWN_DELAY);
        }
    }

    public float getJumpProgress(float delta) {
        if (this.jumpDuration == 0) {
            return 0.0f;
        }
        return ((float)this.jumpTicks + delta) / (float)this.jumpDuration;
    }

    public void setSpeed(double speed) {
        this.getNavigation().setSpeed(speed);
        this.moveControl.moveTo(this.moveControl.getTargetX(), this.moveControl.getTargetY(), this.moveControl.getTargetZ(), speed);
    }

    @Override
    public void setJumping(boolean jumping) {
        super.setJumping(jumping);
        if (jumping) {
            this.playSound(this.getJumpSound(), this.getSoundVolume(), ((this.random.nextFloat() - this.random.nextFloat()) * 0.2f + 1.0f) * 0.8f);
        }
    }

    public void startJump() {
        this.setJumping(true);
        this.jumpDuration = 10;
        this.jumpTicks = 0;
    }

    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(RABBIT_TYPE, RabbitEntity.RabbitType.BROWN.getId());
    }

    @Override
    public void mobTick() {
        if (this.ticksUntilJump > 0) {
            --this.ticksUntilJump;
        }

        if (this.onGround) {
            InfectedRabbitEntity.RabbitJumpControl rabbitJumpControl;
            LivingEntity livingEntity;
            if (!this.lastOnGround) {
                this.setJumping(false);
                this.scheduleJump();
            }

            if (this.getVariant() == RabbitEntity.RabbitType.EVIL && this.ticksUntilJump == 0 && (livingEntity = this.getTarget()) != null && this.squaredDistanceTo(livingEntity) < 16.0) {
                this.lookTowards(livingEntity.getX(), livingEntity.getZ());
                this.moveControl.moveTo(livingEntity.getX(), livingEntity.getY(), livingEntity.getZ(), this.moveControl.getSpeed());
                this.startJump();
                this.lastOnGround = true;
            }

            if ((rabbitJumpControl = (RabbitJumpControl) this.jumpControl).isNotActive()) {
                if (this.moveControl.isMoving() && this.ticksUntilJump == 0) {
                    Path path = this.navigation.getCurrentPath();
                    Vec3d vec3d = new Vec3d(this.moveControl.getTargetX(), this.moveControl.getTargetY(), this.moveControl.getTargetZ());
                    if (path != null && !path.isFinished()) {
                        vec3d = path.getNodePosition(this);
                    }
                    this.lookTowards(vec3d.x, vec3d.z);
                    this.startJump();
                }
            } else if (!rabbitJumpControl.canJump()) {
                this.enableJump();
            }
        }

        this.lastOnGround = this.onGround;
    }

    @Override
    public boolean shouldSpawnSprintingParticles() {
        return false;
    }

    private void lookTowards(double x, double z) {
        this.setYaw((float)(MathHelper.atan2(z - this.getZ(), x - this.getX()) * 57.2957763671875) - 90.0f);
    }

    private void enableJump() {
        ((InfectedRabbitEntity.RabbitJumpControl)this.jumpControl).setCanJump(true);
    }

    private void disableJump() {
        ((InfectedRabbitEntity.RabbitJumpControl)this.jumpControl).setCanJump(false);
    }

    private void doScheduleJump() {
        this.ticksUntilJump = this.moveControl.getSpeed() < 2.2 ? 10 : 1;
    }

    private void scheduleJump() {
        this.doScheduleJump();
        this.disableJump();
    }

    @Override
    public void tickMovement() {
        super.tickMovement();
        if (this.jumpTicks != this.jumpDuration) {
            ++this.jumpTicks;
        } else if (this.jumpDuration != 0) {
            this.jumpTicks = 0;
            this.jumpDuration = 0;
            this.setJumping(false);
        }
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        nbt.putInt("RabbitType", this.getVariant().getId());
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        this.setVariant(RabbitEntity.RabbitType.byId(nbt.getInt("RabbitType")));
    }

    protected SoundEvent getJumpSound() {
        return SoundEvents.ENTITY_RABBIT_JUMP;
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.ENTITY_RABBIT_AMBIENT;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundEvents.ENTITY_RABBIT_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_RABBIT_DEATH;
    }

    @Override
    public boolean tryAttack(Entity target) {
        if (this.getVariant() == RabbitEntity.RabbitType.EVIL) {
            this.playSound(SoundEvents.ENTITY_RABBIT_ATTACK, 1.0f, (this.random.nextFloat() - this.random.nextFloat()) * 0.2f + 1.0f);
            return target.damage(this.getDamageSources().mobAttack(this), 8.0f);
        }
        return target.damage(this.getDamageSources().mobAttack(this), 3.0f);
    }

    @Override
    public RabbitEntity.RabbitType getVariant() {
        return RabbitEntity.RabbitType.byId(this.dataTracker.get(RABBIT_TYPE));
    }

    @Override
    public void setVariant(RabbitEntity.@NotNull RabbitType rabbitType) {
        this.dataTracker.set(RABBIT_TYPE, rabbitType.getId());
        if (rabbitType == RabbitEntity.RabbitType.EVIL && !this.hasCustomName()) {
            this.setCustomName(Text.translatable(Util.createTranslationKey("entity", new Identifier("killer_bunny"))));
        }
    }

    @Override
    @Nullable
    public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, @Nullable EntityData entityData, @Nullable NbtCompound entityNbt) {
        RabbitEntity.RabbitType rabbitType = InfectedRabbitEntity.getTypeFromPos(world, this.getBlockPos());
        if (entityData instanceof RabbitEntity.RabbitData) {
            rabbitType = ((RabbitEntity.RabbitData)entityData).type;
        } else {
            entityData = new RabbitEntity.RabbitData(rabbitType);
        }
        this.setVariant(rabbitType);
        return super.initialize(world, difficulty, spawnReason, entityData, entityNbt);
    }

    private static RabbitEntity.RabbitType getTypeFromPos(@NotNull WorldAccess world, BlockPos pos) {
        RegistryEntry<Biome> registryEntry = world.getBiome(pos);
        int i = world.getRandom().nextInt(100);
        if (registryEntry.isIn(BiomeTags.SPAWNS_WHITE_RABBITS)) {
            return i < 80 ? RabbitEntity.RabbitType.WHITE : RabbitEntity.RabbitType.WHITE_SPLOTCHED;
        }
        if (registryEntry.isIn(BiomeTags.SPAWNS_GOLD_RABBITS)) {
            return RabbitEntity.RabbitType.GOLD;
        }
        return i < 50 ? RabbitEntity.RabbitType.BROWN : (i < 90 ? RabbitEntity.RabbitType.SALT : RabbitEntity.RabbitType.BLACK);
    }

    @Override
    public void handleStatus(byte status) {
        if (status == EntityStatuses.ADD_SPRINTING_PARTICLES_OR_RESET_SPAWNER_MINECART_SPAWN_DELAY) {
            this.spawnSprintingParticles();
            this.jumpDuration = 10;
            this.jumpTicks = 0;
        } else {
            super.handleStatus(status);
        }
    }

    public static class RabbitJumpControl extends JumpControl {
        private final InfectedRabbitEntity entity;
        private boolean canJump;

        public RabbitJumpControl(InfectedRabbitEntity entity) {
            super(entity);
            this.entity = entity;
        }

        public boolean isNotActive() {
            return !this.active;
        }

        public boolean canJump() {
            return this.canJump;
        }

        public void setCanJump(boolean canJump) {
            this.canJump = canJump;
        }

        @Override
        public void tick() {
            if (this.active) {
                this.entity.startJump();
                this.active = false;
            }
        }
    }

    static class RabbitMoveControl extends MoveControl {
        private final InfectedRabbitEntity entity;
        private double speed;

        public RabbitMoveControl(InfectedRabbitEntity owner) {
            super(owner);
            this.entity = owner;
        }

        @Override
        public void tick() {
            if (this.entity.onGround && !this.entity.jumping && ((RabbitJumpControl) this.entity.jumpControl).isNotActive()) {
                this.entity.setSpeed(0.0);
            } else if (this.isMoving()) {
                this.entity.setSpeed(this.speed);
            }
            super.tick();
        }

        @Override
        public void moveTo(double x, double y, double z, double speed) {
            if (this.entity.isTouchingWater()) {
                speed = 1.5;
            }
            super.moveTo(x, y, z, speed);
            if (speed > 0.0) {
                this.speed = speed;
            }
        }
    }

    static class RabbitAttackGoal
            extends MeleeAttackGoal {
        public RabbitAttackGoal(InfectedRabbitEntity entity) {
            super(entity, 1.4, true);
        }

        @Override
        protected double getSquaredMaxAttackDistance(@NotNull LivingEntity entity) {
            return 4.0f + entity.getWidth();
        }
    }
}
