package com.herobrinemod.herobrine.mixin;

import com.herobrinemod.herobrine.savedata.ConfigHandler;
import com.herobrinemod.herobrine.worldgen.BiomeKeyList;
import com.mojang.datafixers.util.Pair;
import net.minecraft.registry.RegistryKey;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.source.util.MultiNoiseUtil;
import net.minecraft.world.biome.source.util.VanillaBiomeParameters;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.Consumer;

@Mixin(VanillaBiomeParameters.class)
public abstract class VanillaBiomeParametersMixin {
    @Shadow
    protected abstract void writeBiomeParameters(Consumer<Pair<MultiNoiseUtil.NoiseHypercube, RegistryKey<Biome>>> parameters, MultiNoiseUtil.ParameterRange temperature, MultiNoiseUtil.ParameterRange humidity, MultiNoiseUtil.ParameterRange continentalness, MultiNoiseUtil.ParameterRange erosion, MultiNoiseUtil.ParameterRange weirdness, float offset, RegistryKey<Biome> biome);

    @Inject(method = "writeLandBiomes", at = @At("TAIL"))
    private void writeCursedForest(Consumer<Pair<MultiNoiseUtil.NoiseHypercube, RegistryKey<Biome>>> parameters, CallbackInfo ci) {
        writeBiomeParameters(parameters, MultiNoiseUtil.ParameterRange.of(-0.45f, 0.55f), MultiNoiseUtil.ParameterRange.of(-0.1f, 0.1f), MultiNoiseUtil.ParameterRange.of(-0.19f, 1.0f), MultiNoiseUtil.ParameterRange.of(-0.7799f, 0.55f), MultiNoiseUtil.ParameterRange.of(ConfigHandler.getHerobrineConfig().readFloat("CursedForestWeirdnessMin"), ConfigHandler.getHerobrineConfig().readFloat("CursedForestWeirdnessMax")), 0.0f, BiomeKeyList.CURSED_FOREST);
    }
}