package com.imeetake.tlib.world;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.material.FluidState;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class TBlockUtils {

    public static boolean isSolid(Level level, BlockPos pos) {
        BlockState state = level.getBlockState(pos);
        return state.isSolidRender() && !state.isAir() && state.getFluidState().isEmpty();
    }

    public static boolean isAirOrFluid(Level level, BlockPos pos) {
        BlockState state = level.getBlockState(pos);
        FluidState fs = state.getFluidState();
        return state.isAir() || !fs.isEmpty();
    }

    public static boolean isLightSource(Level level, BlockPos pos) {
        return level.getBlockState(pos).getLightEmission() > 0;
    }

    public static BlockPos findNearest(Level level, BlockPos center, int radius, Predicate<BlockState> predicate) {
        BlockPos nearest = null;
        double minDist = Double.MAX_VALUE;
        int cx = center.getX(), cy = center.getY(), cz = center.getZ();

        for (int x = cx - radius; x <= cx + radius; x++) {
            for (int y = cy - radius; y <= cy + radius; y++) {
                for (int z = cz - radius; z <= cz + radius; z++) {
                    BlockPos p = new BlockPos(x, y, z);
                    BlockState st = level.getBlockState(p);
                    if (predicate.test(st)) {
                        double d = center.distSqr(p);
                        if (d < minDist) {
                            minDist = d;
                            nearest = p;
                        }
                    }
                }
            }
        }
        return nearest;
    }

    public static boolean isReplaceable(Level level, BlockPos pos) {
        return level.getBlockState(pos).canBeReplaced();
    }

    public static boolean isLiquid(Level level, BlockPos pos) {
        return !level.getBlockState(pos).getFluidState().isEmpty();
    }

    public static boolean isWalkable(Level level, BlockPos pos) {
        BlockState st = level.getBlockState(pos);
        Block b = st.getBlock();
        return st.isSolidRender()
                && st.getFluidState().isEmpty()
                && !st.canBeReplaced()
                && b != Blocks.FIRE;
    }

    public static BlockPos getBlockAbove(BlockPos pos) {
        return pos.above();
    }

    public static BlockPos getBlockBelow(BlockPos pos) {
        return pos.below();
    }

    public static BlockPos traceVertical(Level level, BlockPos start, Direction dir, Predicate<BlockState> stopAt) {
        BlockPos.MutableBlockPos m = start.mutable();
        while (level.isInWorldBounds(m)) {
            m.move(dir);
            if (stopAt.test(level.getBlockState(m))) {
                return m.immutable();
            }
        }
        return null;
    }

    public static List<BlockPos> getNearbyBlocks(Level level, BlockPos center, int radius, Predicate<BlockState> predicate) {
        List<BlockPos> result = new ArrayList<>();
        int cx = center.getX(), cy = center.getY(), cz = center.getZ();

        for (int x = cx - radius; x <= cx + radius; x++) {
            for (int y = cy - radius; y <= cy + radius; y++) {
                for (int z = cz - radius; z <= cz + radius; z++) {
                    BlockPos p = new BlockPos(x, y, z);
                    BlockState st = level.getBlockState(p);
                    if (predicate.test(st)) {
                        result.add(p);
                    }
                }
            }
        }
        return result;
    }

    public static int getSurfaceHeight(Level level, int x, int z) {
        return level.getHeight(Heightmap.Types.WORLD_SURFACE_WG, x, z);
    }
}
