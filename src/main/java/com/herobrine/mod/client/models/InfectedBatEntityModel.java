package com.herobrine.mod.client.models;

import com.herobrine.mod.entities.InfectedBatEntity;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.entity.model.RendererModel;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
public class InfectedBatEntityModel extends EntityModel<InfectedBatEntity> {
    private final RendererModel batHead;
    private final RendererModel batBody;
    private final RendererModel batRightWing;
    private final RendererModel batLeftWing;
    private final RendererModel batOuterRightWing;
    private final RendererModel batOuterLeftWing;

    public InfectedBatEntityModel() {
        this.textureWidth = 64;
        this.textureHeight = 64;
        this.batHead = new RendererModel(this, 0, 0);
        this.batHead.addBox(-3.0F, -3.0F, -3.0F, 6, 6, 6);
        RendererModel renderermodel = new RendererModel(this, 24, 0);
        renderermodel.addBox(-4.0F, -6.0F, -2.0F, 3, 4, 1);
        this.batHead.addChild(renderermodel);
        RendererModel renderermodel1 = new RendererModel(this, 24, 0);
        renderermodel1.mirror = true;
        renderermodel1.addBox(1.0F, -6.0F, -2.0F, 3, 4, 1);
        this.batHead.addChild(renderermodel1);
        this.batBody = new RendererModel(this, 0, 16);
        this.batBody.addBox(-3.0F, 4.0F, -3.0F, 6, 12, 6);
        this.batBody.setTextureOffset(0, 34).addBox(-5.0F, 16.0F, 0.0F, 10, 6, 1);
        this.batRightWing = new RendererModel(this, 42, 0);
        this.batRightWing.addBox(-12.0F, 1.0F, 1.5F, 10, 16, 1);
        this.batOuterRightWing = new RendererModel(this, 24, 16);
        this.batOuterRightWing.setRotationPoint(-12.0F, 1.0F, 1.5F);
        this.batOuterRightWing.addBox(-8.0F, 1.0F, 0.0F, 8, 12, 1);
        this.batLeftWing = new RendererModel(this, 42, 0);
        this.batLeftWing.mirror = true;
        this.batLeftWing.addBox(2.0F, 1.0F, 1.5F, 10, 16, 1);
        this.batOuterLeftWing = new RendererModel(this, 24, 16);
        this.batOuterLeftWing.mirror = true;
        this.batOuterLeftWing.setRotationPoint(12.0F, 1.0F, 1.5F);
        this.batOuterLeftWing.addBox(0.0F, 1.0F, 0.0F, 8, 12, 1);
        this.batBody.addChild(this.batRightWing);
        this.batBody.addChild(this.batLeftWing);
        this.batRightWing.addChild(this.batOuterRightWing);
        this.batLeftWing.addChild(this.batOuterLeftWing);
    }

    @Override
    public void render(@NotNull InfectedBatEntity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
        this.setRotationAngles(entityIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
        this.batHead.render(scale);
        this.batBody.render(scale);
    }

    @Override
    public void setRotationAngles(@NotNull InfectedBatEntity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor) {
        if (entityIn.getIsBatHanging()) {
            this.batHead.rotateAngleX = headPitch * ((float)Math.PI / 180F);
            this.batHead.rotateAngleY = (float)Math.PI - netHeadYaw * ((float)Math.PI / 180F);
            this.batHead.rotateAngleZ = (float)Math.PI;
            this.batHead.setRotationPoint(0.0F, -2.0F, 0.0F);
            this.batRightWing.setRotationPoint(-3.0F, 0.0F, 3.0F);
            this.batLeftWing.setRotationPoint(3.0F, 0.0F, 3.0F);
            this.batBody.rotateAngleX = (float)Math.PI;
            this.batRightWing.rotateAngleX = -0.15707964F;
            this.batRightWing.rotateAngleY = -1.2566371F;
            this.batOuterRightWing.rotateAngleY = -1.7278761F;
            this.batLeftWing.rotateAngleX = this.batRightWing.rotateAngleX;
            this.batLeftWing.rotateAngleY = -this.batRightWing.rotateAngleY;
            this.batOuterLeftWing.rotateAngleY = -this.batOuterRightWing.rotateAngleY;
        } else {
            this.batHead.rotateAngleX = headPitch * ((float)Math.PI / 180F);
            this.batHead.rotateAngleY = netHeadYaw * ((float)Math.PI / 180F);
            this.batHead.rotateAngleZ = 0.0F;
            this.batHead.setRotationPoint(0.0F, 0.0F, 0.0F);
            this.batRightWing.setRotationPoint(0.0F, 0.0F, 0.0F);
            this.batLeftWing.setRotationPoint(0.0F, 0.0F, 0.0F);
            this.batBody.rotateAngleX = ((float)Math.PI / 4F) + MathHelper.cos(ageInTicks * 0.1F) * 0.15F;
            this.batBody.rotateAngleY = 0.0F;
            this.batRightWing.rotateAngleY = MathHelper.cos(ageInTicks * 1.3F) * (float)Math.PI * 0.25F;
            this.batLeftWing.rotateAngleY = -this.batRightWing.rotateAngleY;
            this.batOuterRightWing.rotateAngleY = this.batRightWing.rotateAngleY * 0.5F;
            this.batOuterLeftWing.rotateAngleY = -this.batRightWing.rotateAngleY * 0.5F;
        }

    }
}