package com.herobrine.mod.client.renders;

import com.herobrine.mod.entities.*;
import com.herobrine.mod.util.items.ItemList;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderSnowball;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderRegistry {
    public static void registerEntityRenders() {
        RenderingRegistry.registerEntityRenderingHandler(InfectedPigEntity.class, InfectedPigEntityRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(InfectedSheepEntity.class, InfectedSheepEntityRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(InfectedVillagerEntity.class, InfectedVillagerEntityRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(InfectedMooshroomEntity.class, InfectedMooshroomEntityRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(InfectedCowEntity.class, InfectedCowEntityRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(InfectedChickenEntity.class, InfectedChickenEntityRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(HerobrineMageEntity.class, AbstractHerobrineMageEntityRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(FakeHerobrineMageEntity.class, AbstractHerobrineMageEntityRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(HerobrineSpyEntity.class, AbstractHerobrineEntityRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(HerobrineBuilderEntity.class, AbstractHerobrineEntityRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(HerobrineWarriorEntity.class, AbstractHerobrineEntityRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(SteveSurvivorEntity.class, SteveSurvivorEntityRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(AlexSurvivorEntity.class, AlexSurvivorEntityRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(InfectedBatEntity.class, InfectedBatEntityRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(InfectedDonkeyEntity.class, InfectedDonkeyEntityRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(InfectedHorseEntity.class, InfectedHorseEntityRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(InfectedLlamaEntity.class, InfectedLlamaEntityRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(InfectedRabbitEntity.class, InfectedRabbitEntityRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(InfectedWolfEntity.class, InfectedWolfEntityRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(HolyWaterEntity.class, renderManager -> new RenderSnowball<>(renderManager, new ItemStack(ItemList.holy_water, 1).getItem(), Minecraft.getMinecraft().getRenderItem()));
        RenderingRegistry.registerEntityRenderingHandler(UnholyWaterEntity.class, renderManager -> new RenderSnowball<>(renderManager, new ItemStack(ItemList.unholy_water, 1).getItem(), Minecraft.getMinecraft().getRenderItem()));
    }
}