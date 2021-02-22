package com.herobrine.mod.util.entities;

import net.minecraft.util.ResourceLocation;

//Registry for the default survivor skins from this mod.
public class DefaultSurvivorSkins {
    //Function that registers each skin resource location by adding them to an Arraylist.
    public static void registerDefaultSkins() {
        SurvivorSkinRegistry.addClassicArmsSkin(new ResourceLocation("textures/entity/steve.png"));
        SurvivorSkinRegistry.addClassicArmsSkin(new ResourceLocation("herobrine:textures/entity/survivor/classic_arms/alexmaclean.png"));
        SurvivorSkinRegistry.addSmallArmsSkin(new ResourceLocation("textures/entity/alex.png"));
    }
}