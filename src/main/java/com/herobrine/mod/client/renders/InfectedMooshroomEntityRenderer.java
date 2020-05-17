package com.herobrine.mod.client.renders;

import com.herobrine.mod.HerobrineMod;
import com.herobrine.mod.client.models.InfectedCowEntityModel;
import com.herobrine.mod.client.renders.layers.InfectedMooshroomMushroomLayer;
import com.herobrine.mod.entities.InfectedMooshroomEntity;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.jetbrains.annotations.NotNull;

@SideOnly(Side.CLIENT)
public class InfectedMooshroomEntityRenderer extends RenderLiving<InfectedMooshroomEntity> {
    public static ResourceLocation TEXTURES = HerobrineMod.location("textures/entity/infected_mooshroom.png");

    public InfectedMooshroomEntityRenderer(RenderManager p_i47200_1_) {
        super(p_i47200_1_, new InfectedCowEntityModel(), 0.7F);
        this.addLayer(new InfectedMooshroomMushroomLayer(this));
    }

    @Override
    public @NotNull InfectedCowEntityModel getMainModel() {
        return (InfectedCowEntityModel)super.getMainModel();
    }

    protected ResourceLocation getEntityTexture(@NotNull InfectedMooshroomEntity entity) {
        return TEXTURES;
    }
}
