package com.herobrinemod.herobrine.client.entities.renderers;

import com.herobrinemod.herobrine.HerobrineMod;
import com.herobrinemod.herobrine.client.entities.models.HerobrineModelLayers;
import com.herobrinemod.herobrine.client.entities.models.InfectedPigEntityModel;
import com.herobrinemod.herobrine.entities.InfectedPigEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class InfectedPigEntityRenderer extends MobEntityRenderer<InfectedPigEntity, InfectedPigEntityModel> {
    public InfectedPigEntityRenderer(EntityRendererFactory.Context context) {
        super(context, new InfectedPigEntityModel(context.getPart(HerobrineModelLayers.INFECTED_PIG_MODEL_LAYER)), 0.7f);
        this.addFeature(new InfectedPigEyesFeatureRenderer(this));
    }

    @Override
    public Identifier getTexture(InfectedPigEntity entity) {
        return new Identifier(HerobrineMod.MODID, "textures/entity/infected_pig.png");
    }
}
