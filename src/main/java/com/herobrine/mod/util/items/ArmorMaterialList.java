package com.herobrine.mod.util.items;

import com.herobrine.mod.HerobrineMod;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemArmor;
import net.minecraftforge.common.util.EnumHelper;

public class ArmorMaterialList {
    public static final ItemArmor.ArmorMaterial cursed_diamond_armor_material = EnumHelper.addArmorMaterial("cursed_diamond", HerobrineMod.MODID + ":" + "cursed_diamond", 35, new int[]{4, 7, 9, 4}, 25, SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND, 3.0f);
}
