package com.herobrinemod.herobrine.savedata;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.minecraft.client.MinecraftClient;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Config {
    private JsonObject json; // Allows loading the json data file to memory
    private final String path;
    public Config(String fileName) {
        this.json = null;
        // Store the name of the Json file
        path = MinecraftClient.getInstance().runDirectory.getAbsolutePath() + "/config/" + fileName;
    }

    // Read integer from json file
    public int readInt(String dataName) {
        try {
            if(json == null) { // Check if the json file is null or needs to be reloaded to memory
                json = (JsonObject) JsonParser.parseReader(new FileReader(path)); // Loads json file to memory so the game does not need to read from the disk every time
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
                json = (JsonObject) JsonParser.parseReader(new FileReader(path));
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
                json = (JsonObject) JsonParser.parseReader(new FileReader(path));
            }
            return json.get(dataName).getAsBoolean();
        } catch (FileNotFoundException e) {
            return false;
        }
    }

    // Get Json file in memory
    public JsonObject getJson() {
        return json;
    }

    public void setFile(byte[] newJson) {
        try {
            Files.write(Paths.get(path), newJson);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
