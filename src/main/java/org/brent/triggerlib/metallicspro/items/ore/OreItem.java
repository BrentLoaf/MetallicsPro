package org.brent.triggerlib.metallicspro.items.ore;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public abstract class OreItem {

    protected final Material replacedItem;

    public OreItem(Material replacedItem) {
        this.replacedItem = replacedItem;
    }

    public abstract ItemStack getItemStack();

    public ItemStack getStarterStack() {
        return new ItemStack(replacedItem);
    }

    public String itemName(String name, ChatColor color) {
        return ChatColor.RESET + "" + color + "" + name;
    }
}
