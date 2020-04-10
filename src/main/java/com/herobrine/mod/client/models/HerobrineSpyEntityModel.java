package com.herobrine.mod.client.models;

import com.herobrine.mod.entities.HerobrineSpyEntity;
import com.herobrine.mod.entities.HerobrineWarriorEntity;
import net.minecraft.client.renderer.entity.model.PlayerModel;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class HerobrineSpyEntityModel extends PlayerModel<HerobrineSpyEntity> {
    public HerobrineSpyEntityModel(float modelSize, boolean smallArmsIn) {
        super(modelSize, smallArmsIn);
    }
}