package com.mongoose.clanginghowl.client.world;

import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;

//Stolen and modified from @Smallinger codes:https://github.com/Smallinger/Copper-Age-Backport/blob/1.20.1/common/src/main/java/com/github/smallinger/copperagebackport/client/endflash/EndFlashState.java
public class MeteorFlashState {
    private static final int FLASH_INTERVAL_IN_TICKS = 600;

    private int offset;
    private int duration;
    private float intensity;
    private float xAngle;
    private float yAngle;

    public MeteorFlashState(RandomSource randomSource) {
        this.duration = 220;
        this.xAngle = 10.0F;
        this.yAngle = Mth.randomBetween(randomSource, -180.0F, 180.0F);
        this.intensity = 0.0F;
        this.offset = 0;
    }

    public void tick(long tick) {
        this.intensity = this.calculateIntensity(tick);
    }

    private float calculateIntensity(long tick) {
        long i = tick % FLASH_INTERVAL_IN_TICKS;
        return i >= this.offset && i <= this.offset + this.duration
                ? Mth.sin((float)(i - this.offset) * (float) Math.PI / this.duration)
                : 0.0F;
    }

    public float getXAngle() {
        return this.xAngle;
    }

    public float getYAngle() {
        return this.yAngle;
    }

    public float getIntensity() {
        return this.intensity;
    }
}
