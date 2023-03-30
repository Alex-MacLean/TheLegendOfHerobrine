package com.herobrinemod.herobrine.client.entities.renderers;

import com.herobrinemod.herobrine.HerobrineMod;
import com.herobrinemod.herobrine.client.entities.models.HerobrineModelLayers;
import com.herobrinemod.herobrine.client.entities.models.InfectedCowEntityModel;
import com.herobrinemod.herobrine.entities.InfectedCowEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class InfectedCowEntityRenderer extends MobEntityRenderer<InfectedCowEntity, InfectedCowEntityModel> {
    public InfectedCowEntityRenderer(EntityRendererFactory.Context context) {
        super(context, new InfectedCowEntityModel((context.getPart(HerobrineModelLayers.INFECTED_COW_MODEL_LAYER))), 0.7f);
        this.addFeature(new InfectedCowEyesFeatureRenderer(this));
    }

    @Override
    public Identifier getTexture(InfectedCowEntity entity) {
        return new Identifier(HerobrineMod.MODID, "textures/entity/infected_cow.png");
    }
}
