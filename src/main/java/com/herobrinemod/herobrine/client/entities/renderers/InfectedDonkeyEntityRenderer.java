package com.herobrinemod.herobrine.client.entities.renderers;

import com.herobrinemod.herobrine.HerobrineMod;
import com.herobrinemod.herobrine.client.entities.models.HerobrineModelLayers;
import com.herobrinemod.herobrine.client.entities.models.InfectedDonkeyEntityModel;
import com.herobrinemod.herobrine.entities.InfectedDonkeyEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;

@Environment(EnvType.CLIENT)
public class InfectedDonkeyEntityRenderer extends MobEntityRenderer<InfectedDonkeyEntity, InfectedDonkeyEntityModel> {
    public InfectedDonkeyEntityRenderer(EntityRendererFactory.Context context) {
        super(context, new InfectedDonkeyEntityModel(context.getPart(HerobrineModelLayers.INFECTED_DONKEY_MODEL_LAYER)), 0.75f);
        this.addFeature(new InfectedDonkeyEyesFeatureRenderer(this));
    }

    @Override
    protected void scale(InfectedDonkeyEntity entity, @NotNull MatrixStack matrixStack, float f) {
        matrixStack.scale(0.87f, 0.87f, 0.87f);
    }

    @Override
    public Identifier getTexture(InfectedDonkeyEntity entity) {
        return new Identifier(HerobrineMod.MODID, "textures/entity/infected_donkey.png");
    }
}
