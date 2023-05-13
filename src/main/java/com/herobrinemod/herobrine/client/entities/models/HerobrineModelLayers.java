package com.herobrinemod.herobrine.client.entities.models;

import com.herobrinemod.herobrine.HerobrineMod;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class HerobrineModelLayers {
    public static final EntityModelLayer HEROBRINE_MODEL_LAYER = new EntityModelLayer(new Identifier(HerobrineMod.MODID, "herobrine"), "main");
    public static final EntityModelLayer HEROBRINE_MAGE_MODEL_LAYER = new EntityModelLayer(new Identifier(HerobrineMod.MODID, "herobrine_mage"), "main");
    public static final EntityModelLayer INFECTED_PIG_MODEL_LAYER = new EntityModelLayer(new Identifier(HerobrineMod.MODID, "infected_pig"), "main");
    public static final EntityModelLayer INFECTED_COW_MODEL_LAYER = new EntityModelLayer(new Identifier(HerobrineMod.MODID, "infected_cow"), "main");
    public static final EntityModelLayer INFECTED_VILLAGER_MODEL_LAYER = new EntityModelLayer(new Identifier(HerobrineMod.MODID, "infected_villager"), "main");
    public static final EntityModelLayer INFECTED_CHICKEN_MODEL_LAYER = new EntityModelLayer(new Identifier(HerobrineMod.MODID, "infected_chicken"), "main");
    public static final EntityModelLayer INFECTED_SHEEP_MODEL_LAYER = new EntityModelLayer(new Identifier(HerobrineMod.MODID, "infected_sheep"), "main");
    public static final EntityModelLayer INFECTED_BAT_MODEL_LAYER = new EntityModelLayer(new Identifier(HerobrineMod.MODID, "infected_bat"), "main");
    public static final EntityModelLayer INFECTED_WOLF_MODEL_LAYER = new EntityModelLayer(new Identifier(HerobrineMod.MODID, "infected_wolf"), "main");
    public static final EntityModelLayer INFECTED_DONKEY_MODEL_LAYER = new EntityModelLayer(new Identifier(HerobrineMod.MODID, "infected_donkey"), "main");
    public static final EntityModelLayer INFECTED_HORSE_MODEL_LAYER = new EntityModelLayer(new Identifier(HerobrineMod.MODID, "infected_horse"), "main");
    public static final EntityModelLayer INFECTED_LLAMA_MODEL_LAYER = new EntityModelLayer(new Identifier(HerobrineMod.MODID, "infected_llama"), "main");
    public static final EntityModelLayer INFECTED_LLAMA_SPIT_MODEL_LAYER = new EntityModelLayer(new Identifier(HerobrineMod.MODID, "infected_llama_spit"), "main");
    public static final EntityModelLayer INFECTED_RABBIT_MODEL_LAYER = new EntityModelLayer(new Identifier(HerobrineMod.MODID, "infected_rabbit"), "main");
    public static final EntityModelLayer SURVIVOR_MODEL_LAYER = new EntityModelLayer(new Identifier(HerobrineMod.MODID, "survivor"), "main");
    public static final EntityModelLayer SURVIVOR_INNER_ARMOR = new EntityModelLayer(new Identifier(HerobrineMod.MODID, "survivor"), "inner_armor");
    public static final EntityModelLayer SURVIVOR_OUTER_ARMOR = new EntityModelLayer(new Identifier(HerobrineMod.MODID, "survivor"), "outer_armor");
}
