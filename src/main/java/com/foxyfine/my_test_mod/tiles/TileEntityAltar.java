package com.foxyfine.my_test_mod.tiles;

import com.foxyfine.my_test_mod.init.TileRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class TileEntityAltar extends BlockEntity {
    public TileEntityAltar(BlockPos pos, BlockState state) {
        super(TileRegistry.ALTAR_TILE.get(), pos, state);
    }
}
