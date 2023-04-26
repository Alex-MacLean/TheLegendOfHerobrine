package com.herobrinemod.herobrine.client.entities.renderers;

import com.herobrinemod.herobrine.HerobrineMod;
import com.herobrinemod.herobrine.client.entities.models.InfectedHorseEntityModel;
import com.herobrinemod.herobrine.entities.InfectedHorseEntity;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.entity.feature.EyesFeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.util.Identifier;

public class InfectedHorseEyesFeatureRenderer extends EyesFeatureRenderer<InfectedHorseEntity, InfectedHorseEntityModel> {
    public InfectedHorseEyesFeatureRenderer(FeatureRendererContext<InfectedHorseEntity, InfectedHorseEntityModel> featureRendererContext) {
        super(featureRendererContext);
    }

    @Override
    public RenderLayer getEyesTexture() {
        return RenderLayer.getEyes(new Identifier(HerobrineMod.MODID, "textures/entity/eyes/infected_horse.png"));
    }
}
