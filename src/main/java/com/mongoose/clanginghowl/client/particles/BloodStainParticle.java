package com.mongoose.clanginghowl.client.particles;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.core.particles.SimpleParticleType;

public class BloodStainParticle extends GroundCircleParticle {
    public SpriteSet spriteSet;

    public BloodStainParticle(ClientLevel pLevel, double pX, double pY, double pZ, SpriteSet pSprites) {
        super(pLevel, pX, pY, pZ, 0.0D, 0.0D, 0.0D);
        this.setAlpha(1.0F);
        this.quadSize *= 3.0F;
        this.lifetime = this.random.nextIntBetweenInclusive(70, 90);
        this.gravity = 0.0F;
        this.xd = 0.0D;
        this.yd = 0.0D;
        this.zd = 0.0D;
        this.spriteSet = pSprites;
        this.setSprite(spriteSet.get(this.random));
    }

    @Override
    public void tick() {
        super.tick();
        if (this.age++ >= this.lifetime) {
            this.remove();
        } else {
            if (this.quadSize > 0.0F) {
                this.quadSize -= 0.01F;
            }
        }
    }

    @Override
    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
    }

    public static class Provider implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet sprite;

        public Provider(SpriteSet p_106827_) {
            this.sprite = p_106827_;
        }

        public Particle createParticle(SimpleParticleType p_106838_, ClientLevel p_106839_, double p_106840_, double p_106841_, double p_106842_, double p_106843_, double p_106844_, double p_106845_) {
            return new BloodStainParticle(p_106839_, p_106840_, p_106841_, p_106842_, this.sprite);
        }
    }
}
