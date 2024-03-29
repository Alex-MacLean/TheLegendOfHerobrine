package com.herobrinemod.herobrine.client.entities.renderers;

import com.herobrinemod.herobrine.HerobrineMod;
import com.herobrinemod.herobrine.client.entities.models.InfectedVillagerEntityModel;
import com.herobrinemod.herobrine.entities.InfectedVillagerEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.entity.feature.EyesFeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class InfectedVillagerEyesFeatureRenderer extends EyesFeatureRenderer<InfectedVillagerEntity, InfectedVillagerEntityModel> {
    public InfectedVillagerEyesFeatureRenderer(FeatureRendererContext<InfectedVillagerEntity, InfectedVillagerEntityModel> featureRendererContext) {
        super(featureRendererContext);
    }

    @Override
    public RenderLayer getEyesTexture() {
        return RenderLayer.getEyes(new Identifier(HerobrineMod.MODID, "textures/entity/eyes/infected_villager.png"));
    }
}
