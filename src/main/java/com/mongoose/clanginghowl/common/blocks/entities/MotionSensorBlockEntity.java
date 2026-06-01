package com.mongoose.clanginghowl.common.blocks.entities;

import com.mongoose.clanginghowl.common.blocks.MotionSensorBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;

public class MotionSensorBlockEntity extends BlockEntity {
    public MotionSensorBlockEntity(BlockPos pos, BlockState state) {
        super(CHBlockEntities.MOTION_SENSOR.get(), pos, state);
    }

    public void tick() {
        if (this.level != null) {
            if (this.level.getGameTime() % 4 == 0 && !this.level.isClientSide) {
                if (!(this.getBlockState().getBlock() instanceof MotionSensorBlock)) {
                    return;
                }

                Direction facing = this.getBlockState().getValue(MotionSensorBlock.FACING);
                int range = calcRange(this.level, this.worldPosition, facing);
                AABB bb = new AABB(this.worldPosition).expandTowards(facing.getStepX() * range, facing.getStepY() * range, facing.getStepZ() * range);
                boolean hasEntity = !this.level.getEntitiesOfClass(Entity.class, bb).isEmpty();

                int entityDistance = -1;
                if (hasEntity) {
                    for (int i = 0; i < range; i++) {
                        BlockPos newPos = this.worldPosition.relative(facing, i);
                        if (!this.level.getEntitiesOfClass(LivingEntity.class, new AABB(newPos)).isEmpty()) {
                            entityDistance = i;
                            break;
                        }
                    }
                }

                BlockState newState = this.getBlockState().setValue(MotionSensorBlock.POWER, 0).setValue(MotionSensorBlock.POWERED, false);
                if (hasEntity && entityDistance > -1) {
                    newState = this.getBlockState().setValue(MotionSensorBlock.POWER, 15 - entityDistance).setValue(MotionSensorBlock.POWERED, true);
                }

                if (this.getBlockState() != newState) {
                    this.level.setBlockAndUpdate(this.worldPosition, newState);
                    if (this.getBlockState().getBlock() instanceof MotionSensorBlock block) {
                        block.updateRedstoneNeighbors(this.level, this.worldPosition);
                    }
                }
            }
        }
    }

    public static int calcRange(Level level, BlockPos pos, Direction direction) {
        int i;
        for (i = 0; i <= 14; i++) {
            BlockPos newPos = pos.relative(direction, i);
            if (level.getBlockState(newPos).isViewBlocking(level, pos)) {
                return i - 1;
            }
        }
        return i;
    }
}
