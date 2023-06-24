package com.herobrinemod.herobrine.items;

import com.herobrinemod.herobrine.savedata.ConfigHandler;
import com.mojang.datafixers.util.Pair;
import net.minecraft.block.BlockState;
import net.minecraft.block.Fertilizable;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.HoeItem;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.item.ToolMaterial;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;
import java.util.function.Predicate;

public class CursedDiamondHoeItem extends HoeItem {
    public CursedDiamondHoeItem(ToolMaterial material, int attackDamage, float attackSpeed, Settings settings) {
        super(material, attackDamage, attackSpeed, settings);
    }

    @Override
    public ActionResult useOnBlock(@NotNull ItemUsageContext context) {
        BlockPos blockPos = context.getBlockPos();
        World world = context.getWorld();
        Fertilizable fertilizable;
        BlockState blockState = world.getBlockState(blockPos);
        PlayerEntity player = context.getPlayer();
        Pair<Predicate<ItemUsageContext>, Consumer<ItemUsageContext>> pair = TILLING_ACTIONS.get(world.getBlockState(blockPos = context.getBlockPos()).getBlock());
        if (blockState.getBlock() instanceof Fertilizable && (fertilizable = (Fertilizable) blockState.getBlock()).isFertilizable(world, blockPos, blockState, world.isClient) && pair == null) {
            boolean fertilized = false;
            if (fertilizable.canGrow(world, world.random, blockPos, blockState)) {
                if (world instanceof ServerWorld) {
                    fertilizable.grow((ServerWorld) world, world.random, blockPos, blockState);
                }

                fertilized = true;
            }

            assert player != null;
            if(!player.isCreative()) {
                player.getItemCooldownManager().set(this, ConfigHandler.getHerobrineConfig().readInt("CursedDiamondHoeMagicCooldown"));
                context.getStack().damage(ConfigHandler.getHerobrineConfig().readInt("CursedDiamondMagicItemDamage"), player, p -> p.sendToolBreakStatus(context.getHand()));
            }

            if(fertilized) {
                world.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.ENTITY_ILLUSIONER_CAST_SPELL, SoundCategory.PLAYERS, 0.5f, 0.4f / (world.getRandom().nextFloat() * 0.4f + 0.8f));
                for (int i = 0; i < 16; i ++) {
                    world.addParticle(ParticleTypes.PORTAL, player.getParticleX(1.0), player.getRandomBodyY() - 1, player.getParticleZ(1.0), player.getRandom().nextGaussian() * 0.02, player.getRandom().nextGaussian() * 0.02, player.getRandom().nextGaussian() * 0.02);
                }
                return ActionResult.SUCCESS;
            }
        }
        return super.useOnBlock(context);
    }
}