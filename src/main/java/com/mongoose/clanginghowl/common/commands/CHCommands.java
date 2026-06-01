package com.mongoose.clanginghowl.common.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mongoose.clanginghowl.common.world.data.ICHWorldData;
import com.mongoose.clanginghowl.config.CHConfig;
import com.mongoose.clanginghowl.utils.MathHelper;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;

public class CHCommands {
    public static void register(CommandDispatcher<CommandSourceStack> pDispatcher, CommandBuildContext p_250122_) {
        pDispatcher.register(Commands.literal("clanginghowl")
                .requires((p_198442_0_) -> {
                    return p_198442_0_.hasPermission(2);
                })
                .then(Commands.literal("meteor_shower")
                        .then(Commands.literal("start").executes((p_198445_0_) -> {
                            return startMeteorShower(p_198445_0_.getSource());
                        }))
                        .then(Commands.literal("query").executes((p_198445_0_) -> {
                            return queryMeteorShower(p_198445_0_.getSource());
                        }))
                        .then(Commands.literal("stop").executes((p_198445_0_) -> {
                            return stopMeteorShower(p_198445_0_.getSource());
                        }))));
    }

    private static int startMeteorShower(CommandSourceStack pSource) {
        int i = 0;
        if (pSource.getLevel() instanceof ICHWorldData data) {
            if (data.getCHWorldData().isMeteorShower()) {
                pSource.sendFailure(Component.translatable("commands.clanginghowl.meteor_shower.start.failure.ongoing"));
            } else if (data.getCHWorldData().isForceMeteor() || data.getCHWorldData().startMeteorShower()){
                pSource.sendFailure(Component.translatable("commands.clanginghowl.meteor_shower.start.failure.upcoming"));
            } else if (!data.getCHWorldData().isForceMeteor()) {
                data.getCHWorldData().setForceStop(false);
                data.getCHWorldData().setForceMeteor(true);
                if (pSource.getLevel().isNight()) {
                    pSource.sendSuccess(() -> Component.translatable("commands.clanginghowl.meteor_shower.start.success2"), true);
                } else {
                    pSource.sendSuccess(() -> Component.translatable("commands.clanginghowl.meteor_shower.start.success"), true);
                }
                ++i;
            }
        } else {
            pSource.sendFailure(Component.translatable("commands.clanginghowl.meteor_shower.start.failure.world"));
        }

        return i;
    }

    private static int queryMeteorShower(CommandSourceStack pSource) {
        int i = 0;
        if (pSource.getLevel() instanceof ICHWorldData data) {
            if (data.getCHWorldData().isMeteorShower()) {
                pSource.sendSuccess(() -> Component.translatable("commands.clanginghowl.meteor_shower.query.success"), true);
            } else if (data.getCHWorldData().isForceMeteor() || data.getCHWorldData().startMeteorShower()){
                pSource.sendSuccess(() -> Component.translatable("commands.clanginghowl.meteor_shower.query.success2"), true);
            } else {
                long time = (MathHelper.minecraftDayToTicks(CHConfig.MeteorShowerDays.get()) - (pSource.getLevel().getDayTime() % MathHelper.minecraftDayToTicks(CHConfig.MeteorShowerDays.get()))) / 24000L;
                if (time != 1) {
                    pSource.sendSuccess(() -> Component.translatable("commands.clanginghowl.meteor_shower.query.success3", time), true);
                } else {
                    pSource.sendSuccess(() -> Component.translatable("commands.clanginghowl.meteor_shower.query.success4"), true);
                }
            }
            ++i;
        } else {
            pSource.sendFailure(Component.translatable("commands.clanginghowl.meteor_shower.start.failure.world"));
        }

        return i;
    }

    private static int stopMeteorShower(CommandSourceStack pSource) {
        int i = 0;
        if (pSource.getLevel() instanceof ICHWorldData data) {
            if (data.getCHWorldData().isMeteorShower() || data.getCHWorldData().startMeteorShower()) {
                if (data.getCHWorldData().isMeteorShower()) {
                    pSource.sendSuccess(() -> Component.translatable("commands.clanginghowl.meteor_shower.stop.success"), true);
                } else {
                    pSource.sendSuccess(() -> Component.translatable("commands.clanginghowl.meteor_shower.stop.success2"), true);
                }
                data.getCHWorldData().setForceStop(true);
                data.getCHWorldData().setLastStop(MathHelper.ticksToMinecraftDay(pSource.getLevel().getDayTime()));
                data.getCHWorldData().setForceMeteor(false);
                data.getCHWorldData().stop();
                ++i;
            } else {
                pSource.sendFailure(Component.translatable("commands.clanginghowl.meteor_shower.stop.failure"));
            }
        } else {
            pSource.sendFailure(Component.translatable("commands.clanginghowl.meteor_shower.start.failure.world"));
        }

        return i;
    }
}
