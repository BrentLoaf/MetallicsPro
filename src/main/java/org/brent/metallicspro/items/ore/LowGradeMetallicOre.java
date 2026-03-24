package org.brent.metallicspro.items.ore;

import org.brent.metallicspro.items.metal.Copper;
import org.brent.metallicspro.items.metal.Iron;
import org.brent.metallicspro.items.metal.Zinc;
import org.bukkit.Material;
import org.bukkit.inventory.ItemRarity;

public class LowGradeMetallicOre extends OreItem {

    public LowGradeMetallicOre() {
        super("Low-Grade Metallic", Material.RAW_COPPER, ItemRarity.COMMON, "low-grade_sieve");

        addMetal(new Copper(), 12);
        addMetal(new Zinc(), 6);
        addMetal(new Iron(), 2);
    }
}
