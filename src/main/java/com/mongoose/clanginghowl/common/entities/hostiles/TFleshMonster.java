package com.mongoose.clanginghowl.common.entities.hostiles;

import com.mongoose.clanginghowl.init.CHTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;

public abstract class TFleshMonster extends Monster implements ITFlesh {

    public TFleshMonster(EntityType<? extends Monster> p_33002_, Level p_33003_) {
        super(p_33002_, p_33003_);
    }

    @Override
    public int getXPReward() {
        return this.xpReward;
    }

    @Override
    public void setXPReward(int xpReward) {
        this.xpReward = xpReward;
    }

    @Override
    public boolean isAlliedTo(Entity entity) {
        if (entity == null) {
            return false;
        } else if (entity == this) {
            return true;
        } else if (super.isAlliedTo(entity)) {
            return true;
        } else if (entity.getType().is(CHTags.EntityTypes.TECHNO_FLESH)) {
            return this.getTeam() == null && entity.getTeam() == null;
        } else {
            return false;
        }
    }

    @Override
    public boolean hurt(DamageSource p_21016_, float p_21017_) {
        boolean flag = super.hurt(p_21016_, p_21017_);
        if (flag) {
            this.alertAllies();
        }
        return flag;
    }
}
