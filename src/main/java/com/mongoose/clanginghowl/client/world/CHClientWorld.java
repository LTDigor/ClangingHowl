package com.mongoose.clanginghowl.client.world;

import net.minecraft.client.resources.sounds.SoundInstance;

public class CHClientWorld {
    public static SoundInstance MUSIC = null;
    public int meteorFlashTick;
    public boolean isMeteorShower;
    public boolean isMeteorFlash;
    public boolean playMeteorExplode;
    public boolean playMeteorMusic;
    public boolean isMeteorMusicFullVolume;

    public boolean isMeteorShower() {
        return isMeteorShower;
    }

    public void setMeteorShower(boolean meteorShower) {
        this.isMeteorShower = meteorShower;
    }

    public int getMeteorFlashTick() {
        return this.meteorFlashTick;
    }

    public void setMeteorFlashTick(int meteorFlashTick) {
        this.meteorFlashTick = meteorFlashTick;
    }

    public boolean playMeteorExplode() {
        return this.playMeteorExplode;
    }

    public void setPlayMeteorExplode(boolean meteorExplode) {
        this.playMeteorExplode = meteorExplode;
    }

    public boolean playMeteorMusic() {
        return this.playMeteorMusic;
    }

    public void setPlayMeteorMusic(boolean meteorMusic) {
        this.playMeteorMusic = meteorMusic;
    }

    public boolean isMeteorMusicFullVolume() {
        return this.isMeteorMusicFullVolume;
    }

    public void setMeteorMusicFullVolume(boolean meteorMusic) {
        this.isMeteorMusicFullVolume = meteorMusic;
    }

    public boolean isMeteorFlash() {
        return isMeteorFlash;
    }

    public void setMeteorFlash(boolean meteorFlash) {
        this.isMeteorFlash = meteorFlash;
    }
}
