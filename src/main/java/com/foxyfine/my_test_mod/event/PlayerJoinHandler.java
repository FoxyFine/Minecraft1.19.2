package com.foxyfine.my_test_mod.event;

import com.foxyfine.my_test_mod.entity.PetEntity;
import com.foxyfine.my_test_mod.init.EntityRegistry;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;

public class PlayerJoinHandler {
    public static void onPlayerJoin(ServerPlayer player) {
        if (player.getLevel().isClientSide()) {
            return; // Выходим, если код выполняется на клиентской стороне
        }
        Level world = player.getCommandSenderWorld();

        // Получение объекта EntityType<PetEntity> из PET_ENTITY
        EntityType<PetEntity> entityType = EntityRegistry.PET_ENTITY.get();

        // Создание экземпляра сущности PetEntity
        PetEntity petEntity = new PetEntity(entityType, world);

        // Установка владельца существа
        petEntity.setOwner(player);

        // Установка позиции существа возле владельца
        petEntity.setPos(player.getX(), player.getY(), player.getZ());

        // Добавление сущности в мир
        world.addFreshEntity(petEntity);
    }
}
