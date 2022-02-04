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
    // Read integer from Json file
    public static int readInt(@NotNull World world, String fileName, String dataName) {
        String path = Objects.requireNonNull(world.getServer()).getSavePath(WorldSavePath.ROOT).toString();
        try {
            JsonObject jsonObject = (JsonObject) JsonParser.parseReader(new FileReader(path.substring(0, path.length() - 1) + fileName));
            return jsonObject.get(dataName).getAsInt();
        } catch (FileNotFoundException e) {
            return 0;
        }
    }

    // Read double from Json file
    public static double readDouble(@NotNull World world, String fileName, String dataName) {
        String path = Objects.requireNonNull(world.getServer()).getSavePath(WorldSavePath.ROOT).toString();
        try {
            JsonObject jsonObject = (JsonObject) JsonParser.parseReader(new FileReader(path.substring(0, path.length() - 1) + fileName));
            return jsonObject.get(dataName).getAsDouble();
        } catch (FileNotFoundException e) {
            return 0.0;
        }
    }

    // Read boolean from Json file
    public static boolean readBoolean(@NotNull World world, String fileName, String dataName) {
        String path = Objects.requireNonNull(world.getServer()).getSavePath(WorldSavePath.ROOT).toString();
        try {
            JsonObject jsonObject = (JsonObject) JsonParser.parseReader(new FileReader(path.substring(0, path.length() - 1) + fileName));
            return jsonObject.get(dataName).getAsBoolean();
        } catch (FileNotFoundException e) {
            return false;
        }
    }
}