package com.foxyfine.my_test_mod.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

public class TrampolineBlock extends Block {
        public TrampolineBlock(Block.Properties properties) {
            super(properties);
        }
    @Override
        public void stepOn(@NotNull Level world, BlockPos blockPos, BlockState blockState, Entity entity) {
            Vec3 motion = entity.getDeltaMovement();
            entity.setDeltaMovement(motion.x, 3.0F, motion.z);
            super.stepOn(world, blockPos, blockState, entity);
        }
}
