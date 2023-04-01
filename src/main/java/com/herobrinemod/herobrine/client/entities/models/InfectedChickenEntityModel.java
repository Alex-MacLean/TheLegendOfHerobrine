package com.herobrinemod.herobrine.client.entities.models;

import com.herobrinemod.herobrine.entities.InfectedChickenEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.entity.model.ChickenEntityModel;

@Environment(EnvType.CLIENT)
public class InfectedChickenEntityModel extends ChickenEntityModel<InfectedChickenEntity> {
    public InfectedChickenEntityModel(ModelPart root) {
        super(root);
    }
}
