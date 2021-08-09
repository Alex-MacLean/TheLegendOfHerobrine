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
    private final ModelRenderer leg0;
    private final ModelRenderer leg1;
    private final ModelRenderer leg2;
    private final ModelRenderer leg3;

    public InfectedLlamaEntityModel(float p_i47226_1_) {
        this.texWidth = 128;
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
        this.leg0 = new ModelRenderer(this, 29, 29);
        this.leg0.addBox(-2.0F, 0.0F, -2.0F, 4.0F, 14.0F, 4.0F, p_i47226_1_);
        this.leg0.setPos(-2.5F, 10.0F, 6.0F);
        this.leg1 = new ModelRenderer(this, 29, 29);
        this.leg1.addBox(-2.0F, 0.0F, -2.0F, 4.0F, 14.0F, 4.0F, p_i47226_1_);
        this.leg1.setPos(2.5F, 10.0F, 6.0F);
        this.leg2 = new ModelRenderer(this, 29, 29);
        this.leg2.addBox(-2.0F, 0.0F, -2.0F, 4.0F, 14.0F, 4.0F, p_i47226_1_);
        this.leg2.setPos(-2.5F, 10.0F, -4.0F);
        this.leg3 = new ModelRenderer(this, 29, 29);
        this.leg3.addBox(-2.0F, 0.0F, -2.0F, 4.0F, 14.0F, 4.0F, p_i47226_1_);
        this.leg3.setPos(2.5F, 10.0F, -4.0F);
        --this.leg0.x;
        ++this.leg1.x;
        this.leg0.z += 0.0F;
        this.leg1.z += 0.0F;
        --this.leg2.x;
        ++this.leg3.x;
        --this.leg2.z;
        --this.leg3.z;
    }

    @Override
    public void setupAnim(@NotNull T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.head.xRot = headPitch * ((float) Math.PI / 180F);
        this.head.yRot = netHeadYaw * ((float) Math.PI / 180F);
        this.body.xRot = ((float) Math.PI / 2F);
        this.leg0.xRot = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
        this.leg1.xRot = MathHelper.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.4F * limbSwingAmount;
        this.leg2.xRot = MathHelper.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.4F * limbSwingAmount;
        this.leg3.xRot = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
    }

    @Override
    public void renderToBuffer(@NotNull MatrixStack matrixStackIn, @NotNull IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
        ImmutableList.of(this.head, this.body, this.leg0, this.leg1, this.leg2, this.leg3).forEach((p_228279_8_) -> p_228279_8_.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha));
    }
}