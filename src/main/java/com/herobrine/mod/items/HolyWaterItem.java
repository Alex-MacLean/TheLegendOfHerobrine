package com.herobrine.mod.items;

import com.herobrine.mod.entities.HolyWaterEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;

public class HolyWaterItem extends Item {

    public HolyWaterItem(Properties properties) {
        super(properties);
    }

    @NotNull
    @Override
    public ActionResult<ItemStack> use(@NotNull World worldIn, @NotNull PlayerEntity playerIn, @NotNull Hand handIn) {
        ItemStack itemstack = playerIn.getItemInHand(handIn);
        worldIn.playSound(null, playerIn.getX(), playerIn.getY(), playerIn.getZ(), SoundEvents.SPLASH_POTION_THROW, SoundCategory.PLAYERS, 0.5F, 0.4F / (random.nextFloat() * 0.4F + 0.8F));
        playerIn.getCooldowns().addCooldown(this, 10);
        if (!worldIn.isClientSide) {
            HolyWaterEntity entity = new HolyWaterEntity(worldIn, playerIn);
            entity.setItem(itemstack);
            entity.shootFromRotation(playerIn, playerIn.xRot, playerIn.yRot, -20.0F, 0.5F, 1.0F);
            worldIn.addFreshEntity(entity);
        }

        if (!playerIn.abilities.instabuild) {
            itemstack.shrink(1);
        }

        return ActionResult.success(itemstack);
    }
}