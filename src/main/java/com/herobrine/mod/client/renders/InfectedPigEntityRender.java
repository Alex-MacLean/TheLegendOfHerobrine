package com.herobrine.mod.client.renders;

import com.herobrine.mod.HerobrineMod;
import com.herobrine.mod.client.models.InfectedPigEntityModel;
import com.herobrine.mod.client.renders.layers.InfectedPigEyesLayer;
import com.herobrine.mod.entities.InfectedPigEntity;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
public class InfectedPigEntityRender extends MobRenderer<InfectedPigEntity, InfectedPigEntityModel> {
    public InfectedPigEntityRender(EntityRendererManager manager) {
        super(manager, new InfectedPigEntityModel(), 0.7F);
        this.addLayer(new InfectedPigEyesLayer(this));
    }

    @NotNull
    @Override
    public ResourceLocation getTextureLocation(@NotNull InfectedPigEntity entity) {
        return HerobrineMod.location("textures/entity/infected_pig.png");
    }

    @SuppressWarnings("rawtypes")
    public static class RenderFactory implements IRenderFactory {
        @Override
        public EntityRenderer<? super InfectedPigEntity> createRenderFor(EntityRendererManager manager) {
            return new InfectedPigEntityRender(manager);
        }
    }
}