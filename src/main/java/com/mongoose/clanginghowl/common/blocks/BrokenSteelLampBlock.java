package com.mongoose.clanginghowl.common.blocks;

import com.mongoose.clanginghowl.common.blocks.entities.BrokenSteelLampBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class BrokenSteelLampBlock extends SteelLampBlock implements EntityBlock {

    public BrokenSteelLampBlock() {
        super();
    }

    public RenderShape getRenderShape(BlockState p_60550_) {
        return RenderShape.MODEL;
    }

    public void neighborChanged(BlockState p_55666_, Level p_55667_, BlockPos p_55668_, Block p_55669_, BlockPos p_55670_, boolean p_55671_) {
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos p_153215_, BlockState p_153216_) {
        return new BrokenSteelLampBlockEntity(p_153215_, p_153216_);
    }

    @Nullable
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level p_152755_, BlockState p_152756_, BlockEntityType<T> p_152757_) {
        return (world, pos, state, blockEntity) -> {
            if (blockEntity instanceof BrokenSteelLampBlockEntity block) {
                block.tick();
            }
        };
    }
}
