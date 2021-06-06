package com.herobrine.mod.client.renders;

import com.herobrine.mod.HerobrineMod;
import com.herobrine.mod.client.models.InfectedRabbitEntityModel;
import com.herobrine.mod.client.renders.layers.InfectedRabbitEyesLayer;
import com.herobrine.mod.entities.InfectedRabbitEntity;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
public class InfectedRabbitEntityRender extends MobRenderer<InfectedRabbitEntity, InfectedRabbitEntityModel> {
    private static final ResourceLocation BROWN = new ResourceLocation(HerobrineMod.MODID + ":textures/entity/infected_rabbit/brown.png");
    private static final ResourceLocation WHITE = new ResourceLocation(HerobrineMod.MODID + ":textures/entity/infected_rabbit/white.png");
    private static final ResourceLocation BLACK = new ResourceLocation(HerobrineMod.MODID + ":textures/entity/infected_rabbit/black.png");
    private static final ResourceLocation GOLD = new ResourceLocation(HerobrineMod.MODID + ":textures/entity/infected_rabbit/gold.png");
    private static final ResourceLocation SALT = new ResourceLocation(HerobrineMod.MODID + ":textures/entity/infected_rabbit/salt.png");
    private static final ResourceLocation WHITE_SPLOTCHED = new ResourceLocation(HerobrineMod.MODID + ":textures/entity/infected_rabbit/white_splotched.png");
    private static final ResourceLocation TOAST = new ResourceLocation(HerobrineMod.MODID + ":textures/entity/infected_rabbit/toast.png");
    private static final ResourceLocation CAERBANNOG = new ResourceLocation(HerobrineMod.MODID + ":textures/entity/infected_rabbit/caerbannog.png");

    public InfectedRabbitEntityRender(EntityRendererManager manager) {
        super(manager, new InfectedRabbitEntityModel(), 0.3F);
        this.addLayer(new InfectedRabbitEyesLayer(this));
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull InfectedRabbitEntity entity) {
        String s = TextFormatting.stripFormatting(entity.getName().getString());
        if ("Toast".equals(s)) {
            return TOAST;
        } else {
            switch (entity.getRabbitType()) {
                case 0:
                default:
                    return BROWN;
                case 1:
                    return WHITE;
                case 2:
                    return BLACK;
                case 3:
                    return WHITE_SPLOTCHED;
                case 4:
                    return GOLD;
                case 5:
                    return SALT;
                case 99:
                    InfectedRabbitEyesLayer.RENDER_TYPE = RenderType.eyes(HerobrineMod.location("textures/entity/eyes/infected_rabbit/caerbannog.png"));
                    return CAERBANNOG;
            }
        }
    }

    @SuppressWarnings("rawtypes")
    public static class RenderFactory implements IRenderFactory {
        @Override
        public EntityRenderer<? super InfectedRabbitEntity> createRenderFor(EntityRendererManager manager) {
            return new InfectedRabbitEntityRender(manager);
        }
    }
}
