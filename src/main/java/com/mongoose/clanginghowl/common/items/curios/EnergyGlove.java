package com.mongoose.clanginghowl.common.items.curios;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.mongoose.clanginghowl.common.items.energy.IEnergyItem;
import com.mongoose.clanginghowl.utils.CHCuriosFinder;
import com.mongoose.clanginghowl.utils.CHUUIDUtil;
import com.mongoose.clanginghowl.utils.ItemHelper;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import top.theillusivec4.curios.api.SlotContext;

import javax.annotation.Nullable;
import java.util.List;
import java.util.UUID;

public class EnergyGlove extends CuriosEnergyItem {
    private static final String DISCHARGED = "Discharged";
    private static final String DISCHARGING = "Discharging";

    public EnergyGlove() {
        super(new Properties().stacksTo(1).rarity(Rarity.UNCOMMON));
    }

    @Override
    public int getMaxEnergy() {
        return 1500;
    }

    @Override
    public void inventoryTick(ItemStack stack, Level worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
        super.inventoryTick(stack, worldIn, entityIn, itemSlot, isSelected);
        if (entityIn instanceof LivingEntity wearer && CHCuriosFinder.findCurio(wearer, this) == stack) {
            if (isDischarged(stack)) {
                if (getDischarging(stack) < 80) {
                    setDischarging(stack, getDischarging(stack) + 1);
                } else {
                    setDischarged(stack, false);
                }
            } else {
                if (getDischarging(stack) > 0) {
                    setDischarging(stack, 0);
                }
            }
        }
    }

    public static void setDischarged(ItemStack stack, boolean discharged){
        if (stack.getTag() != null) {
            stack.getTag().putBoolean(DISCHARGED, discharged);
        } else {
            CompoundTag compound = stack.getOrCreateTag();
            compound.putBoolean(DISCHARGED, discharged);
        }
    }

    public static boolean isDischarged(ItemStack stack) {
        if (stack.getTag() != null) {
            return stack.getTag().getBoolean(DISCHARGED);
        } else {
            return false;
        }
    }

    public static void setDischarging(ItemStack stack, int discharging){
        if (stack.getTag() != null) {
            stack.getTag().putInt(DISCHARGING, discharging);
        } else {
            CompoundTag compound = stack.getOrCreateTag();
            compound.putInt(DISCHARGING, discharging);
        }
    }

    public static int getDischarging(ItemStack stack) {
        if (stack.getTag() != null) {
            return stack.getTag().getInt(DISCHARGING);
        } else {
            return 0;
        }
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(SlotContext slotContext,
                                                                        UUID uuid, ItemStack stack) {
        Multimap<Attribute, AttributeModifier> map = HashMultimap.create();
        map.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(CHUUIDUtil.createUUID("item.clanginghowl.energy_glove"), "Energy Glove Boost", 1.0F, AttributeModifier.Operation.ADDITION));
        if (IEnergyItem.isEmpty(stack)) {
            map = super.getAttributeModifiers(slotContext, uuid, stack);
        }
        return map;
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
        super.appendHoverText(stack, worldIn, tooltip, flagIn);
        ItemHelper.addOnShift(tooltip, () -> addInformationAfterShift(tooltip));
        this.addEnergyText(stack, worldIn, tooltip, flagIn);
    }

    public void addInformationAfterShift(List<Component> tooltip) {
        tooltip.add(Component.translatable("info.clanginghowl.item.glove.0"));
        tooltip.add(Component.translatable("info.clanginghowl.item.glove.1"));
        tooltip.add(Component.translatable("info.clanginghowl.item.glove.2"));
        tooltip.add(Component.translatable("info.clanginghowl.item.glove.3"));
    }
}
