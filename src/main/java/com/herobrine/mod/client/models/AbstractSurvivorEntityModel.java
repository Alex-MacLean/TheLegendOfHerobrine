package com.herobrine.mod.client.models;

import net.minecraft.client.model.ModelPlayer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class AbstractSurvivorEntityModel extends ModelPlayer {
    public AbstractSurvivorEntityModel(float modelSize, boolean smallArmsIn) {
        super(modelSize, smallArmsIn);
    }
}
