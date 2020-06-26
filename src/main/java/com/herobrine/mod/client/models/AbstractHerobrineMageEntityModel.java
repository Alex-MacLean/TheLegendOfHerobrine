package com.herobrine.mod.client.models;

import com.herobrine.mod.entities.AbstractHerobrineEntity;
import net.minecraft.client.renderer.entity.model.AbstractZombieModel;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
public class AbstractHerobrineMageEntityModel<T extends AbstractHerobrineEntity> extends AbstractZombieModel<T> {
    public AbstractHerobrineMageEntityModel(float modelSize, boolean p_i1168_2_) {
        this(modelSize, 0.0F, 64, p_i1168_2_ ? 32 : 64);
    }

    protected AbstractHerobrineMageEntityModel(float p_i48914_1_, float p_i48914_2_, int p_i48914_3_, int p_i48914_4_) {
        super(p_i48914_1_, p_i48914_2_, p_i48914_3_, p_i48914_4_);
    }

    @Override
    public boolean func_212850_a_(@NotNull T p_212850_1_) {
        return p_212850_1_.isAggressive();
    }
}