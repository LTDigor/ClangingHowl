package com.mongoose.clanginghowl.client.render;

import com.mongoose.clanginghowl.ClangingHowl;
import com.mongoose.clanginghowl.client.render.model.BloodSpreaderModel;
import com.mongoose.clanginghowl.common.entities.hostiles.BloodSpreader;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class BloodSpreaderRenderer<T extends BloodSpreader> extends MobRenderer<T, BloodSpreaderModel<T>> {

    public BloodSpreaderRenderer(EntityRendererProvider.Context p_174403_) {
        super(p_174403_, new BloodSpreaderModel<>(p_174403_.bakeLayer(CHModelLayer.BLOOD_SPREADER)), 0.0F);
    }

    public ResourceLocation getTextureLocation(T entity) {
        int frame = Mth.floor((entity.tickCount % 20) / 2.0F) + 1;
        return ClangingHowl.location("textures/entity/blood_spreader/" + frame + ".png");
    }
}
