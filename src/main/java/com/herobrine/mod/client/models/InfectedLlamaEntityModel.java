package com.herobrine.mod.client.models;

import com.google.common.collect.ImmutableList;
import com.herobrine.mod.entities.InfectedLlamaEntity;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
public class InfectedLlamaEntityModel<T extends InfectedLlamaEntity> extends EntityModel<T> {
    private final ModelRenderer head;
    private final ModelRenderer body;
    private final ModelRenderer legBackRight;
    private final ModelRenderer legBackLeft;
    private final ModelRenderer legFrontRight;
    private final ModelRenderer legFrontLeft;
    private final ModelRenderer chest1;
    private final ModelRenderer chest2;

    public InfectedLlamaEntityModel(float p_i47226_1_) {
        this.texHeight = 128;
        this.texHeight = 64;
        this.head = new ModelRenderer(this, 0, 0);
        this.head.addBox(-2.0F, -14.0F, -10.0F, 4.0F, 4.0F, 9.0F, p_i47226_1_);
        this.head.setPos(0.0F, 7.0F, -6.0F);
        this.head.texOffs(0, 14).addBox(-4.0F, -16.0F, -6.0F, 8.0F, 18.0F, 6.0F, p_i47226_1_);
        this.head.texOffs(17, 0).addBox(-4.0F, -19.0F, -4.0F, 3.0F, 3.0F, 2.0F, p_i47226_1_);
        this.head.texOffs(17, 0).addBox(1.0F, -19.0F, -4.0F, 3.0F, 3.0F, 2.0F, p_i47226_1_);
        this.body = new ModelRenderer(this, 29, 0);
        this.body.addBox(-6.0F, -10.0F, -7.0F, 12.0F, 18.0F, 10.0F, p_i47226_1_);
        this.body.setPos(0.0F, 5.0F, 2.0F);
        this.chest1 = new ModelRenderer(this, 45, 28);
        this.chest1.addBox(-3.0F, 0.0F, 0.0F, 8.0F, 8.0F, 3.0F, p_i47226_1_);
        this.chest1.setPos(-8.5F, 3.0F, 3.0F);
        this.chest1.yRot = ((float) Math.PI / 2F);
        this.chest2 = new ModelRenderer(this, 45, 41);
        this.chest2.addBox(-3.0F, 0.0F, 0.0F, 8.0F, 8.0F, 3.0F, p_i47226_1_);
        this.chest2.setPos(5.5F, 3.0F, 3.0F);
        this.chest2.yRot = ((float) Math.PI / 2F);
        this.legBackRight = new ModelRenderer(this, 29, 29);
        this.legBackRight.addBox(-2.0F, 0.0F, -2.0F, 4.0F, 14.0F, 4.0F, p_i47226_1_);
        this.legBackRight.setPos(-2.5F, 10.0F, 6.0F);
        this.legBackLeft = new ModelRenderer(this, 29, 29);
        this.legBackLeft.addBox(-2.0F, 0.0F, -2.0F, 4.0F, 14.0F, 4.0F, p_i47226_1_);
        this.legBackLeft.setPos(2.5F, 10.0F, 6.0F);
        this.legFrontRight = new ModelRenderer(this, 29, 29);
        this.legFrontRight.addBox(-2.0F, 0.0F, -2.0F, 4.0F, 14.0F, 4.0F, p_i47226_1_);
        this.legFrontRight.setPos(-2.5F, 10.0F, -4.0F);
        this.legFrontLeft = new ModelRenderer(this, 29, 29);
        this.legFrontLeft.addBox(-2.0F, 0.0F, -2.0F, 4.0F, 14.0F, 4.0F, p_i47226_1_);
        this.legFrontLeft.setPos(2.5F, 10.0F, -4.0F);
        --this.legBackRight.xRot;
        ++this.legBackLeft.xRot;
        this.legBackRight.zRot += 0.0F;
        this.legBackLeft.zRot += 0.0F;
        --this.legFrontRight.xRot;
        ++this.legFrontLeft.xRot;
        --this.legFrontRight.zRot;
        --this.legFrontLeft.zRot;
    }

    @Override
    public void setupAnim(@NotNull T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.head.xRot = headPitch * ((float) Math.PI / 180F);
        this.head.yRot = netHeadYaw * ((float) Math.PI / 180F);
        this.body.xRot = ((float) Math.PI / 2F);
        this.legBackRight.xRot = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
        this.legBackLeft.xRot = MathHelper.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.4F * limbSwingAmount;
        this.legFrontRight.xRot = MathHelper.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.4F * limbSwingAmount;
        this.legFrontLeft.xRot = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
        this.chest1.visible = false;
        this.chest2.visible = false;
    }

    @Override
    public void renderToBuffer(@NotNull MatrixStack matrixStackIn, @NotNull IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
        if (this.young) {
            matrixStackIn.pushPose();
            matrixStackIn.scale(0.71428573F, 0.64935064F, 0.7936508F);
            matrixStackIn.translate(0.0D, 1.3125D, 0.22F);
            this.head.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
            matrixStackIn.popPose();
            matrixStackIn.pushPose();
            matrixStackIn.scale(0.625F, 0.45454544F, 0.45454544F);
            matrixStackIn.translate(0.0D, 2.0625D, 0.0D);
            this.body.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
            matrixStackIn.popPose();
            matrixStackIn.pushPose();
            matrixStackIn.scale(0.45454544F, 0.41322312F, 0.45454544F);
            matrixStackIn.translate(0.0D, 2.0625D, 0.0D);
            ImmutableList.of(this.legBackRight, this.legBackLeft, this.legFrontRight, this.legFrontLeft, this.chest1, this.chest2).forEach((p_228280_8_) -> p_228280_8_.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha));
            matrixStackIn.popPose();
        } else {
            ImmutableList.of(this.head, this.body, this.legBackRight, this.legBackLeft, this.legFrontRight, this.legFrontLeft, this.chest1, this.chest2).forEach((p_228279_8_) -> p_228279_8_.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha));
        }

    }
}