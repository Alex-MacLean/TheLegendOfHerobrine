package com.herobrine.mod.client.renders;

import com.herobrine.mod.HerobrineMod;
import com.herobrine.mod.client.models.InfectedVillagerEntityModel;
import com.herobrine.mod.entities.InfectedVillagerEntity;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.jetbrains.annotations.NotNull;

@SideOnly(Side.CLIENT)
public class InfectedVillagerEntityRenderer extends RenderLiving<InfectedVillagerEntity> {
    public static ResourceLocation TEXTURES = HerobrineMod.location("textures/entity/infected_villager.png");

    public InfectedVillagerEntityRenderer(RenderManager manager) {
        super(manager, new InfectedVillagerEntityModel(0.0f), 0.5F);
    }

    @Override
    protected ResourceLocation getEntityTexture(@NotNull InfectedVillagerEntity entity) {
        return TEXTURES;
    }
}
