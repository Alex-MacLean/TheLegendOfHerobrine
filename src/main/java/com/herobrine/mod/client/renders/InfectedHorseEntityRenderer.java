package com.herobrine.mod.client.renders;

import com.google.common.collect.Maps;
import com.herobrine.mod.client.models.InfectedHorseEntityModel;
import com.herobrine.mod.entities.InfectedHorseEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.texture.LayeredTexture;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

@SideOnly(Side.CLIENT)
public class InfectedHorseEntityRenderer extends RenderLiving<InfectedHorseEntity> {
    private static final Map<String, ResourceLocation> LAYERED_LOCATION_CACHE = Maps.newHashMap();

    public InfectedHorseEntityRenderer(RenderManager manager) {
        super(manager, new InfectedHorseEntityModel(), 0.75F);
    }

    @Override
    protected void preRenderCallback(@NotNull InfectedHorseEntity entitylivingbaseIn, float partialTickTime) {
        GlStateManager.scale(1.0F, 1.0F, 1.0F);
        super.preRenderCallback(entitylivingbaseIn, partialTickTime);
    }

    @Override
    protected ResourceLocation getEntityTexture(@NotNull InfectedHorseEntity entity) {
        String s = entity.getHorseTexture();
        ResourceLocation resourcelocation = LAYERED_LOCATION_CACHE.get(s);
        if (resourcelocation == null) {
            resourcelocation = new ResourceLocation(s);
            Minecraft.getMinecraft().getTextureManager().loadTexture(resourcelocation, new LayeredTexture(entity.getVariantTexturePaths()));
            LAYERED_LOCATION_CACHE.put(s, resourcelocation);
        }
        return resourcelocation;
    }
}
