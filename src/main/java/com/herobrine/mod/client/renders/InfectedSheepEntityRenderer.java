package com.herobrine.mod.client.renders;

import com.herobrine.mod.HerobrineMod;
import com.herobrine.mod.client.models.ShearedInfectedSheepEntityModel;
import com.herobrine.mod.client.renders.layers.InfectedSheepWoolLayer;
import com.herobrine.mod.entities.InfectedSheepEntity;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.jetbrains.annotations.NotNull;

@SideOnly(Side.CLIENT)
public class InfectedSheepEntityRenderer extends RenderLiving<InfectedSheepEntity> {
    public static ResourceLocation TEXTURES = HerobrineMod.location("textures/entity/infected_sheep/infected_sheep.png");

    public InfectedSheepEntityRenderer(RenderManager manager) {
        super(manager, new ShearedInfectedSheepEntityModel(), 0.7F);
        this.addLayer(new InfectedSheepWoolLayer(this));
    }

    @Override
    protected ResourceLocation getEntityTexture(@NotNull InfectedSheepEntity entity) {
        return TEXTURES;
    }
}
