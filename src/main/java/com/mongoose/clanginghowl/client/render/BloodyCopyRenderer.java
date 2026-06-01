package com.mongoose.clanginghowl.client.render;

import com.mongoose.clanginghowl.ClangingHowl;
import com.mongoose.clanginghowl.client.render.model.BloodSpreaderModel;
import com.mongoose.clanginghowl.common.entities.hostiles.BloodyCopy;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

import javax.annotation.Nullable;

public class BloodyCopyRenderer<T extends BloodyCopy> extends MobRenderer<T, BloodSpreaderModel<T>> {

    public BloodyCopyRenderer(EntityRendererProvider.Context p_174403_) {
        super(p_174403_, new BloodSpreaderModel<>(p_174403_.bakeLayer(CHModelLayer.BLOOD_SPREADER)), 0.0F);
    }

    public ResourceLocation getTextureLocation(T entity) {
        int frame = Mth.floor((entity.tickCount % 20) / 2.0F) + 1;
        return ClangingHowl.location("textures/entity/blood_spreader/copy/" + frame + ".png");
    }

    @Nullable
    protected RenderType getRenderType(T p_115322_, boolean p_115323_, boolean p_115324_, boolean p_115325_) {
        ResourceLocation resourcelocation = this.getTextureLocation(p_115322_);
        if (p_115324_) {
            return RenderType.itemEntityTranslucentCull(resourcelocation);
        } else if (p_115323_) {
            return RenderType.entityTranslucent(resourcelocation);
        } else {
            return p_115325_ ? RenderType.outline(resourcelocation) : null;
        }
    }
}
