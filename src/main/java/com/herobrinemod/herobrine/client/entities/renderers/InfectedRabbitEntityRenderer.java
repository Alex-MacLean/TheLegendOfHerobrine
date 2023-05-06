package com.herobrinemod.herobrine.client.entities.renderers;

import com.herobrinemod.herobrine.HerobrineMod;
import com.herobrinemod.herobrine.client.entities.models.HerobrineModelLayers;
import com.herobrinemod.herobrine.client.entities.models.InfectedRabbitEntityModel;
import com.herobrinemod.herobrine.entities.InfectedRabbitEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.entity.passive.RabbitEntity;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;

@Environment(EnvType.CLIENT)
public class InfectedRabbitEntityRenderer extends MobEntityRenderer<InfectedRabbitEntity, InfectedRabbitEntityModel> {
    private static final Identifier BROWN_TEXTURE = new Identifier(HerobrineMod.MODID, "textures/entity/infected_rabbit/brown.png");
    private static final Identifier WHITE_TEXTURE = new Identifier(HerobrineMod.MODID, "textures/entity/infected_rabbit/white.png");
    private static final Identifier BLACK_TEXTURE = new Identifier(HerobrineMod.MODID, "textures/entity/infected_rabbit/black.png");
    private static final Identifier GOLD_TEXTURE = new Identifier(HerobrineMod.MODID, "textures/entity/infected_rabbit/gold.png");
    private static final Identifier SALT_TEXTURE = new Identifier(HerobrineMod.MODID, "textures/entity/infected_rabbit/salt.png");
    private static final Identifier WHITE_SPLOTCHED_TEXTURE = new Identifier(HerobrineMod.MODID, "textures/entity/infected_rabbit/white_splotched.png");
    private static final Identifier TOAST_TEXTURE = new Identifier(HerobrineMod.MODID, "textures/entity/infected_rabbit/toast.png");
    private static final Identifier CAERBANNOG_TEXTURE = new Identifier(HerobrineMod.MODID, "textures/entity/infected_rabbit/caerbannog.png");
    public InfectedRabbitEntityRenderer(EntityRendererFactory.Context context) {
        super(context, new InfectedRabbitEntityModel(context.getPart(HerobrineModelLayers.INFECTED_RABBIT_MODEL_LAYER)), 0.3f);
        this.addFeature(new InfectedRabbitEyesFeatureRenderer(this));
    }

    @Override
    public Identifier getTexture(@NotNull InfectedRabbitEntity entity) {
        String string = Formatting.strip(entity.getName().getString());
        if ("Toast".equals(string)) {
            return TOAST_TEXTURE;
        } else if(entity.getVariant().equals(RabbitEntity.RabbitType.EVIL)) {
            InfectedRabbitEyesFeatureRenderer.EYES_TEXTURE = new Identifier(HerobrineMod.MODID, "textures/entity/eyes/infected_rabbit/caerbannog.png");
        }

        return switch (entity.getVariant()) {
            case BROWN -> BROWN_TEXTURE;
            case WHITE -> WHITE_TEXTURE;
            case BLACK -> BLACK_TEXTURE;
            case GOLD -> GOLD_TEXTURE;
            case SALT -> SALT_TEXTURE;
            case WHITE_SPLOTCHED -> WHITE_SPLOTCHED_TEXTURE;
            case EVIL -> CAERBANNOG_TEXTURE;
        };
    }
}