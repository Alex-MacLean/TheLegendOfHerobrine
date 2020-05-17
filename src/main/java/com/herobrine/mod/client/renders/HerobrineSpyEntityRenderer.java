package com.herobrine.mod.client.renders;

import com.herobrine.mod.HerobrineMod;
import com.herobrine.mod.client.models.HerobrineEntityModel;
import com.herobrine.mod.entities.HerobrineSpyEntity;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.jetbrains.annotations.NotNull;

@SideOnly(Side.CLIENT)
public class HerobrineSpyEntityRenderer extends RenderLiving<HerobrineSpyEntity> {
    public static ResourceLocation TEXTURES = HerobrineMod.location("textures/entity/herobrine.png");

    public HerobrineSpyEntityRenderer(RenderManager manager) {
        super(manager, new HerobrineEntityModel(0.0f, false), 0.5F);
    }

    @Override
    protected ResourceLocation getEntityTexture(@NotNull HerobrineSpyEntity entity) {
        return TEXTURES;
    }
}