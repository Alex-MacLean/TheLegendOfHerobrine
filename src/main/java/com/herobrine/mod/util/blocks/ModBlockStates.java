package com.herobrine.mod.util.blocks;

import net.minecraft.state.IntegerProperty;
import net.minecraft.state.properties.BlockStateProperties;

public class ModBlockStates extends BlockStateProperties {
    public static final IntegerProperty TYPE = IntegerProperty.create("type", 0, 2);
}