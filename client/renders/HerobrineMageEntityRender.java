package com.herobrine.mod.client.renders;

import com.herobrine.mod.HerobrineMod;
import com.herobrine.mod.client.models.HerobrineMageEntityModel;
import com.herobrine.mod.entities.HerobrineMageEntity;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
public class HerobrineMageEntityRender extends MobRenderer<HerobrineMageEntity, HerobrineMageEntityModel<HerobrineMageEntity>> {
    private HerobrineMageEntityRender(EntityRendererManager manager) {
        super(manager, new HerobrineMageEntityModel<>(0.0F, false), 0.5F);
    }

    @NotNull
    @Override
    public ResourceLocation getEntityTexture(@NotNull HerobrineMageEntity entity) {
        return HerobrineMod.location("textures/entity/herobrine.png");
    }

    @SuppressWarnings("rawtypes")
    public static class RenderFactory implements IRenderFactory {
        @Override
        public EntityRenderer<? super HerobrineMageEntity> createRenderFor(EntityRendererManager manager) {
            return new HerobrineMageEntityRender(manager);
        }
    }
}