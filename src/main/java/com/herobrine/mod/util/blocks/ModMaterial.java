package com.herobrine.mod.util.blocks;

import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import org.jetbrains.annotations.NotNull;

public class ModMaterial extends Material {
    private boolean requiresNoTool = true;

    public ModMaterial(MapColor color) {
        super(color);
    }

    @Override
    public @NotNull ModMaterial setRequiresTool()
    {
        this.requiresNoTool = false;
        return this;
    }
}
