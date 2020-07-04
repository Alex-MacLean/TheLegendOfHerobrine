package com.herobrine.mod.client.renders;

import com.herobrine.mod.HerobrineMod;
import com.herobrine.mod.client.models.InfectedLlamaEntityModel;
import com.herobrine.mod.entities.InfectedLlamaEntity;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.jetbrains.annotations.NotNull;

@SideOnly(Side.CLIENT)
public class InfectedLlamaEntityRenderer extends RenderLiving<InfectedLlamaEntity> {
    private static final ResourceLocation[] TEXTURES = new ResourceLocation[]{new ResourceLocation(HerobrineMod.MODID + ":textures/entity/infected_llama/creamy.png"), new ResourceLocation(HerobrineMod.MODID + ":textures/entity/infected_llama/white.png"), new ResourceLocation(HerobrineMod.MODID + ":textures/entity/infected_llama/brown.png"), new ResourceLocation(HerobrineMod.MODID + ":textures/entity/infected_llama/gray.png")};

    public InfectedLlamaEntityRenderer(RenderManager manager) {
        super(manager, new InfectedLlamaEntityModel(0.0F), 0.7F);
    }

    @Override
    protected ResourceLocation getEntityTexture(@NotNull InfectedLlamaEntity entity) {
        return TEXTURES[entity.getVariant()];
    }
}
