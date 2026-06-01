package com.mongoose.clanginghowl.mixin;

import com.mongoose.clanginghowl.common.items.CHItems;
import com.mongoose.clanginghowl.common.items.curios.XRayGoggles;
import com.mongoose.clanginghowl.utils.CHCuriosFinder;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(GameRenderer.class)
public class GameRendererMixin {

    @Inject(method = "getNightVisionScale", at = @At("HEAD"), cancellable = true)
    private static void getNightVisionScale(LivingEntity entity, float gamma, CallbackInfoReturnable<Float> cir) {
        if (!(entity instanceof Player player)) {
            return;
        }

        ItemStack stack = CHCuriosFinder.findCurio(player, CHItems.X_RAY_GOGGLES.get());
        if (!(stack.getItem() instanceof XRayGoggles) || !XRayGoggles.isActivated(stack)) {
            return;
        }

        cir.setReturnValue(1.0F);
    }
}
