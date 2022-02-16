package com.github.alexmaclean.herobrine.client.entities.models;

import com.github.alexmaclean.herobrine.entities.HerobrineEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.Dilation;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.model.TexturedModelData;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

@Environment(EnvType.CLIENT)
public class HerobrineEntityModel extends PlayerEntityModel<HerobrineEntity> {
    public HerobrineEntityModel(ModelPart root, boolean thinArms) {
        super(root, thinArms);
    }


    @Contract(" -> new")
    public static @NotNull TexturedModelData getTexturedModelData() {
        return TexturedModelData.of(PlayerEntityModel.getTexturedModelData(Dilation.NONE, false), 64, 64);
    }
}
