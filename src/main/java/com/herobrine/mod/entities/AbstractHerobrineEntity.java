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
import net.minecraft.item.ItemStack;
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
                        DamageSource.DRYOUT,
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
        for (DamageSource damageSource : damageSources) add(damageSource.getDamageType());
    }};

    @Override
    public boolean attackEntityFrom(@NotNull DamageSource source, float amount) {
        if (source.getImmediateSource() instanceof AreaEffectCloudEntity)
            return false;
        if (source.getImmediateSource() instanceof PotionEntity)
            return false;
        if (source.getImmediateSource() instanceof UnholyWaterEntity)
            return false;
        if (aef_conditions.contains( source.getDamageType() ))
            return false;
        return super.attackEntityFrom(source, amount);
    }

    @Override
    public void baseTick() {
        if(!world.isRemote) {
            if (!SaveDataUtil.canHerobrineSpawn(world)) {
                this.remove();
            }
        }
        this.clearActivePotions();
        super.baseTick();
    }

    @Override
    public boolean attackEntityAsMob(@NotNull Entity entityIn) {
        boolean flag = super.attackEntityAsMob(entityIn);
        if (flag) {
            float f = this.world.getDifficultyForLocation(this.getPosition()).getAdditionalDifficulty();
            if (this.isBurning() && this.rand.nextFloat() < f * 0.3F) {
                entityIn.setFire(2 * (int)f);
            }
        }
        return flag;
    }

    public static boolean isValidLightLevel(@NotNull IServerWorld worldIn, @NotNull BlockPos pos, @NotNull Random randomIn) {
        if (worldIn.getLightFor(LightType.SKY, pos) > randomIn.nextInt(32)) {
            return false;
        } else {
            int i = worldIn.getWorld().isThundering() ? worldIn.getNeighborAwareLightSubtracted(pos, 10) : worldIn.getLight(pos);
            return i <= randomIn.nextInt(8);
        }
    }

    public static boolean canSpawn(EntityType<? extends AbstractHerobrineEntity> type, @NotNull IServerWorld worldIn, SpawnReason reason, BlockPos pos, Random randomIn) {
        return worldIn.getDifficulty() != Difficulty.PEACEFUL && isValidLightLevel(worldIn, pos, randomIn) && canSpawnOn(type, worldIn, reason, pos, randomIn) && SaveDataUtil.canHerobrineSpawn(worldIn);
    }

    @Override
    public @NotNull ResourceLocation getLootTable() {
        return LootTableInit.HEROBRINE;
    }

    @Override
    protected void dropSpecialItems(@NotNull DamageSource source, int looting, boolean recentlyHitIn) {
        super.dropSpecialItems(source, looting, recentlyHitIn);
        Random rand = new Random();
        if(rand.nextInt(100) <= 20 * (looting + 1) && !(this instanceof FakeHerobrineMageEntity)) {
            this.entityDropItem(new ItemStack(ItemList.cursed_dust, 1));
        }
    }
}
