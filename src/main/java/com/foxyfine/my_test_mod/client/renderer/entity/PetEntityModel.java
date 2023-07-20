package com.foxyfine.my_test_mod.client.renderer.entity;

import com.foxyfine.my_test_mod.entity.PetEntity;
import net.minecraft.client.model.RabbitModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.world.entity.animal.Rabbit;

public class PetEntityModel<P extends Rabbit> extends RabbitModel<PetEntity> {
    public PetEntityModel(ModelPart p_170881_) {
        super(p_170881_);
    }
}
