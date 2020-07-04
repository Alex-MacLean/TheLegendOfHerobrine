package com.herobrine.mod.client.renders;

import com.herobrine.mod.HerobrineMod;
import com.herobrine.mod.client.models.InfectedBatEntityModel;
import com.herobrine.mod.entities.InfectedBatEntity;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.jetbrains.annotations.NotNull;

@SideOnly(Side.CLIENT)
public class InfectedBatEntityRenderer extends RenderLiving<InfectedBatEntity> {
    public static ResourceLocation TEXTURES = HerobrineMod.location("textures/entity/infected_bat.png");

    public InfectedBatEntityRenderer(RenderManager manager) {
        super(manager, new InfectedBatEntityModel(), 0.25F);
    }

    @Override
    protected void preRenderCallback(@NotNull InfectedBatEntity entitylivingbaseIn, float partialTickTime) {
        GlStateManager.scale(0.35F, 0.35F, 0.35F);
    }

    @Override
    protected void applyRotations(@NotNull InfectedBatEntity entityLiving, float ageInTicks, float rotationYaw, float partialTicks) {
        if (entityLiving.getIsBatHanging()) {
            GlStateManager.translate(0.0F, -0.1F, 0.0F);
        }
        else {
            GlStateManager.translate(0.0F, MathHelper.cos(ageInTicks * 0.3F) * 0.1F, 0.0F);
        }

        super.applyRotations(entityLiving, ageInTicks, rotationYaw, partialTicks);
    }

    @Override
    protected ResourceLocation getEntityTexture(@NotNull InfectedBatEntity entity) {
        return TEXTURES;
    }
}
