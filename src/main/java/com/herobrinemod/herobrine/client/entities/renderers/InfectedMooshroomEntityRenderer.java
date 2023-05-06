package com.herobrinemod.herobrine.client.entities.renderers;

import com.google.common.collect.Maps;
import com.herobrinemod.herobrine.HerobrineMod;
import com.herobrinemod.herobrine.client.entities.models.HerobrineModelLayers;
import com.herobrinemod.herobrine.client.entities.models.InfectedCowEntityModel;
import com.herobrinemod.herobrine.entities.InfectedMooshroomEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

@Environment(EnvType.CLIENT)
public class InfectedMooshroomEntityRenderer extends MobEntityRenderer<InfectedMooshroomEntity, InfectedCowEntityModel<InfectedMooshroomEntity>> {
    private static final Map<InfectedMooshroomEntity.Type, Identifier> TEXTURES = Util.make(Maps.newHashMap(), map -> {
        map.put(InfectedMooshroomEntity.Type.BROWN, new Identifier(HerobrineMod.MODID, "textures/entity/infected_brown_mooshroom.png"));
        map.put(InfectedMooshroomEntity.Type.RED, new Identifier(HerobrineMod.MODID, "textures/entity/infected_red_mooshroom.png"));
    });
    public InfectedMooshroomEntityRenderer(EntityRendererFactory.Context context) {
        super(context, new InfectedCowEntityModel<>(context.getPart(HerobrineModelLayers.INFECTED_COW_MODEL_LAYER)), 0.7f);
        this.addFeature(new InfectedMooshroomEyesFeatureRenderer(this));
        this.addFeature(new InfectedMooshroomMushroomFeatureRenderer(this, context.getBlockRenderManager()));
    }

    @Override
    public Identifier getTexture(@NotNull InfectedMooshroomEntity entity) {
        return TEXTURES.get(entity.getVariant());
    }
}
