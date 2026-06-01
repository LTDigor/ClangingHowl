package com.mongoose.clanginghowl.mixin;

import com.mongoose.clanginghowl.common.capabilities.CHCapHelper;
import com.mongoose.clanginghowl.common.items.CHItems;
import com.mongoose.clanginghowl.common.items.curios.XRayGoggles;
import com.mongoose.clanginghowl.utils.CHCuriosFinder;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import javax.annotation.Nullable;

@Mixin(Minecraft.class)
public class MinecraftMixin {

    @Shadow @Nullable public LocalPlayer player;

    @Inject(method = "shouldEntityAppearGlowing", at = @At(value = "HEAD"), cancellable = true)
    public void shouldEntityAppearGlowing(Entity pEntity, CallbackInfoReturnable<Boolean> cir) {
        Minecraft minecraft = Minecraft.getInstance();
        LocalPlayer player = minecraft.player;
        if (player != null) {
            if (pEntity instanceof LivingEntity livingEntity) {
                if (CHCapHelper.isEnlightened(livingEntity)) {
                    ItemStack curio = CHCuriosFinder.findCurio(player, CHItems.X_RAY_GOGGLES.get());
                    if (!curio.isEmpty() && XRayGoggles.isActivated(curio)) {
                        cir.setReturnValue(true);
                    }
                }
            }
        }
    }
}
