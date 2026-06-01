package com.mongoose.clanginghowl.common.network.client;

import com.mongoose.clanginghowl.common.capabilities.CHCapHelper;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

/**
 * Based on DoubleJumpPacket from Aether-Redux codes: <a href="https://github.com/Zepalesque/The-Aether-Redux/blob/1.20.1/src/main/java/net/zepalesque/redux/network/packet/DoubleJumpPacket.java">...</a>
 */
public class CJetBootsJumpPacket {

    public static void encode(CJetBootsJumpPacket packet, FriendlyByteBuf buffer) {
    }

    public static CJetBootsJumpPacket decode(FriendlyByteBuf buffer) {
        return new CJetBootsJumpPacket();
    }

    public static void consume(CJetBootsJumpPacket packet, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            ServerPlayer playerEntity = ctx.get().getSender();
            if (playerEntity != null) {
                CHCapHelper.doubleJump(playerEntity);
            }

        });
        ctx.get().setPacketHandled(true);
    }
}
