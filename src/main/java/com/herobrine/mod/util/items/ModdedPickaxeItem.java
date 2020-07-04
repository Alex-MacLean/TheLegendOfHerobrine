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

public class ModdedPickaxeItem extends ItemTool
{
    private static final Set<Block> EFFECTIVE_ON = Sets.newHashSet(Blocks.ACTIVATOR_RAIL, Blocks.COAL_ORE, Blocks.COBBLESTONE, Blocks.DETECTOR_RAIL, Blocks.DIAMOND_BLOCK, Blocks.DIAMOND_ORE, Blocks.DOUBLE_STONE_SLAB, Blocks.GOLDEN_RAIL, Blocks.GOLD_BLOCK, Blocks.GOLD_ORE, Blocks.ICE, Blocks.IRON_BLOCK, Blocks.IRON_ORE, Blocks.LAPIS_BLOCK, Blocks.LAPIS_ORE, Blocks.LIT_REDSTONE_ORE, Blocks.MOSSY_COBBLESTONE, Blocks.NETHERRACK, Blocks.PACKED_ICE, Blocks.RAIL, Blocks.REDSTONE_ORE, Blocks.SANDSTONE, Blocks.RED_SANDSTONE, Blocks.STONE, Blocks.STONE_SLAB, Blocks.STONE_BUTTON, Blocks.STONE_PRESSURE_PLATE);

    public ModdedPickaxeItem(Item.ToolMaterial material)
    {
        super(1.0F, -2.8F, material, EFFECTIVE_ON);
    }

    @Override
    public boolean canHarvestBlock(@NotNull IBlockState state) {
        Block block = state.getBlock();
        Material material = state.getMaterial();
        return !material.isToolNotRequired() && block.getHarvestLevel(state) <= toolMaterial.getHarvestLevel() && Objects.equals(block.getHarvestTool(state), "pickaxe");
    }

    @Override
    public float getDestroySpeed(@NotNull ItemStack stack, @NotNull IBlockState state) {
        Material material = state.getMaterial();
        if(this.canHarvestBlock(state)) {
            return this.efficiency;
        } else return material != Material.IRON && material != Material.ANVIL && material != Material.ROCK ? super.getDestroySpeed(stack, state) : this.efficiency;
    }
}