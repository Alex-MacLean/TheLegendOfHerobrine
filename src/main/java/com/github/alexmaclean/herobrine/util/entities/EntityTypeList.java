package com.github.alexmaclean.herobrine.util.entities;

import com.github.alexmaclean.herobrine.HerobrineMod;
import com.github.alexmaclean.herobrine.entities.*;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class EntityTypeList {
    public static final EntityType<HerobrineWarriorEntity> HEROBRINE_WARRIOR = Registry.register(Registry.ENTITY_TYPE, new Identifier(HerobrineMod.MODID, "herobrine_warrior"), FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, HerobrineWarriorEntity::new).dimensions(EntityDimensions.fixed(0.6f, 1.8f)).build());
    public static final EntityType<HerobrineSpyEntity> HEROBRINE_SPY = Registry.register(Registry.ENTITY_TYPE, new Identifier(HerobrineMod.MODID, "herobrine_spy"), FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, HerobrineSpyEntity::new).dimensions(EntityDimensions.fixed(0.6f, 1.8f)).build());
    public static final EntityType<HerobrineMageEntity> HEROBRINE_MAGE = Registry.register(Registry.ENTITY_TYPE, new Identifier(HerobrineMod.MODID, "herobrine_mage"), FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, HerobrineMageEntity::new).dimensions(EntityDimensions.fixed(0.6f, 1.8f)).build());
    public static final EntityType<FakeHerobrineMageEntity> FAKE_HEROBRINE_MAGE = Registry.register(Registry.ENTITY_TYPE, new Identifier(HerobrineMod.MODID, "fake_herobrine_mage"), FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, FakeHerobrineMageEntity::new).dimensions(EntityDimensions.fixed(0.6f, 1.8f)).build());
    public static final EntityType<HerobrineBuilderEntity> HEROBRINE_BUILDER = Registry.register(Registry.ENTITY_TYPE, new Identifier(HerobrineMod.MODID, "herobrine_builder"), FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, HerobrineBuilderEntity::new).dimensions(EntityDimensions.fixed(0.6f, 1.8f)).build());
    public static final EntityType<HerobrineStalkerEntity> HEROBRINE_STALKER = Registry.register(Registry.ENTITY_TYPE, new Identifier(HerobrineMod.MODID, "herobrine_stalker"), FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, HerobrineStalkerEntity::new).dimensions(EntityDimensions.fixed(0.6f, 1.8f)).build());
}
