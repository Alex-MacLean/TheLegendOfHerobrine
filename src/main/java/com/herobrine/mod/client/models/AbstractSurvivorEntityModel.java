package com.herobrine.mod.client.models;

import com.herobrine.mod.entities.AbstractSurvivorEntity;
import net.minecraft.client.renderer.entity.model.PlayerModel;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class AbstractSurvivorEntityModel extends PlayerModel<AbstractSurvivorEntity> {
    public AbstractSurvivorEntityModel(float modelSize, boolean smallArmsIn) {
        super(modelSize, smallArmsIn);
    }
}