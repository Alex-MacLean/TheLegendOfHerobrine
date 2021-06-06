package com.herobrine.mod.savedata;

import com.herobrine.mod.HerobrineMod;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.world.server.ServerWorld;
import net.minecraft.world.storage.DimensionSavedDataManager;
import net.minecraft.world.storage.WorldSavedData;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

//Suppresses rawtypes warning for Supplier
@SuppressWarnings("rawtypes")
public class HerobrineSaveData extends WorldSavedData implements Supplier {
    public CompoundNBT data = new CompoundNBT();
    private static final String dataFileName = HerobrineMod.MODID;

    public HerobrineSaveData() {
        super(dataFileName);
    }

    //Suppresses unchecked assignment warning for HerobrineSaveData
    @SuppressWarnings("unchecked")
    public static HerobrineSaveData forWorld(ServerWorld world) {
        DimensionSavedDataManager storage = world.getServer().overworld().getDataStorage();
        HerobrineSaveData data = new HerobrineSaveData();
        return (HerobrineSaveData) storage.get(data, dataFileName);
    }

    @Override
    public void load(CompoundNBT nbt) {
        data = nbt.getCompound("Spawn");
    }

    @Override
    public @NotNull CompoundNBT save(CompoundNBT nbt) {
        nbt.put("Spawn", data);
        return nbt;
    }

    @Override
    public Object get() {
        return this;
    }
}