package com.herobrine.mod.items;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.NotNull;

public class HerobrineStatueItem extends BlockItem {
    public HerobrineStatueItem(Block blockIn, Properties builder) {
        super(blockIn, builder);
    }

    private boolean canBePlaced(@NotNull BlockItemUseContext context) {
        BlockPos pos = context.getPos();
        int x = pos.getX();
        int y = pos.getY();
        int z = pos.getZ();
        if(context.getWorld().getBlockState(new BlockPos(x, y, z)).getMaterial() == Material.AIR || context.getWorld().getBlockState(new BlockPos(x, y, z)).isReplaceable(context) || context.getWorld().getBlockState(new BlockPos(x, y, z)).getMaterial() == Material.WATER || context.getWorld().getBlockState(new BlockPos(x, y, z)).getMaterial() == Material.LAVA || context.getWorld().getBlockState(new BlockPos(x, y, z)).getMaterial() == Material.BUBBLE_COLUMN) {
            return context.getWorld().getBlockState(new BlockPos(x, y, z)).getMaterial() == Material.AIR || context.getWorld().getBlockState(new BlockPos(x, y, z)).isReplaceable(context) || context.getWorld().getBlockState(new BlockPos(x, y, z)).getMaterial() == Material.WATER || context.getWorld().getBlockState(new BlockPos(x, y, z)).getMaterial() == Material.LAVA || context.getWorld().getBlockState(new BlockPos(x, y, z)).getMaterial() == Material.BUBBLE_COLUMN;
        } else return false;
    }

    @Override
    protected boolean placeBlock(@NotNull BlockItemUseContext context, @NotNull BlockState state) {
        if(canBePlaced(context)) {
            return context.getWorld().setBlockState(context.getPos(), state, 3);
        } else return false;
    }
}
