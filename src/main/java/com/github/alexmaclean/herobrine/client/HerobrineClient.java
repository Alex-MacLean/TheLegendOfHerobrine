package com.github.alexmaclean.herobrine.client;

import com.github.alexmaclean.herobrine.client.entities.models.HerobrineEntityModel;
import com.github.alexmaclean.herobrine.client.entities.models.HerobrineEntityModelLayers;
import com.github.alexmaclean.herobrine.client.entities.renderers.HerobrineEntityRenderer;
import com.github.alexmaclean.herobrine.util.entities.EntityTypeList;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;

@Environment(EnvType.CLIENT)
public class HerobrineClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        EntityRendererRegistry.register(EntityTypeList.HEROBRINE_WARRIOR, HerobrineEntityRenderer::new);
        EntityModelLayerRegistry.registerModelLayer(HerobrineEntityModelLayers.HEROBRINE_MODEL_LAYER, HerobrineEntityModel::getTexturedModelData);
    }
}
