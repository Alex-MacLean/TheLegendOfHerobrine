package com.herobrine.mod.client.models;

import net.minecraft.client.model.ModelPlayer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class AbstractHerobrineEntityModel extends ModelPlayer {
    public AbstractHerobrineEntityModel(float modelSize, boolean smallArmsIn) {
        super(modelSize, smallArmsIn);
    }
}
