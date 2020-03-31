package com.herobrine.mod.entities;

import com.herobrine.mod.util.entities.EntityRegistry;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.SnowballEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;

import java.util.Random;

public class UnholyWaterEntity extends SnowballEntity{
    protected static final Random random = new Random();

    public UnholyWaterEntity(EntityType<? extends SnowballEntity> entityType, World world) {
        super(entityType, world);
    }

    public UnholyWaterEntity(World worldIn, LivingEntity throwerIn) {
        super(worldIn, throwerIn);
    }

    public UnholyWaterEntity(World worldIn) {
        this((EntityType<? extends UnholyWaterEntity>) EntityRegistry.UNHOLY_WATER_ENTITY, worldIn);
    }

    @Override
    protected void onImpact(@NotNull RayTraceResult result) {
        if (result.getType() == RayTraceResult.Type.ENTITY) {
            Entity entity = ((EntityRayTraceResult)result).getEntity();
            ((LivingEntity) entity).addPotionEffect(new EffectInstance(Effects.WITHER, 300, 1));
           entity.setFire(15);
            int i = 4;
            entity.attackEntityFrom(DamageSource.causeIndirectMagicDamage(this, this.getThrower()), (float)i);
        }
        if (!this.world.isRemote) {
            this.playSound(SoundEvents.BLOCK_GLASS_BREAK, 0.8F,  0.9F / (random.nextFloat() * 0.4F + 0.8F));
            this.world.setEntityState(this, (byte)3);
            this.remove();
        }

    }
}