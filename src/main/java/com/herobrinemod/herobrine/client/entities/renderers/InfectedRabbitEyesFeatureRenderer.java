package com.herobrinemod.herobrine.client.entities.renderers;

import com.herobrinemod.herobrine.HerobrineMod;
import com.herobrinemod.herobrine.client.entities.models.InfectedRabbitEntityModel;
import com.herobrinemod.herobrine.entities.InfectedRabbitEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.entity.feature.EyesFeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class InfectedRabbitEyesFeatureRenderer extends EyesFeatureRenderer<InfectedRabbitEntity, InfectedRabbitEntityModel> {
    public static Identifier EYES_TEXTURE = new Identifier(HerobrineMod.MODID, "textures/entity/eyes/infected_rabbit/infected_rabbit.png");
    public InfectedRabbitEyesFeatureRenderer(FeatureRendererContext<InfectedRabbitEntity, InfectedRabbitEntityModel> featureRendererContext) {
        super(featureRendererContext);
    }

    @Override
    public RenderLayer getEyesTexture() {
        return RenderLayer.getEyes(EYES_TEXTURE);
    }
}
