package com.herobrine.mod.client.models;

import com.herobrine.mod.entities.AbstractHerobrineEntity;
import net.minecraft.client.renderer.entity.model.PlayerModel;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class AbstractHerobrineEntityModel extends PlayerModel<AbstractHerobrineEntity> {
    public AbstractHerobrineEntityModel(float modelSize, boolean smallArmsIn) {
        super(modelSize, smallArmsIn);
    }
}