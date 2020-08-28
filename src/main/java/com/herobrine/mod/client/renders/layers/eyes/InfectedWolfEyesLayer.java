package com.herobrine.mod.client.renders.layers.eyes;

import com.herobrine.mod.HerobrineMod;
import com.herobrine.mod.client.models.InfectedWolfEntityModel;
import com.herobrine.mod.entities.InfectedWolfEntity;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.layers.AbstractEyesLayer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
public class InfectedWolfEyesLayer extends AbstractEyesLayer<InfectedWolfEntity, InfectedWolfEntityModel> {
    private static final RenderType RENDER_TYPE = RenderType.getEyes(HerobrineMod.location("textures/entity/eyes/infected_wolf.png"));

    public InfectedWolfEyesLayer(IEntityRenderer<InfectedWolfEntity, InfectedWolfEntityModel> rendererIn) {
        super(rendererIn);
    }

    @Override
    public @NotNull RenderType getRenderType() {
        return RENDER_TYPE;
    }
}
