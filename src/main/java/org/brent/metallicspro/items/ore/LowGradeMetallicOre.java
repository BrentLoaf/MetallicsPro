package org.brent.metallicspro.items.ore;

import org.brent.metallicspro.items.metal.*;
import org.bukkit.Material;
import org.bukkit.inventory.ItemRarity;

public class LowGradeMetallicOre extends OreItem {

    public LowGradeMetallicOre() {
        super("Low-Grade Metallic", Material.RAW_COPPER, Material.RAW_COPPER_BLOCK, ItemRarity.COMMON, "low-grade_metallic_ore");

        addMetal(new Copper(), 12);
        addMetal(new Tin(), 7);
        addMetal(new Zinc(), 5);
        addMetal(new Aluminum(), 3);
    }
}
