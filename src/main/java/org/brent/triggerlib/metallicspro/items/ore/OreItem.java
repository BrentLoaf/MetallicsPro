package org.brent.triggerlib.metallicspro.items.ore;

import org.brent.triggerlib.metallicspro.items.CustomItem;
import org.brent.triggerlib.metallicspro.items.Rarity;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public abstract class OreItem extends CustomItem {

    public OreItem(String name, Material baseMaterial, Rarity rarity) {
        super(name, baseMaterial, rarity);
    }

    public abstract ItemStack getItemStack();

    public ItemStack getStarterStack() {
        return new ItemStack(baseMaterial);
    }

    public String itemName(String name, ChatColor color) {
        return ChatColor.RESET + "" + color + "" + name;
    }
}
