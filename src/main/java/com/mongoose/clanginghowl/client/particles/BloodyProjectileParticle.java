package com.mongoose.clanginghowl.client.particles;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.SimpleParticleType;

public class BloodyProjectileParticle extends TextureSheetParticle {
    public SpriteSet spriteSet;

    public BloodyProjectileParticle(ClientLevel pLevel, double pX, double pY, double pZ, SpriteSet pSprites) {
        super(pLevel, pX, pY, pZ, 0.0D, 0.0D, 0.0D);
        this.setAlpha(1.0F);
        this.quadSize *= 2.0F;
        this.lifetime = 10;
        this.gravity = 0.1F;
        this.xd = 0.0D;
        this.yd = 0.0D;
        this.zd = 0.0D;
        this.hasPhysics = true;
        this.spriteSet = pSprites;
        this.setSprite(spriteSet.get(this.random));
    }

    @Override
    public void tick() {
        super.tick();
        if (!this.removed) {
            this.setSprite(this.spriteSet.get((this.age / 2) % 6 + 1, 6));
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
            return new BloodyProjectileParticle(p_106839_, p_106840_, p_106841_, p_106842_, this.sprite);
        }
    }
}
