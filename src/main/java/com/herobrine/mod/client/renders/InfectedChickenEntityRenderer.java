package com.herobrine.mod.client.renders;

import com.herobrine.mod.HerobrineMod;
import com.herobrine.mod.client.models.InfectedChickenEntityModel;
import com.herobrine.mod.entities.InfectedChickenEntity;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.jetbrains.annotations.NotNull;

@SideOnly(Side.CLIENT)
public class InfectedChickenEntityRenderer extends RenderLiving<InfectedChickenEntity> {
    public static ResourceLocation TEXTURES = HerobrineMod.location("textures/entity/infected_chicken.png");

    public InfectedChickenEntityRenderer(RenderManager manager) {
        super(manager, new InfectedChickenEntityModel(), 0.3F);
    }

    @Override
    protected float handleRotationFloat(@NotNull InfectedChickenEntity livingBase, float partialTicks)
    {
        float f = livingBase.oFlap + (livingBase.wingRotation - livingBase.oFlap) * partialTicks;
        float f1 = livingBase.oFlapSpeed + (livingBase.destPos - livingBase.oFlapSpeed) * partialTicks;
        return (MathHelper.sin(f) + 1.0F) * f1;
    }

    @Override
    protected ResourceLocation getEntityTexture(@NotNull InfectedChickenEntity entity) {
        return TEXTURES;
    }
}