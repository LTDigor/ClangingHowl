package com.mongoose.clanginghowl.common.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class SteelLampBlock extends Block implements SimpleWaterloggedBlock {
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    public static final BooleanProperty ENABLED = BlockStateProperties.ENABLED;
    public static final DirectionProperty FACING = BlockStateProperties.FACING;
    protected static final VoxelShape UP_SHAPE = Block.box(5.0D, 0.0D, 5.0D, 11.0D, 7.0D, 11.0D);
    protected static final VoxelShape DOWN_SHAPE = Block.box(5.0D, 9.0D, 5.0D, 11.0D, 16.0D, 11.0D);
    protected static final VoxelShape NORTH_SHAPE = Block.box(5.0D, 5.0D, 9.0D, 11.0D, 11.0D, 16.0D);
    protected static final VoxelShape SOUTH_SHAPE = Block.box(5.0D, 5.0D, 0.0D, 11.0D, 11.0D, 7.0D);
    protected static final VoxelShape WEST_SHAPE = Block.box(9.0D, 5.0D, 5.0D, 16.0D, 11.0D, 11.0D);
    protected static final VoxelShape EAST_SHAPE = Block.box(0.0D, 5.0D, 5.0D, 7.0D, 11.0D, 11.0D);

    public SteelLampBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(WATERLOGGED, false).setValue(ENABLED, true).setValue(FACING, Direction.UP));
    }

    public SteelLampBlock() {
        this(Properties.of()
                .strength(1.0F, 1.0F)
                .sound(SoundType.COPPER)
                .lightLevel(l -> l.getValue(ENABLED) ? 15 : 0)
                .noOcclusion());
    }

    public float getShadeBrightness(BlockState p_48731_, BlockGetter p_48732_, BlockPos p_48733_) {
        return 1.0F;
    }

    public boolean propagatesSkylightDown(BlockState p_48740_, BlockGetter p_48741_, BlockPos p_48742_) {
        return true;
    }

    public VoxelShape getShape(BlockState p_152021_, BlockGetter p_152022_, BlockPos p_152023_, CollisionContext p_152024_) {
        Direction direction = p_152021_.getValue(FACING);
        switch (direction) {
            case NORTH:
                return NORTH_SHAPE;
            case SOUTH:
                return SOUTH_SHAPE;
            case EAST:
                return EAST_SHAPE;
            case WEST:
                return WEST_SHAPE;
            case DOWN:
                return DOWN_SHAPE;
            case UP:
            default:
                return UP_SHAPE;
        }
    }

    public void neighborChanged(BlockState p_55666_, Level p_55667_, BlockPos p_55668_, Block p_55669_, BlockPos p_55670_, boolean p_55671_) {
        if (!p_55667_.isClientSide) {
            if (p_55667_.hasNeighborSignal(p_55668_)) {
                p_55667_.setBlock(p_55668_, p_55666_.cycle(ENABLED), 2);
            }
        }
    }

    public BlockState updateShape(BlockState p_51157_, Direction p_51158_, BlockState p_51159_, LevelAccessor p_51160_, BlockPos p_51161_, BlockPos p_51162_) {
        if (p_51157_.getValue(WATERLOGGED)) {
            p_51160_.scheduleTick(p_51161_, Fluids.WATER, Fluids.WATER.getTickDelay(p_51160_));
        }

        return super.updateShape(p_51157_, p_51158_, p_51159_, p_51160_, p_51161_, p_51162_);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        FluidState fluidState = context.getLevel().getFluidState(context.getClickedPos());
        boolean waterlogged = fluidState.getType() == Fluids.WATER;
        return this.defaultBlockState()
                .setValue(FACING, context.getClickedFace())
                .setValue(WATERLOGGED, waterlogged);
    }

    public BlockState rotate(BlockState p_152033_, Rotation p_152034_) {
        return p_152033_.setValue(FACING, p_152034_.rotate(p_152033_.getValue(FACING)));
    }

    public BlockState mirror(BlockState p_152030_, Mirror p_152031_) {
        return p_152030_.rotate(p_152031_.getRotation(p_152030_.getValue(FACING)));
    }

    public FluidState getFluidState(BlockState p_152045_) {
        return p_152045_.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(p_152045_);
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> p_55673_) {
        p_55673_.add(WATERLOGGED, ENABLED, FACING);
    }
}
