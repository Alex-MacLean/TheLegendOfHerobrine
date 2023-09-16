package com.herobrinemod.herobrine.mixin;

import com.herobrinemod.herobrine.entities.InfectedAxolotlEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.projectile.thrown.PotionEntity;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(PotionEntity.class)
public abstract class PotionEntityMixin extends Entity {
    private PotionEntityMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    @Inject(method = "applyWater", at = @At("TAIL"))
    private void hydrateInfectedAxolotlFromPotion(CallbackInfo ci) {
        Box box = this.getBoundingBox().expand(4.0, 2.0, 4.0);
        List<InfectedAxolotlEntity> list = this.getWorld().getNonSpectatingEntities(InfectedAxolotlEntity.class, box);
        for (InfectedAxolotlEntity axolotlEntity : list) {
            axolotlEntity.hydrateFromPotion();
        }
    }
}