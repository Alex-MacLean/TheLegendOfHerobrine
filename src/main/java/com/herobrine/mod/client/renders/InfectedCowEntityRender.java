package com.herobrine.mod.client.renders;

import com.herobrine.mod.HerobrineMod;
import com.herobrine.mod.client.models.InfectedCowEntityModel;
import com.herobrine.mod.client.renders.layers.eyes.InfectedCowEyesLayer;
import com.herobrine.mod.entities.InfectedCowEntity;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
public class InfectedCowEntityRender extends MobRenderer<InfectedCowEntity, InfectedCowEntityModel> {
    public InfectedCowEntityRender(EntityRendererManager manager) {
        super(manager, new InfectedCowEntityModel(), 0.7F);
        this.addLayer(new InfectedCowEyesLayer(this));
    }

    @NotNull
    @Override
    public ResourceLocation getEntityTexture(@NotNull InfectedCowEntity entity) {
        return HerobrineMod.location("textures/entity/infected_cow.png");
    }

    @SuppressWarnings("rawtypes")
    public static class RenderFactory implements IRenderFactory {
        @Override
        public EntityRenderer<? super InfectedCowEntity> createRenderFor(EntityRendererManager manager) {
            return new InfectedCowEntityRender(manager);
        }
    }
}