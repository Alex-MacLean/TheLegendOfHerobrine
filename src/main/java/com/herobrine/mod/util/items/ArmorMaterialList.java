package com.herobrine.mod.util.items;

import com.herobrine.mod.HerobrineMod;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public enum ArmorMaterialList implements IArmorMaterial {
    cursed_diamond_armor_material("cursed_diamond",35, new int[]{4, 7, 9, 4}, 25, ItemList.cursed_diamond, SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND, 3.0F, 0.0F);

        public static final int[] max_damage_array = new int[]{13, 15, 16, 11};
        private final String name;
        private final SoundEvent equipSound;
        private final int durability;
        private final int enchantability;
        private final Item repairItem;
        private final int[] damageReductionAmounts;
        private final float toughness;
        private final float knockbackResistance;

    ArmorMaterialList(String name, int durability, int[] damageReductionAmounts, int enchantability, Item repairItem, SoundEvent equipSound, float toughness, float knockbackResistance) {
        this.name = name;
        this.equipSound = equipSound;
        this.durability = durability;
        this.enchantability = enchantability;
        this.repairItem = repairItem;
        this.damageReductionAmounts = damageReductionAmounts;
        this.toughness = toughness;
        this.knockbackResistance = knockbackResistance;
    }

    @Contract(pure = true)
    @Override
    public int getDurability(@NotNull EquipmentSlotType slotIn) {
        return max_damage_array[slotIn.getIndex()] * this.durability;
    }

    @Contract(pure = true)
    @Override
    public int getDamageReductionAmount(@NotNull EquipmentSlotType slotIn) {
        return  this.damageReductionAmounts[slotIn.getIndex()];
    }

    @Override
    public int getEnchantability() {
        return this.enchantability;
    }

    @NotNull
    @Contract(pure = true)
    @Override
    public SoundEvent getSoundEvent() {
        return this.equipSound;
    }

    @NotNull
    @Contract(pure = true)
    @Override
    public Ingredient getRepairMaterial() {
        return Ingredient.fromItems(this.repairItem);
    }

    @NotNull
    @Contract(pure = true)
    @Override
    public String getName() {
        return HerobrineMod.MODID + ":" + this.name;
    }

    @Override
    public float getToughness() {
        return this.toughness;
    }

    @Override
    public float getKnockbackResistance() {
        return this.knockbackResistance;
    }
}
