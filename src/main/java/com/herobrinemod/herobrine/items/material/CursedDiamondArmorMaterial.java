package com.herobrinemod.herobrine.items.material;

import com.herobrinemod.herobrine.HerobrineMod;
import com.herobrinemod.herobrine.items.ItemList;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.recipe.Ingredient;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import org.jetbrains.annotations.NotNull;

public class CursedDiamondArmorMaterial implements ArmorMaterial {
    private static final int[] BASE_DURABILITY = new int[] {429, 495, 528, 363};
    private static final int[] PROTECTION_VALUES = new int[] {3, 6, 8, 3};

    @Override
    public int getDurability(ArmorItem.@NotNull Type type) {
        return BASE_DURABILITY[type.getEquipmentSlot().getArmorStandSlotId() - 1];
    }

    @Override
    public int getProtection(ArmorItem.@NotNull Type type) {
        return PROTECTION_VALUES[type.getEquipmentSlot().getArmorStandSlotId() - 1];
    }

    @Override
    public int getEnchantability() {
        return 22;
    }

    @Override
    public SoundEvent getEquipSound() {
        return SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND;
    }

    @Override
    public Ingredient getRepairIngredient() {
        return Ingredient.ofItems(ItemList.CURSED_DIAMOND);
    }

    @Override
    public String getName() {
        return HerobrineMod.MODID + ":cursed_diamond";
    }

    @Override
    public float getToughness() {
        return 2.0f;
    }

    @Override
    public float getKnockbackResistance() {
        return 0.0f;
    }
}