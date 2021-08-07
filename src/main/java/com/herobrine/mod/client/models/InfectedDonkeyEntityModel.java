package com.herobrine.mod.client.models;

import com.google.common.collect.ImmutableList;
import com.herobrine.mod.entities.InfectedDonkeyEntity;
import net.minecraft.client.renderer.entity.model.AgeableModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
public class InfectedDonkeyEntityModel extends AgeableModel<InfectedDonkeyEntity> {
    protected final ModelRenderer body;
    protected final ModelRenderer headParts;
    private final ModelRenderer leg1;
    private final ModelRenderer leg2;
    private final ModelRenderer leg3;
    private final ModelRenderer leg4;
    private final ModelRenderer tail;
    private final ModelRenderer[] saddleParts;
    private final ModelRenderer[] ridingParts;

    public InfectedDonkeyEntityModel(float p_i51065_1_) {
        super(true, 16.2F, 1.36F, 2.7272F, 2.0F, 20.0F);
        this.texWidth = 64;
        this.texHeight = 64;
        this.body = new ModelRenderer(this, 0, 32);
        this.body.addBox(-5.0F, -8.0F, -17.0F, 10.0F, 10.0F, 22.0F, 0.05F);
        this.body.setPos(0.0F, 11.0F, 5.0F);
        this.headParts = new ModelRenderer(this, 0, 35);
        this.headParts.addBox(-2.05F, -6.0F, -2.0F, 4.0F, 12.0F, 7.0F);
        this.headParts.xRot = ((float) Math.PI / 6F);
        ModelRenderer modelrenderer = new ModelRenderer(this, 0, 13);
        modelrenderer.addBox(-3.0F, -11.0F, -2.0F, 6.0F, 5.0F, 7.0F, p_i51065_1_);
        ModelRenderer modelrenderer1 = new ModelRenderer(this, 56, 36);
        modelrenderer1.addBox(-1.0F, -11.0F, 5.01F, 2.0F, 16.0F, 2.0F, p_i51065_1_);
        ModelRenderer modelrenderer2 = new ModelRenderer(this, 0, 25);
        modelrenderer2.addBox(-2.0F, -11.0F, -7.0F, 4.0F, 5.0F, 5.0F, p_i51065_1_);
        this.headParts.addChild(modelrenderer);
        this.headParts.addChild(modelrenderer1);
        this.headParts.addChild(modelrenderer2);
        this.addEarModels(this.headParts);
        this.leg1 = new ModelRenderer(this, 48, 21);
        this.leg1.mirror = true;
        this.leg1.addBox(-3.0F, -1.01F, -1.0F, 4.0F, 11.0F, 4.0F, p_i51065_1_);
        this.leg1.setPos(4.0F, 14.0F, 7.0F);
        this.leg2 = new ModelRenderer(this, 48, 21);
        this.leg2.addBox(-1.0F, -1.01F, -1.0F, 4.0F, 11.0F, 4.0F, p_i51065_1_);
        this.leg2.setPos(-4.0F, 14.0F, 7.0F);
        this.leg3 = new ModelRenderer(this, 48, 21);
        this.leg3.mirror = true;
        this.leg3.addBox(-3.0F, -1.01F, -1.9F, 4.0F, 11.0F, 4.0F, p_i51065_1_);
        this.leg3.setPos(4.0F, 6.0F, -12.0F);
        this.leg4 = new ModelRenderer(this, 48, 21);
        this.leg4.addBox(-1.0F, -1.01F, -1.9F, 4.0F, 11.0F, 4.0F, p_i51065_1_);
        this.leg4.setPos(-4.0F, 6.0F, -12.0F);
        this.tail = new ModelRenderer(this, 42, 36);
        this.tail.addBox(-1.5F, 0.0F, 0.0F, 3.0F, 14.0F, 4.0F, p_i51065_1_);
        this.tail.setPos(0.0F, -5.0F, 2.0F);
        this.tail.xRot = ((float) Math.PI / 6F);
        this.body.addChild(this.tail);
        ModelRenderer modelrenderer3 = new ModelRenderer(this, 26, 0);
        modelrenderer3.addBox(-5.0F, -8.0F, -9.0F, 10.0F, 9.0F, 9.0F, 0.5F);
        this.body.addChild(modelrenderer3);
        ModelRenderer modelrenderer4 = new ModelRenderer(this, 29, 5);
        modelrenderer4.addBox(2.0F, -9.0F, -6.0F, 1.0F, 2.0F, 2.0F, p_i51065_1_);
        this.headParts.addChild(modelrenderer4);
        ModelRenderer modelrenderer5 = new ModelRenderer(this, 29, 5);
        modelrenderer5.addBox(-3.0F, -9.0F, -6.0F, 1.0F, 2.0F, 2.0F, p_i51065_1_);
        this.headParts.addChild(modelrenderer5);
        ModelRenderer modelrenderer6 = new ModelRenderer(this, 32, 2);
        modelrenderer6.addBox(3.1F, -6.0F, -8.0F, 0.0F, 3.0F, 16.0F, p_i51065_1_);
        modelrenderer6.xRot = (-(float) Math.PI / 6F);
        this.headParts.addChild(modelrenderer6);
        ModelRenderer modelrenderer7 = new ModelRenderer(this, 32, 2);
        modelrenderer7.addBox(-3.1F, -6.0F, -8.0F, 0.0F, 3.0F, 16.0F, p_i51065_1_);
        modelrenderer7.xRot = (-(float) Math.PI / 6F);
        this.headParts.addChild(modelrenderer7);
        ModelRenderer modelrenderer8 = new ModelRenderer(this, 1, 1);
        modelrenderer8.addBox(-3.0F, -11.0F, -1.9F, 6.0F, 5.0F, 6.0F, 0.2F);
        this.headParts.addChild(modelrenderer8);
        ModelRenderer modelrenderer9 = new ModelRenderer(this, 19, 0);
        modelrenderer9.addBox(-2.0F, -11.0F, -4.0F, 4.0F, 5.0F, 2.0F, 0.2F);
        this.headParts.addChild(modelrenderer9);
        this.saddleParts = new ModelRenderer[]{modelrenderer3, modelrenderer4, modelrenderer5, modelrenderer8, modelrenderer9};
        this.ridingParts = new ModelRenderer[]{modelrenderer6, modelrenderer7};
    }

    protected void addEarModels(@NotNull ModelRenderer p_199047_1_) {
        ModelRenderer modelrenderer = new ModelRenderer(this, 0, 12);
        modelrenderer.addBox(-1.0F, -7.0F, 0.0F, 2.0F, 7.0F, 1.0F);
        modelrenderer.setPos(1.25F, -10.0F, 4.0F);
        ModelRenderer modelrenderer1 = new ModelRenderer(this, 0, 12);
        modelrenderer1.addBox(-1.0F, -7.0F, 0.0F, 2.0F, 7.0F, 1.0F);
        modelrenderer1.setPos(-1.25F, -10.0F, 4.0F);
        modelrenderer.xRot = 0.2617994F;
        modelrenderer.zRot = 0.2617994F;
        modelrenderer1.xRot = 0.2617994F;
        modelrenderer1.zRot = -0.2617994F;
        p_199047_1_.addChild(modelrenderer);
        p_199047_1_.addChild(modelrenderer1);
    }

    @Override
    public void setupAnim(@NotNull InfectedDonkeyEntity p_225597_1_, float p_225597_2_, float p_225597_3_, float p_225597_4_, float p_225597_5_, float p_225597_6_) {
        for (ModelRenderer modelrenderer : this.saddleParts) {
            modelrenderer.visible = false;
        }

        for (ModelRenderer modelrenderer1 : this.ridingParts) {
            modelrenderer1.visible = false;
        }

        this.body.y = 11.0F;
    }

    @Override
    public @NotNull Iterable<ModelRenderer> headParts() {
        return ImmutableList.of(this.headParts);
    }

    @Override
    protected @NotNull Iterable<ModelRenderer> bodyParts() {
        return ImmutableList.of(this.body, this.leg1, this.leg2, this.leg3, this.leg4);
    }

    @Override
    public void prepareMobModel(@NotNull InfectedDonkeyEntity p_212843_1_, float p_212843_2_, float p_212843_3_, float p_212843_4_) {
        super.prepareMobModel(p_212843_1_, p_212843_2_, p_212843_3_, p_212843_4_);
        float f = MathHelper.rotlerp(p_212843_1_.yBodyRotO, p_212843_1_.yBodyRot, p_212843_4_);
        float f1 = MathHelper.rotlerp(p_212843_1_.yHeadRotO, p_212843_1_.yHeadRot, p_212843_4_);
        float f2 = MathHelper.lerp(p_212843_4_, p_212843_1_.xRotO, p_212843_1_.xRot);
        float f3 = f1 - f;
        float f4 = f2 * ((float) Math.PI / 180F);
        if (f3 > 20.0F) {
            f3 = 20.0F;
        }

        if (f3 < -20.0F) {
            f3 = -20.0F;
        }

        if (p_212843_3_ > 0.2F) {
            f4 += MathHelper.cos(p_212843_2_ * 0.4F) * 0.15F * p_212843_3_;
        }

        float f5 = p_212843_1_.getGrassEatingAmount(p_212843_4_);
        float f6 = p_212843_1_.getRearingAmount(p_212843_4_);
        float f7 = 1.0F - f6;
        float f8 = p_212843_1_.getMouthOpennessAngle(p_212843_4_);
        boolean flag = p_212843_1_.tailCounter != 0;
        float f9 = (float) p_212843_1_.tickCount + p_212843_4_;
        this.headParts.y = 4.0F;
        this.headParts.z = -12.0F;
        this.body.xRot = 0.0F;
        this.headParts.xRot = ((float) Math.PI / 6F) + f4;
        this.headParts.yRot = f3 * ((float) Math.PI / 180F);
        float f10 = p_212843_1_.isInWater() ? 0.2F : 1.0F;
        float f11 = MathHelper.cos(f10 * p_212843_2_ * 0.6662F + (float) Math.PI);
        float f12 = f11 * 0.8F * p_212843_3_;
        float f13 = (1.0F - Math.max(f6, f5)) * (((float) Math.PI / 6F) + f4 + f8 * MathHelper.sin(f9) * 0.05F);
        this.headParts.xRot = f6 * (0.2617994F + f4) + f5 * (2.1816616F + MathHelper.sin(f9) * 0.05F) + f13;
        this.headParts.yRot = f6 * f3 * ((float) Math.PI / 180F) + (1.0F - Math.max(f6, f5)) * this.headParts.yRot;
        this.headParts.y = f6 * -4.0F + f5 * 11.0F + (1.0F - Math.max(f6, f5)) * this.headParts.y;
        this.headParts.z = f6 * -4.0F + f5 * -12.0F + (1.0F - Math.max(f6, f5)) * this.headParts.z;
        this.body.xRot = f6 * (-(float) Math.PI / 4F) + f7 * this.body.xRot;
        float f14 = 0.2617994F * f6;
        float f15 = MathHelper.cos(f9 * 0.6F + (float) Math.PI);
        this.leg3.y = 2.0F * f6 + 14.0F * f7;
        this.leg3.z = -6.0F * f6 - 10.0F * f7;
        this.leg4.y = this.leg3.y;
        this.leg4.z = this.leg3.z;
        float f16 = ((-(float) Math.PI / 3F) + f15) * f6 + f12 * f7;
        float f17 = ((-(float) Math.PI / 3F) - f15) * f6 - f12 * f7;
        this.leg1.xRot = f14 - f11 * 0.5F * p_212843_3_ * f7;
        this.leg2.xRot = f14 + f11 * 0.5F * p_212843_3_ * f7;
        this.leg3.xRot = f16;
        this.leg4.xRot = f17;
        this.tail.xRot = ((float) Math.PI / 6F) + p_212843_3_ * 0.75F;
        this.tail.y = -5.0F + p_212843_3_;
        this.tail.z = 2.0F + p_212843_3_ * 2.0F;
        if (flag) {
            this.tail.yRot = MathHelper.cos(f9 * 0.7F);
        } else {
            this.tail.yRot = 0.0F;
        }
    }
}
