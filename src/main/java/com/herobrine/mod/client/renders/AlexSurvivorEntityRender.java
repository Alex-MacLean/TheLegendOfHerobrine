package com.herobrine.mod.client.renders;

import com.herobrine.mod.client.models.AbstractSurvivorEntityModel;
import com.herobrine.mod.entities.AbstractSurvivorEntity;
import com.herobrine.mod.entities.AlexSurvivorEntity;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.*;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import org.jetbrains.annotations.NotNull;

public class AlexSurvivorEntityRender extends MobRenderer<AbstractSurvivorEntity, AbstractSurvivorEntityModel> {
    private AlexSurvivorEntityRender(EntityRendererManager manager) {
        super(manager, new AbstractSurvivorEntityModel(0.0F, true), 0.5F);
        this.addLayer(new BipedArmorLayer<>(this, new BipedModel<>(0.5F), new BipedModel<>(1.0F)));
        this.addLayer(new HeldItemLayer<>(this));
        this.addLayer(new ArrowLayer<>(this));
        this.addLayer(new HeadLayer<>(this));
        this.addLayer(new ElytraLayer<>(this));
    }

    @NotNull
    @Override
    public ResourceLocation getEntityTexture(@NotNull AbstractSurvivorEntity entity) {
        return new ResourceLocation("textures/entity/alex.png");
    }

    @SuppressWarnings("rawtypes")
    public static class RenderFactory implements IRenderFactory {
        @Override
        public EntityRenderer<? super AlexSurvivorEntity> createRenderFor(EntityRendererManager manager) {
            return new AlexSurvivorEntityRender(manager);
        }
    }
}
