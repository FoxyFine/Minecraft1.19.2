package com.foxyfine.my_test_mod.client.renderer;

import com.foxyfine.my_test_mod.tiles.TileEntityPedestal;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.world.item.ItemStack;


public class TileEntityPedestalRenderer implements BlockEntityRenderer<TileEntityPedestal> {

    private final BlockEntityRenderDispatcher blockRenderer;
    public TileEntityPedestalRenderer(BlockEntityRendererProvider.Context pContext) {
        blockRenderer = pContext.getBlockEntityRenderDispatcher();
    }

    @Override
    public void render(TileEntityPedestal tileEntity, float partialTicks, PoseStack matrixStack, MultiBufferSource buffer, int combinedLight, int combinedOverlay) {
            if (tileEntity.getDisplayedItem() == null || tileEntity.getDisplayedItem().isEmpty()) {
                return;
            }
        ItemStack itemStack = tileEntity.getDisplayedItem();
            matrixStack.pushPose();
            matrixStack.translate(0.5D, 0.5D, 0.5D);

            ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();

            BakedModel model = itemRenderer.getModel(itemStack, tileEntity.getLevel(), null,0);

            matrixStack.scale(0.5f, 0.5f, 0.5f);
            matrixStack.translate(0.5, 1.0, 0.5);
            matrixStack.mulPose(Vector3f.YP.rotationDegrees(tileEntity.rotation));

            itemRenderer.render(itemStack, ItemTransforms.TransformType.FIXED, false, matrixStack, buffer, combinedLight, combinedOverlay, model);
            matrixStack.popPose();
    }
}