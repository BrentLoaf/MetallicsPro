package org.brent.triggerlib.metallicspro.listeners.ore;

import org.brent.triggerlib.metallicspro.MetallicsPro;
import org.brent.triggerlib.metallicspro.events.OreBreakEvent;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class OreBreakListener implements Listener {

    @EventHandler
    public void onOreBreak(OreBreakEvent event) {
        Block block = event.getBlock();



        block.getWorld().dropItemNaturally(
                block.getLocation().add(0.5, 0.5, 0.5),
                event.getOreBlock().getDroppedItem(event.getFortuneLevel())
        );
    }

    public static void init() {
        JavaPlugin plugin = MetallicsPro.getPlugin();
        plugin.getServer().getPluginManager().registerEvents(new OreBreakListener(), plugin);
    }
}
