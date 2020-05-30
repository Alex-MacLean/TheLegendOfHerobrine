package com.herobrine.mod.client.renders;

import com.herobrine.mod.HerobrineMod;
import com.herobrine.mod.client.models.FakeHerobrineMageEntityModel;
import com.herobrine.mod.entities.FakeHerobrineMageEntity;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
public class FakeHerobrineMageEntityRender extends MobRenderer<FakeHerobrineMageEntity, FakeHerobrineMageEntityModel<FakeHerobrineMageEntity>> {
    private FakeHerobrineMageEntityRender(EntityRendererManager manager) {
        super(manager, new FakeHerobrineMageEntityModel<>(0.0F, false), 0.5F);
    }

    @NotNull
    @Override
    public ResourceLocation getEntityTexture(@NotNull FakeHerobrineMageEntity entity) {
        return HerobrineMod.location("textures/entity/herobrine.png");
    }

    @SuppressWarnings("rawtypes")
    public static class RenderFactory implements IRenderFactory {
        @Override
        public EntityRenderer<? super FakeHerobrineMageEntity> createRenderFor(EntityRendererManager manager) {
            return new FakeHerobrineMageEntityRender(manager);
        }
    }
}