package com.herobrinemod.herobrine.client.entities.models;

import com.herobrinemod.herobrine.entities.HerobrineEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.Dilation;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.model.TexturedModelData;
import net.minecraft.client.render.entity.model.AbstractZombieModel;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

@Environment(EnvType.CLIENT)
public class HerobrineMageEntityModel extends AbstractZombieModel<HerobrineEntity> {
    public HerobrineMageEntityModel(ModelPart modelPart) {
        super(modelPart);
    }

    @Override
    public boolean isAttacking(@NotNull HerobrineEntity entity) {
        return false;
    }

    @Contract(" -> new")
    public static @NotNull TexturedModelData getTexturedModelData() {
        return TexturedModelData.of(BipedEntityModel.getModelData(Dilation.NONE, 0.0f), 64, 64);
    }
}
