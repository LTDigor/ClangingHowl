package com.mongoose.clanginghowl.client.world;

import net.minecraft.client.multiplayer.ClientLevel;
import org.jetbrains.annotations.Nullable;

public interface ICHClientWorld {
    CHClientWorld getCHClientWorld();

    @Nullable
    MeteorFlashState clanginghowl$getMeteorFlashState();

    @Nullable
    static MeteorFlashState get(ClientLevel level) {
        return ((ICHClientWorld) level).clanginghowl$getMeteorFlashState();
    }
}
