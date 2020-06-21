package com.herobrine.mod.client.renders;

import com.herobrine.mod.HerobrineMod;
import com.herobrine.mod.client.models.InfectedDonkeyEntityModel;
import com.herobrine.mod.entities.InfectedDonkeyEntity;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import org.jetbrains.annotations.NotNull;

public class InfectedDonkeyEntityRender extends MobRenderer<InfectedDonkeyEntity, InfectedDonkeyEntityModel> {
    private final float scale;

    public InfectedDonkeyEntityRender(EntityRendererManager manager) {
        super(manager, new InfectedDonkeyEntityModel(0.0F), 0.75F);
        this.scale = 0.87F;
    }

    @Override
    public @NotNull ResourceLocation getEntityTexture(@NotNull InfectedDonkeyEntity entity) {
        return HerobrineMod.location("textures/entity/infected_donkey.png");
    }

    @Override
    protected void preRenderCallback(@NotNull InfectedDonkeyEntity entitylivingbaseIn, @NotNull MatrixStack matrixStackIn, float partialTickTime) {
        matrixStackIn.scale(this.scale, this.scale, this.scale);
        super.preRenderCallback(entitylivingbaseIn, matrixStackIn, partialTickTime);
    }

    @SuppressWarnings("rawtypes")
    public static class RenderFactory implements IRenderFactory {
        @Override
        public EntityRenderer<? super InfectedDonkeyEntity> createRenderFor(EntityRendererManager manager) {
            return new InfectedDonkeyEntityRender(manager);
        }
    }
}