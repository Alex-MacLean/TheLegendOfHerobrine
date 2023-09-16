package com.herobrinemod.herobrine.mixin;

import com.herobrinemod.herobrine.items.ItemList;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {
    @Shadow
    public abstract ItemStack getEquippedStack(EquipmentSlot var1);

    @Shadow
    public abstract boolean addStatusEffect(StatusEffectInstance effect);

    private LivingEntityMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    @Inject(method = "tick", at = @At("TAIL"))
    private void updateCursedDiamondArmor(CallbackInfo ci) {
        if(getWorld().getTime() % 80L == 0L) {
            if (this.getEquippedStack(EquipmentSlot.HEAD).isOf(ItemList.CURSED_DIAMOND_HELMET)) {
                this.addStatusEffect(new StatusEffectInstance(StatusEffects.WATER_BREATHING, 100, 0, false, false, false));
            }
            if (this.getEquippedStack(EquipmentSlot.CHEST).isOf(ItemList.CURSED_DIAMOND_CHESTPLATE)) {
                this.addStatusEffect(new StatusEffectInstance(StatusEffects.HASTE, 100, 0, false, false, false));
            }
            if (this.getEquippedStack(EquipmentSlot.LEGS).isOf(ItemList.CURSED_DIAMOND_LEGGINGS)) {
                this.addStatusEffect(new StatusEffectInstance(StatusEffects.JUMP_BOOST, 100, 0, false, false, false));
            }
            if (this.getEquippedStack(EquipmentSlot.FEET).isOf(ItemList.CURSED_DIAMOND_BOOTS)) {
                this.addStatusEffect(new StatusEffectInstance(StatusEffects.SPEED, 100, 0, false, false, false));
            }
            if (this.getEquippedStack(EquipmentSlot.HEAD).isOf(ItemList.CURSED_DIAMOND_HELMET) && this.getEquippedStack(EquipmentSlot.CHEST).isOf(ItemList.CURSED_DIAMOND_CHESTPLATE) && this.getEquippedStack(EquipmentSlot.LEGS).isOf(ItemList.CURSED_DIAMOND_LEGGINGS) && this.getEquippedStack(EquipmentSlot.FEET).isOf(ItemList.CURSED_DIAMOND_BOOTS)) {
                this.addStatusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, 100, 1, false, false, false));
                this.addStatusEffect(new StatusEffectInstance(StatusEffects.FIRE_RESISTANCE, 100, 0, false, false, false));
            }
        }
    }
}