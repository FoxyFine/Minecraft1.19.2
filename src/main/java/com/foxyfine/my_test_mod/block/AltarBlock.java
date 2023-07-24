package com.foxyfine.my_test_mod.block;
import com.foxyfine.my_test_mod.init.ItemsRegistry;
import com.foxyfine.my_test_mod.init.TileRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.entity.player.Player;
import com.foxyfine.my_test_mod.tiles.TileEntityPedestal;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class AltarBlock extends Block implements EntityBlock {
    public AltarBlock(BlockBehaviour.Properties props) {
        super(props);
    }
    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return TileRegistry.ALTAR_TILE.get().create(pos, state);
    }
    private static List<BlockPos> findPedestalsInRange(Level world, BlockPos pos, int radius) {
        List<BlockPos> pedestalPositions = new ArrayList<>();
        for (int dx = -radius; dx <= radius; dx++) {
            for (int dy = -radius; dy <= radius; dy++) {
                for (int dz = -radius; dz <= radius; dz++) {
                    BlockPos checkPos = pos.offset(dx, dy, dz);
                    BlockState checkState = world.getBlockState(checkPos);
                    if (checkState.getBlock() instanceof PedestalBlock) {
                        pedestalPositions.add(checkPos);
                    }
                }
            }
        }
        return pedestalPositions;
    }
    @Override
    public InteractionResult use(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, net.minecraft.world.phys.BlockHitResult hit) {
        if (!world.isClientSide()) {
            System.out.println("USE");
            if (player.isShiftKeyDown()) {
                System.out.println("Shift");
                int block_with_stick = 0;
                // Поиск пьедесталов в радиусе 3 блоков от алтаря
                List<BlockPos> pedestalPositions = findPedestalsInRange(world, pos, 3);
                List<BlockPos> pedestalPositionWithStick = new ArrayList<>();
                // Проверка наличия палки на пьедесталах и выполнение действий
                for (BlockPos pedestalPos : pedestalPositions) {
                    BlockEntity pedestalEntity = world.getBlockEntity(pedestalPos);
                    if (pedestalEntity instanceof TileEntityPedestal) {
                        TileEntityPedestal pedestal = (TileEntityPedestal) pedestalEntity;
                        if (!pedestal.getDisplayedItem().isEmpty() && pedestal.getDisplayedItem().getItem() == Items.STICK) {
                            pedestalPositionWithStick.add(pedestalPos);
                            block_with_stick++;
                        }
                    }
                }
                if (block_with_stick == 3) {
                    for (BlockPos pedestalPositionStick : pedestalPositionWithStick) {
                        BlockEntity pedestalEntityStick = world.getBlockEntity(pedestalPositionStick);
                        TileEntityPedestal pedestalStick = (TileEntityPedestal) pedestalEntityStick;
                        // Удалить палку с пьедестала
                        pedestalStick.setDisplayedItem(ItemStack.EMPTY);
                    }
                    ItemEntity itemEntity = new ItemEntity(world, pos.getX() + 0.5, pos.getY() + 1.0, pos.getZ() + 0.5, new ItemStack(ItemsRegistry.EXCALIBUR_SWORD.get()));
                    itemEntity.setNoGravity(true); // Disable gravity for the item entity
                    itemEntity.setDeltaMovement(0, 0, 0); // Set the motion of the item entity to zero
                    world.addFreshEntity(itemEntity);

                } else {
                    return InteractionResult.FAIL;
                }
                world.playSound(null, pos, SoundEvents.ITEM_FRAME_ADD_ITEM, SoundSource.BLOCKS, 1.0f, 1.0f);
            } else {
                return InteractionResult.FAIL;
            }
        }
        return InteractionResult.SUCCESS;
    }
}
