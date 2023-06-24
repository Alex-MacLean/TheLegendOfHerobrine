package com.herobrinemod.herobrine.items;

import com.herobrinemod.herobrine.savedata.ConfigHandler;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LightningEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;

public class CursedDiamondSwordItem extends SwordItem {
    public CursedDiamondSwordItem(ToolMaterial toolMaterial, int attackDamage, float attackSpeed, Settings settings) {
        super(toolMaterial, attackDamage, attackSpeed, settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(@NotNull World world, @NotNull PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand);
        world.playSound(null, user.getX(), user.getY(), user.getZ(), SoundEvents.ENTITY_ILLUSIONER_CAST_SPELL, SoundCategory.PLAYERS, 0.5f, 0.4f / (world.getRandom().nextFloat() * 0.4f + 0.8f));
        for (int i = 0; i < 16; i ++) {
            world.addParticle(ParticleTypes.PORTAL, user.getParticleX(1.0), user.getRandomBodyY() - 1, user.getParticleZ(1.0), user.getRandom().nextGaussian() * 0.02, user.getRandom().nextGaussian() * 0.02, user.getRandom().nextGaussian() * 0.02);
        }
        if(!user.getItemCooldownManager().isCoolingDown(this)) {
            Vec3d pos = user.raycast(16.0, 1.0f, false).getPos();
            LightningEntity lightningentity = EntityType.LIGHTNING_BOLT.create(world);
            assert lightningentity != null;
            lightningentity.setPos(pos.getX(), pos.getY(), pos.getZ());
            world.spawnEntity(lightningentity);
            user.incrementStat(Stats.USED.getOrCreateStat(this));
            if (!user.getAbilities().creativeMode) {
                user.getItemCooldownManager().set(this, ConfigHandler.getHerobrineConfig().readInt("CursedDiamondSwordMagicCooldown"));
                itemStack.damage(ConfigHandler.getHerobrineConfig().readInt("CursedDiamondMagicItemDamage"), user, e -> e.sendEquipmentBreakStatus(EquipmentSlot.MAINHAND));
            }
            return TypedActionResult.success(itemStack, world.isClient());
        }
        return super.use(world, user, hand);
    }
}