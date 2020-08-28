package com.herobrine.mod.client.renders;

import com.herobrine.mod.HerobrineMod;
import com.herobrine.mod.client.models.InfectedWolfEntityModel;
import com.herobrine.mod.client.renders.layers.eyes.InfectedWolfEyesLayer;
import com.herobrine.mod.entities.InfectedWolfEntity;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
public class InfectedWolfEntityRender extends MobRenderer<InfectedWolfEntity, InfectedWolfEntityModel> {
    public InfectedWolfEntityRender(EntityRendererManager manager) {
        super(manager, new InfectedWolfEntityModel(), 0.5F);
        this.addLayer(new InfectedWolfEyesLayer(this));
    }

    @Override
    protected float handleRotationFloat(@NotNull InfectedWolfEntity livingBase, float partialTicks) {
        return livingBase.getTailRotation();
    }

    @NotNull
    @Override
    public ResourceLocation getEntityTexture(@NotNull InfectedWolfEntity entity) {
        return HerobrineMod.location("textures/entity/infected_wolf.png");
    }

    @SuppressWarnings("rawtypes")
    public static class RenderFactory implements IRenderFactory {
        @Override
        public EntityRenderer<? super InfectedWolfEntity> createRenderFor(EntityRendererManager manager) {
            return new InfectedWolfEntityRender(manager);
        }
    }
}
