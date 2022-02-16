package com.github.alexmaclean.herobrine.client.entities.renderers;

import com.github.alexmaclean.herobrine.HerobrineMod;
import com.github.alexmaclean.herobrine.client.entities.models.HerobrineEntityModel;
import com.github.alexmaclean.herobrine.client.entities.models.HerobrineEntityModelLayers;
import com.github.alexmaclean.herobrine.entities.HerobrineEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.feature.HeldItemFeatureRenderer;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class HerobrineEntityRenderer extends MobEntityRenderer<HerobrineEntity, HerobrineEntityModel> {
    public HerobrineEntityRenderer(EntityRendererFactory.Context context) {
        super(context, new HerobrineEntityModel(context.getPart(HerobrineEntityModelLayers.HEROBRINE_MODEL_LAYER), false), 0.5f);
        this.addFeature(new HeldItemFeatureRenderer<>(this));
    }

    @Override
    public Identifier getTexture(HerobrineEntity entity) {
        return new Identifier(HerobrineMod.MODID, "textures/entity/herobrine.png");
    }
}
