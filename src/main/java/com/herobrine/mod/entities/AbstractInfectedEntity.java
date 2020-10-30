package com.herobrine.mod.entities;

import com.herobrine.mod.config.Config;
import com.herobrine.mod.util.items.ItemList;
import com.herobrine.mod.util.savedata.Variables;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.jetbrains.annotations.NotNull;

import java.util.Random;

public class AbstractInfectedEntity extends EntityMob {
    public AbstractInfectedEntity(World worldIn) {
        super(worldIn);
    }

    @SideOnly(Side.CLIENT)
    public void handleStatusUpdate(byte id) {
        if (id == 16) {
            if (!this.isSilent()) {
                this.world.playSound(this.posX, this.posY, this.posZ, SoundEvents.ENTITY_ZOMBIE_VILLAGER_CONVERTED, SoundCategory.NEUTRAL, 2.0F, (rand.nextFloat() - rand.nextFloat()) * 0.2F + 1.0F, false);
            }
        } else {
            super.handleStatusUpdate(id);
        }
    }

    @Override
    public boolean attackEntityFrom(@NotNull DamageSource source, float amount) {
        if (source.getImmediateSource() instanceof UnholyWaterEntity)
            return false;
        return super.attackEntityFrom(source, amount);
    }

    @Override
    public boolean attackEntityAsMob(@NotNull Entity entityIn) {
        boolean flag = super.attackEntityAsMob(entityIn);
        if (flag) {
            float f = this.world.getDifficultyForLocation(new BlockPos(this)).getAdditionalDifficulty();
            if (this.isBurning() && this.rand.nextFloat() < f * 0.3F) {
                entityIn.setFire(2 * (int)f);
            }
        }
        return flag;
    }

    @Override
    public void onUpdate() {
        super.onUpdate();
        Variables.SaveData.get(world).syncData(world);
        if (!Variables.SaveData.get(world).Spawn && !Config.HerobrineAlwaysSpawns && !world.isRemote) {
            this.world.removeEntity(this);
        }
    }

    @Override
    public void onDeath(@NotNull DamageSource source) {
        super.onDeath(source);
        EntityLivingBase entity = (EntityLivingBase) source.getTrueSource();
        Random rand = new Random();
        if(entity != null) {
            int lootingModifier = EnchantmentHelper.getLootingModifier(entity);
            if (rand.nextInt(100) <= 20 * (lootingModifier + 1)) {
                this.dropItem(ItemList.cursed_dust, 1);
            }
        }
    }
}
