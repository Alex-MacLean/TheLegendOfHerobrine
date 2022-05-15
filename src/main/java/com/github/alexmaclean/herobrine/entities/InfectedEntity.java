package com.github.alexmaclean.herobrine.entities;

import com.github.alexmaclean.herobrine.items.ItemList;
import com.github.alexmaclean.herobrine.savedata.WorldSaveData;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Difficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;

import java.util.Random;

public abstract class InfectedEntity extends HostileEntity {
    public InfectedEntity(EntityType<? extends HostileEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    public void handleStatus(byte status) {
        if (status == 16) {
            if (!this.isSilent()) {
                this.world.playSound(this.getX(), this.getEyeY(), this.getZ(), SoundEvents.ENTITY_ZOMBIE_VILLAGER_CURE, this.getSoundCategory(), 1.0f + this.random.nextFloat(), this.random.nextFloat() * 0.7f + 0.3f, false);
            }
        } else {
            super.handleStatus(status);
        }
    }

    public static boolean canSpawn(EntityType<? extends InfectedEntity> type, @NotNull ServerWorldAccess world, SpawnReason spawnReason, BlockPos pos, Random random) {
        return world.getDifficulty() != Difficulty.PEACEFUL && world.isSkyVisible(pos) && isSpawnDark(world, pos, random) && canMobSpawn(type, world, spawnReason, pos, random) && WorldSaveData.readBoolean(world.toServerWorld(), "test.json", "herobrineSummoned");
    }

    @Override
    public boolean damage(DamageSource source, float amount) {
        /*if(source.getSource() instanceof UnholyWaterEntity) {
            return false;
        }*/
        return super.damage(source, amount);
    }

    @Override
    public void tick() {
        if(!world.isClient() && !WorldSaveData.readBoolean(world, "herobrine.json", "herobrineSummoned")) {
            this.remove(RemovalReason.DISCARDED);
        }
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
    protected void dropEquipment(DamageSource source, int lootingMultiplier, boolean allowDrops) {
        super.dropEquipment(source, lootingMultiplier, allowDrops);
        if (random.nextInt(100) <= 20 * (lootingMultiplier + 1)) {
            this.dropItem(ItemList.CURSED_DUST);
        }
    }
}