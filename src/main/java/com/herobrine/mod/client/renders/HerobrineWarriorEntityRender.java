package com.herobrine.mod.client.renders;

import com.herobrine.mod.HerobrineMod;
import com.herobrine.mod.client.models.HerobrineWarriorEntityModel;
import com.herobrine.mod.entities.HerobrineWarriorEntity;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.HeldItemLayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
public class HerobrineWarriorEntityRender extends MobRenderer<HerobrineWarriorEntity, HerobrineWarriorEntityModel> {
    private HerobrineWarriorEntityRender(EntityRendererManager manager) {
        super(manager, new HerobrineWarriorEntityModel(0.0F, false), 0.5F);
        this.addLayer(new HeldItemLayer<>(this));
    }

    @NotNull
    @Override
    public ResourceLocation getEntityTexture(@NotNull HerobrineWarriorEntity entity) {
        return HerobrineMod.location("textures/entity/herobrine.png");
    }

    @SuppressWarnings("rawtypes")
    public static class RenderFactory implements IRenderFactory {
        @Override
        public EntityRenderer<? super HerobrineWarriorEntity> createRenderFor(EntityRendererManager manager) {
            return new HerobrineWarriorEntityRender(manager);
        }
    }
}