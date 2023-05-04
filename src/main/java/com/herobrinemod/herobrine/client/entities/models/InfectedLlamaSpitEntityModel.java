package com.herobrinemod.herobrine.client.entities.models;

import com.herobrinemod.herobrine.entities.InfectedLlamaSpitEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.entity.model.LlamaSpitEntityModel;

@Environment(EnvType.CLIENT)
public class InfectedLlamaSpitEntityModel extends LlamaSpitEntityModel<InfectedLlamaSpitEntity> {
    public InfectedLlamaSpitEntityModel(ModelPart root) {
        super(root);
    }
}
