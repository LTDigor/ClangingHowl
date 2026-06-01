package com.mongoose.clanginghowl.client.gui.overlay;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.mongoose.clanginghowl.ClangingHowl;
import com.mongoose.clanginghowl.common.items.CHItems;
import com.mongoose.clanginghowl.common.items.curios.XRayGoggles;
import com.mongoose.clanginghowl.utils.CHCuriosFinder;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.gui.overlay.ForgeGui;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;

public class XRayOverlay {
    public static final IGuiOverlay OVERLAY = XRayOverlay::drawOverlay;
    private static final Minecraft minecraft = Minecraft.getInstance();

    public static void drawOverlay(ForgeGui gui, GuiGraphics ms, float partialTicks, int screenWidth, int screenHeight) {
        if (minecraft.player != null){
            Player player = minecraft.player;
            ItemStack itemStack = CHCuriosFinder.findCurio(player, CHItems.X_RAY_GOGGLES.get());;
            if (!itemStack.isEmpty() && XRayGoggles.isActivated(itemStack)) {
                gui.setupOverlayRenderState(true, false);
                renderOverlay(ClangingHowl.location("textures/gui/x_ray_goggles_filter.png"), screenWidth, screenHeight);
            }
        }
    }

    public static void renderOverlay(ResourceLocation location, int screenWidth, int screenHeight) {
        RenderSystem.disableDepthTest();
        RenderSystem.depthMask(false);
        RenderSystem.defaultBlendFunc();
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, location);
        Tesselator tesselator = Tesselator.getInstance();
        BufferBuilder bufferbuilder = tesselator.getBuilder();
        bufferbuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);
        bufferbuilder.vertex(0.0, (double)screenHeight, -90.0).uv(0.0F, 1.0F).endVertex();
        bufferbuilder.vertex((double)screenWidth, (double)screenHeight, -90.0).uv(1.0F, 1.0F).endVertex();
        bufferbuilder.vertex((double)screenWidth, 0.0, -90.0).uv(1.0F, 0.0F).endVertex();
        bufferbuilder.vertex(0.0, 0.0, -90.0).uv(0.0F, 0.0F).endVertex();
        tesselator.end();
        RenderSystem.depthMask(true);
        RenderSystem.enableDepthTest();
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
    }
}
