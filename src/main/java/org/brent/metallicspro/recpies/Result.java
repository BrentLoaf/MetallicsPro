package org.brent.metallicspro.recpies;

import org.bukkit.inventory.ItemStack;

public record Result(ItemStack item, int amount, String key) {

    public ItemStack getResult() {
        item.setAmount(amount);
        return item;
    }
}
