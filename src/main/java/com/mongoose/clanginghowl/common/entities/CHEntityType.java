package com.mongoose.clanginghowl.common.entities;

import com.mongoose.clanginghowl.ClangingHowl;
import com.mongoose.clanginghowl.common.entities.hostiles.*;
import com.mongoose.clanginghowl.common.entities.projectiles.BloodTrailProjectile;
import com.mongoose.clanginghowl.common.entities.projectiles.SmallMeteorite;
import com.mongoose.clanginghowl.common.entities.projectiles.SpitProjectile;
import com.mongoose.clanginghowl.common.entities.utils.CameraShake;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class CHEntityType {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPE = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, ClangingHowl.MOD_ID);

    public static final RegistryObject<EntityType<SpitProjectile>> SPIT_PROJECTILE = register("spit_projectile",
            EntityType.Builder.<SpitProjectile>of(SpitProjectile::new, MobCategory.MISC)
                    .sized(0.25F, 0.25F)
                    .clientTrackingRange(8)
                    .updateInterval(1));

    public static final RegistryObject<EntityType<SmallMeteorite>> SMALL_METEORITE = register("small_meteorite",
            EntityType.Builder.<SmallMeteorite>of(SmallMeteorite::new, MobCategory.MISC)
                    .sized(0.5F, 0.5F)
                    .clientTrackingRange(4));

    public static final RegistryObject<EntityType<BloodTrailProjectile>> BLOOD_TRAIL = register("blood_trail",
            EntityType.Builder.<BloodTrailProjectile>of(BloodTrailProjectile::new, MobCategory.MISC)
                    .sized(0.25F, 0.25F)
                    .clientTrackingRange(4)
                    .updateInterval(1));

    public static final RegistryObject<EntityType<HeartOfDecay>> HEART_OF_DECAY = register("heart_of_decay",
            EntityType.Builder.of(HeartOfDecay::new, MobCategory.MONSTER)
                    .sized(0.9F, 0.7F)
                    .clientTrackingRange(8));

    public static final RegistryObject<EntityType<ExReaper>> EX_REAPER = register("extraterrestrial_reaper",
            EntityType.Builder.of(ExReaper::new, MobCategory.MONSTER)
                    .sized(0.6F, 1.95F)
                    .clientTrackingRange(8));

    public static final RegistryObject<EntityType<FleshMaiden>> FLESH_MAIDEN = register("flesh_maiden",
            EntityType.Builder.of(FleshMaiden::new, MobCategory.MONSTER)
                    .sized(0.6F, 1.95F)
                    .clientTrackingRange(8));

    public static final RegistryObject<EntityType<Hematoma>> HEMATOMA = register("hematoma",
            EntityType.Builder.of(Hematoma::new, MobCategory.MONSTER)
                    .sized(0.9F, 0.9F)
                    .clientTrackingRange(8));

    public static final RegistryObject<EntityType<BloodSpreader>> BLOOD_SPREADER = register("blood_spreader",
            EntityType.Builder.of(BloodSpreader::new, MobCategory.MONSTER)
                    .sized(0.6F, 0.85F)
                    .clientTrackingRange(8));

    public static final RegistryObject<EntityType<BloodyCopy>> BLOODY_COPY = register("bloody_copy",
            EntityType.Builder.of(BloodyCopy::new, MobCategory.MONSTER)
                    .sized(0.6F, 0.85F)
                    .clientTrackingRange(8));

    public static final RegistryObject<EntityType<BloodClot>> BLOOD_CLOT = register("blood_clot",
            EntityType.Builder.of(BloodClot::new, MobCategory.MONSTER)
                    .sized(0.3125F, 0.125F)
                    .clientTrackingRange(8));

    public static final RegistryObject<EntityType<Prowler>> PROWLER = register("prowler",
            EntityType.Builder.of(Prowler::new, MobCategory.MONSTER)
                    .sized(0.9F, 2.0F)
                    .clientTrackingRange(8));

    public static final RegistryObject<EntityType<Carcass>> CARCASS = register("carcass",
            EntityType.Builder.of(Carcass::new, MobCategory.MONSTER)
                    .sized(1.675F, 1.75F)
                    .clientTrackingRange(8));

    public static final RegistryObject<EntityType<CameraShake>> CAMERA_SHAKE = register("camera_shake",
            EntityType.Builder.<CameraShake>of(CameraShake::new, MobCategory.MISC)
                    .fireImmune()
                    .noSummon()
                    .sized(1.0F, 1.0F)
                    .updateInterval(Integer.MAX_VALUE));

    private static <T extends Entity> RegistryObject<EntityType<T>> register(String p_20635_, EntityType.Builder<T> p_20636_) {
        return ENTITY_TYPE.register(p_20635_, () -> p_20636_.build(ClangingHowl.location(p_20635_).toString()));
    }
}
