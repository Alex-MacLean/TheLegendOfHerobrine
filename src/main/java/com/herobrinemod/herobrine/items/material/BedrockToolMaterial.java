package com.herobrinemod.herobrine.items.material;

import net.minecraft.item.ToolMaterial;
import net.minecraft.recipe.Ingredient;

public class BedrockToolMaterial implements ToolMaterial {
    @Override
    public int getDurability() {
        return -1;
    }

    @Override
    public float getMiningSpeedMultiplier() {
        return -1;
    }

    @Override
    public float getAttackDamage() {
        return 9.0f;
    }

    @Override
    public int getMiningLevel() {
        return -1;
    }

    @Override
    public int getEnchantability() {
        return -1;
    }

    @Override
    public Ingredient getRepairIngredient() {
        return null;
    }
}
