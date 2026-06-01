package com.mongoose.clanginghowl.utils;

import com.mongoose.clanginghowl.client.particles.CHParticleTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundLevelParticlesPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;

public class ParticleUtil {

    public static <T extends ParticleOptions> int sendAlwaysVisibleParticles(ServerLevel serverLevel, T p_8768_, double p_8769_, double p_8770_, double p_8771_, int p_8772_, double p_8773_, double p_8774_, double p_8775_, double p_8776_) {
        ClientboundLevelParticlesPacket clientboundlevelparticlespacket = new ClientboundLevelParticlesPacket(p_8768_, false, p_8769_, p_8770_, p_8771_, (float)p_8773_, (float)p_8774_, (float)p_8775_, (float)p_8776_, p_8772_);
        int i = 0;

        for(int j = 0; j < serverLevel.getPlayers(serverPlayer -> true).size(); ++j) {
            ServerPlayer serverplayer = serverLevel.getPlayers(serverPlayer -> true).get(j);
            if (sendParticles(serverLevel, serverplayer, true, p_8769_, p_8770_, p_8771_, clientboundlevelparticlespacket)) {
                ++i;
            }
        }

        return i;
    }

    public static boolean sendParticles(ServerLevel serverLevel, ServerPlayer p_8637_, boolean p_8638_, double p_8639_, double p_8640_, double p_8641_, Packet<?> p_8642_) {
        if (p_8637_.level() != serverLevel) {
            return false;
        } else {
            BlockPos blockpos = p_8637_.blockPosition();
            if (blockpos.closerToCenterThan(new Vec3(p_8639_, p_8640_, p_8641_), p_8638_ ? 512.0D : 32.0D)) {
                p_8637_.connection.send(p_8642_);
                return true;
            } else {
                return false;
            }
        }
    }

    public static void addParticlesAroundMiddleSelf(ServerLevel serverLevel, ParticleOptions particleOptions, Entity entity){
        for(int i = 0; i < 5; ++i) {
            double d0 = serverLevel.getRandom().nextGaussian() * 0.02D;
            double d1 = serverLevel.getRandom().nextGaussian() * 0.02D;
            double d2 = serverLevel.getRandom().nextGaussian() * 0.02D;
            sendAlwaysVisibleParticles(serverLevel, particleOptions, entity.getRandomX(1.0D), entity.getRandomY(), entity.getRandomZ(1.0D), 0, d0, d1, d2, 0.5F);
        }
    }

    public static void attractionCloud(ServerLevel serverLevel, LivingEntity livingEntity) {
        serverLevel.sendParticles(CHParticleTypes.ATTRACTION_CLOUD.get(), livingEntity.getX(), livingEntity.getY() + (livingEntity.getBbHeight() + 0.5F), livingEntity.getZ(), 1, 0, 0, 0, 0);
        for(int j2 = 0; j2 < 16; ++j2) {
            float f11 = MathHelper.modelDegrees(j2 * 22.5F);
            double d18 = Mth.cos(f11);
            double d24 = 0.0D;
            double d29 = Mth.sin(f11);
            serverLevel.sendParticles(CHParticleTypes.ATTRACTION_SMOKE.get(), (double)livingEntity.getX() + d18 * 0.1D, (double)livingEntity.getY() + (livingEntity.getBbHeight() / 4.0F), (double)livingEntity.getZ() + d29 * 0.1D, 0, d18, d24, d29, 0.25F);
        }
    }

    public static void quakeCloud(ServerLevel serverLevel, LivingEntity livingEntity) {
        serverLevel.sendParticles(CHParticleTypes.QUAKE_SMOKE.get(), livingEntity.getX(), livingEntity.getY() + (livingEntity.getBbHeight() + 0.5F), livingEntity.getZ(), 1, 0, 0, 0, 0);
        for(int j2 = 0; j2 < 16; ++j2) {
            float f11 = MathHelper.modelDegrees(j2 * 22.5F);
            double d18 = Mth.cos(f11);
            double d24 = 0.0D;
            double d29 = Mth.sin(f11);
            serverLevel.sendParticles(CHParticleTypes.QUAKE_SMOKE.get(), (double)livingEntity.getX() + d18 * 0.1D, (double)livingEntity.getY() + (livingEntity.getBbHeight() / 4.0F), (double)livingEntity.getZ() + d29 * 0.1D, 0, d18, d24, d29, 0.25F);
        }
    }

    public static void shootParticle(ServerLevel serverLevel, LivingEntity livingEntity, boolean leftArm, double y, ParticleOptions options) {
        double x = MobUtil.getHorizontalRightLookAngle(livingEntity).x;
        double z = MobUtil.getHorizontalRightLookAngle(livingEntity).z;
        if (leftArm) {
            x = MobUtil.getHorizontalLeftLookAngle(livingEntity).x;
            z = MobUtil.getHorizontalLeftLookAngle(livingEntity).z;
        }
        x *= livingEntity.getBbWidth();
        z *= livingEntity.getBbWidth();
        Vec3 vec3 = livingEntity.position().add(x, 0, z);
        serverLevel.sendParticles(options, vec3.x, y, vec3.z, 1, 0, 0, 0, 0);
    }

    public static void outerCircleParticles(ServerLevel serverLevel, ParticleOptions particleOptions, Entity entity, float radius, double vx, double vy, double vz){
        float f6 = serverLevel.getRandom().nextFloat() * ((float) Math.PI * 2F);
        float f7 = Mth.sqrt(serverLevel.getRandom().nextFloat()) * radius;
        float f8 = Mth.cos(f6) * f7;
        float f9 = Mth.sin(f6) * f7;
        serverLevel.sendParticles(particleOptions, entity.getX() + (double) f8, entity.getY() + 0.5, entity.getZ() + (double) f9, 0, vx, vy, vz, 1.0F);
    }

    public static void outerCircleParticles(ServerLevel serverLevel, ParticleOptions particleOptions, Entity entity, float radius){
        float f6 = serverLevel.getRandom().nextFloat() * ((float) Math.PI * 2F);
        float f7 = Mth.sqrt(serverLevel.getRandom().nextFloat()) * radius;
        float f8 = Mth.cos(f6) * f7;
        float f9 = Mth.sin(f6) * f7;
        serverLevel.sendParticles(particleOptions, entity.getX() + (double) f8, entity.getY() + 0.5, entity.getZ() + (double) f9, 0, 0, 0, 0, 0.5F);
    }

    public static void circularParticles(ServerLevel serverLevel, ParticleOptions particleOptions, Entity entity, float radius){
        circularParticles(serverLevel, particleOptions, entity.getX(), entity.getY(), entity.getZ(), radius);
    }

    public static void circularParticles(ServerLevel serverLevel, ParticleOptions particleOptions, Entity entity, double xSpeed, double ySpeed, double zSpeed, float radius){
        circularParticles(serverLevel, particleOptions, entity.getX(), entity.getY(), entity.getZ(), xSpeed, ySpeed, zSpeed, radius);
    }

    public static void circularParticles(ServerLevel serverLevel, ParticleOptions particleOptions, double x, double y, double z, float radius){
        circularParticles(serverLevel, particleOptions, x, y, z, 0, 0, 0, radius);
    }

    public static void circularParticles(ServerLevel serverLevel, ParticleOptions particleOptions, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed, float radius){
        float f5 = (float) Math.PI * radius * radius;
        for (int k1 = 0; (float) k1 < f5; ++k1) {
            float f6 = serverLevel.getRandom().nextFloat() * ((float) Math.PI * 2F);
            float f7 = Mth.sqrt(serverLevel.getRandom().nextFloat()) * radius;
            float f8 = Mth.cos(f6) * f7;
            float f9 = Mth.sin(f6) * f7;
            serverLevel.sendParticles(particleOptions, x + (double) f8, y, z + (double) f9, 0, xSpeed, ySpeed, zSpeed, 0.5F);
        }
    }
}
