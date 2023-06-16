package com.herobrinemod.herobrine.client.entities.models;

import com.herobrinemod.herobrine.entities.HerobrineEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.entity.model.CrossbowPosing;

@Environment(EnvType.CLIENT)
public class HerobrineMageEntityModel extends HerobrineEntityModel {
    public HerobrineMageEntityModel(ModelPart modelPart) {
        super(modelPart);
    }

    @Override
    public void setAngles(HerobrineEntity entity, float f, float g, float h, float i, float j) {
        super.setAngles(entity, f, g, h, i, j);
        CrossbowPosing.meleeAttack(this.leftArm, this.rightArm, false, this.handSwingProgress, h);
        CrossbowPosing.meleeAttack(this.leftSleeve, this.rightSleeve, false, this.handSwingProgress, h);
    }
}
