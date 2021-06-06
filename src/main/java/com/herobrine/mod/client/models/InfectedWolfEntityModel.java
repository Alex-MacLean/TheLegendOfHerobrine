package com.herobrine.mod.client.models;

import com.google.common.collect.ImmutableList;
import com.herobrine.mod.entities.InfectedWolfEntity;
import net.minecraft.client.renderer.entity.model.TintedAgeableModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
public class InfectedWolfEntityModel extends TintedAgeableModel<InfectedWolfEntity> {
    private final ModelRenderer head;
    private final ModelRenderer body;
    private final ModelRenderer legBackRight;
    private final ModelRenderer legBackLeft;
    private final ModelRenderer legFrontRight;
    private final ModelRenderer legFrontLeft;
    private final ModelRenderer tail;
    private final ModelRenderer mane;

    public InfectedWolfEntityModel() {
        this.head = new ModelRenderer(this, 0, 0);
        this.head.setPos(-1.0F, 13.5F, -7.0F);
        ModelRenderer headChild = new ModelRenderer(this, 0, 0);
        headChild.addBox(-2.0F, -3.0F, -2.0F, 6.0F, 6.0F, 4.0F, 0.0F);
        this.head.addChild(headChild);
        this.body = new ModelRenderer(this, 18, 14);
        this.body.addBox(-3.0F, -2.0F, -3.0F, 6.0F, 9.0F, 6.0F, 0.0F);
        this.body.setPos(0.0F, 14.0F, 2.0F);
        this.mane = new ModelRenderer(this, 21, 0);
        this.mane.addBox(-3.0F, -3.0F, -3.0F, 8.0F, 6.0F, 7.0F, 0.0F);
        this.mane.setPos(-1.0F, 14.0F, 2.0F);
        this.legBackRight = new ModelRenderer(this, 0, 18);
        this.legBackRight.addBox(0.0F, 0.0F, -1.0F, 2.0F, 8.0F, 2.0F, 0.0F);
        this.legBackRight.setPos(-2.5F, 16.0F, 7.0F);
        this.legBackLeft = new ModelRenderer(this, 0, 18);
        this.legBackLeft.addBox(0.0F, 0.0F, -1.0F, 2.0F, 8.0F, 2.0F, 0.0F);
        this.legBackLeft.setPos(0.5F, 16.0F, 7.0F);
        this.legFrontRight = new ModelRenderer(this, 0, 18);
        this.legFrontRight.addBox(0.0F, 0.0F, -1.0F, 2.0F, 8.0F, 2.0F, 0.0F);
        this.legFrontRight.setPos(-2.5F, 16.0F, -4.0F);
        this.legFrontLeft = new ModelRenderer(this, 0, 18);
        this.legFrontLeft.addBox(0.0F, 0.0F, -1.0F, 2.0F, 8.0F, 2.0F, 0.0F);
        this.legFrontLeft.setPos(0.5F, 16.0F, -4.0F);
        this.tail = new ModelRenderer(this, 9, 18);
        this.tail.setPos(-1.0F, 12.0F, 8.0F);
        ModelRenderer tailChild = new ModelRenderer(this, 9, 18);
        tailChild.addBox(0.0F, 0.0F, -1.0F, 2.0F, 8.0F, 2.0F, 0.0F);
        this.tail.addChild(tailChild);
        headChild.texOffs(16, 14).addBox(-2.0F, -5.0F, 0.0F, 2.0F, 2.0F, 1.0F, 0.0F);
        headChild.texOffs(16, 14).addBox(2.0F, -5.0F, 0.0F, 2.0F, 2.0F, 1.0F, 0.0F);
        headChild.texOffs(0, 10).addBox(-0.5F, 0.0F, -5.0F, 3.0F, 3.0F, 4.0F, 0.0F);
    }

    @Override
    protected @NotNull Iterable<ModelRenderer> headParts() {
        return ImmutableList.of(this.head);
    }

    @Override
    protected @NotNull Iterable<ModelRenderer> bodyParts() {
        return ImmutableList.of(this.body, this.legBackRight, this.legBackLeft, this.legFrontRight, this.legFrontLeft, this.tail, this.mane);
    }

    @Override
    public void prepareMobModel(@NotNull InfectedWolfEntity entityIn, float limbSwing, float limbSwingAmount, float partialTick) {
        this.tail.yRot = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
        this.body.setPos(0.0F, 14.0F, 2.0F);
        this.body.xRot = ((float) Math.PI / 2F);
        this.mane.setPos(-1.0F, 14.0F, -3.0F);
        this.mane.xRot = this.body.xRot;
        this.tail.setPos(-1.0F, 12.0F, 8.0F);
        this.legBackRight.setPos(-2.5F, 16.0F, 7.0F);
        this.legBackLeft.setPos(0.5F, 16.0F, 7.0F);
        this.legFrontRight.setPos(-2.5F, 16.0F, -4.0F);
        this.legFrontLeft.setPos(0.5F, 16.0F, -4.0F);
        this.legBackRight.xRot = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
        this.legBackLeft.xRot = MathHelper.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.4F * limbSwingAmount;
        this.legFrontRight.xRot = MathHelper.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.4F * limbSwingAmount;
        this.legFrontLeft.xRot = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
    }

    @Override
    public void setupAnim(@NotNull InfectedWolfEntity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.head.xRot = headPitch * ((float) Math.PI / 180F);
        this.head.xRot = netHeadYaw * ((float) Math.PI / 180F);
        this.tail.xRot = ageInTicks;
    }
}
