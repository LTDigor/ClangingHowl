package com.mongoose.clanginghowl;

import com.mojang.logging.LogUtils;
import com.mojang.serialization.Codec;
import com.mongoose.clanginghowl.client.ClientProxy;
import com.mongoose.clanginghowl.client.inventory.menu.CHMenuTypes;
import com.mongoose.clanginghowl.client.particles.CHParticleTypes;
import com.mongoose.clanginghowl.common.CommonProxy;
import com.mongoose.clanginghowl.common.blocks.CHBlocks;
import com.mongoose.clanginghowl.common.blocks.entities.CHBlockEntities;
import com.mongoose.clanginghowl.common.crafting.CHRecipeSerializers;
import com.mongoose.clanginghowl.common.effects.CHEffects;
import com.mongoose.clanginghowl.common.enchantments.CHEnchantments;
import com.mongoose.clanginghowl.common.entities.CHEntityType;
import com.mongoose.clanginghowl.common.entities.hostiles.*;
import com.mongoose.clanginghowl.common.items.CHItems;
import com.mongoose.clanginghowl.common.items.CHPotions;
import com.mongoose.clanginghowl.common.network.CHNetwork;
import com.mongoose.clanginghowl.common.world.CHMobSpawnBiomeModifier;
import com.mongoose.clanginghowl.compat.CHCompat;
import com.mongoose.clanginghowl.config.CHConfig;
import com.mongoose.clanginghowl.init.*;
import com.mongoose.clanginghowl.mixin.FireBlockAccessor;
import com.mongoose.clanginghowl.utils.CHPotionUtil;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.brewing.BrewingRecipeRegistry;
import net.minecraftforge.common.world.BiomeModifier;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.event.entity.SpawnPlacementRegisterEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLPaths;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import org.slf4j.Logger;
import top.theillusivec4.curios.api.CuriosApi;

@Mod(ClangingHowl.MOD_ID)
public class ClangingHowl {
    public static final String MOD_ID = "clanginghowl";
    public static final Logger LOGGER = LogUtils.getLogger();
    public static CHProxy PROXY = DistExecutor.unsafeRunForDist(() -> ClientProxy::new, () -> CommonProxy::new);
    public static SidedInit SIDED_INIT = DistExecutor.unsafeRunForDist(() -> ClientSideInit::new, () -> SidedInit::new);

    public static ResourceLocation location(String path) {
        return new ResourceLocation(MOD_ID, path);
    }

    public ClangingHowl() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        CHBlockEntities.BLOCK_ENTITY.register(modEventBus);
        CHEntityType.ENTITY_TYPE.register(modEventBus);
        CHParticleTypes.PARTICLE_TYPES.register(modEventBus);
        CHMenuTypes.MENU_TYPE.register(modEventBus);
        CHEnchantments.ENCHANTMENTS.register(modEventBus);
        CHCreativeTab.CREATIVE_MODE_TABS.register(modEventBus);
        modEventBus.addListener(this::commonSetup);
        modEventBus.addListener(this::setupEntityAttributeCreation);
        modEventBus.addListener(this::SpawnPlacementEvent);

        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, CHConfig.SPEC, "clanginghowl.toml");
        CHConfig.loadConfig(CHConfig.SPEC, FMLPaths.CONFIGDIR.get().resolve("clanginghowl.toml").toString());

        final DeferredRegister<Codec<? extends BiomeModifier>> biomeModifiers = DeferredRegister.create(ForgeRegistries.Keys.BIOME_MODIFIER_SERIALIZERS, ClangingHowl.MOD_ID);
        biomeModifiers.register(modEventBus);
        biomeModifiers.register("mob_spawns", CHMobSpawnBiomeModifier::makeCodec);

        MinecraftForge.EVENT_BUS.register(this);
        CHItems.init();
        CHBlocks.init();
        CHRecipeSerializers.init();
        CHEffects.init();
        CHPotions.init();
        CHSounds.init();
        SIDED_INIT.init();
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        CHNetwork.init();

        CHCompat.setup(event);
        event.enqueueWork(() -> {
            addBrewingRecipes();
            FireBlockAccessor fireBlockAccessor = (FireBlockAccessor) Blocks.FIRE;
            fireBlockAccessor.callSetFlammable(CHBlocks.BLAZE_FUEL_CYLINDER_BLOCK.get(), 15, 100);
            fireBlockAccessor.callSetFlammable(CHBlocks.TECHNOFLESH_BLOCK.get(), 5, 20);
            fireBlockAccessor.callSetFlammable(CHBlocks.TECHNOFLESH_SLAB.get(), 5, 20);
            fireBlockAccessor.callSetFlammable(CHBlocks.TECHNOFLESH_MEMBRANE.get(), 5, 20);
            fireBlockAccessor.callSetFlammable(CHBlocks.TECHNOFLESH_NEST.get(), 5, 20);
        });
    }

    private static void addBrewingRecipes(){
        BrewingRecipeRegistry.addRecipe(new CHPotionUtil(CHPotionUtil.setPotion(Potions.AWKWARD), Ingredient.of(CHItems.HEMATOMA_LUMP.get()), CHPotionUtil.setPotion(CHPotions.ATTRACTION.get())));
        BrewingRecipeRegistry.addRecipe(new CHPotionUtil(CHPotionUtil.setSplashPotion(Potions.AWKWARD), Ingredient.of(CHItems.HEMATOMA_LUMP.get()), CHPotionUtil.setSplashPotion(CHPotions.ATTRACTION.get())));
        BrewingRecipeRegistry.addRecipe(new CHPotionUtil(CHPotionUtil.setSplashPotion(Potions.AWKWARD), Ingredient.of(CHItems.HEMATOMA_LUMP.get()), CHPotionUtil.setLingeringPotion(CHPotions.ATTRACTION.get())));
        BrewingRecipeRegistry.addRecipe(new CHPotionUtil(CHPotionUtil.setPotion(CHPotions.ATTRACTION.get()), Ingredient.of(Items.REDSTONE), CHPotionUtil.setPotion(CHPotions.LONG_ATTRACTION.get())));
        BrewingRecipeRegistry.addRecipe(new CHPotionUtil(CHPotionUtil.setSplashPotion(CHPotions.ATTRACTION.get()), Ingredient.of(Items.REDSTONE), CHPotionUtil.setSplashPotion(CHPotions.LONG_ATTRACTION.get())));
        BrewingRecipeRegistry.addRecipe(new CHPotionUtil(CHPotionUtil.setLingeringPotion(CHPotions.ATTRACTION.get()), Ingredient.of(Items.REDSTONE), CHPotionUtil.setLingeringPotion(CHPotions.LONG_ATTRACTION.get())));
    }

    private void setupEntityAttributeCreation(final EntityAttributeCreationEvent event) {
        event.put(CHEntityType.HEART_OF_DECAY.get(), HeartOfDecay.createAttributes().build());
        event.put(CHEntityType.EX_REAPER.get(), ExReaper.createAttributes().build());
        event.put(CHEntityType.FLESH_MAIDEN.get(), FleshMaiden.createAttributes().build());
        event.put(CHEntityType.HEMATOMA.get(), Hematoma.createAttributes().build());
        event.put(CHEntityType.BLOOD_SPREADER.get(), BloodSpreader.createAttributes().build());
        event.put(CHEntityType.BLOODY_COPY.get(), BloodyCopy.createAttributes().build());
        event.put(CHEntityType.BLOOD_CLOT.get(), BloodClot.createAttributes().build());
        event.put(CHEntityType.PROWLER.get(), Prowler.createAttributes().build());
        event.put(CHEntityType.CARCASS.get(), Carcass.createAttributes().build());
    }

    private void SpawnPlacementEvent(SpawnPlacementRegisterEvent event){
        event.register(CHEntityType.HEART_OF_DECAY.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, HeartOfDecay::checkHoDSpawnRules, SpawnPlacementRegisterEvent.Operation.AND);
        event.register(CHEntityType.EX_REAPER.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, ExReaper::checkExReaperSpawnRules, SpawnPlacementRegisterEvent.Operation.AND);
        event.register(CHEntityType.FLESH_MAIDEN.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, FleshMaiden::checkFleshMaidenSpawnRules, SpawnPlacementRegisterEvent.Operation.AND);
        event.register(CHEntityType.HEMATOMA.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Hematoma::checkHematomaSpawnRules, SpawnPlacementRegisterEvent.Operation.AND);
        event.register(CHEntityType.BLOOD_SPREADER.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, BloodSpreader::checkBloodSpreaderSpawnRules, SpawnPlacementRegisterEvent.Operation.AND);
        event.register(CHEntityType.PROWLER.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Prowler::checkProwlerSpawnRules, SpawnPlacementRegisterEvent.Operation.AND);
        event.register(CHEntityType.CARCASS.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Carcass::checkCarcassSpawnRules, SpawnPlacementRegisterEvent.Operation.AND);
    }

    @SuppressWarnings("all")
    private void enqueueIMC(final InterModEnqueueEvent event) {
        InterModComms.sendTo(CuriosApi.MODID, top.theillusivec4.curios.api.SlotTypeMessage.REGISTER_TYPE, () -> top.theillusivec4.curios.api.SlotTypePreset.HEAD.getMessageBuilder().build());
        InterModComms.sendTo(CuriosApi.MODID, top.theillusivec4.curios.api.SlotTypeMessage.REGISTER_TYPE, () -> top.theillusivec4.curios.api.SlotTypePreset.BODY.getMessageBuilder().build());
        InterModComms.sendTo(CuriosApi.MODID, top.theillusivec4.curios.api.SlotTypeMessage.REGISTER_TYPE, () -> top.theillusivec4.curios.api.SlotTypePreset.HANDS.getMessageBuilder().build());
        InterModComms.sendTo(CuriosApi.MODID, top.theillusivec4.curios.api.SlotTypeMessage.REGISTER_TYPE, () -> top.theillusivec4.curios.api.SlotTypePreset.BELT.getMessageBuilder().build());
    }
}
