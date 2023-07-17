package com.foxyfine.my_test_mod;

import com.foxyfine.my_test_mod.init.BlocksRegistry;
import com.foxyfine.my_test_mod.init.EntityRegistry;
import com.foxyfine.my_test_mod.init.ItemsRegistry;
import com.foxyfine.my_test_mod.init.TileRegistry;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegisterEvent;

import java.util.function.Supplier;

@Mod("test_mod")
@Mod.EventBusSubscriber(modid = TestMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class TestMod {
    public static final CreativeModeTab MYMOD = new CreativeModeTab("mymod") {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(ItemsRegistry.EXCALIBUR.get());
        }
    };
    public static final String MOD_ID = "test_mod";
    @SubscribeEvent
    public static void onRegisterItems(final RegisterEvent event) {
        if (event.getRegistryKey().equals(ForgeRegistries.Keys.ITEMS)){
            BlocksRegistry.BLOCKS.getEntries().forEach( (blockRegistryObject) -> {
                Block block = blockRegistryObject.get();
                Item.Properties properties = new Item.Properties().tab(TestMod.MYMOD);
                Supplier<Item> blockItemFactory = () -> new BlockItem(block, properties);
                event.register(ForgeRegistries.Keys.ITEMS, blockRegistryObject.getId(), blockItemFactory);
            });
        }
    }
    public TestMod() {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::doClientStuff);

        MinecraftForge.EVENT_BUS.register(this);
        BlocksRegistry.BLOCKS.register(FMLJavaModLoadingContext.get().getModEventBus());
        ItemsRegistry.ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
        TileRegistry.TILE_ENTITY_TYPES.register(FMLJavaModLoadingContext.get().getModEventBus());
        EntityRegistry.ENTITY_TYPES.register(FMLJavaModLoadingContext.get().getModEventBus());
    }

    private void setup(final net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent event) {
    }

    private void doClientStuff(final net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent event) {
    }
}
