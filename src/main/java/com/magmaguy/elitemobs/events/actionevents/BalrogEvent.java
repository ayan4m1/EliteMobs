package com.magmaguy.elitemobs.events.actionevents;

import com.magmaguy.elitemobs.EliteMobs;
import com.magmaguy.elitemobs.config.ConfigValues;
import com.magmaguy.elitemobs.config.EventsConfig;
import com.magmaguy.elitemobs.events.mobs.Balrog;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import java.util.concurrent.ThreadLocalRandom;

public class BalrogEvent implements Listener {

    @EventHandler
    public void onMine(BlockBreakEvent event) {

        if (event.isCancelled()) return;
        if (!EliteMobs.validWorldList.contains(event.getPlayer().getWorld())) return;
        if (event.getPlayer().getGameMode() == GameMode.CREATIVE || event.getPlayer().getGameMode() == GameMode.SPECTATOR) return;
        if (!event.getPlayer().hasPermission("elitemobs.events.balrog")) return;
        if (event.getPlayer().getInventory().getItemInMainHand().hasItemMeta() &&
                event.getPlayer().getInventory().getItemInMainHand().getEnchantments().containsKey(Enchantment.SILK_TOUCH))
            return;
        if (!(event.getBlock().getType().equals(Material.DIAMOND_ORE) || event.getBlock().getType().equals(Material.IRON_ORE) ||
                event.getBlock().getType().equals(Material.COAL_ORE) || event.getBlock().getType().equals(Material.REDSTONE_ORE) ||
                event.getBlock().getType().equals(Material.LAPIS_ORE) || event.getBlock().getType().equals(Material.GOLD_ORE))) return;
        if (ThreadLocalRandom.current().nextDouble() > ConfigValues.eventsConfig.getDouble(EventsConfig.BALROG_CHANCE_ON_MINE)) return;

        Balrog.spawnBalrog(event.getBlock().getLocation());

    }

}
