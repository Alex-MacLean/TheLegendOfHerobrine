package com.herobrinemod.herobrine.client.entities.renderers;

import com.herobrinemod.herobrine.HerobrineMod;
import com.herobrinemod.herobrine.client.entities.models.HerobrineModelLayers;
import com.herobrinemod.herobrine.client.entities.models.InfectedCamelEntityModel;
import com.herobrinemod.herobrine.entities.InfectedCamelEntity;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;

public class InfectedCamelEntityRenderer extends MobEntityRenderer<InfectedCamelEntity, InfectedCamelEntityModel> {

    public InfectedCamelEntityRenderer(EntityRendererFactory.Context context) {
        super(context, new InfectedCamelEntityModel(context.getPart(HerobrineModelLayers.INFECTED_CAMEL_MODEL_LAYER)), 0.7f);
        this.addFeature(new InfectedCamelEyesFeatureRenderer(this));
    }

    @Override
    public Identifier getTexture(InfectedCamelEntity entity) {
        return new Identifier(HerobrineMod.MODID, "textures/entity/infected_camel.png");
    }
}