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
        RenderingRegistry.registerEntityRenderingHandler(HerobrineWarriorEntity.class, new HerobrineWarriorEntityRender.RenderFactory());
        RenderingRegistry.registerEntityRenderingHandler(HolyWaterEntity.class, renderManager -> new SpriteRenderer(renderManager, Minecraft.getInstance().getItemRenderer()));
        RenderingRegistry.registerEntityRenderingHandler(UnholyWaterEntity.class, renderManager -> new SpriteRenderer(renderManager, Minecraft.getInstance().getItemRenderer()));
        RenderingRegistry.registerEntityRenderingHandler(InfectedPigEntity.class, new InfectedPigEntityRender.RenderFactory());
        RenderingRegistry.registerEntityRenderingHandler(InfectedChickenEntity.class, new InfectedChickenEntityRender.RenderFactory());
        RenderingRegistry.registerEntityRenderingHandler(InfectedSheepEntity.class, new InfectedSheepEntityRender.RenderFactory());
        RenderingRegistry.registerEntityRenderingHandler(InfectedCowEntity.class, new InfectedCowEntityRender.RenderFactory());
        RenderingRegistry.registerEntityRenderingHandler(InfectedMooshroomEntity.class, new InfectedMooshroomEntityRender.RenderFactory());
        RenderingRegistry.registerEntityRenderingHandler(InfectedVillagerEntity.class, new InfectedVillagerEntityRenderer.RenderFactory());
        RenderingRegistry.registerEntityRenderingHandler(HerobrineSpyEntity.class, new HerobrineSpyEntityRender.RenderFactory());
        RenderingRegistry.registerEntityRenderingHandler(HerobrineBuilderEntity.class, new HerobrineBuilderEntityRender.RenderFactory());
        RenderingRegistry.registerEntityRenderingHandler(HerobrineMageEntity.class, new HerobrineMageEntityRender.RenderFactory());
        RenderingRegistry.registerEntityRenderingHandler(FakeHerobrineMageEntity.class, new FakeHerobrineMageEntityRender.RenderFactory());
    }
}