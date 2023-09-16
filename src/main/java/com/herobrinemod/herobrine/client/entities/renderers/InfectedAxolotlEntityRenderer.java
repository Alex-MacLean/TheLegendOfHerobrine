package com.herobrinemod.herobrine.client.entities.renderers;

import com.google.common.collect.Maps;
import com.herobrinemod.herobrine.HerobrineMod;
import com.herobrinemod.herobrine.client.entities.models.InfectedAxolotlEntityModel;
import com.herobrinemod.herobrine.entities.InfectedAxolotlEntity;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.entity.passive.AxolotlEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import org.jetbrains.annotations.NotNull;

import java.util.Locale;
import java.util.Map;

public class InfectedAxolotlEntityRenderer extends MobEntityRenderer<InfectedAxolotlEntity, InfectedAxolotlEntityModel> {
    private static final Map<AxolotlEntity.Variant, Identifier> TEXTURES = Util.make(Maps.newHashMap(), variants -> {
        for (AxolotlEntity.Variant variant : AxolotlEntity.Variant.values()) {
            variants.put(variant, new Identifier(String.format(Locale.ROOT, HerobrineMod.MODID + ":textures/entity/infected_axolotl/axolotl_%s.png", variant.getName())));
        }
    });

    public InfectedAxolotlEntityRenderer(EntityRendererFactory.Context context) {
        super(context, new InfectedAxolotlEntityModel(context.getPart(EntityModelLayers.AXOLOTL)), 0.5f);
        this.addFeature(new InfectedAxolotlEyesFeatureRenderer(this));
    }

    @Override
    public Identifier getTexture(@NotNull InfectedAxolotlEntity axolotlEntity) {
        return TEXTURES.get(axolotlEntity.getVariant());
    }
}