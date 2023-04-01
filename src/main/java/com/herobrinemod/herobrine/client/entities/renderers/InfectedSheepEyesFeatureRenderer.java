package com.herobrinemod.herobrine.client.entities.renderers;

import com.herobrinemod.herobrine.HerobrineMod;
import com.herobrinemod.herobrine.client.entities.models.InfectedSheepEntityModel;
import com.herobrinemod.herobrine.entities.InfectedSheepEntity;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.entity.feature.EyesFeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.util.Identifier;

public class InfectedSheepEyesFeatureRenderer extends EyesFeatureRenderer<InfectedSheepEntity, InfectedSheepEntityModel> {
    public InfectedSheepEyesFeatureRenderer(FeatureRendererContext<InfectedSheepEntity, InfectedSheepEntityModel> featureRendererContext) {
        super(featureRendererContext);
    }

    @Override
    public RenderLayer getEyesTexture() {
        return RenderLayer.getEyes(new Identifier(HerobrineMod.MODID, "textures/entity/eyes/infected_sheep.png"));
    }
}
