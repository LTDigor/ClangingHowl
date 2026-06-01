package com.mongoose.clanginghowl.mixin;

import com.mongoose.clanginghowl.common.capabilities.CHCapHelper;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Entity.class)
public class EntityMixin {

    @Inject(method = {"getTeamColor()I"}, at = @At(value = "HEAD"), cancellable = true)
    public void getTeamColor(CallbackInfoReturnable<Integer> cir) {
        Entity entity = (Entity) (Object) this;
        if (entity instanceof LivingEntity livingEntity) {
            if (CHCapHelper.isEnlightened(livingEntity)) {
                cir.setReturnValue(0xb9e25b);
            }
        }
    }
}
