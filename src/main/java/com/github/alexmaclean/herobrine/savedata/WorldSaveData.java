package com.github.alexmaclean.herobrine.savedata;

import com.google.gson.JsonObject;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Objects;

public class WorldSaveData {
    private final String fileName;
    private final String name;

    public WorldSaveData(String fileName, String name) {
        this.fileName = fileName;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void writeInt(@NotNull World world, String name, int value) {
        JsonObject json = new JsonObject();
        json.addProperty(name, value);
        try {
            Files.write(Paths.get(Objects.requireNonNull(world.getServer()).getFile("saves/" + world.getServer().getOverworld().toString().substring(12, world.getServer().getOverworld().toString().length() - 1) + "/" + fileName).getPath()), json.toString().getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeDouble(double value) {

    }

    public void writeBoolean(boolean value) {

    }

    public int readInt(/*World world, */String name) {
        return 0;
    }

    public double readDouble(String name) {
        return 0.0;
    }

    public boolean readBoolean(String name) {
        return false;
    }
}
