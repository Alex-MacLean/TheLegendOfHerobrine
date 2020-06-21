package com.herobrine.mod.util.savedata;

import com.herobrine.mod.HerobrineMod;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.server.ServerWorld;
import net.minecraft.world.storage.WorldSavedData;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.network.NetworkEvent;
import net.minecraftforge.fml.network.PacketDistributor;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.function.Supplier;

public class Variables {
    public static class SaveData extends WorldSavedData {
        public static final String DATA_NAME = "herobrine_worldvars";
        public boolean Spawn = false;
        public SaveData() {
            super(DATA_NAME);
        }

        @Override
        public void read(@NotNull CompoundNBT nbt) {
            Spawn = nbt.getBoolean("Spawn");
        }

        @Override
        public @NotNull CompoundNBT write(@NotNull CompoundNBT nbt) {
            nbt.putBoolean("Spawn", Spawn);
            return nbt;
        }

        public void syncData(@NotNull World world) {
            this.markDirty();
            if (world.isRemote) {
                HerobrineMod.PACKET_HANDLER.sendToServer(new WorldSavedDataSyncMessage(this));
            } else {
                HerobrineMod.PACKET_HANDLER.send(PacketDistributor.ALL.noArg(), new WorldSavedDataSyncMessage(this));
            }
        }
        static SaveData clientSide = new SaveData();
        public static SaveData get(World world) {
            if (world instanceof ServerWorld) {
                return Objects.requireNonNull(world.getServer()).getWorld(DimensionType.OVERWORLD).getSavedData().getOrCreate(SaveData::new, DATA_NAME);
            } else {
                return clientSide;
            }
        }
    }

    public static class WorldSavedDataSyncMessage {
        public WorldSavedData data;
        public WorldSavedDataSyncMessage(@NotNull PacketBuffer buffer) {
            this.data = new SaveData();
            this.data.read(Objects.requireNonNull(buffer.readCompoundTag()));
        }

        public WorldSavedDataSyncMessage(WorldSavedData data) {
            this.data = data;
        }

        public static void buffer(@NotNull WorldSavedDataSyncMessage message, @NotNull PacketBuffer buffer) {
            buffer.writeCompoundTag(message.data.write(new CompoundNBT()));
        }

        public static void handler(WorldSavedDataSyncMessage message, @NotNull Supplier<NetworkEvent.Context> contextSupplier) {
            NetworkEvent.Context context = contextSupplier.get();
            context.enqueueWork(() -> {
                if (context.getDirection().getReceptionSide().isServer())
                    syncData(message, context.getDirection().getReceptionSide(), Objects.requireNonNull(context.getSender()).world);
                else {
                    assert Minecraft.getInstance().player != null;
                    syncData(message, context.getDirection().getReceptionSide(), Minecraft.getInstance().player.world);
                }
            });
            context.setPacketHandled(true);
        }

        private static void syncData(WorldSavedDataSyncMessage message, @NotNull LogicalSide side, World world) {
            if (side.isServer()) {
                message.data.markDirty();
                HerobrineMod.PACKET_HANDLER.send(PacketDistributor.ALL.noArg(), message);
                Objects.requireNonNull(world.getServer()).getWorld(DimensionType.OVERWORLD).getSavedData().set(message.data);

            } else {
                SaveData.clientSide = (SaveData) message.data;
            }
        }
    }
}
