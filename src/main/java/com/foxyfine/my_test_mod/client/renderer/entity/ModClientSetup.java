package com.foxyfine.my_test_mod.client.renderer.entity;

import com.foxyfine.my_test_mod.TestMod;
import com.foxyfine.my_test_mod.client.renderer.TileEntityPedestalRenderer;
import com.foxyfine.my_test_mod.init.BlocksRegistry;
import com.foxyfine.my_test_mod.init.EntityRegistry;
import com.foxyfine.my_test_mod.init.TileRegistry;
import com.foxyfine.my_test_mod.tiles.TileEntityPedestal;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@OnlyIn(Dist.CLIENT)
@Mod.EventBusSubscriber(modid = TestMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModClientSetup  {
    @SubscribeEvent
    public static void entityRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(EntityRegistry.PET_ENTITY.get(), new PetEntityRenderer.RenderFactory());
    }
    @SubscribeEvent
    public static void registerRenderers(final EntityRenderersEvent.RegisterRenderers event) {
        event.registerBlockEntityRenderer(TileRegistry.PEDESTAL_TILE.get(), TileEntityPedestalRenderer::new);
    }
}