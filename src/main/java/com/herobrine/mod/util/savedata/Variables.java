package com.herobrine.mod.util.savedata;

import com.herobrine.mod.HerobrineMod;
import net.minecraftforge.fml.network.PacketDistributor;
import net.minecraftforge.fml.network.NetworkEvent;
import net.minecraftforge.fml.LogicalSide;

import net.minecraft.world.storage.WorldSavedData;
import net.minecraft.world.server.ServerWorld;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.World;
import net.minecraft.network.PacketBuffer;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.client.Minecraft;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class Variables {
    public static class WorldVariables extends WorldSavedData {
        public static final String DATA_NAME = "herobrine_worldvars";
        public boolean Spawn = false;

        public WorldVariables() {
            super(DATA_NAME);
            this.addNetworkMessage(Variables.WorldSavedDataSyncMessage.class, Variables.WorldSavedDataSyncMessage::buffer,
                    Variables.WorldSavedDataSyncMessage::new, Variables.WorldSavedDataSyncMessage::handler);
        }

        private int messageID = 0;

        public <T> void addNetworkMessage(Class<T> messageType, BiConsumer<T, PacketBuffer> encoder, Function<PacketBuffer, T> decoder,
                                          BiConsumer<T, Supplier<NetworkEvent.Context>> messageConsumer) {
            HerobrineMod.PACKET_HANDLER.registerMessage(messageID, messageType, encoder, decoder, messageConsumer);
            messageID++;
        }

        @Override
        public void read(@NotNull CompoundNBT nbt) {
            Spawn = nbt.getBoolean("Spawn");
        }

        @NotNull
        @Override
        public CompoundNBT write(@NotNull CompoundNBT nbt) {
            nbt.putBoolean("Spawn", Spawn);
            return nbt;
        }

        public void syncData(@NotNull World world) {
            this.markDirty();
            if (world.isRemote) {
                HerobrineMod.PACKET_HANDLER.sendToServer(new WorldSavedDataSyncMessage(1, this));
            } else {
                HerobrineMod.PACKET_HANDLER.send(PacketDistributor.DIMENSION.with(world.dimension::getType), new WorldSavedDataSyncMessage(1, this));
            }
        }
        static WorldVariables clientSide = new WorldVariables();

        public static WorldVariables get(World world) {
            if (world instanceof ServerWorld) {
                return ((ServerWorld) world).getSavedData().getOrCreate(WorldVariables::new, DATA_NAME);
            } else {
                return clientSide;
            }
        }
    }

    public static class WorldSavedDataSyncMessage {
        public int type;
        public WorldSavedData data;

        public WorldSavedDataSyncMessage(@NotNull PacketBuffer buffer) {
            this.type = buffer.readInt();
            this.data = new WorldVariables();
            this.data.read(Objects.requireNonNull(buffer.readCompoundTag()));
        }

        public WorldSavedDataSyncMessage(int type, WorldSavedData data) {
            this.type = type;
            this.data = data;
        }

        public static void buffer(@NotNull WorldSavedDataSyncMessage message, @NotNull PacketBuffer buffer) {
            buffer.writeInt(message.type);
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
                if (message.type == 0) {
                    HerobrineMod.PACKET_HANDLER.send(PacketDistributor.ALL.noArg(), message);
                } else {
                    HerobrineMod.PACKET_HANDLER.send(PacketDistributor.DIMENSION.with(world.dimension::getType), message);
                    ((ServerWorld) world).getSavedData().set(message.data);
                }
            } else {
                WorldVariables.clientSide = (WorldVariables) message.data;
            }
        }
    }
}