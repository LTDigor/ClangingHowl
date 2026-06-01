package com.mongoose.clanginghowl.common.network.server;

import com.mongoose.clanginghowl.client.world.CHClientWorld;
import com.mongoose.clanginghowl.client.world.ICHClientWorld;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class SSendCHWorldData {
    public boolean isMeteorShower;
    public boolean isMeteorFlash;
    public boolean playMeteorExplode;
    public boolean playMeteorMusic;
    public boolean isMeteorMusicFullVolume;
    public int meteorFlashTick;

    public SSendCHWorldData(boolean isMeteorShower, boolean isMeteorFlash, boolean playMeteorExplode, boolean playMeteorMusic, boolean isMeteorMusicFullVolume, int meteorFlashTick){
        this.isMeteorShower = isMeteorShower;
        this.isMeteorFlash = isMeteorFlash;
        this.playMeteorExplode = playMeteorExplode;
        this.playMeteorMusic = playMeteorMusic;
        this.isMeteorMusicFullVolume = isMeteorMusicFullVolume;
        this.meteorFlashTick = meteorFlashTick;
    }

    public static void encode(SSendCHWorldData packet, FriendlyByteBuf buffer) {
        buffer.writeBoolean(packet.isMeteorShower);
        buffer.writeBoolean(packet.isMeteorFlash);
        buffer.writeBoolean(packet.playMeteorExplode);
        buffer.writeBoolean(packet.playMeteorMusic);
        buffer.writeBoolean(packet.isMeteorMusicFullVolume);
        buffer.writeInt(packet.meteorFlashTick);
    }

    public static SSendCHWorldData decode(FriendlyByteBuf buffer) {
        return new SSendCHWorldData(
                buffer.readBoolean(),
                buffer.readBoolean(),
                buffer.readBoolean(),
                buffer.readBoolean(),
                buffer.readBoolean(),
                buffer.readInt());
    }

    public static void consume(SSendCHWorldData packet, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() ->
                DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> handleSync(packet))
        );
        ctx.get().setPacketHandled(true);
    }

    public static void handleSync(SSendCHWorldData packet) {
        ClientLevel clientLevel = Minecraft.getInstance().level;
        if (clientLevel != null) {
            CHClientWorld chClientWorld = ((ICHClientWorld) clientLevel).getCHClientWorld();
            chClientWorld.setMeteorShower(packet.isMeteorShower);
            chClientWorld.setMeteorFlash(packet.isMeteorFlash);
            chClientWorld.setPlayMeteorExplode(packet.playMeteorExplode);
            chClientWorld.setPlayMeteorMusic(packet.playMeteorMusic);
            chClientWorld.setMeteorMusicFullVolume(packet.isMeteorMusicFullVolume);
            chClientWorld.setMeteorFlashTick(packet.meteorFlashTick);
        }
    }
}
