package com.herobrinemod.herobrine.items;

import com.herobrinemod.herobrine.entities.FakeHerobrineMageEntity;
import com.herobrinemod.herobrine.savedata.ConfigHandler;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LightningEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.screen.ScreenTexts;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class CursedDiamondSwordItem extends SwordItem {
    private final String KILLS_KEY = "Kills";
    public CursedDiamondSwordItem(ToolMaterial toolMaterial, int attackDamage, float attackSpeed, Settings settings) {
        super(toolMaterial, attackDamage, attackSpeed, settings);
    }

    @Override
    public boolean postHit(ItemStack stack, @NotNull LivingEntity target, LivingEntity attacker) {
        if(!target.isAlive() && !(target instanceof FakeHerobrineMageEntity)) {
            this.setKills(stack, getKills(stack) + 1);
        }
        return super.postHit(stack, target, attacker);
    }

    public int getKills(@NotNull ItemStack stack) {
        NbtCompound nbtCompound = stack.getOrCreateNbt();
        return nbtCompound.getInt(KILLS_KEY);
    }

    public void setKills(@NotNull ItemStack stack, int kills) {
        NbtCompound nbtCompound = stack.getOrCreateNbt();
        nbtCompound.putInt(KILLS_KEY, kills);
    }

    @Override
    public void appendTooltip(@NotNull ItemStack stack, @Nullable World world, @NotNull List<Text> tooltip, TooltipContext context) {
        if(this.getKills(stack) < ConfigHandler.getHerobrineConfig().readInt("CursedDiamondSwordChargeCost")) {
            tooltip.add(Text.translatable("item.herobrine.cursed_diamond_sword.kills").append(ScreenTexts.SPACE).append(String.valueOf(this.getKills(stack))).formatted(Formatting.GRAY));
        } else {
            tooltip.add(Text.translatable("item.herobrine.cursed_diamond_sword.kills").append(ScreenTexts.SPACE).append(String.valueOf(this.getKills(stack))).formatted(Formatting.LIGHT_PURPLE));
        }
        super.appendTooltip(stack, world, tooltip, context);
    }

    @Override
    public TypedActionResult<ItemStack> use(@NotNull World world, @NotNull PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand);
        if((user.isCreative() || this.getKills(itemStack) >= ConfigHandler.getHerobrineConfig().readInt("CursedDiamondSwordChargeCost")) && !user.isSneaking()) {
            if(!user.isSilent()) {
                world.playSound(null, user.getX(), user.getY(), user.getZ(), SoundEvents.ENTITY_ILLUSIONER_CAST_SPELL, SoundCategory.PLAYERS, 0.5f, 0.4f / (world.getRandom().nextFloat() * 0.4f + 0.8f));
            }
            for (int i = 0; i < 16; i ++) {
                world.addParticle(ParticleTypes.PORTAL, user.getParticleX(1.0), user.getRandomBodyY() - 1, user.getParticleZ(1.0), user.getRandom().nextGaussian() * 0.02, user.getRandom().nextGaussian() * 0.02, user.getRandom().nextGaussian() * 0.02);
            }
            Vec3d pos = user.raycast(16.0, 0.0f, false).getPos();
            LightningEntity lightningentity = EntityType.LIGHTNING_BOLT.create(world);
            assert lightningentity != null;
            lightningentity.setPos(pos.getX(), pos.getY(), pos.getZ());
            world.spawnEntity(lightningentity);
            user.incrementStat(Stats.USED.getOrCreateStat(this));
            if (!user.isCreative()) {
                user.getItemCooldownManager().set(this, ConfigHandler.getHerobrineConfig().readInt("CursedDiamondSwordMagicCooldownTicks"));
                if(hand.equals(Hand.MAIN_HAND)) {
                    itemStack.damage(ConfigHandler.getHerobrineConfig().readInt("CursedDiamondMagicItemDamage"), user, e -> e.sendEquipmentBreakStatus(EquipmentSlot.MAINHAND));
                } else {
                    itemStack.damage(ConfigHandler.getHerobrineConfig().readInt("CursedDiamondMagicItemDamage"), user, e -> e.sendEquipmentBreakStatus(EquipmentSlot.OFFHAND));
                }
                if(!world.isClient) {
                    this.setKills(itemStack, this.getKills(itemStack) - ConfigHandler.getHerobrineConfig().readInt("CursedDiamondSwordChargeCost") );
                }
            }
            return TypedActionResult.success(itemStack, world.isClient());
        }
        return super.use(world, user, hand);
    }
}