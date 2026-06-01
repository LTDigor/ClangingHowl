package com.mongoose.clanginghowl.common.blocks;

import com.mongoose.clanginghowl.common.blocks.entities.consummate_nest.ConsummateNestState;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;

public class CHBlockStates {
    public static final BooleanProperty OVERGROWN = BooleanProperty.create("overgrown");
    public static final BooleanProperty ALTERNATE = BooleanProperty.create("alternate");
    public static final BooleanProperty AXIS_X = BooleanProperty.create("axis_x");
    public static final BooleanProperty AXIS_Y = BooleanProperty.create("axis_y");
    public static final BooleanProperty AXIS_Z = BooleanProperty.create("axis_z");
    public static final IntegerProperty TRIGGER = IntegerProperty.create("trigger", 0, 2);
    public static final EnumProperty<ConsummateNestState> CONSUMMATE_NEST_STATE = EnumProperty.create("consummate_nest_state", ConsummateNestState.class);
}
