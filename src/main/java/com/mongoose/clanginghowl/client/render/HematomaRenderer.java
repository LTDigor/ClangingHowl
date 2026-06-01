package com.mongoose.clanginghowl.client.render;

import com.mongoose.clanginghowl.ClangingHowl;
import com.mongoose.clanginghowl.client.render.model.HematomaModel;
import com.mongoose.clanginghowl.common.entities.hostiles.Hematoma;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.EyesLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;

public class HematomaRenderer<T extends Hematoma> extends MobRenderer<T, HematomaModel<T>> {
    private static final ResourceLocation TEXTURE = ClangingHowl.location("textures/entity/hematoma.png");

    public HematomaRenderer(EntityRendererProvider.Context p_174403_) {
        super(p_174403_, new HematomaModel<>(p_174403_.bakeLayer(CHModelLayer.HEMATOMA)), 0.0F);
        this.addLayer(new HematomaGlowLayer<>(this));
    }

    public ResourceLocation getTextureLocation(T p_116009_) {
        return TEXTURE;
    }

    public static class HematomaGlowLayer<T extends LivingEntity> extends EyesLayer<T, HematomaModel<T>> {
        private static final RenderType GLOW = RenderType.eyes(ClangingHowl.location("textures/entity/hematoma_e.png"));

        public HematomaGlowLayer(RenderLayerParent<T, HematomaModel<T>> p_116964_) {
            super(p_116964_);
        }

        public RenderType renderType() {
            return GLOW;
        }
    }
}
