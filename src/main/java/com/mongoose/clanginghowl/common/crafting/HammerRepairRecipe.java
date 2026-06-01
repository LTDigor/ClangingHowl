package com.mongoose.clanginghowl.common.crafting;

import com.mongoose.clanginghowl.common.items.ExHammerItem;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;

import java.util.ArrayList;
import java.util.List;

public class HammerRepairRecipe extends CustomRecipe {
    public HammerRepairRecipe(ResourceLocation location, CraftingBookCategory category) {
        super(location, category);
    }

    @Override
    public boolean matches(CraftingContainer container, Level level) {
        List<ItemStack> items = new ArrayList<>();

        for (int i = 0; i < container.getContainerSize(); i++) {
            ItemStack stack = container.getItem(i);
            if (!stack.isEmpty()) {
                items.add(stack);
            }
        }

        return items.size() == 2
                && items.get(0).getItem() instanceof ExHammerItem
                && items.get(1).getItem() instanceof ExHammerItem;
    }

    @Override
    public ItemStack assemble(CraftingContainer container, RegistryAccess access) {
        List<ItemStack> items = new ArrayList<>();

        for (int i = 0; i < container.getContainerSize(); i++) {
            ItemStack stack = container.getItem(i);
            if (!stack.isEmpty()) {
                items.add(stack);
            }
        }

        if (items.size() != 2) {
            return ItemStack.EMPTY;
        }

        ItemStack result = items.get(0).copy();
        int combinedDamage = items.get(0).getDamageValue() + items.get(1).getDamageValue();
        result.setDamageValue(Math.max(0, combinedDamage - result.getMaxDamage() / 10));

        return result;
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return width * height >= 2;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return CHRecipeSerializers.HAMMER_REPAIR.get();
    }

    @Override
    public NonNullList<ItemStack> getRemainingItems(CraftingContainer container) {
        return NonNullList.withSize(container.getContainerSize(), ItemStack.EMPTY);
    }
}
