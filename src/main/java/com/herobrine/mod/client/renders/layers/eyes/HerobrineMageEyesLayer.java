package com.herobrine.mod.client.renders.layers.eyes;

import com.herobrine.mod.HerobrineMod;
import com.herobrine.mod.client.models.AbstractHerobrineMageEntityModel;
import com.herobrine.mod.entities.AbstractHerobrineEntity;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.layers.AbstractEyesLayer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
public class HerobrineMageEyesLayer extends AbstractEyesLayer<AbstractHerobrineEntity, AbstractHerobrineMageEntityModel<AbstractHerobrineEntity>> {
    private static final RenderType RENDER_TYPE = RenderType.getEyes(HerobrineMod.location("textures/entity/eyes/herobrine.png"));

    public HerobrineMageEyesLayer(IEntityRenderer<AbstractHerobrineEntity, AbstractHerobrineMageEntityModel<AbstractHerobrineEntity>> rendererIn) {
        super(rendererIn);
    }

    public @NotNull RenderType getRenderType() {
        return RENDER_TYPE;
    }
}
