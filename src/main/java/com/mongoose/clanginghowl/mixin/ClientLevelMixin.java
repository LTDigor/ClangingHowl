package com.mongoose.clanginghowl.mixin;

import com.mongoose.clanginghowl.ClangingHowl;
import com.mongoose.clanginghowl.client.audio.DirectionalSoundInstance;
import com.mongoose.clanginghowl.client.audio.MusicLoopMusic;
import com.mongoose.clanginghowl.client.world.CHClientWorld;
import com.mongoose.clanginghowl.client.world.ICHClientWorld;
import com.mongoose.clanginghowl.client.world.MeteorFlashState;
import com.mongoose.clanginghowl.config.CHConfig;
import com.mongoose.clanginghowl.init.CHSounds;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.core.Holder;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.storage.WritableLevelData;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.BooleanSupplier;
import java.util.function.Supplier;

//Stolen from @yungnickyoung codes:https://github.com/YUNG-GANG/YUNGs-Cave-Biomes/blob/1.20.1/Common/src/main/java/com/yungnickyoung/minecraft/yungscavebiomes/mixin/lost_caves/client/ClientLevelMixin.java
@Mixin(ClientLevel.class)
public abstract class ClientLevelMixin extends Level implements ICHClientWorld {
    @Unique
    private final CHClientWorld clangingHowl$chClientWorld = new CHClientWorld();

    @Shadow
    @Final
    private Minecraft minecraft;

    @Unique
    private MeteorFlashState clanginghowl$meteorFlashState;

    @Override
    @Unique
    public CHClientWorld getCHClientWorld() {
        return clangingHowl$chClientWorld;
    }

    protected ClientLevelMixin(WritableLevelData p_270739_, ResourceKey<Level> p_270683_, RegistryAccess p_270200_, Holder<DimensionType> p_270240_, Supplier<ProfilerFiller> p_270692_, boolean p_270904_, boolean p_270470_, long p_270248_, int p_270466_) {
        super(p_270739_, p_270683_, p_270200_, p_270240_, p_270692_, p_270904_, p_270470_, p_270248_, p_270466_);
    }

    @Inject(method = "tick", at = @At("HEAD"))
    private void clanginghowl$onPreTick(BooleanSupplier hasTimeLeft, CallbackInfo ci) {
        if (this.dimension() == Level.OVERWORLD && this.getCHClientWorld().isMeteorFlash() && CHConfig.MeteorShowerFlash.get()) {
            if (this.clanginghowl$meteorFlashState == null) {
                this.clanginghowl$meteorFlashState = new MeteorFlashState(this.getRandom());
            }
        } else if (this.clanginghowl$meteorFlashState != null && (this.clanginghowl$meteorFlashState.getIntensity() <= 0.0001F || this.dimension() != Level.OVERWORLD)) {
            this.clanginghowl$meteorFlashState = null;
        }
    }

    @Unique
    private static SoundInstance clangingHowl$INSTANCE = null;

    @Inject(method = "tick", at = @At("TAIL"))
    private void clanginghowl$onTick(BooleanSupplier hasTimeLeft, CallbackInfo ci) {
        if (this.clanginghowl$meteorFlashState != null) {
            this.clanginghowl$meteorFlashState.tick(this.getCHClientWorld().getMeteorFlashTick());

            // Play directional sound when flash starts
            if (this.getCHClientWorld().playMeteorExplode()) {
                clangingHowl$INSTANCE = new DirectionalSoundInstance(
                        CHSounds.METEOR_SHOWER_EXPLODE.get(),
                        SoundSource.WEATHER,
                        this.random,
                        this.minecraft.gameRenderer.getMainCamera(),
                        this.clanginghowl$meteorFlashState.getXAngle(),
                        this.clanginghowl$meteorFlashState.getYAngle()
                );
            } else {
                clangingHowl$INSTANCE = null;
            }
            if (clangingHowl$INSTANCE != null) {
                if (!this.minecraft.getSoundManager().isActive(clangingHowl$INSTANCE)) {
                    this.minecraft.getSoundManager().play(clangingHowl$INSTANCE);
                }
            }
        }
        Player player = ClangingHowl.PROXY.getPlayer();
        if (player != null) {
            if (this.getCHClientWorld().playMeteorMusic() && this.dimension() == OVERWORLD) {
                if (CHClientWorld.MUSIC == null) {
                    CHClientWorld.MUSIC = new MusicLoopMusic(CHSounds.METEOR_SHOWER_MUSIC.get(), player, 1.0F, 1.0F);
                }
                if (!this.minecraft.getSoundManager().isActive(CHClientWorld.MUSIC)) {
                    Minecraft.getInstance().getSoundManager().play(CHClientWorld.MUSIC);
                }
            } else {
                if (CHClientWorld.MUSIC != null) {
                    CHClientWorld.MUSIC = null;
                }
            }
        }
    }

    @Override
    @Unique
    public MeteorFlashState clanginghowl$getMeteorFlashState() {
        return this.clanginghowl$meteorFlashState;
    }
}
