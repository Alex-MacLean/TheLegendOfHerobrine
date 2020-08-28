package com.herobrine.mod.client.renders;

import com.herobrine.mod.HerobrineMod;
import com.herobrine.mod.client.models.InfectedSheepEntityModel;
import com.herobrine.mod.client.renders.layers.InfectedSheepWoolLayer;
import com.herobrine.mod.client.renders.layers.eyes.InfectedSheepEyesLayer;
import com.herobrine.mod.entities.InfectedSheepEntity;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
public class InfectedSheepEntityRender extends MobRenderer<InfectedSheepEntity, InfectedSheepEntityModel<InfectedSheepEntity>> {

    public InfectedSheepEntityRender(EntityRendererManager renderManagerIn) {
        super(renderManagerIn, new InfectedSheepEntityModel<>(), 0.7F);
        this.addLayer(new InfectedSheepEyesLayer(this));
        this.addLayer(new InfectedSheepWoolLayer(this));
    }

    @NotNull
    public ResourceLocation getEntityTexture(@NotNull InfectedSheepEntity entity) {
        return HerobrineMod.location("textures/entity/infected_sheep/infected_sheep.png");
    }

    @SuppressWarnings("rawtypes")
    public static class RenderFactory implements IRenderFactory {
        @Override
        public EntityRenderer<? super InfectedSheepEntity> createRenderFor(EntityRendererManager manager) {
            return new InfectedSheepEntityRender(manager);
        }
    }
}