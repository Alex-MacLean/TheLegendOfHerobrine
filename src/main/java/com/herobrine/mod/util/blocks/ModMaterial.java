package com.herobrine.mod.util.blocks;

import net.minecraft.block.material.EnumPushReaction;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import org.jetbrains.annotations.NotNull;

public class ModMaterial extends Material {
    public static final ModMaterial HEROBRINE_ALTER_MATERIAL = new ModMaterial(MapColor.RED).setRequiresTool().setTranslucent();
    public static final ModMaterial CURSED_DIAMOND_BLOCK_MATERIAL = new ModMaterial(MapColor.PURPLE).setRequiresTool();
    public static final ModMaterial PURIFIED_DIAMOND_BLOCK_MATERIAL = new ModMaterial(MapColor.LIGHT_BLUE).setRequiresTool();
    public static final ModMaterial HEROBRINE_STATUE_MATERIAL = new ModMaterial(MapColor.STONE).setRequiresTool().setTranslucent().setNoPushMobility();

    public boolean canBurn;
    public boolean replaceable;
    public boolean isTranslucent;
    public final MapColor materialMapColor;
    public boolean requiresNoTool = true;
    public EnumPushReaction pushReaction = EnumPushReaction.NORMAL;

    public ModMaterial(MapColor color) {
        super(color);
        this.materialMapColor = color;
    }

    @Override
    public boolean isLiquid() {
        return false;
    }

    @Override
    public boolean isSolid() {
        return true;
    }

    @Override
    public boolean blocksLight() {
        return true;
    }

    @Override
    public boolean blocksMovement() {
        return true;
    }

    @Override
    public @NotNull ModMaterial setRequiresTool() {
        this.requiresNoTool = false;
        return this;
    }

    @Override
    public @NotNull ModMaterial setBurning() {
        this.canBurn = true;
        return this;
    }

    @Override
    public boolean getCanBurn() {
        return this.canBurn;
    }

    @Override
    public @NotNull ModMaterial setReplaceable() {
        this.replaceable = true;
        return this;
    }

    private ModMaterial setTranslucent() {
        this.isTranslucent = true;
        return this;
    }

    @Override
    public boolean isReplaceable() {
        return this.replaceable;
    }

    @Override
    public boolean isOpaque() {
        return !this.isTranslucent && this.blocksMovement();
    }

    @Override
    public boolean isToolNotRequired() {
        return this.requiresNoTool;
    }

    @Override
    public @NotNull EnumPushReaction getPushReaction() {
        return this.pushReaction;
    }

    @Override
    public @NotNull ModMaterial setNoPushMobility() {
        this.pushReaction = EnumPushReaction.DESTROY;
        return this;
    }

    @Override
    public @NotNull ModMaterial setImmovableMobility() {
        this.pushReaction = EnumPushReaction.BLOCK;
        return this;
    }

    @Override
    public @NotNull ModMaterial setAdventureModeExempt() {
        return this;
    }

    @Override
    public @NotNull MapColor getMaterialMapColor() {
        return this.materialMapColor;
    }
}
