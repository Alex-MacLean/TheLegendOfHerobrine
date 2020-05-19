package com.herobrine.mod.util.blocks;

import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import org.jetbrains.annotations.NotNull;

public class ModMaterial extends Material {

    public ModMaterial(MapColor color) {
        super(color);
    }

    @Override
    public @NotNull ModMaterial setRequiresTool()
    {
        return this;
    }
}
