package com.mongoose.clanginghowl.common.network.server;

import com.mongoose.clanginghowl.ClangingHowl;
import com.mongoose.clanginghowl.common.items.CHItems;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class SReanimatorDeathPacket {

    public SReanimatorDeathPacket() {
    }

    public static void encode(SReanimatorDeathPacket packet, FriendlyByteBuf buffer) {
    }

    public static SReanimatorDeathPacket decode(FriendlyByteBuf buffer) {
        return new SReanimatorDeathPacket();
    }

    public static void consume(SReanimatorDeathPacket packet, Supplier<NetworkEvent.Context> ctx) {

        ctx.get().enqueueWork(() -> {
            Player playerEntity = ClangingHowl.PROXY.getPlayer();

            if (playerEntity != null) {
                playerEntity.level().playLocalSound(playerEntity.getX(), playerEntity.getY(), playerEntity.getZ(), SoundEvents.TOTEM_USE, playerEntity.getSoundSource(), 0.25F, 1.0F, false);
                Minecraft.getInstance().gameRenderer.displayItemActivation(new ItemStack(CHItems.REANIMATION.get()));
            }
        });
        ctx.get().setPacketHandled(true);
    }
}
