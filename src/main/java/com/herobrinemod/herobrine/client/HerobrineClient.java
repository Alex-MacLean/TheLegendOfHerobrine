package com.herobrinemod.herobrine.client;

import com.herobrinemod.herobrine.client.entities.models.HerobrineEntityModel;
import com.herobrinemod.herobrine.client.entities.models.HerobrineModelLayers;
import com.herobrinemod.herobrine.client.entities.models.InfectedPigEntityModel;
import com.herobrinemod.herobrine.client.entities.renderers.HerobrineEntityRenderer;
import com.herobrinemod.herobrine.client.entities.renderers.HerobrineMageEntityRenderer;
import com.herobrinemod.herobrine.client.entities.renderers.InfectedPigEntityRenderer;
import com.herobrinemod.herobrine.entities.EntityTypeList;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.render.entity.FlyingItemEntityRenderer;

@Environment(EnvType.CLIENT)
public class HerobrineClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        EntityModelLayerRegistry.registerModelLayer(HerobrineModelLayers.HEROBRINE_MODEL_LAYER, HerobrineEntityModel::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(HerobrineModelLayers.INFECTED_PIG_MODEL_LAYER, InfectedPigEntityModel::getTexturedModelData);
        EntityRendererRegistry.register(EntityTypeList.HEROBRINE_WARRIOR, HerobrineEntityRenderer::new);
        EntityRendererRegistry.register(EntityTypeList.HEROBRINE_SPY, HerobrineEntityRenderer::new);
        EntityRendererRegistry.register(EntityTypeList.HEROBRINE_MAGE, HerobrineMageEntityRenderer::new);
        EntityRendererRegistry.register(EntityTypeList.FAKE_HEROBRINE_MAGE, HerobrineMageEntityRenderer::new);
        EntityRendererRegistry.register(EntityTypeList.HEROBRINE_BUILDER, HerobrineEntityRenderer::new);
        EntityRendererRegistry.register(EntityTypeList.HEROBRINE_STALKER, HerobrineEntityRenderer::new);
        EntityRendererRegistry.register(EntityTypeList.HOLY_WATER, context -> new FlyingItemEntityRenderer(context, 1.0f, false));
        EntityRendererRegistry.register(EntityTypeList.UNHOLY_WATER, context -> new FlyingItemEntityRenderer(context, 1.0f, false));
        EntityRendererRegistry.register(EntityTypeList.INFECTED_PIG, InfectedPigEntityRenderer::new);
    }
}
