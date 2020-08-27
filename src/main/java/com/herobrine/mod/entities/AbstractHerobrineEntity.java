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
import java.util.Random;

public class AbstractHerobrineEntity extends EntityMob {
    public AbstractHerobrineEntity(World worldIn) {
        super(worldIn);
    }

    @Override
    public boolean attackEntityFrom(@NotNull DamageSource source, float amount) {
        if (source.getImmediateSource() instanceof UnholyWaterEntity)
            return false;
        if (source.getImmediateSource() instanceof EntityAreaEffectCloud)
            return false;
        if (source.getImmediateSource() instanceof EntityPotion)
            return false;
        if (source == DamageSource.FALL)
            return false;
        if (source == DamageSource.CACTUS)
            return false;
        if (source == DamageSource.DROWN)
            return false;
        if (source == DamageSource.LIGHTNING_BOLT)
            return false;
        if (source == DamageSource.IN_FIRE)
            return false;
        if (source == DamageSource.ON_FIRE)
            return false;
        if (source == DamageSource.ANVIL)
            return false;
        if (source == DamageSource.CRAMMING)
            return false;
        if (source == DamageSource.DRAGON_BREATH)
            return false;
        if (source == DamageSource.FALLING_BLOCK)
            return false;
        if (source == DamageSource.FIREWORKS)
            return false;
        if (source == DamageSource.FLY_INTO_WALL)
            return false;
        if (source == DamageSource.HOT_FLOOR)
            return false;
        if (source == DamageSource.LAVA)
            return false;
        if (source == DamageSource.IN_WALL)
            return false;
        if (source == DamageSource.MAGIC)
            return false;
        if (source == DamageSource.STARVE)
            return false;
        if (source == DamageSource.WITHER)
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
        if (!Variables.SaveData.get(world).Spawn && !Config.HerobrineAlwaysSpawns) {
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
