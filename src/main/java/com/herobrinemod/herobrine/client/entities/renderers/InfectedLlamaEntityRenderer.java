package com.herobrinemod.herobrine.client.entities.renderers;

import com.herobrinemod.herobrine.HerobrineMod;
import com.herobrinemod.herobrine.client.entities.models.HerobrineModelLayers;
import com.herobrinemod.herobrine.client.entities.models.InfectedLlamaEntityModel;
import com.herobrinemod.herobrine.entities.InfectedLlamaEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;

@Environment(EnvType.CLIENT)
public class InfectedLlamaEntityRenderer extends MobEntityRenderer<InfectedLlamaEntity, InfectedLlamaEntityModel> {
    private static final Identifier CREAMY_TEXTURE = new Identifier(HerobrineMod.MODID, "textures/entity/infected_llama/creamy.png");
    private static final Identifier WHITE_TEXTURE = new Identifier(HerobrineMod.MODID, "textures/entity/infected_llama/white.png");
    private static final Identifier BROWN_TEXTURE = new Identifier(HerobrineMod.MODID, "textures/entity/infected_llama/brown.png");
    private static final Identifier GRAY_TEXTURE = new Identifier(HerobrineMod.MODID, "textures/entity/infected_llama/gray.png");

    public InfectedLlamaEntityRenderer(EntityRendererFactory.Context context) {
        super(context, new InfectedLlamaEntityModel(context.getPart(HerobrineModelLayers.INFECTED_LLAMA_MODEL_LAYER)), 0.7f);
        this.addFeature(new InfectedLlamaEyesFeatureRenderer(this));
    }

    @Override
    public Identifier getTexture(@NotNull InfectedLlamaEntity entity) {
        return switch (entity.getVariant()) {
            case CREAMY -> CREAMY_TEXTURE;
            case WHITE -> WHITE_TEXTURE;
            case BROWN -> BROWN_TEXTURE;
            case GRAY -> GRAY_TEXTURE;
        };
    }
}