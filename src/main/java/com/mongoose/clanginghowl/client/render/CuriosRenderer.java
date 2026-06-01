package com.mongoose.clanginghowl.client.render;

import com.mongoose.clanginghowl.ClangingHowl;
import com.mongoose.clanginghowl.client.render.model.*;
import com.mongoose.clanginghowl.common.items.CHItems;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.resources.ResourceLocation;
import top.theillusivec4.curios.api.client.CuriosRendererRegistry;

public class CuriosRenderer {
    public static String folderPath = "textures/models/curios/";

    public static ResourceLocation render(String textureName){
        return ClangingHowl.location(folderPath + textureName);
    }

    public static void register() {
        CuriosRendererRegistry.register(CHItems.X_RAY_GOGGLES.get(), () -> new WearRenderer(render("x_ray_goggles_off.png"), new XRayGogglesModel(bakeLayer(CHModelLayer.X_RAY_GOGGLES))));
        CuriosRendererRegistry.register(CHItems.ENERGY_BARRIER_GENERATOR.get(), () -> new WearRenderer(render("energy_barrier_generator.png"), new EnergyBarrierGeneratorModel(bakeLayer(CHModelLayer.ENERGY_BARRIER_GENERATOR))));
        CuriosRendererRegistry.register(CHItems.TENDON_STRENGTHENER.get(), () -> new WearRenderer(render("tendon_strengthener.png"), new TendonStrengthenerModel(bakeLayer(CHModelLayer.TENDON_STRENGTHENER))));
        CuriosRendererRegistry.register(CHItems.ENERGY_GLOVE.get(), () -> new WearRenderer(render("energy_glove_discharged.png"), new EnergyGloveModel(bakeLayer(CHModelLayer.ENERGY_GLOVE))));
        CuriosRendererRegistry.register(CHItems.JET_BOOTS.get(), () -> new WearRenderer(render("jet_boots.png"), new JetBootsModel(bakeLayer(CHModelLayer.JET_BOOTS))));
        CuriosRendererRegistry.register(CHItems.BLOODY_BATTERY.get(), () -> new WearRenderer(render("bloody_battery.png"), new BloodyBatteryModel(bakeLayer(CHModelLayer.BLOODY_BATTERY))));
        CuriosRendererRegistry.register(CHItems.REANIMATOR.get(), () -> new WearRenderer(render("reanimator.png"), new ReanimatorModel(bakeLayer(CHModelLayer.REANIMATOR))));
    }

    public static ModelPart bakeLayer(ModelLayerLocation layerLocation) {
        return Minecraft.getInstance().getEntityModels().bakeLayer(layerLocation);
    }
}
