package com.herobrinemod.herobrine.savedata;

import com.herobrinemod.herobrine.HerobrineMod;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;

public class ConfigHandler {
    public static Config herobrineConfig;
    public static void registerHerobrineConfig(String fileName) {
        herobrineConfig = new Config(fileName);
        if(herobrineConfig.getJson() == null) {
            try {
                herobrineConfig.setFile(Files.readAllBytes(Path.of(Objects.requireNonNull(HerobrineMod.class.getClassLoader().getResource("data/" + HerobrineMod.MODID + "/default_config/herobrine.json")).toURI())));
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        }
    }
}