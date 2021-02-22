package com.herobrine.mod.util.entities;

import net.minecraft.util.ResourceLocation;

import java.util.ArrayList;
import java.util.Random;

public class SurvivorSkinRegistry {
    //ArrayLists that hold the resource locations of skins for survivors. One is for Classic Arms and the other for Small Arms
    private static final ArrayList<ResourceLocation> SmallArmsSkinList = new ArrayList<>();
    private static final ArrayList<ResourceLocation> ClassicArmsSkinList = new ArrayList<>();

    //Function that adds a resource location to the SmallArmsSkinList ArrayList.
    public static void addSmallArmsSkin(ResourceLocation skin) {
        SmallArmsSkinList.add(skin);
    }

    //Function that adds a resource location to the ClassicArmsSkinList ArrayList.
    public static void addClassicArmsSkin(ResourceLocation skin) {
        ClassicArmsSkinList.add(skin);
    }

    //When called chooses a random ResourceLocation from the ClassicArmsSkinList ArrayList.
    public static ResourceLocation chooseClassicArmsSkinFromList() {
        Random rand = new Random();
        return ClassicArmsSkinList.get(rand.nextInt(ClassicArmsSkinList.size()));
    }

    //When called chooses a random ResourceLocation from the SmallArmsSkinList ArrayList.
    public static ResourceLocation chooseSmallArmsSkinFromList() {
        Random rand = new Random();
        return SmallArmsSkinList.get(rand.nextInt(SmallArmsSkinList.size()));
    }
}
