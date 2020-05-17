package com.herobrine.mod.client.renders;

import com.herobrine.mod.HerobrineMod;
import com.herobrine.mod.client.models.InfectedPigEntityModel;
import com.herobrine.mod.entities.InfectedPigEntity;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.jetbrains.annotations.NotNull;

@SideOnly(Side.CLIENT)
public class InfectedPigEntityRenderer extends RenderLiving<InfectedPigEntity> {
    public static ResourceLocation TEXTURES = HerobrineMod.location("textures/entity/infected_pig.png");

    public InfectedPigEntityRenderer(RenderManager manager) {
        super(manager, new InfectedPigEntityModel(), 0.7F);
    }

    @Override
    protected ResourceLocation getEntityTexture(@NotNull InfectedPigEntity entity) {
        return TEXTURES;
    }
}
