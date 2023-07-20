package com.foxyfine.my_test_mod.client.renderer;

import com.foxyfine.my_test_mod.tiles.TileEntityPedestal;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemModelShaper;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.world.item.ItemStack;


public class TileEntityPedestalRenderer implements BlockEntityRenderer<TileEntityPedestal> {
    private final ItemRenderer itemRenderer;

    public TileEntityPedestalRenderer(BlockEntityRendererProvider.Context context) {
        this.itemRenderer = context.getItemRenderer();
    }

    @Override
    public void render(TileEntityPedestal tileEntity, float partialTicks, PoseStack matrixStack, MultiBufferSource buffer, int combinedLight, int combinedOverlay) {
        if (!tileEntity.getDisplayedItem().isEmpty()) {
            ItemStack displayedItem = tileEntity.getDisplayedItem();
            BakedModel model = this.itemRenderer.getItemModelShaper().getItemModel(displayedItem);
            matrixStack.pushPose();
            matrixStack.translate(0.5, 1.0, 0.5);
            matrixStack.mulPose(Vector3f.YP.rotationDegrees(tileEntity.rotation));
            matrixStack.scale(0.5f, 0.5f, 0.5f);
            this.itemRenderer.render(displayedItem, ItemTransforms.TransformType.GROUND, false, matrixStack, buffer, combinedLight, combinedOverlay, model);
            matrixStack.popPose();
        }
    }
}