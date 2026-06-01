package com.mongoose.clanginghowl.common.blocks.entities;

import com.mongoose.clanginghowl.common.blocks.ExActivityRadarBlock;
import com.mongoose.clanginghowl.common.blocks.MotionSensorBlock;
import com.mongoose.clanginghowl.common.world.data.ICHWorldData;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class ExActivityRadarBlockEntity extends BlockEntity {
    public ExActivityRadarBlockEntity(BlockPos pos, BlockState state) {
        super(CHBlockEntities.EXTRATERRESTRIAL_ACTIVITY_RADAR.get(), pos, state);
    }

    public void tick() {
        if (this.level != null) {
            if (!this.level.isClientSide) {
                if (this.level instanceof ICHWorldData data) {
                    if (this.level.getGameTime() % 4 == 0) {
                        if (!(this.getBlockState().getBlock() instanceof ExActivityRadarBlock)) {
                            return;
                        }

                        BlockState newState = this.getBlockState().setValue(MotionSensorBlock.POWERED, false);
                        if (data.getCHWorldData().isMeteorShower()) {
                            newState = this.getBlockState().setValue(MotionSensorBlock.POWERED, true);
                        }

                        if (this.getBlockState() != newState) {
                            this.level.setBlockAndUpdate(this.worldPosition, newState);
                        }
                    }
                }
            }
        }
    }
}
