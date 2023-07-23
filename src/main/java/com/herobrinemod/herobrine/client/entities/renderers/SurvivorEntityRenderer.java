package com.herobrinemod.herobrine.client.entities.renderers;

import com.herobrinemod.herobrine.client.entities.models.HerobrineModelLayers;
import com.herobrinemod.herobrine.client.entities.models.SurvivorEntityModel;
import com.herobrinemod.herobrine.entities.SurvivorEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.feature.ArmorFeatureRenderer;
import net.minecraft.client.render.entity.feature.HeldItemFeatureRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;

@Environment(EnvType.CLIENT)
public class SurvivorEntityRenderer extends MobEntityRenderer<SurvivorEntity, SurvivorEntityModel> {
    public SurvivorEntityRenderer(EntityRendererFactory.Context context) {
        super(context, new SurvivorEntityModel(context.getPart(HerobrineModelLayers.SURVIVOR_MODEL_LAYER)), 0.5f);
        this.addFeature(new HeldItemFeatureRenderer<>(this, context.getHeldItemRenderer()));
        this.addFeature(new ArmorFeatureRenderer<>(this, new SurvivorEntityModel(context.getPart(HerobrineModelLayers.SURVIVOR_INNER_ARMOR)), new SurvivorEntityModel(context.getPart(HerobrineModelLayers.SURVIVOR_OUTER_ARMOR)), context.getModelManager()));
    }

    @Override
    protected void scale(@NotNull SurvivorEntity entity, @NotNull MatrixStack matrixStack, float f) {
        matrixStack.scale(0.9375f, 0.9375f, 0.9375f);
    }

    @Override
    public Identifier getTexture(@NotNull SurvivorEntity entity) {
        return entity.getTexture();
    }
}