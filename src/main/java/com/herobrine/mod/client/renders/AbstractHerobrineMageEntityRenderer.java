package com.herobrine.mod.client.renders;

import com.herobrine.mod.HerobrineMod;
import com.herobrine.mod.client.models.AbstractHerobrineMageEntityModel;
import com.herobrine.mod.entities.AbstractHerobrineEntity;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.jetbrains.annotations.NotNull;

@SideOnly(Side.CLIENT)
public class AbstractHerobrineMageEntityRenderer extends RenderLiving<AbstractHerobrineEntity> {
    public AbstractHerobrineMageEntityRenderer(RenderManager manager) {
        super(manager, new AbstractHerobrineMageEntityModel(), 0.5F);
    }
    @Override
    protected ResourceLocation getEntityTexture(@NotNull AbstractHerobrineEntity entity) {
        return HerobrineMod.location("textures/entity/herobrine.png");
    }
}
