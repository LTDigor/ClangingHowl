package com.mongoose.clanginghowl.common.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.MapColor;

import java.util.ArrayDeque;
import java.util.HashSet;
import java.util.Queue;
import java.util.Set;

public class RedstoneCableDistributorBlock extends Block {
    public static final IntegerProperty POWER = BlockStateProperties.POWER;

    public RedstoneCableDistributorBlock() {
        super(BlockBehaviour.Properties.of()
                .sound(SoundType.NETHERITE_BLOCK)
                .mapColor(MapColor.COLOR_GRAY)
                .requiresCorrectToolForDrops()
                .strength(2.0F, 35.0F));
        this.registerDefaultState(this.defaultBlockState().setValue(POWER, 0));
    }

    private int calculateIncomingPower(Level level, BlockPos blockPos) {
        int power = 0;
        for (Direction direction : Direction.values()) {
            BlockPos neighborPos = blockPos.relative(direction);
            BlockState neighborState = level.getBlockState(neighborPos);

            if (neighborState.getBlock() instanceof RedstoneCableBlock) {
                Direction.Axis cableAxis = neighborState.getValue(RedstoneCableBlock.AXIS);
                if (direction.getAxis() != cableAxis) continue;
                int traced = findLiveSourcePower(level, neighborPos, cableAxis, blockPos);
                power = Math.max(power, traced);
                continue;
            }

            if (neighborState.getBlock() instanceof RedstoneCableDistributorBlock) continue;

            int signal = neighborState.getSignal(level, neighborPos, direction.getOpposite());
            power = Math.max(power, signal);
        }
        return power;
    }

    private int findLiveSourcePower(Level level, BlockPos startPos, Direction.Axis axis, BlockPos excludePos) {
        int power = 0;
        Set<BlockPos> visited = new HashSet<>();
        Queue<BlockPos> queue = new ArrayDeque<>();
        queue.add(startPos);
        visited.add(startPos);

        while (!queue.isEmpty()) {
            BlockPos current = queue.poll();
            BlockState currentState = level.getBlockState(current);

            if (!(currentState.getBlock() instanceof RedstoneCableBlock)) continue;
            if (currentState.getValue(RedstoneCableBlock.AXIS) != axis) continue;

            for (Direction direction : Direction.values()) {
                if (direction.getAxis() != axis) continue;
                BlockPos neighborPos = current.relative(direction);

                if (neighborPos.equals(excludePos)) continue;
                if (visited.contains(neighborPos)) continue;

                BlockState neighborState = level.getBlockState(neighborPos);

                if (neighborState.getBlock() instanceof RedstoneCableBlock
                        && neighborState.getValue(RedstoneCableBlock.AXIS) == axis) {
                    visited.add(neighborPos);
                    queue.add(neighborPos);
                    continue;
                }

                if (neighborState.getBlock() instanceof RedstoneCableDistributorBlock) continue;

                int signal = neighborState.getSignal(level, neighborPos, direction.getOpposite());
                power = Math.max(power, signal);
            }
        }
        return power;
    }

    @Override
    public int getSignal(BlockState blockState, BlockGetter getter, BlockPos blockPos, Direction direction) {
        int ourPower = blockState.getValue(POWER);
        if (ourPower == 0) return 0;
        if (!(getter instanceof Level level)) return ourPower;

        BlockPos neighborPos = blockPos.relative(direction.getOpposite());
        BlockState neighborState = level.getBlockState(neighborPos);

        if (neighborState.getBlock() instanceof RedstoneCableBlock) {
            Direction.Axis cableAxis = neighborState.getValue(RedstoneCableBlock.AXIS);
            if (direction.getAxis() == cableAxis) {
                int liveSource = findLiveSourcePower(level, neighborPos, cableAxis, blockPos);
                if (liveSource > 0) return 0;
            }
            return ourPower;
        }

        if (!(neighborState.getBlock() instanceof RedstoneCableDistributorBlock)) {
            int incoming = neighborState.getSignal(level, neighborPos, direction);
            if (incoming > 0) return 0;
        }

        return ourPower;
    }

    @Override
    public int getDirectSignal(BlockState blockState, BlockGetter level, BlockPos blockPos, Direction direction) {
        return getSignal(blockState, level, blockPos, direction);
    }

    @Override
    public boolean isSignalSource(BlockState blockState) {
        return true;
    }

    private void updatePower(BlockState blockState, Level level, BlockPos blockPos) {
        int newPower = calculateIncomingPower(level, blockPos);
        if (newPower != blockState.getValue(POWER)) {
            level.setBlock(blockPos, blockState.setValue(POWER, newPower), 3);
            for (Direction direction : Direction.values()) {
                level.updateNeighborsAt(blockPos.relative(direction), this);
            }
        }
    }

    @Override
    public void neighborChanged(BlockState blockState, Level level, BlockPos blockPos,
                                Block block, BlockPos fromPos, boolean isMoving) {
        if (!level.isClientSide) {
            updatePower(blockState, level, blockPos);
        }
    }

    @Override
    public void onPlace(BlockState blockState, Level level, BlockPos blockPos,
                        BlockState oldState, boolean isMoving) {
        if (!level.isClientSide) {
            updatePower(blockState, level, blockPos);
        }
    }

    @Override
    public void onRemove(BlockState blockState, Level level, BlockPos blockPos,
                         BlockState newState, boolean isMoving) {
        if (!isMoving && blockState.getBlock() != newState.getBlock()) {
            for (Direction direction : Direction.values()) {
                level.updateNeighborsAt(blockPos.relative(direction), this);
            }
        }
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(POWER);
    }
}