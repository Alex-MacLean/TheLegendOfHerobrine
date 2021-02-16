package com.herobrine.mod.util.entities;

import net.minecraft.util.ResourceLocation;

public class DefaultSurvivorSkins {
    public static void registerDefaultSkins() {
        SurvivorSkinRegistry.addClassicArmsSkin(new ResourceLocation("textures/entity/steve.png"));
        SurvivorSkinRegistry.addClassicArmsSkin(new ResourceLocation("herobrine:textures/entity/survivor/classic_arms/alexmaclean.png"));
        SurvivorSkinRegistry.addSmallArmsSkin(new ResourceLocation("textures/entity/alex.png"));
    }
}