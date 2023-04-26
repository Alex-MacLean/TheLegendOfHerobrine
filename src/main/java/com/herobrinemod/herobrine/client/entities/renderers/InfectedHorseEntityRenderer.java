package com.herobrinemod.herobrine.client.entities.renderers;

import com.google.common.collect.Maps;
import com.herobrinemod.herobrine.HerobrineMod;
import com.herobrinemod.herobrine.client.entities.models.HerobrineModelLayers;
import com.herobrinemod.herobrine.client.entities.models.InfectedHorseEntityModel;
import com.herobrinemod.herobrine.entities.InfectedHorseEntity;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.passive.HorseColor;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

public class InfectedHorseEntityRenderer extends MobEntityRenderer<InfectedHorseEntity, InfectedHorseEntityModel> {
    private static final Map<HorseColor, Identifier> TEXTURES = Util.make(Maps.newEnumMap(HorseColor.class), map -> {
        map.put(HorseColor.WHITE, new Identifier(HerobrineMod.MODID, "textures/entity/infected_horse/horse_white.png"));
        map.put(HorseColor.CREAMY, new Identifier(HerobrineMod.MODID, "textures/entity/infected_horse/horse_creamy.png"));
        map.put(HorseColor.CHESTNUT, new Identifier(HerobrineMod.MODID, "textures/entity/infected_horse/horse_chestnut.png"));
        map.put(HorseColor.BROWN, new Identifier(HerobrineMod.MODID, "textures/entity/infected_horse/horse_brown.png"));
        map.put(HorseColor.BLACK, new Identifier(HerobrineMod.MODID, "textures/entity/infected_horse/horse_black.png"));
        map.put(HorseColor.GRAY, new Identifier(HerobrineMod.MODID, "textures/entity/infected_horse/horse_gray.png"));
        map.put(HorseColor.DARK_BROWN, new Identifier(HerobrineMod.MODID, "textures/entity/infected_horse/horse_darkbrown.png"));
    });
    public InfectedHorseEntityRenderer(EntityRendererFactory.Context context) {
        super(context, new InfectedHorseEntityModel(context.getPart(HerobrineModelLayers.INFECTED_HORSE_MODEL_LAYER)), 0.75f);
        this.addFeature(new InfectedHorseMarkingFeatureRenderer(this));
        this.addFeature(new InfectedHorseEyesFeatureRenderer(this));
    }

    @Override
    protected void scale(InfectedHorseEntity entity, @NotNull MatrixStack matrixStack, float f) {
        matrixStack.scale(1.1f, 1.1f, 1.1f);
    }

    @Override
    public Identifier getTexture(InfectedHorseEntity entity) {
        return TEXTURES.get(entity.getVariant());
    }
}
