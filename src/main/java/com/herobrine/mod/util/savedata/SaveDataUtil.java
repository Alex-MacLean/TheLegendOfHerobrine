package com.herobrine.mod.util.savedata;

import com.herobrine.mod.config.Config;
import com.herobrine.mod.savedata.HerobrineSaveData;
import net.minecraft.world.IWorld;
import net.minecraft.world.server.ServerWorld;
import org.jetbrains.annotations.NotNull;

public class SaveDataUtil {
    public static @NotNull Boolean readBoolean(@NotNull IWorld world, String name) {
        if (!world.isClientSide() && world instanceof ServerWorld && name != null) {
            HerobrineSaveData saver = HerobrineSaveData.forWorld((ServerWorld) world);
            return saver.data.getBoolean(name);
        }
        return false;
    }

    public static void writeBoolean(@NotNull IWorld world, String name, Boolean data) {
        if (!world.isClientSide() && world instanceof ServerWorld && name != null && data != null) {
            HerobrineSaveData saver = HerobrineSaveData.forWorld((ServerWorld) world);
            saver.data.putBoolean(name, data);
        }
    }

    public static boolean canHerobrineSpawn(IWorld world) {
        if (Config.COMMON.HerobrineAlwaysSpawns.get() && !world.isClientSide()) {
            return true;
        }
        return readBoolean(world, "Spawn");
    }
}