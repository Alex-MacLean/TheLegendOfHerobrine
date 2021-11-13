package com.github.alexmaclean.herobrine.items.material;

import com.github.alexmaclean.herobrine.items.ItemList;
import net.minecraft.item.ToolMaterial;
import net.minecraft.recipe.Ingredient;

public class CursedDiamondToolMaterial implements ToolMaterial {
    @Override
    public int getDurability() {
        return 1600;
    }

    @Override
    public float getMiningSpeedMultiplier() {
        return 9.0f;
    }

    @Override
    public float getAttackDamage() {
        return 4.0f;
    }

    @Override
    public int getMiningLevel() {
        return 4;
    }

    @Override
    public int getEnchantability() {
        return 22;
    }

    @Override
    public Ingredient getRepairIngredient() {
        return Ingredient.ofItems(ItemList.CURSED_DIAMOND);
    }
}
