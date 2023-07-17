package com.foxyfine.my_test_mod.init;

import com.foxyfine.my_test_mod.TestMod;
import com.foxyfine.my_test_mod.items.SwordExcalibur;
import com.foxyfine.my_test_mod.items.MaterialItem;

import net.minecraft.world.item.Item;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ItemsRegistry {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, TestMod.MOD_ID);

    public static final RegistryObject<Item> EXCALIBUR = ITEMS.register("excalibur", MaterialItem::new);
    public static final RegistryObject<Item> EXCALIBUR_SWORD = ITEMS.register("magic_sword", SwordExcalibur::new);
}
