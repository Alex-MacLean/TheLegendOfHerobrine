package com.herobrinemod.herobrine.client.entities.renderers;

import com.herobrinemod.herobrine.HerobrineMod;
import com.herobrinemod.herobrine.client.entities.models.HerobrineModelLayers;
import com.herobrinemod.herobrine.client.entities.models.InfectedChickenEntityModel;
import com.herobrinemod.herobrine.entities.InfectedChickenEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import org.jetbrains.annotations.NotNull;

@Environment(EnvType.CLIENT)
public class InfectedChickenEntityRenderer extends MobEntityRenderer<InfectedChickenEntity, InfectedChickenEntityModel> {
    public InfectedChickenEntityRenderer(EntityRendererFactory.Context context) {
        super(context, new InfectedChickenEntityModel(context.getPart(HerobrineModelLayers.INFECTED_CHICKEN_MODEL_LAYER)), 0.3f);
        this.addFeature(new InfectedChickenEyesFeatureRenderer(this));
    }

    @Override
    protected float getAnimationProgress(@NotNull InfectedChickenEntity entity, float f) {
        return (MathHelper.sin(MathHelper.lerp(f, entity.prevFlapProgress, entity.flapProgress)) + 1.0f) * MathHelper.lerp(f, entity.prevMaxWingDeviation, entity.maxWingDeviation);
    }

    @Override
    public Identifier getTexture(InfectedChickenEntity entity) {
        return new Identifier(HerobrineMod.MODID, "textures/entity/infected_chicken.png");
    }
}
