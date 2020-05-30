package com.herobrine.mod.client.models;

import com.herobrine.mod.entities.InfectedVillagerEntity;
import net.minecraft.client.renderer.entity.model.VillagerModel;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class InfectedVillagerEntityModel extends VillagerModel<InfectedVillagerEntity> {
    public InfectedVillagerEntityModel(float scale) {
        super(scale);
    }
}