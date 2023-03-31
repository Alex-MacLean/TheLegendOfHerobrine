package com.herobrinemod.herobrine.savedata;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.herobrinemod.herobrine.HerobrineMod;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
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

        JsonObject json = JsonParser.parseString(new String(defaultJson)).getAsJsonObject();
        Iterator<String> keys = json.keySet().iterator();
        while (keys.hasNext()) {
            if(!herobrineConfig.getJson().has(keys.toString())) {
                herobrineConfig.getJson().add(keys.toString(), json.get(keys.toString()));
            }
            keys.next();
        }
    }

    public static Config getHerobrineConfig() {
        return herobrineConfig;
    }
}