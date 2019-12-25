package com.herobrine.mod.client.renders;

import com.herobrine.mod.entities.EntityRegistry;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.client.registry.RenderingRegistry;

@OnlyIn(Dist.CLIENT)
public class RenderRegistry {
    public static void registerEntityRenders() {
        RenderingRegistry.registerEntityRenderingHandler(EntityRegistry.HEROBRINE_ENTITY, new HerobrineEntityRender.RenderFactory());
    }
}