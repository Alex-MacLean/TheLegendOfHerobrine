package com.herobrinemod.herobrine.entities;

import com.herobrinemod.herobrine.HerobrineMod;
import com.herobrinemod.herobrine.items.ItemList;
import com.herobrinemod.herobrine.savedata.SaveDataHandler;
import net.minecraft.entity.AreaEffectCloudEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.projectile.thrown.PotionEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Difficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;

import java.util.Random;

public abstract class HerobrineEntity extends HostileEntity {
    public HerobrineEntity(EntityType<? extends HostileEntity> entityType, World world) {
        super(entityType, world);
    }

    public static boolean canSpawn(EntityType<? extends HerobrineEntity> type, @NotNull ServerWorldAccess world, SpawnReason spawnReason, BlockPos pos, Random random) {
        return world.getDifficulty() != Difficulty.PEACEFUL && world.isSkyVisible(pos) && isSpawnDark(world, pos, (net.minecraft.util.math.random.Random) random) && canMobSpawn(type, world, spawnReason, pos, (net.minecraft.util.math.random.Random) random) && SaveDataHandler.getHerobrineSaveData().readBoolean("herobrineSummoned");
    }

    @Override
    public boolean damage(@NotNull DamageSource source, float amount) {
        if(source.equals(DamageSource.FALL) || source.equals(DamageSource.CACTUS) || source.equals(DamageSource.LIGHTNING_BOLT) || source.equals(DamageSource.IN_FIRE) || source.equals(DamageSource.ON_FIRE) || source.equals(DamageSource.anvil(getAttacker())) || source.equals(DamageSource.DRAGON_BREATH) || source.equals(DamageSource.fallingBlock(getAttacker())) || source.equals(DamageSource.MAGIC) || source.equals(DamageSource.SWEET_BERRY_BUSH) || source.equals(DamageSource.WITHER) || source.equals(DamageSource.STALAGMITE) || source.equals(DamageSource.FREEZE) || source.getSource() instanceof AreaEffectCloudEntity || source.getSource() instanceof PotionEntity/* || source.getSource() instanceof UnholyWaterEntity*/) {
            return false;
        }
        return super.damage(source, amount);
    }

    @Override
    public void tick() {
        if(world instanceof ServerWorld) {
            if(!SaveDataHandler.getHerobrineSaveData().readBoolean("herobrineSummoned")) {
                this.remove(RemovalReason.DISCARDED);
            }
        }
        this.clearStatusEffects();
        super.tick();
    }

    @Override
    public boolean tryAttack(Entity target) {
        boolean bl = super.tryAttack(target);
        if (bl) {
            float f = this.world.getLocalDifficulty(this.getBlockPos()).getLocalDifficulty();
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
        if (this.getTarget() != null) {
            return this.getNavigation().isIdle() && this.distanceTo(this.getTarget()) > 1.5f;
        }
        return false;
    }
}
