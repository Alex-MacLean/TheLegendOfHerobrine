package com.herobrine.mod.client.renders.layers;

import com.herobrine.mod.HerobrineMod;
import com.herobrine.mod.client.models.InfectedSheepEntityModel;
import com.herobrine.mod.client.models.InfectedSheepWoolModel;
import com.herobrine.mod.entities.InfectedSheepEntity;
import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.entity.passive.SheepEntity;
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

    public void render(@NotNull InfectedSheepEntity entityIn, float p_212842_2_, float p_212842_3_, float p_212842_4_, float p_212842_5_, float p_212842_6_, float p_212842_7_, float p_212842_8_) {
        if (!entityIn.getSheared() && !entityIn.isInvisible()) {
            this.bindTexture(TEXTURE);
            if (entityIn.hasCustomName() && "jeb_".equals(entityIn.getName().getUnformattedComponentText())) {
                int i = entityIn.ticksExisted / 25 + entityIn.getEntityId();
                int j = DyeColor.values().length;
                int k = i % j;
                int l = (i + 1) % j;
                float f = ((float)(entityIn.ticksExisted % 25) + p_212842_4_) / 25.0F;
                float[] afloat1 = SheepEntity.getDyeRgb(DyeColor.byId(k));
                float[] afloat2 = SheepEntity.getDyeRgb(DyeColor.byId(l));
                GlStateManager.color3f(afloat1[0] * (1.0F - f) + afloat2[0] * f, afloat1[1] * (1.0F - f) + afloat2[1] * f, afloat1[2] * (1.0F - f) + afloat2[2] * f);
            } else {
                float[] afloat = SheepEntity.getDyeRgb(entityIn.getFleeceColor());
                GlStateManager.color3f(afloat[0], afloat[1], afloat[2]);
            }

            this.getEntityModel().setModelAttributes(this.infectedSheepModel);
            this.infectedSheepModel.setLivingAnimations(entityIn, p_212842_2_, p_212842_3_, p_212842_4_);
            this.infectedSheepModel.render(entityIn, p_212842_2_, p_212842_3_, p_212842_5_, p_212842_6_, p_212842_7_, p_212842_8_);
        }
    }

    public boolean shouldCombineTextures() {
        return true;
    }
}