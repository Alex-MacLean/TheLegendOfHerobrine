package com.herobrinemod.herobrine.entities;

import com.herobrinemod.herobrine.HerobrineMod;
import com.herobrinemod.herobrine.items.ItemList;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.pathing.MobNavigation;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageTypes;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.projectile.thrown.PotionEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.Difficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeKeys;
import net.minecraft.world.dimension.DimensionTypes;
import org.jetbrains.annotations.NotNull;

public abstract class HerobrineEntity extends HostileEntity {
    public HerobrineEntity(EntityType<? extends HostileEntity> entityType, World world) {
        super(entityType, world);
        ((MobNavigation)this.getNavigation()).setCanPathThroughDoors(true);
    }

    @Override
    protected float getActiveEyeHeight(EntityPose pose, EntityDimensions dimensions) {
        return 1.62f;
    }

    public static boolean canSpawn(EntityType<? extends HerobrineEntity> type, @NotNull ServerWorldAccess world, SpawnReason spawnReason, BlockPos pos, Random random) {
        if(world.toServerWorld().getDimensionKey() != DimensionTypes.OVERWORLD && random.nextInt(15) > 1 || world.toServerWorld().getDimensionKey() == DimensionTypes.THE_END && pos.getX() < 1000 && pos.getZ() < 1000 || world.getBiome(pos).matchesId(BiomeKeys.MUSHROOM_FIELDS.getValue()) && random.nextInt(100) > 1  || world.getBiome(pos).matchesId(BiomeKeys.DEEP_DARK.getValue()) && random.nextInt(100) > 1) {
            return false;
        }
        return world.getDifficulty() != Difficulty.PEACEFUL && isSpawnDark(world, pos, random) && canMobSpawn(type, world, spawnReason, pos, random) && HerobrineSpawnHelper.canHerobrineSpawn();
    }

    public static boolean canSpawnPeacefulMode(EntityType<? extends HerobrineEntity> type, @NotNull ServerWorldAccess world, SpawnReason spawnReason, BlockPos pos, net.minecraft.util.math.random.Random random) {
        if(world.toServerWorld().getDimensionKey() != DimensionTypes.OVERWORLD && random.nextInt(15) > 1 || world.toServerWorld().getDimensionKey() == DimensionTypes.THE_END && pos.getX() < 1000 && pos.getZ() < 1000 || world.getBiome(pos).matchesId(BiomeKeys.MUSHROOM_FIELDS.getValue()) && random.nextInt(100) > 1 || world.getBiome(pos).matchesId(BiomeKeys.DEEP_DARK.getValue()) && random.nextInt(100) > 1) {
            return false;
        }
        return isSpawnDark(world, pos, random) && canMobSpawn(type, world, spawnReason, pos, random) && HerobrineSpawnHelper.canHerobrineSpawn();
    }

    @Override
    public boolean damage(@NotNull DamageSource source, float amount) {
        if(source.isOf(DamageTypes.FALL) || source.isOf(DamageTypes.CACTUS) || source.isOf(DamageTypes.LIGHTNING_BOLT) || source.isOf(DamageTypes.IN_FIRE) || source.isOf(DamageTypes.ON_FIRE) || source.isOf(DamageTypes.FALLING_ANVIL) || source.isOf(DamageTypes.FALLING_STALACTITE) || source.isOf(DamageTypes.STALAGMITE) || source.isOf(DamageTypes.DRAGON_BREATH) || source.isOf(DamageTypes.FALLING_BLOCK) || source.isOf(DamageTypes.MAGIC) || source.isOf(DamageTypes.SWEET_BERRY_BUSH) || source.isOf(DamageTypes.WITHER) || source.isOf(DamageTypes.STALAGMITE) || source.isOf(DamageTypes.FREEZE) || source.getSource() instanceof AreaEffectCloudEntity || source.getSource() instanceof PotionEntity) {
            return false;
        }
        return super.damage(source, amount);
    }

    @Override
    public void tick() {
        this.clearStatusEffects();
        if(getWorld() instanceof ServerWorld) {
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

    @Override
    public Identifier getLootTableId() {
        return new Identifier(HerobrineMod.MODID, "entities/herobrine");
    }

    @Override
    protected void dropEquipment(DamageSource source, int lootingMultiplier, boolean allowDrops) {
        super.dropEquipment(source, lootingMultiplier, allowDrops);
        if (random.nextInt(100) <= 20 * (lootingMultiplier + 1) && !(this instanceof FakeHerobrineMageEntity)) {
            this.dropItem(ItemList.CURSED_DUST);
        }
    }

    public boolean unableToAttackTarget() {
        return this.getTarget() != null && this.getNavigation().isIdle() && this.distanceTo(this.getTarget()) > 1.5f;
    }
}
