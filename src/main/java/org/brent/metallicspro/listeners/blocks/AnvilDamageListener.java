package org.brent.metallicspro.listeners.blocks;

import com.destroystokyo.paper.event.block.AnvilDamagedEvent;
import org.brent.metallicspro.MetallicsPro;
import org.bukkit.Material;
import org.bukkit.entity.FallingBlock;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class AnvilDamageListener implements Listener {

    @EventHandler
    public void onAnvilDamage(AnvilDamagedEvent event) {
        event.setCancelled(true);
    }

    @EventHandler
    public void onAnvilLand(EntityChangeBlockEvent event) {
        if (!(event.getEntity() instanceof FallingBlock falling)) return;

        if (falling.getBlockData().getMaterial() != Material.ANVIL &&
                falling.getBlockData().getMaterial() != Material.CHIPPED_ANVIL &&
                falling.getBlockData().getMaterial() != Material.DAMAGED_ANVIL) {
            return;
        }

        event.setCancelled(true);
    }

    public static void init() {
        JavaPlugin plugin = MetallicsPro.getPlugin();
        plugin.getServer().getPluginManager().registerEvents(new AnvilDamageListener(), plugin);
    }
}