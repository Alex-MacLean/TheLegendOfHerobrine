package com.herobrine.mod.client.renders;

import com.google.common.collect.Maps;
import com.herobrine.mod.HerobrineMod;
import com.herobrine.mod.client.renders.layers.InfectedMooshroomMushroomLayer;
import com.herobrine.mod.entities.InfectedMooshroomEntity;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.model.CowModel;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Util;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

@OnlyIn(Dist.CLIENT)
public class InfectedMooshroomEntityRender extends MobRenderer<InfectedMooshroomEntity, CowModel<InfectedMooshroomEntity>> {
    private static final Map<InfectedMooshroomEntity.Type, ResourceLocation> field_217774_a = Util.make(Maps.newHashMap(), (p_217773_0_) -> {
        p_217773_0_.put(InfectedMooshroomEntity.Type.BROWN, HerobrineMod.location("textures/entity/infected_brown_mooshroom.png"));
        p_217773_0_.put(InfectedMooshroomEntity.Type.RED, HerobrineMod.location("textures/entity/infected_red_mooshroom.png"));
    });

    public InfectedMooshroomEntityRender(EntityRendererManager renderManagerIn) {
        super(renderManagerIn, new CowModel<>(), 0.7F);
        this.addLayer(new InfectedMooshroomMushroomLayer<>(this));
    }

    @NotNull
    public ResourceLocation getEntityTexture(@NotNull InfectedMooshroomEntity entity) {
        return field_217774_a.get(entity.getMooshroomType());
    }

    @SuppressWarnings("rawtypes")
    public static class RenderFactory implements IRenderFactory {
        @Override
        public EntityRenderer<? super InfectedMooshroomEntity> createRenderFor(EntityRendererManager manager) {
            return new InfectedMooshroomEntityRender(manager);
        }
    }
}