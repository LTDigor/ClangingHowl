package com.mongoose.clanginghowl.common.items.curios;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Multimap;
import com.mongoose.clanginghowl.common.items.CHItems;
import com.mongoose.clanginghowl.common.items.fuel.IFuel;
import com.mongoose.clanginghowl.utils.CHUUIDUtil;
import com.mongoose.clanginghowl.utils.ItemHelper;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import top.theillusivec4.curios.api.SlotContext;

import javax.annotation.Nullable;
import java.util.List;
import java.util.UUID;

public class JetBoots extends CuriosFuelItem {
    @Override
    public int getMaxFuel() {
        return 1500;
    }

    public static boolean noFuelInInventory(LivingEntity livingEntity, ItemStack itemStack) {
        if (IFuel.isEmpty(itemStack)) {
            if (livingEntity instanceof Player player) {
                Inventory inventory = player.getInventory();
                List<NonNullList<ItemStack>> compartments = ImmutableList.of(inventory.items, inventory.armor, inventory.offhand);
                for (List<ItemStack> list : compartments) {
                    for (ItemStack itemStack1 : list) {
                        if (!itemStack1.isEmpty()) {
                            if (itemStack1.is(CHItems.BLAZE_FUEL_CYLINDER.get())) {
                                itemStack1.shrink(1);
                                IFuel.fillUpItem(itemStack, 1500);
                                return false;
                            }
                        }
                    }
                }
            }
        }
        return IFuel.isEmpty(itemStack);
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(SlotContext slotContext,
                                                                        UUID uuid, ItemStack stack) {
        Multimap<Attribute, AttributeModifier> map = HashMultimap.create();
        map.put(Attributes.ARMOR, new AttributeModifier(CHUUIDUtil.createUUID("item.clanginghowl.jet_boots"), "Jet Boots Boost", 1.0F, AttributeModifier.Operation.ADDITION));
        return map;
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
        super.appendHoverText(stack, worldIn, tooltip, flagIn);
        ItemHelper.addOnShift(tooltip, () -> addInformationAfterShift(tooltip));
        this.addFuelText(stack, worldIn, tooltip, flagIn);
    }

    public void addInformationAfterShift(List<Component> tooltip) {
        tooltip.add(Component.translatable("info.clanginghowl.item.boots.0"));
        tooltip.add(Component.translatable("info.clanginghowl.item.boots.1"));
        tooltip.add(Component.translatable("info.clanginghowl.item.boots.2"));
        tooltip.add(Component.translatable("info.clanginghowl.item.boots.3"));
        tooltip.add(Component.translatable("info.clanginghowl.item.boots.4"));
    }
}
