package com.herobrinemod.herobrine.client.entities.renderers;

import com.herobrinemod.herobrine.HerobrineMod;
import com.herobrinemod.herobrine.client.entities.models.HerobrineModelLayers;
import com.herobrinemod.herobrine.client.entities.models.InfectedBatEntityModel;
import com.herobrinemod.herobrine.entities.InfectedBatEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import org.jetbrains.annotations.NotNull;

@Environment(EnvType.CLIENT)
public class InfectedBatEntityRenderer extends MobEntityRenderer<InfectedBatEntity, InfectedBatEntityModel> {
    public InfectedBatEntityRenderer(EntityRendererFactory.Context context) {
        super(context, new InfectedBatEntityModel(context.getPart(HerobrineModelLayers.INFECTED_BAT_MODEL_LAYER)), 0.25f);
        this.addFeature(new InfectedBatEyesFeatureRenderer(this));
    }

    @Override
    public Identifier getTexture(InfectedBatEntity entity) {
        return new Identifier(HerobrineMod.MODID, "textures/entity/infected_bat.png");
    }

    @Override
    protected void scale(InfectedBatEntity entity, @NotNull MatrixStack matrixStack, float f) {
        matrixStack.scale(0.35f, 0.35f, 0.35f);
    }

    @Override
    protected void setupTransforms(@NotNull InfectedBatEntity entity, MatrixStack matrixStack, float f, float g, float h) {
        if (entity.isRoosting()) {
            matrixStack.translate(0.0f, -0.1f, 0.0f);
        } else {
            matrixStack.translate(0.0f, MathHelper.cos(f * 0.3f) * 0.1f, 0.0f);
        }
        super.setupTransforms(entity, matrixStack, f, g, h);
    }
}
