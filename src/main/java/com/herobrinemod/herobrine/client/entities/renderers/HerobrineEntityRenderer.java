package com.herobrinemod.herobrine.client.entities.renderers;

import com.herobrinemod.herobrine.HerobrineMod;
import com.herobrinemod.herobrine.client.entities.models.HerobrineEntityModel;
import com.herobrinemod.herobrine.client.entities.models.HerobrineModelLayers;
import com.herobrinemod.herobrine.entities.HerobrineEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.feature.HeldItemFeatureRenderer;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class HerobrineEntityRenderer extends MobEntityRenderer<HerobrineEntity, HerobrineEntityModel> {
    public HerobrineEntityRenderer(EntityRendererFactory.Context context) {
        super(context, new HerobrineEntityModel(context.getPart(HerobrineModelLayers.HEROBRINE_MODEL_LAYER), false), 0.5f);
        this.addFeature(new HeldItemFeatureRenderer<>(this, context.getHeldItemRenderer()));
    }

    @Override
    public Identifier getTexture(HerobrineEntity entity) {
        return new Identifier(HerobrineMod.MODID, "textures/entity/herobrine.png");
    }
}
