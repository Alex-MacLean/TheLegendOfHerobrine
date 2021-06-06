package com.herobrine.mod.client.renders;

import com.herobrine.mod.HerobrineMod;
import com.herobrine.mod.client.models.AbstractHerobrineEntityModel;
import com.herobrine.mod.client.renders.layers.HerobrineEyesLayer;
import com.herobrine.mod.entities.AbstractHerobrineEntity;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.HeldItemLayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
public class AbstractHerobrineEntityRender extends MobRenderer<AbstractHerobrineEntity, AbstractHerobrineEntityModel> {
    private AbstractHerobrineEntityRender(EntityRendererManager manager) {
        super(manager, new AbstractHerobrineEntityModel(0.0F, false), 0.5F);
        this.addLayer(new HeldItemLayer<>(this));
        this.addLayer(new HerobrineEyesLayer(this));
    }

    @NotNull
    @Override
    public ResourceLocation getTextureLocation(@NotNull AbstractHerobrineEntity entity) {
        return HerobrineMod.location("textures/entity/herobrine.png");
    }

    @Override
    protected void scale(@NotNull AbstractHerobrineEntity entitylivingbaseIn, MatrixStack matrixStackIn, float partialTickTime) {
        matrixStackIn.scale(0.9375F, 0.9375F, 0.9375F);
        super.scale(entitylivingbaseIn, matrixStackIn, partialTickTime);
    }

    @SuppressWarnings("rawtypes")
    public static class RenderFactory implements IRenderFactory {
        @Override
        public EntityRenderer<? super AbstractHerobrineEntity> createRenderFor(EntityRendererManager manager) {
            return new AbstractHerobrineEntityRender(manager);
        }
    }
}