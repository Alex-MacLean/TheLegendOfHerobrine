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
        RenderingRegistry.registerEntityRenderingHandler(HerobrineMageEntity.class, HerobrineMageEntityRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(FakeHerobrineMageEntity.class, FakeHerobrineMageEntityRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(HerobrineSpyEntity.class, HerobrineSpyEntityRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(HerobrineBuilderEntity.class, HerobrineBuilderEntityRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(HerobrineWarriorEntity.class, HerobrineWarriorEntityRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(HolyWaterEntity.class, renderManager -> new RenderSnowball<>(renderManager, new ItemStack(ItemList.holy_water, 1).getItem(), Minecraft.getMinecraft().getRenderItem()));
        RenderingRegistry.registerEntityRenderingHandler(UnholyWaterEntity.class, renderManager -> new RenderSnowball<>(renderManager, new ItemStack(ItemList.unholy_water, 1).getItem(), Minecraft.getMinecraft().getRenderItem()));
    }
}