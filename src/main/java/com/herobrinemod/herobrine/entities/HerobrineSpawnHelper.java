package com.herobrinemod.herobrine.entities;

import com.herobrinemod.herobrine.savedata.ConfigHandler;
import com.herobrinemod.herobrine.savedata.SaveDataHandler;

public class HerobrineSpawnHelper {
    public static boolean canHerobrineSpawn() {
        return SaveDataHandler.getHerobrineSaveData().readBoolean("herobrineSummoned") || ConfigHandler.getHerobrineConfig().readBoolean("HerobrineAlwaysSpawns");
    }

    public static int getStage() {
        return ConfigHandler.getHerobrineConfig().readBoolean("HerobrineAlwaysSpawns") ? 3 : SaveDataHandler.getHerobrineSaveData().readInt("stage");
    }

    public static int getStageTime() {
        return ConfigHandler.getHerobrineConfig().readBoolean("HerobrineAlwaysSpawns") ? 0 : SaveDataHandler.getHerobrineSaveData().readInt("stageTime");
    }
}