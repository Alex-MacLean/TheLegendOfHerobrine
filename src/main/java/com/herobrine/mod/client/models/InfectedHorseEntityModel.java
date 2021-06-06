package com.herobrine.mod.client.models;

import com.google.common.collect.ImmutableList;
import com.herobrine.mod.entities.InfectedHorseEntity;
import net.minecraft.client.renderer.entity.model.AgeableModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
public class InfectedHorseEntityModel extends AgeableModel<InfectedHorseEntity> {
    protected final ModelRenderer body;
    protected final ModelRenderer head;
    private final ModelRenderer field_228262_f_;
    private final ModelRenderer field_228263_g_;
    private final ModelRenderer field_228264_h_;
    private final ModelRenderer field_228265_i_;
    private final ModelRenderer field_228266_j_;
    private final ModelRenderer field_228267_k_;
    private final ModelRenderer field_228268_l_;
    private final ModelRenderer field_228269_m_;
    private final ModelRenderer field_217133_j;
    private final ModelRenderer[] field_217134_k;
    private final ModelRenderer[] field_217135_l;

    public InfectedHorseEntityModel(float p_i51065_1_) {
        super(true, 16.2F, 1.36F, 2.7272F, 2.0F, 20.0F);
        this.texWidth = 64;
        this.texHeight = 64;
        this.body = new ModelRenderer(this, 0, 32);
        this.body.addBox(-5.0F, -8.0F, -17.0F, 10.0F, 10.0F, 22.0F, 0.05F);
        this.body.setPos(0.0F, 11.0F, 5.0F);
        this.head = new ModelRenderer(this, 0, 35);
        this.head.addBox(-2.05F, -6.0F, -2.0F, 4.0F, 12.0F, 7.0F);
        this.head.xRot = ((float) Math.PI / 6F);
        ModelRenderer modelrenderer = new ModelRenderer(this, 0, 13);
        modelrenderer.addBox(-3.0F, -11.0F, -2.0F, 6.0F, 5.0F, 7.0F, p_i51065_1_);
        ModelRenderer modelrenderer1 = new ModelRenderer(this, 56, 36);
        modelrenderer1.addBox(-1.0F, -11.0F, 5.01F, 2.0F, 16.0F, 2.0F, p_i51065_1_);
        ModelRenderer modelrenderer2 = new ModelRenderer(this, 0, 25);
        modelrenderer2.addBox(-2.0F, -11.0F, -7.0F, 4.0F, 5.0F, 5.0F, p_i51065_1_);
        this.head.addChild(modelrenderer);
        this.head.addChild(modelrenderer1);
        this.head.addChild(modelrenderer2);
        this.func_199047_a(this.head);
        this.field_228262_f_ = new ModelRenderer(this, 48, 21);
        this.field_228262_f_.mirror = true;
        this.field_228262_f_.addBox(-3.0F, -1.01F, -1.0F, 4.0F, 11.0F, 4.0F, p_i51065_1_);
        this.field_228262_f_.setPos(4.0F, 14.0F, 7.0F);
        this.field_228263_g_ = new ModelRenderer(this, 48, 21);
        this.field_228263_g_.addBox(-1.0F, -1.01F, -1.0F, 4.0F, 11.0F, 4.0F, p_i51065_1_);
        this.field_228263_g_.setPos(-4.0F, 14.0F, 7.0F);
        this.field_228264_h_ = new ModelRenderer(this, 48, 21);
        this.field_228264_h_.mirror = true;
        this.field_228264_h_.addBox(-3.0F, -1.01F, -1.9F, 4.0F, 11.0F, 4.0F, p_i51065_1_);
        this.field_228264_h_.setPos(4.0F, 6.0F, -12.0F);
        this.field_228265_i_ = new ModelRenderer(this, 48, 21);
        this.field_228265_i_.addBox(-1.0F, -1.01F, -1.9F, 4.0F, 11.0F, 4.0F, p_i51065_1_);
        this.field_228265_i_.setPos(-4.0F, 6.0F, -12.0F);
        this.field_228266_j_ = new ModelRenderer(this, 48, 21);
        this.field_228266_j_.mirror = true;
        this.field_228266_j_.addBox(-3.0F, -1.01F, -1.0F, 4.0F, 11.0F, 4.0F, p_i51065_1_, p_i51065_1_ + 5.5F, p_i51065_1_);
        this.field_228266_j_.setPos(4.0F, 14.0F, 7.0F);
        this.field_228267_k_ = new ModelRenderer(this, 48, 21);
        this.field_228267_k_.addBox(-1.0F, -1.01F, -1.0F, 4.0F, 11.0F, 4.0F, p_i51065_1_, p_i51065_1_ + 5.5F, p_i51065_1_);
        this.field_228267_k_.setPos(-4.0F, 14.0F, 7.0F);
        this.field_228268_l_ = new ModelRenderer(this, 48, 21);
        this.field_228268_l_.mirror = true;
        this.field_228268_l_.addBox(-3.0F, -1.01F, -1.9F, 4.0F, 11.0F, 4.0F, p_i51065_1_, p_i51065_1_ + 5.5F, p_i51065_1_);
        this.field_228268_l_.setPos(4.0F, 6.0F, -12.0F);
        this.field_228269_m_ = new ModelRenderer(this, 48, 21);
        this.field_228269_m_.addBox(-1.0F, -1.01F, -1.9F, 4.0F, 11.0F, 4.0F, p_i51065_1_, p_i51065_1_ + 5.5F, p_i51065_1_);
        this.field_228269_m_.setPos(-4.0F, 6.0F, -12.0F);
        this.field_217133_j = new ModelRenderer(this, 42, 36);
        this.field_217133_j.addBox(-1.5F, 0.0F, 0.0F, 3.0F, 14.0F, 4.0F, p_i51065_1_);
        this.field_217133_j.setPos(0.0F, -5.0F, 2.0F);
        this.field_217133_j.xRot = ((float) Math.PI / 6F);
        this.body.addChild(this.field_217133_j);
        ModelRenderer modelrenderer3 = new ModelRenderer(this, 26, 0);
        modelrenderer3.addBox(-5.0F, -8.0F, -9.0F, 10.0F, 9.0F, 9.0F, 0.5F);
        this.body.addChild(modelrenderer3);
        ModelRenderer modelrenderer4 = new ModelRenderer(this, 29, 5);
        modelrenderer4.addBox(2.0F, -9.0F, -6.0F, 1.0F, 2.0F, 2.0F, p_i51065_1_);
        this.head.addChild(modelrenderer4);
        ModelRenderer modelrenderer5 = new ModelRenderer(this, 29, 5);
        modelrenderer5.addBox(-3.0F, -9.0F, -6.0F, 1.0F, 2.0F, 2.0F, p_i51065_1_);
        this.head.addChild(modelrenderer5);
        ModelRenderer modelrenderer6 = new ModelRenderer(this, 32, 2);
        modelrenderer6.addBox(3.1F, -6.0F, -8.0F, 0.0F, 3.0F, 16.0F, p_i51065_1_);
        modelrenderer6.xRot = (-(float) Math.PI / 6F);
        this.head.addChild(modelrenderer6);
        ModelRenderer modelrenderer7 = new ModelRenderer(this, 32, 2);
        modelrenderer7.addBox(-3.1F, -6.0F, -8.0F, 0.0F, 3.0F, 16.0F, p_i51065_1_);
        modelrenderer7.xRot = (-(float) Math.PI / 6F);
        this.head.addChild(modelrenderer7);
        ModelRenderer modelrenderer8 = new ModelRenderer(this, 1, 1);
        modelrenderer8.addBox(-3.0F, -11.0F, -1.9F, 6.0F, 5.0F, 6.0F, 0.2F);
        this.head.addChild(modelrenderer8);
        ModelRenderer modelrenderer9 = new ModelRenderer(this, 19, 0);
        modelrenderer9.addBox(-2.0F, -11.0F, -4.0F, 4.0F, 5.0F, 2.0F, 0.2F);
        this.head.addChild(modelrenderer9);
        this.field_217134_k = new ModelRenderer[]{modelrenderer3, modelrenderer4, modelrenderer5, modelrenderer8, modelrenderer9};
        this.field_217135_l = new ModelRenderer[]{modelrenderer6, modelrenderer7};
    }

    protected void func_199047_a(@NotNull ModelRenderer p_199047_1_) {
        ModelRenderer modelrenderer = new ModelRenderer(this, 19, 16);
        modelrenderer.addBox(0.55F, -13.0F, 4.0F, 2.0F, 3.0F, 1.0F, -0.001F);
        ModelRenderer modelrenderer1 = new ModelRenderer(this, 19, 16);
        modelrenderer1.addBox(-2.55F, -13.0F, 4.0F, 2.0F, 3.0F, 1.0F, -0.001F);
        p_199047_1_.addChild(modelrenderer);
        p_199047_1_.addChild(modelrenderer1);
    }

    @Override
    public void setupAnim(@NotNull InfectedHorseEntity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        boolean flag = entityIn.isHorseSaddled();
        boolean flag1 = entityIn.isVehicle();

        for (ModelRenderer modelrenderer : this.field_217134_k) {
            modelrenderer.visible = flag;
        }

        for (ModelRenderer modelrenderer1 : this.field_217135_l) {
            modelrenderer1.visible = flag1 && flag;
        }

        this.body.yRot = 11.0F;
    }

    @Override
    public @NotNull Iterable<ModelRenderer> headParts() {
        return ImmutableList.of(this.head);
    }

    @Override
    protected @NotNull Iterable<ModelRenderer> bodyParts() {
        return ImmutableList.of(this.body, this.field_228262_f_, this.field_228263_g_, this.field_228264_h_, this.field_228265_i_, this.field_228266_j_, this.field_228267_k_, this.field_228268_l_, this.field_228269_m_);
    }

    @Override
    public void prepareMobModel(@NotNull InfectedHorseEntity entityIn, float limbSwing, float limbSwingAmount, float partialTick) {
        super.prepareMobModel(entityIn, limbSwing, limbSwingAmount, partialTick);
        float f = MathHelper.rotLerp(entityIn.yBodyRotO, entityIn.yBodyRot, partialTick);
        float f1 = MathHelper.rotLerp(entityIn.yBodyRotO, entityIn.yBodyRot, partialTick);
        float f2 = MathHelper.lerp(partialTick, entityIn.xRotO, entityIn.xRot);
        float f3 = f1 - f;
        float f4 = f2 * ((float) Math.PI / 180F);
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
        float f9 = (float) entityIn.tickCount + partialTick;
        this.head.yRot = 4.0F;
        this.head.zRot = -12.0F;
        this.body.xRot = 0.0F;
        this.head.xRot = ((float) Math.PI / 6F) + f4;
        this.head.yRot = f3 * ((float) Math.PI / 180F);
        float f10 = entityIn.isInWater() ? 0.2F : 1.0F;
        float f11 = MathHelper.cos(f10 * limbSwing * 0.6662F + (float) Math.PI);
        float f12 = f11 * 0.8F * limbSwingAmount;
        float f13 = (1.0F - Math.max(f6, f5)) * (((float) Math.PI / 6F) + f4 + f8 * MathHelper.sin(f9) * 0.05F);
        this.head.xRot = f6 * (0.2617994F + f4) + f5 * (2.1816616F + MathHelper.sin(f9) * 0.05F) + f13;
        this.head.yRot = f6 * f3 * ((float) Math.PI / 180F) + (1.0F - Math.max(f6, f5)) * this.head.yRot;
        this.head.yRot = f6 * -4.0F + f5 * 11.0F + (1.0F - Math.max(f6, f5)) * this.head.yRot;
        this.head.zRot = f6 * -4.0F + f5 * -12.0F + (1.0F - Math.max(f6, f5)) * this.head.zRot;
        this.body.xRot = f6 * (-(float) Math.PI / 4F) + f7 * this.body.xRot;
        float f14 = 0.2617994F * f6;
        float f15 = MathHelper.cos(f9 * 0.6F + (float) Math.PI);
        this.field_228264_h_.yRot = 2.0F * f6 + 14.0F * f7;
        this.field_228264_h_.zRot = -6.0F * f6 - 10.0F * f7;
        this.field_228265_i_.yRot = this.field_228264_h_.yRot;
        this.field_228265_i_.zRot = this.field_228264_h_.zRot;
        float f16 = ((-(float) Math.PI / 3F) + f15) * f6 + f12 * f7;
        float f17 = ((-(float) Math.PI / 3F) - f15) * f6 - f12 * f7;
        this.field_228262_f_.xRot = f14 - f11 * 0.5F * limbSwingAmount * f7;
        this.field_228263_g_.xRot = f14 + f11 * 0.5F * limbSwingAmount * f7;
        this.field_228264_h_.xRot = f16;
        this.field_228265_i_.xRot = f17;
        this.field_217133_j.xRot = ((float) Math.PI / 6F) + limbSwingAmount * 0.75F;
        this.field_217133_j.yRot = -5.0F + limbSwingAmount;
        this.field_217133_j.zRot = 2.0F + limbSwingAmount * 2.0F;
        if (flag) {
            this.field_217133_j.yRot = MathHelper.cos(f9 * 0.7F);
        } else {
            this.field_217133_j.yRot = 0.0F;
        }

        this.field_228266_j_.yRot = this.field_228262_f_.yRot;
        this.field_228266_j_.zRot = this.field_228262_f_.zRot;
        this.field_228266_j_.xRot = this.field_228262_f_.xRot;
        this.field_228267_k_.yRot = this.field_228263_g_.yRot;
        this.field_228267_k_.zRot = this.field_228263_g_.zRot;
        this.field_228267_k_.xRot = this.field_228263_g_.xRot;
        this.field_228268_l_.yRot = this.field_228264_h_.yRot;
        this.field_228268_l_.zRot = this.field_228264_h_.zRot;
        this.field_228268_l_.xRot = this.field_228264_h_.xRot;
        this.field_228269_m_.yRot = this.field_228265_i_.yRot;
        this.field_228269_m_.zRot = this.field_228265_i_.zRot;
        this.field_228269_m_.xRot = this.field_228265_i_.xRot;
        boolean flag1 = entityIn.isBaby();
        this.field_228262_f_.visible = !flag1;
        this.field_228263_g_.visible = !flag1;
        this.field_228264_h_.visible = !flag1;
        this.field_228265_i_.visible = !flag1;
        this.field_228266_j_.visible = flag1;
        this.field_228267_k_.visible = flag1;
        this.field_228268_l_.visible = flag1;
        this.field_228269_m_.visible = flag1;
        this.body.yRot = flag1 ? 10.8F : 0.0F;
    }
}
