package com.herobrinemod.herobrine.savedata;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.fabricmc.loader.impl.FabricLoaderImpl;
import net.minecraft.client.MinecraftClient;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Config {
    private JsonObject json; // Allows loading the json data file to memory
    private final String directory;
    private final String path;
    public Config(String fileName) {
        // Store the path of the Json file
        this.directory = FabricLoaderImpl.INSTANCE.getConfigDir().toString() + "/";
        this.path = directory + fileName;
        try {
            this.json = (JsonObject) JsonParser.parseReader(new FileReader(path));
        } catch (FileNotFoundException e) {
            this.json = null;
        }
    }

    // Read integer from json file
    public int readInt(String dataName) {
        if(json.get(dataName) == null || this.json == null) {
            System.out.println("[The Legend of Herobrine/Config/ERROR]: Integer \"" + dataName + "\" Could not be found!");
            return 0;
        }
        return json.get(dataName).getAsInt();
    }

    // Read double from json file
    public double readDouble(String dataName) {
        if(json.get(dataName) == null || this.json == null) {
            System.out.println("[The Legend of Herobrine/Config/ERROR]: Double \"" + dataName + "\" Could not be found!");
            return 0.0;
        }
        return json.get(dataName).getAsDouble();
    }

    // Read float from json file
    public float readFloat(String dataName) {
        if(json.get(dataName) == null || this.json == null) {
            System.out.println("[The Legend of Herobrine/Config/ERROR]: Float \"" + dataName + "\" Could not be found!");
            return 0.0f;
        }
        return json.get(dataName).getAsFloat();
    }

    // Read boolean from json file
    public boolean readBoolean(String dataName) {
        if(json.get(dataName) == null || this.json == null) {
            System.out.println("[The Legend of Herobrine/Config/ERROR]: Boolean \"" + dataName + "\" Could not be found!");
            return false;
        }
        return json.get(dataName).getAsBoolean();
    }

    // Get Json file in memory
    public JsonObject getJson() {
        return json;
    }

    public String getPath() {
        return path;
    }

    public void setFile(byte[] newJson) {
        try {
            json = JsonParser.parseString(new String(newJson)).getAsJsonObject();
            if(!new File(directory).exists()) {
                Files.createDirectory(Path.of(MinecraftClient.getInstance().runDirectory.getAbsolutePath() + "/config/"));
            }
            Files.write(Paths.get(path), newJson);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
