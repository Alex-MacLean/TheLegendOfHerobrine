package com.herobrine.mod.entities;

import com.herobrine.mod.util.entities.EntityRegistry;
import net.minecraft.entity.EntityType;
import net.minecraft.world.World;

public class AlexSurvivorEntity extends AbstractSurvivorEntity {
    protected AlexSurvivorEntity(EntityType<? extends AlexSurvivorEntity> type, World world) {
        super(type, world);
    }

    public AlexSurvivorEntity(World worldIn) {
        this(EntityRegistry.ALEX_SURVIVOR_ENTITY, worldIn);
    }
}