package com.herobrine.mod.util.blocks;

import net.minecraft.state.BooleanProperty;
import net.minecraft.state.properties.BlockStateProperties;

public class ModBlockStates extends BlockStateProperties {
    public static final BooleanProperty ACTIVE = BooleanProperty.create("active");
}