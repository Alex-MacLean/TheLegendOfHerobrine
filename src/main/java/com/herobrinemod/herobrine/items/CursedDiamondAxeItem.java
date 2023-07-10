package com.herobrinemod.herobrine.items;

import com.herobrinemod.herobrine.savedata.ConfigHandler;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.AxeItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterial;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;

public class CursedDiamondAxeItem extends AxeItem {
    public CursedDiamondAxeItem(ToolMaterial material, float attackDamage, float attackSpeed, Settings settings) {
        super(material, attackDamage, attackSpeed, settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(@NotNull World world, @NotNull PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand);
        if(!user.isSneaking()) {
            user.addStatusEffect(new StatusEffectInstance(StatusEffects.SPEED, ConfigHandler.getHerobrineConfig().readInt("CursedDiamondAxeEffectsDurationTicks"), 1, false, false, true));
            user.addStatusEffect(new StatusEffectInstance(StatusEffects.STRENGTH, ConfigHandler.getHerobrineConfig().readInt("CursedDiamondAxeEffectsDurationTicks"), 0, false, false, true));
            user.addStatusEffect(new StatusEffectInstance(StatusEffects.RESISTANCE, ConfigHandler.getHerobrineConfig().readInt("CursedDiamondAxeEffectsDurationTicks"), 0, false, false, true));
            user.addStatusEffect(new StatusEffectInstance(StatusEffects.HASTE, ConfigHandler.getHerobrineConfig().readInt("CursedDiamondAxeEffectsDurationTicks"), 1, false, false, true));
            user.incrementStat(Stats.USED.getOrCreateStat(this));
            if(!user.isSilent()) {
                world.playSound(null, user.getX(), user.getY(), user.getZ(), SoundEvents.ENTITY_ILLUSIONER_CAST_SPELL, SoundCategory.PLAYERS, 0.5f, 0.4f / (world.getRandom().nextFloat() * 0.4f + 0.8f));
            }
            for (int i = 0; i < 16; i ++) {
                world.addParticle(ParticleTypes.PORTAL, user.getParticleX(1.0), user.getRandomBodyY() - 1, user.getParticleZ(1.0), user.getRandom().nextGaussian() * 0.02, user.getRandom().nextGaussian() * 0.02, user.getRandom().nextGaussian() * 0.02);
            }
            if(!user.isCreative()) {
                user.getItemCooldownManager().set(this, ConfigHandler.getHerobrineConfig().readInt("CursedDiamondAxeMagicCooldownTicks"));
                if(hand.equals(Hand.MAIN_HAND)) {
                    itemStack.damage(ConfigHandler.getHerobrineConfig().readInt("CursedDiamondMagicItemDamage"), user, e -> e.sendEquipmentBreakStatus(EquipmentSlot.MAINHAND));
                } else {
                    itemStack.damage(ConfigHandler.getHerobrineConfig().readInt("CursedDiamondMagicItemDamage"), user, e -> e.sendEquipmentBreakStatus(EquipmentSlot.OFFHAND));
                }
            }
            return TypedActionResult.success(itemStack, world.isClient());
        }
        return super.use(world, user, hand);
    }
}