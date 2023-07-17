package com.foxyfine.my_test_mod.items;

import com.foxyfine.my_test_mod.TestMod;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class MaterialItem extends Item {
    private boolean PKM;

    public MaterialItem() {
        super(new Item.Properties().tab(TestMod.MYMOD));
    }
    @SubscribeEvent
    public void tick(TickEvent.PlayerTickEvent event) {
        if (event.player != null) {
            Player player = event.player;
            Level world = player.level;
            int x = (int) player.getX();
            int y = (int) player.getY();
            int z = (int) player.getZ();
            if (PKM) {
                BlockPos blockAtPosition = new BlockPos(x - 1, y - 1, z);
                if (world.getBlockState(blockAtPosition).isAir() || world.getBlockState(blockAtPosition).is(Blocks.WATER) || world.getBlockState(blockAtPosition).is(Blocks.LAVA)) {
                    world.setBlockAndUpdate(blockAtPosition, Blocks.GLASS.defaultBlockState());
                }
            }
        }
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        if (!world.isClientSide) {
            if (!PKM) {
                PKM = true;
                MinecraftForge.EVENT_BUS.register(this);
            } else {
                PKM = false;
                MinecraftForge.EVENT_BUS.unregister(this);
            }
        }
        return new InteractionResultHolder<>(InteractionResult.SUCCESS, itemstack);
    }
}
