package com.herobrine.mod.entities;

import com.herobrine.mod.util.entities.EntityRegistry;
import net.minecraft.entity.EntityType;
import net.minecraft.world.World;

public class SteveSurvivorEntity extends AbstractSurvivorEntity {
    protected SteveSurvivorEntity(EntityType<? extends SteveSurvivorEntity> type, World world) {
        super(type, world);
    }

    public SteveSurvivorEntity(World worldIn) {
        this(EntityRegistry.STEVE_SURVIVOR_ENTITY, worldIn);
    }
}
