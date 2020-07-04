package com.herobrine.mod.client.renders;

import com.herobrine.mod.client.models.AbstractSurvivorEntityModel;
import com.herobrine.mod.entities.AbstractSurvivorEntity;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.layers.LayerArrow;
import net.minecraft.client.renderer.entity.layers.LayerBipedArmor;
import net.minecraft.client.renderer.entity.layers.LayerElytra;
import net.minecraft.client.renderer.entity.layers.LayerHeldItem;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.jetbrains.annotations.NotNull;

@SideOnly(Side.CLIENT)
public class SteveSurvivorEntityRenderer extends RenderLiving<AbstractSurvivorEntity> {
    public SteveSurvivorEntityRenderer(RenderManager manager) {
        super(manager, new AbstractSurvivorEntityModel(0.0f, false), 0.5F);
        this.addLayer(new LayerBipedArmor(this));
        this.addLayer(new LayerHeldItem(this));
        this.addLayer(new LayerArrow(this));
        this.addLayer(new LayerElytra(this));
    }
    @Override
    protected ResourceLocation getEntityTexture(@NotNull AbstractSurvivorEntity entity) {
        return new ResourceLocation("textures/entity/steve.png");
    }
    @Override
    public void transformHeldFull3DItemLayer()
    {
        GlStateManager.translate(0.0F, 0.1875F, 0.0F);
    }
}
