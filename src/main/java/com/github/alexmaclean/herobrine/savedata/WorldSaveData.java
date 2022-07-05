package com.github.alexmaclean.herobrine.savedata;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.WorldSavePath;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Objects;

public class WorldSaveData {
    private static JsonObject json = null; // Allows loading the json data file to memory
    private static boolean updateData; // Pointer for whether the data in memory needs to be updated
    private static String jsonFileName; // Store Json file name to be checked when reading or writing data

    // Read integer from json file
    public static int readInt(@NotNull World world, String fileName, String dataName) {
        String path = Objects.requireNonNull(world.getServer()).getSavePath(WorldSavePath.ROOT).toString();
        try {
            if(json == null || updateData || !jsonFileName.equals(fileName)) { // Check if the json file is null or needs to be reloaded to memory
                json = (JsonObject) JsonParser.parseReader(new FileReader(path.substring(0, path.length() - 1) + fileName)); // Loads json file to memory so the game does not need to read from the disk every time
                jsonFileName = fileName;
                updateData = false;
            }
            return json.get(dataName).getAsInt();
        } catch (FileNotFoundException e) {
            return 0;
        }
    }

    // Read double from json file
    public static double readDouble(@NotNull World world, String fileName, String dataName) {
        String path = Objects.requireNonNull(world.getServer()).getSavePath(WorldSavePath.ROOT).toString();
        try {
            if(json == null || updateData || !jsonFileName.equals(fileName)) {
                json = (JsonObject) JsonParser.parseReader(new FileReader(path.substring(0, path.length() - 1) + fileName));
                jsonFileName = fileName;
                updateData = false;
            }
            return json.get(dataName).getAsDouble();
        } catch (FileNotFoundException e) {
            return 0.0;
        }
    }

    // Read boolean from json file
    public static boolean readBoolean(@NotNull World world, String fileName, String dataName) {
        String path = Objects.requireNonNull(world.getServer()).getSavePath(WorldSavePath.ROOT).toString();
        try {
            if(json == null || updateData || !jsonFileName.equals(fileName)) {
                json = (JsonObject) JsonParser.parseReader(new FileReader(path.substring(0, path.length() - 1) + fileName));
                jsonFileName = fileName;
                updateData = false;
            }
            return json.get(dataName).getAsBoolean();
        } catch (FileNotFoundException e) {
            return false;
        }
    }

    // Write integer value to json file
    public static void writeInt(World world, String fileName, String dataName, int dataValue) {
        if(world instanceof ServerWorld) {
            try {
                String path = Objects.requireNonNull(world.getServer()).getSavePath(WorldSavePath.ROOT).toString();
                String jsonPath = path.substring(0, path.length() - 1) + fileName;
                if(json == null || updateData || !jsonFileName.equals(fileName)) {
                    try {
                        json = (JsonObject) JsonParser.parseReader(new FileReader(jsonPath));
                    } catch(java.io.FileNotFoundException e) {
                        json =  new JsonObject();
                    }
                    jsonFileName = fileName;
                    updateData = false;
                }
                json.addProperty(dataName, dataValue);
                Files.write(Paths.get(jsonPath), json.toString().getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // Write double value to json file
    public static void writeDouble(World world, String fileName, String dataName, double dataValue) {
        if(world instanceof ServerWorld) {
            try {
                String path = Objects.requireNonNull(world.getServer()).getSavePath(WorldSavePath.ROOT).toString();
                String jsonPath = path.substring(0, path.length() - 1) + fileName;
                if(json == null || updateData || !jsonFileName.equals(fileName)) {
                    try {
                        json = (JsonObject) JsonParser.parseReader(new FileReader(jsonPath));
                    } catch(java.io.FileNotFoundException e) {
                        json =  new JsonObject();
                    }
                    jsonFileName = fileName;
                    updateData = false;
                }
                json.addProperty(dataName, dataValue);
                Files.write(Paths.get(jsonPath), json.toString().getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // Write boolean value to json file
    public static void writeBoolean(@NotNull World world, String fileName, String dataName, boolean dataValue) {
        if(world instanceof ServerWorld) {
            try {
                String path = Objects.requireNonNull(world.getServer()).getSavePath(WorldSavePath.ROOT).toString();
                String jsonPath = path.substring(0, path.length() - 1) + fileName;
                if(json == null || updateData || !jsonFileName.equals(fileName)) {
                    try {
                        json = (JsonObject) JsonParser.parseReader(new FileReader(jsonPath));
                    } catch(java.io.FileNotFoundException e) {
                        json =  new JsonObject();
                    }
                    jsonFileName = fileName;
                    updateData = false;
                }
                json.addProperty(dataName, dataValue);
                Files.write(Paths.get(jsonPath), json.toString().getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // Set whether json in memory needs to be updated
    public static void setUpdateData(boolean b) {
        updateData = b;
    }

    // Returns whether json in memory needs to be updated
    public static boolean getUpdateData() {
        return updateData;
    }

    // Get json in memory
    public static JsonObject getJson() {
        return json;
    }

    public static void handleServerStart(MinecraftServer server) {
        jsonFileName = "";
        json = null;
        updateData = true;
    }

    public static void handleServerStop(MinecraftServer server) {
        jsonFileName = "";
        json = null;
        updateData = true;
    }
}