package com.herobrine.mod;

import net.minecraft.network.PacketBuffer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.network.NetworkEvent;
import net.minecraftforge.forgespi.language.ModFileScanData;
import org.jetbrains.annotations.NotNull;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;



public class ElementsHerobrine {
    protected final List<ModElement> elements = new ArrayList<>();

    public ElementsHerobrine() {
        try {
            ModFileScanData modFileInfo = ModList.get().getModFileById(HerobrineMod.MODID).getFile().getScanResult();
            Set<ModFileScanData.AnnotationData> annotations = modFileInfo.getAnnotations();
            for (ModFileScanData.AnnotationData annotationData : annotations) {
                if (annotationData.getAnnotationType().getClassName().equals(ModElement.Tag.class.getName())) {
                    Class<?> clazz = Class.forName(annotationData.getClassType().getClassName());
                    if (clazz.getSuperclass() == ElementsHerobrine.ModElement.class)
                        elements.add((ElementsHerobrine.ModElement) clazz.getConstructor(this.getClass()).newInstance(this));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        Collections.sort(elements);
        elements.forEach(ElementsHerobrine.ModElement::initElements);
        this.addNetworkMessage(Variables.WorldSavedDataSyncMessage.class, Variables.WorldSavedDataSyncMessage::buffer,
                Variables.WorldSavedDataSyncMessage::new, Variables.WorldSavedDataSyncMessage::handler);
        MinecraftForge.EVENT_BUS.register(this);
    }

    private int messageID = 0;

    public <T> void addNetworkMessage(Class<T> messageType, BiConsumer<T, PacketBuffer> encoder, Function<PacketBuffer, T> decoder,
                                      BiConsumer<T, Supplier<NetworkEvent.Context>> messageConsumer) {
        HerobrineMod.PACKET_HANDLER.registerMessage(messageID, messageType, encoder, decoder, messageConsumer);
        messageID++;
    }

    public static class ModElement implements Comparable<ModElement> {
        @Retention(RetentionPolicy.RUNTIME)
        public @interface Tag {
        }
        protected final ElementsHerobrine elements;
        protected final int sortid;

        public ModElement(ElementsHerobrine elements, int sortid) {
            this.elements = elements;
            this.sortid = sortid;
        }

        public void initElements() {
        }

        @Override
        public int compareTo(@NotNull ModElement other) {
            return this.sortid - other.sortid;
        }
    }
}