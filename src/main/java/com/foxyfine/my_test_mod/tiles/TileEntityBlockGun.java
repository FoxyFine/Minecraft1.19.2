package com.foxyfine.my_test_mod.tiles;

import com.foxyfine.my_test_mod.init.TileRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Snowball;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraft.util.Mth;


import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class TileEntityBlockGun extends BlockEntity {
    public TileEntityBlockGun(BlockPos pos, BlockState state) {
        super(TileRegistry.GUN_TILE.get(), pos, state);
    }
    private int timer = 0;
    private boolean isActive = true;
    private static final int RANGE = 10;

    @Override
    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return null;
    }

    @Override
    public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket pkt) {
    }

    private void hurtMobs() {
        BlockPos topCorner = this.getBlockPos().offset(RANGE, RANGE, RANGE);
        BlockPos bottomCorner = this.getBlockPos().offset(-RANGE, -RANGE, -RANGE);
        AABB axisalignedbb = new AABB(topCorner, bottomCorner);
        List<LivingEntity> list = this.level.getEntitiesOfClass(LivingEntity.class, axisalignedbb);
        list = list.stream()
                .sorted(Comparator.comparingDouble(entity -> entity.position().distanceToSqr(Vec3.atCenterOf(this.getBlockPos()))))
                .collect(Collectors.toList());
        LivingEntity entity = null;
        for (LivingEntity target : list) {
            if (target == null || !target.isAlive())
                continue;

            entity = target;
            break;
        }

        if (entity != null && !(entity instanceof Player)) {
            BlockPos pos = this.getBlockPos();
            double x = pos.getX();
            double y = pos.getY();
            double z = pos.getZ();
            Snowball snowball = new Snowball(this.level, x, y, z);
            Vec3 vec3d = entity.getEyePosition(1.0F);
            Vec3 vec3d1 = snowball.position();
            double d0 = vec3d.x() - vec3d1.x();
            double d1 = vec3d.y() - vec3d1.y();
            double d2 = vec3d.z() - vec3d1.z();
            float f = Mth.sqrt((float) (d0 * d0 + d2 * d2)) * 0.2F;
            snowball.shoot(d0, d1 + f, d2, 1.6F, 12.0F);
            this.level.addFreshEntity(snowball);
        }
    }

    public void toggle() {
        this.isActive = !this.isActive;
    }

    public static <T extends BlockEntity> void tick(Level level, BlockPos pos, BlockState state, T be) {
        TileEntityBlockGun tile = (TileEntityBlockGun) be;
        if (!level.isClientSide() && tile.isActive) {
            tile.timer++;
            if (tile.timer > 5) {
                tile.timer = 0;
                tile.hurtMobs();
            }
        }
    }

    @Override
    public void saveAdditional(CompoundTag nbt) {
        super.saveAdditional(nbt);
        nbt.putBoolean("active", this.isActive);
    }
    @Override
    public void load(CompoundTag nbt) {
        super.load(nbt);
        this.isActive = nbt.getBoolean("active");
    }
}