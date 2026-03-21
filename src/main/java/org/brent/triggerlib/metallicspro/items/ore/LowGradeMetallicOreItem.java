package org.brent.triggerlib.metallicspro.items.ore;

import org.brent.triggerlib.metallicspro.items.Rarity;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class LowGradeMetallicOreItem extends OreItem {

    public LowGradeMetallicOreItem() {
        super("Low-Grade Metallic Ore", Material.RAW_COPPER, Rarity.COMMON);
    }

    @Override
    public ItemStack getItemStack() {
        ItemStack itemStack = getStarterStack();

        itemStack.editMeta(meta -> {
            meta.setDisplayName(itemName("Low Grade Metallic Ore", ChatColor.WHITE));
        });

        return itemStack;
    }
}
