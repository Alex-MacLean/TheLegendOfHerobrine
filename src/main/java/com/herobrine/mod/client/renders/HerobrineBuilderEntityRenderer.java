package com.herobrine.mod.client.renders;

import com.herobrine.mod.HerobrineMod;
import com.herobrine.mod.client.models.HerobrineEntityModel;
import com.herobrine.mod.entities.HerobrineBuilderEntity;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.layers.LayerHeldItem;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.jetbrains.annotations.NotNull;

@SideOnly(Side.CLIENT)
public class HerobrineBuilderEntityRenderer extends RenderLiving<HerobrineBuilderEntity> {
    public static ResourceLocation TEXTURES = HerobrineMod.location("textures/entity/herobrine.png");

    public HerobrineBuilderEntityRenderer(RenderManager manager) {
        super(manager, new HerobrineEntityModel(0.0f, false), 0.5F);
        LayerHeldItem layerhelditem = new LayerHeldItem(this);
        this.addLayer(layerhelditem);
    }

    @Override
    protected ResourceLocation getEntityTexture(@NotNull HerobrineBuilderEntity entity) {
        return TEXTURES;
    }

    @Override
    public void transformHeldFull3DItemLayer()
    {
        GlStateManager.translate(0.0F, 0.1875F, 0.0F);
    }
}
