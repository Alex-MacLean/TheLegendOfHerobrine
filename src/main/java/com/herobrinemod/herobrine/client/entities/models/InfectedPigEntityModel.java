package com.herobrinemod.herobrine.client.entities.models;

import com.herobrinemod.herobrine.entities.InfectedPigEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.EntityModelPartNames;
import net.minecraft.client.render.entity.model.PigEntityModel;
import net.minecraft.client.render.entity.model.QuadrupedEntityModel;
import org.jetbrains.annotations.NotNull;

@Environment(EnvType.CLIENT)
public class InfectedPigEntityModel extends PigEntityModel<InfectedPigEntity> {
    public InfectedPigEntityModel(ModelPart root) {
        super(root);
    }

    public static @NotNull TexturedModelData getTexturedModelData() {
        ModelData modelData = QuadrupedEntityModel.getModelData(6, Dilation.NONE);
        ModelPartData modelPartData = modelData.getRoot();
        modelPartData.addChild(EntityModelPartNames.HEAD, ModelPartBuilder.create().uv(0, 0).cuboid(-4.0f, -4.0f, -8.0f, 8.0f, 8.0f, 8.0f, Dilation.NONE).uv(16, 16).cuboid(-2.0f, 0.0f, -9.0f, 4.0f, 3.0f, 1.0f, Dilation.NONE), ModelTransform.pivot(0.0f, 12.0f, -6.0f));
        return TexturedModelData.of(modelData, 64, 32);
    }
}
