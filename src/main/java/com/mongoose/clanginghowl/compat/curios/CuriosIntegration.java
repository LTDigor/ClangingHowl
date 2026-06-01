package com.mongoose.clanginghowl.compat.curios;

import com.google.common.collect.ImmutableMap;
import com.mongoose.clanginghowl.common.items.CHItems;
import com.mongoose.clanginghowl.common.items.curios.CHCurioItem;
import com.mongoose.clanginghowl.compat.CHCompatable;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import top.theillusivec4.curios.api.CuriosApi;

import java.util.Map;

@SuppressWarnings("all")
public class CuriosIntegration implements CHCompatable {

    private static final Map<Item, String> TYPES = ImmutableMap.<Item, String>builder()
            .put(CHItems.X_RAY_GOGGLES.get(), "head")
            .put(CHItems.ENERGY_BARRIER_GENERATOR.get(), "body")
            .put(CHItems.TENDON_STRENGTHENER.get(), "legs")
            .put(CHItems.ENERGY_GLOVE.get(), "hands")
            .put(CHItems.JET_BOOTS.get(), "feet")
            .put(CHItems.BLOODY_BATTERY.get(), "belt")
            .put(CHItems.REANIMATOR.get(), "heart")
            .build();

    public void setup(FMLCommonSetupEvent event) {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::sendImc);
        MinecraftForge.EVENT_BUS.addListener(this::registerCapabilities);
    }

    private void sendImc(InterModEnqueueEvent event) {
        TYPES.values().stream().distinct().forEach(t -> InterModComms.sendTo("curios", top.theillusivec4.curios.api.SlotTypeMessage.REGISTER_TYPE, () -> new top.theillusivec4.curios.api.SlotTypeMessage.Builder(t).build()));
    }

    private void registerCapabilities(RegisterCapabilitiesEvent event) {
        TYPES.keySet().forEach(entry -> {
            if (entry instanceof CHCurioItem item) {
                CuriosApi.registerCurio(item, new CHCurioItem());
            }
        });
    }

}
