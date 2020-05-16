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
    public ActionResult<ItemStack> onItemRightClick(@NotNull World worldIn, @NotNull PlayerEntity playerIn, @NotNull Hand handIn) {
        ItemStack itemstack = playerIn.getHeldItem(handIn);
        worldIn.playSound(null, playerIn.posX, playerIn.posY, playerIn.posZ, SoundEvents.ENTITY_SPLASH_POTION_THROW, SoundCategory.PLAYERS, 0.5F, 0.4F / (random.nextFloat() * 0.4F + 0.8F));
        playerIn.getCooldownTracker().setCooldown(this, 10);
        if (!worldIn.isRemote) {
            HolyWaterEntity entity = new HolyWaterEntity(worldIn, playerIn);
            entity.setItem(itemstack);
            entity.shoot(playerIn, playerIn.rotationPitch, playerIn.rotationYaw, -20.0F, 0.5F, 1.0F);
            worldIn.addEntity(entity);
        }

        if (!playerIn.abilities.isCreativeMode) {
            itemstack.shrink(1);
        }

        playerIn.swingArm(handIn);
        return super.onItemRightClick(worldIn, playerIn, handIn);
    }
}