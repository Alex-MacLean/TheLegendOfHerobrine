package com.herobrinemod.herobrine.client.entities.renderers;

import com.google.common.collect.Maps;
import com.herobrinemod.herobrine.client.entities.models.InfectedHorseEntityModel;
import com.herobrinemod.herobrine.entities.InfectedHorseEntity;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.passive.HorseMarking;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;

import java.util.Map;

public class InfectedHorseMarkingFeatureRenderer extends FeatureRenderer<InfectedHorseEntity, InfectedHorseEntityModel> {
    private static final Map<HorseMarking, Identifier> TEXTURES = Util.make(Maps.newEnumMap(HorseMarking.class), textures -> {
        textures.put(HorseMarking.NONE, null);
        textures.put(HorseMarking.WHITE, new Identifier("textures/entity/horse/horse_markings_white.png"));
        textures.put(HorseMarking.WHITE_FIELD, new Identifier("textures/entity/horse/horse_markings_whitefield.png"));
        textures.put(HorseMarking.WHITE_DOTS, new Identifier("textures/entity/horse/horse_markings_whitedots.png"));
        textures.put(HorseMarking.BLACK_DOTS, new Identifier("textures/entity/horse/horse_markings_blackdots.png"));
    });

    public InfectedHorseMarkingFeatureRenderer(FeatureRendererContext<InfectedHorseEntity, InfectedHorseEntityModel> featureRendererContext) {
        super(featureRendererContext);
    }

    @Override
    public void render(MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, InfectedHorseEntity entity, float f, float g, float h, float j, float k, float l) {
        Identifier identifier = TEXTURES.get(entity.getMarking());
        if (identifier == null || entity.isInvisible()) {
            return;
        }
        VertexConsumer vertexConsumer = vertexConsumerProvider.getBuffer(RenderLayer.getEntityTranslucent(identifier));
        (this.getContextModel()).render(matrixStack, vertexConsumer, i, LivingEntityRenderer.getOverlay(entity, 0.0f), 1.0f, 1.0f, 1.0f, 1.0f);
    }
}
