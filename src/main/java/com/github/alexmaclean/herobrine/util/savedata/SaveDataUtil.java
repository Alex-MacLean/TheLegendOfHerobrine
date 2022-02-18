package com.github.alexmaclean.herobrine.util.savedata;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.minecraft.util.WorldSavePath;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Objects;

public class SaveDataUtil {
    private static JsonObject json = null; // Allows loading the json data file to memory
    public static boolean updateData; // Pointer for whether the data in memory needs to be updated. Safe to reference and set directly without getter or setter methods as you do not create instances of this class.

    // Read integer from Json file
    public static int readInt(@NotNull World world, String fileName, String dataName) {
        String path = Objects.requireNonNull(world.getServer()).getSavePath(WorldSavePath.ROOT).toString();
        try {
            if(json == null || updateData) { // Check if the json file is null or needs to be reloaded to memory
                json = (JsonObject) JsonParser.parseReader(new FileReader(path.substring(0, path.length() - 1) + fileName)); // Loads json file to memory so the game does not need to read from the disk every time
                updateData = false;
            }
            return json.get(dataName).getAsInt();
        } catch (FileNotFoundException e) {
            return 0;
        }
    }

    // Read double from Json file
    public static double readDouble(@NotNull World world, String fileName, String dataName) {
        String path = Objects.requireNonNull(world.getServer()).getSavePath(WorldSavePath.ROOT).toString();
        try {
            if(json == null || updateData) {
                json = (JsonObject) JsonParser.parseReader(new FileReader(path.substring(0, path.length() - 1) + fileName));
                updateData = false;
            }
            return json.get(dataName).getAsDouble();
        } catch (FileNotFoundException e) {
            return 0.0;
        }
    }

    // Read boolean from Json file
    public static boolean readBoolean(@NotNull World world, String fileName, String dataName) {
        String path = Objects.requireNonNull(world.getServer()).getSavePath(WorldSavePath.ROOT).toString();
        try {
            if(json == null || updateData) {
                json = (JsonObject) JsonParser.parseReader(new FileReader(path.substring(0, path.length() - 1) + fileName));
                updateData = false;
            }
            return json.get(dataName).getAsBoolean();
        } catch (FileNotFoundException e) {
            return false;
        }
    }
}