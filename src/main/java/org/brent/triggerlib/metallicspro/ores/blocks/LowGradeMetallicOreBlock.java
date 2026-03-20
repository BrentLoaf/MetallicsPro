package org.brent.triggerlib.metallicspro.ores.blocks;

import org.brent.triggerlib.metallicspro.ores.items.LowGradeMetallicOreItem;
import org.bukkit.Material;

public final class LowGradeMetallicOreBlock extends OreBlock {

    public LowGradeMetallicOreBlock() {
        super(Material.COPPER_ORE, new LowGradeMetallicOreItem());
        dropAmount = 2;
    }
}
