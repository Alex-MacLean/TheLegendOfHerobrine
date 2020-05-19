package com.herobrine.mod.util.items;

import net.minecraft.item.IItemTier;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import org.jetbrains.annotations.NotNull;

public enum ItemTierList implements IItemTier {
    bedrock_item_tier(9.0f, -1.0f, -1, -1, -1, Items.BEDROCK),
    cursed_diamond_item_tier(4.0f, 9.0f, 1600, 4, 22, ItemList.cursed_diamond);

    private final float attackDamage;
    private final float efficiency;
    private final int maxUses;
    private final int harvestLevel;
    private final int enchantability;
    private final Item repairMaterial;

    ItemTierList(float attackDamage, float efficiency, int maxUses, int harvestLevel, int enchantability, Item repairMaterial) {
        this.attackDamage = attackDamage;
        this.efficiency = efficiency;
        this.maxUses = maxUses;
        this.harvestLevel = harvestLevel;
        this.enchantability = enchantability;
        this.repairMaterial = repairMaterial;
    }

    @Override
    public int getMaxUses() {
        return this.maxUses;
    }

    @Override
    public float getEfficiency() {
        return this.efficiency;
    }

    @Override
    public float getAttackDamage() {
        return this.attackDamage;
    }

    @Override
    public int getHarvestLevel() {
        return this.harvestLevel;
    }

    @Override
    public int getEnchantability() {
        return this.enchantability;
    }

    @NotNull
    @Override
    public Ingredient getRepairMaterial() {
        return Ingredient.fromItems(this.repairMaterial);
    }
}