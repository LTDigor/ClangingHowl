package com.mongoose.clanginghowl.client.particles;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.core.particles.SimpleParticleType;

public class QuakeSmokeParticle extends TextureSheetParticle {
    private final SpriteSet spriteSet;

    protected QuakeSmokeParticle(ClientLevel world, double x, double y, double z, double vx, double vy, double vz, SpriteSet spriteSet) {
        super(world, x, y, z);
        this.spriteSet = spriteSet;
        this.scale(3.0F);
        this.setSize(0.25F, 0.25F);
        this.lifetime = 18;
        this.gravity = -0.01F;
        this.friction = 0.9F;
        this.xd = vx == 0 ? 0 : vx + (Math.random() * 2.0D - 1.0D) * (double)0.05F;
        this.yd = vy == 0 ? 0 : vy + (Math.random() * 2.0D - 1.0D) * (double)0.05F;
        this.zd = vz == 0 ? 0 : vz + (Math.random() * 2.0D - 1.0D) * (double)0.05F;
        this.setSpriteFromAge(spriteSet);
    }

    public void tick() {
        super.tick();
        if (!this.removed) {
            this.setSprite(this.spriteSet.get((this.age / 2) % 10 + 1, 10));
        }
    }

    public int getLightColor(float p_233983_) {
        return LightTexture.FULL_BRIGHT;
    }

    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
    }

    public static class Provider implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet spriteSet;

        public Provider(SpriteSet spriteSet) {
            this.spriteSet = spriteSet;
        }

        public Particle createParticle(SimpleParticleType typeIn, ClientLevel worldIn, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            return new QuakeSmokeParticle(worldIn, x, y, z, xSpeed, ySpeed, zSpeed, this.spriteSet);
        }
    }
}
