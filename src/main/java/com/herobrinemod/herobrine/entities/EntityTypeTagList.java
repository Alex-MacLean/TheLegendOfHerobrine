package com.herobrinemod.herobrine.entities;

import com.herobrinemod.herobrine.HerobrineMod;
import net.minecraft.entity.EntityType;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

public class EntityTypeTagList {
    public static final TagKey<EntityType<?>> IS_HEROBRINE = TagKey.of(RegistryKeys.ENTITY_TYPE, new Identifier(HerobrineMod.MODID, "is_herobrine"));
    public static final TagKey<EntityType<?>> IS_INFECTED_BY_HEROBRINE = TagKey.of(RegistryKeys.ENTITY_TYPE, new Identifier(HerobrineMod.MODID, "is_infected_by_herobrine"));
}
