package com.mongoose.clanginghowl.client.particles;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.SimpleParticleType;

public class EnergeticEmergenceParticle extends TextureSheetParticle {
    private final SpriteSet sprites;

    protected EnergeticEmergenceParticle(ClientLevel clientLevel, double x, double y, double z, double xd, double yd, double zd, float size, SpriteSet spriteSet) {
        super(clientLevel, x, y, z, 0.0, 0.0, 0.0);
        this.sprites = spriteSet;
        this.friction = 0.96F;
        this.gravity = -0.1F;
        this.speedUpWhenYMotionIsBlocked = true;
        this.xd *= 0.0;
        this.yd *= 0.9;
        this.zd *= 0.0;
        this.xd += xd;
        this.yd += yd;
        this.zd += zd;
        this.quadSize *= 0.75F * size;
        this.lifetime = 10;
        this.setSpriteFromAge(spriteSet);
        this.hasPhysics = true;
    }

    @Override
    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
    }

    @Override
    public int getLightColor(float f) {
        return 240;
    }

    @Override
    public void tick() {
        super.tick();
        if (!this.removed) {
            this.setSprite(this.sprites.get((this.age / 2) % 6 + 1, 6));
        }
    }

    public static class Provider implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet sprites;

        public Provider(SpriteSet spriteSet) {
            this.sprites = spriteSet;
        }

        public Particle createParticle(SimpleParticleType simpleParticleType, ClientLevel clientLevel, double d, double e, double f, double g, double h, double i) {
            return new EnergeticEmergenceParticle(clientLevel, d, e, f, g, h, i, 1.0F, this.sprites);
        }
    }
}
