package com.herobrine.mod.client.renders;

import com.herobrine.mod.HerobrineMod;
import com.herobrine.mod.client.models.InfectedBatEntityModel;
import com.herobrine.mod.client.renders.layers.InfectedBatEyesLayer;
import com.herobrine.mod.entities.InfectedBatEntity;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
public class InfectedBatEntityRender extends MobRenderer<InfectedBatEntity, InfectedBatEntityModel> {
    public InfectedBatEntityRender(EntityRendererManager manager) {
        super(manager, new InfectedBatEntityModel(), 0.25F);
        this.addLayer(new InfectedBatEyesLayer(this));
    }

    @NotNull
    @Override
    public ResourceLocation getTextureLocation(@NotNull InfectedBatEntity entity) {
        return HerobrineMod.location("textures/entity/infected_bat.png");
    }

    @Override
    protected void scale(@NotNull InfectedBatEntity entitylivingbaseIn, @NotNull MatrixStack matrixStackIn, float partialTickTime) {
        matrixStackIn.scale(0.35F, 0.35F, 0.35F);
    }

    @Override
    protected void setupRotations(@NotNull InfectedBatEntity entityLiving, @NotNull MatrixStack matrixStackIn, float ageInTicks, float rotationYaw, float partialTicks) {
        if (entityLiving.getIsBatHanging()) {
            matrixStackIn.translate(0.0D, -0.1F, 0.0D);
        } else {
            matrixStackIn.translate(0.0D, MathHelper.cos(ageInTicks * 0.3F) * 0.1F, 0.0D);
        }
        super.setupRotations(entityLiving, matrixStackIn, ageInTicks, rotationYaw, partialTicks);
    }

    @SuppressWarnings("rawtypes")
    public static class RenderFactory implements IRenderFactory {
        @Override
        public EntityRenderer<? super InfectedBatEntity> createRenderFor(EntityRendererManager manager) {
            return new InfectedBatEntityRender(manager);
        }
    }
}
