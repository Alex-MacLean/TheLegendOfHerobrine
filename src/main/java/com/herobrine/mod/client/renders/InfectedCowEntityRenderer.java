package com.herobrine.mod.client.renders;

import com.herobrine.mod.HerobrineMod;
import com.herobrine.mod.client.models.InfectedCowEntityModel;
import com.herobrine.mod.entities.InfectedCowEntity;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.jetbrains.annotations.NotNull;

@SideOnly(Side.CLIENT)
public class InfectedCowEntityRenderer extends RenderLiving<InfectedCowEntity> {
    public static ResourceLocation TEXTURES = HerobrineMod.location("textures/entity/infected_cow.png");

    public InfectedCowEntityRenderer(RenderManager manager) {
        super(manager, new InfectedCowEntityModel(), 0.7F);
    }

    @Override
    protected ResourceLocation getEntityTexture(@NotNull InfectedCowEntity entity) {
        return TEXTURES;
    }
}
