package com.foxyfine.my_test_mod.block;
import com.foxyfine.my_test_mod.init.TileRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nullable;

public class AltarBlock extends Block implements EntityBlock {
    public AltarBlock(BlockBehaviour.Properties props) {
        super(props);
    }
    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return TileRegistry.ALTAR_TILE.get().create(pos, state);
    }
}
