package com.herobrinemod.herobrine.client.entities.renderers;

import com.herobrinemod.herobrine.HerobrineMod;
import com.herobrinemod.herobrine.client.entities.models.HerobrineModelLayers;
import com.herobrinemod.herobrine.client.entities.models.InfectedWolfEntityModel;
import com.herobrinemod.herobrine.entities.InfectedWolfEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;

@Environment(EnvType.CLIENT)
public class InfectedWolfEntityRenderer extends MobEntityRenderer<InfectedWolfEntity, InfectedWolfEntityModel> {
    public InfectedWolfEntityRenderer(EntityRendererFactory.Context context) {
        super(context, new InfectedWolfEntityModel(context.getPart(HerobrineModelLayers.INFECTED_WOLF_MODEL_LAYER)), 0.5f);
        this.addFeature(new InfectedWolfEyesFeatureRenderer(this));
    }

    @Override
    protected float getAnimationProgress(@NotNull InfectedWolfEntity entity, float f) {
        return (float) Math.PI / 5.0f;
    }

    @Override
    public void render(InfectedWolfEntity entity, float f, float g, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i) {
        if (entity.isFurWet()) {
            float h = entity.getFurWetBrightnessMultiplier(g);
            (this.model).setColorMultiplier(h, h, h);
        }
        super.render(entity, f, g, matrixStack, vertexConsumerProvider, i);
        if (entity.isFurWet()) {
            (this.model).setColorMultiplier(1.0f, 1.0f, 1.0f);
        }
    }

    @Override
    public Identifier getTexture(InfectedWolfEntity entity) {
        return new Identifier(HerobrineMod.MODID, "textures/entity/infected_wolf.png");
    }
}
