package com.foxyfine.my_test_mod.init;

import com.foxyfine.my_test_mod.TestMod;
import com.foxyfine.my_test_mod.entity.PetEntity;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ObjectHolder;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraft.world.entity.EntityType;
import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.registries.ForgeRegistries;

@Mod.EventBusSubscriber(modid = TestMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class EntityRegistry {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, TestMod.MOD_ID);

    public static final RegistryObject<EntityType<PetEntity>> PET_ENTITY = ENTITY_TYPES.register("pet_entity",
            () -> EntityType.Builder.of(PetEntity::new, MobCategory.MONSTER)
                    .sized(1.0F, 2.0F)
                    .fireImmune()
                    .updateInterval(1)
                    .build(TestMod.MOD_ID + ":pet_entity")
    );

    @SubscribeEvent
    public static void newEntityAttributes(EntityAttributeCreationEvent event) {
        event.put(PET_ENTITY.get(), PetEntity.createAttributes().build());
    }
}







