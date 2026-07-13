package org.brent.metallicspro.listeners.item;

import org.brent.metallicspro.MetallicsPro;
import org.brent.metallicspro.events.ItemEnterWaterEvent;
import org.brent.metallicspro.items.CustomItem;
import org.brent.metallicspro.materials.CustomMaterial;
import org.brent.metallicspro.materials.FormType;
import org.brent.metallicspro.materials.MaterialType;
import org.brent.metallicspro.materials.types.MetalMaterial;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.Item;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public class ItemEnterWaterListener implements Listener {

    @EventHandler
    public void onItemEnterWater(ItemEnterWaterEvent event) {
        Item item = event.getItem();

        CustomMaterial material = MetallicsPro.getMaterialRegistry().getMaterial(item.getItemStack());

        if (material == null) return;

        if (material.getProperties().isHydroExplosive()) {
            item.remove();

            Location location = item.getLocation();
            World world = location.getWorld();

            world.createExplosion(location, 2.0f);
        } else if (MaterialType.isType(item.getItemStack(), FormType.FIRED_INGOT)) {
            CustomItem customItem = material.getItem(FormType.INGOT);
            if (customItem == null) return;

            ItemStack newItemStack = customItem.getItemStack();

            newItemStack.setAmount(item.getItemStack().getAmount());

            Location location = item.getLocation();
            World world = location.getWorld();

            item.remove();

            Item newItem = world.dropItem(location, newItemStack);

            newItem.setPickupDelay(20);

            world.playSound(location, Sound.BLOCK_LAVA_EXTINGUISH, 1.0f, 1.0f);
            world.spawnParticle(Particle.CLOUD, location.add(0, 0.15, 0), 10, 0, 1, 0, 0.1);
        }
    }

    public static void init() {
        JavaPlugin plugin = MetallicsPro.getPlugin();
        plugin.getServer().getPluginManager().registerEvents(new ItemEnterWaterListener(), plugin);
    }
}
