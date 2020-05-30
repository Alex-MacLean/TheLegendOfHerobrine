package com.herobrine.mod.client.renders.layers;

import com.herobrine.mod.HerobrineMod;
import com.herobrine.mod.client.models.InfectedSheepEntityModel;
import com.herobrine.mod.client.models.InfectedSheepWoolModel;
import com.herobrine.mod.entities.InfectedSheepEntity;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.item.DyeColor;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
public class InfectedSheepWoolLayer extends LayerRenderer<InfectedSheepEntity, InfectedSheepEntityModel<InfectedSheepEntity>> {
    private static final ResourceLocation TEXTURE = HerobrineMod.location("textures/entity/infected_sheep/infected_sheep_fur.png");
    private final InfectedSheepWoolModel<InfectedSheepEntity> infectedSheepModel = new InfectedSheepWoolModel<>();

    public InfectedSheepWoolLayer(IEntityRenderer<InfectedSheepEntity, InfectedSheepEntityModel<InfectedSheepEntity>> rendererIn) {
        super(rendererIn);
    }

    public void render(@NotNull MatrixStack matrixStackIn, @NotNull IRenderTypeBuffer bufferIn, int packedLightIn, @NotNull InfectedSheepEntity entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        if (!entitylivingbaseIn.getSheared() && !entitylivingbaseIn.isInvisible()) {
            float f;
            float f1;
            float f2;
            if (entitylivingbaseIn.hasCustomName() && "jeb_".equals(entitylivingbaseIn.getName().getUnformattedComponentText())) {
                int i = entitylivingbaseIn.ticksExisted / 25 + entitylivingbaseIn.getEntityId();
                int j = DyeColor.values().length;
                int k = i % j;
                int l = (i + 1) % j;
                float f3 = ((float)(entitylivingbaseIn.ticksExisted % 25) + partialTicks) / 25.0F;
                float[] afloat1 = InfectedSheepEntity.getDyeRgb(DyeColor.byId(k));
                float[] afloat2 = InfectedSheepEntity.getDyeRgb(DyeColor.byId(l));
                f = afloat1[0] * (1.0F - f3) + afloat2[0] * f3;
                f1 = afloat1[1] * (1.0F - f3) + afloat2[1] * f3;
                f2 = afloat1[2] * (1.0F - f3) + afloat2[2] * f3;
            } else {
                float[] afloat = InfectedSheepEntity.getDyeRgb(entitylivingbaseIn.getFleeceColor());
                f = afloat[0];
                f1 = afloat[1];
                f2 = afloat[2];
            }

            renderCopyCutoutModel(this.getEntityModel(), this.infectedSheepModel, TEXTURE, matrixStackIn, bufferIn, packedLightIn, entitylivingbaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, partialTicks, f, f1, f2);
        }
    }
}
