package com.mongoose.clanginghowl.client.particles;

import com.mojang.serialization.Codec;
import com.mongoose.clanginghowl.ClangingHowl;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class CHParticleTypes {
    public static DeferredRegister<ParticleType<?>> PARTICLE_TYPES = DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, ClangingHowl.MOD_ID);

    public static final RegistryObject<SimpleParticleType> CRYSTAL_LUSTER = PARTICLE_TYPES.register("crystal_luster",
            () -> new SimpleParticleType(false));

    public static final RegistryObject<SimpleParticleType> REPAIR = PARTICLE_TYPES.register("repair",
            () -> new SimpleParticleType(false));

    public static final RegistryObject<SimpleParticleType> BREAKDOWN_SMOKE = PARTICLE_TYPES.register("breakdown_smoke",
            () -> new SimpleParticleType(false));

    public static final RegistryObject<SimpleParticleType> OVERDRIVE_FIRE = PARTICLE_TYPES.register("overdrive_fire",
            () -> new SimpleParticleType(false));

    public static final RegistryObject<SimpleParticleType> FLAMETHROWER_FLAME = PARTICLE_TYPES.register("flamethrower_flame",
            () -> new SimpleParticleType(false));

    public static final RegistryObject<SimpleParticleType> FLAMETHROWER_SOUL_FLAME = PARTICLE_TYPES.register("flamethrower_soul_flame",
            () -> new SimpleParticleType(false));

    public static final RegistryObject<SimpleParticleType> FLAMETHROWER_BURST = PARTICLE_TYPES.register("flamethrower_burst",
            () -> new SimpleParticleType(false));

    public static final RegistryObject<SimpleParticleType> SPIT = PARTICLE_TYPES.register("extraterrestrial_spit",
            () -> new SimpleParticleType(false));

    public static final RegistryObject<SimpleParticleType> INFECTION = PARTICLE_TYPES.register("sign_of_infection",
            () -> new SimpleParticleType(false));

    public static final RegistryObject<SimpleParticleType> CRIMSON_POOF = PARTICLE_TYPES.register("crimson_poof",
            () -> new SimpleParticleType(false));

    public static final RegistryObject<SimpleParticleType> ENERGY_DISSOLUTION = PARTICLE_TYPES.register("energy_dissolution",
            () -> new SimpleParticleType(true));

    public static final RegistryObject<SimpleParticleType> ENERGY_PARTICLE = PARTICLE_TYPES.register("energy_particle",
            () -> new SimpleParticleType(true));

    public static final RegistryObject<SimpleParticleType> BLOODY_ENERGY = PARTICLE_TYPES.register("bloody_energy",
            () -> new SimpleParticleType(true));

    public static final RegistryObject<SimpleParticleType> BIG_BLOODY_ENERGY = PARTICLE_TYPES.register("big_bloody_energy",
            () -> new SimpleParticleType(true));

    public static final RegistryObject<SimpleParticleType> GAS_SURGE = PARTICLE_TYPES.register("gas_surge",
            () -> new SimpleParticleType(true));

    public static final RegistryObject<SimpleParticleType> ELECTRIC_SPARK = PARTICLE_TYPES.register("electric_spark",
            () -> new SimpleParticleType(true));

    public static final RegistryObject<SimpleParticleType> METEORITE_TRAIL = PARTICLE_TYPES.register("meteorite_trail",
            () -> new SimpleParticleType(true));

    public static final RegistryObject<SimpleParticleType> METEORITE_SPLIT = PARTICLE_TYPES.register("meteorite_split",
            () -> new SimpleParticleType(true));

    public static final RegistryObject<SimpleParticleType> NEUROTOXIN = PARTICLE_TYPES.register("neurotoxin",
            () -> new SimpleParticleType(false));

    public static final RegistryObject<SimpleParticleType> BLOOD_STAIN = PARTICLE_TYPES.register("blood_stain",
            () -> new SimpleParticleType(false));

    public static final RegistryObject<SimpleParticleType> BLOODY_PROJECTILE = PARTICLE_TYPES.register("bloody_projectile",
            () -> new SimpleParticleType(false));

    public static final RegistryObject<SimpleParticleType> ATTRACTION_CLOUD = PARTICLE_TYPES.register("attraction_cloud",
            () -> new SimpleParticleType(false));

    public static final RegistryObject<SimpleParticleType> ATTRACTION_SMOKE = PARTICLE_TYPES.register("attraction_smoke",
            () -> new SimpleParticleType(false));

    public static final RegistryObject<SimpleParticleType> QUAKE_SMOKE = PARTICLE_TYPES.register("quake_smoke",
            () -> new SimpleParticleType(false));

    public static final RegistryObject<SimpleParticleType> ENERGETIC_EMERGENCE = PARTICLE_TYPES.register("energetic_emergence",
            () -> new SimpleParticleType(false));

    public static final RegistryObject<ParticleType<ElectricSplashParticleOption>> HORIZONTAL_ELECTRICAL_SPLASH = PARTICLE_TYPES.register("horizontal_electrical_splash",
            () -> new ParticleType<>(true, ElectricSplashParticleOption.DESERIALIZER) {
                @Override
                public Codec<ElectricSplashParticleOption> codec() {
                    return ElectricSplashParticleOption.CODEC;
                }
            });

    public static final RegistryObject<ParticleType<ElectricShockParticleOption>> VERTICAL_ELECTRIC_SHOCK = PARTICLE_TYPES.register("vertical_electric_shock",
            () -> new ParticleType<>(false, ElectricShockParticleOption.DESERIALIZER) {
                @Override
                public Codec<ElectricShockParticleOption> codec() {
                    return ElectricShockParticleOption.CODEC;
                }
            });

    public static final RegistryObject<ParticleType<FieryExplosionParticleOption>> FIERY_EXPLOSION = PARTICLE_TYPES.register("fiery_explosion",
            () -> new ParticleType<>(false, FieryExplosionParticleOption.DESERIALIZER) {
                @Override
                public Codec<FieryExplosionParticleOption> codec() {
                    return FieryExplosionParticleOption.CODEC;
                }
            });

    public static final RegistryObject<ParticleType<BloodSplashParticleOption>> BLOOD_SPLASH = PARTICLE_TYPES.register("blood_splash",
            () -> new ParticleType<>(false, BloodSplashParticleOption.DESERIALIZER) {
                @Override
                public Codec<BloodSplashParticleOption> codec() {
                    return BloodSplashParticleOption.CODEC;
                }
            });

    public static final RegistryObject<ParticleType<RotationParticleOption>> ROTATION = PARTICLE_TYPES.register("rotation",
            () -> new ParticleType<>(false, RotationParticleOption.DESERIALIZER) {
                @Override
                public Codec<RotationParticleOption> codec() {
                    return RotationParticleOption.CODEC;
                }
            });

    public static final RegistryObject<ParticleType<SmallFireSplashParticleOption>> SMALL_FIRE_SPLASH = PARTICLE_TYPES.register("small_fire_splash",
            () -> new ParticleType<>(true, SmallFireSplashParticleOption.DESERIALIZER) {
                @Override
                public Codec<SmallFireSplashParticleOption> codec() {
                    return SmallFireSplashParticleOption.CODEC;
                }
            });

    public static final RegistryObject<ParticleType<ShockWaveParticleOption>> SHOCK_WAVE = PARTICLE_TYPES.register("shock_wave",
            () -> new ParticleType<>(true, ShockWaveParticleOption.DESERIALIZER) {
                @Override
                public Codec<ShockWaveParticleOption> codec() {
                    return ShockWaveParticleOption.CODEC;
                }
            });
}
