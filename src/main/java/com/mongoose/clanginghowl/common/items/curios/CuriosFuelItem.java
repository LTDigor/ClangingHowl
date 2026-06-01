package com.mongoose.clanginghowl.common.items.curios;

import com.mongoose.clanginghowl.common.items.fuel.IFuel;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public abstract class CuriosFuelItem extends CHCurioItem implements IFuel {

    public CuriosFuelItem() {
        super();
    }

    public CuriosFuelItem(Properties p_41383_) {
        super(p_41383_);
    }

    public ItemStack getPowerlessItem(){
        ItemStack itemStack = new ItemStack(this);
        IFuel.setFuel(itemStack, 0);
        IFuel.setMaxFuelAmount(itemStack, this.getMaxFuel());
        return itemStack;
    }

    public ItemStack getPoweredItem(){
        ItemStack itemStack = new ItemStack(this);
        IFuel.setFuel(itemStack, this.getMaxFuel());
        IFuel.setMaxFuelAmount(itemStack, this.getMaxFuel());
        return itemStack;
    }

    public int getBarColor(ItemStack stack) {
        float f = Math.max(0.0F, 1.0F - amountColor(stack));
        return Mth.color(1.0F, 0.88F * f, 0.045F);
    }

    public float amountColor(ItemStack stack){
        if (stack.getTag() != null) {
            int energy = stack.getTag().getInt(FUEL_AMOUNT);
            int maxEnergy = stack.getTag().getInt(MAX_FUEL_AMOUNT);
            return 1.0F - ((float) energy / maxEnergy);
        } else {
            return 1.0F;
        }
    }

    @Override
    public boolean isBarVisible(ItemStack stack) {
        return stack.getTag() != null && !IFuel.isFull(stack);
    }

    @Override
    public int getBarWidth(ItemStack stack){
        if (stack.getTag() != null) {
            int energy = stack.getTag().getInt(FUEL_AMOUNT);
            int maxEnergy = stack.getTag().getInt(MAX_FUEL_AMOUNT);
            return Math.round((energy * 13.0F / maxEnergy));
        } else {
            return 0;
        }
    }

    @Override
    public void onCraftedBy(ItemStack pStack, Level pLevel, Player pPlayer) {
        IFuel.setFuel(pStack, 0);
        IFuel.setMaxFuelAmount(pStack, this.getMaxFuel());
        super.onCraftedBy(pStack, pLevel, pPlayer);
    }

    @Override
    public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged) {
        return super.shouldCauseReequipAnimation(oldStack, newStack, slotChanged) && slotChanged;
    }
}
