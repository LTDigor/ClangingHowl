package com.mongoose.clanginghowl.init;

import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import org.apache.commons.lang3.ArrayUtils;
import org.lwjgl.glfw.GLFW;

public class CHKeybindings {
    public static KeyMapping[] keyBindings = new KeyMapping[1];

    public static void init(){
        keyBindings[0] = new KeyMapping("key.clanginghowl.curios", GLFW.GLFW_KEY_Y, "key.clanginghowl.category");

        for (KeyMapping keyBinding : keyBindings) {
            Minecraft.getInstance().options.keyMappings = ArrayUtils.add(Minecraft.getInstance().options.keyMappings, keyBinding);
        }
    }

    public static KeyMapping curioActivate(){
        if (keyBindings[0] != null) {
            return keyBindings[0];
        } else {
            return null;
        }
    }
}
