package com.herobrinemod.herobrine.client.entities.models;

import com.google.common.collect.ImmutableList;
import com.herobrinemod.herobrine.entities.InfectedDonkeyEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.AnimalModel;
import net.minecraft.client.render.entity.model.EntityModelPartNames;
import net.minecraft.util.math.MathHelper;
import org.jetbrains.annotations.NotNull;

@Environment(EnvType.CLIENT)
public class InfectedDonkeyEntityModel extends AnimalModel<InfectedDonkeyEntity> {
    protected static final String HEAD_PARTS = "head_parts";
    protected final ModelPart body;
    protected final ModelPart head;
    private final ModelPart rightHindLeg;
    private final ModelPart leftHindLeg;
    private final ModelPart rightFrontLeg;
    private final ModelPart leftFrontLeg;
    private final ModelPart tail;

    public InfectedDonkeyEntityModel(@NotNull ModelPart root) {
        super(true, 16.2f, 1.36f, 2.7272f, 2.0f, 20.0f);
        this.body = root.getChild(EntityModelPartNames.BODY);
        this.head = root.getChild(HEAD_PARTS);
        this.rightHindLeg = root.getChild(EntityModelPartNames.RIGHT_HIND_LEG);
        this.leftHindLeg = root.getChild(EntityModelPartNames.LEFT_HIND_LEG);
        this.rightFrontLeg = root.getChild(EntityModelPartNames.RIGHT_FRONT_LEG);
        this.leftFrontLeg = root.getChild(EntityModelPartNames.LEFT_FRONT_LEG);
        this.tail = this.body.getChild(EntityModelPartNames.TAIL);
    }

    public static @NotNull ModelData getModelData(Dilation dilation) {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        ModelPartData modelPartData2 = modelPartData.addChild(EntityModelPartNames.BODY, ModelPartBuilder.create().uv(0, 32).cuboid(-5.0f, -8.0f, -17.0f, 10.0f, 10.0f, 22.0f, new Dilation(0.05f)), ModelTransform.pivot(0.0f, 11.0f, 5.0f));
        ModelPartData modelPartData3 = modelPartData.addChild(HEAD_PARTS, ModelPartBuilder.create().uv(0, 35).cuboid(-2.05f, -6.0f, -2.0f, 4.0f, 12.0f, 7.0f), ModelTransform.of(0.0f, 4.0f, -12.0f, 0.5235988f, 0.0f, 0.0f));
        ModelPartData modelPartData4 = modelPartData3.addChild(EntityModelPartNames.HEAD, ModelPartBuilder.create().uv(0, 13).cuboid(-3.0f, -11.0f, -2.0f, 6.0f, 5.0f, 7.0f, dilation), ModelTransform.NONE);
        modelPartData3.addChild(EntityModelPartNames.MANE, ModelPartBuilder.create().uv(56, 36).cuboid(-1.0f, -11.0f, 5.01f, 2.0f, 16.0f, 2.0f, dilation), ModelTransform.NONE);
        modelPartData3.addChild("upper_mouth", ModelPartBuilder.create().uv(0, 25).cuboid(-2.0f, -11.0f, -7.0f, 4.0f, 5.0f, 5.0f, dilation), ModelTransform.NONE);
        modelPartData.addChild(EntityModelPartNames.LEFT_HIND_LEG, ModelPartBuilder.create().uv(48, 21).mirrored().cuboid(-3.0f, -1.01f, -1.0f, 4.0f, 11.0f, 4.0f, dilation), ModelTransform.pivot(4.0f, 14.0f, 7.0f));
        modelPartData.addChild(EntityModelPartNames.RIGHT_HIND_LEG, ModelPartBuilder.create().uv(48, 21).cuboid(-1.0f, -1.01f, -1.0f, 4.0f, 11.0f, 4.0f, dilation), ModelTransform.pivot(-4.0f, 14.0f, 7.0f));
        modelPartData.addChild(EntityModelPartNames.LEFT_FRONT_LEG, ModelPartBuilder.create().uv(48, 21).mirrored().cuboid(-3.0f, -1.01f, -1.9f, 4.0f, 11.0f, 4.0f, dilation), ModelTransform.pivot(4.0f, 14.0f, -12.0f));
        modelPartData.addChild(EntityModelPartNames.RIGHT_FRONT_LEG, ModelPartBuilder.create().uv(48, 21).cuboid(-1.0f, -1.01f, -1.9f, 4.0f, 11.0f, 4.0f, dilation), ModelTransform.pivot(-4.0f, 14.0f, -12.0f));
        modelPartData2.addChild(EntityModelPartNames.TAIL, ModelPartBuilder.create().uv(42, 36).cuboid(-1.5f, 0.0f, 0.0f, 3.0f, 14.0f, 4.0f, dilation), ModelTransform.of(0.0f, -5.0f, 2.0f, 0.5235988f, 0.0f, 0.0f));
        modelPartData4.addChild(EntityModelPartNames.LEFT_EAR, ModelPartBuilder.create().uv(19, 16).cuboid(0.55f, -13.0f, 4.0f, 2.0f, 3.0f, 1.0f, new Dilation(-0.001f)), ModelTransform.NONE);
        modelPartData4.addChild(EntityModelPartNames.RIGHT_EAR, ModelPartBuilder.create().uv(19, 16).cuboid(-2.55f, -13.0f, 4.0f, 2.0f, 3.0f, 1.0f, new Dilation(-0.001f)), ModelTransform.NONE);
        return modelData;
    }

    public static @NotNull TexturedModelData getTexturedModelData() {
        ModelData modelData = getModelData(Dilation.NONE);
        ModelPartData modelPartData = modelData.getRoot();
        ModelPartData modelPartData3 = modelPartData.getChild("head_parts").getChild(EntityModelPartNames.HEAD);
        ModelPartBuilder modelPartBuilder2 = ModelPartBuilder.create().uv(0, 12).cuboid(-1.0f, -7.0f, 0.0f, 2.0f, 7.0f, 1.0f);
        modelPartData3.addChild(EntityModelPartNames.LEFT_EAR, modelPartBuilder2, ModelTransform.of(1.25f, -10.0f, 4.0f, 0.2617994f, 0.0f, 0.2617994f));
        modelPartData3.addChild(EntityModelPartNames.RIGHT_EAR, modelPartBuilder2, ModelTransform.of(-1.25f, -10.0f, 4.0f, 0.2617994f, 0.0f, -0.2617994f));
        return TexturedModelData.of(modelData, 64, 64);
    }

    @Override
    public void setAngles(InfectedDonkeyEntity entity, float f, float g, float h, float i, float j) {
        this.body.pivotY = 11.0f;
    }

    @Override
    public Iterable<ModelPart> getHeadParts() {
        return ImmutableList.of(this.head);
    }

    @Override
    protected Iterable<ModelPart> getBodyParts() {
        return ImmutableList.of(this.body, this.rightHindLeg, this.leftHindLeg, this.rightFrontLeg, this.leftFrontLeg);
    }

    @Override
    public void animateModel(InfectedDonkeyEntity entity, float f, float g, float h) {
        super.animateModel(entity, f, g, h);
        float i = MathHelper.lerpAngleDegrees(h, entity.prevBodyYaw, entity.bodyYaw);
        float j = MathHelper.lerpAngleDegrees(h, entity.prevHeadYaw, entity.headYaw);
        float k = MathHelper.lerp(h, entity.prevPitch, entity.getPitch());
        float l = j - i;
        float m = k * ((float)Math.PI / 180);
        if (l > 20.0f) {
            l = 20.0f;
        }

        if (l < -20.0f) {
            l = -20.0f;
        }

        if (g > 0.2f) {
            m += MathHelper.cos(f * 0.8f) * 0.15f * g;
        }

        float n = entity.getEatingGrassAnimationProgress(h);
        float o = entity.getAngryAnimationProgress(h);
        float p = 1.0f - o;
        float q = entity.getEatingAnimationProgress(h);
        boolean bl = entity.getTailWagTicks() != 0;
        float r = (float) entity.age + h;
        this.head.pivotY = 4.0f;
        this.head.pivotZ = -12.0f;
        this.body.pitch = 0.0f;
        this.head.pitch = 0.5235988f + m;
        this.head.yaw = l * ((float)Math.PI / 180);
        float s = entity.isTouchingWater() ? 0.2f : 1.0f;
        float t = MathHelper.cos(s * f * 0.6662f + (float)Math.PI);
        float u = t * 0.8f * g;
        float v = (1.0f - Math.max(o, n)) * (0.5235988f + m + q * MathHelper.sin(r) * 0.05f);
        this.head.pitch = o * (0.2617994f + m) + n * (2.1816616f + MathHelper.sin(r) * 0.05f) + v;
        this.head.yaw = o * l * ((float)Math.PI / 180) + (1.0f - Math.max(o, n)) * this.head.yaw;
        this.head.pivotY = o * -4.0f + n * 11.0f + (1.0f - Math.max(o, n)) * this.head.pivotY;
        this.head.pivotZ = o * -4.0f + n * -12.0f + (1.0f - Math.max(o, n)) * this.head.pivotZ;
        this.body.pitch = o * -0.7853982f + p * this.body.pitch;
        float w = 0.2617994f * o;
        float x = MathHelper.cos(r * 0.6f + (float)Math.PI);
        this.leftFrontLeg.pivotY = 2.0f * o + 14.0f * p;
        this.leftFrontLeg.pivotZ = -6.0f * o - 10.0f * p;
        this.rightFrontLeg.pivotY = this.leftFrontLeg.pivotY;
        this.rightFrontLeg.pivotZ = this.leftFrontLeg.pivotZ;
        float y = (-1.0471976f + x) * o + u * p;
        float z = (-1.0471976f - x) * o - u * p;
        this.leftHindLeg.pitch = w - t * 0.5f * g * p;
        this.rightHindLeg.pitch = w + t * 0.5f * g * p;
        this.leftFrontLeg.pitch = y;
        this.rightFrontLeg.pitch = z;
        this.tail.pitch = 0.5235988f + g * 0.75f;
        this.tail.pivotY = -5.0f + g;
        this.tail.pivotZ = 2.0f + g * 2.0f;
        this.tail.yaw = bl ? MathHelper.cos(r * 0.7f) : 0.0f;
        this.body.pivotY = 10.8f;
    }
}
