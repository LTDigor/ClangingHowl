package com.mongoose.clanginghowl.common.crafting;

import com.mongoose.clanginghowl.ClangingHowl;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.SimpleCraftingRecipeSerializer;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class CHRecipeSerializers {
    public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister.create(
            ForgeRegistries.RECIPE_SERIALIZERS, ClangingHowl.MOD_ID);

    public static void init(){
        RECIPE_SERIALIZERS.register(FMLJavaModLoadingContext.get().getModEventBus());
    }

    public static final RegistryObject<RecipeSerializer<HammerRepairRecipe>> HAMMER_REPAIR = RECIPE_SERIALIZERS.register("hammer_repair",
            () -> new SimpleCraftingRecipeSerializer<>(HammerRepairRecipe::new));
}
