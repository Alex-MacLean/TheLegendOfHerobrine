package com.herobrine.mod.client.renders;

import com.herobrine.mod.HerobrineMod;
import com.herobrine.mod.client.models.HerobrineEntityModel;
import com.herobrine.mod.entities.HerobrineEntity;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.*;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
public class HerobrineEntityRender extends MobRenderer<HerobrineEntity, HerobrineEntityModel> {
    private HerobrineEntityRender(EntityRendererManager manager) {
        super(manager, new HerobrineEntityModel(0.0F, false), 0.5F);
        this.addLayer(new HeldItemLayer<>(this));
        this.addLayer(new BipedArmorLayer<>(this, new BipedModel<>(0.5F), new BipedModel<>(1.0F)));
        this.addLayer(new ArrowLayer<>(this));
        this.addLayer(new HeadLayer<>(this));
        this.addLayer(new ElytraLayer<>(this));
    }

    @NotNull
    @Override
    public ResourceLocation getEntityTexture(@NotNull HerobrineEntity entity) {
        return HerobrineMod.location("textures/entity/herobrine.png");
    }

    public static class RenderFactory implements IRenderFactory {
        @Override
        public EntityRenderer<? super HerobrineEntity> createRenderFor(EntityRendererManager manager) {
            return new HerobrineEntityRender(manager);
        }
    }
}