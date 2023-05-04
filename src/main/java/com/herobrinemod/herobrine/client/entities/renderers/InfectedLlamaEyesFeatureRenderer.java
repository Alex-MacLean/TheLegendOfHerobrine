package com.herobrinemod.herobrine.client.entities.renderers;

import com.herobrinemod.herobrine.HerobrineMod;
import com.herobrinemod.herobrine.client.entities.models.InfectedLlamaEntityModel;
import com.herobrinemod.herobrine.entities.InfectedLlamaEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.entity.feature.EyesFeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class InfectedLlamaEyesFeatureRenderer extends EyesFeatureRenderer<InfectedLlamaEntity, InfectedLlamaEntityModel> {
    public InfectedLlamaEyesFeatureRenderer(FeatureRendererContext<InfectedLlamaEntity, InfectedLlamaEntityModel> featureRendererContext) {
        super(featureRendererContext);
    }

    @Override
    public RenderLayer getEyesTexture() {
        return RenderLayer.getEyes(new Identifier(HerobrineMod.MODID, "textures/entity/eyes/infected_llama.png"));
    }
}
