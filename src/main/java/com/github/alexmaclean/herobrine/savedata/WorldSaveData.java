package com.github.alexmaclean.herobrine.savedata;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
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
    private final String fileName;
    private final JsonObject json = new JsonObject();
    private final World world;
    private final String path;

    // Constructor for WorldSaveData. Takes the World to save the data to and String for the Json file name as parameters. Ex: ... new WorldSaveData(world, "file.json");
    public WorldSaveData(@NotNull World world, String fileName) {
        this.world = world;
        this.fileName = fileName;
        // For some reason WorldSavePath.ROOT has a period at the end. Use substring method to work around this when referenced
        this.path = Objects.requireNonNull(world.getServer()).getSavePath(WorldSavePath.ROOT).toString();
    }

    // Write integer value to Json file
    public void writeInt(String name, int value) {
        if(world instanceof ServerWorld) {
            json.addProperty(name, value);

            try {
                Files.write(Paths.get(path.substring(0, path.length() - 1) + fileName), json.toString().getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // Write double value to Json file
    public void writeDouble(String name, double value) {
        if(world instanceof ServerWorld) {
            json.addProperty(name, value);

            try {
                Files.write(Paths.get(path.substring(0, path.length() - 1) + fileName), json.toString().getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // Write boolean value to Json file
    public void writeBoolean(String name, Boolean value) {
        if(world instanceof ServerWorld) {
            json.addProperty(name, value);

            try {
                Files.write(Paths.get(path.substring(0, path.length() - 1) + fileName), json.toString().getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // Read integer from Json file
    public int readInt(String name) {
        try {
            JsonObject jsonObject = (JsonObject) JsonParser.parseReader(new FileReader(path.substring(0, path.length() - 1) + fileName));

            return jsonObject.get(name).getAsInt();
        } catch (FileNotFoundException e) {
            return 0;
        }
    }

    // Read double from Json file
    public double readDouble(String name) {
        try {
            JsonObject jsonObject = (JsonObject) JsonParser.parseReader(new FileReader(path.substring(0, path.length() - 1) + fileName));

            return jsonObject.get(name).getAsDouble();
        } catch (FileNotFoundException e) {
            return 0.0;
        }
    }

    // Read boolean from Json file
    public boolean readBoolean(String name) {
        try {
            JsonObject jsonObject = (JsonObject) JsonParser.parseReader(new FileReader(path.substring(0, path.length() - 1) + fileName));

            return jsonObject.get(name).getAsBoolean();
        } catch (FileNotFoundException e) {
            return false;
        }
    }
}