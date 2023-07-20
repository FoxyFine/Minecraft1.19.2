package com.foxyfine.my_test_mod.client.renderer.entity;

import com.foxyfine.my_test_mod.entity.PetEntity;
import net.minecraft.client.model.RabbitModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.RabbitRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class PetEntityRenderer extends MobRenderer<PetEntity, RabbitModel<PetEntity>> {
    private static final ResourceLocation TEXTURE = new ResourceLocation("test_mod", "textures/entity/pet_entity.png");

    public PetEntityRenderer(EntityRendererProvider.Context context) {
        super(context, new RabbitModel<>(context.bakeLayer(ModelLayers.RABBIT)), 0.5f);
    }

    @Override
    public ResourceLocation getTextureLocation(PetEntity entity) {
        return TEXTURE;
    }

    public static class RenderFactory implements EntityRendererProvider {
        @Override
        public EntityRenderer<PetEntity> create(EntityRendererProvider.Context context) {
            return new PetEntityRenderer(context);
        }
    }
}

