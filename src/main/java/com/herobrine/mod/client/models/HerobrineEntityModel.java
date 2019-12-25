package com.herobrine.mod.client.models;

import com.herobrine.mod.entities.HerobrineEntity;
import net.minecraft.client.renderer.entity.model.PlayerModel;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class HerobrineEntityModel extends PlayerModel<HerobrineEntity> {
    public HerobrineEntityModel(float modelSize, boolean smallArmsIn) {
        super(modelSize, smallArmsIn);
    }
}