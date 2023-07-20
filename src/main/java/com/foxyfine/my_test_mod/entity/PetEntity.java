package com.foxyfine.my_test_mod.entity;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.RandomSwimmingGoal;
import net.minecraft.world.entity.animal.Rabbit;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkHooks;

import java.util.UUID;

public class PetEntity extends Rabbit {
    private Player targetPlayer;
    public PetEntity(EntityType<? extends Rabbit> entityType, Level level) {
        super(entityType, level);
    }
    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 100.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.25D);
    }
    public void setOwner(Player player) {
        this.targetPlayer = player;
    }
    public Player getOwner() {
        return targetPlayer;
    }
    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(1, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(2, new RandomSwimmingGoal(this, 1.0D, 10));

    }
    @Override
    public void tick() {
        super.tick();
        if (!this.level.isClientSide) {
            double distance = this.distanceTo(targetPlayer); // Расстояние между питомцем и игроком
            if (distance <= 3.0) {
                // Ходить рандомно
                double posX = this.getX() + (this.getRandom().nextDouble() - 0.5) * 2.0; // Случайное смещение по оси X
                double posY = this.getY(); // Поддерживаем текущую высоту
                double posZ = this.getZ() + (this.getRandom().nextDouble() - 0.5) * 2.0; // Случайное смещение по оси Z
                this.getNavigation().moveTo(posX, posY, posZ, 1.0); // Двигаться к случайной позиции
            }
            else {
                if (distance > 20.0) {
                    double playerX = targetPlayer.getX();
                    double playerY = targetPlayer.getY();
                    double playerZ = targetPlayer.getZ();
                    this.teleportTo(playerX, playerY, playerZ); // Телепортируем питомца к игроку
                }
                else {
                    if (targetPlayer != null) {
                        this.getNavigation().moveTo(targetPlayer, 1.0);
                    }
                }
            }
        }
    }
    public void setTargetPlayer(Player player) {
        this.targetPlayer = player;
    }
    @Override
    public boolean hurt(DamageSource source, float amount) {
        return false; // Существо не получает урон
    }
    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);

        if (compound.contains("Owner", 10)) {
            CompoundTag playerTag = compound.getCompound("Owner");
            UUID playerId = UUID.fromString(playerTag.getString("Id"));
            targetPlayer = this.level.getServer().getPlayerList().getPlayer(playerId);
        }
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);

        if (targetPlayer != null) {
            CompoundTag playerTag = new CompoundTag();
            targetPlayer.saveWithoutId(playerTag);
            compound.put("Owner", playerTag);
        }
    }

    @Override
    public Packet<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }
}
