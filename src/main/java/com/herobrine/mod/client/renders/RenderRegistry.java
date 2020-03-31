package com.herobrine.mod.client.renders;

import com.herobrine.mod.util.entities.EntityRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.SpriteRenderer;
import net.minecraft.entity.Entity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.client.registry.RenderingRegistry;

@OnlyIn(Dist.CLIENT)
public class RenderRegistry {
    public static void registerEntityRenders() {
        RenderingRegistry.registerEntityRenderingHandler(EntityRegistry.HEROBRINE_ENTITY, new HerobrineEntityRender.RenderFactory());
        RenderingRegistry.registerEntityRenderingHandler(EntityRegistry.HOLY_WATER_ENTITY, (IRenderFactory<Entity>) manager -> new SpriteRenderer(manager, Minecraft.getInstance().getItemRenderer()));
        RenderingRegistry.registerEntityRenderingHandler(EntityRegistry.UNHOLY_WATER_ENTITY, (IRenderFactory<Entity>) manager -> new SpriteRenderer(manager, Minecraft.getInstance().getItemRenderer()));
    }
}