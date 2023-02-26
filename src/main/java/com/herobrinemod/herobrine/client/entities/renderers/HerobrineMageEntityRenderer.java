package com.herobrinemod.herobrine.client.entities.renderers;

import com.herobrinemod.herobrine.HerobrineMod;
import com.herobrinemod.herobrine.client.entities.models.HerobrineMageEntityModel;
import com.herobrinemod.herobrine.client.entities.models.HerobrineModelLayers;
import com.herobrinemod.herobrine.entities.HerobrineEntity;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;

public class HerobrineMageEntityRenderer extends MobEntityRenderer<HerobrineEntity, HerobrineMageEntityModel> {

    public HerobrineMageEntityRenderer(EntityRendererFactory.Context context) {
        super(context, new HerobrineMageEntityModel(context.getPart(HerobrineModelLayers.HEROBRINE_MODEL_LAYER)), 0.5f);
    }

    @Override
    public Identifier getTexture(HerobrineEntity entity) {
        return new Identifier(HerobrineMod.MODID, "textures/entity/herobrine.png");
    }
}
