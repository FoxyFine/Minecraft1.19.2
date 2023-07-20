package com.foxyfine.my_test_mod.init;

import com.foxyfine.my_test_mod.TestMod;
import com.foxyfine.my_test_mod.block.AltarBlock;
import com.foxyfine.my_test_mod.block.GunBlock;
import com.foxyfine.my_test_mod.block.PedestalBlock;
import com.foxyfine.my_test_mod.block.TrampolineBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class BlocksRegistry {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, TestMod.MOD_ID);
    /*public static final RegistryObject<Block> TRAMPOLINE = BLOCKS.register("trampoline", TrampolineBlock::new);*/
    public static final RegistryObject<Block> TRAMPOLINE = BLOCKS.register("trampoline",
            () -> new TrampolineBlock(Block.Properties.copy(Blocks.DIRT)));
    public static final RegistryObject<Block> GUN_BLOCK = BLOCKS.register("gun",
            () -> new GunBlock(Block.Properties.copy(Blocks.IRON_BLOCK)));
    public static final RegistryObject<Block> PEDESTAL_BLOCK = BLOCKS.register("pedestal",
            () -> new PedestalBlock(Block.Properties.copy(Blocks.IRON_BLOCK)));
    public static final RegistryObject<Block> ALTAR_BLOCK = BLOCKS.register("altar",
            () -> new AltarBlock(Block.Properties.copy(Blocks.IRON_BLOCK)));
}
