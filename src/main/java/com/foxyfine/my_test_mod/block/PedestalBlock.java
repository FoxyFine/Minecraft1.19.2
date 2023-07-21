package com.foxyfine.my_test_mod.block;

import com.foxyfine.my_test_mod.init.TileRegistry;
import com.foxyfine.my_test_mod.tiles.TileEntityPedestal;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nullable;

public class PedestalBlock extends Block implements EntityBlock {
    public PedestalBlock(BlockBehaviour.Properties props) {
        super(props);
    }
    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new TileEntityPedestal(pos, state);
    }
    ItemEntity displayedItemEntity = null;
    @Override
    public InteractionResult use(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, net.minecraft.world.phys.BlockHitResult hit) {
        if (!world.isClientSide()) {
            ItemStack heldItem = player.getItemInHand(hand);
            TileEntityPedestal tileEntity = (TileEntityPedestal) world.getBlockEntity(pos);
            if (heldItem.isEmpty()) {
                // Если в руке у игрока пустота, забираем предмет с вершины блока, если он там есть
                ItemStack itemStack = tileEntity.getDisplayedItem();
                if (!itemStack.isEmpty()) {
                    player.setItemInHand(hand, itemStack);
                    tileEntity.setDisplayedItem(ItemStack.EMPTY);
                    world.playSound(null, pos, SoundEvents.ITEM_PICKUP, SoundSource.BLOCKS, 1.0f, 1.0f);
                    /*if(displayedItemEntity != null)
                    {
                        removeItemFromWorld(displayedItemEntity);
                    }*/
                    return InteractionResult.SUCCESS;
                }
            } else {
                // Если в руке у игрока есть предмет, кладем его на вершину блока
                if (tileEntity.getDisplayedItem().isEmpty()) {

                    /*ItemStack itemStack = heldItem.copy();
                    itemStack.setCount(1); // Чтобы отображать только один предмет*/

                    ItemStack appleStack = new ItemStack(Items.APPLE);
                    tileEntity.setDisplayedItem(appleStack);

                    /*tileEntity.setDisplayedItem(itemStack);*/

                    heldItem.shrink(1); // Уменьшаем стак в руке на 1 предмет

                    // Создаем новый ItemEntity с предметом, который будет отображаться над блоком
                    /*ItemEntity itemEntity = new ItemEntity(world, pos.getX() + 0.5, pos.getY() + 1.1, pos.getZ() + 0.5, displayedItem);
                    itemEntity.setPickUpDelay(-1); // Устанавливаем флаг pickupDelay в отрицательное значение
                    itemEntity.setNoGravity(true); // Disable gravity for the item entity
                    itemEntity.setDeltaMovement(0, 0, 0); // Set the motion of the item entity to zero
                    world.addFreshEntity(itemEntity); // Добавляем созданный ItemEntity в ми
                    displayedItemEntity = itemEntity;*/

                    world.playSound(null, pos, SoundEvents.ITEM_FRAME_ADD_ITEM, SoundSource.BLOCKS, 1.0f, 1.0f);
               }
            }
        }
        return InteractionResult.SUCCESS;
    }
    // Метод для удаления предмета
    /*public void removeItemFromWorld(ItemEntity itemEntity) {
        if (itemEntity != null && itemEntity.isAlive()) {
            itemEntity.remove(Entity.RemovalReason.DISCARDED);
        }
    }*/
    @Override
    public void onRemove(BlockState state, Level world, BlockPos pos, BlockState newState, boolean isMoving) {
        if (state.getBlock() != newState.getBlock()) {
            TileEntityPedestal tileEntity = (TileEntityPedestal) world.getBlockEntity(pos);
            if (tileEntity != null) {
                // Выпадение предмета при ломании блока
                ItemStack displayedItem = tileEntity.getDisplayedItem();
                if (!displayedItem.isEmpty()) {
                    world.addFreshEntity(new ItemEntity(world, pos.getX() + 0.5, pos.getY() + 1, pos.getZ() + 0.5, displayedItem));
                    /*
                    if(displayedItemEntity != null)
                    {
                        removeItemFromWorld(displayedItemEntity);
                    }*/
                }
            }
        }
        super.onRemove(state, world, pos, newState, isMoving);
    }
    // Реализуем метод getTicker для обработки обновлений блока-сущности (tick updates)
    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level world, BlockState state, BlockEntityType<T> type) {
        return createTickerHelper(type, TileRegistry.PEDESTAL_TILE.get(), TileEntityPedestal::tick);
    }

    // Создаем вспомогательный метод createTickerHelper
    @Nullable
    protected static <E extends BlockEntity, A extends BlockEntity> BlockEntityTicker<A> createTickerHelper(BlockEntityType<A> type, BlockEntityType<E> correctType, BlockEntityTicker<? super E> ticker) {
        return correctType == type ? (BlockEntityTicker<A>) ticker : null;
    }
}
