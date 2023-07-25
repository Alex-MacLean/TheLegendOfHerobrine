package com.herobrinemod.herobrine.items;

import com.herobrinemod.herobrine.HerobrineMod;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

public class ItemTagList {
    public static final TagKey<Item> ACTIVATES_HEROBRINE_ALTAR = TagKey.of(RegistryKeys.ITEM, new Identifier(HerobrineMod.MODID, "activates_herobrine_altar"));
}