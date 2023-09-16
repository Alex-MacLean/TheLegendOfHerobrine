package com.herobrinemod.herobrine.client.entities.renderers;

import com.herobrinemod.herobrine.HerobrineMod;
import com.herobrinemod.herobrine.client.entities.models.InfectedAxolotlEntityModel;
import com.herobrinemod.herobrine.entities.InfectedAxolotlEntity;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.entity.feature.EyesFeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.util.Identifier;

public class InfectedAxolotlEyesFeatureRenderer extends EyesFeatureRenderer<InfectedAxolotlEntity, InfectedAxolotlEntityModel> {
    public InfectedAxolotlEyesFeatureRenderer(FeatureRendererContext<InfectedAxolotlEntity, InfectedAxolotlEntityModel> featureRendererContext) {
        super(featureRendererContext);
    }

    @Override
    public RenderLayer getEyesTexture() {
        return RenderLayer.getEyes(new Identifier(HerobrineMod.MODID, "textures/entity/eyes/infected_axolotl.png"));
    }
}
