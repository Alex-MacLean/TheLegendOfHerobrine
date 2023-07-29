package com.herobrinemod.herobrine.entities;

import com.herobrinemod.herobrine.items.ItemList;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Difficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;

public abstract class InfectedEntity extends HostileEntity {
    private EntityType conversionEntity;
    public InfectedEntity(EntityType<? extends HostileEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    public void handleStatus(byte status) {
        super.handleStatus(status);
        if (status == 16) {
            if (!this.isSilent()) {
                this.getWorld().playSound(this.getX(), this.getEyeY(), this.getZ(), SoundEvents.ENTITY_ZOMBIE_VILLAGER_CONVERTED, this.getSoundCategory(), 1.0f + this.random.nextFloat(), this.random.nextFloat() * 0.7f + 0.3f, false);
            }
        }

    }

    public static boolean canSpawn(EntityType<? extends InfectedEntity> type, @NotNull ServerWorldAccess world, SpawnReason spawnReason, BlockPos pos, net.minecraft.util.math.random.Random random) {
        return world.getDifficulty() != Difficulty.PEACEFUL && world.getBlockState(pos.down()).isIn(BlockTags.ANIMALS_SPAWNABLE_ON) && isSpawnDark(world, pos, random) && canMobSpawn(type, world, spawnReason, pos, random) && HerobrineSpawnHelper.canHerobrineSpawn() && HerobrineSpawnHelper.getStage() > 1;
    }

    public void convert() {
        this.getWorld().sendEntityStatus(this, (byte) 16);
        this.dropItem(ItemList.CURSED_DUST);
        MobEntity entity = this.convertTo(conversionEntity, false);
        assert entity != null;
        entity.addStatusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, 300, 1));
        entity.addStatusEffect(new StatusEffectInstance(StatusEffects.HEALTH_BOOST, 300, 1));
        entity.initialize((ServerWorldAccess) getWorld(), getWorld().getLocalDifficulty(this.getBlockPos()), SpawnReason.CONVERSION, null, null);
    }

    @Override
    public void tick() {
        if(this.getWorld() instanceof ServerWorld) {
            if(!HerobrineSpawnHelper.canHerobrineSpawn()) {
                this.remove(RemovalReason.DISCARDED);
            }
        }
        super.tick();
    }

    @Override
    public boolean tryAttack(Entity target) {
        boolean bl = super.tryAttack(target);
        if (bl) {
            float f = this.getWorld().getLocalDifficulty(this.getBlockPos()).getLocalDifficulty();
            if (this.isOnFire() && this.random.nextFloat() < f * 0.3f) {
                target.setOnFireFor(2 * (int)f);
            }
        }
        return bl;
    }
    
    public EntityType getConversionEntity() {
        return this.conversionEntity;
    }
    
    public void setConversionEntity(EntityType entity) {
        this.conversionEntity = entity;
    }

    @Override
    public Identifier getLootTableId() {
        return conversionEntity.getLootTableId();
    }

    @Override
    protected void dropEquipment(DamageSource source, int lootingMultiplier, boolean allowDrops) {
        super.dropEquipment(source, lootingMultiplier, allowDrops);
        if (random.nextInt(100) <= 20 * (lootingMultiplier + 1) && allowDrops) {
            this.dropItem(ItemList.CURSED_DUST);
        }
    }
}