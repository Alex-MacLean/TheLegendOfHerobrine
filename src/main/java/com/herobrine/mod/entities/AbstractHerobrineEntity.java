package com.herobrine.mod.entities;

import com.herobrine.mod.util.items.ItemList;
import com.herobrine.mod.util.loot_tables.LootTableInit;
import com.herobrine.mod.util.savedata.SaveDataUtil;
import net.minecraft.entity.AreaEffectCloudEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.projectile.PotionEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Difficulty;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.LightType;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.Random;

public class AbstractHerobrineEntity extends MonsterEntity {
    protected AbstractHerobrineEntity(EntityType<? extends AbstractHerobrineEntity> type, World worldIn) {
        super(type, worldIn);
    }

    // (from Guliver Jham)
    // This is made to correct 20 instances of if statements to make it the code easier.
    private static final HashSet<String> aef_conditions = new HashSet<String>()
    {{
        DamageSource[] damageSources = new DamageSource[]
                {
                        // All of the damage sources below will make the function
                        // "attackEntityFrom" return false.
                        DamageSource.FALL,
                        DamageSource.CACTUS,
                        DamageSource.DROWN,
                        DamageSource.LIGHTNING_BOLT,
                        DamageSource.IN_FIRE,
                        DamageSource.ON_FIRE,
                        DamageSource.ANVIL,
                        DamageSource.CRAMMING,
                        DamageSource.DRAGON_BREATH,
                        DamageSource.DRY_OUT,
                        DamageSource.FALLING_BLOCK,
                        DamageSource.FLY_INTO_WALL,
                        DamageSource.HOT_FLOOR,
                        DamageSource.LAVA,
                        DamageSource.IN_WALL,
                        DamageSource.MAGIC,
                        DamageSource.STARVE,
                        DamageSource.SWEET_BERRY_BUSH,
                        DamageSource.WITHER,
                };
        //This piece right here adds all 'possibilities' to the hash set.
        for (DamageSource damageSource : damageSources) add(damageSource.getMsgId());
    }};

    public static boolean isValidLightLevel(@NotNull IServerWorld worldIn, @NotNull BlockPos pos, @NotNull Random randomIn) {
        if (worldIn.getBrightness(LightType.SKY, pos) > randomIn.nextInt(32)) {
            return false;
        } else {
            int i = worldIn.getLevel().isThundering() ? worldIn.getMaxLocalRawBrightness(pos, 10) : worldIn.getLightEmission(pos);
            return i <= randomIn.nextInt(8);
        }
    }

    public static boolean canSpawn(EntityType<? extends AbstractHerobrineEntity> type, @NotNull IServerWorld worldIn, SpawnReason reason, BlockPos pos, Random randomIn) {
        return worldIn.getDifficulty() != Difficulty.PEACEFUL && isValidLightLevel(worldIn, pos, randomIn) && checkMobSpawnRules(type, worldIn, reason, pos, randomIn) && SaveDataUtil.canHerobrineSpawn(worldIn);
    }

    public static boolean canSpawnPeacefulMode(EntityType<? extends AbstractHerobrineEntity> type, @NotNull IServerWorld worldIn, SpawnReason reason, BlockPos pos, Random randomIn) {
        return isValidLightLevel(worldIn, pos, randomIn) && checkMobSpawnRules(type, worldIn, reason, pos, randomIn) && SaveDataUtil.canHerobrineSpawn(worldIn);
    }

    @Override
    public boolean hurt(@NotNull DamageSource source, float amount) {
        if (source.getDirectEntity() instanceof AreaEffectCloudEntity)
            return false;
        if (source.getDirectEntity() instanceof PotionEntity)
            return false;
        if (source.getDirectEntity() instanceof UnholyWaterEntity)
            return false;
        if (aef_conditions.contains(source.getMsgId()))
            return false;
        return super.hurt(source, amount);
    }

    @Override
    public void baseTick() {
        if (!level.isClientSide) {
            if (!SaveDataUtil.canHerobrineSpawn(level)) {
                this.remove();
            }
        }
        this.removeAllEffects();
        super.baseTick();
    }

    @Override
    public boolean doHurtTarget(@NotNull Entity entityIn) {
        boolean flag = super.doHurtTarget(entityIn);
        if (flag) {
            float f = this.level.getCurrentDifficultyAt(this.getOnPos()).getEffectiveDifficulty();
            if (this.isOnFire() && this.random.nextFloat() < f * 0.3F) {
                entityIn.setSecondsOnFire(2 * (int) f);
            }
        }
        return flag;
    }

    @Override
    public @NotNull ResourceLocation getDefaultLootTable() {
        return LootTableInit.HEROBRINE;
    }

    @Override
    protected void dropCustomDeathLoot(@NotNull DamageSource source, int looting, boolean recentlyHitIn) {
        super.dropCustomDeathLoot(source, looting, recentlyHitIn);
        Random rand = new Random();
        if (rand.nextInt(100) <= 20 * (looting + 1) && !(this instanceof FakeHerobrineMageEntity)) {
            this.spawnAtLocation(ItemList.cursed_dust);
        }
    }
}
