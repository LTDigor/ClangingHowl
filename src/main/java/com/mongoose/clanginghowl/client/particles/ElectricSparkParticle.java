package com.mongoose.clanginghowl.client.particles;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;

public class ElectricSparkParticle extends TextureSheetParticle {

    public ElectricSparkParticle(ClientLevel p_107074_, double p_107075_, double p_107076_, double p_107077_, double xSpeed, double ySpeed, double zSpeed) {
        super(p_107074_, p_107075_, p_107076_, p_107077_, xSpeed, ySpeed, zSpeed);
        this.gravity = 0.75F;
        this.friction = 0.96F;
        this.xd *= 2.0F;
        this.zd *= 2.0F;
        this.quadSize *= this.random.nextFloat() * 1.25F + 0.2F;
        this.lifetime = this.random.nextIntBetweenInclusive(15, 20);
    }

    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_OPAQUE;
    }

    public float getQuadSize(float p_107089_) {
        float f = ((float)this.age + p_107089_) / (float)this.lifetime;
        return this.quadSize * (1.0F - f * f);
    }

    public int getLightColor(float p_106921_) {
        return LightTexture.FULL_BRIGHT;
    }

    public void tick() {
        super.tick();
        if (!this.removed) {
            float f = (float)this.age / (float)this.lifetime;
            if (f >= 0.75F && this.random.nextFloat() < f) {
                this.level.addParticle(ParticleTypes.SMOKE, this.x, this.y, this.z, this.xd, this.yd, this.zd);
            }
        }

    }

    public static class Provider implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet sprite;

        public Provider(SpriteSet p_107092_) {
            this.sprite = p_107092_;
        }

        public Particle createParticle(SimpleParticleType p_107103_, ClientLevel p_107104_, double p_107105_, double p_107106_, double p_107107_, double p_107108_, double p_107109_, double p_107110_) {
            ElectricSparkParticle sparkParticle = new ElectricSparkParticle(p_107104_, p_107105_, p_107106_, p_107107_, p_107108_, p_107109_, p_107110_);
            sparkParticle.pickSprite(this.sprite);
            return sparkParticle;
        }
    }
}
