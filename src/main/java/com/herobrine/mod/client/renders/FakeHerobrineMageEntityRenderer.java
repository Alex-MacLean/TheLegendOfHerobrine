package com.herobrine.mod.client.renders;

import com.herobrine.mod.HerobrineMod;
import com.herobrine.mod.client.models.HerobrineMageEntityModel;
import com.herobrine.mod.entities.FakeHerobrineMageEntity;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.jetbrains.annotations.NotNull;

@SideOnly(Side.CLIENT)
public class FakeHerobrineMageEntityRenderer extends RenderLiving<FakeHerobrineMageEntity> {
    public static ResourceLocation TEXTURES = HerobrineMod.location("textures/entity/herobrine.png");

    public FakeHerobrineMageEntityRenderer(RenderManager manager) {
        super(manager, new HerobrineMageEntityModel(), 0.5F);
    }

    @Override
    protected ResourceLocation getEntityTexture(@NotNull FakeHerobrineMageEntity entity) {
        return TEXTURES;
    }
}
