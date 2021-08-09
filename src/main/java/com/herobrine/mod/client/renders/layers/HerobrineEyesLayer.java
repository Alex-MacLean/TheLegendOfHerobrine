package com.herobrine.mod.client.renders.layers;

import com.herobrine.mod.HerobrineMod;
import com.herobrine.mod.client.models.AbstractHerobrineEntityModel;
import com.herobrine.mod.entities.AbstractHerobrineEntity;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.layers.AbstractEyesLayer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
public class HerobrineEyesLayer extends AbstractEyesLayer<AbstractHerobrineEntity, AbstractHerobrineEntityModel> {
    public HerobrineEyesLayer(IEntityRenderer<AbstractHerobrineEntity, AbstractHerobrineEntityModel> renderer) {
        super(renderer);
    }

    @Override
    public @NotNull RenderType renderType() {
        return RenderType.eyes(HerobrineMod.location("textures/entity/eyes/herobrine.png"));
    }
}