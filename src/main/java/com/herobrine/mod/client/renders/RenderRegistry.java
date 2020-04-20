package com.herobrine.mod.client.renders;

import com.herobrine.mod.entities.*;
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
        RenderingRegistry.registerEntityRenderingHandler(HerobrineWarriorEntity.class, new HerobrineWarriorEntityRender.RenderFactory());
        RenderingRegistry.registerEntityRenderingHandler(HolyWaterEntity.class, (IRenderFactory<Entity>) manager -> new SpriteRenderer(manager, Minecraft.getInstance().getItemRenderer()));
        RenderingRegistry.registerEntityRenderingHandler(UnholyWaterEntity.class, (IRenderFactory<Entity>) manager -> new SpriteRenderer(manager, Minecraft.getInstance().getItemRenderer()));
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