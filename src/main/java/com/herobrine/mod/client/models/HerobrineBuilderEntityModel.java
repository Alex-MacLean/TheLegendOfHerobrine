package com.herobrine.mod.client.models;

import com.herobrine.mod.entities.HerobrineBuilderEntity;
import net.minecraft.client.renderer.entity.model.PlayerModel;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class HerobrineBuilderEntityModel extends PlayerModel<HerobrineBuilderEntity> {
    public HerobrineBuilderEntityModel(float modelSize, boolean smallArmsIn) {
        super(modelSize, smallArmsIn);
    }
}