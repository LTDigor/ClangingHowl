package com.mongoose.clanginghowl.client.particles;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.SimpleParticleType;

public class EnergyParticle extends TextureSheetParticle {
    private final SpriteSet sprites;

    protected EnergyParticle(ClientLevel clientLevel, double x, double y, double z, double xd, double yd, double zd, SpriteSet spriteSet) {
        super(clientLevel, x, y, z, 0.0, 0.0, 0.0);
        this.sprites = spriteSet;
        this.friction = 0.96F;
        this.gravity = -0.1F;
        this.speedUpWhenYMotionIsBlocked = true;
        this.xd *= 0.0;
        this.yd *= 0.5;
        this.zd *= 0.0;
        this.xd += xd;
        this.yd += yd;
        this.zd += zd;
        this.quadSize *= 0.75F;
        this.lifetime = clientLevel.getRandom().nextIntBetweenInclusive(15, 25);
        this.setSpriteFromAge(spriteSet);
        this.hasPhysics = true;
    }

    @Override
    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_OPAQUE;
    }

    @Override
    public int getLightColor(float f) {
        return 240;
    }

    @Override
    public void tick() {
        super.tick();
        this.setSpriteFromAge(this.sprites);
    }

    @Override
    public float getQuadSize(float partialTick) {
        float f = ((float)this.age + partialTick) / (float)this.lifetime;
        return this.quadSize * (1.0F - f * f);
    }

    public static class Provider implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet sprites;

        public Provider(SpriteSet spriteSet) {
            this.sprites = spriteSet;
        }

        public Particle createParticle(SimpleParticleType simpleParticleType, ClientLevel clientLevel, double d, double e, double f, double g, double h, double i) {
            return new EnergyParticle(clientLevel, d, e, f, g, h, i, this.sprites);
        }
    }

    public static class BloodyProvider implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet sprites;

        public BloodyProvider(SpriteSet spriteSet) {
            this.sprites = spriteSet;
        }

        public Particle createParticle(SimpleParticleType simpleParticleType, ClientLevel clientLevel, double d, double e, double f, double g, double h, double i) {
            EnergyParticle particle = new EnergyParticle(clientLevel, d, e, f, g, h, i, this.sprites);
            particle.hasPhysics = false;
            return particle;
        }
    }

    public static class BigBloodyProvider implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet sprites;

        public BigBloodyProvider(SpriteSet spriteSet) {
            this.sprites = spriteSet;
        }

        public Particle createParticle(SimpleParticleType simpleParticleType, ClientLevel clientLevel, double d, double e, double f, double g, double h, double i) {
            EnergyParticle particle = new EnergyParticle(clientLevel, d, e, f, g, h, i, this.sprites);
            particle.quadSize *= 2.0F;
            particle.hasPhysics = false;
            return particle;
        }
    }
}
