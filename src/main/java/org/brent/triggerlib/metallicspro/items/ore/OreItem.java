package org.brent.triggerlib.metallicspro.items.ore;

import org.brent.triggerlib.metallicspro.items.CustomItem;
import org.brent.triggerlib.metallicspro.items.Rarity;
import org.bukkit.Material;

public abstract class OreItem extends CustomItem {

    public OreItem(String name, Material baseMaterial, Rarity rarity) {
        super(name, baseMaterial, rarity);
    }
}
