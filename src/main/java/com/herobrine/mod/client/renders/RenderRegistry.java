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
        RenderingRegistry.registerEntityRenderingHandler(EntityRegistry.HEROBRINE_WARRIOR_ENTITY, new HerobrineWarriorEntityRender.RenderFactory());
        RenderingRegistry.registerEntityRenderingHandler(EntityRegistry.HOLY_WATER_ENTITY, (IRenderFactory<Entity>) manager -> new SpriteRenderer(manager, Minecraft.getInstance().getItemRenderer()));
        RenderingRegistry.registerEntityRenderingHandler(EntityRegistry.UNHOLY_WATER_ENTITY, (IRenderFactory<Entity>) manager -> new SpriteRenderer(manager, Minecraft.getInstance().getItemRenderer()));
        RenderingRegistry.registerEntityRenderingHandler(EntityRegistry.INFECTED_PIG_ENTITY, new InfectedPigEntityRender.RenderFactory());
        RenderingRegistry.registerEntityRenderingHandler(EntityRegistry.INFECTED_CHICKEN_ENTITY, new InfectedChickenEntityRender.RenderFactory());
        RenderingRegistry.registerEntityRenderingHandler(EntityRegistry.INFECTED_SHEEP_ENTITY, new InfectedSheepEntityRender.RenderFactory());
        RenderingRegistry.registerEntityRenderingHandler(EntityRegistry.INFECTED_COW_ENTITY, new InfectedCowEntityRender.RenderFactory());
        RenderingRegistry.registerEntityRenderingHandler(EntityRegistry.INFECTED_MOOSHROOM_ENTITY, new InfectedMooshroomEntityRender.RenderFactory());
        RenderingRegistry.registerEntityRenderingHandler(EntityRegistry.INFECTED_VILLAGER_ENTITY, new InfectedVillagerEntityRenderer.RenderFactory());
        RenderingRegistry.registerEntityRenderingHandler(EntityRegistry.HEROBRINE_SPY_ENTITY, new HerobrineSpyEntityRender.RenderFactory());
        RenderingRegistry.registerEntityRenderingHandler(EntityRegistry.HEROBRINE_BUILDER_ENTITY, new HerobrineBuilderEntityRender.RenderFactory());
    }
}