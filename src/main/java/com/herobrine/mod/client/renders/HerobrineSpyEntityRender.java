package com.herobrine.mod.client.renders;

import com.herobrine.mod.HerobrineMod;
import com.herobrine.mod.client.models.HerobrineSpyEntityModel;
import com.herobrine.mod.entities.HerobrineSpyEntity;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
public class HerobrineSpyEntityRender extends MobRenderer<HerobrineSpyEntity, HerobrineSpyEntityModel> {
    private HerobrineSpyEntityRender(EntityRendererManager manager) {
        super(manager, new HerobrineSpyEntityModel(0.0F, false), 0.5F);
    }

    @NotNull
    @Override
    public ResourceLocation getEntityTexture(@NotNull HerobrineSpyEntity entity) {
        return HerobrineMod.location("textures/entity/herobrine.png");
    }

    public static class RenderFactory implements IRenderFactory {
        @Override
        public EntityRenderer<? super HerobrineSpyEntity> createRenderFor(EntityRendererManager manager) {
            return new HerobrineSpyEntityRender(manager);
        }
    }
}