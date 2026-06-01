package com.mongoose.clanginghowl.common.network;

import com.mongoose.clanginghowl.ClangingHowl;
import com.mongoose.clanginghowl.common.capabilities.CHCapUpdatePacket;
import com.mongoose.clanginghowl.common.network.client.CActivateCurioKeyPacket;
import com.mongoose.clanginghowl.common.network.client.CIsMovingPacket;
import com.mongoose.clanginghowl.common.network.client.CJetBootsJumpPacket;
import com.mongoose.clanginghowl.common.network.server.SInstaLookPacket;
import com.mongoose.clanginghowl.common.network.server.SPlayWorldSoundPacket;
import com.mongoose.clanginghowl.common.network.server.SReanimatorDeathPacket;
import com.mongoose.clanginghowl.common.network.server.SSendCHWorldData;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;

public class CHNetwork {
    public static SimpleChannel INSTANCE;
    private static int id = 0;

    public static int nextID() {
        return id++;
    }

    public static void init() {
        INSTANCE = NetworkRegistry.newSimpleChannel(ClangingHowl.location("channel"), () -> "1.0", s -> true, s -> true);

        INSTANCE.registerMessage(nextID(), CIsMovingPacket.class, CIsMovingPacket::encode, CIsMovingPacket::decode, CIsMovingPacket::consume);
        INSTANCE.registerMessage(nextID(), CActivateCurioKeyPacket.class, CActivateCurioKeyPacket::encode, CActivateCurioKeyPacket::decode, CActivateCurioKeyPacket::consume);
        INSTANCE.registerMessage(nextID(), CJetBootsJumpPacket.class, CJetBootsJumpPacket::encode, CJetBootsJumpPacket::decode, CJetBootsJumpPacket::consume);
        INSTANCE.registerMessage(nextID(), SInstaLookPacket.class, SInstaLookPacket::encode, SInstaLookPacket::decode, SInstaLookPacket::consume);
        INSTANCE.registerMessage(nextID(), SPlayWorldSoundPacket.class, SPlayWorldSoundPacket::encode, SPlayWorldSoundPacket::decode, SPlayWorldSoundPacket::consume);
        INSTANCE.registerMessage(nextID(), SSendCHWorldData.class, SSendCHWorldData::encode, SSendCHWorldData::decode, SSendCHWorldData::consume);
        INSTANCE.registerMessage(nextID(), SReanimatorDeathPacket.class, SReanimatorDeathPacket::encode, SReanimatorDeathPacket::decode, SReanimatorDeathPacket::consume);
        INSTANCE.registerMessage(nextID(), CHCapUpdatePacket.class, CHCapUpdatePacket::encode, CHCapUpdatePacket::decode, CHCapUpdatePacket::consume);
    }

    public static <MSG> void sendTo(Player player, MSG msg) {
        CHNetwork.INSTANCE.send(PacketDistributor.PLAYER.with(() -> (ServerPlayer) player), msg);
    }

    public static <MSG> void sendToServer(MSG msg) {
        CHNetwork.INSTANCE.sendToServer(msg);
    }

    public static <MSG> void sentToTrackingChunk(LevelChunk chunk, MSG msg) {
        CHNetwork.INSTANCE.send(PacketDistributor.TRACKING_CHUNK.with(() -> chunk), msg);
    }

    public static <MSG> void sentToTrackingEntity(Entity entity, MSG msg) {
        CHNetwork.INSTANCE.send(PacketDistributor.TRACKING_ENTITY.with(() -> entity), msg);
    }

    public static <MSG> void sentToTrackingEntityAndPlayer(Entity entity, MSG msg) {
        CHNetwork.INSTANCE.send(PacketDistributor.TRACKING_ENTITY_AND_SELF.with(() -> entity), msg);
    }

    public static <MSG> void sendToALL(MSG msg) {
        CHNetwork.INSTANCE.send(PacketDistributor.ALL.noArg(), msg);
    }

    public static <MSG> void sendToClient(ServerPlayer player, MSG msg) {
        CHNetwork.INSTANCE.sendTo(msg, player.connection.connection, NetworkDirection.PLAY_TO_CLIENT);
    }

    public static <MSG> void sendToClientLevel(MSG message, ResourceKey<Level> levelResourceKey) {
        CHNetwork.INSTANCE.send(PacketDistributor.DIMENSION.with(() -> levelResourceKey), message);
    }
}
