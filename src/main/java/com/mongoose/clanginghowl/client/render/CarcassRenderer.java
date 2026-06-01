package com.mongoose.clanginghowl.client.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mongoose.clanginghowl.ClangingHowl;
import com.mongoose.clanginghowl.client.render.model.CarcassModel;
import com.mongoose.clanginghowl.common.entities.hostiles.Carcass;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Pose;

public class CarcassRenderer<T extends Carcass> extends MobRenderer<T, CarcassModel<T>> {
    private static final ResourceLocation TEXTURE = ClangingHowl.location("textures/entity/carcass.png");

    public CarcassRenderer(EntityRendererProvider.Context p_174403_) {
        super(p_174403_, new CarcassModel<>(p_174403_.bakeLayer(CHModelLayer.CARCASS)), 0.0F);
    }

    @Override
    public void render(T p_115308_, float p_115309_, float p_115310_, PoseStack p_115311_, MultiBufferSource p_115312_, int p_115313_) {
        if (p_115308_.tickCount < 2 && p_115308_.getPose() == Pose.EMERGING) {
            return;
        }
        super.render(p_115308_, p_115309_, p_115310_, p_115311_, p_115312_, p_115313_);
    }

    protected void scale(T p_115681_, PoseStack p_115682_, float p_115683_) {
        int i = p_115681_.getCarcassSize();
        float f = 0.9F + (0.1F * i);
        p_115682_.scale(f, f, f);
    }

    public ResourceLocation getTextureLocation(T p_116009_) {
        return TEXTURE;
    }

}
