package com.mongoose.clanginghowl.common.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;

public class RedstoneSteelLampBlock extends SteelLampBlock {
    public static final IntegerProperty TRIGGER = CHBlockStates.TRIGGER;

    public RedstoneSteelLampBlock() {
        super(Properties.of()
                .strength(1.0F, 1.0F)
                .sound(SoundType.COPPER)
                .lightLevel(l -> l.getValue(ENABLED) ? 13 : 0)
                .noOcclusion());
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(WATERLOGGED, false)
                .setValue(ENABLED, true)
                .setValue(FACING, Direction.UP)
                .setValue(TRIGGER, 0));
    }

    public void onPlace(BlockState p_55724_, Level p_55725_, BlockPos p_55726_, BlockState p_55727_, boolean p_55728_) {
        for (Direction direction : Direction.values()) {
            p_55725_.updateNeighborsAt(p_55726_.relative(direction), this);
        }
    }

    public void onRemove(BlockState p_55706_, Level p_55707_, BlockPos p_55708_, BlockState p_55709_, boolean p_55710_) {
        if (!p_55710_) {
            for(Direction direction : Direction.values()) {
                p_55707_.updateNeighborsAt(p_55708_.relative(direction), this);
            }
        }
    }

    protected boolean hasNeighborSignal(Level p_55748_, BlockPos p_55749_, BlockState p_55750_) {
        Direction direction = p_55750_.getValue(FACING).getOpposite();
        return p_55748_.hasSignal(p_55749_.relative(direction), direction);
    }

    public void neighborChanged(BlockState blockState, Level level, BlockPos blockPos, Block block, BlockPos fromPos, boolean isMoving) {
        if (this.hasNeighborSignal(level, blockPos, blockState)) {
            if (blockState.getValue(TRIGGER) > 0) {
                level.setBlock(blockPos, blockState.setValue(TRIGGER, Math.max(0, blockState.getValue(TRIGGER) - 1)), 3);
            }
        }
        if (blockState.getValue(TRIGGER) <= 0 && blockState.getValue(ENABLED) == this.hasNeighborSignal(level, blockPos, blockState) && !level.getBlockTicks().willTickThisTick(blockPos, this)) {
            level.scheduleTick(blockPos, this, 2);
        }
    }

    public void tick(BlockState p_221949_, ServerLevel p_221950_, BlockPos p_221951_, RandomSource p_221952_) {
        boolean flag = this.hasNeighborSignal(p_221950_, p_221951_, p_221949_);

        if (p_221949_.getValue(ENABLED)) {
            if (flag) {
                p_221950_.setBlock(p_221951_, p_221949_.setValue(ENABLED, Boolean.FALSE), 3);
            }
        } else if (!flag) {
            p_221950_.setBlock(p_221951_, p_221949_.setValue(ENABLED, Boolean.TRUE), 3);
        }

    }

    @Override
    public int getSignal(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, Direction direction) {
        return blockState.getValue(ENABLED) && blockState.getValue(FACING).getOpposite() != direction ? 15 : 0;
    }

    @Override
    public int getDirectSignal(BlockState blockState, BlockGetter level, BlockPos blockPos, Direction direction) {
        return direction == blockState.getValue(FACING).getOpposite() ? getSignal(blockState, level, blockPos, direction) : 0;
    }

    public boolean isSignalSource(BlockState p_55730_) {
        return true;
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> p_55673_) {
        p_55673_.add(WATERLOGGED, ENABLED, FACING, TRIGGER);
    }
}
