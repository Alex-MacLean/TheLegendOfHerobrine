package com.herobrine.mod.util.entities;

import com.herobrine.mod.util.misc.ElementsHerobrine;
import com.herobrine.mod.util.misc.Variables;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;

@ElementsHerobrine.ModElement.Tag
public class SummoningEntitySpawnSettings extends ElementsHerobrine.ModElement {
    public SummoningEntitySpawnSettings(ElementsHerobrine instance) {
        super(instance, 9);
    }

    public static void executeProcedure(@NotNull java.util.HashMap<String, Object> dependencies) {
        World world = (World) dependencies.get("world");
        Entity entity = (Entity) dependencies.get("entity");
        if ((!(Variables.WorldVariables.get(world).Spawn))) {
            entity.remove();
        }
    }
}
