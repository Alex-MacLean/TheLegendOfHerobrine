package com.herobrinemod.herobrine.items.material;

import com.herobrinemod.herobrine.HerobrineMod;
import com.herobrinemod.herobrine.items.ItemList;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.recipe.Ingredient;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;

import java.util.EnumMap;
import java.util.List;

public class ArmorMaterialList {
    public static final ArmorMaterial CURSED_DIAMOND = new CursedDiamondArmorMaterial();
}
