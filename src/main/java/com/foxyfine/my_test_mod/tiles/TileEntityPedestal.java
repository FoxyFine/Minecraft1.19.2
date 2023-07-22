package com.foxyfine.my_test_mod.tiles;

import com.foxyfine.my_test_mod.init.TileRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class TileEntityPedestal extends BlockEntity {
    public TileEntityPedestal(BlockPos pos, BlockState state) {
        super(TileRegistry.PEDESTAL_TILE.get(), pos, state);
    }
    public float rotation = 0.0f;
    private static final float ROTATION_SPEED = 2.0f; // Скорость вращения предмета (можно изменить по желанию)
    private ItemStack displayedItem = ItemStack.EMPTY;
    // Получение предмета, который отображается на вершине блока
    public ItemStack getDisplayedItem() {
        System.out.println("getDisplayedItem: " + displayedItem);
        return displayedItem;
    }
    public static void tick(Level level, BlockPos pos, BlockState state, TileEntityPedestal blockEntity) {
            blockEntity.rotation += ROTATION_SPEED;
            if (blockEntity.rotation >= 360.0f) {
                blockEntity.rotation -= 360.0f;
            }
    }
    public void setDisplayedItem(ItemStack itemStack) {
        displayedItem = itemStack;
        // Отправить пакет синхронизации на клиенты
        BlockState state = level.getBlockState(worldPosition);
        level.sendBlockUpdated(worldPosition, state, state, 2);
        System.out.println("setDisplayedItem setDisplayedItem setDisplayedItem: " + displayedItem);
    }
    @Override
    public void saveAdditional(CompoundTag compound) {
        super.saveAdditional(compound);
        if (!displayedItem.isEmpty()) {
            CompoundTag itemStack = new CompoundTag();
            displayedItem.save(itemStack);
            compound.put("DisplayedItem", itemStack);
        }
        System.out.println("Saved displayedItem: " + displayedItem);
    }
    @Override
    public void load(CompoundTag compound) {
        super.load(compound);
        if (compound.contains("DisplayedItem")) {
            displayedItem = ItemStack.of(compound.getCompound("DisplayedItem"));
        }
        System.out.println("Loaded displayedItem: " + displayedItem);
    }
    @Override
    public CompoundTag getUpdateTag() {
        CompoundTag compound = super.getUpdateTag();
        saveAdditional(compound);
        return compound;
    }
    @Override
    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this, BlockEntity::getUpdateTag);
    }
    @Override
    public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket pkt) {
        load(pkt.getTag()); // Загрузите данные из пакета и обновите вашу TileEntity на клиентской стороне
    }
}
