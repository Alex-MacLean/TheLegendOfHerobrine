package com.herobrinemod.herobrine.savedata;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.herobrinemod.herobrine.HerobrineMod;
import org.apache.logging.log4j.core.config.json.JsonConfiguration;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.Objects;

public class ConfigHandler {
    private static Config herobrineConfig;
    public static void registerHerobrineConfig(String fileName) {
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
                herobrineConfig.getJson().add(key, defaultJsonObject.get(key));
                try {
                    Files.write(Paths.get(herobrineConfig.getPath()), herobrineConfig.getJson().toString().getBytes());
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }
    }

    public static Config getHerobrineConfig() {
        return herobrineConfig;
    }
}