package com.herobrinemod.herobrine.entities;

import net.minecraft.util.Identifier;

import java.util.ArrayList;

public class SurvivorSkinRegistry {
    // ArrayList that stores all registered Survivor skins
    private static final ArrayList<Identifier> skinList = new ArrayList<>();

    // Adds a skin to skinList with the use of an Identifier
    public static void addSkin(Identifier skinLocation) {
        skinList.add(skinLocation);
    }

    // Safely access skinList
    public static ArrayList<Identifier> getSkinList() {
        return skinList;
    }
}