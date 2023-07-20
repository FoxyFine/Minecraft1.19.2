package com.foxyfine.my_test_mod.tiles;

import com.foxyfine.my_test_mod.init.TileRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
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
        return displayedItem;
    }
    // Метод, который будет вызываться при каждом обновлении мира
    /*public static void tick(Level level, BlockPos pos, BlockState state, TileEntityPedestal blockEntity) {
        if (!blockEntity.getDisplayedItem().isEmpty() && !level.isClientSide()) {
            blockEntity.rotation += ROTATION_SPEED;
            if (blockEntity.rotation >= 360.0f) {
                blockEntity.rotation -= 360.0f;
            }
            blockEntity.setChanged();
        }
    }*/

    // Установка предмета для отображения на вершине блока
    public void setDisplayedItem(ItemStack itemStack) {
        this.displayedItem = itemStack;
        // Отметить блок-сущность как измененную для сохранения данных
        this.setChanged();
    }

    // В этом методе сохраняем данные сущности в NBT-тег для сохранения/загрузки
    @Override
    public void saveAdditional(CompoundTag compound) {
        super.saveAdditional(compound);
        if (!displayedItem.isEmpty()) {
            compound.put("DisplayedItem", displayedItem.save(new CompoundTag()));
        }
    }

    // В этом методе загружаем данные сущности из NBT-тега после сохранения/загрузки
    @Override
    public void load(CompoundTag compound) {
        super.load(compound);
        if (compound.contains("DisplayedItem")) {
            displayedItem = ItemStack.of(compound.getCompound("DisplayedItem"));
        }
    }
}
