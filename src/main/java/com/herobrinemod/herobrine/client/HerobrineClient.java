package com.herobrinemod.herobrine.client;

import com.herobrinemod.herobrine.client.entities.models.*;
import com.herobrinemod.herobrine.client.entities.renderers.*;
import com.herobrinemod.herobrine.entities.EntityTypeList;
import com.herobrinemod.herobrine.entities.InfectedLlamaSpitEntity;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.model.Dilation;
import net.minecraft.client.render.entity.FlyingItemEntityRenderer;
import net.minecraft.entity.EntityType;

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
        EntityModelLayerRegistry.registerModelLayer(HerobrineModelLayers.INFECTED_WOLF_MODEL_LAYER, InfectedWolfEntityModel::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(HerobrineModelLayers.INFECTED_DONKEY_MODEL_LAYER, InfectedDonkeyEntityModel::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(HerobrineModelLayers.INFECTED_HORSE_MODEL_LAYER, InfectedHorseEntityModel::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(HerobrineModelLayers.INFECTED_LLAMA_MODEL_LAYER, InfectedLlamaEntityModel::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(HerobrineModelLayers.INFECTED_LLAMA_SPIT_MODEL_LAYER, InfectedLlamaSpitEntityModel::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(HerobrineModelLayers.INFECTED_RABBIT_MODEL_LAYER, InfectedRabbitEntityModel::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(HerobrineModelLayers.SURVIVOR_MODEL_LAYER, SurvivorEntityModel::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(HerobrineModelLayers.SURVIVOR_INNER_ARMOR, () -> SurvivorEntityModel.getTexturedArmorModelData(new Dilation(0.5f))); // Why do these require a lambda reference for getTexturedModelData methods to return TexturedModelDataProvider? Nothing is passed to it. Vanilla ModelLayer registries don't need a lambda reference. tl;dr: Java is dumb
        EntityModelLayerRegistry.registerModelLayer(HerobrineModelLayers.SURVIVOR_OUTER_ARMOR, () -> SurvivorEntityModel.getTexturedArmorModelData(new Dilation(1.0f)));
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
        EntityRendererRegistry.register(EntityTypeList.INFECTED_WOLF, InfectedWolfEntityRenderer::new);
        EntityRendererRegistry.register(EntityTypeList.INFECTED_MOOSHROOM, InfectedMooshroomEntityRenderer::new);
        EntityRendererRegistry.register(EntityTypeList.INFECTED_DONKEY, InfectedDonkeyEntityRenderer::new);
        EntityRendererRegistry.register(EntityTypeList.INFECTED_HORSE, InfectedHorseEntityRenderer::new);
        EntityRendererRegistry.register(EntityTypeList.INFECTED_LLAMA, InfectedLlamaEntityRenderer::new);
        EntityRendererRegistry.register((EntityType<? extends InfectedLlamaSpitEntity>) EntityTypeList.INFECTED_LLAMA_SPIT, InfectedLlamaSpitEntityRenderer::new);
        EntityRendererRegistry.register(EntityTypeList.INFECTED_RABBIT, InfectedRabbitEntityRenderer::new);
        EntityRendererRegistry.register(EntityTypeList.SURVIVOR, SurvivorEntityRenderer::new);
    }
}
