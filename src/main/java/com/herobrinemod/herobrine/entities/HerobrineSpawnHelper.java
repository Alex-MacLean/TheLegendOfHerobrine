package com.herobrinemod.herobrine.entities;

import com.herobrinemod.herobrine.savedata.ConfigHandler;
import com.herobrinemod.herobrine.savedata.SaveDataHandler;

public class HerobrineSpawnHelper {
    public static boolean canHerobrineSpawn() {
        return SaveDataHandler.getHerobrineSaveData().readBoolean("herobrineSummoned") || ConfigHandler.herobrineConfig.readBoolean("HerobrineAlwaysSpawns");
    }
}
