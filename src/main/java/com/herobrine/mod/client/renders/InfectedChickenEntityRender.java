package com.herobrine.mod.client.renders;

import com.herobrine.mod.HerobrineMod;
import com.herobrine.mod.client.models.InfectedChickenEntityModel;
import com.herobrine.mod.entities.InfectedChickenEntity;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
public class InfectedChickenEntityRender extends MobRenderer<InfectedChickenEntity, InfectedChickenEntityModel> {
    public InfectedChickenEntityRender(EntityRendererManager manager) {
        super(manager, new InfectedChickenEntityModel(), 0.3F);
    }

    @NotNull
    @Override
    public ResourceLocation getEntityTexture(@NotNull InfectedChickenEntity entity) {
        return HerobrineMod.location("textures/entity/infected_chicken.png");
    }

    @Override
    protected float handleRotationFloat(@NotNull InfectedChickenEntity livingBase, float partialTicks) {
        float f = MathHelper.lerp(partialTicks, livingBase.oFlap, livingBase.wingRotation);
        float f1 = MathHelper.lerp(partialTicks, livingBase.oFlapSpeed, livingBase.destPos);
        return (MathHelper.sin(f) + 1.0F) * f1;
    }

    public static class RenderFactory implements IRenderFactory {
        @Override
        public EntityRenderer<? super InfectedChickenEntity> createRenderFor(EntityRendererManager manager) {
            return new InfectedChickenEntityRender(manager);
        }
    }
}