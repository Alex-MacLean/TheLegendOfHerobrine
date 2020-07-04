package com.herobrine.mod.client.renders;

import com.herobrine.mod.HerobrineMod;
import com.herobrine.mod.client.models.InfectedWolfEntityModel;
import com.herobrine.mod.entities.InfectedWolfEntity;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.jetbrains.annotations.NotNull;

@SideOnly(Side.CLIENT)
public class InfectedWolfEntityRenderer extends RenderLiving<InfectedWolfEntity> {
    public static ResourceLocation TEXTURES = HerobrineMod.location("textures/entity/infected_wolf.png");

    public InfectedWolfEntityRenderer(RenderManager manager) {
        super(manager, new InfectedWolfEntityModel(), 0.5F);
    }

    @Override
    protected float handleRotationFloat(@NotNull InfectedWolfEntity livingBase, float partialTicks) {
        return livingBase.getTailRotation();
    }

    @Override
    protected ResourceLocation getEntityTexture(@NotNull InfectedWolfEntity entity) {
        return TEXTURES;
    }
}
