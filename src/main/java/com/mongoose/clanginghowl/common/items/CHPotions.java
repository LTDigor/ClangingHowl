package com.mongoose.clanginghowl.common.items;

import com.mongoose.clanginghowl.ClangingHowl;
import com.mongoose.clanginghowl.common.effects.CHEffects;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class CHPotions {
    public static DeferredRegister<Potion> POTIONS = DeferredRegister.create(ForgeRegistries.POTIONS, ClangingHowl.MOD_ID);

    public static void init(){
        CHPotions.POTIONS.register(FMLJavaModLoadingContext.get().getModEventBus());
    }

    public static final RegistryObject<Potion> ATTRACTION = POTIONS.register("attraction", () -> new Potion(new MobEffectInstance(CHEffects.ATTRACTION.get(), 1800)));
    public static final RegistryObject<Potion> LONG_ATTRACTION = POTIONS.register("long_attraction", () -> new Potion("attraction", new MobEffectInstance(CHEffects.ATTRACTION.get(), 3600)));
}
