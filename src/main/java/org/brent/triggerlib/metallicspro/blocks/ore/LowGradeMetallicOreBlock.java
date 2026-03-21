package org.brent.triggerlib.metallicspro.blocks.ore;

import org.brent.triggerlib.metallicspro.items.ore.LowGradeMetallicOreItem;
import org.bukkit.Material;

public final class LowGradeMetallicOreBlock extends OreBlock {

    public LowGradeMetallicOreBlock() {
        super(Material.COPPER_ORE, new LowGradeMetallicOreItem());
        dropAmount = 2;
    }
}
