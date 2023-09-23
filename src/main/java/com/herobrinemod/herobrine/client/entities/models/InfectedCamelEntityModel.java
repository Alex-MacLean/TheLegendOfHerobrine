package com.herobrinemod.herobrine.client.entities.models;

import com.herobrinemod.herobrine.entities.InfectedCamelEntity;
import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.animation.CamelAnimations;
import net.minecraft.client.render.entity.model.EntityModelPartNames;
import net.minecraft.client.render.entity.model.SinglePartEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.MathHelper;
import org.jetbrains.annotations.NotNull;

public class InfectedCamelEntityModel extends SinglePartEntityModel<InfectedCamelEntity> {
    private final ModelPart root;
    private final ModelPart head;

    public InfectedCamelEntityModel(@NotNull ModelPart root) {
        this.root = root;
        ModelPart modelPart = root.getChild(EntityModelPartNames.BODY);
        this.head = modelPart.getChild(EntityModelPartNames.HEAD);
    }

    public static @NotNull TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        ModelPartData modelPartData2 = modelPartData.addChild(EntityModelPartNames.BODY, ModelPartBuilder.create().uv(0, 25).cuboid(-7.5f, -12.0f, -23.5f, 15.0f, 12.0f, 27.0f), ModelTransform.pivot(0.0f, 4.0f, 9.5f));
        modelPartData2.addChild("hump", ModelPartBuilder.create().uv(74, 0).cuboid(-4.5f, -5.0f, -5.5f, 9.0f, 5.0f, 11.0f), ModelTransform.pivot(0.0f, -12.0f, -10.0f));
        modelPartData2.addChild(EntityModelPartNames.TAIL, ModelPartBuilder.create().uv(122, 0).cuboid(-1.5f, 0.0f, 0.0f, 3.0f, 14.0f, 0.0f), ModelTransform.pivot(0.0f, -9.0f, 3.5f));
        ModelPartData modelPartData3 = modelPartData2.addChild(EntityModelPartNames.HEAD, ModelPartBuilder.create().uv(60, 24).cuboid(-3.5f, -7.0f, -15.0f, 7.0f, 8.0f, 19.0f).uv(21, 0).cuboid(-3.5f, -21.0f, -15.0f, 7.0f, 14.0f, 7.0f).uv(50, 0).cuboid(-2.5f, -21.0f, -21.0f, 5.0f, 5.0f, 6.0f), ModelTransform.pivot(0.0f, -3.0f, -19.5f));
        modelPartData3.addChild(EntityModelPartNames.LEFT_EAR, ModelPartBuilder.create().uv(45, 0).cuboid(-0.5f, 0.5f, -1.0f, 3.0f, 1.0f, 2.0f), ModelTransform.pivot(2.5f, -21.0f, -9.5f));
        modelPartData3.addChild(EntityModelPartNames.RIGHT_EAR, ModelPartBuilder.create().uv(67, 0).cuboid(-2.5f, 0.5f, -1.0f, 3.0f, 1.0f, 2.0f), ModelTransform.pivot(-2.5f, -21.0f, -9.5f));
        modelPartData.addChild(EntityModelPartNames.LEFT_HIND_LEG, ModelPartBuilder.create().uv(58, 16).cuboid(-2.5f, 2.0f, -2.5f, 5.0f, 21.0f, 5.0f), ModelTransform.pivot(4.9f, 1.0f, 9.5f));
        modelPartData.addChild(EntityModelPartNames.RIGHT_HIND_LEG, ModelPartBuilder.create().uv(94, 16).cuboid(-2.5f, 2.0f, -2.5f, 5.0f, 21.0f, 5.0f), ModelTransform.pivot(-4.9f, 1.0f, 9.5f));
        modelPartData.addChild(EntityModelPartNames.LEFT_FRONT_LEG, ModelPartBuilder.create().uv(0, 0).cuboid(-2.5f, 2.0f, -2.5f, 5.0f, 21.0f, 5.0f), ModelTransform.pivot(4.9f, 1.0f, -10.5f));
        modelPartData.addChild(EntityModelPartNames.RIGHT_FRONT_LEG, ModelPartBuilder.create().uv(0, 26).cuboid(-2.5f, 2.0f, -2.5f, 5.0f, 21.0f, 5.0f), ModelTransform.pivot(-4.9f, 1.0f, -10.5f));
        return TexturedModelData.of(modelData, 128, 128);
    }

    @Override
    public void setAngles(@NotNull InfectedCamelEntity entity, float f, float g, float h, float i, float j) {
        this.getPart().traverse().forEach(ModelPart::resetTransform);
        this.setHeadAngles(i, j);
        this.animateMovement(CamelAnimations.WALKING, f, g, 2.0f, 2.5f);
        this.updateAnimation(entity.sittingTransitionAnimationState, CamelAnimations.SITTING_TRANSITION, h, 1.0f);
        this.updateAnimation(entity.sittingAnimationState, CamelAnimations.SITTING, h, 1.0f);
        this.updateAnimation(entity.standingTransitionAnimationState, CamelAnimations.STANDING_TRANSITION, h, 1.0f);
        this.updateAnimation(entity.idlingAnimationState, CamelAnimations.IDLING, h, 1.0f);
    }

    private void setHeadAngles(float headYaw, float headPitch) {
        headYaw = MathHelper.clamp(headYaw, -30.0f, 30.0f);
        headPitch = MathHelper.clamp(headPitch, -25.0f, 45.0f);
        this.head.yaw = headYaw * ((float)Math.PI / 180);
        this.head.pitch = headPitch * ((float)Math.PI / 180);
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumer vertices, int light, int overlay, float red, float green, float blue, float alpha) {
        if (this.child) {
            matrices.push();
            matrices.scale(0.45f, 0.45f, 0.45f);
            matrices.translate(0.0f, 1.834375f, 0.0f);
            this.getPart().render(matrices, vertices, light, overlay, red, green, blue, alpha);
            matrices.pop();
        } else {
            this.getPart().render(matrices, vertices, light, overlay, red, green, blue, alpha);
        }
    }

    @Override
    public ModelPart getPart() {
        return this.root;
    }
}
