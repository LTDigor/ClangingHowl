package com.mongoose.clanginghowl.client.audio;

import com.mongoose.clanginghowl.client.world.CHClientWorld;
import com.mongoose.clanginghowl.client.world.ICHClientWorld;
import com.mongoose.clanginghowl.utils.ControlledAnimation;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.resources.sounds.AbstractTickableSoundInstance;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;

/**
 * Music fade ripped from L_Ender's codes: <a href="https://github.com/lender544/new1.20.1/blob/master/src/main/java/com/github/L_Ender/cataclysm/client/sound/BossMusicSound.java">...</a>
 */
public class MusicLoopMusic extends AbstractTickableSoundInstance {
    protected final LivingEntity entity;
    private final float trueVolume;
    private int ticksExisted = 0;
    private float reduction = 0.0F;
    ControlledAnimation volumeControl;

    public MusicLoopMusic(SoundEvent soundEvent, LivingEntity entity) {
        this(soundEvent, entity, 1.0F, 1.0F);
    }

    public MusicLoopMusic(SoundEvent soundEvent, LivingEntity entity, float volume, float pitch) {
        super(soundEvent, SoundSource.MUSIC, SoundInstance.createUnseededRandom());
        this.entity = entity;
        this.x = entity.getX();
        this.y = entity.getY();
        this.z = entity.getZ();
        this.looping = true;
        this.delay = 0;
        this.volumeControl = new ControlledAnimation(40);
        this.volumeControl.setTimer(20);
        this.volume = 0.0001F;
        this.trueVolume = volume;
        this.pitch = pitch;
    }

    public void tick() {
        if (this.entity.level().dimension() != Level.OVERWORLD){
            CHClientWorld.MUSIC = null;
            this.stop();
        }
        this.x = this.entity.getX();
        this.y = this.entity.getY();
        this.z = this.entity.getZ();

        if (CHClientWorld.MUSIC == null) {
            this.volumeControl.decreaseTimer();
        } else {
            this.volumeControl.increaseTimer();
        }

        this.volume = this.volumeControl.getAnimationFraction() / this.trueVolume;

        if (this.entity.level() instanceof ClientLevel clientLevel) {
            CHClientWorld chClientWorld = ((ICHClientWorld) clientLevel).getCHClientWorld();
            if ((chClientWorld.isMeteorMusicFullVolume() && this.reduction < 1.0F)) {
                this.reduction += 0.01F;
            } else if (this.reduction < 0.25F) {
                this.reduction += 0.0005F;
            }

            this.volume *= this.reduction;
        }

        if (this.volumeControl.getAnimationFraction() < 0.025) {
            CHClientWorld.MUSIC = null;
            this.stop();
        }

        if (this.ticksExisted % 100 == 0) {
            Minecraft.getInstance().getMusicManager().stopPlaying();
        }
        this.ticksExisted++;
    }
}
