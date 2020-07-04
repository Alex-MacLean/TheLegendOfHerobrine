package com.herobrine.mod.client.models;

import net.minecraft.client.model.ModelQuadruped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.jetbrains.annotations.NotNull;

@SideOnly(Side.CLIENT)
public class InfectedLlamaEntityModel extends ModelQuadruped {
    public InfectedLlamaEntityModel(float p_i47226_1_) {
        super(15, p_i47226_1_);
        this.textureWidth = 128;
        this.textureHeight = 64;
        this.head = new ModelRenderer(this, 0, 0);
        this.head.addBox(-2.0F, -14.0F, -10.0F, 4, 4, 9, p_i47226_1_);
        this.head.setRotationPoint(0.0F, 7.0F, -6.0F);
        this.head.setTextureOffset(0, 14).addBox(-4.0F, -16.0F, -6.0F, 8, 18, 6, p_i47226_1_);
        this.head.setTextureOffset(17, 0).addBox(-4.0F, -19.0F, -4.0F, 3, 3, 2, p_i47226_1_);
        this.head.setTextureOffset(17, 0).addBox(1.0F, -19.0F, -4.0F, 3, 3, 2, p_i47226_1_);
        this.body = new ModelRenderer(this, 29, 0);
        this.body.addBox(-6.0F, -10.0F, -7.0F, 12, 18, 10, p_i47226_1_);
        this.body.setRotationPoint(0.0F, 5.0F, 2.0F);
        this.leg1 = new ModelRenderer(this, 29, 29);
        this.leg1.addBox(-2.0F, 0.0F, -2.0F, 4, 14, 4, p_i47226_1_);
        this.leg1.setRotationPoint(-2.5F, 10.0F, 6.0F);
        this.leg2 = new ModelRenderer(this, 29, 29);
        this.leg2.addBox(-2.0F, 0.0F, -2.0F, 4, 14, 4, p_i47226_1_);
        this.leg2.setRotationPoint(2.5F, 10.0F, 6.0F);
        this.leg3 = new ModelRenderer(this, 29, 29);
        this.leg3.addBox(-2.0F, 0.0F, -2.0F, 4, 14, 4, p_i47226_1_);
        this.leg3.setRotationPoint(-2.5F, 10.0F, -4.0F);
        this.leg4 = new ModelRenderer(this, 29, 29);
        this.leg4.addBox(-2.0F, 0.0F, -2.0F, 4, 14, 4, p_i47226_1_);
        this.leg4.setRotationPoint(2.5F, 10.0F, -4.0F);
        --this.leg1.rotationPointX;
        ++this.leg2.rotationPointX;
        this.leg1.rotationPointZ += 0.0F;
        this.leg2.rotationPointZ += 0.0F;
        --this.leg3.rotationPointX;
        ++this.leg4.rotationPointX;
        --this.leg3.rotationPointZ;
        --this.leg4.rotationPointZ;
        this.childZOffset += 2.0F;
    }

    @Override
    public void render(@NotNull Entity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
        this.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale, entityIn);
        this.head.render(scale);
        this.body.render(scale);
        this.leg1.render(scale);
        this.leg2.render(scale);
        this.leg3.render(scale);
        this.leg4.render(scale);
    }
}
