package org.brent.metallicspro.listeners.item;

import org.brent.metallicspro.MetallicsPro;
import org.brent.metallicspro.events.ItemEnterWaterEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Item;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDropItemEvent;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.entity.ItemDespawnEvent;
import org.bukkit.event.entity.ItemSpawnEvent;
import org.bukkit.event.inventory.InventoryPickupItemEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashSet;
import java.util.Iterator;

public class ItemEnterWaterRegister implements Listener {

    public static final HashSet<Item> items = new HashSet<>();

    @EventHandler
    public void onItemSpawn(ItemSpawnEvent event) {
        Item item = event.getEntity();
        items.add(item);
    }

    @EventHandler
    public void onItemDrop(EntityDropItemEvent event) {
        Item item = event.getItemDrop();
        items.add(item);
    }

    @EventHandler
    public void onItemPlayerDrop(PlayerDropItemEvent event) {
        Item item = event.getItemDrop();
        items.add(item);
    }


    @EventHandler
    public void onItemDespawn(ItemDespawnEvent event) {
        Item item = event.getEntity();
        if (!items.contains(item)) return;

        items.remove(item);
    }

    @EventHandler
    public void onItemEntityPickup(EntityPickupItemEvent event) {
        Item item = event.getItem();
        if (!items.contains(item)) return;

        items.remove(item);
    }

    @EventHandler
    public void onItemInventoryPickup(InventoryPickupItemEvent event) {
        Item item = event.getItem();
        if (!items.contains(item)) return;

        items.remove(item);
    }

    public static void init() {
        JavaPlugin plugin = MetallicsPro.getPlugin();
        plugin.getServer().getPluginManager().registerEvents(new ItemEnterWaterRegister(), plugin);

        Bukkit.getScheduler().runTaskTimer(MetallicsPro.getPlugin(), () -> {
            Iterator<Item> iterator = items.iterator();

            while (iterator.hasNext()) {
                Item item = iterator.next();

                if (!item.isInWater()) continue;

                ItemEnterWaterEvent event = new ItemEnterWaterEvent(item);
                Bukkit.getPluginManager().callEvent(event);

                if (event.isRemove()) iterator.remove();
            }
        }, 100L, 5L);
    }
}
