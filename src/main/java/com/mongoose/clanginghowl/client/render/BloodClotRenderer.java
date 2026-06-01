package com.mongoose.clanginghowl.client.render;

import com.mongoose.clanginghowl.ClangingHowl;
import com.mongoose.clanginghowl.client.render.model.BloodClotModel;
import com.mongoose.clanginghowl.common.entities.hostiles.BloodClot;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class BloodClotRenderer<T extends BloodClot> extends MobRenderer<T, BloodClotModel<T>> {
    private static final ResourceLocation TEXTURE = ClangingHowl.location("textures/entity/blood_clot.png");

    public BloodClotRenderer(EntityRendererProvider.Context p_174403_) {
        super(p_174403_, new BloodClotModel<>(p_174403_.bakeLayer(CHModelLayer.BLOOD_CLOT)), 0.0F);
    }

    public ResourceLocation getTextureLocation(T p_116009_) {
        return TEXTURE;
    }
}
