package org.brent.triggerlib.metallicspro.items.ore;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class LowGradeMetallicOreItem extends OreItem {

    public LowGradeMetallicOreItem() {
        super(Material.RAW_COPPER);
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
