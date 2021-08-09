package com.herobrine.mod.client.renders;

import com.herobrine.mod.HerobrineMod;
import com.herobrine.mod.client.models.InfectedLlamaEntityModel;
import com.herobrine.mod.client.renders.layers.InfectedLlamaEyesLayer;
import com.herobrine.mod.entities.InfectedLlamaEntity;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
public class InfectedLlamaEntityRender extends MobRenderer<InfectedLlamaEntity, InfectedLlamaEntityModel<InfectedLlamaEntity>> {
    private static final ResourceLocation[] TEXTURES = new ResourceLocation[]{new ResourceLocation(HerobrineMod.MODID + ":textures/entity/infected_llama/creamy.png"), new ResourceLocation(HerobrineMod.MODID + ":textures/entity/infected_llama/white.png"), new ResourceLocation(HerobrineMod.MODID + ":textures/entity/infected_llama/brown.png"), new ResourceLocation(HerobrineMod.MODID + ":textures/entity/infected_llama/gray.png")};

    public InfectedLlamaEntityRender(EntityRendererManager renderManagerIn) {
        super(renderManagerIn, new InfectedLlamaEntityModel<>(0.0F), 0.7F);
        this.addLayer(new InfectedLlamaEyesLayer(this));
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull InfectedLlamaEntity entity) {
        return TEXTURES[entity.getVariant()];
    }

    @SuppressWarnings("rawtypes")
    public static class RenderFactory implements IRenderFactory {
        @Override
        public EntityRenderer<? super InfectedLlamaEntity> createRenderFor(EntityRendererManager manager) {
            return new InfectedLlamaEntityRender(manager);
        }
    }
}