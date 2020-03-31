package com.herobrine.mod.util.entities;

import com.herobrine.mod.util.misc.ElementsHerobrine;
import com.herobrine.mod.util.misc.Variables;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;

@ElementsHerobrine.ModElement.Tag
public class HerobrineEntityOnUpdateTick extends ElementsHerobrine.ModElement {
    public HerobrineEntityOnUpdateTick(ElementsHerobrine instance) {
        super(instance, 6);
    }

    public static void executeProcedure(@NotNull java.util.HashMap<String, Object> dependencies) {
        World world = (World) dependencies.get("world");
        Entity entity = (Entity) dependencies.get("entity");
        LivingEntity livingEntity = (LivingEntity) dependencies.get("entity");
        livingEntity.clearActivePotions();
        if ((!(Variables.WorldVariables.get(world).Spawn))) {
            entity.remove();
        }
    }
}