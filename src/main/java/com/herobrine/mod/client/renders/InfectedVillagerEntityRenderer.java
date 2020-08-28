package com.herobrine.mod.client.renders;

import com.herobrine.mod.HerobrineMod;
import com.herobrine.mod.client.models.InfectedVillagerEntityModel;
import com.herobrine.mod.client.renders.layers.eyes.InfectedVillagerEyesLayer;
import com.herobrine.mod.entities.InfectedVillagerEntity;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
public class InfectedVillagerEntityRenderer extends MobRenderer<InfectedVillagerEntity, InfectedVillagerEntityModel> {

    public InfectedVillagerEntityRenderer(EntityRendererManager renderManagerIn) {
        super(renderManagerIn, new InfectedVillagerEntityModel(0.0f), 0.5f);
        this.addLayer(new InfectedVillagerEyesLayer(this));
    }

    @NotNull
    @Override
    public ResourceLocation getEntityTexture(@NotNull InfectedVillagerEntity entity) {
        return HerobrineMod.location("textures/entity/infected_villager.png");
    }

    @SuppressWarnings("rawtypes")
    public static class RenderFactory implements IRenderFactory {
        @Override
        public EntityRenderer<? super InfectedVillagerEntity> createRenderFor(EntityRendererManager manager) {
            return new InfectedVillagerEntityRenderer(manager);
        }
    }
}