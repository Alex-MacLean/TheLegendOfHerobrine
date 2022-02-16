package com.github.alexmaclean.herobrine.util.entities;

import com.github.alexmaclean.herobrine.HerobrineMod;
import com.github.alexmaclean.herobrine.entities.HerobrineWarriorEntity;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class EntityTypeList {
    public static final EntityType<HerobrineWarriorEntity> HEROBRINE_WARRIOR = Registry.register(Registry.ENTITY_TYPE, new Identifier(HerobrineMod.MODID, "herobrine_warrior"), FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, HerobrineWarriorEntity::new).dimensions(EntityDimensions.fixed(0.6f, 1.8f)).build());
}
