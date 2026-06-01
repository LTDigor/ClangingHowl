package com.mongoose.clanginghowl.common.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class SteelRodBlock extends Block implements SimpleWaterloggedBlock {
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    protected static final VoxelShape Y_AXIS_AABB = Block.box(7D, 0.0D, 7D, 9D, 16.0D, 9D);
    protected static final VoxelShape Z_AXIS_AABB = Block.box(7D, 7D, 0.0D, 9D, 9D, 16.0D);
    protected static final VoxelShape X_AXIS_AABB = Block.box(0.0D, 7D, 7D, 16.0D, 9D, 9D);
    protected static final VoxelShape Y_Z_AXIS_AABB = Shapes.or(Y_AXIS_AABB, Z_AXIS_AABB);
    protected static final VoxelShape Y_X_AXIS_AABB = Shapes.or(Y_AXIS_AABB, X_AXIS_AABB);
    protected static final VoxelShape X_Z_AXIS_AABB = Shapes.or(X_AXIS_AABB, Z_AXIS_AABB);
    protected static final VoxelShape X_Y_Z_AXIS_AABB = Shapes.or(X_AXIS_AABB, Y_AXIS_AABB, Z_AXIS_AABB);

    public static final BooleanProperty AXIS_X = CHBlockStates.AXIS_X;
    public static final BooleanProperty AXIS_Y = CHBlockStates.AXIS_Y;
    public static final BooleanProperty AXIS_Z = CHBlockStates.AXIS_Z;

    public static final Map<Direction.Axis, BooleanProperty> AXIS2PROPERTY =
            Map.of(Direction.Axis.X, AXIS_X, Direction.Axis.Y, AXIS_Y, Direction.Axis.Z, AXIS_Z);

    public SteelRodBlock(BlockBehaviour.Properties p_53085_) {
        super(p_53085_);
        this.registerDefaultState(this.stateDefinition.any().setValue(WATERLOGGED, Boolean.FALSE).setValue(AXIS_Y, true).setValue(AXIS_X, false).setValue(AXIS_Z, false));
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter reader, BlockPos pos, CollisionContext context) {
        boolean x = state.getValue(AXIS_X);
        boolean y = state.getValue(AXIS_Y);
        boolean z = state.getValue(AXIS_Z);
        return getStickShape(x, y, z);
    }

    public static VoxelShape getStickShape(boolean x, boolean y, boolean z) {
        if (x) {
            if (y) {
                if (z) return X_Y_Z_AXIS_AABB;
                return Y_X_AXIS_AABB;
            } else if (z) return X_Z_AXIS_AABB;
            return X_AXIS_AABB;
        }
        if (z) {
            if (y) return Y_Z_AXIS_AABB;
            return Z_AXIS_AABB;
        }
        return Y_AXIS_AABB;
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        BlockState blockstate = context.getLevel().getBlockState(context.getClickedPos());
        BooleanProperty axis = AXIS2PROPERTY.get(context.getClickedFace().getAxis());
        if (blockstate.is(this)) {
            return blockstate.setValue(axis, true);
        } else {
            return super.getStateForPlacement(context).setValue(AXIS_Y, false).setValue(axis, true);
        }
    }

    @Override
    public boolean canBeReplaced(BlockState state, BlockPlaceContext context) {
        if (!context.isSecondaryUseActive() && context.getItemInHand().is(this.asItem())) {
            BooleanProperty axis = AXIS2PROPERTY.get(context.getClickedFace().getAxis());
            if (!state.getValue(axis)) {
                return true;
            }
        }
        return super.canBeReplaced(state, context);
    }

    private static boolean isVertical(BlockState state) {
        return state.getValue(AXIS_Y) && !state.getValue(AXIS_X) && !state.getValue(AXIS_Z);
    }

    public BlockState applyRotationLock(Level world, BlockPos blockPos, BlockState state, Direction dir, int half) {
        int i = 0;
        if (state.getValue(AXIS_X)) i++;
        if (state.getValue(AXIS_Y)) i++;
        if (state.getValue(AXIS_Z)) i++;
        if (i == 1) state.setValue(AXIS_Z, false).setValue(AXIS_X, false)
                .setValue(AXIS_Y, false).setValue(AXIS2PROPERTY.get(dir.getAxis()), true);
        return state;
    }

    public Optional<BlockState> getRotatedState(BlockState state, LevelAccessor world, BlockPos pos, Rotation rotation, Direction axis, @Nullable Vec3 hit) {
        if (rotation == Rotation.CLOCKWISE_180) {
            return Optional.empty();
        }
        boolean x = state.getValue(AXIS_X);
        boolean y = state.getValue(AXIS_Y);
        boolean z = state.getValue(AXIS_Z);
        BlockState newState = switch (axis.getAxis()) {
            case Y -> state.setValue(AXIS_X, z).setValue(AXIS_Z, x);
            case X -> state.setValue(AXIS_Y, z).setValue(AXIS_Z, y);
            case Z -> state.setValue(AXIS_X, y).setValue(AXIS_Y, x);
        };
        if (newState != state) {
            return Optional.of(newState);
        }
        return Optional.empty();
    }

    @Override
    public BlockState rotate(BlockState state, Rotation rotation) {
        return switch (rotation) {
            case NONE, CLOCKWISE_180 -> state;
            case CLOCKWISE_90, COUNTERCLOCKWISE_90 -> state
                    .setValue(AXIS_X, state.getValue(AXIS_Z))
                    .setValue(AXIS_Z, state.getValue(AXIS_X));
        };
    }

    @Override
    public BlockState mirror(BlockState state, Mirror mirror) {
        return state;
    }

    @Override
    public List<ItemStack> getDrops(BlockState state, LootParams.Builder pBuilder) {
        int i = 0;
        if (state.getValue(AXIS_X)) {
            i++;
        }
        if (state.getValue(AXIS_Y)) {
            i++;
        }
        if (state.getValue(AXIS_Z)) {
            i++;
        }
        return List.of(new ItemStack(this.asItem(), i));
    }

    public BlockState updateShape(BlockState p_153739_, Direction p_153740_, BlockState p_153741_, LevelAccessor p_153742_, BlockPos p_153743_, BlockPos p_153744_) {
        if (p_153739_.getValue(WATERLOGGED)) {
            p_153742_.scheduleTick(p_153743_, Fluids.WATER, Fluids.WATER.getTickDelay(p_153742_));
        }

        return super.updateShape(p_153739_, p_153740_, p_153741_, p_153742_, p_153743_, p_153744_);
    }

    public FluidState getFluidState(BlockState p_153759_) {
        return p_153759_.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(p_153759_);
    }

    public boolean isPathfindable(BlockState p_154341_, BlockGetter p_154342_, BlockPos p_154343_, PathComputationType p_154344_) {
        return false;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(WATERLOGGED, AXIS_X, AXIS_Y, AXIS_Z);
    }
}
