package com.herobrine.mod.util.entities;

import net.minecraft.util.ResourceLocation;

import java.util.ArrayList;
import java.util.Random;

public class SurvivorSkinRegistry {
    private static final ArrayList<ResourceLocation> SmallArmsSkinList = new ArrayList<>();
    private static final ArrayList<ResourceLocation> ClassicArmsSkinList = new ArrayList<>();

    public static void addSmallArmsSkin(ResourceLocation skin) {
        SmallArmsSkinList.add(skin);
    }

    public static void addClassicArmsSkin(ResourceLocation skin) {
        ClassicArmsSkinList.add(skin);
    }

    public static ResourceLocation chooseClassicArmsSkinFromList() {
        Random rand = new Random();
        return ClassicArmsSkinList.get(rand.nextInt(ClassicArmsSkinList.size()));
    }

    public static ResourceLocation chooseSmallArmsSkinFromList() {
        Random rand = new Random();
        return SmallArmsSkinList.get(rand.nextInt(SmallArmsSkinList.size()));
    }
}
