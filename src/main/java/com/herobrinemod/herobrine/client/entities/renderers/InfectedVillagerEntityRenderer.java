package com.herobrinemod.herobrine.client.entities.renderers;

import com.herobrinemod.herobrine.HerobrineMod;
import com.herobrinemod.herobrine.client.entities.models.HerobrineModelLayers;
import com.herobrinemod.herobrine.client.entities.models.InfectedVillagerEntityModel;
import com.herobrinemod.herobrine.entities.InfectedVillagerEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.feature.HeadFeatureRenderer;
import net.minecraft.client.render.entity.feature.VillagerClothingFeatureRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;

@Environment(EnvType.CLIENT)
public class InfectedVillagerEntityRenderer extends MobEntityRenderer<InfectedVillagerEntity, InfectedVillagerEntityModel> {
    public InfectedVillagerEntityRenderer(EntityRendererFactory.Context context) {
        super(context, new InfectedVillagerEntityModel(context.getPart(HerobrineModelLayers.INFECTED_VILLAGER_MODEL_LAYER)), 0.5f);
        this.addFeature(new InfectedVillagerEyesFeatureRenderer(this));
        this.addFeature(new HeadFeatureRenderer<>(this, context.getModelLoader(), context.getHeldItemRenderer()));
        this.addFeature(new VillagerClothingFeatureRenderer<>(this, context.getResourceManager(), "villager"));
    }

    @Override
    public Identifier getTexture(InfectedVillagerEntity entity) {
        return new Identifier(HerobrineMod.MODID, "textures/entity/infected_villager.png");
    }

    @Override
    protected void scale(InfectedVillagerEntity entity, @NotNull MatrixStack matrixStack, float f) {
        matrixStack.scale(0.9375f, 0.9375f, 0.9375f);
    }
}
