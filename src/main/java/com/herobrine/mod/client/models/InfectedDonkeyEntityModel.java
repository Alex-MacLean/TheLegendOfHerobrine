package com.herobrine.mod.client.models;

import com.herobrine.mod.entities.InfectedDonkeyEntity;
import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.entity.model.RendererModel;
import net.minecraft.util.math.MathHelper;
import org.jetbrains.annotations.NotNull;

public class InfectedDonkeyEntityModel extends EntityModel<InfectedDonkeyEntity> {
    protected final RendererModel field_217127_a;
    protected final RendererModel field_217128_b;
    private final RendererModel field_217129_f;
    private final RendererModel field_217130_g;
    private final RendererModel field_217131_h;
    private final RendererModel field_217132_i;
    private final RendererModel field_217133_j;
    private final RendererModel[] field_217134_k;
    private final RendererModel[] field_217135_l;

    public InfectedDonkeyEntityModel(float p_i51065_1_) {
        this.textureWidth = 64;
        this.textureHeight = 64;
        this.field_217127_a = new RendererModel(this, 0, 32);
        this.field_217127_a.addBox(-5.0F, -8.0F, -17.0F, 10, 10, 22, 0.05F);
        this.field_217127_a.setRotationPoint(0.0F, 11.0F, 5.0F);
        this.field_217128_b = new RendererModel(this, 0, 35);
        this.field_217128_b.addBox(-2.05F, -6.0F, -2.0F, 4, 12, 7);
        this.field_217128_b.rotateAngleX = ((float)Math.PI / 6F);
        RendererModel renderermodel = new RendererModel(this, 0, 13);
        renderermodel.addBox(-3.0F, -11.0F, -2.0F, 6, 5, 7, p_i51065_1_);
        RendererModel renderermodel1 = new RendererModel(this, 56, 36);
        renderermodel1.addBox(-1.0F, -11.0F, 5.01F, 2, 16, 2, p_i51065_1_);
        RendererModel renderermodel2 = new RendererModel(this, 0, 25);
        renderermodel2.addBox(-2.0F, -11.0F, -7.0F, 4, 5, 5, p_i51065_1_);
        this.field_217128_b.addChild(renderermodel);
        this.field_217128_b.addChild(renderermodel1);
        this.field_217128_b.addChild(renderermodel2);
        this.func_199047_a(this.field_217128_b);
        this.field_217129_f = new RendererModel(this, 48, 21);
        this.field_217129_f.mirror = true;
        this.field_217129_f.addBox(-3.0F, -1.01F, -1.0F, 4, 11, 4, p_i51065_1_);
        this.field_217129_f.setRotationPoint(4.0F, 14.0F, 7.0F);
        this.field_217130_g = new RendererModel(this, 48, 21);
        this.field_217130_g.addBox(-1.0F, -1.01F, -1.0F, 4, 11, 4, p_i51065_1_);
        this.field_217130_g.setRotationPoint(-4.0F, 14.0F, 7.0F);
        this.field_217131_h = new RendererModel(this, 48, 21);
        this.field_217131_h.mirror = true;
        this.field_217131_h.addBox(-3.0F, -1.01F, -1.9F, 4, 11, 4, p_i51065_1_);
        this.field_217131_h.setRotationPoint(4.0F, 6.0F, -12.0F);
        this.field_217132_i = new RendererModel(this, 48, 21);
        this.field_217132_i.addBox(-1.0F, -1.01F, -1.9F, 4, 11, 4, p_i51065_1_);
        this.field_217132_i.setRotationPoint(-4.0F, 6.0F, -12.0F);
        this.field_217133_j = new RendererModel(this, 42, 36);
        this.field_217133_j.addBox(-1.5F, 0.0F, 0.0F, 3, 14, 4, p_i51065_1_);
        this.field_217133_j.setRotationPoint(0.0F, -5.0F, 2.0F);
        this.field_217133_j.rotateAngleX = ((float)Math.PI / 6F);
        this.field_217127_a.addChild(this.field_217133_j);
        RendererModel renderermodel3 = new RendererModel(this, 26, 0);
        renderermodel3.addBox(-5.0F, -8.0F, -9.0F, 10, 9, 9, 0.5F);
        this.field_217127_a.addChild(renderermodel3);
        RendererModel renderermodel4 = new RendererModel(this, 29, 5);
        renderermodel4.addBox(2.0F, -9.0F, -6.0F, 1, 2, 2, p_i51065_1_);
        this.field_217128_b.addChild(renderermodel4);
        RendererModel renderermodel5 = new RendererModel(this, 29, 5);
        renderermodel5.addBox(-3.0F, -9.0F, -6.0F, 1, 2, 2, p_i51065_1_);
        this.field_217128_b.addChild(renderermodel5);
        RendererModel renderermodel6 = new RendererModel(this, 32, 2);
        renderermodel6.addBox(3.1F, -6.0F, -8.0F, 0, 3, 16, p_i51065_1_);
        renderermodel6.rotateAngleX = (-(float)Math.PI / 6F);
        this.field_217128_b.addChild(renderermodel6);
        RendererModel renderermodel7 = new RendererModel(this, 32, 2);
        renderermodel7.addBox(-3.1F, -6.0F, -8.0F, 0, 3, 16, p_i51065_1_);
        renderermodel7.rotateAngleX = (-(float)Math.PI / 6F);
        this.field_217128_b.addChild(renderermodel7);
        RendererModel renderermodel8 = new RendererModel(this, 1, 1);
        renderermodel8.addBox(-3.0F, -11.0F, -1.9F, 6, 5, 6, 0.2F);
        this.field_217128_b.addChild(renderermodel8);
        RendererModel renderermodel9 = new RendererModel(this, 19, 0);
        renderermodel9.addBox(-2.0F, -11.0F, -4.0F, 4, 5, 2, 0.2F);
        this.field_217128_b.addChild(renderermodel9);
        this.field_217134_k = new RendererModel[]{renderermodel3, renderermodel4, renderermodel5, renderermodel8, renderermodel9};
        this.field_217135_l = new RendererModel[]{renderermodel6, renderermodel7};
    }

    protected void func_199047_a(@NotNull RendererModel p_199047_1_) {
        RendererModel renderermodel = new RendererModel(this, 19, 16);
        renderermodel.addBox(0.55F, -13.0F, 4.0F, 2, 3, 1, -0.001F);
        RendererModel renderermodel1 = new RendererModel(this, 19, 16);
        renderermodel1.addBox(-2.55F, -13.0F, 4.0F, 2, 3, 1, -0.001F);
        p_199047_1_.addChild(renderermodel);
        p_199047_1_.addChild(renderermodel1);
    }

    @Override
    public void render(@NotNull InfectedDonkeyEntity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
        boolean flag = entityIn.isChild();
        float f = entityIn.getRenderScale();
        boolean flag1 = entityIn.isHorseSaddled();
        boolean flag2 = entityIn.isBeingRidden();

        for(RendererModel renderermodel : this.field_217134_k) {
            renderermodel.showModel = flag1;
        }

        for(RendererModel renderermodel1 : this.field_217135_l) {
            renderermodel1.showModel = flag2 && flag1;
        }

        if (flag) {
            GlStateManager.pushMatrix();
            GlStateManager.scalef(f, 0.5F + f * 0.5F, f);
            GlStateManager.translatef(0.0F, 0.95F * (1.0F - f), 0.0F);
        }

        this.field_217129_f.render(scale);
        this.field_217130_g.render(scale);
        this.field_217131_h.render(scale);
        this.field_217132_i.render(scale);
        if (flag) {
            GlStateManager.popMatrix();
            GlStateManager.pushMatrix();
            GlStateManager.scalef(f, f, f);
            GlStateManager.translatef(0.0F, 2.3F * (1.0F - f), 0.0F);
        }

        this.field_217127_a.render(scale);
        if (flag) {
            GlStateManager.popMatrix();
            GlStateManager.pushMatrix();
            float f1 = f + 0.1F * f;
            GlStateManager.scalef(f1, f1, f1);
            GlStateManager.translatef(0.0F, 2.25F * (1.0F - f1), 0.1F * (1.4F - f1));
        }

        this.field_217128_b.render(scale);
        if (flag) {
            GlStateManager.popMatrix();
        }

    }

    @Override
    public void setLivingAnimations(@NotNull InfectedDonkeyEntity entityIn, float limbSwing, float limbSwingAmount, float partialTick) {
        super.setLivingAnimations(entityIn, limbSwing, limbSwingAmount, partialTick);
        float f = this.func_217126_a(entityIn.prevRenderYawOffset, entityIn.renderYawOffset, partialTick);
        float f1 = this.func_217126_a(entityIn.prevRotationYawHead, entityIn.rotationYawHead, partialTick);
        float f2 = MathHelper.lerp(partialTick, entityIn.prevRotationPitch, entityIn.rotationPitch);
        float f3 = f1 - f;
        float f4 = f2 * ((float)Math.PI / 180F);
        if (f3 > 20.0F) {
            f3 = 20.0F;
        }

        if (f3 < -20.0F) {
            f3 = -20.0F;
        }

        if (limbSwingAmount > 0.2F) {
            f4 += MathHelper.cos(limbSwing * 0.4F) * 0.15F * limbSwingAmount;
        }

        float f5 = entityIn.getGrassEatingAmount(partialTick);
        float f6 = entityIn.getRearingAmount(partialTick);
        float f7 = 1.0F - f6;
        float f8 = entityIn.getMouthOpennessAngle(partialTick);
        boolean flag = entityIn.tailCounter != 0;
        float f9 = (float)entityIn.ticksExisted + partialTick;
        this.field_217128_b.rotationPointY = 4.0F;
        this.field_217128_b.rotationPointZ = -12.0F;
        this.field_217127_a.rotateAngleX = 0.0F;
        this.field_217128_b.rotateAngleX = ((float)Math.PI / 6F) + f4;
        this.field_217128_b.rotateAngleY = f3 * ((float)Math.PI / 180F);
        float f10 = entityIn.isInWater() ? 0.2F : 1.0F;
        float f11 = MathHelper.cos(f10 * limbSwing * 0.6662F + (float)Math.PI);
        float f12 = f11 * 0.8F * limbSwingAmount;
        float f13 = (1.0F - Math.max(f6, f5)) * (((float)Math.PI / 6F) + f4 + f8 * MathHelper.sin(f9) * 0.05F);
        this.field_217128_b.rotateAngleX = f6 * (0.2617994F + f4) + f5 * (2.1816616F + MathHelper.sin(f9) * 0.05F) + f13;
        this.field_217128_b.rotateAngleY = f6 * f3 * ((float)Math.PI / 180F) + (1.0F - Math.max(f6, f5)) * this.field_217128_b.rotateAngleY;
        this.field_217128_b.rotationPointY = f6 * -4.0F + f5 * 11.0F + (1.0F - Math.max(f6, f5)) * this.field_217128_b.rotationPointY;
        this.field_217128_b.rotationPointZ = f6 * -4.0F + f5 * -12.0F + (1.0F - Math.max(f6, f5)) * this.field_217128_b.rotationPointZ;
        this.field_217127_a.rotateAngleX = f6 * (-(float)Math.PI / 4F) + f7 * this.field_217127_a.rotateAngleX;
        float f14 = 0.2617994F * f6;
        float f15 = MathHelper.cos(f9 * 0.6F + (float)Math.PI);
        this.field_217131_h.rotationPointY = 2.0F * f6 + 14.0F * f7;
        this.field_217131_h.rotationPointZ = -6.0F * f6 - 10.0F * f7;
        this.field_217132_i.rotationPointY = this.field_217131_h.rotationPointY;
        this.field_217132_i.rotationPointZ = this.field_217131_h.rotationPointZ;
        float f16 = ((-(float)Math.PI / 3F) + f15) * f6 + f12 * f7;
        float f17 = ((-(float)Math.PI / 3F) - f15) * f6 - f12 * f7;
        this.field_217129_f.rotateAngleX = f14 - f11 * 0.5F * limbSwingAmount * f7;
        this.field_217130_g.rotateAngleX = f14 + f11 * 0.5F * limbSwingAmount * f7;
        this.field_217131_h.rotateAngleX = f16;
        this.field_217132_i.rotateAngleX = f17;
        this.field_217133_j.rotateAngleX = ((float)Math.PI / 6F) + limbSwingAmount * 0.75F;
        this.field_217133_j.rotationPointY = -5.0F + limbSwingAmount;
        this.field_217133_j.rotationPointZ = 2.0F + limbSwingAmount * 2.0F;
        if (flag) {
            this.field_217133_j.rotateAngleY = MathHelper.cos(f9 * 0.7F);
        } else {
            this.field_217133_j.rotateAngleY = 0.0F;
        }

    }

    private float func_217126_a(float p_217126_1_, float p_217126_2_, float p_217126_3_) {
        float f;
        for(f = p_217126_2_ - p_217126_1_; f < -180.0F; f += 360.0F) {
        }

        while(f >= 180.0F) {
            f -= 360.0F;
        }

        return p_217126_1_ + p_217126_3_ * f;
    }
}
