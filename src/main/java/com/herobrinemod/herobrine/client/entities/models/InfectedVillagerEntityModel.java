package com.herobrinemod.herobrine.client.entities.models;

import com.herobrinemod.herobrine.entities.InfectedVillagerEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.model.TexturedModelData;
import net.minecraft.client.render.entity.model.VillagerResemblingModel;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

@Environment(EnvType.CLIENT)
public class InfectedVillagerEntityModel extends VillagerResemblingModel<InfectedVillagerEntity> {
    public InfectedVillagerEntityModel(ModelPart root) {
        super(root);
    }

    @Contract(" -> new")
    public static @NotNull TexturedModelData getTexturedModelData() {
        return TexturedModelData.of(VillagerResemblingModel.getModelData(), 64, 64);
    }
}
