package org.brent.metallicspro.items.metal;

import org.brent.metallicspro.items.ItemRegistry;
import org.brent.metallicspro.items.ore.LowGradeMetallicOre;
import org.brent.metallicspro.items.ore.OreItem;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class MetalRegistry {

    private final List<OreItem> oreItems = new ArrayList<>();
    private final List<Metal> metals = new ArrayList<>();

    public MetalRegistry(ItemRegistry itemRegistry) {
        // Raw Ores
        add(new LowGradeMetallicOre(), itemRegistry);

        // Metals
        add(new Copper(), itemRegistry);
        add(new Zinc(), itemRegistry);
        add(new Iron(), itemRegistry);
    }

    private void add(Metal metal, ItemRegistry registry) {
        metals.add(metal);

        registry.add(metal.getNugget());
        registry.add(metal.getIngot());
        registry.add(metal.getPowder());
        registry.add(metal.getSmallPowder());

        // metal.init();
    }

    private void add(OreItem oreItem, ItemRegistry registry) {
        oreItems.add(oreItem);

        registry.add(oreItem.getRawOre());
        registry.add(oreItem.getCrushedOre());

        oreItem.init();
    }

    public @Nullable Metal getFromName(String name) {
        for (Metal metal : metals) {
            if (metal.nugget.getRawName().equalsIgnoreCase(name) ||
                    metal.ingot.getRawName().equalsIgnoreCase(name) ||
                    metal.powder.getRawName().equalsIgnoreCase(name) ||
                    metal.smallPowder.getRawName().equalsIgnoreCase(name)) {

                return metal;
            }
        }

        return null;
    }

    public List<Metal> getMetals() {
        return metals;
    }

    /*public @Nullable OreItem getFromName(String name) {
        for (Metal metal : metals) {
            if (metal.nugget.getRawName().equalsIgnoreCase(name) ||
                    metal.ingot.getRawName().equalsIgnoreCase(name) ||
                    metal.powder.getRawName().equalsIgnoreCase(name) ||
                    metal.smallPowder.getRawName().equalsIgnoreCase(name)) {

                return metal;
            }
        }
        return null;
    }*/

    public List<OreItem> getOreItems() {
        return oreItems;
    }
}
