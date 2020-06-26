package com.herobrine.mod.client.models;

import com.herobrine.mod.entities.InfectedWolfEntity;
import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.entity.model.RendererModel;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
public class InfectedWolfEntityModel extends EntityModel<InfectedWolfEntity> {
    private final RendererModel head;
    private final RendererModel body;
    private final RendererModel legBackRight;
    private final RendererModel legBackLeft;
    private final RendererModel legFrontRight;
    private final RendererModel legFrontLeft;
    private final RendererModel tail;
    private final RendererModel mane;

    public InfectedWolfEntityModel() {
        this.head = new RendererModel(this, 0, 0);
        this.head.addBox(-2.0F, -3.0F, -2.0F, 6, 6, 4, 0.0F);
        this.head.setRotationPoint(-1.0F, 13.5F, -7.0F);
        this.body = new RendererModel(this, 18, 14);
        this.body.addBox(-3.0F, -2.0F, -3.0F, 6, 9, 6, 0.0F);
        this.body.setRotationPoint(0.0F, 14.0F, 2.0F);
        this.mane = new RendererModel(this, 21, 0);
        this.mane.addBox(-3.0F, -3.0F, -3.0F, 8, 6, 7, 0.0F);
        this.mane.setRotationPoint(-1.0F, 14.0F, 2.0F);
        this.legBackRight = new RendererModel(this, 0, 18);
        this.legBackRight.addBox(0.0F, 0.0F, -1.0F, 2, 8, 2, 0.0F);
        this.legBackRight.setRotationPoint(-2.5F, 16.0F, 7.0F);
        this.legBackLeft = new RendererModel(this, 0, 18);
        this.legBackLeft.addBox(0.0F, 0.0F, -1.0F, 2, 8, 2, 0.0F);
        this.legBackLeft.setRotationPoint(0.5F, 16.0F, 7.0F);
        this.legFrontRight = new RendererModel(this, 0, 18);
        this.legFrontRight.addBox(0.0F, 0.0F, -1.0F, 2, 8, 2, 0.0F);
        this.legFrontRight.setRotationPoint(-2.5F, 16.0F, -4.0F);
        this.legFrontLeft = new RendererModel(this, 0, 18);
        this.legFrontLeft.addBox(0.0F, 0.0F, -1.0F, 2, 8, 2, 0.0F);
        this.legFrontLeft.setRotationPoint(0.5F, 16.0F, -4.0F);
        this.tail = new RendererModel(this, 9, 18);
        this.tail.addBox(0.0F, 0.0F, -1.0F, 2, 8, 2, 0.0F);
        this.tail.setRotationPoint(-1.0F, 12.0F, 8.0F);
        this.head.setTextureOffset(16, 14).addBox(-2.0F, -5.0F, 0.0F, 2, 2, 1, 0.0F);
        this.head.setTextureOffset(16, 14).addBox(2.0F, -5.0F, 0.0F, 2, 2, 1, 0.0F);
        this.head.setTextureOffset(0, 10).addBox(-0.5F, 0.0F, -5.0F, 3, 3, 4, 0.0F);
    }

    @Override
    public void render(@NotNull InfectedWolfEntity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
        super.render(entityIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
        this.setRotationAngles(entityIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
        if (this.isChild) {
            GlStateManager.pushMatrix();
            GlStateManager.translatef(0.0F, 5.0F * scale, 2.0F * scale);
            this.head.renderWithRotation(scale);
            GlStateManager.popMatrix();
            GlStateManager.pushMatrix();
            GlStateManager.scalef(0.5F, 0.5F, 0.5F);
            GlStateManager.translatef(0.0F, 24.0F * scale, 0.0F);
            this.body.render(scale);
            this.legBackRight.render(scale);
            this.legBackLeft.render(scale);
            this.legFrontRight.render(scale);
            this.legFrontLeft.render(scale);
            this.tail.renderWithRotation(scale);
            this.mane.render(scale);
            GlStateManager.popMatrix();
        } else {
            this.head.renderWithRotation(scale);
            this.body.render(scale);
            this.legBackRight.render(scale);
            this.legBackLeft.render(scale);
            this.legFrontRight.render(scale);
            this.legFrontLeft.render(scale);
            this.tail.renderWithRotation(scale);
            this.mane.render(scale);
        }

    }

    @Override
    public void setLivingAnimations(@NotNull InfectedWolfEntity entityIn, float limbSwing, float limbSwingAmount, float partialTick) {
        this.tail.rotateAngleY = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
        this.body.setRotationPoint(0.0F, 14.0F, 2.0F);
        this.body.rotateAngleX = ((float)Math.PI / 2F);
        this.mane.setRotationPoint(-1.0F, 14.0F, -3.0F);
        this.mane.rotateAngleX = this.body.rotateAngleX;
        this.tail.setRotationPoint(-1.0F, 12.0F, 8.0F);
        this.legBackRight.setRotationPoint(-2.5F, 16.0F, 7.0F);
        this.legBackLeft.setRotationPoint(0.5F, 16.0F, 7.0F);
        this.legFrontRight.setRotationPoint(-2.5F, 16.0F, -4.0F);
        this.legFrontLeft.setRotationPoint(0.5F, 16.0F, -4.0F);
        this.legBackRight.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
        this.legBackLeft.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float)Math.PI) * 1.4F * limbSwingAmount;
        this.legFrontRight.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float)Math.PI) * 1.4F * limbSwingAmount;
        this.legFrontLeft.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
    }

    @Override
    public void setRotationAngles(@NotNull InfectedWolfEntity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor) {
        super.setRotationAngles(entityIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor);
        this.head.rotateAngleX = headPitch * ((float)Math.PI / 180F);
        this.head.rotateAngleY = netHeadYaw * ((float)Math.PI / 180F);
        this.tail.rotateAngleX = ageInTicks;
    }
}
