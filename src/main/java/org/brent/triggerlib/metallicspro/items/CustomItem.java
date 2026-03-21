package org.brent.triggerlib.metallicspro.items;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public abstract class CustomItem {

    protected final String name;
    protected final Material baseMaterial;
    protected final Rarity rarity;

    public CustomItem(String name, Material baseMaterial, Rarity rarity) {
        this.name = name;
        this.baseMaterial = baseMaterial;
        this.rarity = rarity;
    }

    public ItemStack getItemStack() {
        ItemStack itemStack = new ItemStack(baseMaterial);

        itemStack.editMeta(meta -> {
            meta.setDisplayName(ChatColor.RESET + "" + rarity.getColor() + "" + name);
        });

        return itemStack;
    }

    public String getName() {
        return name;
    }

    public Material getBaseMaterial() {
        return baseMaterial;
    }

    public Rarity getRarity() {
        return rarity;
    }
}
