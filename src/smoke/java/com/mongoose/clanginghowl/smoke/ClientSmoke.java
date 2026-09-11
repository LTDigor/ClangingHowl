package com.mongoose.clanginghowl.smoke;

import com.mongoose.clanginghowl.ClangingHowl;
import com.mongoose.clanginghowl.client.ClientProxy;
import com.mongoose.clanginghowl.common.items.CHItems;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraft.client.model.HumanoidModel;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;
import java.util.List;

/** Enabled only by the clientSmoke development run. Never included in the mod JAR. */
@EventBusSubscriber(modid = "clanginghowl", value = Dist.CLIENT)
public final class ClientSmoke {
    private static int stableTicks;
    private static boolean finished;

    @SubscribeEvent
    public static void onClientTick(ClientTickEvent.Post event) {
        if (!Boolean.getBoolean("clanginghowl.smokeTest") || finished) return;
        Minecraft mc = Minecraft.getInstance();
        if (!(mc.screen instanceof TitleScreen) || mc.getOverlay() != null) {
            stableTicks = 0;
            return;
        }
        if (++stableTicks < 20) return;
        if (!(ClangingHowl.PROXY instanceof ClientProxy)) throw new IllegalStateException("Client entrypoint did not run");
        for (String pose : List.of("CHAINSAW", "IDLE_SAW", "DRILL", "IDLE_DRILL", "FLAME", "IDLE_FLAME")) {
            HumanoidModel.ArmPose.valueOf("CLANGINGHOWL_" + pose);
        }
        for (var item : List.of(CHItems.ADVANCED_CHAINSAW.get(), CHItems.ADVANCED_HAND_DRILL.get(), CHItems.FLAMETHROWER.get())) {
            if (IClientItemExtensions.of(item) == IClientItemExtensions.DEFAULT) {
                throw new IllegalStateException("Missing weapon client extension: " + item);
            }
        }
        finished = true;
        ClangingHowl.LOGGER.info("CLANGINGHOWL_CLIENT_SMOKE_PASS: title screen, resources, six arm poses and weapon extensions");
        mc.stop();
    }
}
