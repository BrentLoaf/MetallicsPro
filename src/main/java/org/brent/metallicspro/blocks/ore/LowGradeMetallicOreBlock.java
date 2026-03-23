package org.brent.metallicspro.blocks.ore;

import org.brent.metallicspro.items.ore.LowGradeMetallicOre;
import org.bukkit.Material;

public final class LowGradeMetallicOreBlock extends OreBlock {

    public LowGradeMetallicOreBlock() {
        super(Material.COPPER_ORE, new LowGradeMetallicOre());
        dropAmount = 2;
    }
}
