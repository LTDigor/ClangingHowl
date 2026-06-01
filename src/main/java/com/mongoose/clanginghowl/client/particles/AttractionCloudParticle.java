package com.mongoose.clanginghowl.client.particles;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.core.particles.SimpleParticleType;

public class AttractionCloudParticle extends TextureSheetParticle {
    private final SpriteSet spriteSet;

    protected AttractionCloudParticle(ClientLevel world, double x, double y, double z, double vx, double vy, double vz, SpriteSet spriteSet) {
        super(world, x, y, z);
        this.spriteSet = spriteSet;
        this.setSize(0.25F, 0.25F);
        this.quadSize = (0.1F * 2.0F) * 3.5F;
        this.lifetime = 16;
        this.gravity = -0.01F;
        this.hasPhysics = false;
        this.xd = 0.0F;
        this.yd = 0.01F;
        this.zd = 0.0F;
        this.setSpriteFromAge(spriteSet);
    }

    public void tick() {
        super.tick();
        if (!this.removed) {
            this.setSprite(this.spriteSet.get((this.age / 2) % 9 + 1, 9));
        }
    }

    public int getLightColor(float p_233983_) {
        return LightTexture.FULL_BRIGHT;
    }

    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_LIT;
    }

    public static class Provider implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet spriteSet;

        public Provider(SpriteSet spriteSet) {
            this.spriteSet = spriteSet;
        }

        public Particle createParticle(SimpleParticleType typeIn, ClientLevel worldIn, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            return new AttractionCloudParticle(worldIn, x, y, z, xSpeed, ySpeed, zSpeed, this.spriteSet);
        }
    }
}
