package com.herobrinemod.herobrine.client.entities.models;

import com.herobrinemod.herobrine.entities.InfectedCowEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.entity.model.CowEntityModel;

@Environment(EnvType.CLIENT)
public class InfectedCowEntityModel<T extends InfectedCowEntity> extends CowEntityModel<T> {
    public InfectedCowEntityModel(ModelPart root) {
        super(root);
    }
}
