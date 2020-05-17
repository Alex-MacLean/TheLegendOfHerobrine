package com.herobrine.mod.util.savedata;

import com.herobrine.mod.HerobrineMod;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraft.world.storage.WorldSavedData;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class Variables {
    public static class WorldVariables extends WorldSavedData {
        public static final String DATA_NAME = "herobrine_worldvars";
        public boolean Spawn = false;
        public WorldVariables() {
            super(DATA_NAME);
        }

        public WorldVariables(String s) {
            super(s);
        }

        @Override
        public void readFromNBT(@NotNull NBTTagCompound nbt) {
            Spawn = nbt.getBoolean("Spawn");
        }

        @Override
        public @NotNull NBTTagCompound writeToNBT(@NotNull NBTTagCompound nbt) {
            nbt.setBoolean("Spawn", Spawn);
            return nbt;
        }

        public void syncData(@NotNull World world) {
            this.markDirty();
            if (world.isRemote) {
                HerobrineMod.PACKET_HANDLER.sendToServer(new WorldSavedDataSyncMessage(1, this));
            } else {
                HerobrineMod.PACKET_HANDLER.sendToDimension(new WorldSavedDataSyncMessage(1, this), world.provider.getDimension());
            }
        }

        public static @NotNull WorldVariables get(@NotNull World world) {
            WorldVariables instance = (WorldVariables) world.getPerWorldStorage().getOrLoadData(WorldVariables.class, DATA_NAME);
            if (instance == null) {
                instance = new WorldVariables();
                world.getPerWorldStorage().setData(DATA_NAME, instance);
            }
            return instance;
        }
    }

    public static class WorldSavedDataSyncMessageHandler implements IMessageHandler<WorldSavedDataSyncMessage, IMessage> {
        @Override
        public IMessage onMessage(WorldSavedDataSyncMessage message, @NotNull MessageContext context) {
            if (context.side == Side.SERVER)
                context.getServerHandler().player.getServerWorld()
                        .addScheduledTask(() -> syncData(message, context, context.getServerHandler().player.world));
            else
                Minecraft.getMinecraft().addScheduledTask(() -> syncData(message, context, Minecraft.getMinecraft().player.world));
            return null;
        }

        private void syncData(WorldSavedDataSyncMessage message, @NotNull MessageContext context, World world) {
            if (context.side == Side.SERVER) {
                message.data.markDirty();
                HerobrineMod.PACKET_HANDLER.sendToDimension(message, world.provider.getDimension());
            }
            world.getPerWorldStorage().setData(WorldVariables.DATA_NAME, message.data);
        }
    }

    public static class WorldSavedDataSyncMessage implements IMessage {
        public int type;
        public WorldSavedData data;
        public WorldSavedDataSyncMessage() {
        }

        public WorldSavedDataSyncMessage(int type, WorldSavedData data) {
            this.type = type;
            this.data = data;
        }

        @Override
        public void toBytes(io.netty.buffer.@NotNull ByteBuf buf) {
            buf.writeInt(this.type);
            ByteBufUtils.writeTag(buf, this.data.writeToNBT(new NBTTagCompound()));
        }

        @Override
        public void fromBytes(io.netty.buffer.@NotNull ByteBuf buf) {
            this.type = buf.readInt();
            this.data = new WorldVariables();
            this.data.readFromNBT(Objects.requireNonNull(ByteBufUtils.readTag(buf)));
        }
    }
}