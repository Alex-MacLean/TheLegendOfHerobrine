package com.herobrine.mod.client.renders.layers;

import com.herobrine.mod.HerobrineMod;
import com.herobrine.mod.client.models.InfectedLlamaEntityModel;
import com.herobrine.mod.entities.InfectedLlamaEntity;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.layers.AbstractEyesLayer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
public class InfectedLlamaEyesLayer extends AbstractEyesLayer<InfectedLlamaEntity, InfectedLlamaEntityModel<InfectedLlamaEntity>> {
    public InfectedLlamaEyesLayer(IEntityRenderer<InfectedLlamaEntity, InfectedLlamaEntityModel<InfectedLlamaEntity>> renderer) {
        super(renderer);
    }

    @Override
    public @NotNull RenderType renderType() {
        return RenderType.eyes(HerobrineMod.location("textures/entity/eyes/infected_llama.png"));
    }
}