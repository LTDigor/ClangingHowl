package com.mongoose.clanginghowl.client.render;

import net.minecraft.client.renderer.FogRenderer;

//Stolen from @yungnickyoung codes: https://github.com/YUNG-GANG/YUNGs-Cave-Biomes/blob/1.20.1/Common/src/main/java/com/yungnickyoung/minecraft/yungscavebiomes/client/render/sandstorm/SandstormFogRenderer.java
public class MeteorShowerFogRenderer {
    private double fogLevel = 0;

    public void render(FogRenderer.FogMode mode, float renderDistance) {
        /*if (mode == FogRenderer.FogMode.FOG_TERRAIN) {
            LocalPlayer localPlayer = Minecraft.getInstance().player;
            ClientLevel clientLevel = Minecraft.getInstance().level;
            if (localPlayer == null || clientLevel == null) {
                return;
            }

            ICHClientWorld data = (ICHClientWorld) clientLevel;
            if (data.getCHClientWorld().isMeteorShower()) {
                this.fogLevel = Mth.clamp(this.fogLevel + 0.002, 0, 1);
            } else {
                this.fogLevel = Mth.clamp(this.fogLevel - 0.002, 0, 1);
            }

            if (!localPlayer.isAlive()) {
                this.fogLevel = 0;
            }

            if (this.fogLevel > 0) {
                RenderSystem.setShaderFogStart((float) Mth.lerp(this.fogLevel, RenderSystem.getShaderFogStart(), -4.0F));
                RenderSystem.setShaderFogEnd((float) Mth.lerp(this.fogLevel, RenderSystem.getShaderFogEnd(), Math.min(renderDistance * 0.45F, 64)));
            }
        }*/
    }

    public double getFogLevel() {
        return fogLevel;
    }
}
