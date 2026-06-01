package com.mongoose.clanginghowl.common.entities.hostiles;

import com.mongoose.clanginghowl.init.CHTags;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.phys.AABB;

import java.util.List;

public interface ITFlesh {

    default int getXPReward() {
        return 0;
    }

    default void setXPReward(int xpReward) {
    }

    default boolean canAlert() {
        return true;
    }

    default void alertAllies(){
        if (this.canAlert()) {
            if (this instanceof LivingEntity flesh) {
                LivingEntity attacker = flesh.getLastHurtByMob();
                if (attacker != null) {
                    double d0 = flesh.getAttributeValue(Attributes.FOLLOW_RANGE);
                    AABB aabb = AABB.unitCubeFromLowerCorner(flesh.position()).inflate(d0, 10.0D, d0);
                    List<? extends Mob> list = flesh.level().getEntitiesOfClass(Mob.class, aabb, livingEntity -> EntitySelector.NO_SPECTATORS.test(livingEntity) && livingEntity != flesh && livingEntity.getType().is(CHTags.EntityTypes.TECHNO_FLESH));

                    for (Mob ally : list) {
                        if (ally.getTarget() == null && !ally.isAlliedTo(attacker) && EntitySelector.NO_CREATIVE_OR_SPECTATOR.test(attacker)) {
                            ally.setTarget(attacker);
                        }
                    }
                }
            }
        }
    }
}
