package com.herobrine.mod.client.models;

import com.herobrine.mod.entities.InfectedLlamaEntity;
import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.renderer.entity.model.QuadrupedModel;
import net.minecraft.client.renderer.entity.model.RendererModel;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
public class InfectedLlamaEntityModel<T extends InfectedLlamaEntity> extends QuadrupedModel<T> {
    private final RendererModel chest1;
    private final RendererModel chest2;

    public InfectedLlamaEntityModel(float p_i47226_1_) {
        super(15, p_i47226_1_);
        this.textureWidth = 128;
        this.textureHeight = 64;
        this.headModel = new RendererModel(this, 0, 0);
        this.headModel.addBox(-2.0F, -14.0F, -10.0F, 4, 4, 9, p_i47226_1_);
        this.headModel.setRotationPoint(0.0F, 7.0F, -6.0F);
        this.headModel.setTextureOffset(0, 14).addBox(-4.0F, -16.0F, -6.0F, 8, 18, 6, p_i47226_1_);
        this.headModel.setTextureOffset(17, 0).addBox(-4.0F, -19.0F, -4.0F, 3, 3, 2, p_i47226_1_);
        this.headModel.setTextureOffset(17, 0).addBox(1.0F, -19.0F, -4.0F, 3, 3, 2, p_i47226_1_);
        this.body = new RendererModel(this, 29, 0);
        this.body.addBox(-6.0F, -10.0F, -7.0F, 12, 18, 10, p_i47226_1_);
        this.body.setRotationPoint(0.0F, 5.0F, 2.0F);
        this.chest1 = new RendererModel(this, 45, 28);
        this.chest1.addBox(-3.0F, 0.0F, 0.0F, 8, 8, 3, p_i47226_1_);
        this.chest1.setRotationPoint(-8.5F, 3.0F, 3.0F);
        this.chest1.rotateAngleY = ((float)Math.PI / 2F);
        this.chest2 = new RendererModel(this, 45, 41);
        this.chest2.addBox(-3.0F, 0.0F, 0.0F, 8, 8, 3, p_i47226_1_);
        this.chest2.setRotationPoint(5.5F, 3.0F, 3.0F);
        this.chest2.rotateAngleY = ((float)Math.PI / 2F);
        this.legBackRight = new RendererModel(this, 29, 29);
        this.legBackRight.addBox(-2.0F, 0.0F, -2.0F, 4, 14, 4, p_i47226_1_);
        this.legBackRight.setRotationPoint(-2.5F, 10.0F, 6.0F);
        this.legBackLeft = new RendererModel(this, 29, 29);
        this.legBackLeft.addBox(-2.0F, 0.0F, -2.0F, 4, 14, 4, p_i47226_1_);
        this.legBackLeft.setRotationPoint(2.5F, 10.0F, 6.0F);
        this.legFrontRight = new RendererModel(this, 29, 29);
        this.legFrontRight.addBox(-2.0F, 0.0F, -2.0F, 4, 14, 4, p_i47226_1_);
        this.legFrontRight.setRotationPoint(-2.5F, 10.0F, -4.0F);
        this.legFrontLeft = new RendererModel(this, 29, 29);
        this.legFrontLeft.addBox(-2.0F, 0.0F, -2.0F, 4, 14, 4, p_i47226_1_);
        this.legFrontLeft.setRotationPoint(2.5F, 10.0F, -4.0F);
        --this.legBackRight.rotationPointX;
        ++this.legBackLeft.rotationPointX;
        this.legBackRight.rotationPointZ += 0.0F;
        this.legBackLeft.rotationPointZ += 0.0F;
        --this.legFrontRight.rotationPointX;
        ++this.legFrontLeft.rotationPointX;
        --this.legFrontRight.rotationPointZ;
        --this.legFrontLeft.rotationPointZ;
        this.childZOffset += 2.0F;
    }

    @Override
    public void render(@NotNull T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
        boolean flag = !entityIn.isChild() && entityIn.hasChest();
        this.setRotationAngles(entityIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
        if (this.isChild) {
            GlStateManager.pushMatrix();
            GlStateManager.translatef(0.0F, this.childYOffset * scale, this.childZOffset * scale);
            GlStateManager.popMatrix();
            GlStateManager.pushMatrix();
            GlStateManager.scalef(0.71428573F, 0.64935064F, 0.7936508F);
            GlStateManager.translatef(0.0F, 21.0F * scale, 0.22F);
            this.headModel.render(scale);
            GlStateManager.popMatrix();
            GlStateManager.pushMatrix();
            GlStateManager.scalef(0.625F, 0.45454544F, 0.45454544F);
            GlStateManager.translatef(0.0F, 33.0F * scale, 0.0F);
            this.body.render(scale);
            GlStateManager.popMatrix();
            GlStateManager.pushMatrix();
            GlStateManager.scalef(0.45454544F, 0.41322312F, 0.45454544F);
            GlStateManager.translatef(0.0F, 33.0F * scale, 0.0F);
            this.legBackRight.render(scale);
            this.legBackLeft.render(scale);
            this.legFrontRight.render(scale);
            this.legFrontLeft.render(scale);
            GlStateManager.popMatrix();
        } else {
            this.headModel.render(scale);
            this.body.render(scale);
            this.legBackRight.render(scale);
            this.legBackLeft.render(scale);
            this.legFrontRight.render(scale);
            this.legFrontLeft.render(scale);
        }
        if (flag) {
            this.chest1.render(scale);
            this.chest2.render(scale);
        }
    }
}