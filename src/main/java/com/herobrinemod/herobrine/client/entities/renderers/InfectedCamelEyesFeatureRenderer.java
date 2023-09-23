package com.herobrinemod.herobrine.client.entities.renderers;

import com.herobrinemod.herobrine.HerobrineMod;
import com.herobrinemod.herobrine.client.entities.models.InfectedCamelEntityModel;
import com.herobrinemod.herobrine.entities.InfectedCamelEntity;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.entity.feature.EyesFeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.util.Identifier;

public class InfectedCamelEyesFeatureRenderer extends EyesFeatureRenderer<InfectedCamelEntity, InfectedCamelEntityModel> {
    public InfectedCamelEyesFeatureRenderer(FeatureRendererContext<InfectedCamelEntity, InfectedCamelEntityModel> featureRendererContext) {
        super(featureRendererContext);
    }

    @Override
    public RenderLayer getEyesTexture() {
        return RenderLayer.getEyes(new Identifier(HerobrineMod.MODID, "textures/entity/eyes/infected_camel.png"));
    }
}
