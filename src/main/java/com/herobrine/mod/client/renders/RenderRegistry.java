package com.herobrine.mod.client.renders;

import com.herobrine.mod.util.entities.EntityRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.SpriteRenderer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.client.registry.RenderingRegistry;

@OnlyIn(Dist.CLIENT)
public class RenderRegistry {
    @SuppressWarnings({"unchecked", "rawtypes"})
    public static void registerEntityRenders() {
        RenderingRegistry.registerEntityRenderingHandler(EntityRegistry.HEROBRINE_WARRIOR_ENTITY, new AbstractHerobrineEntityRender.RenderFactory());
        RenderingRegistry.registerEntityRenderingHandler(EntityRegistry.HOLY_WATER_ENTITY, renderManager -> new SpriteRenderer(renderManager, Minecraft.getInstance().getItemRenderer()));
        RenderingRegistry.registerEntityRenderingHandler(EntityRegistry.UNHOLY_WATER_ENTITY, renderManager -> new SpriteRenderer(renderManager, Minecraft.getInstance().getItemRenderer()));
        RenderingRegistry.registerEntityRenderingHandler(EntityRegistry.INFECTED_PIG_ENTITY, new InfectedPigEntityRender.RenderFactory());
        RenderingRegistry.registerEntityRenderingHandler(EntityRegistry.INFECTED_CHICKEN_ENTITY, new InfectedChickenEntityRender.RenderFactory());
        RenderingRegistry.registerEntityRenderingHandler(EntityRegistry.INFECTED_SHEEP_ENTITY, new InfectedSheepEntityRender.RenderFactory());
        RenderingRegistry.registerEntityRenderingHandler(EntityRegistry.INFECTED_COW_ENTITY, new InfectedCowEntityRender.RenderFactory());
        RenderingRegistry.registerEntityRenderingHandler(EntityRegistry.INFECTED_MOOSHROOM_ENTITY, new InfectedMooshroomEntityRender.RenderFactory());
        RenderingRegistry.registerEntityRenderingHandler(EntityRegistry.INFECTED_VILLAGER_ENTITY, new InfectedVillagerEntityRenderer.RenderFactory());
        RenderingRegistry.registerEntityRenderingHandler(EntityRegistry.HEROBRINE_SPY_ENTITY, new AbstractHerobrineEntityRender.RenderFactory());
        RenderingRegistry.registerEntityRenderingHandler(EntityRegistry.HEROBRINE_BUILDER_ENTITY, new AbstractHerobrineEntityRender.RenderFactory());
        RenderingRegistry.registerEntityRenderingHandler(EntityRegistry.HEROBRINE_MAGE_ENTITY, new AbstractHerobrineMageEntityRender.RenderFactory());
        RenderingRegistry.registerEntityRenderingHandler(EntityRegistry.FAKE_HEROBRINE_MAGE_ENTITY, new AbstractHerobrineMageEntityRender.RenderFactory());
        RenderingRegistry.registerEntityRenderingHandler(EntityRegistry.STEVE_SURVIVOR_ENTITY, new SteveSurvivorEntityRender.RenderFactory());
        RenderingRegistry.registerEntityRenderingHandler(EntityRegistry.ALEX_SURVIVOR_ENTITY, new AlexSurvivorEntityRender.RenderFactory());
        RenderingRegistry.registerEntityRenderingHandler(EntityRegistry.INFECTED_WOLF_ENTITY, new InfectedWolfEntityRender.RenderFactory());
        RenderingRegistry.registerEntityRenderingHandler(EntityRegistry.INFECTED_LLAMA_ENTITY, new InfectedLlamaEntityRender.RenderFactory());
        RenderingRegistry.registerEntityRenderingHandler(EntityRegistry.INFECTED_HORSE_ENTITY, new InfectedHorseEntityRender.RenderFactory());
        RenderingRegistry.registerEntityRenderingHandler(EntityRegistry.INFECTED_DONKEY_ENTITY, new InfectedDonkeyEntityRender.RenderFactory());
        RenderingRegistry.registerEntityRenderingHandler(EntityRegistry.INFECTED_RABBIT_ENTITY, new InfectedRabbitEntityRender.RenderFactory());
        RenderingRegistry.registerEntityRenderingHandler(EntityRegistry.INFECTED_BAT_ENTITY, new InfectedBatEntityRender.RenderFactory());
    }
}