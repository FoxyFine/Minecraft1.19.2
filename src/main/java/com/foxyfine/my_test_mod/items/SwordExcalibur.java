package com.foxyfine.my_test_mod.items;

import com.foxyfine.my_test_mod.TestMod;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.ArrayList;
import java.util.List;
public class SwordExcalibur extends SwordItem {

    public SwordExcalibur(Tier tier, int attackDamage, float attackSpeed) {
        super(tier, attackDamage, attackSpeed, new Properties().stacksTo(1).tab(TestMod.MYMOD));
    }

    public SwordExcalibur() {
        this(Tiers.NETHERITE, 8, -2.4F);
    }

    @Override
    public boolean isFoil(ItemStack stack) {
        return true;
    }
    @SubscribeEvent
    public void onPlayerInteract(PlayerInteractEvent.LeftClickBlock event) {
        System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAa");
        Player player = event.getEntity();
        Level level = player.getLevel();
        BlockPos blockPos = event.getPos();
        InteractionHand hand = event.getHand();
        ItemStack itemStack = player.getItemInHand(hand);

        if (itemStack.getItem() == this) {
            if (level.isClientSide) {
                return;
            }

            // Ищем руду в радиусе 10 блоков от кликнутого блока
            List<BlockPos> orePositions = findOrePositions(level, blockPos, 10);

            // Вскапываем и роняем руду перед игроком
            for (BlockPos pos : orePositions) {
                BlockState state = level.getBlockState(pos);
                Block block = state.getBlock();

                if (state.isAir()) {
                    continue; // Пропускаем пустые позиции
                }

                List<ItemStack> drops = Block.getDrops(state, (ServerLevel) level, pos, null); // Получаем список дропа

                // Удаляем блок с сохранением дропа
                level.destroyBlock(pos, false, null);

                // Дропаем каждый предмет из списка
                for (ItemStack stack : drops) {
                    level.addFreshEntity(new ItemEntity(level, pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5, stack)); // Создаем и добавляем предмет в мир
                }
            }

            event.setCancellationResult(InteractionResult.SUCCESS);
            event.setCanceled(true);
        }
    }

    private List<BlockPos> findOrePositions(Level world, BlockPos centerPos, int radius) {
        List<BlockPos> orePositions = new ArrayList<>();
        for (int x = -radius; x <= radius; x++) {
            for (int y = -radius; y <= radius; y++) {
                for (int z = -radius; z <= radius; z++) {
                    BlockPos pos = centerPos.offset(x, y, z);
                    Block block = world.getBlockState(pos).getBlock();
                    // Проверяем, является ли блок рудой (здесь можно добавить свою логику для определения руды)
                    if (block == Blocks.IRON_ORE || block == Blocks.GOLD_ORE || block == Blocks.DIAMOND_ORE) {
                        orePositions.add(pos);
                    }
                }
            }
        }
        return orePositions;
    }
}
