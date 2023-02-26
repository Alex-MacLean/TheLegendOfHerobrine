package com.herobrinemod.herobrine.sounds;

import com.herobrinemod.herobrine.HerobrineMod;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;

public class SoundList {
    public static final Identifier MUSIC_DISC_DOG_IDENTIFIER = new Identifier(HerobrineMod.MODID, "music_disc_dog");
    public static final SoundEvent MUSIC_DISC_DOG = SoundEvent.of(MUSIC_DISC_DOG_IDENTIFIER);
}
