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

public class HolyWaterEntity extends SnowballEntity{
    protected static final Random random = new Random();

    public HolyWaterEntity(EntityType<? extends SnowballEntity> entityType, World world) {
        super(entityType, world);
    }

    public HolyWaterEntity(World worldIn, LivingEntity throwerIn) {
        super(worldIn, throwerIn);
    }

    public HolyWaterEntity(World worldIn) {
        this((EntityType<? extends HolyWaterEntity>) EntityRegistry.HOLY_WATER_ENTITY, worldIn);
    }

    @Override
    protected void onImpact(@NotNull RayTraceResult result) {
        if (result.getType() == RayTraceResult.Type.ENTITY) {
            Entity entity = ((EntityRayTraceResult)result).getEntity();
            ((LivingEntity) entity).clearActivePotions();
            ((LivingEntity) entity).addPotionEffect(new EffectInstance(Effects.REGENERATION, 300, 1));
            ((LivingEntity) entity).addPotionEffect(new EffectInstance(Effects.HEALTH_BOOST, 300, 1));
            entity.extinguish();
            int i = entity instanceof HerobrineEntity ? 12 : 0;
            entity.attackEntityFrom(DamageSource.causeThrownDamage(this, this.getThrower()), (float)i);
        }
        if (!this.world.isRemote) {
            this.playSound(SoundEvents.BLOCK_GLASS_BREAK, 0.8F,  0.9F / (random.nextFloat() * 0.4F + 0.8F));
            this.world.setEntityState(this, (byte)3);
            this.remove();
        }
    }
}