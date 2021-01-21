package com.herobrine.mod.entities;

import com.herobrine.mod.config.Config;
import com.herobrine.mod.util.items.ItemList;
import com.herobrine.mod.util.loot_tables.LootTableInit;
import com.herobrine.mod.util.savedata.Variables;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAreaEffectCloud;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.projectile.EntityPotion;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.HashSet;
import java.util.Random;

public class AbstractHerobrineEntity extends EntityMob {
    public AbstractHerobrineEntity(World worldIn) {
        super(worldIn);
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
                        DamageSource.FALLING_BLOCK,
                        DamageSource.FLY_INTO_WALL,
                        DamageSource.HOT_FLOOR,
                        DamageSource.LAVA,
                        DamageSource.IN_WALL,
                        DamageSource.MAGIC,
                        DamageSource.STARVE,
                        DamageSource.WITHER,
                        DamageSource.FIREWORKS
                };
        //This piece right here adds all 'possibilities' to the hash set.
        for (DamageSource damageSource : damageSources) add(damageSource.getDamageType());
    }};

    @Override
    public boolean attackEntityFrom(@NotNull DamageSource source, float amount) {
        if (source.getImmediateSource() instanceof EntityAreaEffectCloud)
            return false;
        if (source.getImmediateSource() instanceof EntityPotion)
            return false;
        if (source.getImmediateSource() instanceof UnholyWaterEntity)
            return false;
        if (aef_conditions.contains( source.getDamageType() ))
            return false;
        return super.attackEntityFrom(source, amount);
    }

    @Override
    public boolean attackEntityAsMob(@NotNull Entity entityIn) {
        boolean flag = super.attackEntityAsMob(entityIn);
        if (flag)
        {
            float f = this.world.getDifficultyForLocation(new BlockPos(this)).getAdditionalDifficulty();

            if (this.isBurning() && this.rand.nextFloat() < f * 0.3F)
            {
                entityIn.setFire(2 * (int)f);
            }
        }
        return flag;
    }

    @Override
    public boolean getCanSpawnHere() {
        return super.getCanSpawnHere() && Variables.SaveData.get(world).Spawn || super.getCanSpawnHere() && Config.HerobrineAlwaysSpawns;
    }

    @Override
    public void onUpdate() {
        super.onUpdate();
        Variables.SaveData.get(world).syncData(world);
        if (!Variables.SaveData.get(world).Spawn && !Config.HerobrineAlwaysSpawns && !world.isRemote) {
            this.world.removeEntity(this);
        }
        this.clearActivePotions();
    }

    @Nullable
    @Override
    protected ResourceLocation getLootTable() {
        return LootTableInit.HEROBRINE;
    }

    @Override
    public void onDeath(@NotNull DamageSource source) {
        super.onDeath(source);
        EntityLivingBase entity = (EntityLivingBase) source.getTrueSource();
        Random rand = new Random();
        if(entity != null) {
            int lootingModifier = EnchantmentHelper.getLootingModifier(entity);
            if (rand.nextInt(100) <= 20 * (lootingModifier + 1) && !(this instanceof FakeHerobrineMageEntity)) {
                this.dropItem(ItemList.cursed_dust, 1);
            }
        }
    }
}
