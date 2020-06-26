package com.herobrine.mod.client.renders;

import com.herobrine.mod.entities.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.SpriteRenderer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.client.registry.RenderingRegistry;

@OnlyIn(Dist.CLIENT)
public class RenderRegistry {
    @SuppressWarnings({"unchecked", "rawtypes"})
    public static void registerEntityRenders() {
        RenderingRegistry.registerEntityRenderingHandler(HerobrineWarriorEntity.class, new AbstractHerobrineEntityRender.RenderFactory());
        RenderingRegistry.registerEntityRenderingHandler(HolyWaterEntity.class, renderManager -> new SpriteRenderer(renderManager, Minecraft.getInstance().getItemRenderer()));
        RenderingRegistry.registerEntityRenderingHandler(UnholyWaterEntity.class, renderManager -> new SpriteRenderer(renderManager, Minecraft.getInstance().getItemRenderer()));
        RenderingRegistry.registerEntityRenderingHandler(InfectedPigEntity.class, new InfectedPigEntityRender.RenderFactory());
        RenderingRegistry.registerEntityRenderingHandler(InfectedChickenEntity.class, new InfectedChickenEntityRender.RenderFactory());
        RenderingRegistry.registerEntityRenderingHandler(InfectedSheepEntity.class, new InfectedSheepEntityRender.RenderFactory());
        RenderingRegistry.registerEntityRenderingHandler(InfectedCowEntity.class, new InfectedCowEntityRender.RenderFactory());
        RenderingRegistry.registerEntityRenderingHandler(InfectedMooshroomEntity.class, new InfectedMooshroomEntityRender.RenderFactory());
        RenderingRegistry.registerEntityRenderingHandler(InfectedVillagerEntity.class, new InfectedVillagerEntityRenderer.RenderFactory());
        RenderingRegistry.registerEntityRenderingHandler(HerobrineSpyEntity.class, new AbstractHerobrineEntityRender.RenderFactory());
        RenderingRegistry.registerEntityRenderingHandler(HerobrineBuilderEntity.class, new AbstractHerobrineEntityRender.RenderFactory());
        RenderingRegistry.registerEntityRenderingHandler(HerobrineMageEntity.class, new AbstractHerobrineMageEntityRender.RenderFactory());
        RenderingRegistry.registerEntityRenderingHandler(FakeHerobrineMageEntity.class, new AbstractHerobrineEntityRender.RenderFactory());
        RenderingRegistry.registerEntityRenderingHandler(SteveSurvivorEntity.class, new SteveSurvivorEntityRender.RenderFactory());
        RenderingRegistry.registerEntityRenderingHandler(AlexSurvivorEntity.class, new AlexSurvivorEntityRender.RenderFactory());
        RenderingRegistry.registerEntityRenderingHandler(InfectedWolfEntity.class, new InfectedWolfEntityRender.RenderFactory());
        RenderingRegistry.registerEntityRenderingHandler(InfectedLlamaEntity.class, new InfectedLlamaEntityRender.RenderFactory());
        RenderingRegistry.registerEntityRenderingHandler(InfectedHorseEntity.class, new InfectedHorseEntityRender.RenderFactory());
        RenderingRegistry.registerEntityRenderingHandler(InfectedDonkeyEntity.class, new InfectedDonkeyEntityRender.RenderFactory());
        RenderingRegistry.registerEntityRenderingHandler(InfectedRabbitEntity.class, new InfectedRabbitEntityRender.RenderFactory());
        RenderingRegistry.registerEntityRenderingHandler(InfectedBatEntity.class, new InfectedBatEntityRender.RenderFactory());
    }
}