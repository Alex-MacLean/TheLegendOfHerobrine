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
            // I am not at all proud of this segment of code. Even I barely understand what I have done to make it work. It is a combination of black magic and poor programming practices. Minecraft does not have a method to get the root directory of a world, so I had to improvise
            Files.write(Paths.get(Objects.requireNonNull(world.getServer()).getFile("saves/" + world.getServer().getOverworld().toString().substring(12, world.getServer().getOverworld().toString().length() - 7) + "/" + fileName).getPath()), json.toString().getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeDouble(@NotNull World world, String name, int value) {
        JsonObject json = new JsonObject();
        json.addProperty(name, value);
        try {
            // Oh boy, here it is again :)
            Files.write(Paths.get(Objects.requireNonNull(world.getServer()).getFile("saves/" + world.getServer().getOverworld().toString().substring(12, world.getServer().getOverworld().toString().length() - 7) + "/" + fileName).getPath()), json.toString().getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeBoolean(@NotNull World world, String name, int value) {
        JsonObject json = new JsonObject();
        json.addProperty(name, value);
        try {
            // THREE times now. Jesus, Alex
            Files.write(Paths.get(Objects.requireNonNull(world.getServer()).getFile("saves/" + world.getServer().getOverworld().toString().substring(12, world.getServer().getOverworld().toString().length() - 7) + "/" + fileName).getPath()), json.toString().getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
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
