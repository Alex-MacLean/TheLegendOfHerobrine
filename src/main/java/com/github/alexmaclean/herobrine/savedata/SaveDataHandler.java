package com.github.alexmaclean.herobrine.savedata;

import net.minecraft.server.MinecraftServer;
import org.jetbrains.annotations.NotNull;

public class SaveDataHandler { // Class to handle save data on a per-world basis. Part of a proper WorldSaveData implementation
    private static WorldSaveData herobrineSaveData; // Pointer for the WorldSaveData instance for the currently loaded world

    // Return instance of WorldSaveData
    public static WorldSaveData getHerobrineSaveData() {
        return herobrineSaveData;
    }

    // Properly initialize the instance of WorldSaveData when loading the server
    public static void handleServerStart(@NotNull MinecraftServer server) {
        herobrineSaveData = new WorldSaveData(server, "herobrine.json");
    }

    // Unload all save data for a world when the server stops
    public static void handleServerStop(MinecraftServer ignored) {
        herobrineSaveData = null;
    }
}