package com.herobrinemod.herobrine.client.entities.models;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;
import com.herobrinemod.herobrine.entities.SurvivorEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.*;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.entity.model.EntityModelPartNames;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Arm;
import org.jetbrains.annotations.NotNull;

@Environment(EnvType.CLIENT)
public class SurvivorEntityModel extends BipedEntityModel<SurvivorEntity> {
    public static final String LEFT_SLEEVE = "left_sleeve";
    public static final String RIGHT_SLEEVE = "right_sleeve";
    public static final String LEFT_PANTS = "left_pants";
    public static final String RIGHT_PANTS = "right_pants";
    public static final String LEFT_SLEEVE_SLIM = "left_sleeve_slim";
    public static final String RIGHT_SLEEVE_SLIM = "right_sleeve_slim";
    public static final String LEFT_ARM_SLIM = "left_arm_slim";
    public static final String RIGHT_ARM_SLIM = "right_arm_slim";
    public final ModelPart leftSleeve;
    public final ModelPart rightSleeve;
    public final ModelPart leftPants;
    public final ModelPart rightPants;
    public final ModelPart jacket;
    private final ModelPart leftArmSlim;
    private final ModelPart rightArmSlim;
    private final ModelPart leftSleeveSlim;
    private final ModelPart rightSleeveSlim;

    public SurvivorEntityModel(ModelPart root) {
        super(root, RenderLayer::getEntityTranslucent);
        this.leftSleeve = root.getChild(LEFT_SLEEVE);
        this.rightSleeve = root.getChild(RIGHT_SLEEVE);
        this.leftPants = root.getChild(LEFT_PANTS);
        this.rightPants = root.getChild(RIGHT_PANTS);
        this.jacket = root.getChild(EntityModelPartNames.JACKET);
        this.leftArmSlim = root.getChild(LEFT_ARM_SLIM);
        this.rightArmSlim = root.getChild(RIGHT_ARM_SLIM);
        this.leftSleeveSlim = root.getChild(LEFT_SLEEVE_SLIM);
        this.rightSleeveSlim = root.getChild(RIGHT_SLEEVE_SLIM);
    }

    public static @NotNull ModelData getModelData() {
        ModelData modelData = BipedEntityModel.getModelData(Dilation.NONE, 0.0f);
        ModelPartData modelPartData = modelData.getRoot();
        modelPartData.addChild(LEFT_ARM_SLIM, ModelPartBuilder.create().uv(32, 48).cuboid(-1.0f, -2.0f, -2.0f, 3.0f, 12.0f, 4.0f, Dilation.NONE), ModelTransform.pivot(5.0f, 2.5f, 0.0f));
        modelPartData.addChild(RIGHT_ARM_SLIM, ModelPartBuilder.create().uv(40, 16).cuboid(-2.0f, -2.0f, -2.0f, 3.0f, 12.0f, 4.0f, Dilation.NONE), ModelTransform.pivot(-5.0f, 2.5f, 0.0f));
        modelPartData.addChild(LEFT_SLEEVE_SLIM, ModelPartBuilder.create().uv(48, 48).cuboid(-1.0f, -2.0f, -2.0f, 3.0f, 12.0f, 4.0f, Dilation.NONE.add(0.25f)), ModelTransform.pivot(5.0f, 2.5f, 0.0f));
        modelPartData.addChild(RIGHT_SLEEVE_SLIM, ModelPartBuilder.create().uv(40, 32).cuboid(-2.0f, -2.0f, -2.0f, 3.0f, 12.0f, 4.0f, Dilation.NONE.add(0.25f)), ModelTransform.pivot(-5.0f, 2.5f, 0.0f));
        modelPartData.addChild(EntityModelPartNames.LEFT_ARM, ModelPartBuilder.create().uv(32, 48).cuboid(-1.0f, -2.0f, -2.0f, 4.0f, 12.0f, 4.0f, Dilation.NONE), ModelTransform.pivot(5.0f, 2.0f, 0.0f));
        modelPartData.addChild(LEFT_SLEEVE, ModelPartBuilder.create().uv(48, 48).cuboid(-1.0f, -2.0f, -2.0f, 4.0f, 12.0f, 4.0f, Dilation.NONE.add(0.25f)), ModelTransform.pivot(5.0f, 2.0f, 0.0f));
        modelPartData.addChild(RIGHT_SLEEVE, ModelPartBuilder.create().uv(40, 32).cuboid(-3.0f, -2.0f, -2.0f, 4.0f, 12.0f, 4.0f, Dilation.NONE.add(0.25f)), ModelTransform.pivot(-5.0f, 2.0f, 0.0f));
        modelPartData.addChild(EntityModelPartNames.LEFT_LEG, ModelPartBuilder.create().uv(16, 48).cuboid(-2.0f, 0.0f, -2.0f, 4.0f, 12.0f, 4.0f, Dilation.NONE), ModelTransform.pivot(1.9f, 12.0f, 0.0f));
        modelPartData.addChild(LEFT_PANTS, ModelPartBuilder.create().uv(0, 48).cuboid(-2.0f, 0.0f, -2.0f, 4.0f, 12.0f, 4.0f, Dilation.NONE.add(0.25f)), ModelTransform.pivot(1.9f, 12.0f, 0.0f));
        modelPartData.addChild(RIGHT_PANTS, ModelPartBuilder.create().uv(0, 32).cuboid(-2.0f, 0.0f, -2.0f, 4.0f, 12.0f, 4.0f, Dilation.NONE.add(0.25f)), ModelTransform.pivot(-1.9f, 12.0f, 0.0f));
        modelPartData.addChild(EntityModelPartNames.JACKET, ModelPartBuilder.create().uv(16, 32).cuboid(-4.0f, 0.0f, -2.0f, 8.0f, 12.0f, 4.0f, Dilation.NONE.add(0.25f)), ModelTransform.NONE);
        return modelData;
    }

    public static @NotNull TexturedModelData getTexturedModelData() {
        return TexturedModelData.of(getModelData(), 64, 64);
    }

    // If I put this anywhere else, even if I access the same ModelData the game will crash. I don't know why and I hate it
    public static @NotNull TexturedModelData getTexturedArmorModelData(@NotNull Dilation dilation) {
        ModelData modelData = getModelData();
        ModelPartData modelPartData = modelData.getRoot();
        modelPartData.addChild(EntityModelPartNames.HEAD, ModelPartBuilder.create().uv(0, 0).cuboid(-4.0f, -8.0f, -4.0f, 8.0f, 8.0f, 8.0f, dilation), ModelTransform.pivot(0.0f, 0.0f, 0.0f));
        modelPartData.addChild(EntityModelPartNames.BODY, ModelPartBuilder.create().uv(16, 16).cuboid(-4.0f, 0.0f, -2.0f, 8.0f, 12.0f, 4.0f, dilation), ModelTransform.pivot(0.0f, 0.0f, 0.0f));
        modelPartData.addChild(EntityModelPartNames.RIGHT_ARM, ModelPartBuilder.create().uv(40, 16).cuboid(-3.0f, -2.0f, -2.0f, 4.0f, 12.0f, 4.0f, dilation), ModelTransform.pivot(-5.0f, 2.0f, 0.0f));
        modelPartData.addChild(EntityModelPartNames.LEFT_ARM, ModelPartBuilder.create().uv(40, 16).mirrored().cuboid(-1.0f, -2.0f, -2.0f, 4.0f, 12.0f, 4.0f, dilation), ModelTransform.pivot(5.0f, 2.0f, 0.0f));
        modelPartData.addChild(LEFT_ARM_SLIM, ModelPartBuilder.create().uv(32, 48).cuboid(-1.0f, -2.0f, -2.0f, 3.0f, 12.0f, 4.0f, dilation), ModelTransform.pivot(5.0f, 2.5f, 0.0f));
        modelPartData.addChild(RIGHT_ARM_SLIM, ModelPartBuilder.create().uv(40, 16).cuboid(-2.0f, -2.0f, -2.0f, 3.0f, 12.0f, 4.0f, dilation), ModelTransform.pivot(-5.0f, 2.5f, 0.0f));
        modelPartData.addChild(EntityModelPartNames.RIGHT_LEG, ModelPartBuilder.create().uv(0, 16).cuboid(-2.0f, 0.0f, -2.0f, 4.0f, 12.0f, 4.0f, dilation), ModelTransform.pivot(-1.9f, 12.0f, 0.0f));
        modelPartData.addChild(EntityModelPartNames.LEFT_LEG, ModelPartBuilder.create().uv(0, 16).mirrored().cuboid(-2.0f, 0.0f, -2.0f, 4.0f, 12.0f, 4.0f, dilation), ModelTransform.pivot(1.9f, 12.0f, 0.0f));
        return TexturedModelData.of(modelData, 64, 32);
    }

    @Override
    protected Iterable<ModelPart> getBodyParts() {
        return Iterables.concat(super.getBodyParts(), ImmutableList.of(this.leftPants, this.rightPants, this.leftSleeve, this.leftSleeveSlim, this.leftArmSlim, this.rightSleeve, this.rightSleeveSlim, this.rightArmSlim, this.jacket));
    }

    @Override
    public void setAngles(SurvivorEntity entity, float f, float g, float h, float i, float j) {
        super.setAngles(entity, f, g, h, i, j);
        this.leftPants.copyTransform(this.leftLeg);
        this.rightPants.copyTransform(this.rightLeg);
        this.leftSleeve.copyTransform(this.leftArm);
        this.rightSleeve.copyTransform(this.rightArm);
        this.leftArmSlim.copyTransform(this.leftArm);
        this.rightArmSlim.copyTransform(this.rightArm);
        this.leftSleeveSlim.copyTransform(this.leftArm);
        this.rightSleeveSlim.copyTransform(this.rightArm);
        this.jacket.copyTransform(this.body);
        // Choose whether to use slim arms. This is the only method that passes the entity to the method. Because of this the model is overly complicated. Why do this Mojang?
        // For some reason the armor's sleeves aren't affected by this even though it accesses the same model parts causing slim arm survivors to have a slightly too large armor sleeve model. Don't ask me why.
        if(entity.getSmallArms()) {
            this.leftArmSlim.visible = true;
            this.rightArmSlim.visible = true;
            this.leftSleeveSlim.visible = true;
            this.rightSleeveSlim.visible = true;
            this.leftArm.visible = false;
            this.rightArm.visible = false;
            this.leftSleeve.visible = false;
            this.rightSleeve.visible = false;
        } else {
            this.leftArmSlim.visible = false;
            this.rightArmSlim.visible = false;
            this.leftSleeveSlim.visible = false;
            this.rightSleeveSlim.visible = false;
            this.leftArm.visible = true;
            this.rightArm.visible = true;
            this.leftSleeve.visible = true;
            this.rightSleeve.visible = true;
        }
    }

    @Override
    public void setVisible(boolean visible) {
        super.setVisible(visible);
        this.leftSleeve.visible = visible;
        this.rightSleeve.visible = visible;
        this.leftPants.visible = visible;
        this.rightPants.visible = visible;
        this.jacket.visible = visible;
        this.leftArmSlim.visible = visible;
        this.rightArmSlim.visible = visible;
        this.leftSleeveSlim.visible = visible;
        this.rightSleeveSlim.visible = visible;
    }

    @Override
    public void setArmAngle(Arm arm, MatrixStack matrices) {
        ModelPart modelPart = this.getArm(arm);
        // Detect if model is using slim arms by checking if the left slim arm element is visible.
        if (this.leftArmSlim.visible) {
            float f = 0.5f * (float)(arm == Arm.RIGHT ? 1 : -1);
            modelPart.pivotX += f;
            modelPart.rotate(matrices);
            modelPart.pivotX -= f;
        } else {
            modelPart.rotate(matrices);
        }
    }
}
