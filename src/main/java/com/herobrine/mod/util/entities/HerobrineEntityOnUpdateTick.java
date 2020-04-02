package com.herobrine.mod.util.entities;

import com.herobrine.mod.util.misc.ElementsHerobrine;
import net.minecraft.entity.LivingEntity;
import org.jetbrains.annotations.NotNull;

@ElementsHerobrine.ModElement.Tag
public class HerobrineEntityOnUpdateTick extends ElementsHerobrine.ModElement {
    public HerobrineEntityOnUpdateTick(ElementsHerobrine instance) {
        super(instance, 6);
    }

    public static void executeProcedure(@NotNull java.util.HashMap<String, Object> dependencies) {
        LivingEntity livingEntity = (LivingEntity) dependencies.get("entity");
        livingEntity.clearActivePotions();
    }
}