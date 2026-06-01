package com.mongoose.clanginghowl.mixin;

import com.mojang.blaze3d.platform.NativeImage;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LightTexture;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

//Stolen from @Smallinger's codes:https://github.com/Smallinger/Copper-Age-Backport/blob/1.20.1/common/src/main/java/com/github/smallinger/copperagebackport/mixin/client/LightTextureMixin.java
@Mixin(LightTexture.class)
public abstract class LightTextureMixin {
    private static final int ORANGE_R = 216;
    private static final int ORANGE_G = 169;
    private static final int ORANGE_B = 71;

    @Shadow
    @Final
    private Minecraft minecraft;

    @Shadow
    @Final
    private NativeImage lightPixels;

    @Unique
    private float clanginghowl$meteorFlashIntensity;

    /**
     * Calculate End flash intensity before updating light texture
     */
    @Inject(method = "updateLightTexture", at = @At("HEAD"))
    private void clanginghowl$captureMeteorFlashIntensity(float partialTicks, CallbackInfo ci) {
        /*this.clanginghowl$meteorFlashIntensity = 0.0F;
        ClientLevel level = this.minecraft.level;
        if (level == null || level.dimension() != Level.OVERWORLD) {
            return;
        }

        MeteorFlashState state = ICHClientWorld.get(level);
        if (state == null || this.minecraft.options.hideLightningFlash().get()) {
            return;
        }

        float intensity = state.getIntensity(partialTicks);
        if (intensity <= 0.0F) {
            return;
        }

        if (this.minecraft.gui.getBossOverlay().shouldCreateWorldFog()) {
            intensity /= 3.0F;
        }

        this.clanginghowl$meteorFlashIntensity = intensity;*/
    }

    /**
     * After the lightmap is calculated but before upload, apply the purple tint.
     * We inject right before the upload() call and modify the pixel data.
     */
    @Inject(method = "updateLightTexture", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/texture/DynamicTexture;upload()V"))
    private void clanginghowl$applyMeteorFlashTint(float partialTicks, CallbackInfo ci) {
        /*if (this.clanginghowl$meteorFlashIntensity <= 0.0F) {
            return;
        }

        ClientLevel level = this.minecraft.level;
        if (level == null || level.dimension() != Level.OVERWORLD) {
            return;
        }

        float intensity = this.clanginghowl$meteorFlashIntensity;
        float brightnessBoost = 1.0F + intensity * 0.5F;

        for (int sky = 0; sky < 16; ++sky) {
            for (int block = 0; block < 16; ++block) {
                int pixel = this.lightPixels.getPixelRGBA(block, sky);
                int alpha = (pixel >>> 24) & 0xFF;
                int blue = (pixel >>> 16) & 0xFF;
                int green = (pixel >>> 8) & 0xFF;
                int red = pixel & 0xFF;

                red = (int)Math.min(255.0F, (red + (ORANGE_R - red) * intensity) * brightnessBoost);
                green = (int)Math.min(255.0F, (green + (ORANGE_G - green) * intensity) * brightnessBoost);
                blue = (int)Math.min(255.0F, (blue + (ORANGE_B - blue) * intensity) * brightnessBoost);

                this.lightPixels.setPixelRGBA(block, sky, (alpha << 24) | (blue << 16) | (green << 8) | red);
            }
        }*/
    }
}
