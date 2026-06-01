package com.mongoose.clanginghowl.utils;

import com.google.common.collect.ImmutableList;
import com.mongoose.clanginghowl.common.items.energy.IEnergyItem;
import net.minecraft.core.NonNullList;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.List;
import java.util.function.Predicate;

public class EnergyUtil {

    public static void chargeAllItems(Player player, int energy) {
        chargeAllItems(player, energy, itemStack -> false);
    }

    public static void chargeAllItems(Player player, int energy, Predicate<ItemStack> excludePredicate) {
        if (!player.level().isClientSide) {
            Inventory inventory = player.getInventory();
            List<NonNullList<ItemStack>> compartments = ImmutableList.of(inventory.items, inventory.armor, inventory.offhand);
            for (List<ItemStack> list : compartments) {
                for (ItemStack itemStack : list) {
                    if (!itemStack.isEmpty()) {
                        if (itemStack.getItem() instanceof IEnergyItem item && !excludePredicate.test(itemStack) && item.canCharge() && !IEnergyItem.isFull(itemStack)) {
                            IEnergyItem.powerItem(itemStack, energy);
                        }
                    }
                }
            }
            for (ItemStack itemStack : CHCuriosFinder.getCurioList(player, item -> item instanceof IEnergyItem)) {
                if (!itemStack.isEmpty()) {
                    if (itemStack.getItem() instanceof IEnergyItem item && !excludePredicate.test(itemStack) && item.canCharge() && !IEnergyItem.isFull(itemStack)) {
                        IEnergyItem.powerItem(itemStack, energy);
                    }
                }
            }
        }
    }
}
