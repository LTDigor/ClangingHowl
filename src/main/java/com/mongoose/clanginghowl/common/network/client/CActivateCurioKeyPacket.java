package com.mongoose.clanginghowl.common.network.client;

import com.mongoose.clanginghowl.common.items.curios.IActivatable;
import com.mongoose.clanginghowl.utils.CHCuriosFinder;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class CActivateCurioKeyPacket {
    public static void encode(CActivateCurioKeyPacket packet, FriendlyByteBuf buffer) {
    }

    public static CActivateCurioKeyPacket decode(FriendlyByteBuf buffer) {
        return new CActivateCurioKeyPacket();
    }

    public static void consume(CActivateCurioKeyPacket packet, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            ServerPlayer playerEntity = ctx.get().getSender();

            if (playerEntity != null) {
                ItemStack stack = CHCuriosFinder.findCurio(playerEntity, itemStack -> itemStack.getItem() instanceof IActivatable);

                if (!stack.isEmpty() && stack.getItem() instanceof IActivatable activatable){
                    activatable.activate(playerEntity.level(), playerEntity, stack);
                }
            }
        });
        ctx.get().setPacketHandled(true);
    }
}
