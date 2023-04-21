package com.herobrinemod.herobrine.client;

import com.herobrinemod.herobrine.client.entities.models.*;
import com.herobrinemod.herobrine.client.entities.renderers.*;
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
        EntityModelLayerRegistry.registerModelLayer(HerobrineModelLayers.HEROBRINE_MAGE_MODEL_LAYER, HerobrineMageEntityModel::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(HerobrineModelLayers.INFECTED_PIG_MODEL_LAYER, InfectedPigEntityModel::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(HerobrineModelLayers.INFECTED_COW_MODEL_LAYER, InfectedCowEntityModel::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(HerobrineModelLayers.INFECTED_VILLAGER_MODEL_LAYER, InfectedVillagerEntityModel::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(HerobrineModelLayers.INFECTED_CHICKEN_MODEL_LAYER, InfectedChickenEntityModel::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(HerobrineModelLayers.INFECTED_SHEEP_MODEL_LAYER, InfectedSheepEntityModel::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(HerobrineModelLayers.INFECTED_BAT_MODEL_LAYER, InfectedBatEntityModel::getTexturedModelData);
        EntityRendererRegistry.register(EntityTypeList.HEROBRINE_WARRIOR, HerobrineEntityRenderer::new);
        EntityRendererRegistry.register(EntityTypeList.HEROBRINE_SPY, HerobrineEntityRenderer::new);
        EntityRendererRegistry.register(EntityTypeList.HEROBRINE_MAGE, HerobrineMageEntityRenderer::new);
        EntityRendererRegistry.register(EntityTypeList.FAKE_HEROBRINE_MAGE, HerobrineMageEntityRenderer::new);
        EntityRendererRegistry.register(EntityTypeList.HEROBRINE_BUILDER, HerobrineEntityRenderer::new);
        EntityRendererRegistry.register(EntityTypeList.HEROBRINE_STALKER, HerobrineEntityRenderer::new);
        EntityRendererRegistry.register(EntityTypeList.HOLY_WATER, context -> new FlyingItemEntityRenderer(context, 1.0f, false));
        EntityRendererRegistry.register(EntityTypeList.UNHOLY_WATER, context -> new FlyingItemEntityRenderer(context, 1.0f, false));
        EntityRendererRegistry.register(EntityTypeList.INFECTED_PIG, InfectedPigEntityRenderer::new);
        EntityRendererRegistry.register(EntityTypeList.INFECTED_COW, InfectedCowEntityRenderer::new);
        EntityRendererRegistry.register(EntityTypeList.INFECTED_VILLAGER, InfectedVillagerEntityRenderer::new);
        EntityRendererRegistry.register(EntityTypeList.INFECTED_CHICKEN, InfectedChickenEntityRenderer::new);
        EntityRendererRegistry.register(EntityTypeList.INFECTED_SHEEP, InfectedSheepEntityRenderer::new);
        EntityRendererRegistry.register(EntityTypeList.INFECTED_BAT, InfectedBatEntityRenderer::new);
    }
}
