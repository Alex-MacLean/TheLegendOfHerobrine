package com.herobrinemod.herobrine.mixin;

import com.herobrinemod.herobrine.worldgen.BiomeKeyList;
import net.minecraft.registry.RegistryKey;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.source.util.VanillaBiomeParameters;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

// This class is very cursed
@Mixin(VanillaBiomeParameters.class)
public class VanillaBiomeParametersMixin {
    @Shadow
    @Final
    private RegistryKey<Biome>[][] uncommonBiomes;
    @Shadow
    @Final
    private RegistryKey<Biome>[][] specialNearMountainBiomes;

    @Inject(method = "<init>*", at = @At("TAIL"))
    private void injectCursedForest(CallbackInfo ci) {
        if(uncommonBiomes[2][1] == null) {
            uncommonBiomes[2][1] = BiomeKeyList.CURSED_FOREST;
        }
        if(specialNearMountainBiomes[2][4] == null) {
            specialNearMountainBiomes[2][4] = BiomeKeyList.CURSED_FOREST;
        }
    }
}