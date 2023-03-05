package com.herobrinemod.herobrine.client.entities.renderers;

import com.herobrinemod.herobrine.HerobrineMod;
import com.herobrinemod.herobrine.client.entities.models.HerobrineMageEntityModel;
import com.herobrinemod.herobrine.client.entities.models.HerobrineModelLayers;
import com.herobrinemod.herobrine.entities.HerobrineEntity;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;

public class HerobrineMageEntityRenderer extends MobEntityRenderer<HerobrineEntity, HerobrineMageEntityModel> {

    public HerobrineMageEntityRenderer(EntityRendererFactory.Context context) {
        super(context, new HerobrineMageEntityModel(context.getPart(HerobrineModelLayers.HEROBRINE_MODEL_LAYER)), 0.5f);
        this.addFeature(new HerobrineEyesFeatureRenderer(this));
    }

    @Override
    public Identifier getTexture(HerobrineEntity entity) {
        return new Identifier(HerobrineMod.MODID, "textures/entity/herobrine.png");
    }

    @Override
    protected void scale(HerobrineEntity herobrineEntity, @NotNull MatrixStack matrixStack, float f) {
        matrixStack.scale(0.9375f, 0.9375f, 0.9375f);
    }
}
