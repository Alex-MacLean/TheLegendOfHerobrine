package com.herobrine.mod.client.renders;

import com.google.common.collect.Maps;
import com.herobrine.mod.client.models.InfectedHorseEntityModel;
import com.herobrine.mod.client.renders.layers.InfectedHorseEyesLayer;
import com.herobrine.mod.entities.InfectedHorseEntity;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.entity.passive.horse.CoatColors;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Util;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

@OnlyIn(Dist.CLIENT)
public class InfectedHorseEntityRender extends MobRenderer<InfectedHorseEntity, InfectedHorseEntityModel> {

    @SuppressWarnings("rawtypes") //Suppresses raw usage of parameterized class map
    private static final Map field_239383_a_ = Util.make(Maps.newEnumMap(CoatColors.class), (p_239384_0_) -> {
        p_239384_0_.put(CoatColors.WHITE, new ResourceLocation("herobrine:textures/entity/infected_horse/horse_white.png"));
        p_239384_0_.put(CoatColors.CREAMY, new ResourceLocation("herobrine:textures/entity/infected_horse/horse_creamy.png"));
        p_239384_0_.put(CoatColors.CHESTNUT, new ResourceLocation("herobrine:textures/entity/infected_horse/horse_chestnut.png"));
        p_239384_0_.put(CoatColors.BROWN, new ResourceLocation("herobrine:textures/entity/infected_horse/horse_brown.png"));
        p_239384_0_.put(CoatColors.BLACK, new ResourceLocation("herobrine:textures/entity/infected_horse/horse_black.png"));
        p_239384_0_.put(CoatColors.GRAY, new ResourceLocation("herobrine:textures/entity/infected_horse/horse_gray.png"));
        p_239384_0_.put(CoatColors.DARKBROWN, new ResourceLocation("herobrine:textures/entity/infected_horse/horse_darkbrown.png"));
    });

    public InfectedHorseEntityRender(EntityRendererManager manager) {
        super(manager, new InfectedHorseEntityModel(0.0F), 0.75F);
        this.addLayer(new InfectedHorseEyesLayer(this));
    }

    @Override
    protected void scale(@NotNull InfectedHorseEntity entitylivingbaseIn, @NotNull MatrixStack matrixStackIn, float partialTickTime) {
        matrixStackIn.scale(1.1F, 1.1F, 1.1F);
        super.scale(entitylivingbaseIn, matrixStackIn, partialTickTime);
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull InfectedHorseEntity entity) {
        return (ResourceLocation) field_239383_a_.get(entity.func_234239_eK_());
    }

    @SuppressWarnings("rawtypes")
    public static class RenderFactory implements IRenderFactory {
        @Override
        public EntityRenderer<? super InfectedHorseEntity> createRenderFor(EntityRendererManager manager) {
            return new InfectedHorseEntityRender(manager);
        }
    }
}
