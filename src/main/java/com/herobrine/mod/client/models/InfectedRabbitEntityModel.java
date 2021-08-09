package com.herobrine.mod.client.models;

import com.google.common.collect.ImmutableList;
import com.herobrine.mod.entities.InfectedRabbitEntity;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
public class InfectedRabbitEntityModel extends EntityModel<InfectedRabbitEntity> {
    private final ModelRenderer rabbitLeftFoot = new ModelRenderer(this, 26, 24);
    private final ModelRenderer rabbitRightFoot;
    private final ModelRenderer rabbitLeftThigh;
    private final ModelRenderer rabbitRightThigh;
    private final ModelRenderer rabbitBody;
    private final ModelRenderer rabbitLeftArm;
    private final ModelRenderer rabbitRightArm;
    private final ModelRenderer rabbitHead;
    private final ModelRenderer rabbitRightEar;
    private final ModelRenderer rabbitLeftEar;
    private final ModelRenderer rabbitTail;
    private final ModelRenderer rabbitNose;
    private float jumpRotation;

    public InfectedRabbitEntityModel() {
        this.rabbitLeftFoot.addBox(-1.0F, 5.5F, -3.7F, 2.0F, 1.0F, 7.0F);
        this.rabbitLeftFoot.setPos(3.0F, 17.5F, 3.7F);
        this.rabbitLeftFoot.mirror = true;
        this.setRotationOffset(this.rabbitLeftFoot, 0.0F, 0.0F);
        this.rabbitRightFoot = new ModelRenderer(this, 8, 24);
        this.rabbitRightFoot.addBox(-1.0F, 5.5F, -3.7F, 2.0F, 1.0F, 7.0F);
        this.rabbitRightFoot.setPos(-3.0F, 17.5F, 3.7F);
        this.rabbitRightFoot.mirror = true;
        this.setRotationOffset(this.rabbitRightFoot, 0.0F, 0.0F);
        this.rabbitLeftThigh = new ModelRenderer(this, 30, 15);
        this.rabbitLeftThigh.addBox(-1.0F, 0.0F, 0.0F, 2.0F, 4.0F, 5.0F);
        this.rabbitLeftThigh.setPos(3.0F, 17.5F, 3.7F);
        this.rabbitLeftThigh.mirror = true;
        this.setRotationOffset(this.rabbitLeftThigh, -0.34906584F, 0.0F);
        this.rabbitRightThigh = new ModelRenderer(this, 16, 15);
        this.rabbitRightThigh.addBox(-1.0F, 0.0F, 0.0F, 2.0F, 4.0F, 5.0F);
        this.rabbitRightThigh.setPos(-3.0F, 17.5F, 3.7F);
        this.rabbitRightThigh.mirror = true;
        this.setRotationOffset(this.rabbitRightThigh, -0.34906584F, 0.0F);
        this.rabbitBody = new ModelRenderer(this, 0, 0);
        this.rabbitBody.addBox(-3.0F, -2.0F, -10.0F, 6.0F, 5.0F, 10.0F);
        this.rabbitBody.setPos(0.0F, 19.0F, 8.0F);
        this.rabbitBody.mirror = true;
        this.setRotationOffset(this.rabbitBody, -0.34906584F, 0.0F);
        this.rabbitLeftArm = new ModelRenderer(this, 8, 15);
        this.rabbitLeftArm.addBox(-1.0F, 0.0F, -1.0F, 2.0F, 7.0F, 2.0F);
        this.rabbitLeftArm.setPos(3.0F, 17.0F, -1.0F);
        this.rabbitLeftArm.mirror = true;
        this.setRotationOffset(this.rabbitLeftArm, -0.17453292F, 0.0F);
        this.rabbitRightArm = new ModelRenderer(this, 0, 15);
        this.rabbitRightArm.addBox(-1.0F, 0.0F, -1.0F, 2.0F, 7.0F, 2.0F);
        this.rabbitRightArm.setPos(-3.0F, 17.0F, -1.0F);
        this.rabbitRightArm.mirror = true;
        this.setRotationOffset(this.rabbitRightArm, -0.17453292F, 0.0F);
        this.rabbitHead = new ModelRenderer(this, 32, 0);
        this.rabbitHead.addBox(-2.5F, -4.0F, -5.0F, 5.0F, 4.0F, 5.0F);
        this.rabbitHead.setPos(0.0F, 16.0F, -1.0F);
        this.rabbitHead.mirror = true;
        this.setRotationOffset(this.rabbitHead, 0.0F, 0.0F);
        this.rabbitRightEar = new ModelRenderer(this, 52, 0);
        this.rabbitRightEar.addBox(-2.5F, -9.0F, -1.0F, 2.0F, 5.0F, 1.0F);
        this.rabbitRightEar.setPos(0.0F, 16.0F, -1.0F);
        this.rabbitRightEar.mirror = true;
        this.setRotationOffset(this.rabbitRightEar, 0.0F, -0.2617994F);
        this.rabbitLeftEar = new ModelRenderer(this, 58, 0);
        this.rabbitLeftEar.addBox(0.5F, -9.0F, -1.0F, 2.0F, 5.0F, 1.0F);
        this.rabbitLeftEar.setPos(0.0F, 16.0F, -1.0F);
        this.rabbitLeftEar.mirror = true;
        this.setRotationOffset(this.rabbitLeftEar, 0.0F, 0.2617994F);
        this.rabbitTail = new ModelRenderer(this, 52, 6);
        this.rabbitTail.addBox(-1.5F, -1.5F, 0.0F, 3.0F, 3.0F, 2.0F);
        this.rabbitTail.setPos(0.0F, 20.0F, 7.0F);
        this.rabbitTail.mirror = true;
        this.setRotationOffset(this.rabbitTail, -0.3490659F, 0.0F);
        this.rabbitNose = new ModelRenderer(this, 32, 9);
        this.rabbitNose.addBox(-0.5F, -2.5F, -5.5F, 1.0F, 1.0F, 1.0F);
        this.rabbitNose.setPos(0.0F, 16.0F, -1.0F);
        this.rabbitNose.mirror = true;
        this.setRotationOffset(this.rabbitNose, 0.0F, 0.0F);
    }

    private void setRotationOffset(@NotNull ModelRenderer renderer, float x, float y) {
        renderer.xRot = x;
        renderer.yRot = y;
        renderer.zRot = (float) 0.0;
    }

    @Override
    public void renderToBuffer(@NotNull MatrixStack matrixStackIn, @NotNull IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
        matrixStackIn.pushPose();
        matrixStackIn.scale(0.6F, 0.6F, 0.6F);
        matrixStackIn.translate(0.0D, 1.0D, 0.0D);
        ImmutableList.of(this.rabbitLeftFoot, this.rabbitRightFoot, this.rabbitLeftThigh, this.rabbitRightThigh, this.rabbitBody, this.rabbitLeftArm, this.rabbitRightArm, this.rabbitHead, this.rabbitRightEar, this.rabbitLeftEar, this.rabbitTail, this.rabbitNose).forEach((p_228290_8_) -> p_228290_8_.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha));
        matrixStackIn.popPose();
    }

    @Override
    public void setupAnim(@NotNull InfectedRabbitEntity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        float f = ageInTicks - (float) entityIn.tickCount;
        this.rabbitNose.xRot = headPitch * ((float) Math.PI / 180F);
        this.rabbitHead.xRot = headPitch * ((float) Math.PI / 180F);
        this.rabbitRightEar.xRot = headPitch * ((float) Math.PI / 180F);
        this.rabbitLeftEar.xRot = headPitch * ((float) Math.PI / 180F);
        this.rabbitNose.yRot = netHeadYaw * ((float) Math.PI / 180F);
        this.rabbitHead.yRot = netHeadYaw * ((float) Math.PI / 180F);
        this.rabbitRightEar.yRot = this.rabbitNose.yRot - 0.2617994F;
        this.rabbitLeftEar.yRot = this.rabbitNose.yRot + 0.2617994F;
        this.jumpRotation = MathHelper.sin(entityIn.getJumpCompletion(f) * (float) Math.PI);
        this.rabbitLeftThigh.xRot = (this.jumpRotation * 50.0F - 21.0F) * ((float) Math.PI / 180F);
        this.rabbitRightThigh.xRot = (this.jumpRotation * 50.0F - 21.0F) * ((float) Math.PI / 180F);
        this.rabbitLeftFoot.xRot = this.jumpRotation * 50.0F * ((float) Math.PI / 180F);
        this.rabbitRightFoot.xRot = this.jumpRotation * 50.0F * ((float) Math.PI / 180F);
        this.rabbitLeftArm.xRot = (this.jumpRotation * -40.0F - 11.0F) * ((float) Math.PI / 180F);
        this.rabbitRightArm.xRot = (this.jumpRotation * -40.0F - 11.0F) * ((float) Math.PI / 180F);
    }

    @Override
    public void prepareMobModel(@NotNull InfectedRabbitEntity entityIn, float limbSwing, float limbSwingAmount, float partialTick) {
        super.prepareMobModel(entityIn, limbSwing, limbSwingAmount, partialTick);
        this.jumpRotation = MathHelper.sin(entityIn.getJumpCompletion(partialTick) * (float) Math.PI);
    }
}
