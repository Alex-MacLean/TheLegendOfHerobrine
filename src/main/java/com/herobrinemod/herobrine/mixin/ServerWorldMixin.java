package com.herobrinemod.herobrine.mixin;

import com.herobrinemod.herobrine.entities.HerobrineSpawnHelper;
import com.herobrinemod.herobrine.savedata.ConfigHandler;
import com.herobrinemod.herobrine.savedata.SaveDataHandler;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.world.ServerWorld;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.BooleanSupplier;

@Mixin(ServerWorld.class)
public abstract class ServerWorldMixin {
    @Shadow @NotNull public abstract MinecraftServer getServer();

    @Inject(method = "tick", at = @At(value = "TAIL"))
    private void progressHerobrineStage(BooleanSupplier shouldKeepTicking, CallbackInfo ci) {
        int stage = SaveDataHandler.getHerobrineSaveData().readInt("stage");
        int stageTime = SaveDataHandler.getHerobrineSaveData().readInt("stageTime");
        if(!ConfigHandler.getHerobrineConfig().readBoolean("HerobrineAlwaysSpawns") && HerobrineSpawnHelper.canHerobrineSpawn() && stage < 3) {
            stageTime ++;
            SaveDataHandler.getHerobrineSaveData().writeInt("stageTime", stageTime);
            if(stageTime > 23999) {
                stage ++;
                SaveDataHandler.getHerobrineSaveData().writeInt("stageTime", 0);
                SaveDataHandler.getHerobrineSaveData().writeInt("stage", stage);
            }
        }
    }
}