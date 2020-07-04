package com.herobrine.mod.client.renders;

import com.google.common.collect.Maps;
import com.herobrine.mod.client.models.InfectedHorseEntityModel;
import com.herobrine.mod.entities.InfectedHorseEntity;
import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.texture.LayeredTexture;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

@OnlyIn(Dist.CLIENT)
public class InfectedHorseEntityRender extends MobRenderer<InfectedHorseEntity, InfectedHorseEntityModel> {
    private static final Map<String, ResourceLocation> LAYERED_LOCATION_CACHE = Maps.newHashMap();
    public InfectedHorseEntityRender(EntityRendererManager manager) {
        super(manager, new InfectedHorseEntityModel(0.0F), 0.75F);
    }

    @Override
    protected void preRenderCallback(@NotNull InfectedHorseEntity entitylivingbaseIn, float partialTickTime) {
        GlStateManager.scalef(1.1F, 1.1F, 1.1F);
    }

    @Override
    public @NotNull ResourceLocation getEntityTexture(@NotNull InfectedHorseEntity entity) {
        String s = entity.getHorseTexture();
        ResourceLocation resourcelocation = LAYERED_LOCATION_CACHE.get(s);
        if (resourcelocation == null) {
            resourcelocation = new ResourceLocation(s);
            Minecraft.getInstance().getTextureManager().loadTexture(resourcelocation, new LayeredTexture(entity.getVariantTexturePaths()));
            LAYERED_LOCATION_CACHE.put(s, resourcelocation);
        }

        return resourcelocation;
    }

    @SuppressWarnings("rawtypes")
    public static class RenderFactory implements IRenderFactory {
        @Override
        public EntityRenderer<? super InfectedHorseEntity> createRenderFor(EntityRendererManager manager) {
            return new InfectedHorseEntityRender(manager);
        }
    }
}
