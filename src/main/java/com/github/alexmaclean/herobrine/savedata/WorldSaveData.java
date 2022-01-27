package com.github.alexmaclean.herobrine.savedata;

import com.google.gson.JsonObject;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;
import org.apache.logging.log4j.core.jmx.Server;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Objects;

public class WorldSaveData {
    private final String fileName;
    private final JsonObject json = new JsonObject();

    public WorldSaveData(String fileName) {
        this.fileName = fileName;
    }

    public void writeInt(@NotNull World world, String name, int value) {
        if(world instanceof ServerWorld) {
            json.addProperty(name, value);
            try {
                // THREE times now. Jesus, Alex
                Files.write(Paths.get(Objects.requireNonNull(world.getServer()).getFile("saves/" + world.getServer().getOverworld().toString().substring(12, world.getServer().getOverworld().toString().length() - 7) + "/" + fileName).getPath()), json.toString().getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void writeDouble(@NotNull World world, String name, double value) {
        if(world instanceof ServerWorld) {
            json.addProperty(name, value);
            try {
                // THREE times now. Jesus, Alex
                Files.write(Paths.get(Objects.requireNonNull(world.getServer()).getFile("saves/" + world.getServer().getOverworld().toString().substring(12, world.getServer().getOverworld().toString().length() - 7) + "/" + fileName).getPath()), json.toString().getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void writeBoolean(@NotNull World world, String name, Boolean value) {
        if(world instanceof ServerWorld) {
            json.addProperty(name, value);
            try {
                // THREE times now. Jesus, Alex
                System.out.println("Testing file at: " + Objects.requireNonNull(world.getServer()).getFile("saves/" + world.getServer().getOverworld().toString().substring(12, world.getServer().getOverworld().toString().length() - 7) + "/" + fileName).getPath());
                Files.write(Paths.get(Objects.requireNonNull(world.getServer()).getFile("saves/" + world.getServer().getOverworld().toString().substring(12, world.getServer().getOverworld().toString().length() - 7) + "/" + fileName).getPath()), json.toString().getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public int readInt(String name) {
        return json.get(name).getAsInt();
    }

    public double readDouble(String name) {
        return json.get(name).getAsDouble();
    }

    public boolean readBoolean(String name) {
        return json.get(name).getAsBoolean();
    }
}
