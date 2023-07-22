package com.foxyfine.my_test_mod.client.renderer;

import com.foxyfine.my_test_mod.tiles.TileEntityPedestal;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LevelRenderer;
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

            matrixStack.scale(0.35F, 0.35F, 0.35F);
            matrixStack.translate(0.0, 2.5, 0.0);
            matrixStack.mulPose(Vector3f.YP.rotationDegrees(tileEntity.rotation));

            // Используйте другой источник света для комбинированного света
            int light = LevelRenderer.getLightColor(tileEntity.getLevel(), tileEntity.getBlockPos().above());

            itemRenderer.render(itemStack, ItemTransforms.TransformType.FIXED, false, matrixStack, buffer, light, combinedOverlay, model);
            matrixStack.popPose();
    }
}