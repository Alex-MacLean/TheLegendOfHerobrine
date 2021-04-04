package com.herobrine.mod.util.savedata;

import com.herobrine.mod.config.Config;
import com.herobrine.mod.savedata.HerobrineSaveData;
import net.minecraft.world.IWorld;
import net.minecraft.world.server.ServerWorld;

public class SaveDataUtil {

    public static Boolean readBoolean(IWorld world, String name) {
        if(!world.isRemote()) {
            HerobrineSaveData saver = HerobrineSaveData.forWorld((ServerWorld) world);
            return saver.data.getBoolean(name);
        } else return false;
    }

    public static void writeBoolean(IWorld world, String name, Boolean data) {
        if(!world.isRemote()) {
            HerobrineSaveData saver = HerobrineSaveData.forWorld((ServerWorld) world);
            saver.data.putBoolean(name, data);
        }
    }

    public static boolean canHerobrineSpawn(IWorld world) {
        if(Config.COMMON.HerobrineAlwaysSpawns.get() && !world.isRemote()) {
            return true;
        }
        return readBoolean(world, "Spawn");
    }
}
