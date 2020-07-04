package com.herobrine.mod.client.renders;

import com.herobrine.mod.HerobrineMod;
import com.herobrine.mod.client.models.AbstractHerobrineEntityModel;
import com.herobrine.mod.entities.AbstractHerobrineEntity;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.layers.LayerHeldItem;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.jetbrains.annotations.NotNull;

@SideOnly(Side.CLIENT)
public class AbstractHerobrineEntityRenderer extends RenderLiving<AbstractHerobrineEntity> {
    public AbstractHerobrineEntityRenderer(RenderManager manager) {
        super(manager, new AbstractHerobrineEntityModel(0.0f, false), 0.5F);
        this.addLayer(new LayerHeldItem(this));
    }
    @Override
    protected ResourceLocation getEntityTexture(@NotNull AbstractHerobrineEntity entity) {
        return HerobrineMod.location("textures/entity/herobrine.png");
    }
    @Override
    public void transformHeldFull3DItemLayer()
    {
        GlStateManager.translate(0.0F, 0.1875F, 0.0F);
    }
}