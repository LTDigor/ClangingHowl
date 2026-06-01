package com.mongoose.clanginghowl.init;

import com.mojang.brigadier.CommandDispatcher;
import com.mongoose.clanginghowl.ClangingHowl;
import com.mongoose.clanginghowl.common.capabilities.CHCapProvider;
import com.mongoose.clanginghowl.common.capabilities.ICHCap;
import com.mongoose.clanginghowl.common.commands.CHCommands;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = ClangingHowl.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class InitEvents {

    @SubscribeEvent
    public static void onRegisterCommandEvent(RegisterCommandsEvent event) {
        CommandDispatcher<CommandSourceStack> commandDispatcher = event.getDispatcher();
        CHCommands.register(commandDispatcher, event.getBuildContext());
    }

    @SubscribeEvent
    public static void registerCapabilities(RegisterCapabilitiesEvent event){
        event.register(ICHCap.class);
    }

    @SubscribeEvent
    public static void attachCapabilities(AttachCapabilitiesEvent<Entity> event) {
        if (event.getObject() instanceof LivingEntity) {
            event.addCapability(ClangingHowl.location("misc"), new CHCapProvider());
        }
    }
}
