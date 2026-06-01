package com.mongoose.clanginghowl.init;

import com.mongoose.clanginghowl.ClangingHowl;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.entries.LootPoolEntryContainer;
import net.minecraft.world.level.storage.loot.entries.LootTableReference;
import net.minecraftforge.event.LootTableLoadEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;

@Mod.EventBusSubscriber(modid = ClangingHowl.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class CHLootInject {

    private static final List<String> CHEST_TABLES = List.of("bastion_bridge", "bastion_treasure", "bastion_other", "nether_bridge");

    @SubscribeEvent
    public static void InjectLootTables(LootTableLoadEvent evt) {
        String chestsPrefix = "minecraft:chests/";
        String name = evt.getName().toString();

        if ((name.startsWith(chestsPrefix) && CHEST_TABLES.contains(name.substring(chestsPrefix.length())))) {
            String file = name.substring("minecraft:".length());
            evt.getTable().addPool(getInjectPool(file));
        }
    }

    private static LootPool getInjectPool(String entryName) {
        return LootPool.lootPool().add(getInjectEntry(entryName)).name("clanginghowl_inject_pool").build();
    }

    private static LootPoolEntryContainer.Builder<?> getInjectEntry(String name) {
        return LootTableReference.lootTableReference(ClangingHowl.location("inject/" + name));
    }
}
