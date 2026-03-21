package org.brent.triggerlib.metallicspro.items;

import org.bukkit.ChatColor;

public enum Rarity {
    COMMON(ChatColor.WHITE),
    UNCOMMON(ChatColor.YELLOW),
    RARE(ChatColor.AQUA),
    EPIC(ChatColor.LIGHT_PURPLE);

    private final ChatColor color;

    Rarity(ChatColor color) {
        this.color = color;
    }

    public ChatColor getColor() {
        return color;
    }
}
