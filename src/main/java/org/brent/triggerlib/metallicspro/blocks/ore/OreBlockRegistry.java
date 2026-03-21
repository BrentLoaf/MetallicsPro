package org.brent.triggerlib.metallicspro.blocks.ore;

import java.util.HashSet;

public final class OreBlockRegistry {

    private final HashSet<OreBlock> oreBlocks = new HashSet<>();

    public OreBlockRegistry() {
        this.oreBlocks.add(new LowGradeMetallicOreBlock());
    }

    public HashSet<OreBlock> getOreBlocks() {
        return oreBlocks;
    }
}
