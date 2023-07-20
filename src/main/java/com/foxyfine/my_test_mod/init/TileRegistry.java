package com.foxyfine.my_test_mod.init;

import com.foxyfine.my_test_mod.TestMod;
import com.foxyfine.my_test_mod.tiles.TileEntityAltar;
import com.foxyfine.my_test_mod.tiles.TileEntityBlockGun;
import com.foxyfine.my_test_mod.tiles.TileEntityPedestal;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class TileRegistry {
    public static final DeferredRegister<BlockEntityType<?>> TILE_ENTITY_TYPES =
            DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, TestMod.MOD_ID);

    public static final RegistryObject<BlockEntityType<TileEntityBlockGun>> GUN_TILE =
            TILE_ENTITY_TYPES.register("gun_tile",
                    () -> BlockEntityType.Builder.of(TileEntityBlockGun::new, BlocksRegistry.GUN_BLOCK.get()).build(null));
    public static final RegistryObject<BlockEntityType<TileEntityPedestal>> PEDESTAL_TILE =
            TILE_ENTITY_TYPES.register("pedestal_tile",
                    () -> BlockEntityType.Builder.of(TileEntityPedestal::new, BlocksRegistry.PEDESTAL_BLOCK.get()).build(null));
    public static final RegistryObject<BlockEntityType<TileEntityAltar>> ALTAR_TILE =
            TILE_ENTITY_TYPES.register("altar_tile",
                    () -> BlockEntityType.Builder.of(TileEntityAltar::new, BlocksRegistry.ALTAR_BLOCK.get()).build(null));
}