package com.mongoose.clanginghowl.common.items;

import com.mongoose.clanginghowl.ClangingHowl;
import com.mongoose.clanginghowl.common.blocks.CHBlocks;
import com.mongoose.clanginghowl.common.effects.CHEffects;
import com.mongoose.clanginghowl.common.entities.CHEntityType;
import com.mongoose.clanginghowl.common.items.curios.*;
import com.mongoose.clanginghowl.common.items.energy.*;
import com.mongoose.clanginghowl.common.items.fuel.FlamethrowerItem;
import com.mongoose.clanginghowl.common.items.fuel.IFuel;
import com.mongoose.clanginghowl.utils.ItemHelper;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import javax.annotation.Nullable;
import java.util.List;

public class CHItems {
    public static DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, ClangingHowl.MOD_ID);

    public static void init(){
        CHItems.ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
    }

    public static final RegistryObject<Item> EXTRATERRESTRIAL_STEEL = ITEMS.register("extraterrestrial_steel",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> PIECE_OF_EXTRATERRESTRIAL_STEEL = ITEMS.register("piece_of_extraterrestrial_steel",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> EXTRATERRESTRIAL_STEEL_INGOT = ITEMS.register("extraterrestrial_steel_ingot",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> EXTRATERRESTRIAL_STEEL_NUGGET = ITEMS.register("extraterrestrial_steel_nugget",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> EXTRATERRESTRIAL_STEEL_PLATE = ITEMS.register("extraterrestrial_steel_plate",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> EXTRATERRESTRIAL_ENERGY_CRYSTAL = ITEMS.register("extraterrestrial_energy_crystal",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> BATTERY_PANEL = ITEMS.register("battery_panel",
            () -> new Item(new Item.Properties().rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> CRYOGENIC_FUEL = ITEMS.register("cryogenic_fuel",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> BLAZE_FUEL = ITEMS.register("blaze_fuel",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> BLAZE_FUEL_CYLINDER = ITEMS.register("blaze_fuel_cylinder",
            () -> new BlockItem(CHBlocks.BLAZE_FUEL_CYLINDER_BLOCK.get(), new Item.Properties().stacksTo(16).rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> DRILL_BIT = ITEMS.register("drill_bit",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> CHAINSAW_TEETH = ITEMS.register("chainsaw_teeth",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> REDSTONE_WIRE = ITEMS.register("redstone_wire",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> DIAMOND_DIODE = ITEMS.register("diamond_diode",
            () -> new Item(new Item.Properties().rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> CHUNK_OF_TECHNOFLESH = ITEMS.register("chunk_of_technoflesh",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> TECHNO_OPTICS = ITEMS.register("techno_optics",
            () -> new Item(new Item.Properties().rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> TECHNO_HEART = ITEMS.register("techno_heart",
            () -> new Item(new Item.Properties().rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> HEMATOMA_LUMP = ITEMS.register("hematoma_lump",
            () -> new CHBaseItem(new Item.Properties().rarity(Rarity.UNCOMMON).food(new FoodProperties.Builder().effect(() -> new MobEffectInstance(CHEffects.ATTRACTION.get(), 300), 1.0F).effect(() -> new MobEffectInstance(MobEffects.HUNGER, 300), 1.0F).alwaysEat().nutrition(1).saturationMod(1.0F).build())));
    public static final RegistryObject<Item> ENERGY_FIBER = ITEMS.register("energy_fiber",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> ADVANCED_ELECTRONICS = ITEMS.register("advanced_electronics",
            () -> new Item(new Item.Properties().rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> NETHERRACK_SHAVINGS = ITEMS.register("netherrack_shavings",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> FIREPROOF_STEEL_COATING = ITEMS.register("fireproof_steel_coating",
            () -> new Item(new Item.Properties().fireResistant()));

    //Batteries
    public static final RegistryObject<EnergyItem> SMALL_ENERGY_BATTERY = ITEMS.register("small_energy_battery",
            () -> new BatteryItem(new Item.Properties().rarity(Rarity.UNCOMMON), 500));
    public static final RegistryObject<EnergyItem> ENERGY_BATTERY = ITEMS.register("energy_battery",
            () -> new BatteryItem(new Item.Properties().stacksTo(1).rarity(Rarity.UNCOMMON), 2000));
    public static final RegistryObject<EnergyItem> ENERGY_INTENSIVE_BATTERY = ITEMS.register("energy_intensive_battery",
            () -> new BatteryItem(new Item.Properties().stacksTo(1).rarity(Rarity.RARE), 7000));
    public static final RegistryObject<EnergyItem> ADVANCED_ENERGY_BATTERY = ITEMS.register("advanced_energy_battery",
            () -> new BatteryItem(new Item.Properties().stacksTo(1).rarity(Rarity.EPIC), 12000, true, true));

    //Tools
    public static final RegistryObject<Item> EXTRATERRESTRIAL_SWORD = ITEMS.register("extraterrestrial_sword", () -> new SwordItem(CHTiers.EXTRATERRESTRIAL, 3, -2.4F, new Item.Properties()){
        @Override
        public void appendHoverText(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
            super.appendHoverText(stack, worldIn, tooltip, flagIn);
            ItemHelper.addOnShift(tooltip, () -> addInformationAfterShift(tooltip));
        }

        public void addInformationAfterShift(List<Component> tooltip) {
            tooltip.add(Component.translatable("info.clanginghowl.item.extraterrestrial"));
        }
    });
    public static final RegistryObject<Item> EXTRATERRESTRIAL_SHOVEL = ITEMS.register("extraterrestrial_shovel", () -> new ShovelItem(CHTiers.EXTRATERRESTRIAL, 1.5F, -3.0F, new Item.Properties()){
        @Override
        public void appendHoverText(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
            super.appendHoverText(stack, worldIn, tooltip, flagIn);
            ItemHelper.addOnShift(tooltip, () -> addInformationAfterShift(tooltip));
        }

        public void addInformationAfterShift(List<Component> tooltip) {
            tooltip.add(Component.translatable("info.clanginghowl.item.extraterrestrial"));
        }
    });
    public static final RegistryObject<Item> EXTRATERRESTRIAL_PICKAXE = ITEMS.register("extraterrestrial_pickaxe", () -> new PickaxeItem(CHTiers.EXTRATERRESTRIAL, 1, -2.8F, new Item.Properties()){
        @Override
        public void appendHoverText(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
            super.appendHoverText(stack, worldIn, tooltip, flagIn);
            ItemHelper.addOnShift(tooltip, () -> addInformationAfterShift(tooltip));
        }

        public void addInformationAfterShift(List<Component> tooltip) {
            tooltip.add(Component.translatable("info.clanginghowl.item.extraterrestrial"));
        }
    });
    public static final RegistryObject<Item> EXTRATERRESTRIAL_AXE = ITEMS.register("extraterrestrial_axe", () -> new AxeItem(CHTiers.EXTRATERRESTRIAL, 5.0F, -3.0F, new Item.Properties()){
        @Override
        public void appendHoverText(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
            super.appendHoverText(stack, worldIn, tooltip, flagIn);
            ItemHelper.addOnShift(tooltip, () -> addInformationAfterShift(tooltip));
        }

        public void addInformationAfterShift(List<Component> tooltip) {
            tooltip.add(Component.translatable("info.clanginghowl.item.extraterrestrial"));
        }
    });
    public static final RegistryObject<Item> EXTRATERRESTRIAL_HOE = ITEMS.register("extraterrestrial_hoe", () -> new HoeItem(CHTiers.EXTRATERRESTRIAL, -3, 0.0F, new Item.Properties()){
        @Override
        public void appendHoverText(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
            super.appendHoverText(stack, worldIn, tooltip, flagIn);
            ItemHelper.addOnShift(tooltip, () -> addInformationAfterShift(tooltip));
        }

        public void addInformationAfterShift(List<Component> tooltip) {
            tooltip.add(Component.translatable("info.clanginghowl.item.extraterrestrial"));
        }
    });
    public static final RegistryObject<Item> EXTRATERRESTRIAL_HAMMER = ITEMS.register("extraterrestrial_hammer", ExHammerItem::new);

    public static final RegistryObject<Item>  INDUSTRIAL_ADJUSTABLE_WRENCH = ITEMS.register("industrial_adjustable_wrench", WrenchItem::new);
    public static final RegistryObject<Item> BLAZE_BURNER = ITEMS.register("blaze_burner", BlazeBurnerItem::new);
    public static final RegistryObject<Item> ATTRACTION_DEVICE = ITEMS.register("attraction_device", AttractionDevice::new);
    public static final RegistryObject<DrillItem>  ADVANCED_HAND_DRILL = ITEMS.register("advanced_hand_drill", DrillItem::new);
    public static final RegistryObject<ChainsawItem>  ADVANCED_CHAINSAW = ITEMS.register("advanced_chainsaw", ChainsawItem::new);
    public static final RegistryObject<ChainswordItem>  ADVANCED_CHAINSWORD = ITEMS.register("advanced_chainsword", ChainswordItem::new);
    public static final RegistryObject<FlamethrowerItem>  FLAMETHROWER = ITEMS.register("flamethrower", FlamethrowerItem::new);

    public static final RegistryObject<Item> PORTABLE_CHARGER = ITEMS.register("portable_charger", PortableChargerItem::new);

    //Curios
    public static final RegistryObject<CuriosEnergyItem> X_RAY_GOGGLES = ITEMS.register("x_ray_goggles", XRayGoggles::new);
    public static final RegistryObject<CuriosEnergyItem> ENERGY_BARRIER_GENERATOR = ITEMS.register("energy_barrier_generator", EnergyBarrierGenerator::new);
    public static final RegistryObject<CuriosEnergyItem> TENDON_STRENGTHENER = ITEMS.register("tendon_strengthener", TendonStrengthener::new);
    public static final RegistryObject<CuriosEnergyItem> ENERGY_GLOVE = ITEMS.register("energy_glove", EnergyGlove::new);
    public static final RegistryObject<CuriosFuelItem> JET_BOOTS = ITEMS.register("jet_boots", JetBoots::new);
    public static final RegistryObject<CHCurioItem> BLOODY_BATTERY = ITEMS.register("bloody_battery", () -> new CHCurioItem(new Item.Properties().stacksTo(1).rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<CuriosEnergyItem> REANIMATOR = ITEMS.register("reanimator", Reanimator::new);

    //Spawn Eggs
    public static final RegistryObject<ForgeSpawnEggItem> HEART_OF_DECAY_SPAWN_EGG = ITEMS.register("heart_of_decay_spawn_egg",
            () -> new ForgeSpawnEggItem(CHEntityType.HEART_OF_DECAY, 0xFFFFFF, 0xFFFFFF, new Item.Properties()));

    public static final RegistryObject<ForgeSpawnEggItem> EX_REAPER_SPAWN_EGG = ITEMS.register("extraterrestrial_reaper_spawn_egg",
            () -> new ForgeSpawnEggItem(CHEntityType.EX_REAPER, 0xFFFFFF, 0xFFFFFF, new Item.Properties()));

    public static final RegistryObject<ForgeSpawnEggItem> FLESH_MAIDEN_SPAWN_EGG = ITEMS.register("flesh_maiden_spawn_egg",
            () -> new ForgeSpawnEggItem(CHEntityType.FLESH_MAIDEN, 0xFFFFFF, 0xFFFFFF, new Item.Properties()));

    public static final RegistryObject<ForgeSpawnEggItem> HEMATOMA_SPAWN_EGG = ITEMS.register("hematoma_spawn_egg",
            () -> new ForgeSpawnEggItem(CHEntityType.HEMATOMA, 0xFFFFFF, 0xFFFFFF, new Item.Properties()));

    public static final RegistryObject<ForgeSpawnEggItem> BLOOD_SPREADER_SPAWN_EGG = ITEMS.register("blood_spreader_spawn_egg",
            () -> new ForgeSpawnEggItem(CHEntityType.BLOOD_SPREADER, 0xFFFFFF, 0xFFFFFF, new Item.Properties()));

    public static final RegistryObject<ForgeSpawnEggItem> PROWLER_SPAWN_EGG = ITEMS.register("prowler_spawn_egg",
            () -> new ForgeSpawnEggItem(CHEntityType.PROWLER, 0xFFFFFF, 0xFFFFFF, new Item.Properties()));

    public static final RegistryObject<ForgeSpawnEggItem> CARCASS_SPAWN_EGG = ITEMS.register("carcass_spawn_egg",
            () -> new ForgeSpawnEggItem(CHEntityType.CARCASS, 0xFFFFFF, 0xFFFFFF, new Item.Properties()));

    //Dummy
    public static final RegistryObject<Item> REANIMATION = ITEMS.register("reanimation", DummyItem::new);

    public static boolean shouldSkipCreativeModTab(Item item) {
        return item instanceof EnergyItem || item instanceof IFuel || item instanceof CHCurioItem || item instanceof DummyItem;
    }
}
