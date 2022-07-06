package com.github.alexmaclean.herobrine.savedata;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.WorldSavePath;
import org.jetbrains.annotations.NotNull;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Objects;

public class WorldSaveData {
    private JsonObject json; // Allows loading the json data file to memory
    private final String fileName; // Store the name of the Json file
    private final String jsonPath;
    public WorldSaveData(@NotNull MinecraftServer server, String fileName) {
        this.json = null;
        this.fileName = fileName;
        String path = Objects.requireNonNull(server).getSavePath(WorldSavePath.ROOT).toString();
        this.jsonPath = path.substring(0, path.length() - 1) + fileName;
    }

    // Read integer from json file
    public int readInt(String dataName) {
        try {
            if(json == null) { // Check if the json file is null or needs to be reloaded to memory
                json = (JsonObject) JsonParser.parseReader(new FileReader(jsonPath)); // Loads json file to memory so the game does not need to read from the disk every time
            }
            return json.get(dataName).getAsInt();
        } catch (FileNotFoundException e) {
            return 0;
        }
    }

    // Read double from json file
    public double readDouble(String dataName) {
        try {
            if(json == null) {
                json = (JsonObject) JsonParser.parseReader(new FileReader(jsonPath));
            }
            return json.get(dataName).getAsDouble();
        } catch (FileNotFoundException e) {
            return 0.0;
        }
    }

    // Read boolean from json file
    public boolean readBoolean(String dataName) {
        try {
            if(json == null) {
                json = (JsonObject) JsonParser.parseReader(new FileReader(jsonPath));
            }
            return json.get(dataName).getAsBoolean();
        } catch (FileNotFoundException e) {
            return false;
        }
    }

    // Write integer value to json file
    public void writeInt(String dataName, int dataValue) {
        try {
            if(json == null) {
                try {
                    json = (JsonObject) JsonParser.parseReader(new FileReader(jsonPath));
                } catch(java.io.FileNotFoundException e) {
                    json =  new JsonObject();
                }
            }
            json.addProperty(dataName, dataValue);
            Files.write(Paths.get(jsonPath), json.toString().getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Write double value to json file
    public void writeDouble(String dataName, double dataValue) {
        try {
            if(json == null) {
                try {
                    json = (JsonObject) JsonParser.parseReader(new FileReader(jsonPath));
                } catch(java.io.FileNotFoundException e) {
                    json =  new JsonObject();
                }
            }
            json.addProperty(dataName, dataValue);
            Files.write(Paths.get(jsonPath), json.toString().getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Write boolean value to json file
    public void writeBoolean(String dataName, boolean dataValue) {
        try {
            if(json == null) {
                try {
                    json = (JsonObject) JsonParser.parseReader(new FileReader(jsonPath));
                } catch(java.io.FileNotFoundException e) {
                    json =  new JsonObject();
                }
            }
            json.addProperty(dataName, dataValue);
            Files.write(Paths.get(jsonPath), json.toString().getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Get Json file in memory
    public JsonObject getJson() {
        return json;
    }

    // Return name of the Json file in memory
    public String getFileName() {
        return fileName;
    }
}