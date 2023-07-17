package com.foxyfine.my_test_mod.tiles;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public abstract class TileBase extends BlockEntity {
    public TileBase(BlockEntityType<?> tileEntityTypeIn, BlockPos pos, BlockState state) {
        super(tileEntityTypeIn, pos, state);
    }

    public CompoundTag getUpdateTag() {
        return saveWithoutMetadata();
    }

    public abstract ClientboundBlockEntityDataPacket getUpdatePacket();

    public abstract void onDataPacket(Connection net, ClientboundBlockEntityDataPacket pkt);
}