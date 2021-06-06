package com.herobrine.mod.client.models;

import com.herobrine.mod.entities.InfectedSheepEntity;
import net.minecraft.client.renderer.entity.model.QuadrupedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
public class InfectedSheepEntityModel<T extends InfectedSheepEntity> extends QuadrupedModel<T> {
    private float headRotationAngleX;

    public InfectedSheepEntityModel() {
        super(12, 0.0F, false, 8.0F, 4.0F, 2.0F, 2.0F, 24);
        this.head = new ModelRenderer(this, 0, 0);
        this.head.addBox(-3.0F, -4.0F, -6.0F, 6.0F, 6.0F, 8.0F, 0.0F);
        this.head.setPos(0.0F, 6.0F, -8.0F);
        this.body = new ModelRenderer(this, 28, 8);
        this.body.addBox(-4.0F, -10.0F, -7.0F, 8.0F, 16.0F, 6.0F, 0.0F);
        this.body.setPos(0.0F, 5.0F, 2.0F);
    }

    @Override
    public void prepareMobModel(@NotNull T entityIn, float limbSwing, float limbSwingAmount, float partialTick) {
        super.prepareMobModel(entityIn, limbSwing, limbSwingAmount, partialTick);
        this.head.yRot = 6.0F + entityIn.getHeadRotationPointY(partialTick) * 9.0F;
        this.headRotationAngleX = entityIn.getHeadRotationAngleX(partialTick);
    }

    @Override
    public void setupAnim(@NotNull T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        super.setupAnim(entityIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
        this.head.xRot = this.headRotationAngleX;
    }
}
