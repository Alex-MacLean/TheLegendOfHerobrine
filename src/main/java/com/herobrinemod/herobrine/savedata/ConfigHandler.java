package com.herobrinemod.herobrine.savedata;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.herobrinemod.herobrine.HerobrineMod;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

public class ConfigHandler {
    private static Config herobrineConfig;
    public static void registerHerobrineConfig(String fileName) {
        boolean fixedConfig = false;
        herobrineConfig = new Config(fileName);
        byte[] defaultJson = new byte[0];
        try {
            defaultJson = Files.readAllBytes(Path.of(Objects.requireNonNull(HerobrineMod.class.getClassLoader().getResource("data/" + HerobrineMod.MODID + "/default_config/herobrine.json")).toURI()));
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }

        if(herobrineConfig.getJson() == null) {
            herobrineConfig.setFile(defaultJson);
            return;
        }

        JsonObject defaultJsonObject = JsonParser.parseString(new String(defaultJson)).getAsJsonObject();
        for (String key : defaultJsonObject.keySet()) {
            if(!herobrineConfig.getJson().has(key)) {
                herobrineConfig.getJson().addProperty(key, String.valueOf(defaultJsonObject.get(key)));
                fixedConfig = true;
            }
        }

        if(fixedConfig) {
            try {
                Files.write(Paths.get(herobrineConfig.getPath()), herobrineConfig.getJson().toString().getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static Config getHerobrineConfig() {
        return herobrineConfig;
    }
}