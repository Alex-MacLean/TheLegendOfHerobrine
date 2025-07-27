package com.herobrinemod.herobrine.client.entities.models;

import com.google.common.collect.ImmutableList;
import com.herobrinemod.herobrine.entities.InfectedLlamaEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.EntityModelPartNames;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.MathHelper;
import org.jetbrains.annotations.NotNull;

@Environment(EnvType.CLIENT)
public class InfectedLlamaEntityModel extends EntityModel<InfectedLlamaEntity> {
    private final ModelPart head;
    private final ModelPart body;
    private final ModelPart rightHindLeg;
    private final ModelPart leftHindLeg;
    private final ModelPart rightFrontLeg;
    private final ModelPart leftFrontLeg;

    public InfectedLlamaEntityModel(@NotNull ModelPart root) {
        this.head = root.getChild(EntityModelPartNames.HEAD);
        this.body = root.getChild(EntityModelPartNames.BODY);
        this.rightHindLeg = root.getChild(EntityModelPartNames.RIGHT_HIND_LEG);
        this.leftHindLeg = root.getChild(EntityModelPartNames.LEFT_HIND_LEG);
        this.rightFrontLeg = root.getChild(EntityModelPartNames.RIGHT_FRONT_LEG);
        this.leftFrontLeg = root.getChild(EntityModelPartNames.LEFT_FRONT_LEG);
    }

    public static @NotNull TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        modelPartData.addChild(EntityModelPartNames.HEAD, ModelPartBuilder.create().uv(0, 0).cuboid(-2.0f, -14.0f, -10.0f, 4.0f, 4.0f, 9.0f, Dilation.NONE).uv(0, 14).cuboid(EntityModelPartNames.NECK, -4.0f, -16.0f, -6.0f, 8.0f, 18.0f, 6.0f, Dilation.NONE).uv(17, 0).cuboid("ear", -4.0f, -19.0f, -4.0f, 3.0f, 3.0f, 2.0f, Dilation.NONE).uv(17, 0).cuboid("ear", 1.0f, -19.0f, -4.0f, 3.0f, 3.0f, 2.0f, Dilation.NONE), ModelTransform.pivot(0.0f, 7.0f, -6.0f));
        modelPartData.addChild(EntityModelPartNames.BODY, ModelPartBuilder.create().uv(29, 0).cuboid(-6.0f, -10.0f, -7.0f, 12.0f, 18.0f, 10.0f, Dilation.NONE), ModelTransform.of(0.0f, 5.0f, 2.0f, 1.5707964f, 0.0f, 0.0f));
        ModelPartBuilder modelPartBuilder = ModelPartBuilder.create().uv(29, 29).cuboid(-2.0f, 0.0f, -2.0f, 4.0f, 14.0f, 4.0f, Dilation.NONE);
        modelPartData.addChild(EntityModelPartNames.RIGHT_HIND_LEG, modelPartBuilder, ModelTransform.pivot(-3.5f, 10.0f, 6.0f));
        modelPartData.addChild(EntityModelPartNames.LEFT_HIND_LEG, modelPartBuilder, ModelTransform.pivot(3.5f, 10.0f, 6.0f));
        modelPartData.addChild(EntityModelPartNames.RIGHT_FRONT_LEG, modelPartBuilder, ModelTransform.pivot(-3.5f, 10.0f, -5.0f));
        modelPartData.addChild(EntityModelPartNames.LEFT_FRONT_LEG, modelPartBuilder, ModelTransform.pivot(3.5f, 10.0f, -5.0f));
        return TexturedModelData.of(modelData, 128, 64);
    }

    @Override
    public void setAngles(InfectedLlamaEntity entity, float f, float g, float h, float i, float j) {
        this.head.pitch = j * ((float)Math.PI / 180);
        this.head.yaw = i * ((float)Math.PI / 180);
        this.rightHindLeg.pitch = MathHelper.cos(f * 0.6662f) * 1.4f * g;
        this.leftHindLeg.pitch = MathHelper.cos(f * 0.6662f + (float)Math.PI) * 1.4f * g;
        this.rightFrontLeg.pitch = MathHelper.cos(f * 0.6662f + (float)Math.PI) * 1.4f * g;
        this.leftFrontLeg.pitch = MathHelper.cos(f * 0.6662f) * 1.4f * g;
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumer vertices, int light, int overlay, float red, float green, float blue, float alpha) {
        if (this.child) {
            matrices.push();
            matrices.scale(0.71428573f, 0.64935064f, 0.7936508f);
            matrices.translate(0.0f, 1.3125f, 0.22f);
            this.head.render(matrices, vertices, light, overlay, red, green, blue, alpha);
            matrices.pop();
            matrices.push();
            matrices.scale(0.625f, 0.45454544f, 0.45454544f);
            matrices.translate(0.0f, 2.0625f, 0.0f);
            this.body.render(matrices, vertices, light, overlay, red, green, blue, alpha);
            matrices.pop();
            matrices.push();
            matrices.scale(0.45454544f, 0.41322312f, 0.45454544f);
            matrices.translate(0.0f, 2.0625f, 0.0f);
            ImmutableList.of(this.rightHindLeg, this.leftHindLeg, this.rightFrontLeg, this.leftFrontLeg).forEach(part -> part.render(matrices, vertices, light, overlay, red, green, blue, alpha));
            matrices.pop();
        } else {
            ImmutableList.of(this.head, this.body, this.rightHindLeg, this.leftHindLeg, this.rightFrontLeg, this.leftFrontLeg).forEach(part -> part.render(matrices, vertices, light, overlay, red, green, blue, alpha));
        }
    }
}