package com.herobrine.mod.client.renders.layers;

import com.herobrine.mod.entities.InfectedMooshroomEntity;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockRendererDispatcher;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.Vector3f;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.LivingRenderer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.entity.model.CowModel;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
public class InfectedMooshroomMushroomLayer<T extends InfectedMooshroomEntity> extends LayerRenderer<T, CowModel<T>> {
    public InfectedMooshroomMushroomLayer(IEntityRenderer<T, CowModel<T>> rendererIn) {
        super(rendererIn);
    }

    public void render(@NotNull MatrixStack matrixStackIn, @NotNull IRenderTypeBuffer bufferIn, int packedLightIn, @NotNull T entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        if (!entitylivingbaseIn.isChild() && !entitylivingbaseIn.isInvisible()) {
            BlockRendererDispatcher blockrendererdispatcher = Minecraft.getInstance().getBlockRendererDispatcher();
            BlockState blockstate = entitylivingbaseIn.getMooshroomType().getRenderState();
            int i = LivingRenderer.getPackedOverlay(entitylivingbaseIn, 0.0F);
            matrixStackIn.push();
            matrixStackIn.translate(0.2F, -0.35F, 0.5D);
            matrixStackIn.rotate(Vector3f.YP.rotationDegrees(-48.0F));
            matrixStackIn.scale(-1.0F, -1.0F, 1.0F);
            matrixStackIn.translate(-0.5D, -0.5D, -0.5D);
            blockrendererdispatcher.renderBlock(blockstate, matrixStackIn, bufferIn, packedLightIn, i);
            matrixStackIn.pop();
            matrixStackIn.push();
            matrixStackIn.translate(0.2F, -0.35F, 0.5D);
            matrixStackIn.rotate(Vector3f.YP.rotationDegrees(42.0F));
            matrixStackIn.translate(0.1F, 0.0D, -0.6F);
            matrixStackIn.rotate(Vector3f.YP.rotationDegrees(-48.0F));
            matrixStackIn.scale(-1.0F, -1.0F, 1.0F);
            matrixStackIn.translate(-0.5D, -0.5D, -0.5D);
            blockrendererdispatcher.renderBlock(blockstate, matrixStackIn, bufferIn, packedLightIn, i);
            matrixStackIn.pop();
            matrixStackIn.push();
            this.getEntityModel().getHead().translateRotate(matrixStackIn);
            matrixStackIn.translate(0.0D, -0.7F, -0.2F);
            matrixStackIn.rotate(Vector3f.YP.rotationDegrees(-78.0F));
            matrixStackIn.scale(-1.0F, -1.0F, 1.0F);
            matrixStackIn.translate(-0.5D, -0.5D, -0.5D);
            blockrendererdispatcher.renderBlock(blockstate, matrixStackIn, bufferIn, packedLightIn, i);
            matrixStackIn.pop();
        }
    }
}