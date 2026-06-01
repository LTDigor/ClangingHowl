package com.mongoose.clanginghowl.init;

import com.mongoose.clanginghowl.ClangingHowl;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class CHSounds {
    public static DeferredRegister<SoundEvent> SOUNDS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, ClangingHowl.MOD_ID);

    public static void init(){
        SOUNDS.register(FMLJavaModLoadingContext.get().getModEventBus());
    }

    public static final RegistryObject<SoundEvent> TECHNO_FLESH_IDLE = create("techno_flesh_idle");
    public static final RegistryObject<SoundEvent> TECHNO_FLESH_HURT = create("techno_flesh_hurt");
    public static final RegistryObject<SoundEvent> TECHNO_FLESH_ATTACK = create("techno_flesh_attack");
    public static final RegistryObject<SoundEvent> TECHNO_FLESH_STEP = create("techno_flesh_step");
    public static final RegistryObject<SoundEvent> TECHNO_FLESH_DEATH = create("techno_flesh_death");

    public static final RegistryObject<SoundEvent> HEMATOMA_STEP = create("hematoma_step");

    public static final RegistryObject<SoundEvent> PROWLER_STEP = create("prowler_step");

    public static final RegistryObject<SoundEvent> CARCASS_SPIT = create("carcass_spit");
    public static final RegistryObject<SoundEvent> CARCASS_QUAKE = create("carcass_quake");
    public static final RegistryObject<SoundEvent> CARCASS_STEP = create("carcass_step");

    public static final RegistryObject<SoundEvent> HEART_OF_DECAY_DEATH = create("heart_of_decay_death");

    public static final RegistryObject<SoundEvent> FLESH_WHIPPING = create("flesh_whipping");
    public static final RegistryObject<SoundEvent> FLESH_MAIDEN_DEATH = create("flesh_maiden_death");

    public static final RegistryObject<SoundEvent> INJECT = create("inject");
    public static final RegistryObject<SoundEvent> INFECT = create("infect");

    public static final RegistryObject<SoundEvent> FLESH_RUPTURE_BEGINNING = create("flesh_rupture_beginning");
    public static final RegistryObject<SoundEvent> FLESH_RUPTURE_ENDING = create("flesh_rupture_ending");
    public static final RegistryObject<SoundEvent> FLESH_TEAR = create("flesh_tear");

    public static final RegistryObject<SoundEvent> SMALL_METEORITE_LANDING = create("small_meteorite_landing");

    public static final RegistryObject<SoundEvent> SMOKE_RELEASE = create("smoke_release");
    public static final RegistryObject<SoundEvent> HOWL_OF_TECHNOFLESH = create("howl_of_technoflesh");

    public static final RegistryObject<SoundEvent> DRILLING = create("drilling");

    public static final RegistryObject<SoundEvent> CHAINSAW_IDLE = create("chainsaw_idle");
    public static final RegistryObject<SoundEvent> CHAINSAW_CUT = create("chainsaw_cut");
    public static final RegistryObject<SoundEvent> CHAINSAW_BLOW = create("chainsaw_blow");
    public static final RegistryObject<SoundEvent> CHAINSAW_DISCHARGED = create("chainsaw_discharged");
    public static final RegistryObject<SoundEvent> CHAINSAW_OVERDRIVE = create("chainsaw_overdrive");

    public static final RegistryObject<SoundEvent> FLAMETHROWER_ACTIVATION = create("flamethrower_activation");
    public static final RegistryObject<SoundEvent> FLAMETHROWER_BURNS = create("flamethrower_burns");
    public static final RegistryObject<SoundEvent> FLAMETHROWER_EMISSION = create("flamethrower_emission");

    public static final RegistryObject<SoundEvent> X_RAY_ACTIVATION = create("x_ray_activation");

    public static final RegistryObject<SoundEvent> ENERGY_BARRIER_IMPACT = create("energy_barrier_impact");

    public static final RegistryObject<SoundEvent> DISCHARGED = create("discharged");
    public static final RegistryObject<SoundEvent> ELECTRIC_SHOCK = create("electric_shock");

    public static final RegistryObject<SoundEvent> MALFUNCTIONING_ELECTRICS = create("malfunctioning_electrics");

    public static final RegistryObject<SoundEvent> CONSUMMATE_NEST_AMBIENT = create("consummate_nest_ambient");
    public static final RegistryObject<SoundEvent> CONSUMMATE_NEST_SPAWN = create("consummate_nest_spawn");
    public static final RegistryObject<SoundEvent> CONSUMMATE_NEST_OPEN = create("consummate_nest_open");
    public static final RegistryObject<SoundEvent> CONSUMMATE_NEST_CLOSE = create("consummate_nest_close");
    public static final RegistryObject<SoundEvent> CONSUMMATE_NEST_EJECT_ITEM = create("consummate_nest_eject_item");

    public static final RegistryObject<SoundEvent> METEOR_SHOWER_FLY = create("meteor_shower_fly");
    public static final RegistryObject<SoundEvent> METEOR_SHOWER_EXPLODE = create("meteor_shower_explode");
    public static final RegistryObject<SoundEvent> METEOR_SHOWER_MUSIC = create("meteor_shower_music");

    static RegistryObject<SoundEvent> create(String name) {
        SoundEvent event = SoundEvent.createVariableRangeEvent(ClangingHowl.location(name));
        return SOUNDS.register(name, () -> event);
    }
}
