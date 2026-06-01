package com.mongoose.clanginghowl.client.gui.overlay;

import com.mongoose.clanginghowl.common.enchantments.CHEnchantments;
import com.mongoose.clanginghowl.common.items.CHItems;
import com.mongoose.clanginghowl.common.items.energy.ChainsawItem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.gui.overlay.ForgeGui;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;

public class OverheatOverlay {
    public static final IGuiOverlay OVERLAY = OverheatOverlay::drawOverlay;
    protected static final ResourceLocation GUI_ICONS_LOCATION = new ResourceLocation("textures/gui/icons.png");
    private static final Minecraft minecraft = Minecraft.getInstance();

    public static void drawOverlay(ForgeGui gui, GuiGraphics ms, float partialTicks, int screenWidth, int screenHeight) {
        if (minecraft.player != null){
            Player player = minecraft.player;
            if (player.isHolding(CHItems.ADVANCED_CHAINSAW.get())) {
                ItemStack itemStack = player.getMainHandItem();
                if (!itemStack.is(CHItems.ADVANCED_CHAINSAW.get())) {
                    itemStack = player.getOffhandItem();
                }
                if (itemStack.getEnchantmentLevel(CHEnchantments.FULL_POWER.get()) > 0) {
                    renderOverheatBar(ms, ChainsawItem.getOverheat(itemStack), 200);
                }
            }
        }
    }

    private static void renderOverheatBar(GuiGraphics gui, int overheat, int totalOverheat) {
        float f = (float) overheat / totalOverheat;
        int i = gui.guiWidth() / 2 - 91;
        int j = (int)(f * 183.0F);
        int k = gui.guiHeight() - 32 + 3;
        gui.blit(GUI_ICONS_LOCATION, i, k, 0, 84, 182, 5);
        if (j > 0) {
            gui.blit(GUI_ICONS_LOCATION, i, k, 0, 89, j, 5);
        }
    }
}
