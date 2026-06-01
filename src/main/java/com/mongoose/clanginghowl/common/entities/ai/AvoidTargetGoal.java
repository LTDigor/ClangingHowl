package com.mongoose.clanginghowl.common.entities.ai;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;

import java.util.function.Predicate;

public class AvoidTargetGoal<T extends LivingEntity> extends AvoidEntityGoal<T> {

    public AvoidTargetGoal(PathfinderMob mob, Class<T> tClass, Predicate<LivingEntity> predicate, float radius, double minSpeed, double maxSpeed) {
        super(mob, tClass, predicate, radius, minSpeed, maxSpeed, livingEntity -> mob.getTarget() == livingEntity);
    }

    public AvoidTargetGoal(PathfinderMob mob, Class<T> tClass, float radius, double minSpeed, double maxSpeed) {
        super(mob, tClass, radius, minSpeed, maxSpeed, livingEntity -> mob.getTarget() == livingEntity);
    }

    public static AvoidTargetGoal<LivingEntity> newGoal(PathfinderMob pathfinderMob, float radius, double minSpeed, double maxSpeed){
        return new AvoidTargetGoal<>(pathfinderMob, LivingEntity.class, radius, minSpeed, maxSpeed);
    }

    @Override
    public boolean canContinueToUse() {
        return super.canContinueToUse() && this.toAvoid != null && this.toAvoid.distanceTo(this.mob) < this.maxDist;
    }

    public void stop() {
        super.stop();
        this.pathNav.stop();
    }
}