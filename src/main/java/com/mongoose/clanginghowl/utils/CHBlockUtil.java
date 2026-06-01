package com.mongoose.clanginghowl.utils;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.DirectionalPlaceContext;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.SlabType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

public class CHBlockUtil {

    public static boolean canBeReplaced(Level pLevel, BlockPos pReplaceablePos){
        return canBeReplaced(pLevel, pReplaceablePos, pReplaceablePos);
    }

    public static boolean canBeReplaced(Level pLevel, BlockPos pReplaceablePos, BlockPos pReplacedBlockPos){
        return pLevel.getBlockState(pReplaceablePos).canBeReplaced(new DirectionalPlaceContext(pLevel, pReplacedBlockPos, Direction.DOWN, ItemStack.EMPTY, Direction.UP));
    }

    public static Iterable<BlockPos> multiBlockBreak(LivingEntity livingEntity, BlockPos blockPos, int x, int y, int z){
        BlockHitResult blockHitResult = MobUtil.rayTrace(livingEntity, 10, false);
        Direction direction = blockHitResult.getDirection();
        boolean hasX = direction.getStepX() == 0;
        boolean hasY = direction.getStepY() == 0;
        boolean hasZ = direction.getStepZ() == 0;
        Vec3i start = new Vec3i(hasX ? -x : 0, hasY ? -y : 0, hasZ ? -z : 0);
        Vec3i end = new Vec3i(hasX ? x : 0, hasY ? (y * 2) - 1 : 0, hasZ ? z : 0);
        return BlockPos.betweenClosed(
                blockPos.offset(start),
                blockPos.offset(end));
    }

    public static boolean areSamePos(BlockPos blockPos1, BlockPos blockPos2) {
        if (blockPos1 == null || blockPos2 == null) {
            return false;
        }
        return blockPos1.getX() == blockPos2.getX() && blockPos1.getY() == blockPos2.getY() && blockPos1.getZ() == blockPos2.getZ();
    }

    public static double moveDownToGround(Entity entity) {
        HitResult rayTrace = rayTrace(entity);
        if (rayTrace.getType() == HitResult.Type.BLOCK) {
            BlockHitResult hitResult = (BlockHitResult) rayTrace;
            if (hitResult.getDirection() == Direction.UP) {
                BlockState hitBlock = entity.level().getBlockState(hitResult.getBlockPos());
                if (hitBlock.getBlock() instanceof SlabBlock && hitBlock.getValue(BlockStateProperties.SLAB_TYPE) == SlabType.BOTTOM) {
                    return hitResult.getBlockPos().getY() + 1.0625F - 0.5F;
                } else {
                    return hitResult.getBlockPos().getY() + 1.0625F;
                }
            }
        }
        return entity.getY();
    }

    private static HitResult rayTrace(Entity entity) {
        Vec3 startPos = new Vec3(entity.getX(), entity.getY(), entity.getZ());
        Vec3 endPos = new Vec3(entity.getX(), 0, entity.getZ());
        return entity.level().clip(new ClipContext(startPos, endPos, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, entity));
    }

    public static Vec3 SummonPosition(Entity entity, Vec3 vec3){
        return SummonPosition(entity.level(), entity, vec3);
    }

    public static Vec3 SummonPosition(Level level, Entity entity, Vec3 vec3){
        double d3 = vec3.y;
        boolean flag = false;
        Vec3 vec31 = new Vec3(vec3.x, vec3.y, vec3.z);
        if (level.isLoaded(BlockPos.containing(vec31))) {
            boolean flag1 = false;

            while(!flag1 && vec31.y > level.getMinBuildHeight()) {
                BlockPos blockpos1 = BlockPos.containing(vec31).below();
                BlockState blockstate = level.getBlockState(blockpos1);
                if (blockstate.blocksMotion()) {
                    flag1 = true;
                } else {
                    --d3;
                    vec31 = blockpos1.getCenter();
                }
            }

            if (flag1) {
                if (level.noCollision(entity) && !level.containsAnyLiquid(entity.getBoundingBox())) {
                    flag = true;
                }
            }
        }
        if (!flag) {
            return vec31;
        } else {
            return new Vec3(vec3.x, d3, vec3.z);
        }
    }

    public static boolean emptySpaceBetween(Level level, BlockPos blockPos, int distance, Direction direction){
        BlockPos.MutableBlockPos blockpos$mutable = blockPos.mutable();
        boolean flag = false;
        if (direction == Direction.UP) {
            while (blockpos$mutable.getY() < blockPos.getY() + distance && level.getBlockState(blockpos$mutable).getCollisionShape(level, blockpos$mutable).isEmpty()){
                blockpos$mutable.move(Direction.UP);
                flag = true;
            }
        } else if (direction == Direction.DOWN) {
            while (blockpos$mutable.getY() > blockPos.getY() - distance && level.getBlockState(blockpos$mutable).getCollisionShape(level, blockpos$mutable).isEmpty()){
                blockpos$mutable.move(Direction.DOWN);
                flag = true;
            }
        } else if (direction == Direction.WEST) {
            while (blockpos$mutable.getX() > blockPos.getX() - distance && level.getBlockState(blockpos$mutable).getCollisionShape(level, blockpos$mutable).isEmpty()){
                blockpos$mutable.move(Direction.WEST);
                flag = true;
            }
        } else if (direction == Direction.EAST) {
            while (blockpos$mutable.getX() < blockPos.getX() + distance && level.getBlockState(blockpos$mutable).getCollisionShape(level, blockpos$mutable).isEmpty()){
                blockpos$mutable.move(Direction.EAST);
                flag = true;
            }
        } else if (direction == Direction.NORTH) {
            while (blockpos$mutable.getZ() > blockPos.getZ() - distance && level.getBlockState(blockpos$mutable).getCollisionShape(level, blockpos$mutable).isEmpty()){
                blockpos$mutable.move(Direction.NORTH);
                flag = true;
            }
        } else if (direction == Direction.SOUTH) {
            while (blockpos$mutable.getZ() < blockPos.getZ() + distance && level.getBlockState(blockpos$mutable).getCollisionShape(level, blockpos$mutable).isEmpty()){
                blockpos$mutable.move(Direction.SOUTH);
                flag = true;
            }
        }
        if (!level.getBlockState(blockpos$mutable).getCollisionShape(level, blockpos$mutable ).isEmpty()){
            flag = false;
        }
        return flag;
    }

}
