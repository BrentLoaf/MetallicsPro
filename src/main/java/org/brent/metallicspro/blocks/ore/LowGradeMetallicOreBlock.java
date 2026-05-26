package org.brent.metallicspro.blocks.ore;

import org.brent.metallicspro.items.ore.LowGradeMetallicOre;
import org.bukkit.Material;

import java.util.Map;

public final class LowGradeMetallicOreBlock extends OreBlock {

    public LowGradeMetallicOreBlock() {
        super(new LowGradeMetallicOre(), Map.of(
                Material.DEEPSLATE_COPPER_ORE, new int[]{1, 2},
                Material.COPPER_ORE, new int[]{1, 2},
                Material.RAW_COPPER_BLOCK, new int[]{9}
        ));
    }
}
