package com.herobrine.mod.client.renders;

import com.herobrine.mod.HerobrineMod;
import com.herobrine.mod.client.models.InfectedDonkeyEntityModel;
import com.herobrine.mod.entities.InfectedDonkeyEntity;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.jetbrains.annotations.NotNull;

@SideOnly(Side.CLIENT)
public class InfectedDonkeyEntityRenderer extends RenderLiving<InfectedDonkeyEntity> {
    public static ResourceLocation TEXTURES = HerobrineMod.location("textures/entity/infected_donkey.png");

    public InfectedDonkeyEntityRenderer(RenderManager manager) {
        super(manager, new InfectedDonkeyEntityModel(), 0.75F);
    }

    @Override
    protected void preRenderCallback(@NotNull InfectedDonkeyEntity entitylivingbaseIn, float partialTickTime)
    {
        GlStateManager.scale(0.87F, 0.87F, 0.87F);
        super.preRenderCallback(entitylivingbaseIn, partialTickTime);
    }

    @Override
    protected ResourceLocation getEntityTexture(@NotNull InfectedDonkeyEntity entity) {
        return TEXTURES;
    }
}
