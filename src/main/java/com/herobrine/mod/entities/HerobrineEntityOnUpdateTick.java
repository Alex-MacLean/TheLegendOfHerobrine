package com.herobrine.mod.entities;

import com.herobrine.mod.ElementsHerobrine;
import com.herobrine.mod.Variables;
import net.minecraft.entity.Entity;
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
        if ((!(Variables.WorldVariables.get(world).Spawn))) {
            entity.remove();
        }
    }
}