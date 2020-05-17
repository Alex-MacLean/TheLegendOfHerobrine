package com.herobrine.mod.client.models;

import net.minecraft.client.model.ModelVillager;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class InfectedVillagerEntityModel extends ModelVillager {
    public InfectedVillagerEntityModel(float scale) {
        super(scale);
    }
}