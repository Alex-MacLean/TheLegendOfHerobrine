package com.herobrinemod.herobrine.client.entities.renderers;

import com.herobrinemod.herobrine.HerobrineMod;
import com.herobrinemod.herobrine.client.entities.models.InfectedDonkeyEntityModel;
import com.herobrinemod.herobrine.entities.InfectedDonkeyEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.entity.feature.EyesFeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class InfectedDonkeyEyesFeatureRenderer extends EyesFeatureRenderer<InfectedDonkeyEntity, InfectedDonkeyEntityModel> {
    public InfectedDonkeyEyesFeatureRenderer(FeatureRendererContext<InfectedDonkeyEntity, InfectedDonkeyEntityModel> featureRendererContext) {
        super(featureRendererContext);
    }

    @Override
    public RenderLayer getEyesTexture() {
        return RenderLayer.getEyes(new Identifier(HerobrineMod.MODID, "textures/entity/eyes/infected_donkey.png"));
    }
}
