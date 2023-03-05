package com.herobrinemod.herobrine.client.entities.models;

import com.herobrinemod.herobrine.entities.HerobrineEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.entity.model.AbstractZombieModel;
import org.jetbrains.annotations.NotNull;

@Environment(EnvType.CLIENT)
public class HerobrineMageEntityModel extends AbstractZombieModel<HerobrineEntity> {
    public HerobrineMageEntityModel(ModelPart modelPart) {
        super(modelPart);
    }

    @Override
    public boolean isAttacking(@NotNull HerobrineEntity entity) {
        return entity.isAttacking();
    }
}
