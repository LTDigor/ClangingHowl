package com.mongoose.clanginghowl.common.blocks.entities;

import com.mongoose.clanginghowl.client.particles.CHParticleTypes;
import com.mongoose.clanginghowl.client.particles.ElectricSplashParticleOption;
import com.mongoose.clanginghowl.common.blocks.ExBarrierBlock;
import com.mongoose.clanginghowl.common.entities.projectiles.SmallMeteorite;
import com.mongoose.clanginghowl.utils.ParticleUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;

public class ExBarrierBlockEntity extends BlockEntity {

    public ExBarrierBlockEntity(BlockPos p_155229_, BlockState p_155230_) {
        super(CHBlockEntities.BARRIER_OF_EXTRATERRESTRIAL_ACTIVITY.get(), p_155229_, p_155230_);
    }

    public void tick() {
        if (this.level != null) {
            if (this.level instanceof ServerLevel serverLevel) {
                if (this.getBlockState().getValue(ExBarrierBlock.ENABLED)) {
                    for (SmallMeteorite entity : this.level.getEntitiesOfClass(SmallMeteorite.class, new AABB(this.worldPosition).inflate(128.0D))) {
                        ParticleUtil.addParticlesAroundMiddleSelf(serverLevel, CHParticleTypes.ENERGY_DISSOLUTION.get(), entity);
                        ParticleUtil.sendAlwaysVisibleParticles(serverLevel, new ElectricSplashParticleOption(2.0F, 0), entity.getX(), entity.getY() + 0.5D, entity.getZ(), 1, 0.0D, 0.0D, 0.0D, 0.0D);
                        entity.discard();
                    }
                }
            }
        }
    }
}
