package com.herobrinemod.herobrine.client.entities.renderers;

import com.herobrinemod.herobrine.HerobrineMod;
import com.herobrinemod.herobrine.client.entities.models.HerobrineModelLayers;
import com.herobrinemod.herobrine.client.entities.models.InfectedSheepEntityModel;
import com.herobrinemod.herobrine.entities.InfectedSheepEntity;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;

public class InfectedSheepEntityRenderer extends MobEntityRenderer<InfectedSheepEntity, InfectedSheepEntityModel> {
    public InfectedSheepEntityRenderer(EntityRendererFactory.Context context) {
        super(context, new InfectedSheepEntityModel(context.getPart(HerobrineModelLayers.INFECTED_SHEEP_MODEL_LAYER)), 0.7f);
        this.addFeature(new InfectedSheepEyesFeatureRenderer(this));
        this.addFeature(new InfectedSheepWoolFeatureRenderer(this, context.getModelLoader()));
    }

    @Override
    public Identifier getTexture(InfectedSheepEntity entity) {
        return new Identifier(HerobrineMod.MODID, "textures/entity/infected_sheep.png");
    }
}
