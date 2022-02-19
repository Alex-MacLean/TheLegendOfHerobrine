package com.github.alexmaclean.herobrine.client.entities.models;

import com.github.alexmaclean.herobrine.HerobrineMod;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class HerobrineModelLayers {
    public static final EntityModelLayer HEROBRINE_MODEL_LAYER = new EntityModelLayer(new Identifier(HerobrineMod.MODID, "herobrine"), "main");
}
