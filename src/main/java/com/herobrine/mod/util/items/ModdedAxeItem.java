package com.herobrine.mod.util.items;

import com.google.common.collect.Sets;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.Set;

public class ModdedAxeItem extends ItemTool {
    private static final Set<Block> EFFECTIVE_ON = Sets.newHashSet(Blocks.PLANKS, Blocks.BOOKSHELF, Blocks.LOG, Blocks.LOG2, Blocks.CHEST, Blocks.PUMPKIN, Blocks.LIT_PUMPKIN, Blocks.MELON_BLOCK, Blocks.LADDER, Blocks.WOODEN_BUTTON, Blocks.WOODEN_PRESSURE_PLATE);
    public ModdedAxeItem(Item.ToolMaterial material, float damage, float speed) {
        super(material, EFFECTIVE_ON);
        this.attackDamage = damage;
        this.attackSpeed = speed;
    }

    @Override
    public boolean canHarvestBlock(@NotNull IBlockState state) {
        Block block = state.getBlock();
        Material material = state.getMaterial();
        return !material.isToolNotRequired() && block.getHarvestLevel(state) <= toolMaterial.getHarvestLevel() && Objects.equals(block.getHarvestTool(state), "axe");
    }

    @Override
    public float getDestroySpeed(@NotNull ItemStack stack, @NotNull IBlockState state) {
        Material material = state.getMaterial();
        if(this.canHarvestBlock(state)) {
            return this.efficiency;
        } else return material != Material.IRON && material != Material.ANVIL && material != Material.ROCK ? super.getDestroySpeed(stack, state) : this.efficiency;
    }
}