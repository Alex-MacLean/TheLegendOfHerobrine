package com.herobrine.mod.items;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.BlockItem;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;

public class HerobrineStatueItem extends BlockItem {
    public HerobrineStatueItem(Block blockIn, Properties builder) {
        super(blockIn, builder);
    }

    private boolean canBePlaced(@NotNull World world, @NotNull BlockPos pos) {
        int x = pos.getX();
        int y = pos.getY();
        int z = pos.getZ();
        BlockState state = world.getBlockState(new BlockPos(x, y + 1, z));
        return state.getMaterial().isReplaceable();
    }

    @Override
    public @NotNull ActionResultType place(@NotNull BlockItemUseContext context) {
        if (this.canBePlaced(context.getLevel(), context.getClickedPos())) {
            return super.place(context);
        } else return ActionResultType.FAIL;
    }
}