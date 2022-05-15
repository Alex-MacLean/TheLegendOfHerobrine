package com.github.alexmaclean.herobrine.entities;

import com.github.alexmaclean.herobrine.savedata.WorldSaveData;
import net.minecraft.entity.EntityData;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.goal.FleeEntityGoal;
import net.minecraft.entity.ai.goal.LookAtEntityGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Random;

public class HerobrineStalkerEntity extends HerobrineEntity {
    private int lifeTimer = 6000;
    private int runAtTargetDelay = 500;
    private int runAtTargetTime = 250;
    private boolean isRunningAtTarget = false;

    public HerobrineStalkerEntity(EntityType<? extends HostileEntity> entityType, World world) {
        super(entityType, world);
        this.lifeTimer = 6000;
        this.experiencePoints = 5;
    }

    @Override
    protected void initGoals() {
        this.goalSelector.add(0, new SwimGoal(this));
        this.goalSelector.add(1, new LookAtEntityGoal(this, PlayerEntity.class, 1024.0f));
        this.goalSelector.add(2, new FleeEntityGoal<>(this, PlayerEntity.class, 32.0f, 0.7, 1.0));
        //this.goalSelector.add(3, new LookAtEntityGoal(this, SurvivorEntity.class, 1024.0f));
        //this.goalSelector.add(4, new FleeEntityGoal<>(this, SurvivorEntity.class, 32.0f, 0.7, 1.0));
    }

    public static DefaultAttributeContainer.Builder registerAttributes() {
        return HerobrineEntity.createHostileAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 20.0)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 0.0)
                .add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 1.0)
                .add(EntityAttributes.GENERIC_ARMOR, 0.0)
                .add(EntityAttributes.GENERIC_ARMOR_TOUGHNESS, 0.0)
                .add(EntityAttributes.GENERIC_FOLLOW_RANGE, 64.0)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.6);
    }

    @Override
    protected boolean isDisallowedInPeaceful() {
        return false;
    }

    public static boolean canSpawn(EntityType<? extends HerobrineEntity> type, @NotNull ServerWorldAccess world, SpawnReason spawnReason, BlockPos pos, Random random) {
        return world.isSkyVisible(pos) && isSpawnDark(world, pos, random) && canMobSpawn(type, world, spawnReason, pos, random) && WorldSaveData.readBoolean(world.toServerWorld(), "herobrine.json", "herobrineSummoned");
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        nbt.putInt("LifeTimer", this.lifeTimer);
        nbt.putInt("RunAtTargetDelay", this.runAtTargetDelay);
        nbt.putInt("RunAtTargetTime", this.runAtTargetTime);
        nbt.putBoolean("RunningAtTarget", this.isRunningAtTarget);
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        this.lifeTimer = nbt.getInt("LifeTimer");
        this.runAtTargetDelay = nbt.getInt("RunAtTargetDelay");
        this.runAtTargetTime = nbt.getInt("RunAtTargetTime");
        this.isRunningAtTarget = nbt.getBoolean("RunningAtTarget");
    }

    @Override
    public void mobTick() {
        if(this.lifeTimer < 1) {
            this.world.sendEntityStatus(this, (byte) 4);
            this.remove(RemovalReason.DISCARDED);
        }
        this.lifeTimer --;
        super.mobTick();
    }

    @Override
    public void handleStatus(byte status) {
        super.handleStatus(status);
        switch(status) {
            case 4:
                if(this.world.isClient) {
                    if (!this.isSilent()) {
                        this.world.playSound(this.getX(), this.getEyeY(), this.getZ(), SoundEvents.ITEM_FIRECHARGE_USE, this.getSoundCategory(), 1.0f, (random.nextFloat() - random.nextFloat()) * 0.2f + 1.0f, false);
                    }

                    for (int i = 0; i < 20; i ++) {
                        this.world.addParticle(ParticleTypes.POOF, this.getParticleX(1.0), this.getRandomBodyY(), this.getParticleZ(1.0), random.nextGaussian() * 0.02, random.nextGaussian() * 0.02, random.nextGaussian() * 0.02);
                    }
                }
                break;
            case 5:
                if(this.world.isClient) {
                    if (!this.isSilent()) {
                        this.world.playSound(this.getX(), this.getEyeY(), this.getZ(), SoundEvents.AMBIENT_CAVE, this.getSoundCategory(), 1.0f, (random.nextFloat() - random.nextFloat()) * 0.2f + 1.0f, false);
                    }

                    for (int i = 0; i < 20; i ++) {
                        this.world.addParticle(ParticleTypes.POOF, this.getParticleX(1.0), this.getRandomBodyY(), this.getParticleZ(1.0), random.nextGaussian() * 0.02, random.nextGaussian() * 0.02, random.nextGaussian() * 0.02);
                    }
                }
                break;
        }
    }

    @Override
    public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, @Nullable EntityData entityData, @Nullable NbtCompound entityNbt) {
        this.setPersistent();
        return super.initialize(world, difficulty, spawnReason, entityData, entityNbt);
    }
}
