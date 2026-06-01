package com.mongoose.clanginghowl.common.blocks.entities.consummate_nest;

import com.mongoose.clanginghowl.client.particles.CHParticleTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.RandomSource;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public enum ConsummateNestState implements StringRepresentable {
    INACTIVE("inactive"),
    ACTIVE("active"),
    EJECTING_REWARD("ejecting_reward"),
    COOLDOWN("cooldown");

    private final String name;

    ConsummateNestState(String name) {
        this.name = name;
    }

    @Override
    public String getSerializedName() {
        return this.name;
    }

    public int lightLevel() {
        return switch (this) {
            case ACTIVE, EJECTING_REWARD -> 8;
            case COOLDOWN -> 2;
            default -> 0;
        };
    }

    public void emitParticles(Level level, BlockPos blockPos) {
        RandomSource random = level.getRandom();
        Vec3 center = blockPos.getCenter();
        switch (this) {
            case ACTIVE -> {
                Vec3 vec3 = center.offsetRandom(random, 1.0F);
                level.addParticle(CHParticleTypes.BIG_BLOODY_ENERGY.get(), vec3.x(), vec3.y(), vec3.z(), 0, 0, 0);
                if (random.nextInt(3) == 0)
                    level.addParticle(ParticleTypes.SMOKE, vec3.x(), vec3.y(), vec3.z(), 0, 0, 0);
            }
            case COOLDOWN -> {
                if (random.nextInt(3) == 0) {
                    Vec3 vec3 = center.offsetRandom(random, 0.9F);
                    level.addParticle(ParticleTypes.SMOKE, vec3.x(), vec3.y(), vec3.z(), 0, 0, 0);
                }
                if (level.getGameTime() % 20L == 0L) {
                    Vec3 top = center.add(0, 0.5, 0);
                    for (int i = 0; i < random.nextInt(4) + 20; i++)
                        level.addParticle(ParticleTypes.SMOKE, top.x(), top.y(), top.z(), 0, 0, 0);
                }
            }
            default -> {}
        }
    }
}
